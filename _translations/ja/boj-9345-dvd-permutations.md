---
title: BOJ. デジタルビデオディスク (9345)
author: MINJUN PARK
date: 2022-01-20 00:38:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Segment Tree,
    Data Structure,
    Digital Video Disk,
    디지털 비디오 디스크(DVDs),
		Review
  ]
pin: false
lang: ja
translation_key: boj-9345-dvd-permutations
permalink: /ja/posts/boj-9345-dvd-permutations/
source_permalink: /posts/BOJ-9345/
---

[問題リンク](https://www.acmicpc.net/problem/9345) · [English](/posts/BOJ-9345/) · [한국어](/ko/posts/boj-9345-dvd-permutations/)

## DVD の順列を扱う反復型セグメント木

配列は最初 `0, 1, ..., N - 1` の順列です。タイプ0の操作では2つの位置の値を交換し、タイプ1の操作では現在の区間 `[A, B]` に番号 `A` から `B` までの DVD がちょうど含まれるかを調べます。

配列が順列であるため、この条件は `min(A..B) == A` かつ `max(A..B) == B` と同値です。区間には `B - A + 1` 個の異なる値があり、すべて `[A, B]` 内です。最小値と最大値が両端の値なら、その間の値もすべて含まれます。逆に必要な DVD がすべてあれば、最小値と最大値はそれぞれ両端の値です。

反復型セグメント木は各区間の最小値と最大値を保持します。`size` は `N` 以上となる最小の2のべき乗で、位置 `i` に対応する葉は `size + i` です。各内部ノードには2つの子の最小値と最大値を格納します。1点を更新すると葉を置き換えて祖先を再計算するため、2位置を交換した後に両方の葉を更新します。範囲クエリでは半開区間 `[A, B + 1)` に対応する葉から上へ進み、対象ノードだけを結合します。配列末尾の空き葉は最小値に `Integer.MAX_VALUE`、最大値に `Integer.MIN_VALUE` を設定し、クエリ結果に影響しないようにします。

点更新と範囲クエリはいずれも `O(log N)` 時間、木と順列は `O(N)` 空間です。`N = 1` の場合も、1つの葉で `[0, 0]` のクエリを正しく処理できます。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int operations = input.nextInt();
            int[] dvdAt = new int[n];
            for (int i = 0; i < n; i++) {
                dvdAt[i] = i;
            }

            int size = 1;
            while (size < n) {
                size <<= 1;
            }
            int[] minTree = new int[size << 1];
            int[] maxTree = new int[size << 1];
            for (int i = 0; i < size; i++) {
                int value = i < n ? dvdAt[i] : Integer.MAX_VALUE;
                minTree[size + i] = value;
                maxTree[size + i] = i < n ? dvdAt[i] : Integer.MIN_VALUE;
            }
            for (int node = size - 1; node > 0; node--) {
                minTree[node] = Math.min(minTree[node << 1], minTree[node << 1 | 1]);
                maxTree[node] = Math.max(maxTree[node << 1], maxTree[node << 1 | 1]);
            }

            for (int operation = 0; operation < operations; operation++) {
                int type = input.nextInt();
                int a = input.nextInt();
                int b = input.nextInt();
                if (type == 0) {
                    int valueA = dvdAt[a];
                    int valueB = dvdAt[b];
                    dvdAt[a] = valueB;
                    dvdAt[b] = valueA;
                    update(minTree, maxTree, size, a, valueB);
                    update(minTree, maxTree, size, b, valueA);
                } else {
                    int minimum = Integer.MAX_VALUE;
                    int maximum = Integer.MIN_VALUE;
                    int left = size + a;
                    int right = size + b + 1;
                    while (left < right) {
                        if ((left & 1) != 0) {
                            minimum = Math.min(minimum, minTree[left]);
                            maximum = Math.max(maximum, maxTree[left]);
                            left++;
                        }
                        if ((right & 1) != 0) {
                            --right;
                            minimum = Math.min(minimum, minTree[right]);
                            maximum = Math.max(maximum, maxTree[right]);
                        }
                        left >>= 1;
                        right >>= 1;
                    }
                    output.append(minimum == a && maximum == b ? "YES\n" : "NO\n");
                }
            }
        }

        System.out.print(output);
    }

    private static void update(int[] minTree, int[] maxTree, int size, int position, int value) {
        int node = size + position;
        minTree[node] = value;
        maxTree[node] = value;
        for (node >>= 1; node > 0; node >>= 1) {
            minTree[node] = Math.min(minTree[node << 1], minTree[node << 1 | 1]);
            maxTree[node] = Math.max(maxTree[node << 1], maxTree[node << 1 | 1]);
        }
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```
