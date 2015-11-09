package org.nofdev.servicefacade;

/**
 * Created by Qiang on 11/6/15.
 */
public class ServiceContextHolder {
    private static ThreadLocal<ServiceContext> threadLocal = new ThreadLocal<ServiceContext>() {
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
}
