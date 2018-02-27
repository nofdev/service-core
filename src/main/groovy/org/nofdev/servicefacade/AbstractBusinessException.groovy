package org.nofdev.servicefacade;

/**
 * 业务逻辑异常基类
 * <p/>
 * 所有业务系统能够捕获的异常都要转换为具体类型的业务逻辑异常抛出，如OrderNotFoundException
 */
abstract class AbstractBusinessException<T> extends RuntimeException {
    static String DEFAULT_EXCEPTION_MESSAGE = "业务逻辑异常"

    T detail
    @Deprecated
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

    AbstractBusinessException(String message, T detail) {
        super(message)
        this.detail = detail
        this.datail = detail
    }

    AbstractBusinessException(String message, Throwable cause, T detail) {
        super(message, cause)
        this.detail = detail
        this.datail = detail
    }

    AbstractBusinessException(Throwable cause, T detail) {
        super(cause)
        this.detail = detail
        this.datail = detail
    }

}
