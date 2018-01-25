package com.leo.util.algorithm.bst;

import java.util.Optional;

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
public interface BinarySearchTree<K, V> {

    /**
     * 将节点放入树中. 当节点已存在时,则更新value
     *
     * @param key   键,不允许为空
     * @param value 值,不允许为空
     * @return 返回旧的value值或null
     * @throws IllegalArgumentException 当key或value为null时，抛出异常
     */
    Optional put(K key, V value) throws IllegalArgumentException;

    /**
     * 根据key查找value
     *
     * @param key 键
     * @return 返回查找到的value或null
     * @throws IllegalArgumentException 当key = null时，抛出异常
     */
    Optional<V> get(K key) throws IllegalArgumentException;

    /**
     * 获取树高
     *
     * @return
     */
    int getHight();
}

