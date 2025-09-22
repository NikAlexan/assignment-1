package com.nikitavassilenko.assignment1;

import com.nikitavassilenko.assignment1.closest.ClosestPair;
import com.nikitavassilenko.assignment1.metrics.CsvWriter;
import com.nikitavassilenko.assignment1.metrics.Metrics;
import com.nikitavassilenko.assignment1.select.DeterministicSelect;
import com.nikitavassilenko.assignment1.sort.MergeSort;
import com.nikitavassilenko.assignment1.sort.QuickSort;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: java -jar assignment1.jar <algorithm> <n> <csvPath>");
            System.out.println("Algorithms: mergesort, quicksort, select, closest");
            return;
        }

        String algorithm = args[0];
        int n = Integer.parseInt(args[1]);
        String csvPath = args[2];

        CsvWriter writer = new CsvWriter(csvPath);
        writer.writeHeader();

        Metrics metrics = new Metrics();
        long start, end;

        switch (algorithm.toLowerCase()) {
            case "mergesort" -> {
                int[] a = new Random().ints(n, -100000, 100000).toArray();
                start = System.nanoTime();
                MergeSort.sort(a, metrics);
                end = System.nanoTime();
                writer.append("mergesort", n, metrics, end - start);
            }
            case "quicksort" -> {
                int[] a = new Random().ints(n, -100000, 100000).toArray();
                start = System.nanoTime();
                QuickSort.sort(a, metrics);
                end = System.nanoTime();
                writer.append("quicksort", n, metrics, end - start);
            }
            case "select" -> {
                int[] a = new Random().ints(n, -100000, 100000).toArray();
                int k = n / 2;
                start = System.nanoTime();
                int result = DeterministicSelect.select(Arrays.copyOf(a, a.length), k, metrics);
                end = System.nanoTime();
                System.out.println("Median ~ " + result);
                writer.append("select", n, metrics, end - start);
            }
            case "closest" -> {
                ClosestPair.Point[] pts = new ClosestPair.Point[n];
                Random rnd = new Random();
                for (int i = 0; i < n; i++) {
                    pts[i] = new ClosestPair.Point(rnd.nextDouble(1000), rnd.nextDouble(1000));
                }
                start = System.nanoTime();
                ClosestPair.Result res = ClosestPair.findClosest(pts);
                end = System.nanoTime();
                System.out.println("Closest distance = " + res.dist());
                writer.append("closest", n, metrics, end - start);
            }
            default -> {
                System.err.println("Unknown algorithm: " + algorithm);
                return;
            }
        }

        System.out.println("Results written to " + csvPath);
    }
}