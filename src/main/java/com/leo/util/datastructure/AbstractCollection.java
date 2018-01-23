package com.leo.util.datastructure;

/**
 * 所有实现了Collection接口的基类
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/5
 * @since 1.0
 */
public abstract class AbstractCollection<E> implements Collection<E> {

    /**
     * 实际长度
     */
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

}
