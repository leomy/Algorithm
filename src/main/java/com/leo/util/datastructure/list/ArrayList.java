package com.leo.util.datastructure.list;

import com.leo.util.datastructure.Collection;
import com.leo.util.datastructure.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

/**
 * 数组实现的List
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/19
 * @since 1.0
 */
public class ArrayList<E> extends AbstractList<E> implements List<E> {

    /**
     * 保存所有的元素
     */
    private Object[] elements;

    /**
     * 构造方法所使用的数组，当第一次put时才分配真正的数组
     */
    private static final Object[] EMPTY_ELEMENTS = new Object[]{};

    /**
     * 容量
     */
    private int capacity;

    /**
     * 默认初始容量,
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 最大容量
     */
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initCapacity) {
        if (initCapacity < 0 || capacity > MAX_CAPACITY) {
            throw new IllegalArgumentException("Illegal capacity: " + initCapacity);
        }
        capacity = initCapacity;
        elements = EMPTY_ELEMENTS;
    }


    @Override
    public boolean add(E e) throws NullPointerException {
        checkElement(e);

        ensureCapacity();
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean addAll(Collection<E> collection) throws IllegalArgumentException {
        if (collection == null) {
            throw new IllegalArgumentException("the value of collection is null");
        }

        int needMinCapacity = collection.size() + size;
        while (needMinCapacity >= capacity) {
            capacity += (capacity >> 1);
        }
        elements = Arrays.copyOf(elements, capacity);
        for (E e : collection) {
            elements[size++] = e;
        }
        return true;
    }

    @Override
    public boolean add(int index, E e) throws IndexOutOfBoundsException, IllegalArgumentException {
        checkElement(e);
        checkIndex(index);

        ensureCapacity();
        for (int i = size; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        E element = (E) elements[index];
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        return element;
    }


    @Override
    public Optional<E> remove(E e) {
        if (e == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(e)) {
                for (int j = i, endIndex = size - 1; j < endIndex; j++) {
                    elements[j] = elements[j + 1];
                }
                size--;
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException, IllegalArgumentException {
        checkElement(e);
        if (index < 0 || index >= size || (size == 0 && index == 0)) {
            throw new IndexOutOfBoundsException(indexAndSizeMassage(index));
        }

        E element = (E) elements[index];
        elements[index] = e;

        return e;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        return (E) elements[index];
    }

    @Override
    public boolean contains(E e) {
        if (e == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            /**
             * 记录迭代时的位置
             */
            private int currentPosition;

            @Override
            public boolean hasNext() {
                return currentPosition < size;
            }

            @Override
            public E next() {
                return (E) elements[currentPosition++];
            }
        };
    }

    /**
     * 确保并扩大容量.每次扩大到原来的1.5倍但不能超过最大容量.
     *
     * @throws RuntimeException 当超出最大容量时抛出异常
     */
    private void ensureCapacity() throws RuntimeException {
        if (size == capacity) {
            if (MAX_CAPACITY == capacity) {
                throw new RuntimeException("Size: " + size + " is up to the limit");
            }
            int halfCapacity = capacity >> 1;
            capacity = (MAX_CAPACITY - capacity) <= halfCapacity ? MAX_CAPACITY : capacity + halfCapacity;
            elements = Arrays.copyOf(elements, capacity);
        } else if (elements.equals(EMPTY_ELEMENTS)) {
            elements = new Object[capacity];
        }
    }

}
