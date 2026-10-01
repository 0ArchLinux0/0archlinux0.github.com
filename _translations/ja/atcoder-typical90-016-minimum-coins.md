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
lang: ja
translation_key: atcoder-typical90-016-minimum-coins
permalink: /ja/posts/atcoder-typical90-016-minimum-coins/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_p)

`dp[x]` を合計 `x` を作るために必要なコインの最小枚数とします。`dp[0] = 0` とし、正の金額では最後に使ったコインが 3 種類の額面 `A`、`B`、`C` のいずれかです。したがって、次の漸化式になります。

`dp[x] = min(dp[x - coin] + 1)`（各額面 `coin <= x` について）

金額を小さい順に処理することで、`dp[x - coin]` はすでに計算済みとなり、同じ額面を何度でも使えます。これは各額面を無制限に利用できるコイン問題です。各金額ですべての額面を確認するため、入力されたコインの順序は結果に影響しません。問題の制約上、目標金額は必ず作れます。

作れない状態を表す値として `N + 1` を使います。各額面は正の整数なので、作れる金額は最大 `N` 枚で表現でき、この値は可能な答えより大きくなります。時間計算量は `O(3N) = O(N)`、空間計算量は `O(N)` です。

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
