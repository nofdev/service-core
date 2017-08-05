package org.nofdev.exception

import groovy.transform.InheritConstructors
import org.nofdev.servicefacade.AbstractBusinessException

/**
 * Created by Liutengfei on 2017/8/5 0005.
 */
@InheritConstructors
class ParamsException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "无效的参数"

    ParamsException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }
}
