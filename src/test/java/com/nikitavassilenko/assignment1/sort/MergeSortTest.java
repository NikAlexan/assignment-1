package com.nikitavassilenko.assignment1.sort;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTest {
    @Test
    void testEmptyArray() {
        int[] a = {};
        Metrics metrics = new Metrics();
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void testSingleElement() {
        int[] a = {42};
        Metrics metrics = new Metrics();
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void testSortedArray() {
        int[] a = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics();
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
        assertTrue(metrics.getComparisons() > 0);
    }

    @Test
    void testRandomArray() {
        int[] a = new Random(42).ints(100, -1000, 1000).toArray();
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(a, metrics);

        assertArrayEquals(expected, a);
        assertTrue(metrics.getMaxDepth() > 0);
    }

    @Test
    void testCutoffInsertionSort() {
        int[] a = {5, 4, 3, 2, 1};
        Metrics metrics = new Metrics();
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }
}
