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
lang: ko
translation_key: atcoder-typical90-009-three-point-angle
permalink: /ko/posts/atcoder-typical90-009-three-point-angle/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_i)

각 점을 기준점으로 삼고, 그 점에서 다른 모든 점을 향하는 방향을 살펴봅니다. 두 방향을 선택하면 기준점에서 하나의 각이 만들어지므로, 모든 방향 쌍 중 작은 쪽의 각을 최대로 하면 됩니다.

방향각을 도 단위의 `[0, 360)` 범위로 정렬한 뒤, 정렬된 배열을 복제하여 복사본의 각도에 `360`을 더합니다. `m = N - 1`이라 할 때 각 방향마다 뒤따르는 `m - 1`개의 원소만 보면 됩니다. 이 범위는 `0°` 경계를 넘어가는 방향도 포함해 나머지 모든 방향을 정확히 한 번씩 나타냅니다. 두 방향이 `180°`만큼 떨어져 서로 정반대일 때 작은 쪽의 각이 가장 커집니다. 따라서 해당 범위에서 `angle + 180°`를 이분 탐색하고, 하한 위치와 그 직전 원소를 확인합니다. 두 후보는 이상적인 정반대 방향의 양쪽에서 가장 가까운 각도입니다.

각 후보에서 각도 차이를 `diff`라고 하면 작은 쪽의 각은 `min(diff, 360 - diff)`입니다. 모든 기준점과 방향 쌍에서 얻은 값의 최댓값이 답이며, 결과는 도 단위로 출력합니다. 기준점마다 정렬과 이분 탐색을 수행하므로 전체 시간 복잡도는 `O(N² log N)`입니다. 각도 배열에 사용하는 작업 공간은 `O(N)`입니다.

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
