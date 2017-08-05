package org.nofdev.exception

import groovy.transform.InheritConstructors;
import org.nofdev.servicefacade.AbstractBusinessException;

/**
 * Created by Liutengfei on 2016/9/23 0023.
 */
@InheritConstructors
class AuthenticationException extends AbstractBusinessException {
    static String DEFAULT_EXCEPTION_MESSAGE = "认证失败异常"

    AuthenticationException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }
}
