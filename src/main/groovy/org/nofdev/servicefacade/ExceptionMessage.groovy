package org.nofdev.servicefacade
/**
 * Created by Qiang on 7/31/14.
 */
class ExceptionMessage<T> {
    T detail
    String name
    String msg
    ExceptionMessage cause
    String stack
    HashMap<String, ExceptionMessage> children
}
