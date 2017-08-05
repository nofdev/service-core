package org.nofdev.servicefacade

import groovy.transform.InheritConstructors

/**
 * Created by Qiang on 04/05/2017.
 * 通常调用方没有服务方抛出的异常类时会报这个异常
 */
@InheritConstructors
class ErrorDeserializedException extends AbstractBusinessException{
    static String DEFAULT_EXCEPTION_MESSAGE = "错误信息反序列化异常"

    ErrorDeserializedException() {
        super(DEFAULT_EXCEPTION_MESSAGE)
    }
}
