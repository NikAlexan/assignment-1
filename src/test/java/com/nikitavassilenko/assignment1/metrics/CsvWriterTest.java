package com.nikitavassilenko.assignment1.metrics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CsvWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void writeHeader() throws IOException {
        Path csv = tempDir.resolve("test.csv");
        CsvWriter writer = new CsvWriter(csv.toString());

        writer.writeHeader();

        List<String> lines = Files.readAllLines(csv);
        assertFalse(lines.isEmpty(), "File should not be empty");
        assertEquals("algorithm,n,comparisons,allocations,depth,time", lines.getFirst());
    }

    @Test
    void append() throws IOException {
        Path csv = tempDir.resolve("test.csv");
        CsvWriter writer = new CsvWriter(csv.toString());

        Metrics metrics = new Metrics();
        metrics.incComparisons();
        metrics.incAllocations();
        metrics.enterRecursion();
        metrics.enterRecursion();
        metrics.exitRecursion();
        metrics.exitRecursion();

        writer.writeHeader();
        long timeNanos = 123456L;
        writer.append("mergesort", 10, metrics, timeNanos);

        List<String> lines = Files.readAllLines(csv);
        assertEquals(2, lines.size(), "File should have 2 lines");

        String header = lines.getFirst();
        String data = lines.get(1);
        
        assertEquals("algorithm,n,comparisons,allocations,depth,time", header, "Header should be first line");

        String[] fields = data.split(",", -1);
        assertEquals("mergesort", fields[0]);
        assertEquals("10", fields[1]);
        assertEquals(String.valueOf(metrics.getComparisons()), fields[2]);
        assertEquals(String.valueOf(metrics.getAllocations()), fields[3]);
        assertEquals(String.valueOf(metrics.getMaxDepth()), fields[4]);
        assertEquals(String.valueOf(timeNanos), fields[5]);
    }
}
