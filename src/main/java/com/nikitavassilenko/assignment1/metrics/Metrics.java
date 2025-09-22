package com.nikitavassilenko.assignment1.metrics;

public class Metrics {
    private long comparisons = 0;
    private long allocations = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;

    public void incComparisons() {
        comparisons++;
    }

    public void incAllocations() {
        allocations++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAllocations() {
        return allocations;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
        currentDepth = 0;
    }
}
