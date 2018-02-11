package org.nofdev.servicefacade

/**
 * Created by Liutengfei on 2018/2/9
 */
class Sort {
    Sort(String field, Direction direction) {
        this.field = field
        this.direction = direction
    }

    Sort() {
    }

    String field
    Direction direction
}
