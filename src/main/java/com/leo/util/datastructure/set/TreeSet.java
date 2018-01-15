package com.leo.util.datastructure.set;

import com.leo.util.datastructure.Collection;
import com.leo.util.datastructure.Set;

import java.util.Iterator;
import java.util.Optional;

/**
 * 通过二叉树保证Set中元素的唯一性
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/5
 * @since 1.0
 */
public class TreeSet<E> extends AbstractSet<E> implements Set<E> {
    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean addAll(Collection<E> collection) {
        return false;
    }

    @Override
    public Optional<E> remove(E e) {
        return null;
    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
