package com.sven.wms.db.param.impl;

import java.io.Serializable;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class VarableBind<E> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String column;
    //有可能是Object,或者是List<Object>
    private E value;

    public VarableBind(String column) {
        this.column = column;
    }

    public VarableBind(String column, E value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
