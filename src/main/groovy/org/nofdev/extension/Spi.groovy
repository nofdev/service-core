package org.nofdev.extension

import java.lang.annotation.*

/**
 * Created by Liutengfei on 2018/1/12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@interface Activate {
    //顺序，越小越在前
    int order() default 0

    //spi 的key,获取spi列表时，根据key进行匹配，当key中存在待过滤的search-key时，匹配成功
    String[] key() default []

}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@interface Spi {

    Scope scope() default Scope.PROTOTYPE

}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@interface SpiMeta {

    String name() default ""

}

enum Scope {
    /**
     * 单例模式
     */
    SINGLETON,

    /**
     * 多例模式
     */
    PROTOTYPE
}