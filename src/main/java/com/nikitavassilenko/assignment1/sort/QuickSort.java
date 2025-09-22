package com.nikitavassilenko.assignment1.sort;

import com.nikitavassilenko.assignment1.metrics.Metrics;

import java.util.Random;

public class QuickSort {
    private static final Random RAND = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        quicksort(a, 0, a.length - 1, metrics);
    }

    private static void quicksort(int[] a, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + RAND.nextInt(right - left + 1);
            int pivotValue = a[pivotIndex];
            swap(a, pivotIndex, right);

            int partitionIndex = partition(a, left, right, pivotValue, metrics);

            if (partitionIndex - left < right - partitionIndex) {
                quicksort(a, left, partitionIndex - 1, metrics);
            } else {
                quicksort(a, partitionIndex + 1, right, metrics);
                right = partitionIndex - 1;
            }

            metrics.exitRecursion();
        }
    }

    private static int partition(int[] a, int left, int right, int pivot, Metrics metrics) {
        int i = left;
        for (int j = left; j < right; j++) {
            metrics.incComparisons();
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, right);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i != j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
