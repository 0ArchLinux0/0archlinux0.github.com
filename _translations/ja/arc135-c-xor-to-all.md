---
title: AtCoder ARC 135 C - XOR to All
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ARC, XOR]
pin: false
lang: ja
translation_key: arc135-c-xor-to-all
permalink: /ja/posts/arc135-c-xor-to-all/
source_permalink: /posts/Atcoder-C-XOR-to-All/
---

[問題: AtCoder ARC 135 C — XOR to All](https://atcoder.jp/contests/arc135/tasks/arc135_c) · [English](/posts/Atcoder-C-XOR-to-All/) · [한국어](/ko/posts/arc135-c-xor-to-all/)

各添字 `i` について `sum_j (A[i] XOR A[j])` を計算し、すべての `i` の中から最大値を求めます。ビット位置 `b` ごとに考えます。`A[i]` の `b` ビット目が 0 なら、XOR の結果でそのビットが 1 になるのは、同じビットが 1 である `count[b]` 個です。一方、そのビットが 1 なら、残りの `N - count[b]` 個で XOR のビットが 1 になります。このビットの寄与は `2^b` と該当する個数の積であり、全ビットの寄与を合計すると `i` に対する和になります。

値の上限は `10^8` なので、ビット位置 0 から 29 までの 30 ビットですべての値を表せます。入力値とビットごとの個数を一度保存し、各値について 30 ビットを調べます。合計と最大値は `long` で扱い、ビットの重みも `1L << b` とすることで、乗算前に `int` がオーバーフローするのを防ぎます。最大値を 0 で初期化すれば、すべての値が 0 の場合も正しく処理できます。時間計算量は `B = 30` として `O(N * B)`、保存した入力配列を除く追加領域は `O(B)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int BITS = 30;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        String[] tokens = input.readLine().split(" ");
        int[] values = new int[n];
        int[] bitCount = new int[BITS];

        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(tokens[i]);
            for (int bit = 0; bit < BITS; bit++) {
                if ((values[i] & (1 << bit)) != 0) {
                    bitCount[bit]++;
                }
            }
        }

        long answer = 0;
        for (int value : values) {
            long sum = 0;
            for (int bit = 0; bit < BITS; bit++) {
                int ones = (value & (1 << bit)) == 0
                        ? bitCount[bit]
                        : n - bitCount[bit];
                sum += (1L << bit) * ones;
            }
            answer = Math.max(answer, sum);
        }

        System.out.println(answer);
    }
}
```
