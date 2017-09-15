package org.nofdev.exception

import org.nofdev.servicefacade.AbstractBusinessException

/**
 * Created by Liutengfei on 2016/9/23 0023.
 */
class AuthenticationException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "认证失败异常"

    AuthenticationException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    AuthenticationException(String message) {
        super(message)
    }

    AuthenticationException(String message, Throwable cause) {
        super(message, cause)
    }

    AuthenticationException(Throwable cause) {
        super(cause)
    }

    AuthenticationException(String message, Object datail) {
        super(message, datail)
    }

    AuthenticationException(String message, Throwable cause, Object datail) {
        super(message, cause, datail)
    }

    AuthenticationException(Throwable cause, Object datail) {
        super(cause, datail)
    }
}
