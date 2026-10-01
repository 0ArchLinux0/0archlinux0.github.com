---
title: BOJ. 二つの溶液 (2470)
author: MINJUN PARK
date: 2022-01-27 22:56:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Two Liquid, 두 용액]
pin: false
lang: ja
translation_key: boj-2470-closest-to-zero-pair
permalink: /ja/posts/boj-2470-closest-to-zero-pair/
source_permalink: /posts/BOJ-2470/
---

[BOJ 2470: 二つの溶液](https://www.acmicpc.net/problem/2470) · [English](/posts/BOJ-2470/) · [한국어](/ko/posts/boj-2470-closest-to-zero-pair/)

異なる2つの溶液を選び、和の絶対値を最小にします。値をソートし、両端にポインターを置きます。各ステップでポインターが指す異なる2要素の和を候補として調べ、これまでの最小絶対値より小さければインデックスを保存します。和が負なら和を大きくするため左ポインターを右へ進め、正なら和を小さくするため右ポインターを左へ進めます。和が0になれば絶対値をこれ以上小さくできないので、直ちに終了します。

すべての値が同じ符号の場合も同じ規則でポインターを進め、可能な組を調べます。常に `left < right` なので、同じ要素を2回選ぶことはありません。加算前に値を `long` に拡張し、絶対値の比較も `long` の範囲で行うことで、大きな和のオーバーフローを防ぎます。ソートは `O(N log N)`、二ポインター走査は `O(N)` なので、全体の時間計算量は `O(N log N)`、ソートした配列のための空間計算量は `O(N)` です。

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
        Arrays.sort(values);

        int left = 0;
        int right = n - 1;
        int bestLeft = left;
        int bestRight = right;
        long bestAbs = Long.MAX_VALUE;

        while (left < right) {
            long sum = (long) values[left] + values[right];
            long absSum = Math.abs(sum);
            if (absSum < bestAbs) {
                bestAbs = absSum;
                bestLeft = left;
                bestRight = right;
            }

            if (sum == 0) {
                break;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(values[bestLeft] + " " + values[bestRight]);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = input.read();
            }
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return sign * value;
        }
    }
}
```

## JavaScript

```javascript
const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split(/\s+/).map(BigInt);
const n = Number(input[0]);
const values = input.slice(1, n + 1).sort((a, b) => (a < b ? -1 : a > b ? 1 : 0));

let left = 0;
let right = n - 1;
let bestLeft = left;
let bestRight = right;
let bestAbs = null;

while (left < right) {
    const sum = values[left] + values[right];
    const absSum = sum < 0n ? -sum : sum;
    if (bestAbs === null || absSum < bestAbs) {
        bestAbs = absSum;
        bestLeft = left;
        bestRight = right;
    }

    if (sum === 0n) {
        break;
    } else if (sum < 0n) {
        left++;
    } else {
        right--;
    }
}

console.log(`${values[bestLeft]} ${values[bestRight]}`);
```

JavaScriptでは `BigInt` を使い、加算と絶対値の比較を正確に行います。保存したインデックスはソート済み配列の前後の位置なので、出力されるのは順序どおりの2つの値であり、入力の異なる2要素です。
