package com.leo.util.datastructure;

import java.util.Optional;

/**
 * 单列容器接口
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/21
 * @since 1.0
 */
public interface Collection<E> extends Iterable<E> {

    /**
     * 向容器中添加一个元素
     *
     * @param e 待添加的元素
     * @return 返回true, 表示添加成功;返回false,表示添加失败
     *
     * @throws UnsupportedOperationException 当前容器不支持该操作时抛出异常
     */
    default boolean add(E e) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * 将另一个容器中的元素添加到本容器
     *
     * @param collection 待添加的容器
     * @return 返回true, 表示添加成功;返回false,表示添加失败
     * @throws UnsupportedOperationException 当前容器不支持该操作时抛出异常
     * @throws NullPointerException 当collection为空时抛出异常
     */
    default boolean addAll(Collection<E> collection) throws UnsupportedOperationException{
        if(collection == null){
            throw new NullPointerException();
        }
        collection.forEach(e -> add(e));
        return true;
    }

    /**
     * 从容器中移除指定的元素
     *
     * @param e 被移除的元素
     * @return 被移除的元素
     * @throws UnsupportedOperationException 当前容器不支持该操作时抛出异常
     */
    default Optional<E> remove(E e) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * 容器是否包含元素
     *
     * @param e 待查找的元素
     * @return
     */
    boolean contains(E e);

    /**
     * 获取长度
     *
     * @return 容器的长度
     */
    int size();

    /**
     * 容器是否为空
     *
     * @return 返回true，表示容器为空;返回false表示容器不为空
     */
    boolean isEmpty();

    /**
     * 清空容器
     * @throws UnsupportedOperationException
     */
    default void clear() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
