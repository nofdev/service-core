package org.nofdev.servicefacade;

/**
 * 业务逻辑异常基类
 * <p/>
 * 所有业务系统能够捕获的异常都要转换为具体类型的业务逻辑异常抛出，如OrderNotFoundException
 */
abstract class AbstractBusinessException<T> extends RuntimeException {
    static String DEFAULT_EXCEPTION_MESSAGE = "业务逻辑异常";

    T datail

    AbstractBusinessException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    AbstractBusinessException(String message) {
        super(message)
    }

    AbstractBusinessException(String message, Throwable cause) {
        super(message, cause)
    }

    AbstractBusinessException(Throwable cause) {
        super(cause)
    }
    AbstractBusinessException(String message,T datail) {
        super(message)
        this.datail=datail
    }

    AbstractBusinessException(String message, Throwable cause,T datail) {
        super(message, cause)
        this.datail=datail
    }

    AbstractBusinessException(Throwable cause,T datail) {
        super(cause)
        this.datail=datail
    }

}
