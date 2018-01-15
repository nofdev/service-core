package org.nofdev.extension

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import java.lang.reflect.Constructor
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Created by Liutengfei on 2018/1/12
 */
@CompileStatic
@Slf4j
class ExtensionLoader<T> {
    private static final String SPI_SERVICE_PREFIX = "META-INF/services/"

    private
    static ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<Class<?>, ExtensionLoader<?>>()

    private ConcurrentMap<String, T> singletonInstances = null
    private ConcurrentMap<String, Class<T>> extensionClasses = null

    private Class<T> type
    private ClassLoader classLoader

    private volatile boolean init = false

    private ExtensionLoader(Class<T> type) {
        this(type, Thread.currentThread().getContextClassLoader())
    }

    private ExtensionLoader(Class<T> type, ClassLoader classLoader) {
        this.type = type
        this.classLoader = classLoader
    }

    private void checkInit() {
        if (!init) {
            loadExtensionClasses()
        }
    }

    Class<T> getExtensionClass(String name) {
        checkInit()

        return extensionClasses.get(name)
    }

    T getExtension(String name) {
        checkInit()

        if (name == null) {
            return null
        }

        try {
            Spi spi = type.getAnnotation(Spi.class)

            if (spi.scope() == Scope.SINGLETON) {
                return getSingletonInstance(name)
            } else {
                Class<T> clz = extensionClasses.get(name)

                if (clz == null) {
                    return null
                }

                return clz.newInstance()
            }
        } catch (Exception e) {
            failThrows(type, "Error when getExtension " + name, e)
        }

        return null
    }

    private T getSingletonInstance(String name) throws InstantiationException, IllegalAccessException {
        T obj = singletonInstances.get(name)

        if (obj != null) {
            return obj
        }

        Class<T> clz = extensionClasses.get(name)

        if (clz == null) {
            return null
        }

        synchronized (singletonInstances) {
            obj = singletonInstances.get(name)
            if (obj != null) {
                return obj
            }

            obj = clz.newInstance()
            singletonInstances.put(name, obj)
        }

        return obj
    }

    void addExtensionClass(Class<T> clz) {
        if (clz == null) {
            return
        }

        checkInit()

        checkExtensionType(clz)

        String spiName = getSpiName(clz)

        synchronized (extensionClasses) {
            if (extensionClasses.containsKey(spiName)) {
                failThrows(clz, ":Error spiName already exist " + spiName)
            } else {
                extensionClasses.put(spiName, clz)
            }
        }
    }

    private synchronized void loadExtensionClasses() {
        if (init) {
            return
        }

        extensionClasses = loadExtensionClasses(SPI_SERVICE_PREFIX)
        singletonInstances = new ConcurrentHashMap<String, T>()

        init = true
    }

