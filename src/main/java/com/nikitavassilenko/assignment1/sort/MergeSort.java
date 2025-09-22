package com.nikitavassilenko.assignment1.sort;

import com.nikitavassilenko.assignment1.metrics.Metrics;

public class MergeSort {
    private static final int CUTOFF = 32;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        int[] buffer = new int[a.length];
        sortRecursive(a, buffer, 0, a.length - 1, metrics);
    }

    private static void sortRecursive(int[] a, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = (left + right) >>> 1;
        sortRecursive(a, buffer, left, mid, metrics);
        sortRecursive(a, buffer, mid + 1, right, metrics);
        merge(a, buffer, left, mid, right, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics metrics) {
        for (int i = left; i <= mid; i++) {
            buffer[i] = a[i];
            metrics.incAllocations();
        }

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (buffer[i] <= a[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = a[j++];
            }
        }

        while (i <= mid) {
            a[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}
