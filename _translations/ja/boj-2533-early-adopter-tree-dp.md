---
title: BOJ. 社会網サービス (SNS) (2533)
author: MINJUN PARK
date: 2022-02-01 13:16:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Graph, Dynamic Programming, 社会網サービス(SNS)]
pin: false
lang: ja
translation_key: boj-2533-early-adopter-tree-dp
permalink: /ja/posts/boj-2533-early-adopter-tree-dp/
source_permalink: /posts/BOJ-2533/
---

[問題: BOJ 2533 — 社会網サービス(SNS)](https://www.acmicpc.net/problem/2533)

[English](/posts/BOJ-2533/) · [한국어](/ko/posts/boj-2533-early-adopter-tree-dp/)

## 解法

頂点 `0` を根として、反復処理で根優先の順序を作ります。この順序を逆順に処理すると、すべての子を先に計算できるため、再帰呼び出しは不要です。そのため、最大 `10^6` 頂点の一本鎖の木でも呼び出しスタックがあふれません。

各頂点 `u` について、`notAdopter[u]` は `u` がアーリーアダプターでない場合に、部分木に必要なアーリーアダプターの最小数です。この場合、すべての子がアーリーアダプターでなければならないため、子の `adopter` 状態を合計します。`adopter[u]` は `u` がアーリーアダプターである場合の最小数です。このとき各子は二つの状態のうち小さい方を選べるため、その合計に `u` 自身を加えます。したがって、葉の状態は `(0, 1)` です。頂点が一つだけの木も同じように処理でき、答えは `min(0, 1) = 0` です。

隣接リスト、巡回順序、二つのDP状態はプリミティブ配列に格納します。各頂点と辺を定数回処理するため、時間計算量は `O(N)`、空間計算量は `O(N)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] head = new int[n];
        int[] to = new int[2 * (n - 1)];
        int[] next = new int[2 * (n - 1)];
        java.util.Arrays.fill(head, -1);

        int edgeCount = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            to[edgeCount] = b;
            next[edgeCount] = head[a];
            head[a] = edgeCount++;
            to[edgeCount] = a;
            next[edgeCount] = head[b];
            head[b] = edgeCount++;
        }

        int[] parent = new int[n];
        int[] order = new int[n];
        int size = 1;
        order[0] = 0;
        parent[0] = -1;

        // Build a root-first order without recursion.
        for (int i = 0; i < size; i++) {
            int node = order[i];
            for (int edge = head[node]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (child == parent[node]) {
                    continue;
                }
                parent[child] = node;
                order[size++] = child;
            }
        }

        int[] notAdopter = new int[n];
        int[] adopter = new int[n];
        for (int i = size - 1; i >= 0; i--) {
            int node = order[i];
            adopter[node] = 1;
            for (int edge = head[node]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (child == parent[node]) {
                    continue;
                }
                notAdopter[node] += adopter[child];
                adopter[node] += Math.min(notAdopter[child], adopter[child]);
            }
        }

        System.out.println(Math.min(notAdopter[0], adopter[0]));
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```
