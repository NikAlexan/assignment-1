package com.nikitavassilenko.assignment1.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricsTest {

    @Test
    public void testIncComparisons() {
        Metrics metrics = new Metrics();

        metrics.incComparisons();

        assertEquals(1, metrics.getComparisons());
    }

    @Test
    public void testIncAllocations() {
        Metrics metrics = new Metrics();

        metrics.incAllocations();
        metrics.incAllocations();

        assertEquals(2, metrics.getAllocations());
    }

    @Test
    public void testRecursionDepth() {
        Metrics metrics = new Metrics();

        metrics.enterRecursion();
        metrics.enterRecursion();
        metrics.exitRecursion();
        metrics.exitRecursion();

        assertEquals(2, metrics.getMaxDepth());
    }

    @Test
    public void testReset() {
        Metrics metrics = new Metrics();

        metrics.enterRecursion();
        metrics.enterRecursion();
        metrics.exitRecursion();

        metrics.incComparisons();
        metrics.incAllocations();

        metrics.reset();

        assertEquals(0, metrics.getMaxDepth());
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getAllocations());
    }
}
