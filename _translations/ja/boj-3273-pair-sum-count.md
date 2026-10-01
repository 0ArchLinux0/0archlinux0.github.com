---
title: BOJ 3273 - 2つの数の和
author: MINJUN PARK
date: 2022-01-27 07:08:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 2つの数の和, two pointers]
pin: false
lang: ja
translation_key: boj-3273-pair-sum-count
permalink: /ja/posts/boj-3273-pair-sum-count/
source_permalink: /posts/BOJ-3273/
---

[問題: BOJ 3273 — 2つの数の和](https://www.acmicpc.net/problem/3273)

`N`個の互いに異なる正の整数と目標値`X`が与えられます（`1 ≤ N ≤ 100,000`、各数は最大`1,000,000`）。和が`X`になる、異なる2つの入力要素からなる順序を区別しないペアの数を求めます。

## ソートとツーポインタ

数をソートし、未確認の範囲の最小値と最大値に2つのポインタを置きます。2つの数の和が`X`より小さい場合、左の数は残りのどの数と組み合わせても`X`にならないため、左ポインタを右へ進めます。和が`X`より大きい場合、右の数は残りのどの数と組み合わせても大きすぎるため、右ポインタを左へ進めます。

和が`X`と一致したらペア数を1増やし、両方のポインタを内側へ進めます。両端の要素を消費することで、同じペアを重複して数えません。ポインタが交差するまで候補を調べ、一致するペアがなければ個数は`0`のままです。最小の入力`N = 1`でもポインタは最初から同じ位置なので、ペアは数えられません。

和はオーバーフローを避けるため`long`で計算します。ソートに`O(N log N)`、ポインタ走査に`O(N)`かかるため、全体の時間計算量は`O(N log N)`、入力配列を含む空間計算量は`O(N)`です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }
        int target = input.nextInt();

        Arrays.sort(values);
        int left = 0;
        int right = n - 1;
        int count = 0;
        while (left < right) {
            long sum = (long) values[left] + values[right];
            if (sum == target) {
                count++;
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(count);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
