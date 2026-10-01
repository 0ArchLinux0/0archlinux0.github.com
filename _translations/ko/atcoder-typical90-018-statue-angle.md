---
title: AtCoder Typical 90 018 — Statue of Chokudai
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
lang: ko
translation_key: atcoder-typical90-018-statue-angle
permalink: /ko/posts/atcoder-typical90-018-statue-angle/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_r)

`r = L / 2`라고 하겠습니다. 경과 시간 `E`에서 관람차의 회전각은 주기 `T`를 이용해 `theta = 2π * (E mod T) / T` 라디안으로 나타낼 수 있습니다. `E`가 `T`보다 커도 `E mod T`를 사용하면 각도를 한 바퀴 범위 안으로 되돌릴 수 있습니다.

관람차의 최하점 높이를 0으로 두면, 회전하는 수직면에서 탑승자의 좌표는 `y = -r sin(theta)`, `z = r(1 - cos(theta))`입니다. 관측자는 `(X, Y, 0)`에 있으므로 탑승자까지의 수평 거리는 `sqrt(X² + (Y - y)²)`입니다. 올려다보는 각도는 `atan2(z, horizontal distance)`로 구하고, 라디안에서 도 단위로 변환하면 답이 됩니다. 탑승자가 최하점에 있는 경우에도 `atan2`를 사용하므로 별도 처리가 필요하지 않습니다.

각 질의마다 상수 번의 산술 연산과 삼각 함수 계산을 하므로 시간 복잡도는 `O(Q)`입니다. 출력 버퍼에 `O(Q)` 공간을 사용합니다.

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
