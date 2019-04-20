package com.leo.util.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author leo
 * @date 2019/4/19
 */
public class ArraysTest {


    @Test
    public void testQuickSortIsTrue() {

        int loopNumber = 1_000;
        int arrayLength = 10_000;
        int maxRange = 100_000;
        Random random = new Random();

        for (int i = 0; i < loopNumber; i++) {
            List<Integer> numbers = Stream.generate(() -> random.nextInt(maxRange)).limit(arrayLength).collect(Collectors.toList());
            Collections.shuffle(numbers);
            int[] sortByJava = numbers.stream().mapToInt(Integer::intValue).toArray();
            Collections.shuffle(numbers);
            int[] sortByMine = numbers.stream().mapToInt(Integer::intValue).toArray();

            Arrays.quickSort(sortByMine);
            java.util.Arrays.sort(sortByJava);

            Assert.assertArrayEquals(sortByJava, sortByMine);
        }
    }
}
