package org.nofdev.exception

import org.nofdev.servicefacade.AbstractBusinessException

/**
 * Created by Liutengfei on 2017/8/5 0005.
 */
class ParamsException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "无效的参数"

    ParamsException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    ParamsException(String message) {
        super(message)
    }

    ParamsException(String message, Throwable cause) {
        super(message, cause)
    }

    ParamsException(Throwable cause) {
        super(cause)
    }

    ParamsException(String message, Object detail) {
        super(message, detail)
    }

    ParamsException(String message, Throwable cause, Object detail) {
        super(message, cause, detail)
    }

    ParamsException(Throwable cause, Object detail) {
        super(cause, detail)
    }
}
