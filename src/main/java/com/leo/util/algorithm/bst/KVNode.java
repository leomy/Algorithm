package com.leo.util.algorithm.bst;

/**
 * 保存K-V键值对
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/22
 * @since 1.0
 */
public class KVNode<K, V> {

    /**
     * 保存键
     */
    K key;

    /**
     * 保存值
     */
    V value;

    public KVNode() {
    }

    public KVNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }


    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key.toString() + ":" + value.toString();
    }
}