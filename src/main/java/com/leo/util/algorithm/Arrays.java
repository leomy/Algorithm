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
    public static int binarySearch(int[] array, int key) throws NullPointerException {
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
    public static int binarySearch(int[] array, int startIndex, int endIndex, int key) throws NullPointerException, IllegalArgumentException {
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
    public static <E> int binarySearch(E[] array, E key, Comparator<E> comparator) throws IllegalArgumentException {
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
        if (index1 == index2) {
            return;
        }
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
    private static <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * 判断 [startIndex,endIndex) 是否是 [0,array.length)的子集
     *
     * @param array
     * @param startIndex 起始索引
     * @param endIndex   结束索引
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    private static void checkIndex(int[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        if (startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 判断 [startIndex,endIndex) 是否是 [0,array.length)的子集
     *
     * @param array
     * @param startIndex 起始索引
     * @param endIndex   结束索引
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    private static void checkIndex(Object[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        if (startIndex > endIndex || startIndex < 0 || endIndex > array.length) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 冒泡排序(只有int[],该算法实际应用不大).按照升序排序.时间复杂度 O(n ^ 2)
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static Optional<int[]> bubbleSort(int[] array) {
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
    public static Optional<int[]> selectSort(int[] array) {
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
    public static Optional<int[]> insertSort(int[] array) {
        return insertSort(array, 0, array.length);
    }

    /**
     * 插入排序.按照升序排序.时间复杂度O(n ^ 2).
     * Note: 适用范围(数组部分有序): <br/>
     * 1. 数组中每个元素距它的最终位置不远 <br/>
     * 2. 一个有序的大数组接上一个小数组 <br/>
     * 3. 数组中只有几个元素位置不正确 <br/>
     *
     * @param array      待排序的数组
     * @param startIndex 起始索引,包含
     * @param endIndex   结束索引,不包含
     * @return 排序(以从小到大的顺序)完的数组
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static final Optional<int[]> insertSort(int[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        checkIndex(array, startIndex, endIndex);
        if (array == null) {
            return Optional.empty();
        }

        for (int i = startIndex + 1, index; i < endIndex; i++) {
            index = i;
            while (index > startIndex && array[index] < array[index - 1]) {
                swap(array, index, index - 1);
                index--;
            }
        }

        return Optional.of(array);
    }

    /**
     * 获得希尔排序中的最大h
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static int getShellSortMaxH(int startIndex, int endIndex) {
        int plus = startIndex + 1;
        int h = plus, hMax = (endIndex - startIndex) / 3;
        while (h < hMax) {
            h = 3 * h + plus;
        }
        return h;
    }

    /**
     * 希尔排序.在插入排序的基础上改进. 在 [0,array.length) 上对数组从小到大排序
     *
     * @param array 待排序的数组
     * @return 排序(以从小到大的顺序)完的数组
     */
    public static Optional<int[]> shellSort(int[] array) {
        return shellSort(array, 0, array.length);
    }

    /**
     * 希尔排序.在插入排序的基础上改进. 在 [startIndex,endIndex) 上对数组从小到大排序
     *
     * @param array      待排序的数组
     * @param startIndex 起始索引,包含
     * @param endIndex   结束索引,不包含
     * @return 排序(以从小到大的顺序)完的数组, 或为null
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static Optional<int[]> shellSort(int[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        if (array == null) {
            return Optional.empty();
        }
        checkIndex(array, startIndex, endIndex);

        int h = getShellSortMaxH(startIndex, endIndex);
        while (h >= 1) {
            for (int i = h, lenght = endIndex; i < lenght; i++) {
                for (int j = i; j >= h && array[j] < array[j - h]; j -= h) {
                    swap(array, j, j - h);
                }
            }
            h /= 3;
        }

        return Optional.of(array);
    }

    /**
     * 希尔排序.在 [0,array.length) 上对数组从小到大排序
     *
     * @param array      待排序的数组
     * @param comparator 比较器.自定义排序规则
     */
    public static <E> void shellSort(Object[] array, Comparator<E> comparator) {
        shellSort(array, 0, array.length, comparator);
    }

    /**
     * 希尔排序.在插入排序的基础上改进.在 [startIndex,endIndex) 上对数组从小到大排序
     *
     * @param array      待排序的数组
     * @param startIndex 起始索引,包含
     * @param endIndex   结束索引,不包含
     * @param comparator 比较器.自定义排序规则
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. 当comparator为空时，抛出异常
     *                                  2. startIndex > endIndex <br/>
     *                                  3. startIndex < 0 <br/>
     *                                  4. endIndex > array.length <br/>
     */
    public static final <E> void shellSort(Object[] array, int startIndex, int endIndex, Comparator<E> comparator) throws IllegalArgumentException {
        if (array == null) {
            return;
        }
        if (comparator == null) {
            throw new IllegalArgumentException();
        }
        checkIndex(array, startIndex, endIndex);

        int h = getShellSortMaxH(startIndex, endIndex);
        while (h >= 1) {
            for (int i = h, lenght = array.length; i < lenght; i++) {
                for (int j = i; j >= h && comparator.compare((E) array[j], (E) array[j - h]) < 0; j -= h) {
                    swap(array, j, j - h);
                }
            }
            h /= 3;
        }
    }

    /**
     * 希尔排序.在插入排序的基础上改进.在 [startIndex,endIndex) 上对数组从小到大排序
     *
     * @param array      待排序的数组
     * @param startIndex 起始索引,包含
     * @param endIndex   结束索引,不包含
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static <E extends Comparable> void shellSort(Object[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        if (array == null) {
            return;
        }
        checkIndex(array, startIndex, endIndex);

        int h = getShellSortMaxH(startIndex, endIndex);
        while (h >= 1) {
            for (int i = h, lenght = endIndex; i < lenght; i++) {
                for (int j = i; j >= h && ((E) array[j]).compareTo(array[j - h]) < 0; j -= h) {
                    swap(array, j, j - h);
                }
            }
            h /= 3;
        }
    }

    /**
     * 原地归并排序.将数组[0.array.length)内的元素排序
     *
     * @param array 待排序的数组
     */
    public static void mergeSort(int[] array) {
        mergeSort(array, 0, array.length);
    }

    /**
     * 原地归并排序.将数组[startIndex,endIndex)内的元素排序
     *
     * @param array      待排序的数组
     * @param startIndex 起始索引,包含
     * @param endIndex   结束索引,不包含
     * @throws IllegalArgumentException 当出现下列情况时,抛出异常: <br/>
     *                                  1. startIndex > endIndex <br/>
     *                                  2. startIndex < 0 <br/>
     *                                  3. endIndex > array.length <br/>
     */
    public static void mergeSort(int[] array, int startIndex, int endIndex) throws IllegalArgumentException {
        if (array == null) {
            return;
        }
        checkIndex(array, startIndex, endIndex);

        // 1. 当排序长度太小直接用插入排序
        if (endIndex - 4 < startIndex) {
            insertSort(array, startIndex, endIndex);
            return;
        }

        int[] cache = new int[endIndex - startIndex];
        mergeSort(array, startIndex, ((endIndex + startIndex) >> 1), endIndex, cache);
    }


    /**
     * 真正实现merge sort的函数.将src的[startIndex,endIndex)元素排序至dest
     *
     * @param src         待排序的数组
     * @param startIndex  要排序的起始索引,包含
     * @param middleIndex 要排序中间索引
     * @param endIndex    要排序的结束索引,不包含
     * @param cache       需要的辅助数组
     */
    private static void mergeSort(int[] src, int startIndex, int middleIndex, int endIndex, int[] cache) {
        if (endIndex - 4 < startIndex) {
            insertSort(src, startIndex, endIndex);
            return;
        }

        // 左半边数组排序
        mergeSort(src, startIndex, (startIndex + middleIndex) >> 1, middleIndex, cache);

        // 右半边数组排序
        mergeSort(src, middleIndex, (endIndex + middleIndex) >> 1, endIndex, cache);

        int cacheLength = endIndex - startIndex, cacheEndIndex = cacheLength - 1, cacheMiddleIndex = (cacheLength >> 1);
        System.arraycopy(src, startIndex, cache, 0, cacheLength);

        int leftIndex = 0, rightIndex = cacheMiddleIndex;
        for (int i = startIndex; i < endIndex; i++) {
            if (leftIndex >= cacheMiddleIndex) {
                src[i] = cache[rightIndex++];
            } else if (rightIndex > cacheEndIndex) {
                src[i] = cache[leftIndex++];
            } else if (cache[leftIndex] < cache[rightIndex]) {
                src[i] = cache[leftIndex++];
            } else {
                src[i] = cache[rightIndex++];
            }
        }
    }

    /**
     * 快速排序
     *
     * @param array 待排序的数组
     * @throws IllegalArgumentException 数组为null
     */
    public static void quickSort(int[] array) throws IllegalArgumentException {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * 快速排序
     *
     * @param array 待排序的数组
     * @param start 起始索引,包含在内
     * @param end   结束索引,不包含在内
     * @throws IllegalArgumentException
     */
    private static void quickSort(int[] array, int start, int end) throws IllegalArgumentException {
        if (array == null || start > end || start < 0 || end > array.length) {
            throw new IllegalArgumentException();
        }
        quickSortImplementsWithRecursive(array, start, end);
    }

    /**
     * 用递归实现的快速排序
     *
     * @param array 待排序的数组
     * @param start 起始位置,包含在内
     * @param end   结束位置,包含在内
     */
    private static void quickSortImplementsWithRecursive(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int left = start, right = end;
        int target = array[left];
        while (left < right) {
            while (target <= array[right] && left < right) {
                --right;
            }
            array[left] = array[right];
            while (target >= array[left] && left < right) {
                ++left;
            }
            array[right] = array[left];
        }
        array[left] = target;

        quickSortImplementsWithRecursive(array, start, left - 1);
        quickSortImplementsWithRecursive(array, left + 1, end);
    }


}


