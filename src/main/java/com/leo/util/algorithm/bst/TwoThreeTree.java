package com.leo.util.algorithm.bst;

import java.util.Comparator;
import java.util.Optional;

/**
 * 二三树的实现
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/19
 * @since 1.0
 */
public class TwoThreeTree<K, V> extends AbstractBinarySearchTree<K, V> implements BinarySearchTree<K, V> {

    /**
     * 保存根节点
     */
    protected TwoNode root;

    /**
     * 2-节点.
     * 保存:
     * 1. 一对key-value.
     * 2. 两个节点. 左节点的key都小于该节点的key;右节点的key都大于该节点的key
     */
    private static class TwoNode extends BinaryNode {

        /**
         * flag = true时，表示2-节点; flag = false时,表示3-节点
         */
        boolean flag;

        {
            flag = true;
        }

        TwoNode(KVNode kvNode) {
            this(kvNode, null, null, null);
        }

        TwoNode(KVNode kvNode, BinaryNode left, BinaryNode right) {
            this(kvNode, null, left, right);
        }

        TwoNode(KVNode kvNode, TwoNode parent, BinaryNode left, BinaryNode right) {
            super(kvNode, left, right);
            this.parent = parent;
            this.parent = parent;
        }
    }

    /**
     * 3-节点.
     * 保存:
     * 1. 两对key-value.
     * 2. 三个节点. 左节点的key都小于该节点的key;中间节点的key介于该节点的左右节点的key之间;右节点的key都大于该节点的key
     */
    private static class ThreeNode extends TwoNode {

        /**
         * 另一个K-V节点，是两个节点中key最大的一个
         */
        KVNode rightKVNode;

        /**
         * 指向中间节点
         */
        TwoNode center;

        {
            flag = false;
        }

        public ThreeNode() {
            this(null, null);
        }

        ThreeNode(KVNode kvNode, KVNode rightKVNode) {
            this(kvNode, rightKVNode, null, null, null);
        }

        ThreeNode(KVNode kvNode, KVNode rightKVNode, TwoNode parent, BinaryNode left, BinaryNode right) {
            super(kvNode, parent, left, right);
            this.rightKVNode = rightKVNode;
        }

        /**
         * 两个节点建立中连接关系
         *
         * @param centerChild 要被设置成的中子节点
         */
        void buildCenterRelation(TwoNode centerChild) {
            if (centerChild != null) {
                centerChild.parent = this;
            }

            this.center = centerChild;
        }


        /**
         * 将oldNode父节点的相应指向变成当前节点，且将当前节点的的parent指向oldNode的父节点
         *
         * @param oldNode 要被替换的旧节点
         * @return 返回true, 替换成功.返回false,替换失败
         */
        void replace(TwoNode oldNode) {
            if (!super.replace(oldNode)) {
                ((ThreeNode) oldNode.parent).buildCenterRelation(this);
            }
        }
    }

    public TwoThreeTree() {
        this(null);
    }

    public TwoThreeTree(Comparator<K> comparator) {
        super(comparator);
    }

    /**
     * root为一个3-节点裂变成3个2-节点时，原来的center节点应该添加在哪个位置
     */
    private static enum CenterDirection {

        // root->left->right
        ROOT_LEFT_RIGHT,

        // root->right->left
        ROOT_RIGHT_LEFT,

        // 不添加
        NONE
    }

    @Override
    public Optional<V> put(K key, V value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            root = new TwoNode(new KVNode(key, value));
            root.parent = root;
            return Optional.empty();
        }

        // 找到节点并更新value
        KVNode kvNode = null;
        TwoNode node = find(root, key, kvNode);
        if (kvNode != null) {
            V oldValue = (V) kvNode.value;
            kvNode.value = value;
            return Optional.of(oldValue);
        }

