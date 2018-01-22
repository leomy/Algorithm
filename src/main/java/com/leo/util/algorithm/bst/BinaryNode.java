package com.leo.util.algorithm.bst;

/**
 * 节点.
 * 保存:
 * 1. 一对key-value
 * 2. 两个(左右)节点.左节点的key都小于该节点的key;右节点的key都大于该节点的key
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/22
 * @since 1.0
 */
class BinaryNode {

    /**
     * 保存K-V键值对
     */
    protected KVNode kvNode;


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


    /**
     * 获取一对K-V键值对
     */
    KVNode getKVNode() {
        return kvNode;
    }

    /**
     * 设置KV键值对
     *
     * @param kvNode
     */
    void setKVNode(KVNode kvNode) {
        this.kvNode = kvNode;
    }

    /**
     * 两个节点建立左连接关系
     *
     * @param leftChild 要被设置城的左子节节点
     */
    protected void buildLeftRelation(BinaryNode leftChild) {
        if (leftChild != null) {
            leftChild.parent = this;
        }
        this.left = leftChild;
    }

    /**
     * 两个节点建立右连接关系
     *
     * @param rightChild 要被设置城的父节点
     */
    protected void buildRightRelation(BinaryNode rightChild) {
        if (rightChild != null) {
            rightChild.parent = this;
        }
        this.right = rightChild;
    }

    /**
     * 将oldNode父节点的相应指向变成当前节点，且将当前节点的的parent指向oldNode的父节点
     *
     * @param oldNode 要被替换的旧节点
     * @return 返回true, 替换成功.返回false,替换失败
     */
    protected boolean replace(BinaryNode oldNode) {
        BinaryNode oldNodeParent = oldNode.parent;
        if (oldNodeParent.left.equals(oldNode)) {
            oldNodeParent.buildLeftRelation(this);
            return true;
        }


        if (oldNodeParent.right.equals(oldNode)) {
            oldNodeParent.buildRightRelation(this);
            return true;
        }
        return false;
    }
}
