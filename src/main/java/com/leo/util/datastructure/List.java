package com.leo.util.datastructure;

/**
 * List的顶层接口<br/>
 * 注意:容器不保存null
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/18
 * @since 1.0
 */
public interface List<E> extends Collection<E> {

    /**
     * 根据索引增加元素
     *
     * @param index index在[0,size)范围内
     * @param e     当元素为null时，不保存
     * @return 返回true, 表示添加成功;返回false,表示添加失败
     * @throws IndexOutOfBoundsException 当index不在[0,size)范围时抛出异常
     * @throws NullPointerException      当e为null时抛出异常
     */
    boolean add(int index, E e) throws IndexOutOfBoundsException, NullPointerException;

    /**
     * 根据索引删除元素
     *
     * @param index index在[0,size)范围内
     * @return 返回被移除的元素
     * @throws IndexOutOfBoundsException 当index不在[0,size)范围时抛出异常
     */
    E remove(int index) throws IndexOutOfBoundsException, NullPointerException;

    /**
     * 根据索引设置元素，并返回旧指
     *
     * @param index index在[0,size)范围内
     * @param e     当元素为null时，不设置
     * @return 返回该索引处原来的值
     * @throws IndexOutOfBoundsException 当index不在[0,size)范围时抛出异常
     * @throws NullPointerException      当e为null时抛出异常
     */
    E set(int index, E e) throws IndexOutOfBoundsException, NullPointerException;

    /**
     * 根据索引查找元素
     *
     * @param index index在[0,size)范围内
     * @return 返回索引到的元素
     * @throws IndexOutOfBoundsException 当index不在[0,size)范围时抛出异常
     */
    E get(int index) throws IndexOutOfBoundsException;
}
