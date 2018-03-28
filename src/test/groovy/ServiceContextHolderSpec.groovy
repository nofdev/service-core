import org.nofdev.servicefacade.ServiceContext
import org.nofdev.servicefacade.ServiceContextHolder
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ServiceContextHolderSpec extends Specification{

    def "测试清除上下文"(){
        setup:
        ServiceContext serviceContext = new ServiceContext()
        serviceContext.putUserdata("aa","11")
        ServiceContextHolder.setServiceContext(serviceContext)
        def bb = ServiceContextHolder.serviceContext
        println bb
        println Thread.currentThread().name
        CompletableFuture.runAsync({
            println Thread.currentThread().name
            sleep(1000)
            println "new Thread"+ServiceContextHolder.serviceContext
            ServiceContextHolder.setServiceContext(bb)
            println "new Thread2"+ServiceContextHolder.serviceContext
        },new ThreadPoolExecutor(200, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()))
        ServiceContextHolder.getServiceContext().clear()
        println bb
        println ServiceContextHolder.serviceContext
        sleep(2000)
        expect:
        1==1
    }

    def "测试清除上下文2"(){
        setup:
        def executor = new ThreadPoolExecutor(200, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>())

        ServiceContext serviceContext = new ServiceContext()
        serviceContext.putUserdata("aa","11")
        ServiceContextHolder.setServiceContext(serviceContext)

        def bb = ServiceContextHolder.serviceContext
        println bb

        CompletableFuture.runAsync(new RunAsyncImpl() {
            @Override
            void run() {
                super.copyParentThreadServiceContext()
                println "new thread"+ServiceContextHolder.serviceContext
            }
        }, executor)

        ServiceContextHolder.clearContext()

        println "main thread clear" + ServiceContextHolder.serviceContext
        sleep(2000)
        expect:
        1==1
    }
}
abstract class CopyParentThreadServiceContext {
    private ServiceContext nofdevCtx

    CopyParentThreadServiceContext() {
        nofdevCtx = ServiceContextHolder.getServiceContext()
        println "2"
    }

    void copyParentThreadServiceContext() {
        ServiceContextHolder.setServiceContext(nofdevCtx)
    }
}

abstract class RunAsyncImpl extends CopyParentThreadServiceContext implements Runnable {
    @Override
    abstract void run()
}
