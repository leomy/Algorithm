package com.leo.util.datastructure.list;

import com.leo.util.datastructure.Collection;
import com.leo.util.datastructure.List;

import java.util.Iterator;
import java.util.Optional;

/**
 * 双向循环链表
 *
 * @author leo
 * @version 1.0
 * @date: 2017/12/18
 * @since 1.0
 */
public class LinkedList<E> extends AbstractList<E> implements List<E> {

    /**
     * 头节点
     */
    private Node head;

    /**
     * 通过Node类保存指针域和值域
     */
    private class Node {
        /**
         * 指向前一个元素
         */
        Node prev;

        /**
         * 指向后一个元素
         */
        Node next;

        /**
         * 保存实际值
         */
        E value;
    }

    @Override
    public boolean add(E e) throws IllegalArgumentException {
        checkElement(e);

        Node newNode = new Node();
        newNode.value = e;

        //没有节点
        if (size == 0) {
            addFirstNode(newNode);
        } else {
            //头插法
            Node oldNode = head.next;
            head.next = newNode;
            oldNode.prev = newNode;
            newNode.next = oldNode;
            newNode.prev = head;
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection<E> collection) throws IllegalArgumentException {
        if (collection == null) {
            throw new NullPointerException("the value of collection is null");
        }

        for (E e : collection) {
            add(e);
        }

        return true;
    }

    @Override
    public Optional<E> remove(E e) {
        if (e == null) {
            return Optional.empty();
        }

        Node node = head.next;
        for (int i = 0; i < size; i++) {
            if (node.value.equals(e)) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.prev = null;
                node.next = null;
                node.value = null;
                size--;
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean add(int index, E e) throws IndexOutOfBoundsException, IllegalArgumentException {
        checkElement(e);
        checkIndex(index);

        Node newNode = new Node();
        newNode.value = e;

        //没有节点
        if (size == 0) {
            addFirstNode(newNode);
        } else {
            linkBefore(getNodeByIndex(index), newNode);
        }
        size++;
        return true;
    }


    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        Node node = getNodeByIndex(index);
        E e = node.value;

        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        node.value = null;

        size--;
        return e;
    }

    @Override
    public void clear() {
        Node temp = head.next;
        for (int i = 0; i < size; i++) {
            temp.prev = null;
            temp.value = null;
            temp = temp.next;
            temp.prev.next = null;
        }
        head.prev = null;
        head.next = null;
        size = 0;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException, IllegalArgumentException {
        checkElement(e);
        if (index < 0 || index >= size || (size == 0 && index == 0)) {
            throw new IndexOutOfBoundsException(indexAndSizeMassage(index));
        }

        E oldValue = null;

        if (size == 0) {
            //没有节点
            Node node = new Node();
            node.value = e;
            addFirstNode(node);
            size++;
        } else {
            Node node = getNodeByIndex(index);
            oldValue = node.value;
            node.value = e;
        }

        return oldValue;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        return getNodeByIndex(index).value;
    }

    @Override
    public boolean contains(E e) throws IllegalArgumentException {
        checkElement(e);

        int i = 0;
        Node temp = head.next;

        while (i <= size) {
            if (temp.value.equals(e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode.equals(head.prev);
            }

            @Override
            public E next() {
                return currentNode.next.value;
            }
        };
    }

    /**
     * 添加第一个节点时，需要进行特殊处理
     *
     * @param newNode
     */
    private void addFirstNode(Node newNode) {
        head = new Node();
        head.prev = newNode;
        head.next = newNode;
        newNode.prev = head;
        newNode.next = head;
    }

    /**
     * 根据索引获取元素
     *
     * @param index 默认 0 <= index < size，未作越界检查
     * @return
     */
    private Node getNodeByIndex(int index) {
        Node node = head.next;
        int i = 0;
        // 当前索引小于size的一半，从头节点开始查找
        if (index <= (size >> 1)) {
            while (++i <= index) {
                node = node.next;
            }
        } else {
            //当前索引大于等于size的一半，从未节点开始查找
            i = size;
            node = head.prev;
            while (--i >= index) {
                node = node.prev;
            }
        }
        return node;
    }

    /**
     * 在节点前增加节点
     *
     * @param oldNode 原来的节点
     * @param newNode 新增的节点
     */
    private void linkBefore(Node oldNode, Node newNode) {
        newNode.prev = oldNode.prev;
        newNode.next = oldNode;
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
    }

    /**
     * 在节点后增加节点
     *
     * @param oldNode 原来的节点
     * @param newNode 新增的节点
     */
    private void linkAfter(Node oldNode, Node newNode) {
        newNode.prev = oldNode;
        newNode.next = oldNode.next;
        oldNode.next.prev = newNode;
        oldNode.next = newNode;
    }
}
