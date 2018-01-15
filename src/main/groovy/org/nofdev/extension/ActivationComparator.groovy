
package org.nofdev.extension

class ActivationComparator<T> implements Comparator<T> {
    /**
     * order 大的排在后面
     * 如果没有设置order的排到最前面
     */
    @Override
    int compare(T o1, T o2) {
        Activate p1 = o1.getClass().getAnnotation(Activate.class)
        Activate p2 = o2.getClass().getAnnotation(Activate.class)
        if (p1 == null) {
            return 1
        } else if (p2 == null) {
            return -1
        } else {
            return p1.order() - p2.order()
        }
    }


}