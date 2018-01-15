package com.leo.util.datastructure.list;

import com.leo.util.datastructure.AbstractCollection;
import com.leo.util.datastructure.List;

/**
 * 所有实现了List接口的基类
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/20
 * @since 1.0
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

    /**
     * 检查索引是否在给定范围内
     *
     * @param index
     * @throws IndexOutOfBoundsException 当index 不在[0,size)范围时抛出异常
     */
    protected void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(indexAndSizeMassage(index));
        }
    }

    /**
     * 生成包含index,size信息的字符串
     *
     * @param index 索引
     * @return 生成的字符串
     */
    protected String indexAndSizeMassage(int index) {
        return new StringBuilder().append("Index: ").append(index).append(", Size: ").append(size).toString();
    }

    /**
     * 检查元素是否为空
     *
     * @param e 待检查的元素
     * @throws NullPointerException 当e为null时,抛出异常
     */
    protected void checkElement(E e) throws NullPointerException {
        if (e == null) {
            throw new NullPointerException("the value of e is null");
        }
    }
}
