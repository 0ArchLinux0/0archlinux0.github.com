---
title: BOJ. 連続する素数の和 (1644)
author: MINJUN PARK
date: 2022-01-26 07:51:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Consecutive prime sum,
    소수의 연속합,
    Review,
  ]
pin: false
lang: ja
translation_key: boj-1644-consecutive-prime-sum
permalink: /ja/posts/boj-1644-consecutive-prime-sum/
source_permalink: /posts/BOJ-1644/
---

[問題: BOJ 1644 — 連続する素数の和](https://www.acmicpc.net/problem/1644)

## エラトステネスのふるいとスライディングウィンドウ

まずエラトステネスのふるいで `N` 以下の素数をすべて求めます。次に、添字 `left` から `right` までの連続する素数の区間と、その合計を管理します。合計が `N` より小さい間は右端を伸ばします。合計が `N` 以上になったら、等しい場合は答えに加え、最も左の素数を区間から取り除きます。素数はすべて正なので、区間を伸ばすと合計は増加し、先頭の要素を除くと合計は減少します。そのため、両方のポインタは前にだけ進み、連続する素数の区間をそれぞれ一度ずつ調べれば十分です。

`N = 1` の場合、`N` 以下に素数は存在しないため答えは0です。素数がない入力も空のリストとして処理できるので、同様に0になります。入力の上限は4,000,000なので、各素数と個数は `int` に収まり、累積和には安全のため `long` を使います。

ふるいの時間計算量は `O(N log log N)`、空間計算量は `O(N)` です。素数の個数を `P` とすると、ウィンドウ走査は `O(P)` であり、上限の範囲では `O(N)` です。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        boolean[] composite = new boolean[n + 1];
        for (int p = 2; p <= n / p; p++) {
            if (!composite[p]) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    composite[multiple] = true;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int value = 2; value <= n; value++) {
            if (!composite[value]) {
                primes.add(value);
            }
        }

        int left = 0;
        long sum = 0;
        int count = 0;
        for (int right = 0; right < primes.size(); right++) {
            sum += primes.get(right);
            while (sum >= n) {
                if (sum == n) {
                    count++;
                }
                sum -= primes.get(left++);
            }
        }

        System.out.println(count);
    }
}
```
