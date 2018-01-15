package org.nofdev.extension

import org.nofdev.servicefacade.AbstractBusinessException

/**
 * Created by Liutengfei on 2018/1/12
 */
class ExtensionException extends AbstractBusinessException{
    static String DEFAULT_EXCEPTION_MESSAGE = "SPI扩展异常"

    ExtensionException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }

    ExtensionException(String message) {
        super(message)
    }

    ExtensionException(String message, Throwable cause) {
        super(message, cause)
    }

    ExtensionException(Throwable cause) {
        super(cause)
    }

    ExtensionException(String message, Object detail) {
        super(message, detail)
    }

    ExtensionException(String message, Throwable cause, Object detail) {
        super(message, cause, detail)
    }

    ExtensionException(Throwable cause, Object detail) {
        super(cause, detail)
    }
}
