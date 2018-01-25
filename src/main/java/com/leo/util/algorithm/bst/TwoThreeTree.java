package com.leo.util.algorithm.bst;

import java.util.Comparator;

/**
 * 二三树的实现
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/19
 * @since 1.0
 */
public class TwoThreeTree<T> extends AbstractBinarySearchTree<T> implements BinarySearchTree<T> {

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
     * 1. 一个value
     * 2. 两个节点. 左节点的value都小于该节点的value;右节点的value都大于该节点的value
     */
    private class TwoNode extends BinaryNode {

        NodeFlag nodeFlag;

        {
            nodeFlag = NodeFlag.TWO;
        }

        TwoNode(T value) {
            this(value, null, null, null);
        }

        TwoNode(T value, TwoNode parent, BinaryNode left, BinaryNode right) {
            super(value, left, right);
            this.parent = parent;
            this.parent = parent;
        }
    }

    /**
     * 3-节点.
     * 保存:
     * 1. 两个value
     * 2. 三个节点. 左节点的value都小于该节点的value;中间节点的value介于该节点的左右节点的value之间;右节点的value都大于该节点的value
     */
    private class ThreeNode extends TwoNode {

        /**
         * 另一个K-V节点，是两个节点中key最大的一个
         */
        T maxValue;

        /**
         * 指向中间节点
         */
        TwoNode center;

        {
            nodeFlag = NodeFlag.THREE;
        }

        ThreeNode(T value, T maxValue) {
            this(value, maxValue, null, null, null);
        }

        ThreeNode(T value, T maxValue, TwoNode parent, BinaryNode left, BinaryNode right) {
            super(value, parent, left, right);
            this.maxValue = maxValue;
        }

        /**
         * 两个节点建立中连接关系
         *
         * @param centerChild 要被设置成的中子节点
         * @return 返回当前节点, 便于链式调用
         */

        ThreeNode buildCenterRelation(TwoNode centerChild) {
            if (centerChild != null) {
                centerChild.parent = this;
            }

            this.center = centerChild;
            return this;
        }
    }

    /**
     * 4-节点.仅做中间状态用
     */
    private class FourNode extends TwoNode {

        /**
         * 保存中间大小的KVNode
         */
        T middleValue;

        /**
         * 保存最大的KVNode
         */
        T maxValue;

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

        FourNode(T value, T middleValue, T maxValue,
                 TwoNode parent,
                 BinaryNode left, TwoNode leftCenter, TwoNode rightCenter, BinaryNode right) {
            super(value, parent, left, right);
            this.middleValue = middleValue;
            this.maxValue = maxValue;
            this.leftCenter = leftCenter;
            this.rightCenter = rightCenter;
        }

        /**
         * 两个节点建立左中连接关系
         *
         * @param leftCenterChild 要被设置成的中子节点
         * @return 返回当前节点, 便于链式调用
         */


        FourNode buildLeftCenterRelation(TwoNode leftCenterChild) {
            if (leftCenterChild != null) {
                leftCenterChild.parent = this;
            }
            this.leftCenter = leftCenterChild;
            return this;
        }

        /**
         * 两个节点建立左中连接关系
         *
         * @param rightCenterChild 要被设置成的中子节点
         * @return 返回当前节点, 便于链式调用
         */
        FourNode buildRightCenterRelation(TwoNode rightCenterChild) {
            if (rightCenterChild != null) {
                rightCenterChild.parent = this;
            }

            this.rightCenter = rightCenterChild;
            return this;
        }
    }


    public TwoThreeTree() {
        this(null);
    }

