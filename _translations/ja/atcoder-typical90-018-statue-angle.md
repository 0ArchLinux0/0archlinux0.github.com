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
lang: ja
translation_key: atcoder-typical90-018-statue-angle
permalink: /ja/posts/atcoder-typical90-018-statue-angle/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_r)

`r = L / 2` とします。経過時間 `E` における観覧車の回転角は、周期 `T` を使って `theta = 2π * (E mod T) / T` ラジアンと表せます。`E` が `T` を超える場合も、`E mod T` によって角度を1周分の範囲に収められます。

車輪の最下点の高さを0とすると、回転する垂直面内での乗客の座標は `y = -r sin(theta)`、`z = r(1 - cos(theta))` です。観測者の位置は `(X, Y, 0)` なので、乗客までの水平距離は `sqrt(X² + (Y - y)²)` です。仰角は `atan2(z, horizontal distance)` で求められます。この値をラジアンから度に変換すれば答えになります。乗客が最下点にいる場合も、`atan2` を使えば特別な処理は必要ありません。

各クエリでは定数回の算術演算と三角関数の計算を行うため、時間計算量は `O(Q)` です。出力バッファに `O(Q)` の領域を使います。

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
