---
title: BOJ 14002 — 最長増加部分列 4
author: MINJUN PARK
date: 2022-01-11 18:36:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, LIS, Longest Increasing Subsequence(4), 最長増加部分列]
pin: false
lang: ja
translation_key: boj-14002-lis-sequence
permalink: /ja/posts/boj-14002-lis-sequence/
source_permalink: /posts/BOJ-14002/
---

[問題リンク](https://www.acmicpc.net/problem/14002)

各入力値について、`tails[length]` にその長さの増加部分列が取り得る最小の末尾値を保持し、その末尾値を与えた入力インデックスも保存します。現在の値以上となる最初の末尾値（`lower_bound`）を二分探索し、その位置を現在の値で置き換えます。tails 配列そのものが実際の部分列とは限りません。実際の列を復元するためにインデックスを記録します。

最初の末尾値 `>= value` を探すことで、部分列を厳密な増加に保てます。既存の末尾値と等しい値は列を延長せず、その位置を置き換えるためです。現在のインデックスの直前のインデックスには、tails を更新する前に取得した一つ短い長さの末尾インデックスを設定します。最長の末尾インデックスから predecessor のリンクをたどると選ばれた列を逆順に得られ、これを反転すれば答えになります。

各値について二分探索と更新を行うため、時間計算量は `O(N log N)` です。入力、tails、末尾インデックス、predecessor、復元した答えはいずれも `O(N)` の空間を使います。要素が1つなら長さ1の列になり、重複値や減少する値によって列の長さが誤って増えることはありません。

```java
import java.io.*;

public class Main {
    private static final class FastScanner {
        private final InputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] values = new int[n];
        int[] tails = new int[n];
        int[] tailIndices = new int[n];
        int[] predecessor = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
            predecessor[i] = -1;
        }

        int length = 0;
        for (int i = 0; i < n; i++) {
            int value = values[i];
            int left = 0;
            int right = length;
            while (left < right) {
                int middle = left + (right - left) / 2;
                if (tails[middle] >= value) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }

            int position = left;
            if (position > 0) predecessor[i] = tailIndices[position - 1];
            tails[position] = value;
            tailIndices[position] = i;
            if (position == length) length++;
        }

        int[] answer = new int[length];
        int index = tailIndices[length - 1];
        for (int i = length - 1; i >= 0; i--) {
            answer[i] = values[index];
            index = predecessor[index];
        }

        StringBuilder output = new StringBuilder();
        output.append(length).append('\n');
        for (int value : answer) output.append(value).append(' ');
        System.out.println(output);
    }
}
```
