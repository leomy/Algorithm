package com.leo.util.datastructure.map;

import com.leo.util.datastructure.Map;
import com.leo.util.datastructure.Set;
import com.leo.util.datastructure.set.AbstractSet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

/**
 * HashMap的实现 <br/>
 * Note: 实现依赖于key的hashCode()、equest()方法，建议重写这两个方法
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/22
 * @since 1.0
 */
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    /**
     * 当前的容量,保证为2^n (n >= 4).
     * Note: 为了每次index()时，取模运算(%)能更快、分配得更均匀
     */
    private int capacity;

    /**
     * 负载因子.范围在(0,1]之间
     */
    private float loadFactor;

    /**
     * 扩容时的临界值
     * threshold = capacity * loadFactor
     */
    private int threshold;

    /**
     * 通过拉链法保存实际的key-value键值对
     */
    private Object[] table;

    /**
     * 默认的初始化容量
     */
    private static final int DEFAULT_INIT_CAPACITY = 1 << 4;

    /**
     * 最大容量
     */
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;

    /**
     * 默认的负载因子
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 保存2的指数,min = 16, max = (1<<32)
     */
    private static int POWER[];

    static {
        POWER = new int[28];
        for (int i = 0, length = POWER.length - 1; i < length; i++) {
            POWER[i] = (DEFAULT_INIT_CAPACITY << i);
        }
        POWER[POWER.length - 1] = Integer.MAX_VALUE;
    }

    /**
     * 定义类来保存实际的kv键值对
     */
    private class Node extends AbstractEntry {

        /**
         * 指向下一个Node
         */
        Node next;

    }

    /**
     * key的Set集合
     *
     * @param <E>
     */
    private class KeySet<E> extends AbstractSet<E> {

        @Override
        public int size() {
            return HashMap.this.size;
        }

        @Override
        public boolean isEmpty() {
            return HashMap.this.size == 0;
        }

        @Override
        public Optional<E> remove(E key) {
            return HashMap.this.remove((K) key).map((value) -> key);
        }

        @Override
        public boolean contains(E key) {
            return HashMap.this.containsKey((K) key);
        }

        @Override
        public void clear() {
            HashMap.this.clear();
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {

                /**
                 * 记录当前到table的何处
                 */
                private int position;

                /**
                 * 记录现在已经了的迭代元素个数
                 */
                private int size;

                /**
                 * 表示下一个节点
                 */
                private Node node = (Node) table[0];

                @Override
                public boolean hasNext() {
                    return this.size < HashMap.this.size;
                }

                @Override
                public E next() {
                    Node node = this.node;
                    while (node == null) {
                        node = (Node) table[position++];
                    }
                    Node oldNode = node;
                    this.node = node.next;
                    this.size++;
                    return (E) oldNode.key;
                }
            };
        }
    }

    /**
     * Entry的Set集合
     */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public int size() {
            return HashMap.this.size;
        }

        @Override
        public boolean isEmpty() {
            return HashMap.this.size == 0;
        }

        @Override
        public Optional<Map.Entry<K, V>> remove(Map.Entry<K, V> entry) {
            HashMap.this.remove(entry);
            return Optional.ofNullable(entry);
        }

        @Override
        public boolean contains(Map.Entry<K, V> entry) {
            return HashMap.this.containsEntry(entry);
        }

        @Override
        public void clear() {
            HashMap.this.clear();
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() {

                /**
                 * 记录当前到table的何处
                 */
                private int position;

                /**
                 * 记录现在已经了的迭代元素个数
                 */
                private int size;

                /**
                 * 表示下一个节点
                 */
                private Node node = (Node) table[0];

                @Override
                public boolean hasNext() {
                    return this.size < HashMap.this.size;
                }

                @Override
                public Entry<K, V> next() {
                    Node node = this.node;
                    while (node == null) {
                        node = (Node) table[position++];
                    }
                    Node oldNode = node;
                    this.node = node.next;
                    this.size++;
                    return oldNode;
                }
            };
        }
    }

    public HashMap() {
        this(DEFAULT_INIT_CAPACITY);
    }

    /**
     * @param initCapacity 初始化容量
     * @throws IllegalArgumentException 当初始化容量超过最大容量时，抛出异常
     */
    public HashMap(int initCapacity) throws IllegalArgumentException {
        this(initCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * @param initCapacity   初始化容量
     * @param initLoadFactor 初始化负载因子 [0, 1)
     * @throws IllegalArgumentException 当初始化容量超过最大容量时，抛出异常
     */
    public HashMap(int initCapacity, float initLoadFactor) throws IllegalArgumentException {
        //计算负载因子
        if (initLoadFactor <= 0 || initLoadFactor > 1) {
            initLoadFactor = DEFAULT_LOAD_FACTOR;
        } else {
            loadFactor = initLoadFactor;
        }

        // 得出最靠近initCapacity的capacity = 2^n(n >= 4)的最小值,
        if (initCapacity <= DEFAULT_INIT_CAPACITY) {
            capacity = DEFAULT_INIT_CAPACITY;
        } else if (initCapacity <= MAX_CAPACITY) {
            int index = Arrays.binarySearch(POWER, initCapacity);
            if (index < 0) {
                index = Math.abs(index) - 2;
            }
            capacity = POWER[index];
        } else {
            throw new IllegalArgumentException("Illegal capacity: " + initCapacity);
        }

        resetThreshold();
        table = new Object[capacity];
    }

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.ofNullable(put(indexOfKey(key), key, value));
    }

    @Override
    public Optional<V> get(K key) {
        // 先获取索引的位置，然后一个一个实际比较
        // Note: JDK8为了性能当链表长度大于8时换成了红黑树
        Node node = (Node) table[indexOfKey(key)];
        while (node != null && !key.equals(node.key)) {
            node = node.next;
        }

        return Optional.ofNullable(node == null ? null : (V) node.value);
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.ofNullable(remove(key, null, true));
    }

    @Override
    public Optional<V> remove(K key, V value) {
        return Optional.ofNullable(remove(key, value, false));
    }

    @Override
    public boolean containsKey(K key) {
        Node node = (Node) table[indexOfKey(key)];
        while (node != null) {
            if (equalsKeyOrValue(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsEntry(K key, V value) {
        Node node = (Node) table[indexOfKey(key)];
        while (node != null) {
            if (equalsKeyOrValue(node.key, key) && equalsKeyOrValue(node.value, value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet<>();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public void clear() {
        capacity = DEFAULT_INIT_CAPACITY;
        loadFactor = DEFAULT_LOAD_FACTOR;
        size = 0;
        resetThreshold();
        table = new Object[capacity];
    }

    /**
     * node.key == key 或 node.value = value是否相等
     *
     * @param nodeProp node中的一个属性,或key或value
     * @param otherVar 另一个要比较的值
     * @return 返回true, 则相等;反之,则不相等
     */
    private static final <E> boolean equalsKeyOrValue(E nodeProp, E otherVar) {
        return (nodeProp != null && nodeProp.equals(otherVar)) || (nodeProp == null && otherVar == null);
    }

    /**
     * 重新设置threshold
     */
    private void resetThreshold() {
        threshold = (int) (capacity * loadFactor);
    }

    /**
     * 通过求模(%)获得实际的索引值<br/>
     * 当capacity为2的倍数时, hashcode % capacity 等价于 hashCode & (capacity - 1);<br/>
     * 当key == null时,直接返回0
     *
     * @param key
     * @return 返回在table的索引
     */
    private int indexOfKey(K key) {
        return key == null ? 0 : (key.hashCode() & 0x7FFF_FFFF) & (capacity - 1);
    }

    /**
     * 真正执行put操作
     *
     * @param index 要放入数组中的位置
     * @param key
     * @param value
     * @return 返回旧值;如果不存在旧值,则为null
     */
    private V put(int index, K key, V value) {
        Node node = (Node) table[index];
        while (node != null) {
            if (equalsKeyOrValue(node.key, key)) {
                break;
            }
            node = node.next;
        }

        V oldValue = null;

        if (node != null) {
            oldValue = (V) node.value;
            node.value = value;
        } else {
            if (++size > threshold) {
                ensureCapacity();
            }
            addNode(table, index, key, value);
        }

        return oldValue;
    }


    /**
     * 向table中增加一个节点
     *
     * @param table
     * @param index 处于table的哪个位置
     * @param key
     * @param value
     */

    private void addNode(Object[] table, int index, K key, V value) {
        Node node = new Node();
        node.key = key;
        node.value = value;
        node.next = (Node) table[index];
        table[index] = node;
    }

    /**
     * 确保容量<br/>
     * 当发生扩容时,会重新hash
     *
     * @throws RuntimeException 当容量超过最大容量时，抛出异常
     */
    private void ensureCapacity() throws RuntimeException {
        if (capacity < MAX_CAPACITY) {
            capacity <<= 1;
            resetThreshold();
            reHash();
        } else {
            throw new RuntimeException("up the max capacity");
        }
    }

    /**
     * 对Map中的元素重新进行Hash
     */
    private void reHash() {
        Object[] newTable = new Object[capacity];
        for (Object node : table) {
            Node temp = (Node) node;
            while (temp != null) {
                addNode(newTable, indexOfKey((K) temp.key), (K) temp.key, (V) temp.value);
                temp = temp.next;
            }
        }
        table = newTable;
    }

    /**
     * 真正的移除键值对
     *
     * @param key         待移除的键
     * @param value       待移除的值
     * @param ignoreValue 为true时,忽略value,,key符合时就移除;为false时,key-value都符合时，才移除
     * @return 返回被移除的value或null
     */
    private V remove(K key, V value, boolean ignoreValue) {
        int index = indexOfKey(key);
        Node node = (Node) table[index];
        Node prevNode = node;
        boolean isRoot = true;
        while (node != null) {
            if (equalsKeyOrValue(node.key, key)) {
                if (!ignoreValue && !equalsKeyOrValue(node.value, value)) {
                    continue;
                }
                if (isRoot) {
                    prevNode.next = node.next;
                } else {
                    table[index] = node.next;
                }
                size--;
                return (V) node.value;
            }
            prevNode = node;
            node = node.next;
            isRoot = false;
        }
        return null;
    }
}
