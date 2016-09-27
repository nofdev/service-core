package org.nofdev.servicefacade

/**
 * Created by Liutengfei on 2016/9/23 0023.
 */
interface Authentication {
    void tokenToUser(String packageName,
                     String interfaceName,
                     String methodName,
                     Object params,
                     Map<String, String> header)
}
