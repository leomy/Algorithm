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
     * 标识时哪种节点
     */
    private enum NodeFlag {

        /**
         * 2-节点
         */
        TWO,

        /**
         * 3-节点
         */
        THREE,

        /**
         * 4-节点
         */
        FOUR,
    }

    /**
     * 记录两个节点的直接关系
     */
    private enum Direction {

        /**
         * child在parent左边
         */
        LEFT,

        /**
         * child在parent中间
         */
        CENTER,

        /**
         * child在parent右边
         */
        RIGHT,

        /**
         * 两个节点没有关系
         */
        NONE,
    }

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
        NodeFlag nodeFlag;

        {
            nodeFlag = NodeFlag.TWO;
        }

        TwoNode(KVNode kvNode) {
            this(kvNode, null, null, null);
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
        KVNode maxKVNode;

        /**
         * 指向中间节点
         */
        TwoNode center;

        {
            nodeFlag = NodeFlag.THREE;
        }

        ThreeNode(KVNode kvNode, KVNode maxKVNode) {
            this(kvNode, maxKVNode, null, null, null);
        }

        ThreeNode(KVNode kvNode, KVNode maxKVNode, TwoNode parent, BinaryNode left, BinaryNode right) {
            super(kvNode, parent, left, right);
            this.maxKVNode = maxKVNode;
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
    }

    /**
     * 4-节点.仅做中间状态用
     */
    private static class FourNode extends TwoNode {

        /**
         * 保存中间大小的KVNode
         */
        KVNode middleKVNode;

        /**
         * 保存最大的KVNode
         */
        KVNode maxKVNode;

        /**
         * 保存left center
         */
        TwoNode leftCenter;

        /**
         * 保存right center
         */
        TwoNode rightCenter;

        {
            nodeFlag = NodeFlag.FOUR;
        }

        FourNode(KVNode kvNode, KVNode middleKVNode, KVNode maxKVNode,
                 TwoNode parent,
                 BinaryNode left, TwoNode leftCenter, TwoNode rightCenter, BinaryNode right) {
            super(kvNode, parent, left, right);
            this.middleKVNode = middleKVNode;
            this.maxKVNode = maxKVNode;
            this.leftCenter = leftCenter;
            this.rightCenter = rightCenter;
        }

        /**
         * 两个节点建立左中连接关系
         *
         * @param leftCenterChild 要被设置成的中子节点
         */
        void buildLeftCenterRelation(TwoNode leftCenterChild) {
            if (leftCenterChild != null) {
                leftCenterChild.parent = this;
            }
            this.leftCenter = leftCenterChild;
        }

        /**
         * 两个节点建立左中连接关系
         *
         * @param rightCenterChild 要被设置成的中子节点
         */
        void buildRightCenterRelation(TwoNode rightCenterChild) {
            if (rightCenterChild != null) {
                rightCenterChild.parent = this;
            }

            this.rightCenter = rightCenterChild;
        }
    }


    public TwoThreeTree() {
        this(null);
    }

    public TwoThreeTree(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    public Optional<V> put(K key, V value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // 1. 此时root节点为null,新建、设置并返回
        if (root == null) {
            root = new TwoNode(new KVNode(key, value));
            root.parent = root;
            hight = 1;
            return Optional.empty();
        }

        // 2. 此时节点处于树中,更新value并返回旧value
        Object[] objects = find((TwoNode) root, key);
        KVNode kvNode = (KVNode) objects[0];
        if (kvNode != null) {
            V oldValue = (V) kvNode.value;
            kvNode.value = value;
            return Optional.of(oldValue);
        }

        TwoNode node = (TwoNode) objects[1];
        kvNode = new KVNode(key, value);

        // 3. 此时树高为1时,root变成3-节点或变成4-节点(分裂)并返回
        if (hight < 2) {
            if (node.nodeFlag.equals(NodeFlag.TWO)) {
                // 3.1 表示当前节点是个2-节点,直接变成一个三节点
                KVNode[] nodes = sort(new KVNode[]{kvNode, node.kvNode});
                ThreeNode newRoot = new ThreeNode(nodes[0], nodes[1]);
                root = newRoot;
                root.parent = newRoot;
            } else {
                // 3.2 表示当前节点为3节点,裂变成3个2-节点,树高加1
                KVNode[] nodes = sort(new KVNode[]{root.kvNode, kvNode, ((ThreeNode) root).maxKVNode});
                TwoNode newRoot = new TwoNode(nodes[1]);
                newRoot.buildLeftRelation(new TwoNode(nodes[0]));
                newRoot.buildRightRelation(new TwoNode(nodes[2]));
                root = newRoot;
                root.parent = newRoot;
                hight++;
            }
            return Optional.empty();
        }

        // 4. 此时node为 2-节点.直接变成3-节点,返回
        if (node.nodeFlag.equals(NodeFlag.TWO)) {
            KVNode[] nodes = sort(new KVNode[]{kvNode, node.kvNode});
            if (!node.getClass().equals(ThreeNode.class)) {
                ThreeNode threeNode = new ThreeNode(nodes[0], nodes[1]);
                replace(threeNode, node);
                threeNode.buildLeftRelation(node.left);
                threeNode.buildRightRelation(node.right);
            } else {
                node.nodeFlag = NodeFlag.THREE;
                ((ThreeNode) node).kvNode = nodes[0];
                ((ThreeNode) node).maxKVNode = nodes[1];
            }
            return Optional.empty();
        }

        // 5. 此时node为3-节点,变成4-节点并处理
        ThreeNode threeNode = (ThreeNode) node;
        KVNode[] nodes = sort(new KVNode[]{kvNode, threeNode.kvNode, threeNode.maxKVNode});
        FourNode fourNode = new FourNode(nodes[0], nodes[1], nodes[2],
                (TwoNode) node.parent,
                threeNode.left, threeNode.center, null, threeNode.right);
        replace(fourNode, node);
        decomposeFourNode(fourNode);
        return Optional.empty();

    }

    @Override
    public Optional<V> get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Object[] objects = find((TwoNode) root, key);
        return Optional.ofNullable(objects[0] == null ? null : (V) ((KVNode) objects[0]).value);
    }

    /**
     * 查找操作
     *
     * @param node 起始节点,没有做空处理
     * @param key  键
     * @return 如果返回值不为null, 则返回的数组中，其索引对应的值表示为: <br/>
     * 1. index = 0 : 如果不为null,用来标识找到的到底是哪一个k-v键值.影响后面的索引对应的值 <br/>
     * 2. index = 1 当0所对应的值为null时,表示找到和key最相近的node; 反之,表示找到的了与key相等的node <br/>
     */
    private Object[] find(TwoNode node, final K key) {
        Object[] objects = new Object[2];
        TwoNode parent = node;

        while (node != null) {
            int result;
            parent = node;
            if ((result = comparator.compare(key, (K) node.kvNode.key)) == 0) {
                objects[0] = node.kvNode;
                objects[1] = node;
                break;
            } else if (result < 0) {
                node = (TwoNode) node.left;
                continue;
            } else if (node.nodeFlag.equals(NodeFlag.TWO)) {
                node = (TwoNode) node.right;
            } else {
                ThreeNode threeNode = (ThreeNode) node;
                if ((result = comparator.compare(key, (K) threeNode.maxKVNode.key)) == 0) {
                    objects[0] = threeNode.maxKVNode;
                    objects[1] = threeNode;
                    break;
                }
                node = (result < 0 ? threeNode.center : (TwoNode) threeNode.right);
            }
        }

        objects[1] = parent;
        return objects;
    }

    /**
     * 分解4-节点
     *
     * @param fourNode 要被分解的4-节点
     */
    private void decomposeFourNode(FourNode fourNode) {
        // 1 此时root为4-节点,变为3个2-节点,树高加1,返回
        if (root.equals(fourNode)) {
            root = new TwoNode(fourNode.middleKVNode);
            root.parent = root;

            root.buildLeftRelation(new TwoNode(fourNode.kvNode));
            root.buildRightRelation(new TwoNode(fourNode.maxKVNode));

            root.left.buildLeftRelation(fourNode.left);
            root.left.buildRightRelation(fourNode.leftCenter);
            root.right.buildLeftRelation(fourNode.rightCenter);
            root.right.buildRightRelation(fourNode.right);

            hight++;
            return;
        }

        TwoNode parent = (TwoNode) fourNode.parent;
        Direction direction = Direction.NONE;
        if (fourNode.equals(parent.left)) {
            direction = Direction.LEFT;
        } else if (fourNode.equals(parent.right)) {
            direction = Direction.RIGHT;
        }

        // 2. 此时parent是2-节点,直接变成3-节点,返回
        ThreeNode parentIsThreeNode;
        if (parent.nodeFlag.equals(NodeFlag.TWO)) {
            if (!parent.getClass().equals(ThreeNode.class)) {
                parentIsThreeNode = new ThreeNode(null, null);
                replace(parentIsThreeNode, parent);
            } else {
                parentIsThreeNode = (ThreeNode) parent;
                parent.nodeFlag = NodeFlag.THREE;
            }

            if (direction.equals(Direction.LEFT)) {
                // 2.1 从parent左侧插入,广度+先序设置
                parentIsThreeNode.maxKVNode = parent.kvNode;
                parentIsThreeNode.kvNode = fourNode.middleKVNode;

                parentIsThreeNode.buildLeftRelation(new TwoNode(fourNode.kvNode));
                parentIsThreeNode.buildCenterRelation(new TwoNode(fourNode.maxKVNode));
                parentIsThreeNode.buildRightRelation(parent.right);

                parentIsThreeNode.left.buildLeftRelation(fourNode.left);
                parentIsThreeNode.left.buildRightRelation(fourNode.leftCenter);
                parentIsThreeNode.center.buildLeftRelation(fourNode.rightCenter);
                parentIsThreeNode.center.buildRightRelation(fourNode.right);
            } else {
                // 2.2 从parent右侧插入.广度+先序设置
                parentIsThreeNode.kvNode = parent.kvNode;
                parentIsThreeNode.maxKVNode = fourNode.middleKVNode;

                parentIsThreeNode.buildLeftRelation(parent.left);
                parentIsThreeNode.buildCenterRelation(new TwoNode(fourNode.kvNode));
                parentIsThreeNode.buildRightRelation(new TwoNode(fourNode.maxKVNode));

                parentIsThreeNode.center.buildLeftRelation(fourNode.left);
                parentIsThreeNode.center.buildRightRelation(fourNode.leftCenter);
                parentIsThreeNode.right.buildLeftRelation(fourNode.rightCenter);
                parentIsThreeNode.right.buildRightRelation(fourNode.right);
            }
            return;
        }

        // 3 此时parent为3-节点
        parentIsThreeNode = (ThreeNode) parent;
        if (direction.equals(Direction.NONE)) {
            direction = Direction.CENTER;
        }

        // 3.1 将parent替换成4-节点,并递归处理parent
        replace(fourNode, parent);

        TwoNode left = new TwoNode(fourNode.kvNode);
        left.buildLeftRelation(fourNode.left);
        left.buildRightRelation(fourNode.leftCenter);
        TwoNode right = new TwoNode(fourNode.maxKVNode);
        right.buildLeftRelation(fourNode.rightCenter);
        right.buildRightRelation(fourNode.right);
        if (direction.equals(Direction.LEFT)) {
            // 3.1.1 child在parent左子树上
            fourNode.kvNode = fourNode.middleKVNode;
            fourNode.middleKVNode = parentIsThreeNode.kvNode;
            fourNode.maxKVNode = parentIsThreeNode.maxKVNode;
            fourNode.buildLeftRelation(left);
            fourNode.buildLeftCenterRelation(right);
            fourNode.buildRightCenterRelation(parentIsThreeNode.center);
            fourNode.buildRightRelation(parentIsThreeNode.right);
        } else if (direction.equals(Direction.CENTER)) {
            // 3.1.2 child在parent中间子树上
            fourNode.kvNode = fourNode.middleKVNode;
            fourNode.middleKVNode = parentIsThreeNode.kvNode;
            fourNode.maxKVNode = parentIsThreeNode.maxKVNode;
            fourNode.buildLeftRelation(parentIsThreeNode.left);
            fourNode.buildLeftCenterRelation(left);
            fourNode.buildRightCenterRelation(right);
            fourNode.buildRightRelation(parentIsThreeNode.right);
        } else {
            // 3.1.3 child在parent右子树上
            fourNode.maxKVNode = fourNode.middleKVNode;
            fourNode.kvNode = parentIsThreeNode.kvNode;
            fourNode.middleKVNode = parentIsThreeNode.maxKVNode;
            fourNode.buildLeftRelation(parentIsThreeNode.left);
            fourNode.buildLeftCenterRelation(parentIsThreeNode.center);
            fourNode.buildRightCenterRelation(left);
            fourNode.buildRightRelation(right);
        }
        decomposeFourNode(fourNode);
    }

    /**
     * 将oldNode父节点的相应指向(left,right,center)变成newNode，且将newNode节点的的parent指向oldNode的父节点
     *
     * @param newNode 要替换的新节点
     * @param oldNode 要被替换的旧节点
     */
    private void replace(TwoNode newNode, TwoNode oldNode) {
        if (!super.replace(newNode, oldNode)) {
            ((ThreeNode) oldNode.parent).buildCenterRelation(newNode);
        }
    }
}
