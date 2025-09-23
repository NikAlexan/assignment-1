# Report

## Benchmark Experimental Results (n = 1000)

| algorithm | n    | comparisons | allocations | depth | time (ns)  |
|-----------|------|-------------|-------------|-------|------------|
| closest   | 1000 | 0           | 0           | 0     | 16 507 560 |
| mergesort | 1000 | 13 479      | 2 508       | 6     | 2 014 008  |
| quicksort | 1000 | 58 720      | 0           | 7     | 4 532 665  |
| select    | 1000 | 2 145       | 0           | 1     | 4 201 759  |

## Benchmark Experimental Results (n = 100000)

| Algorithm | n      | Comparisons | Allocations | Depth | Time (ns)   |
|-----------|--------|-------------|-------------|-------|-------------|
| Closest   | 100000 | 0           | 0           | 0     | 172,493,758 |
| MergeSort | 100000 | 1,864,265   | 601,360     | 13    | 36,385,557  |
| QuickSort | 100000 | 21,106,995  | 0           | 12    | 146,764,850 |
| Select    | 100000 | 205,837     | 0           | 1     | 34,042,088  |

# Report

## Architecture Notes

- **Comparisons** are incremented each time an element-to-element comparison occurs (e.g., `if (a[i] <= a[j])` in
  sorting, pivot checks in QuickSort, pairwise checks in Select).
- **Allocations** are counted explicitly when temporary buffers are created (e.g., MergeSort’s auxiliary array).
- **Depth** is tracked via recursive entry/exit: each `enterRecursion()` increases the current depth, and each
  `exitRecursion()` decreases it. The maximum observed depth is stored as `maxDepth`.

This architecture allows both **time** and **structural complexity** of the algorithms to be measured.

---

## Recurrence Analysis

- **MergeSort**  
  Recurrence: `T(n) = 2T(n/2) + O(n)` for merging.  
  By Master theorem, case 2: `O(n log n)`.  
  Experimental depth grows logarithmically (= log2n), and allocations scale linearly with `n` (auxiliary buffer).

- **QuickSort**  
  Recurrence: `T(n) = T(k) + T(n−k−1) + O(n)` with randomized pivots.  
  Average case: `O(n log n)`, worst case: `O(n^2)`.  
  Depth matches log n when balanced; comparisons grow faster than MergeSort in practice.

- **Deterministic Select (MoM5)**  
  Recurrence: `T(n) = T(n/5) + T(7n/10) + O(n)`.  
  By Akra–Bazzi, the solution is linear: `O(n)`.  
  Depth remains constant (always one recursion at the top level), comparisons grow linearly, but wall-clock time shows
  significant constant overhead.

- **Closest Pair (Divide-and-Conquer)**  
  Recurrence: `T(n) = 2T(n/2) + O(n)` for merge-by-y and strip check.  
  By Master theorem, case 2: `O(n log n)`.  
  Our instrumentation currently does not count comparisons/depth, but runtime scales as expected with a larger constant
  factor.

---

## Plots and Discussion

### Time vs n

- At **n = 1000**: MergeSort is fastest (~2 ms), QuickSort slower (~4.5 ms), Select comparable (~4.2 ms), Closest
  significantly slower (~16.5 ms).
- At **n = 100000**: MergeSort still scales well (~36 ms), Select is ~34 ms (linear trend), QuickSort grows steeply (~
  147 ms), Closest is the slowest (~172 ms).

### Depth vs n

- MergeSort depth increases logarithmically (6 at n=1000, 13 at n=100000).
- QuickSort depth ~7-12 depending on pivot balance.
- Select depth remains constant at 1 (by design).
- The closest depth is not tracked.

### Constant-factor effects

- **Cache locality**: MergeSort benefits from sequential memory access, QuickSort suffers more at larger n.
- **GC (Garbage Collection)**: noticeable in Java for large allocations, especially in MergeSort (aux buffers).
- **Algorithmic overhead**: Select’s recursion is shallow, but pivot sampling and partitioning add constant costs,
  making it slower than sorting for small n despite linear asymptotics.

---

## Summary

- **Alignment with theory**:
    - MergeSort, QuickSort, Closest Pair all scale as `n log n`.
    - Select scales linearly in comparisons and depth.

- **Mismatch**:
    - Select’s runtime is worse than MergeSort at small sizes due to large constants, but catches up at larger n.
    - QuickSort shows more comparisons and slower execution despite the same asymptotics — practical evidence of
      constant-factor differences.
    - The Closest Pair has the largest constants, which dominate runtime despite optimal asymptotic complexity.

**Overall:** theoretical expectations hold, but real measurements highlight the impact of constants, memory allocation,
cache effects, and JVM overhead.