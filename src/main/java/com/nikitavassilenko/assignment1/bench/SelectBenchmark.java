package com.nikitavassilenko.assignment1.bench;

import com.nikitavassilenko.assignment1.metrics.Metrics;
import com.nikitavassilenko.assignment1.select.DeterministicSelect;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectBenchmark {
    @Param({"100", "1000", "10000"})
    private int n;

    private int[] data;
    private int k;

    @Setup(Level.Invocation)
    public void setup() {
        Random rnd = new Random(123);
        data = rnd.ints(n, -100000, 100000).toArray();
        k = n / 2;
    }

    @Benchmark
    public int testDeterministicSelect() {
        Metrics m = new Metrics();
        return DeterministicSelect.select(Arrays.copyOf(data, data.length), k, m);
    }

    @Benchmark
    public int testArraysSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k];
    }
}
