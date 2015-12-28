package org.nofdev.servicefacade

import groovy.transform.CompileStatic

/**
 * 支持分页的结果集，内含总条目数，当前页码，每页条目数等信息
 *
 * @author Richard  Zhang
 */
@CompileStatic
class PagedList<T> {

    /**
     * 总页数
     */
    long totalPage
    /**
     * 信息总数
     */
    long totalCount
    /**
     * 当前页数
     */
    long currentPage
    /**
     * 每页的信息条数
     */
    int pageSize
    /**
     * 信息列表
     */
    ArrayList<T> list

    @Deprecated
    PagedList() {

    }

    PagedList(long totalCount, long currentPage, int pageSize, Collection<T> list) {
        this.totalCount = totalCount
        this.currentPage = currentPage
        this.pageSize = pageSize
        this.list = list
    }

    PagedList(long totalCount, Paginator paginator, Collection<T> list) {
        this.totalCount = totalCount
        this.currentPage = paginator.page
        this.pageSize = paginator.pageSize
        this.list = list
    }

    long getTotalPage() {
        if (totalPage == 0) {
            if (this.totalCount <= 0) {
                totalPage = 1;
            } else {
                totalPage = this.totalCount % this.pageSize > 0 ? this.totalCount / this.pageSize + 1 : this.totalCount / this.pageSize;
            }
        }
        return totalPage;
    }

    static <E> PagedList<E> wrap(Collection<E> collection) {
        return new PagedList<E>(collection.size(), Paginator.page(1, collection.size()), collection);
    }
}
