package com.leo.util.datastructure.set;

import com.leo.util.datastructure.Collection;
import com.leo.util.datastructure.Set;
import com.leo.util.datastructure.map.HashMap;

import java.util.Iterator;
import java.util.Optional;

/**
 * Hash算法实现的Set，大部分情况下直接调用HashMap的方法
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/5
 * @since 1.0
 */
public class HashSet<E> extends AbstractSet<E> implements Set<E> {

    /**
     * 通过HashMap的键保证Set中元素的唯一性
     */
    private HashMap<E, Object> map;

    /**
     * map中所有的value都是该常量
     */
    private static final Object VALUE = new Object();

    public HashSet() {
        map = new HashMap(16, 1);
    }

    @Override
    public boolean add(E e) {
        return map.put(e, VALUE).orElse(null) != null;
    }

    @Override
    public boolean addAll(Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException();
        }
        collection.forEach(element -> map.put(element, VALUE));
        return true;
    }

    @Override
    public Optional<E> remove(E e) {
        map.remove(e);
        return Optional.ofNullable(e);
    }

    @Override
    public boolean contains(E e) {
        return map.containsKey(e);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
}
