---
title: AtCoder. 018 Statue of Chokudai(3)
author: MINJUN PARK
date: 2021-12-30 03:03:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Statue of Chokudai,
  ]
pin: false
lang: en
translation_key: atcoder-typical90-018-statue-angle
permalink: /posts/競プロ典型-90-問-018-Statue-of-Chokudai(3)/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_r)

Let `r = L / 2`. At elapsed time `E`, the wheel has turned through `theta = 2π * (E mod T) / T` radians, where `T` is its period. Wrapping the elapsed time by `T` keeps the angle within one revolution, including when `E` is greater than `T`.

With the bottom of the wheel at height zero, the passenger's coordinates in the rotating vertical plane are `y = -r sin(theta)` and `z = r(1 - cos(theta))`. The observer is at `(X, Y, 0)`, so the horizontal distance to the passenger is `sqrt(X² + (Y - y)²)`. The elevation angle is `atan2(z, horizontal distance)`; converting that result from radians to degrees gives the required answer. `atan2` handles the passenger being at the bottom without a special case.

Each query requires a constant number of arithmetic and trigonometric operations, so the time complexity is `O(Q)`. The implementation uses `O(Q)` space for the output buffer.

```java
import java.io.*;

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

    long nextLong() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);
      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      long value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return sign * value;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner();
    long period = input.nextLong();
    double radius = input.nextLong() / 2.0;
    double observerX = input.nextLong();
    double observerY = input.nextLong();
    int q = (int) input.nextLong();
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < q; i++) {
      long elapsed = input.nextLong();
      double theta = 2.0 * Math.PI * (elapsed % period) / period;
      double passengerY = -radius * Math.sin(theta);
      double passengerZ = radius * (1.0 - Math.cos(theta));
      double horizontal = Math.hypot(observerX, observerY - passengerY);
      double angle = Math.toDegrees(Math.atan2(passengerZ, horizontal));
      output.append(angle).append('\n');
    }

    System.out.print(output);
  }
}
```
