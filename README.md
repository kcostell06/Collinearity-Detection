# Collinearity Detection

An algorithm in Java that counts collinear points across three arrays of integers, comparing a brute-force approach against a sort + binary-search optimisation — with empirical performance analysis. Written for CSU22011 (Algorithms), Trinity College Dublin.

## The Problem

Given three arrays `a1`, `a2`, `a3`, each integer `a1[i]` represents a point `(a1[i], 1)` on the plane, and likewise `(a2[i], 2)` and `(a3[i], 3)`. Since arrays `a1`, `a2`, `a3` sit on the horizontal lines `y=1`, `y=2`, `y=3`, any non-horizontal line crossing all three contributes one point from each array. Three points `(x1,y1)`, `(x2,y2)`, `(x3,y3)` are collinear when:

```
x1(y2-y3) + x2(y3-y1) + x3(y1-y2) = 0
```

The task is to count how many collinear triples `(x1, x2, x3)` — one element from each array — exist.

## Approaches

- **`countCollinear`** — brute force: check every combination of one element from each array. **O(N³)**.
- **`countCollinearFast`** — sort `a3` with insertion sort, then for each pair `(x1, x2)` compute the required `x3 = 2*x2 - x1` and binary search for it in `a3`. **O(N² log N)**.
- **`sort`** — insertion sort, **Θ(N²)** worst case.
- **`binarySearch`** — iterative binary search over a sorted array, **Θ(log N)** worst case.

## Project Structure

```
src/csu22011_a1/
  Collinear.java       Both counting algorithms, sort, and binary search
  CollinearTest.java   JUnit test suite + timing harness
  TestRunner.java       Runs CollinearTest via JUnitCore
  In.java, StdOut.java, Stopwatch.java   Sedgewick & Wayne stdlib helpers
  r0*-{1,2,3}.txt      Random input arrays (N = 1000, 2000, 4000, 5000) for timing trials
```

## Running the Tests

This is an Eclipse project (Java SE 22, JUnit 4) with no external build tool. To run the test suite from Eclipse: right-click `CollinearTest.java` → **Run As → JUnit Test**, or run `TestRunner.main()` to execute the same suite via `JUnitCore` from the console.

## Performance

`CollinearTest.main()` times both algorithms against the bundled `r0*.txt` datasets (N = 1000, 2000, 4000, 5000), confirming the brute-force approach's cubic blowup against the near-quadratic growth of the sort + binary-search version.

## Resources

- Sedgewick & Wayne, *Algorithms* (4th ed.) — `In`, `StdOut`, and `Stopwatch` are adapted from the accompanying [algs4](https://algs4.cs.princeton.edu/code/) library.
