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
lang: ko
translation_key: atcoder-typical90-015-non-adjacent
permalink: /ko/posts/atcoder-typical90-015-non-adjacent/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_o)

일렬로 놓인 `N`개의 위치 중에서 서로 인접하지 않도록 `k`개를 고르는 방법의 수를 `k = 1`부터 `N`까지 각각 구합니다. 각 답은 `1,000,000,007`로 나눈 나머지로 출력합니다.

선택한 위치를 `x_1 < x_2 < ... < x_k`라고 합시다. 연속해서 선택된 두 위치는 인접할 수 없으므로 `x_(i+1) >= x_i + 2`입니다. `i`번째 선택 위치를 `i - 1`만큼 왼쪽으로 옮겨 `y_i = x_i - (i - 1)`로 정의하면, `y_i`들은 `1`부터 `N - k + 1` 사이에서 고른 서로 다른 위치가 됩니다. 이 변환은 일대일 대응입니다. 줄어든 범위에서 서로 다른 위치 `k`개를 고른 뒤 각각의 `i`번째 위치에 `i - 1`을 더하면 원래 줄에서 서로 인접하지 않는 선택을 정확히 하나 복원할 수 있습니다. 따라서 답은 `C(N - k + 1, k)`입니다. `k > N - k + 1`이면 간격을 확보하고 남은 위치가 부족하므로 답은 0입니다.

`N`까지의 팩토리얼을 미리 계산하고, 페르마의 소정리로 역팩토리얼을 구합니다. 소수 `MOD`에 대해 0이 아닌 `a`는 `a^(MOD-1) ≡ 1`을 만족하므로 `a^(MOD-2)`가 역원입니다. 이진 거듭제곱으로 `N!`의 역원을 한 번 구한 뒤, 역방향으로 순회하면 모든 역팩토리얼을 얻습니다. 그 다음 `C(n, k) = n! / (k!(n-k)!)`을 모듈러 곱셈으로 계산합니다.

전처리는 `O(N + log MOD)` 시간과 `O(N)` 공간을 사용하며, `N`개의 답은 각각 상수 시간에 구합니다. 두 나머지의 곱은 `MOD^2 < Long.MAX_VALUE`이므로 Java `long`에 안전하게 들어갑니다.

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
