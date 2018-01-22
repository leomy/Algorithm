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

    public AbstractBinarySearchTree(Comparator<K> comparator) {
        this.comparator = (comparator == null ? generateCompator() : comparator);
    }

    /**
     * 生成比较器
     *
     * @return
     */
    private Comparator<K> generateCompator() {
        return (k1, k2) -> ((Comparable) k1).compareTo(k2);
    }

    /**
     * 按照key将两个KVNode从小到大排序
     */
    protected final void sort(KVNode[] nodes) {
        Arrays.shellSort(nodes, (node1, node2) -> comparator.compare((K) (node1.key), (K) (node2.key)));
    }
}
