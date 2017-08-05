package org.nofdev.exception

import groovy.transform.InheritConstructors
import org.nofdev.servicefacade.AbstractBusinessException;

@InheritConstructors
class BatchException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "批处理异常"

    BatchException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }
}