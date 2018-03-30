package com.leo.util.algorithm.btree;

import com.leo.util.algorithm.Arrays;

import java.util.Comparator;

/**
 * 所有平衡二叉树接口的基类
 * Note: 不保存null
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/19
 * @since 1.0
 */
public abstract class AbstractBalancedBinaryTree<T> implements BalancedBinaryTree<T> {
    /**
     * 保存树高
     */
    protected int hight;

    /**
     * 比较器,用来比较大小
     */
    protected Comparator<T> comparator;

    public AbstractBalancedBinaryTree(Comparator<T> comparator) {
        this.comparator = (comparator == null ? generateCompator() : comparator);
    }

    @Override
    public boolean contains(T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        Object[] objects = find(getRoot(), value);
        return (boolean) objects[0];
    }

    @Override
    public int getHight() {
        return hight;
    }

    /**
     * 生成比较器
     *
     * @return
     */
    private static final Comparator generateCompator() {
        return (value1, value2) -> ((Comparable) value1).compareTo(value2);
    }

    /**
     * 在以start为起始节点的树中查找value
     * Note: 如果子类重新定义的节点新增了其他子树,需要重写该方法
     *
     * @param start 起始节点
     * @param value 要查找的值
     * @return 1. index = 0 : 表示是否找到value;true表示找到,false表示未找到,影响index = 2,index = 3  <br/>
     * 2. index = 1 : 当index = 0的值为false时,表示找的最近的节点;当index = 0为true时,其值无效 <br/>
     * 3. index = 2 : 当index = 0的值为fale时,表示参数value与最近节点的value与的大小关系.大于0，表示参数value > 最近节点的value,反之，参数value < 最近节点的value;当index = 0为true时,其值无效 <br/>
     */
    protected Object[] find(BinaryNode start, T value) {
        Object[] objects = new Object[2];
        BinaryNode parent = start;
        int result = 0;

        while (start != null) {
            parent = start;
            if ((result = comparator.compare(value, (T) start.value)) == 0) {
                objects[0] = true;
                return objects;
            }

            start = result < 0 ? start.left : start.right;
        }

        objects[0] = false;
        objects[1] = parent;
        return objects;
    }

    /**
     * 将保存value的数组排序从小到大排序
     *
     * @return 返回从小到大的数组
     * @throws IllegalArgumentException 当values = null时抛出异常
     */
    protected final void sort(Object[] values) throws IllegalArgumentException {
        Arrays.shellSort(values, (value1, value2) -> comparator.compare((T) value1, (T) value2));
    }

    /**
     * 获取root节点
     *
     * @return
     */
    protected abstract BinaryNode getRoot();
}
