package com.nikitavassilenko.assignment1.sort;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import com.nikitavassilenko.assignment1.util.ArrayUtils;

import java.util.Random;

public class QuickSort {
    private static final Random RAND = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (ArrayUtils.isNullOrTrivial(a)) return;

        if (a.length < 2) return;
        quicksort(a, 0, a.length - 1, metrics);
    }

    private static void quicksort(int[] a, int left, int right, Metrics metrics) {
        if (ArrayUtils.isNullOrTrivial(a)) return;

        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + RAND.nextInt(right - left + 1);
            int pivotValue = a[pivotIndex];
            ArrayUtils.swap(a, pivotIndex, right);

            int partitionIndex = ArrayUtils.partition(a, left, right, pivotValue, metrics);

            if (partitionIndex - left < right - partitionIndex) {
                quicksort(a, left, partitionIndex - 1, metrics);
            } else {
                quicksort(a, partitionIndex + 1, right, metrics);
                right = partitionIndex - 1;
            }

            metrics.exitRecursion();
        }
    }
}
