package com.nikitavassilenko.assignment1;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import com.nikitavassilenko.assignment1.util.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {
    @Test
    void testSwap() {
        int[] array = {1, 2, 3};

        ArrayUtils.swap(array, 0, 2);

        assertArrayEquals(new int[]{3, 2, 1}, array);
    }

    @Test
    void testSwapInvalidIndex() {
        int[] array = {1, 2, 3};

        assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.swap(array, -1, 2));
    }

    @Test
    void testPartition() {
        int[] array = {3, 2, 1, 5, 4};
        Metrics metrics = new Metrics();

        int partition = ArrayUtils.partition(array, 0, array.length - 1, 3, metrics);

        assertTrue(Arrays.stream(array, 0, partition).allMatch(x -> x <= 3));
        assertTrue(Arrays.stream(array, partition + 1, array.length).allMatch(x -> x > 3));
    }

    @Test
    void testShuffle() {
        int[] array = {1, 2, 3, 4, 5};
        int[] copyOfArray = Arrays.copyOf(array, array.length);

        ArrayUtils.shuffle(array);

        assertEquals(5, array.length);
        assertTrue(Arrays.stream(array).allMatch(x -> Arrays.stream(copyOfArray).anyMatch(y -> y == x)));
    }

    @Test
    void testGuard() {
        assertTrue(ArrayUtils.isNullOrTrivial(null));
        assertTrue(ArrayUtils.isNullOrTrivial(new int[]{}));
        assertTrue(ArrayUtils.isNullOrTrivial(new int[]{42}));
        assertFalse(ArrayUtils.isNullOrTrivial(new int[]{1, 2}));
    }
}
