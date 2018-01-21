package com.leo.util.algorithm.bst;

import com.leo.util.algorithm.Arrays;

import java.util.Comparator;

/**
 * 所有实现了二叉查找树接口的基类
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/19
 * @since 1.0
 */
public abstract class AbstractBinarySearchTree<K, V> implements BinarySearchTree<K, V> {

    /**
     * key的比较器,用来比较key的大小
     */
    protected Comparator<K> comparator;

    /**
     * K-V键值对
     */
    protected class KVNode {

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

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }

    /**
     * 节点.
     * 保存:
     * 1. 一对key-value
     * 2. 两个节点.左节点的key都小于该节点的key;右节点的key都大于该节点的key
     */
    protected class BinaryNode {

        /**
         * 保存K-V键值对
         */
        protected KVNode kvNode;

        /**
         * 指向左节点
         */
        protected BinaryNode left;

        /**
         * 指向右节点
         */
        protected BinaryNode right;

        /**
         * 保存以该节点为根的子树中的节点总数
         */
        protected int size;

        public BinaryNode() {
        }

        protected BinaryNode(KVNode kvNode) {
            this(kvNode, null, null);
        }

        protected BinaryNode(BinaryNode left, BinaryNode right) {
            this(null, left, right);
        }

        protected BinaryNode(KVNode kvNode, BinaryNode left, BinaryNode right) {
            this.kvNode = kvNode;
            this.left = left;
            this.right = right;
        }
    }

    public AbstractBinarySearchTree(Comparator<K> comparator) {
        this.comparator = (comparator == null ? generateCompator() : comparator);
    }


    /**
     * 生成比较器
     *
     * @return
     */
    protected Comparator<K> generateCompator() {
        return (k1, k2) -> ((Comparable) k1).compareTo(k2);
    }

    /**
     * 按照key将两个KVNode从小到大排序
     */
    protected final void sort(Object[] kvNodes) {
        Arrays.shellSort(kvNodes, (kvNode1, kvNode2) -> comparator.compare(((KVNode) kvNode1).key, ((KVNode) kvNode2).key));
    }

}
