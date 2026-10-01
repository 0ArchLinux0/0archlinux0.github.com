---
title: AtCoder. 009 Three Point Angle(6)
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Three Point Angle,
    Review,
  ]
pin: false
lang: en
translation_key: atcoder-typical90-009-three-point-angle
permalink: /posts/競プロ典型-90-問-009-Three-Point-Angle/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_i)

For each point as a pivot, consider the directions from it to every other point. Any choice of two such directions forms an angle at the pivot, so the answer is the largest smaller angle among all pairs of directions.

After sorting the directions in degrees in `[0, 360)`, duplicate the sorted list with `360` added to each copied angle. For each direction, only the next `m - 1` entries are relevant, where `m = N - 1`; they represent every other direction exactly once, including directions across the `0°` boundary. The smaller angle is largest when the two directions are as close as possible to being opposite (`180°` apart). Therefore, binary-search for `angle + 180°` in that range and check the lower-bound position and its predecessor. Those are the closest candidates on either side of the ideal opposite direction.

For each candidate, let `diff` be the nonnegative angular difference. The smaller angle is `min(diff, 360 - diff)`. Taking the maximum over all pivots and pairs gives the required angle in degrees. Sorting and searching for each pivot takes `O(N log N)` time, for `O(N² log N)` total time. The angle arrays use `O(N)` working space.

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream input = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int length;
    private int pointer;

    private int read() throws IOException {
      if (pointer == length) {
        length = input.read(buffer);
        pointer = 0;
        if (length == -1) return -1;
      }
      return buffer[pointer++];
    }

    int nextInt() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);
      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return sign * value;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner();
    int n = input.nextInt();
    int[] x = new int[n];
    int[] y = new int[n];
    for (int i = 0; i < n; i++) {
      x[i] = input.nextInt();
      y[i] = input.nextInt();
    }

    int m = n - 1;
    double answer = 0.0;
    double[] angles = new double[m];
    double[] doubled = new double[2 * m];
    for (int pivot = 0; pivot < n; pivot++) {
      int size = 0;
      for (int point = 0; point < n; point++) {
        if (point == pivot) continue;
        double dx = (double) x[point] - x[pivot];
        double dy = (double) y[point] - y[pivot];
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        if (angle < 0) angle += 360.0;
        angles[size++] = angle;
      }
      Arrays.sort(angles);
      for (int i = 0; i < m; i++) {
        doubled[i] = angles[i];
        doubled[i + m] = angles[i] + 360.0;
      }

      for (int i = 0; i < m; i++) {
        double target = angles[i] + 180.0;
        int low = i + 1;
        int high = i + m;
        while (low < high) {
          int middle = low + (high - low) / 2;
          if (doubled[middle] < target) low = middle + 1;
          else high = middle;
        }
        answer = Math.max(answer, smallerAngle(doubled[low] - angles[i]));
        if (low > i + 1) {
          answer = Math.max(answer, smallerAngle(doubled[low - 1] - angles[i]));
        }
      }
    }
    System.out.println(answer);
  }

  static double smallerAngle(double difference) {
    return Math.min(difference, 360.0 - difference);
  }
}
```
