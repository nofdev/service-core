package org.nofdev.exception

import org.nofdev.servicefacade.AbstractBusinessException

class BatchException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "批处理异常"

    BatchException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    BatchException(String message) {
        super(message)
    }

    BatchException(String message, Throwable cause) {
        super(message, cause)
    }

    BatchException(Throwable cause) {
        super(cause)
    }

    BatchException(String message, Object detail) {
        super(message, detail)
    }

    BatchException(String message, Throwable cause, Object detail) {
        super(message, cause, detail)
    }

    BatchException(Throwable cause, Object detail) {
        super(cause, detail)
    }
}