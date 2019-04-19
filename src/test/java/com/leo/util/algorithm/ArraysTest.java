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
        int length = 10_000;
        int numberMaxRange = 100_000;
        Random random = new Random(numberMaxRange);

        List<Integer> numbers = Stream.generate(() -> random.nextInt()).limit(length).collect(Collectors.toList());
        Collections.shuffle(numbers);
        int[] sortByJava = numbers.stream().mapToInt(number -> number.intValue()).toArray();
        Collections.shuffle(numbers);
        int[] sortByMine = numbers.stream().mapToInt(number -> number.intValue()).toArray();

        Arrays.quickSort(sortByMine);
        java.util.Arrays.sort(sortByJava);

        Assert.assertArrayEquals(sortByJava, sortByMine);
    }
}
