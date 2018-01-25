package com.leo.util.algorithm.bst;

/**
 * 节点.
 * 保存:
 * 1. 一个value
 * 2. 两个(左右)节点.左节点的value都小于该节点的value;右节点的value都大于该节点的value
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/22
 * @since 1.0
 */
public class BinaryNode<T> {

    /**
     * 保存真实值
     */
    protected T value;

    /**
     * 指向父节点.永不为null
     */
    protected BinaryNode parent;

    /**
     * 指向左节点
     */
    protected BinaryNode left;

    /**
     * 指向右节点
     */
    protected BinaryNode right;

    protected BinaryNode(T value) {
        this(value, null, null);
    }

    protected BinaryNode(T value, BinaryNode left, BinaryNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }


    /**
     * 获取一对K-V键值对
     */
    T getValue() {
        return value;
    }

    /**
     * 两个节点建立左连接关系
     *
     * @param leftChild 要被设置城的左子节节点
     * @return 返回当前节点, 便于链式调用
     */
    protected BinaryNode buildLeftRelation(BinaryNode leftChild) {
        if (leftChild != null) {
            leftChild.parent = this;
        }

        this.left = leftChild;
        return this;
    }

    /**
     * 两个节点建立右连接关系
     *
     * @param rightChild 要被设置城的父节点
     * @return 返回当前节点, 便于链式调用
     */
    protected BinaryNode buildRightRelation(BinaryNode rightChild) {
        if (rightChild != null) {
            rightChild.parent = this;
        }

        this.right = rightChild;
        return this;
    }
}