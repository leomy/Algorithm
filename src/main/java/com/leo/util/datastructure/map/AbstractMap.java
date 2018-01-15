package com.leo.util.datastructure.map;

import com.leo.util.datastructure.Map;

/**
 * 所有实现了Map接口的基类
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/5
 * @since 1.0
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    /**
     * 保存实际的键值对个数.
     */
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(Entry<K, V> entry) {
        return entry == null ? true : remove(entry.getKey().orElse(null), entry.getValue().orElse(null));
    }

    @Override
    public boolean containsEntry(Entry<K, V> entry){
        return entry == null ? true : containsEntry(entry.getKey().orElse(null), entry.getValue().orElse(null));
    }

}
