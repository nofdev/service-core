package org.nofdev.servicefacade;

/**
 * Created by Qiang on 11/6/15.
 */
public class ServiceContextHolder {
    private static InheritableThreadLocal<ServiceContext> threadLocal = new InheritableThreadLocal<ServiceContext>() {
        @Override
        protected ServiceContext initialValue() {
            return new ServiceContext();
        }
    };

    public static void setServiceContext(ServiceContext serviceContext) {
        threadLocal.set(serviceContext);
    }

    public static ServiceContext getServiceContext() {
        return threadLocal.get();
    }

    public static void clearContext(){
        setServiceContext(new ServiceContext())
    }
}
