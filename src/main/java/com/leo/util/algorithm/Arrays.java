package com.leo.util.algorithm;

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
     * @param key 待查找的元素
     * @return 当 return >= 0 时,表示key在array中的位置;<br/>
     * 当 return < 0 时,-return + 1 表示key插入array时，应该在的位置
     * @throws NullPointerException 当array为空时抛出异常
     */
    public static final int binarySearch(int[] array, int key) throws NullPointerException {
        return binarySearch(array, 0, array.length, key);
    }

    /**
     * 在数组的 [startIndex,toIndex) 范围内查找key
     *
     * @param array      已经排序号好的数组
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
        if (key < array[startIndex]) {
            return -(startIndex + 1);
        }

        if (key > array[endIndex]) {
            return -(endIndex - startIndex + 2);
        }

        int middleIndex = 0, middle = 0;
        while (true) {
            middleIndex = ((startIndex + endIndex) >>> 1);
            middle = array[middleIndex];
            if (key == middle) {
                return middleIndex;
            } else if (key > middle) {
                startIndex = middleIndex + 1;
            } else {
                endIndex = middleIndex - 1;
            }
            if (startIndex > endIndex) {
                break;
            }
        }

        return -(startIndex + 1);

    }
}
