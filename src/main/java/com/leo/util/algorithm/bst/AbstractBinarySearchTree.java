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
     * 保存根节点
     */
    protected BinaryNode root;

    /**
     * 保存树高
     */
    protected int hight;

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
    private static final Comparator generateCompator() {
        return (k1, k2) -> ((Comparable) k1).compareTo(k2);
    }

    /**
     * 按照key将两个KVNode从小到大排序
     *
     * @return 返回从小到大的数组，
     */
    protected final KVNode[] sort(KVNode[] nodes) {
        return Arrays.shellSort(nodes, (node1, node2) -> comparator.compare((K) (node1.key), (K) (node2.key))).orElse(null);
    }

    @Override
    public int getHight() {
        return hight;
    }

    /**
     * 将oldNode父节点的相应指向(left,right)变成newNode，且将newNode节点的的parent指向oldNode的父节点
     *
     * @param newNode 要替换的新节点
     * @param oldNode 要被替换的旧节点
     * @return 返回true, 替换成功.返回false,替换失败
     */
    protected boolean replace(BinaryNode newNode, BinaryNode oldNode) {
        if (newNode == null || oldNode == null) {
            return false;
        }

        if (root.equals(oldNode)) {
            root = newNode;
            root.parent = newNode;
            return true;
        }

        BinaryNode oldNodeParent = oldNode.parent;
        if (oldNode.equals(oldNodeParent.left)) {
            oldNodeParent.buildLeftRelation(newNode);
            return true;
        }

        if (oldNode.equals(oldNodeParent.right)) {
            oldNodeParent.buildRightRelation(newNode);
            return true;
        }

        return false;
    }
}
