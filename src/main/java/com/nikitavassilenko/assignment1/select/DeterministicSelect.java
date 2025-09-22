package com.nikitavassilenko.assignment1.select;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import com.nikitavassilenko.assignment1.util.ArrayUtils;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Index k out of bounds");
        }
        return selectRecursive(a, 0, a.length - 1, k, metrics);
    }

    private static int selectRecursive(int[] a, int left, int right, int k, Metrics metrics) {
        while (true) {
            metrics.enterRecursion();

            if (left == right) {
                metrics.exitRecursion();
                return a[left];
            }

            int pivotIndex = medianOfMedians(a, left, right, metrics);
            int pivotValue = a[pivotIndex];
            ArrayUtils.swap(a, pivotIndex, right);

            int partitionIndex = ArrayUtils.partition(a, left, right, pivotValue, metrics);

            if (k == partitionIndex) {
                metrics.exitRecursion();
                return a[k];
            } else if (k < partitionIndex) {
                right = partitionIndex - 1;
            } else {
                left = partitionIndex + 1;
            }

            metrics.exitRecursion();
        }
    }

    private static int medianOfMedians(int[] a, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(a, left, right + 1);
            return left + n / 2;
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(a, i, subRight + 1);
            int medianIndex = i + (subRight - i) / 2;
            ArrayUtils.swap(a, left + numMedians, medianIndex);
            numMedians++;
        }

        return medianOfMedians(a, left, left + numMedians - 1, metrics);
    }
}
