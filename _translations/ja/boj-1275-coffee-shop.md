---
title: BOJ 1275 - コーヒーショップ2
author: MINJUN PARK
date: 2022-02-18 08:37:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, セグメント木, コーヒーショップ2]
pin: false
lang: ja
translation_key: boj-1275-coffee-shop
permalink: /ja/posts/boj-1275-coffee-shop/
source_permalink: /posts/BOJ-1275/
---

[問題: BOJ 1275 — コーヒーショップ2](https://www.acmicpc.net/problem/1275) · [English](/posts/BOJ-1275/) · [한국어](/ko/posts/boj-1275-coffee-shop/)

配列はクエリごとに変化します。各クエリでは `x` から `y` までの和を求め、その後、位置 `a` の値を `b` に代入します。反復型セグメント木では、配列の各値を葉に置き、内部ノードには左右の子ノードの和を保存します。

木の配列にはサイズ `2N` を使います。0始まりのインデックス `i` の葉は `N + i` に置き、各親ノードには二つの子ノードの和を格納します。この構造は `N` が2のべき乗でなくても、パディングなしで使えます。区間和を求める際は、まず両端を並べ替え、両端を含む区間を半開区間 `[left, right)` に変換します。二つの境界を木の上へ移動しながら、残りの区間に含まれる境界ノードを和に加えます。単一点の代入では、該当する葉を書き換え、祖先ノードの和を再計算します。

各クエリは先に和を求めてから更新を適用し、その更新は次のクエリを処理する前に完了します。合計が `int` の範囲を超える可能性があるため、木、入力値、和には `long` を使います。

木の構築には `O(N)` 時間がかかります。`Q` 個の各クエリでは区間和と一点更新をそれぞれ一度行い、各処理は `O(log N)` です。したがって全体の時間計算量は `O((N + Q) log N)`、空間計算量は `O(N)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] tree;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokens.nextToken());
        int queryCount = Integer.parseInt(tokens.nextToken());

        tree = new long[2 * n];
        tokens = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            tree[n + i] = Long.parseLong(tokens.nextToken());
        }
        for (int node = n - 1; node > 0; node--) {
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            tokens = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokens.nextToken());
            int y = Integer.parseInt(tokens.nextToken());
            int a = Integer.parseInt(tokens.nextToken()) - 1;
            long b = Long.parseLong(tokens.nextToken());

            int left = Math.min(x, y) - 1;
            int right = Math.max(x, y);
            output.append(rangeSum(left, right)).append('\n');
            assign(a, b);
        }
        System.out.print(output);
    }

    static long rangeSum(int left, int right) {
        long sum = 0;
        for (left += n, right += n; left < right; left /= 2, right /= 2) {
            if (left % 2 == 1) sum += tree[left++];
            if (right % 2 == 1) sum += tree[--right];
        }
        return sum;
    }

    static void assign(int position, long value) {
        int node = n + position;
        tree[node] = value;
        while (node > 1) {
            node /= 2;
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }
    }
}
```
