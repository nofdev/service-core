package org.nofdev.servicefacade

import groovy.transform.InheritConstructors

/**
 * Created by Qiang on 11/2/15.
 */
@InheritConstructors
class ServiceNotFoundException extends AbstractBusinessException{
    static String DEFAULT_EXCEPTION_MESSAGE = "该服务不存在"

    ServiceNotFoundException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }
}
