package com.nikitavassilenko.assignment1.select;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeterministicSelectTest {
    @Test
    void testSingleElement() {
        int[] ints = {42};
        Metrics metrics = new Metrics();

        assertEquals(42, DeterministicSelect.select(ints, 0, metrics));
    }

    @Test
    void testSmallArray() {
        int[] ints = {3, 1, 2};
        Metrics metrics = new Metrics();

        assertEquals(1, DeterministicSelect.select(ints, 0, metrics));
        assertEquals(2, DeterministicSelect.select(ints, 1, metrics));
        assertEquals(3, DeterministicSelect.select(ints, 2, metrics));
    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(123);
        for (int n = 10; n <= 200; n += 10) {
            int[] a = rnd.ints(n, -1000, 1000).toArray();
            int[] sorted = Arrays.copyOf(a, a.length);

            Arrays.sort(sorted);

            for (int k = 0; k < n; k += Math.max(1, n / 5)) {
                Metrics m = new Metrics();
                int result = DeterministicSelect.select(Arrays.copyOf(a, a.length), k, m);

                assertEquals(sorted[k], result, "Mismatch at k=" + k + " n=" + n);
            }
        }
    }

    @Test
    void testInvalidK() {
        int[] ints = {1, 2, 3};
        Metrics metrics = new Metrics();

        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(ints, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(ints, 3, metrics));
    }
}
