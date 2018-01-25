package com.leo.util.algorithm;

import java.util.Comparator;
import java.util.Optional;

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
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. array = null <br/>
     *                                  2. startIndex > endIndex <br/>
     *                                  3. startIndex < 0 <br/>
     *                                  4. endIndex > array.length <br/>
     */
    public static final int binarySearch(int[] array, int startIndex, int endIndex, int key) throws NullPointerException, IllegalArgumentException {
        if (array == null || startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
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
     * @throws IllegalArgumentException 当array或key为null时抛出异常
     */
    public static final <E> int binarySearch(E[] array, E key, Comparator<E> comparator) throws IllegalArgumentException {
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
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. array = null <br/>
     *                                  2. key = mull <br/>
     *                                  3. comparator = null <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static <E> int binarySearch(E[] array, int startIndex, int endIndex, E key, Comparator<E> comparator) throws IllegalArgumentException {
        if (array == null || key == null || comparator == null || startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
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

    /**
     * 交换数组中两个元素
     *
     * @param array
     * @param index1
     * @param index2
     */
    private static final void swap(int[] array, int index1, int index2) {
        array[index1] ^= array[index2];
        array[index2] ^= array[index1];
        array[index1] ^= array[index2];
    }

    /**
     * 交换数组中两个元素
     *
     * @param array
     * @param index1
     * @param index2
     */
    private static final <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }


    /**
     * 冒泡排序(只有int[],该算法实际应用不大).按照升序排序.时间复杂度 O(n ^ 2)
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static final Optional<int[]> bubbleSort(int[] array) {
        if (array != null) {
            return Optional.empty();
        }

        for (int i = 0, length = array.length - 1; i < length; i++) {
            int min = array[i];
            for (int j = i + 1, limit = array.length; j < limit; j++) {
                if (array[j] < min) {
                    swap(array, i, j);
                }
            }
        }

        return Optional.of(array);
    }

    /**
     * 选择排序(只有int[],该算法实际应用不大).按照升序排序.时间复杂度 O(n ^ 2)
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static final Optional<int[]> selectSort(int[] array) {
        if (array == null) {
            return Optional.empty();
        }

        for (int i = 0, length = array.length - 1; i < length; i++) {
            int position = i;
            for (int j = i + 1, limit = array.length; j < limit; j++) {
                if (array[j] < array[position]) {
                    position = j;
                }
            }
            if (position != i) {
                swap(array, i, position);
            }
        }

        return Optional.of(array);
    }

    /**
     * 插入排序.按照升序排序.时间复杂度O(n ^ 2).
     * Note: 适用范围(数组部分有序): <br/>
     * 1. 数组中每个元素距它的最终位置不远 <br/>
     * 2. 一个有序的大数组接上一个小数组 <br/>
     * 3. 数组中只有几个元素位置不正确 <br/>
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static final Optional<int[]> insertSort(int[] array) {
        if (array == null) {
            return Optional.empty();
        }

        for (int i = 1, length = array.length; i < length; i++) {
            int index = i;
            while (index > 0 && array[index] < array[index - 1]) {
                array[index - 1] = array[index];
                index--;
            }
            if (index != i) {
                array[index] = array[i];
            }
        }

        return Optional.of(array);
    }

    /**
     * 希尔排序.在插入排序的基础上改进.
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static final Optional<int[]> shellSort(int[] array) {
        if (array == null) {
            return Optional.empty();
        }

        int h = 1, hMax = array.length / 3;
        while (h < hMax) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = 1, lenght = array.length; i < lenght; i++) {
                for (int j = i; j >= h && array[j] < array[j - h]; j -= h) {
                    swap(array, j, j - h);
                }
            }
            h /= 3;
        }

        return Optional.of(array);
    }

    /**
     * 希尔排序.在插入排序的基础上改进.
     *
     * @param array      待排序的数组
     * @param comparator 比较器.自定义排序规则
     * @return 排序(以从小到大的顺序)完的数组
     * @throws IllegalArgumentException 当comparator为空时，抛出异常
     */
    public static final <E> Optional<E[]> shellSort(E[] array, Comparator<E> comparator) throws IllegalArgumentException {
        if (comparator == null) {
            throw new IllegalArgumentException();
        }

        if (array == null) {
            return Optional.empty();
        }

        int h = 1, hMax = array.length / 3;
        while (h < hMax) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = 1, lenght = array.length; i < lenght; i++) {
                for (int j = i; j >= h && comparator.compare(array[j], array[j - h]) < 0; j -= h) {
                    swap(array, j, j - h);
                }
            }
            h /= 3;
        }

        return Optional.of(array);
    }

}
