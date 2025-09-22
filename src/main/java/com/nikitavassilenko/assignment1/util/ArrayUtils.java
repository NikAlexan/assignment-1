package com.nikitavassilenko.assignment1.util;

import com.nikitavassilenko.assignment1.metrics.Metrics;

import java.util.Random;

public class ArrayUtils {
    private static final Random RAND = new Random();

    public static void swap(int[] a, int i, int j) {
        if (a == null) throw new IllegalArgumentException("Array is null");
        if (i < 0 || j < 0 || i >= a.length || j >= a.length) {
            throw new IndexOutOfBoundsException("Invalid index: " + i + ", " + j);
        }
        if (i != j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static int partition(int[] a, int left, int right, int pivot, Metrics metrics) {
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

    public static void shuffle(int[] a) {
        if (a == null) return;
        for (int i = a.length - 1; i > 0; i--) {
            int j = RAND.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static boolean isNullOrTrivial(int[] a) {
        return a == null || a.length < 2;
    }
}
