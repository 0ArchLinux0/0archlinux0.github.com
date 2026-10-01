---
title: BOJ. 最小値と最大値 (2357)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Segment Tree, Data Structure, Maximum and Minimum Values, 최솟값과 최댓값]
pin: false
lang: ja
translation_key: boj-2357-range-min-max
permalink: /ja/posts/boj-2357-range-min-max/
source_permalink: /posts/BOJ-2357/
---

[BOJ 2357: 最小値と最大値](https://www.acmicpc.net/problem/2357)

## 反復型セグメントツリー

入力値は2つのフラットな配列のそれぞれで、葉 `n + i` に格納します。各内部ノードは2つの子をマージします。最小値ツリーには `min(left, right)`、最大値ツリーには `max(left, right)` を格納します。このマージ演算は結合的なので、区間を重ならない複数のツリー区間に分割し、その結果を任意の順序で結合できます。

1-based の両端を含むクエリ `[a, b]` は、0-based の半開区間 `[a - 1, b)` に変換し、ツリー上のインデックス `a - 1 + n` と `b + n` で表します。左右の端点を同時に上へ移動します。左端点が右の子ならそのノードを結果に加えて左端点を進め、右端点が奇数なら1つ左へ移動してそのノードを加えます。最小値の単位元 `Integer.MAX_VALUE` と最大値の単位元 `Integer.MIN_VALUE` は実際の結果を変えないため、負の値だけ、または正の値だけを含むクエリも正しく処理できます。

2つのツリーの構築には `O(N)` 時間、`O(N)` の記憶領域が必要です。各クエリは最大 `O(log N)` 階層を通り、選択した区間を各階層で定数時間で結合するため、クエリ時間は `O(log N)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int queryCount = input.nextInt();

        int[] minTree = new int[2 * n];
        int[] maxTree = new int[2 * n];
        for (int i = 0; i < n; i++) {
            int value = input.nextInt();
            minTree[n + i] = value;
            maxTree[n + i] = value;
        }

        for (int i = n - 1; i > 0; i--) {
            minTree[i] = Math.min(minTree[2 * i], minTree[2 * i + 1]);
            maxTree[i] = Math.max(maxTree[2 * i], maxTree[2 * i + 1]);
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            int left = input.nextInt() - 1 + n;
            int right = input.nextInt() + n;
            int minimum = Integer.MAX_VALUE;
            int maximum = Integer.MIN_VALUE;

            while (left < right) {
                if ((left & 1) == 1) {
                    minimum = Math.min(minimum, minTree[left]);
                    maximum = Math.max(maximum, maxTree[left]);
                    left++;
                }
                if ((right & 1) == 1) {
                    --right;
                    minimum = Math.min(minimum, minTree[right]);
                    maximum = Math.max(maximum, maxTree[right]);
                }
                left >>= 1;
                right >>= 1;
            }

            output.append(minimum).append(' ').append(maximum).append('\n');
        }
        System.out.print(output);
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
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
}
```
