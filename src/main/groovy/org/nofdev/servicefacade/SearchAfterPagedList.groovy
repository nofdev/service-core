package org.nofdev.servicefacade

import groovy.transform.CompileStatic

/**
 * 支持滚动查询的结果集，内含总条目数，没页信息条数, 本次查询的最后一条记录的定位值等信息
 *
 * @author Richard  Zhang
 */
@CompileStatic
class SearchAfterPagedList<T> {

    /**
     * 信息总数
     */
    long totalCount
    /**
     * 每页的信息条数
     */
    int pageSize
    /**
     * 信息列表
     */
    ArrayList<T> list
    /**
     * 本次查询的最后一条记录的定位值, 要跟排序字段一一对应, 要包含能唯一标识一条记录的字段. 用以滚动查询的 paginator 入参
     * 实际上应该是一个 tuple 类型
     */
    List<Object> searchAfter

    SearchAfterPagedList(long totalCount, int pageSize, List<Object> searchAfter, ArrayList<T> list) {
        this.totalCount = totalCount
        this.searchAfter = searchAfter
        this.pageSize = pageSize
        this.list = list
    }

}