        put0(node, new KVNode(key, value), CenterDirection.NONE);

        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        KVNode kvNode = null;
        find(root, key, kvNode);
        return Optional.ofNullable(kvNode == null ? null : (V) kvNode.value);
    }

    /**
     * 查找操作
     *
     * @param root   起始节点
     * @param key    键
     * @param kvNode 输出参数.如果不为null,用来标识找到的到底是哪一个k-v键值对
     * @return kvNode != null时,表示找到节点; 反之，表示找到插入时的父节点
     */
    private TwoNode find(final TwoNode root, final K key, KVNode kvNode) {
        TwoNode parent = root, node = root;
        int result = 0;

        while (node != null) {
            parent = node;
            if ((result = comparator.compare(key, (K) node.kvNode.key)) == 0) {
                kvNode = node.kvNode;
                return node;
            }
            if (result < 0) {
                node = (TwoNode) node.left;
                continue;
            }

            if (node.flag) {
                node = (TwoNode) node.right;
            } else {
                ThreeNode threeNode = (ThreeNode) node;
                if ((result = comparator.compare(key, (K) threeNode.rightKVNode.key)) == 0) {
                    kvNode = threeNode.rightKVNode;
                    return threeNode;
                }
                node = (result < 0 ? threeNode.center : (TwoNode) threeNode.right);
            }
        }

        return parent;
    }

    /**
     * 真正实现put操作
     *
     * @param node   最近的节点
     * @param kvNode kv键值对
     * @return
     */
    private void put0(TwoNode node, KVNode kvNode, CenterDirection centerDirection) {
        // 1. 检查是否为root节点
        if (checkRoot(node, kvNode, centerDirection)) {
            return;
        }

        KVNode max = null, middle = null, min = null;

        // 2. 此时node为 2-节点.直接变成3-节点
        if (node.flag) {
            KVNode[] nodes = {node.kvNode, kvNode};
            sort(nodes);
            min = nodes[0];
            max = nodes[1];
            ThreeNode threeNode = null;

            if (!node.getClass().equals(ThreeNode.class)) {
                threeNode = new ThreeNode(min, max);
                threeNode.replace(node);
                threeNode.buildLeftRelation(node.left);
                threeNode.buildRightRelation(node.right);
            } else {
                threeNode = (ThreeNode) node;
                threeNode.flag = false;
                threeNode.kvNode = min;
                threeNode.rightKVNode = max;
            }
            return;
        }

        // 3. 此时node(表现、真正)为3-节点
        ThreeNode threeNode = (ThreeNode) node;
        TwoNode parentIsTwoNode = (TwoNode) threeNode.parent;
        ThreeNode parentIsThreeNode = null;
        KVNode[] nodes = new KVNode[]{threeNode.rightKVNode, kvNode, threeNode.kvNode};
        sort(nodes);
        min = nodes[0];
        middle = nodes[1];
        max = nodes[2];

        // 3.1 此时parent表现为为2-节点，将parent变成3-节点
        if (parentIsTwoNode.flag) {
            if (!parentIsTwoNode.getClass().equals(ThreeNode.class)) {
                parentIsThreeNode = new ThreeNode();
                parentIsThreeNode.replace(parentIsTwoNode);
            } else {
                parentIsThreeNode = (ThreeNode) parentIsTwoNode;
                parentIsThreeNode.flag = false;
            }

            if (threeNode.equals(parentIsTwoNode.left)) {
                // 3.1.1 从parent左侧插入
                // 3.1.1.1 parent.排序kvnode(middle为kvNode)、新增center(max变成knNode,将threeNode的right变成自己的right)、重建子树
                parentIsThreeNode.rightKVNode = parentIsTwoNode.kvNode;
                parentIsThreeNode.kvNode = middle;
                parentIsThreeNode.buildLeftRelation(threeNode);
                parentIsThreeNode.buildCenterRelation(new TwoNode(max));
                parentIsThreeNode.buildRightRelation(parentIsTwoNode.right);
                // 3.1.1.2 parent.center. 重建子树
                parentIsThreeNode.center.buildRightRelation(threeNode.right);
                // 3.1.1.3 threeNode. min变成kvNode,重建子树，
                threeNode.kvNode = min;
                threeNode.right = threeNode.center;
            } else {
                // 3.1.2 从parent右侧插入
                // 3.1.2.1 parent. 排序kvnode(middle为rightKvNode)、新增center(min变成kvNode,将threeNode的left变成自己的)、重建子树
                parentIsThreeNode.kvNode = parentIsTwoNode.kvNode;
                parentIsThreeNode.rightKVNode = middle;
                parentIsThreeNode.buildLeftRelation(parentIsTwoNode.left);
                parentIsThreeNode.buildCenterRelation(new TwoNode(min));
                parentIsThreeNode.buildRightRelation(threeNode);
                // 3.1.2.3 parent.center. 重建子树
                parentIsThreeNode.center.buildLeftRelation(threeNode.left);
                // 3.1.2.2 threeNode. max变成kvNode,重建子树，并变成2-节点
                threeNode.kvNode = max;
                threeNode.left = threeNode.center;
            }

            // 3,1.2 使threeNode并变成2-节点
            threeNode.flag = true;
            threeNode.rightKVNode = null;
            threeNode.center = null;
            return;
        }

        // 3.2. 此时parent为3-节点，insertParent表示要向父节点插入的KVNode
        parentIsThreeNode = (ThreeNode) parentIsTwoNode;
        KVNode insertParent = null;
        //

        if (threeNode.equals(parentIsThreeNode.left)) {
            // 3.2.1 从左侧插入,
            threeNode.kvNode = min;
            threeNode.rightKVNode = middle;
            insertParent = max;
            centerDirection = CenterDirection.ROOT_RIGHT_LEFT;
        } else if (threeNode.equals(parentIsThreeNode.center)) {
            // 3.2.2 从中间插入
            threeNode.kvNode = min;
            threeNode.rightKVNode = middle;
            insertParent = max;
            centerDirection = CenterDirection.ROOT_LEFT_RIGHT;
        } else {
            // 3.2.3 从右侧插入
            threeNode.kvNode = middle;
            threeNode.rightKVNode = max;
            insertParent = min;
            centerDirection = CenterDirection.ROOT_LEFT_RIGHT;
        }

        put0((TwoNode) parentIsThreeNode.parent, insertParent, centerDirection);
    }

    /**
     * 检查node是否是root节点.
     *
     * @param node
     * @param kvNode
     * @return 返回true, 则将kvNode插入到2-3树中; 返回false,则什么也不做
     */
    private boolean checkRoot(TwoNode node, KVNode kvNode, CenterDirection centerDirection) {
        KVNode max = null, middle = null, min = null;
        if (node.equals(root)) {
            ThreeNode newRoot = null;

            if (root.flag) {
                // 1. 此时root表现为2-节点，将其变成3-节点
                KVNode[] nodes = {node.kvNode, kvNode};
                sort(nodes);
                min = nodes[0];
                max = nodes[1];

                // 1.1 此时root真正类型为2-节点,则新建一个3-节点(将原来root的值拷贝过去、设置两个KVNode)，并将修改root
                if (!root.getClass().equals(ThreeNode.class)) {
                    newRoot = new ThreeNode(min, max);
                    newRoot.buildLeftRelation(root.left);
                    newRoot.buildRightRelation(root.right);
                    root = newRoot;
                    root.parent = newRoot;
                    return true;
                }
                // 1.2 此时root真正类型为3-节点(设置两个KVNode,并修改flag)
                newRoot = (ThreeNode) root;
                newRoot.flag = false;
                newRoot.kvNode = min;
                newRoot.rightKVNode = max;
                return true;
            }

            // 2. 此时root(表现、真正)类型为3-节点,分裂成3个2-节点
            newRoot = (ThreeNode) root;
            KVNode[] nodes = {newRoot.kvNode, newRoot.rightKVNode, kvNode};
            sort(nodes);
            min = nodes[0];
            middle = nodes[1];
            max = nodes[2];
            // 2.1 新建左节点
            TwoNode left = new TwoNode(min, root, newRoot.left, newRoot.center);
            left.buildLeftRelation(newRoot.left);
            newRoot.buildLeftRelation(left);
            // 2.2 新建右节点
            TwoNode right = new TwoNode(max, root, null, newRoot.right);
            right.buildRightRelation(newRoot.right);
            newRoot.buildRightRelation(right);
            // 2.3 原来的中间节点
            if (CenterDirection.ROOT_LEFT_RIGHT.equals(centerDirection)) {
                left.buildRightRelation(newRoot.center);
            } else if (CenterDirection.NONE.ROOT_RIGHT_LEFT.equals(centerDirection)) {
                right.buildLeftRelation(newRoot.center);
            }
            // 2.4 根节点,并表现为2-节点
            newRoot.kvNode = middle;
            newRoot.flag = true;
            newRoot.rightKVNode = null;
            newRoot.center = null;
            return true;
        }
        return false;
    }
}
