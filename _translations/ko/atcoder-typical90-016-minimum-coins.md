---
title: AtCoder Typical 90 016 — Minimum Coins
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
    Minimum Coins,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-016-minimum-coins
permalink: /ko/posts/atcoder-typical90-016-minimum-coins/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_p)

`dp[x]`를 합계 `x`를 만드는 데 필요한 동전의 최소 개수라고 정의합니다. `dp[0] = 0`으로 두고, 양의 금액마다 마지막에 사용한 동전은 세 가지 액면 `A`, `B`, `C` 중 하나입니다. 따라서 다음 점화식을 얻습니다.

`dp[x] = min(dp[x - coin] + 1)` (각 액면 `coin <= x`에 대해)

금액을 작은 순서대로 처리하면 `dp[x - coin]`은 이미 계산되어 있으며, 해당 액면을 여러 번 사용한 경우도 포함합니다. 각 액면을 반복해서 사용할 수 있는 무제한 동전 교환 문제입니다. 모든 금액에서 세 액면을 모두 확인하므로 입력된 동전의 순서는 결과에 영향을 주지 않습니다. 문제 조건상 목표 금액은 만들 수 있습니다.

만들 수 없는 상태의 값으로 `N + 1`을 사용합니다. 액면은 양의 정수이므로 만들 수 있는 금액은 최대 `N`개의 동전으로 표현할 수 있고, 따라서 이 값은 가능한 답보다 큽니다. 시간 복잡도는 `O(3N) = O(N)`, 공간 복잡도는 `O(N)`입니다.

```java
import java.io.*;

public class Main {
  static class FastScanner {
    private final InputStream input = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

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

  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner();
    int target = scanner.nextInt();
    int[] coins = { scanner.nextInt(), scanner.nextInt(), scanner.nextInt() };

    int unreachable = target + 1;
    int[] dp = new int[target + 1];
    for (int amount = 1; amount <= target; amount++) {
      dp[amount] = unreachable;
      for (int coin : coins) {
        if (coin <= amount && dp[amount - coin] != unreachable) {
          dp[amount] = Math.min(dp[amount], dp[amount - coin] + 1);
        }
      }
    }

    System.out.println(dp[target]);
  }
}
```
