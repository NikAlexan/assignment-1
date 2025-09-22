package com.nikitavassilenko.assignment1.metrics;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private final String path;

    public CsvWriter(String path) {
        this.path = path;
    }

    public void writeHeader() throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("algorithm,n,comparisons,allocations,depth,time\n");
        }
    }

    public void append(String algorithm, int n, Metrics metrics, long timeNanos) throws IOException {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(String.format("%s,%d,%d,%d,%d,%d\n",
                    algorithm,
                    n,
                    metrics.getComparisons(),
                    metrics.getAllocations(),
                    metrics.getMaxDepth(),
                    timeNanos));
        }
    }
}
