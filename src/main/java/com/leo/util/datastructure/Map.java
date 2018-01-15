package com.leo.util.datastructure;

import java.util.Optional;

/**
 * Map的顶层接口
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/19
 * @since 1.0
 */
public interface Map<K, V> {


    interface Entry<K, V> {

        /**
         * 返回键
         *
         * @return
         */
        Optional<K> getKey();

        /**
         * 返回值
         *
         * @return
         */
        Optional<V> getValue();

        /**
         * 设置value
         *
         * @param value
         */
        void setValue(V value);
    }

    /**
     * 放入键值对
     *
     * @param key   键
     * @param value 值
     * @return 返回key所对应旧的value
     */
    Optional<V> put(K key, V value);

    /**
     * 获取key所对应的value
     *
     * @param Key 键
     * @return 返回key所对应的value
     */
    Optional<V> get(K Key);

    /**
     * 根据key移除键值对
     *
     * @param key 键
     * @return 返回key所对应的value
     */
    Optional<V> remove(K key);

    /**
     * 移除特定的键值对
     *
     * @param key   键
     * @param value 值
     * @return 返回true, 移除成功;反之,失败
     */
    boolean remove(K key, V value);

    /**
     * 根据Entry移除
     *
     * @param entry
     * @return 返回true, 移除成功;反之,失败
     */
    boolean remove(Entry<K, V> entry);

    /**
     * 检测map中是否包含某个key
     *
     * @param key 待检测的键
     * @return 返回true, 包含key;反之,不包含
     */
    boolean containsKey(K key);

    /**
     * 检测map中是否包含某个entry
     *
     * @param entry 待检测的entry
     * @return 返回true, 包含entry;反正,不包含
     */
    boolean containsEntry(K key, V value);

    /**
     * 检测map中是否包含某个entry
     *
     * @param entry 待检测的entry
     * @return 返回true, 包含entry;反之,不包含
     */
    boolean containsEntry(Entry<K, V> entry);

    /**
     * 获取键的Set集合，不保证顺序
     *
     * @return
     */
    Set<K> keySet();

    /**
     * 获取键值对的Set集合，不保证顺序
     *
     * @return
     */
    Set<Entry<K, V>> entrySet();

    /**
     * map的长度
     *
     * @return
     */
    int size();

    /**
     * map是否为空
     *
     * @return true为空;false不为空
     */
    boolean isEmpty();

    /**
     * 清空map
     */
    void clear();


}