    public TwoThreeTree(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void put(T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        // 1. 此时root节点为null,新建、设置并返回
        if (root == null) {
            root = new TwoNode(value);
            root.parent = root;
            hight = 1;
            return;
        }

        Object[] objects = find(root, value);

        // 2. 此时节点处于树中,返回
        boolean contins = (boolean) objects[0];
        if (contins) {
            return;
        }

        TwoNode node = (TwoNode) objects[1];

        // 3. 此时树高为1时,root变成3-节点或变成4-节点(分裂)并返回
        if (hight < 2) {
            if (node.nodeFlag.equals(NodeFlag.TWO)) {
                // 3.1 表示当前节点是个2-节点,直接变成一个三节点
                Object[] values = {value, node.value};
                sort(values);
                ThreeNode newRoot = new ThreeNode((T) values[0], (T) values[1]);
                root = newRoot;
                root.parent = newRoot;
            } else {
                // 3.2 表示当前节点为3节点,裂变成3个2-节点,树高加1
                Object[] values = {root.value, value, ((ThreeNode) root).maxValue};
                sort(values);
                TwoNode newRoot = new TwoNode((T) values[1]);
                newRoot.buildLeftRelation(new TwoNode((T) values[0]))
                        .buildRightRelation(new TwoNode((T) values[2]));
                root = newRoot;
                root.parent = newRoot;
                hight++;
            }
            return;
        }

        // 4. 此时node为 2-节点.直接变成3-节点,返回
        if (node.nodeFlag.equals(NodeFlag.TWO)) {
            Object[] values = {value, node.value};
            sort(values);
            if (!node.getClass().equals(ThreeNode.class)) {
                ThreeNode threeNode = new ThreeNode((T) values[0], (T) values[1]);
                replace(threeNode, node);
                threeNode.buildLeftRelation(node.left)
                        .buildRightRelation(node.right);
            } else {
                node.nodeFlag = NodeFlag.THREE;
                ((ThreeNode) node).value = values[0];
                ((ThreeNode) node).maxValue = (T) values[1];
            }
            return;
        }

        // 5. 此时node为3-节点,变成4-节点并处理
        ThreeNode threeNode = (ThreeNode) node;
        Object[] values = {value, threeNode.value, threeNode.maxValue};
        sort(values);
        FourNode fourNode = new FourNode((T) values[0], (T) values[1], (T) values[2],
                (TwoNode) node.parent,
                threeNode.left, threeNode.center, null, threeNode.right);
        replace(fourNode, node);
        decomposeFourNode(fourNode);
    }

    /**
     * 在以start为起始节点的树中查找value
     *
     * @param start 起始节点
     * @param value 要查找的值
     * @return 1. index = 0 : 表示是否找到value;true表示找到,false表示未找到,影响index = 1 <br/>
     * 2. index = 1 当index = false时,表示找到和key最相近的node;当index = true时,该值无效 <br/>
     */
    @Override
    protected Object[] find(BinaryNode start, T value) {
        TwoNode node = (TwoNode) start;
        Object[] objects = new Object[2];
        TwoNode parent = node;
        int result;

        while (node != null) {
            parent = node;
            if ((result = comparator.compare(value, (T) node.value)) == 0) {
                objects[0] = true;
                return objects;
            } else if (result < 0) {
                node = (TwoNode) node.left;
            } else if (node.nodeFlag.equals(NodeFlag.TWO)) {
                node = (TwoNode) node.right;
            } else {
                ThreeNode threeNode = (ThreeNode) node;
                if ((result = comparator.compare(value, threeNode.maxValue)) == 0) {
                    objects[0] = true;
                    return objects;
                }
                node = (result < 0 ? threeNode.center : (TwoNode) threeNode.right);
            }
        }

        objects[0] = false;
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
            root = new TwoNode(fourNode.middleValue);
            root.parent = root;

            root.buildLeftRelation(new TwoNode((T) fourNode.value))
                    .buildRightRelation(new TwoNode(fourNode.maxValue));
            root.left.buildLeftRelation(fourNode.left)
                    .buildRightRelation(fourNode.leftCenter);
            root.right.buildLeftRelation(fourNode.rightCenter)
                    .buildRightRelation(fourNode.right);

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
                parentIsThreeNode.maxValue = (T) parent.value;
                parentIsThreeNode.value = fourNode.middleValue;

                parentIsThreeNode.buildCenterRelation(new TwoNode(fourNode.maxValue))
                        .buildLeftRelation(new TwoNode((T) fourNode.value))
                        .buildRightRelation(parent.right);

                parentIsThreeNode.left.buildLeftRelation(fourNode.left)
                        .buildRightRelation(fourNode.leftCenter);
                parentIsThreeNode.center.buildLeftRelation(fourNode.rightCenter)
                        .buildRightRelation(fourNode.right);
            } else {
                // 2.2 从parent右侧插入.广度+先序设置
                parentIsThreeNode.value = parent.value;
                parentIsThreeNode.maxValue = fourNode.middleValue;

                parentIsThreeNode.buildCenterRelation(new TwoNode((T) fourNode.value))
                        .buildLeftRelation(parent.left)
                        .buildRightRelation(new TwoNode(fourNode.maxValue));

                parentIsThreeNode.center.buildLeftRelation(fourNode.left)
                        .buildRightRelation(fourNode.leftCenter);
                parentIsThreeNode.right.buildLeftRelation(fourNode.rightCenter)
                        .buildRightRelation(fourNode.right);
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

        TwoNode left = new TwoNode((T) fourNode.value);
        left.buildLeftRelation(fourNode.left);
        left.buildRightRelation(fourNode.leftCenter);
        TwoNode right = new TwoNode(fourNode.maxValue);
        right.buildLeftRelation(fourNode.rightCenter);
        right.buildRightRelation(fourNode.right);
        if (direction.equals(Direction.LEFT)) {
            // 3.1.1 child在parent左子树上
            fourNode.value = fourNode.middleValue;
            fourNode.middleValue = (T) parentIsThreeNode.value;
            fourNode.maxValue = parentIsThreeNode.maxValue;
            fourNode.buildLeftCenterRelation(right)
                    .buildRightCenterRelation(parentIsThreeNode.center)
                    .buildLeftRelation(left)
                    .buildRightRelation(parentIsThreeNode.right);
        } else if (direction.equals(Direction.CENTER)) {
            // 3.1.2 child在parent中间子树上
            fourNode.value = fourNode.middleValue;
            fourNode.middleValue = (T) parentIsThreeNode.value;
            fourNode.maxValue = parentIsThreeNode.maxValue;
            fourNode.buildLeftCenterRelation(left)
                    .buildRightCenterRelation(right)
                    .buildLeftRelation(parentIsThreeNode.left)
                    .buildRightRelation(parentIsThreeNode.right);
        } else {
            // 3.1.3 child在parent右子树上
            fourNode.maxValue = fourNode.middleValue;
            fourNode.value = parentIsThreeNode.value;
            fourNode.middleValue = parentIsThreeNode.maxValue;
            fourNode.buildLeftCenterRelation(parentIsThreeNode.center)
                    .buildRightCenterRelation(left)
                    .buildLeftRelation(parentIsThreeNode.left)
                    .buildRightRelation(right);
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