    static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        checkInterfaceType(type)

        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type)

        if (loader == null) {
            loader = initExtensionLoader(type)
        }
        return loader
    }

    static synchronized <T> ExtensionLoader<T> initExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type)

        if (loader == null) {
            loader = new ExtensionLoader<T>(type)

            extensionLoaders.putIfAbsent(type, loader)

            loader = (ExtensionLoader<T>) extensionLoaders.get(type)
        }

        return loader
    }

    List<T> getExtensions(String key) {
        checkInit()

        if (extensionClasses.size() == 0) {
            return Collections.emptyList()
        }

        List<T> exts = new ArrayList<T>(extensionClasses.size())

        for (Map.Entry<String, Class<T>> entry : extensionClasses.entrySet()) {
            SpiMeta activation = entry.getValue().getAnnotation(SpiMeta.class)
            if (key == null || key.trim().length() == 0) {
                exts.add(getExtension(entry.getKey()))
            } else if (activation != null && activation.key() != null) {
                for (String k : activation.key()) {
                    if (key == k) {
                        exts.add(getExtension(entry.getKey()))
                        break
                    }
                }
            }
        }
        Collections.sort(exts, new ActivationComparator<T>())
        return exts
    }

    private static <T> void checkInterfaceType(Class<T> clz) {
        if (clz == null) {
            failThrows(clz, "Error extension type is null")
        }

        if (!clz.isInterface()) {
            failThrows(clz, "Error extension type is not interface")
        }

        if (!isSpiType(clz)) {
            failThrows(clz, "Error extension type without @Spi annotation")
        }
    }

    private void checkExtensionType(Class<T> clz) {
        checkClassPublic(clz)

        checkConstructorPublic(clz)

        checkClassInherit(clz)
    }

    private void checkClassInherit(Class<T> clz) {
        if (!type.isAssignableFrom(clz)) {
            failThrows(clz, "Error is not instanceof " + type.getName())
        }
    }

    private void checkClassPublic(Class<T> clz) {
        if (!Modifier.isPublic(clz.getModifiers())) {
            failThrows(clz, "Error is not a public class")
        }
    }

    private void checkConstructorPublic(Class<T> clz) {
        Constructor<?>[] constructors = clz.getConstructors()

        if (constructors == null || constructors.length == 0) {
            failThrows(clz, "Error has no public no-args constructor")
        }

        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                return
            }
        }

        failThrows(clz, "Error has no public no-args constructor")
    }

    private static <T> boolean isSpiType(Class<T> clz) {
        return clz.isAnnotationPresent(Spi.class)
    }

    private ConcurrentMap<String, Class<T>> loadExtensionClasses(String prefix) {
        String fullName = prefix + type.getName()
        List<String> classNames = new ArrayList<String>()

        try {
            Enumeration<URL> urls
            if (classLoader == null) {
                urls = ClassLoader.getSystemResources(fullName)
            } else {
                urls = classLoader.getResources(fullName)
            }

            if (urls == null || !urls.hasMoreElements()) {
                return new ConcurrentHashMap<String, Class<T>>()
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement()

                parseUrl(type, url, classNames)
            }
        } catch (Exception e) {
            throw new ExtensionException(
                    "ExtensionLoader loadExtensionClasses error, prefix: " + prefix + " type: " + type.getClass(), e)
        }

        return loadClass(classNames)
    }

    private ConcurrentMap<String, Class<T>> loadClass(List<String> classNames) {
        ConcurrentMap<String, Class<T>> map = new ConcurrentHashMap<String, Class<T>>()

        for (String className : classNames) {
            try {
                Class<T> clz
                if (classLoader == null) {
                    clz = (Class<T>) Class.forName(className)
                } else {
                    clz = (Class<T>) Class.forName(className, true, classLoader)
                }

                checkExtensionType(clz)

                String spiName = getSpiName(clz)

                if (map.containsKey(spiName)) {
                    failThrows(clz, ":Error spiName already exist " + spiName)
                } else {
                    map.put(spiName, clz)
                }
            } catch (Exception e) {
                log.error("${type.getName()}: Error load spi class", e)
            }
        }

        return map

    }

    String getSpiName(Class<?> clazz) {
        SpiMeta spiMeta = clazz.getAnnotation(SpiMeta.class)
        //如果扩展类有SpiMeta的注解，那么获取对应的name，如果没有的话获取classname
        String name = (spiMeta != null && "" != spiMeta.name()) ? spiMeta.name() : clazz.getSimpleName()

        return name
    }

    private void parseUrl(Class<T> type, URL url, List<String> classNames) throws ServiceConfigurationError {
        InputStream inputStream = null
        BufferedReader reader = null
        try {
            inputStream = url.openStream()
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))

            String line = null
            int indexNumber = 0

            while ((line = reader.readLine()) != null) {
                indexNumber++
                parseLine(type, url, line, indexNumber, classNames)
            }
        } catch (Exception x) {
            log.error("${type.getName()}: Error reading spi configuration file", x)
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
                if (inputStream != null) {
                    inputStream.close()
                }
            } catch (IOException y) {
                log.error("${type.getName()}: Error closing spi configuration file", y)
            }
        }
    }

    private void parseLine(Class<T> type, URL url, String line, int lineNumber, List<String> names) throws IOException,
            ServiceConfigurationError {
        int ci = line.indexOf('#')

        if (ci >= 0) {
            line = line.substring(0, ci)
        }

        line = line.trim()

        if (line.length() <= 0) {
            return
        }

        if ((line.indexOf(' ') >= 0) || (line.indexOf('\t') >= 0)) {
            failThrows(type, url, lineNumber, "Illegal spi configuration-file syntax")
        }

        int cp = line.codePointAt(0)
        if (!Character.isJavaIdentifierStart(cp)) {
            failThrows(type, url, lineNumber, "Illegal spi provider-class name: " + line)
        }

        for (int i = Character.charCount(cp); i < line.length(); i += Character.charCount(cp)) {
            cp = line.codePointAt(i)
            if (!Character.isJavaIdentifierPart(cp) && (cp != '.')) {
                failThrows(type, url, lineNumber, "Illegal spi provider-class name: " + line)
            }
        }

        if (!names.contains(line)) {
            names.add(line)
        }
    }


    private static <T> void failThrows(Class<T> type, String msg, Throwable cause) {
        throw new ExtensionException(type.getName() + ": " + msg, cause)
    }

    private static <T> void failThrows(Class<T> type, String msg) {
        throw new ExtensionException(type.getName() + ": " + msg)
    }

    private static <T> void failThrows(Class<T> type, URL url, int line, String msg) throws ServiceConfigurationError {
        failThrows(type, "${url}:${line}:${msg}")
    }

}
