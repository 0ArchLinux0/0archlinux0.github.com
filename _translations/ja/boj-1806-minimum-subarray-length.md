---
title: BOJ. 部分和 (1806)
author: MINJUN PARK
date: 2022-01-27 03:45:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    JavaScript,
    Algorithm,
    Coding Interview,
    Two Pointer,
    BOJ,
    Subsequence sum,
    部分和,
    Review
  ]
pin: false
lang: ja
translation_key: boj-1806-minimum-subarray-length
permalink: /ja/posts/boj-1806-minimum-subarray-length/
source_permalink: /posts/BOJ-1806/
---

[問題: BOJ 1806 — 部分和](https://www.acmicpc.net/problem/1806)

すべての数が正なので、右端の排他的境界を右へ動かすと区間和は増加し、左端を右へ動かすと区間和は減少します。排他的な右端を1つずつ拡張し、合計が `S` 以上になったらその区間を答えの候補として記録します。その後、合計が `S` 以上である間は左端を進めます。同じ位置で終わるより長い区間が答えをより小さくすることはありません。区間は `[left, right)` と表すため、長さは正確に `right - left` です。長さ1の区間や配列の先頭・末尾を含む区間も、特別扱いせず正しく計算できます。条件を満たす区間がなければ答えは `0` です。両端は前方向にしか動かないため、時間計算量は `O(N)`、入力配列を保存する空間計算量は `O(N)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long target = input.nextLong();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        int minLength = n + 1;
        int left = 0;
        long sum = 0;
        int right = 0;
        while (right < n) {
            sum += values[right++];
            while (sum >= target) {
                minLength = Math.min(minLength, right - left);
                sum -= values[left++];
            }
        }

        System.out.println(minLength == n + 1 ? 0 : minLength);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```

```javascript
const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split(/\s+/).map(Number);
const n = input[0];
const target = input[1];
const values = input.slice(2);

let minLength = n + 1;
let left = 0;
let sum = 0;
let right = 0;
while (right < n) {
  sum += values[right++];
  while (sum >= target) {
    minLength = Math.min(minLength, right - left);
    sum -= values[left++];
  }
}

console.log(minLength === n + 1 ? 0 : minLength);
```
