---
title: AtCoder Typical 90 015 — Don't be too close(6)
author: MINJUN PARK
date: 2021-12-30 03:00:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Don't be too close,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-015-non-adjacent
permalink: /ja/posts/atcoder-typical90-015-non-adjacent/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_o)

一列に並んだ `N` 個の位置から、互いに隣り合わないように `k` 個を選ぶ方法の数を、`k = 1` から `N` までそれぞれ求めます。各答えを `1,000,000,007` で割った余りを出力します。

選んだ位置を `x_1 < x_2 < ... < x_k` とします。連続する選択位置は隣り合えないため、`x_(i+1) >= x_i + 2` が成り立ちます。`i` 番目の選択位置を `i - 1` だけ左にずらし、`y_i = x_i - (i - 1)` と定義すると、`y_i` は `1` から `N - k + 1` までの範囲から選んだ相異なる位置になります。この変換は全単射です。短くした範囲から相異なる位置を `k` 個選び、`i` 番目の位置に `i - 1` を足せば、元の列で隣り合わない選び方が一意に復元されます。したがって答えは `C(N - k + 1, k)` です。`k > N - k + 1` の場合は、必要な間隔を取り除いた後の位置が足りないため、答えは 0 です。

`N` までの階乗を前計算し、フェルマーの小定理を使って逆階乗を求めます。素数 `MOD` を法として `0` でない `a` について `a^(MOD-1) ≡ 1` なので、`a^(MOD-2)` が逆元になります。二分累乗法で `N!` の逆元を一度求め、後ろから順に計算すればすべての逆階乗が得られます。その後、`C(n, k) = n! / (k!(n-k)!)` を剰余付きの乗算で計算します。

前計算は時間 `O(N + log MOD)`、空間 `O(N)` です。`N` 個の答えはそれぞれ定数時間で求められます。剰余同士の積は `MOD^2 < Long.MAX_VALUE` なので、Java の `long` に安全に収まります。

```java
import java.io.*;

public class Main {
  static final long MOD = 1_000_000_007L;

  static class FastScanner {
    private final InputStream input;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

    FastScanner(InputStream input) {
      this.input = input;
    }

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

      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value;
    }
  }

  static long modPow(long base, long exponent) {
    long result = 1;
    while (exponent > 0) {
      if ((exponent & 1) == 1) result = result * base % MOD;
      base = base * base % MOD;
      exponent >>= 1;
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    long[] factorial = new long[n + 1];
    long[] inverseFactorial = new long[n + 1];

    factorial[0] = 1;
    for (int i = 1; i <= n; i++) {
      factorial[i] = factorial[i - 1] * i % MOD;
    }
    inverseFactorial[n] = modPow(factorial[n], MOD - 2);
    for (int i = n; i > 0; i--) {
      inverseFactorial[i - 1] = inverseFactorial[i] * i % MOD;
    }

    StringBuilder output = new StringBuilder();
    for (int k = 1; k <= n; k++) {
      int available = n - k + 1;
      long ways = 0;
      if (k <= available) {
        ways = factorial[available] * inverseFactorial[k] % MOD
            * inverseFactorial[available - k] % MOD;
      }
      output.append(ways).append('\n');
    }
    System.out.print(output);
  }
}
```
