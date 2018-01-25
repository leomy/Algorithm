package com.leo.util.algorithm.bst;

/**
 * 二叉查找树顶层接口 <br/>
 * Note: 由于用key做比较，因此建议: <br/>
 * 1. key实现了Comparable接口 <br/>
 * 2. 或自定义比较器 <br/>
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/19
 * @since 1.0
 */
public interface BinarySearchTree<T> {

    /**
     * 将元素放入树中
     *
     * @param value 元素
     * @return 返回旧的
     * @throws IllegalArgumentException 当t = null时，抛出异常
     */
    void put(T value) throws IllegalArgumentException;

    /**
     * 是否包含元素
     *
     * @param value
     * @return 返回查找到的value或null
     * @throws IllegalArgumentException 当t = null时，抛出异常
     */
    boolean contains(T value) throws IllegalArgumentException;

    /**
     * 获取树高
     *
     * @return
     */
    int getHight();
}

