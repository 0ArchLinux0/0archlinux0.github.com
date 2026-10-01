---
title: AtCoder Typical 90 009 — Three Point Angle
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
lang: ja
translation_key: atcoder-typical90-009-three-point-angle
permalink: /ja/posts/atcoder-typical90-009-three-point-angle/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_i)

各点を基準点とし、その点から他のすべての点へ向かう方向を考えます。2つの方向を選ぶと基準点に角ができるため、すべての方向の組について小さい方の角を求め、その最大値を答えにします。

方向の角度を度数法の `[0, 360)` に正規化してソートし、ソート済み配列を複製してコピー側の各角度に `360` を加えます。`m = N - 1` とすると、各方向について後続する `m - 1` 個の要素だけを調べれば十分です。この範囲には `0°` をまたぐ方向も含まれ、他の方向がそれぞれ一度ずつ現れます。2つの方向が `180°` 離れて正反対に近いほど、小さい方の角は大きくなります。そのため、この範囲で `angle + 180°` を二分探索し、下限の位置とその直前の要素を確認します。この2つが理想的な正反対の方向を挟む最も近い候補です。

候補ごとに角度差を `diff` とすると、小さい方の角は `min(diff, 360 - diff)` です。すべての基準点と方向の組について最大値を取り、度数法で出力します。基準点ごとのソートと二分探索を合わせた時間計算量は `O(N² log N)`、角度配列に必要な作業領域は `O(N)` です。

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
