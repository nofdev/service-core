package org.nofdev.exception

import org.nofdev.servicefacade.AbstractBusinessException

/**
 * Created by Liutengfei on 2016/4/21 0021.
 */
class AuthorizationException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "授权失败异常";

    AuthorizationException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    AuthorizationException(String message) {
        super(message)
    }

    AuthorizationException(String message, Throwable cause) {
        super(message, cause)
    }

    AuthorizationException(Throwable cause) {
        super(cause)
    }
}
