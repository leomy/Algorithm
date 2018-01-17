package com.leo.util.algorithm;

import java.util.Comparator;

/**
 * 关于数组的算法.如排序、查找
 *
 * @author leo
 * @version 1.0
 * @date: 2018/1/16
 * @since 1.0
 */
public final class Arrays {

    private Arrays() {
    }

    /**
     * 在数组的 [0,array.length) 范围内查找key
     *
     * @param array 按从小到大顺序排列好的数组
     * @param key   待查找的元素
     * @return 当 return >= 0 时,表示key在array中的位置;<br/>
     * 当 return < 0 时,-return + 1 表示key插入array时，应该在的位置
     * @throws NullPointerException 当array为空时抛出异常
     */
    public static final int binarySearch(int[] array, int key) throws NullPointerException {
        return binarySearch(array, 0, array.length, key);
    }

    /**
     * 在数组的 [startIndex,endIndex) 范围内查找key
     *
     * @param array      按从小到大顺序排列好的数组
     * @param startIndex 起始索引,包含该索引.取值  [0, endIndex]
     * @param endIndex   结束索引，不包含该索引.取值 [startIndex, array.length)
     * @param key        待查找的元素
     * @return 当 return >= 0 时,表示key在array中的位置; <br/>
     * 当 return < 0 时,-return + 1 表示key插入array时，应该在的位置
     * @throws NullPointerException     当array为空时抛出异常
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static final int binarySearch(int[] array, int startIndex, int endIndex, int key) throws NullPointerException, IllegalArgumentException {
        if (array == null) {
            throw new NullPointerException("array is null");
        }
        if (startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
            throw new IllegalArgumentException();
        }

        endIndex = (endIndex == array.length) ? array.length - 1 : endIndex - 1;

        int start = array[startIndex], end = array[endIndex];
        if (key < start) {
            return -(startIndex + 1);
        }
        if (key == start) {
            return startIndex;
        }

        if (key > end) {
            return -(endIndex - startIndex + 2);
        }
        if (key == end) {
            return endIndex;
        }

        int middleIndex = 0, middle = 0;
        while (startIndex <= endIndex) {
            middleIndex = ((startIndex + endIndex) >>> 1);
            middle = array[middleIndex];
            if (key == middle) {
                return middleIndex;
            } else if (key > middle) {
                startIndex = middleIndex + 1;
            } else {
                endIndex = middleIndex - 1;
            }
        }

        return -(startIndex + 1);
    }

    /**
     * 在数组的 [0,array.length) 范围内查找key
     *
     * @param array      按从小到大顺序排列好的数组
     * @param key        待查找的元素
     * @param comparator 比较器,自定义规则比较对象大小
     * @return 当 return >= 0 时,表示key在array中的位置;<br/>
     * 当 return < 0 时,-return + 1 表示key插入array时，应该在的位置
     * @throws NullPointerException 当array或key为null时抛出异常
     */
    public static final <E> int binarySearch(E[] array, E key, Comparator<E> comparator) throws NullPointerException {
        return binarySearch(array, 0, array.length, key, comparator);
    }

    /**
     * 在数组的 [startIndex,endIndex) 范围内查找key
     *
     * @param array      按从小到大顺序排列好的数组
     * @param startIndex 起始索引,包含该索引.取值  [0, endIndex]
     * @param endIndex   结束索引，不包含该索引.取值 [startIndex, array.length)
     * @param key        待查找的元素
     * @param comparator 比较器,自定义规则比较对象大小
     * @return 当 return >= 0 时,表示key在array中的位置; <br/>
     * 当 return < 0 时,-return + 1 表示key插入array时，应该在的位置
     * @throws NullPointerException     当array、key、comparator中任意一个为null时抛出异常
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static <E> int binarySearch(E[] array, int startIndex, int endIndex, E key, Comparator<E> comparator) throws NullPointerException, IllegalArgumentException {
        if (array == null || key == null || comparator == null) {
            throw new NullPointerException();
        }
        if (startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
            throw new IllegalArgumentException();
        }

        endIndex = (endIndex == array.length) ? array.length - 1 : endIndex - 1;

        E start = array[startIndex], end = array[endIndex];
        int result = 0;

        if ((result = comparator.compare(key, start)) < 0) {
            return -(startIndex + 1);
        }
        if (result == 0) {
            return startIndex;
        }

        if ((result = comparator.compare(key, end)) > 0) {
            return -(endIndex - startIndex + 2);
        }
        if (result == 0) {
            return endIndex;
        }

        int middleIndex = 0, middle = 0;
        while (startIndex <= endIndex) {
            middleIndex = ((startIndex + endIndex) >>> 1);
            if ((result = comparator.compare(key, array[middleIndex])) == 0) {
                return middleIndex;
            } else if (result > 0) {
                startIndex = middleIndex + 1;
            } else {
                endIndex = middleIndex - 1;
            }
        }

        return -(startIndex + 1);
    }
}
