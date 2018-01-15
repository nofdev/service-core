package org.nofdev.core

import org.nofdev.extension.Spi

/**
 * Created by Liutengfei on 2017/10/26
 */
@Spi
interface RpcFilter {
    abstract Object invoke(Caller caller, Request request) throws Throwable
}