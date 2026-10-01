---
title: Codeforces Global Round 19 A — Sorting Parts
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, Codeforces, Codeforces Global Round 19, Sorting Parts]
pin: false
lang: ja
translation_key: cf-1637a-sorting-parts
permalink: /ja/posts/cf-1637a-sorting-parts/
source_permalink: /posts/Codeforces-Global-Round-19-A.-Sorting-Parts/
---

[問題: Codeforces 1637A — Sorting Parts](https://codeforces.com/contest/1637/problem/A) · [English](/posts/Codeforces-Global-Round-19-A.-Sorting-Parts/) · [한국어](/ko/posts/cf-1637a-sorting-parts/)

各テストケースでは、`1 <= k < n` を満たす分割位置 `k` を選びます。接頭部分 `a[1..k]` と接尾部分 `a[k+1..n]` をそれぞれ独立にソートします。この操作後、配列全体が非減少順にならないような有効な分割が存在するかを判定します。任意の部分区間を 1 つ選ぶ操作ではなく、分割の両側をソートする点に注意してください。

元の配列がすでに非減少順なら、どちらの部分をソートしても要素の順序は変わらず、両部分の境界も順序どおりです。したがって、どの分割を選んでも結果はソート済みなので `NO` を出力します。

配列がソートされていなければ、隣接する逆転 `a[i] > a[i + 1]` が少なくとも 1 つあります。`k = i` と選ぶと、その逆転は分割の境界に位置します。接頭部分をソートした後の末尾は接頭部分の最大値なので `a[i]` 以上です。接尾部分をソートした後の先頭は接尾部分の最小値なので `a[i + 1]` 以下です。よって境界では依然として左側の値が右側より大きく、配列全体はソートされません。したがって `YES` を出力します。逆転が 1 つだけの場合もこの証明はそのまま成り立ち、同じ値が繰り返されていても厳密な `>` のみを逆転とみなします。

問題の制約は `n >= 2` なので、有効な分割は必ず存在します。制約外の `n = 1` について考えると、有効な分割が存在しないため答えは `NO` です。コードも隣接する要素の組がないため `NO` を返します。

隣接する組をそれぞれ 1 回確認するため、時間計算量は `O(n)` です。入力配列に `O(n)` の領域を使い、配列以外の判定処理は `O(1)` の追加領域で行います。プログラムはテストケース数を読み取り、各ケースの `n` と配列を処理して、各ケースにつき大文字の `YES` または `NO` を 1 行に出力します。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int n = input.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = input.nextInt();

            boolean hasInversion = false;
            for (int i = 0; i + 1 < n; i++) {
                if (a[i] > a[i + 1]) {
                    hasInversion = true;
                    break;
                }
            }

            output.append(hasInversion ? "YES\n" : "NO\n");
        }

        System.out.print(output);
    }

    static class FastScanner {
        private final BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        int nextInt() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokenizer.nextToken());
        }
    }
}
```
