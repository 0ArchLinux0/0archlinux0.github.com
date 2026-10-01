---
title: BOJ 11659 - 区間和を求める 4
author: MINJUN PARK
date: 2021-11-17 14:11:00 +0900
categories: [Record, Code]
tags: [コード, Java, アルゴリズム, 累積和, BOJ, 区間和]
lang: ja
translation_key: boj-11659-range-sum
permalink: /ja/posts/boj-11659-range-sum/
pin: false
---

[BOJ 11659: 区間和を求める 4](https://www.acmicpc.net/problem/11659)

配列の累積和配列を作る。`prefix[0] = 0`とし、`prefix[i + 1] = prefix[i] + value[i]`で定義する。入力の位置は1始まりで、両端を含む区間`[a, b]`の合計は`prefix[b] - prefix[a - 1]`となる。この式で累積和配列のインデックスを使うことで、入力位置とJavaの0始まり配列インデックスの違いを扱える。

累積和配列の構築には`O(N)`時間がかかり、`M`個の各クエリには`O(1)`時間で答えられる。したがって、全体の時間計算量は`O(N + M)`、空間計算量は`O(N)`である。`BufferedReader`と`StringTokenizer`を使い、空白や改行の位置に依存せずトークンを読み込み、`StringBuilder`に答えをまとめて出力する。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static BufferedReader reader =
      new BufferedReader(new InputStreamReader(System.in));
  private static StringTokenizer tokenizer;

  private static int nextInt() throws IOException {
    while (tokenizer == null || !tokenizer.hasMoreTokens()) {
      tokenizer = new StringTokenizer(reader.readLine());
    }
    return Integer.parseInt(tokenizer.nextToken());
  }

  public static void main(String[] args) throws IOException {
    int n = nextInt();
    int m = nextInt();
    int[] prefix = new int[n + 1];

    for (int i = 0; i < n; i++) {
      prefix[i + 1] = prefix[i] + nextInt();
    }

    StringBuilder output = new StringBuilder();
    for (int i = 0; i < m; i++) {
      int a = nextInt();
      int b = nextInt();
      output.append(prefix[b] - prefix[a - 1]).append('\n');
    }
    System.out.print(output);
  }
}
```
