---
title: BOJ. 集合の表現 (1717)
author: MINJUN PARK
date: 2021-12-29 21:08:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Union Find, Expression of Set, 集合の表現]
pin: false
lang: ja
translation_key: boj-1717-disjoint-set
permalink: /ja/posts/boj-1717-disjoint-set/
---

素集合データ構造（DSU）は、`0`から`n`までの整数を複数の集合に分けて管理します。各集合は根で表され、`find(x)`は要素`x`が属する集合の代表元を返します。併合操作は2つの集合を1つにまとめ、連結性の問い合わせでは2つの要素の代表元が同じかを調べます。

`parent`配列は森を構成します。自分自身を親として指す要素が根であり、同じ集合の各要素は親リンクをたどって代表元に到達します。`find`は反復型のパスハルビングを行い、訪問したノードの親を祖父母に付け替えます。`union`は小さい木を大きい木の下に接続します。経路圧縮とサイズによる併合を組み合わせると、1操作あたりの償却時間は逆アッカーマン関数`α(N)`に対して`O(α(N))`、空間計算量は`O(N)`です。

操作種別`0`では`a`と`b`が属する集合を併合し、それ以外の種別では2つの要素が連結しているかを問い合わせます。入力は空白区切りの整数として読み込むため、改行や空白の配置に依存しません。

[問題リンク](https://www.acmicpc.net/problem/1717)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();

        DisjointSet sets = new DisjointSet(n + 1);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int type = input.nextInt();
            int a = input.nextInt();
            int b = input.nextInt();
            if (type == 0) {
                sets.union(a, b);
            } else {
                output.append(sets.find(a) == sets.find(b) ? "YES" : "NO").append('\n');
            }
        }
        System.out.print(output);
    }

    private static final class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int count) {
            parent = new int[count];
            size = new int[count];
            for (int i = 0; i < count; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int element) {
            while (element != parent[element]) {
                parent[element] = parent[parent[element]];
                element = parent[element];
            }
            return element;
        }

        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return;
            }
            if (size[rootA] < size[rootB]) {
                int temporary = rootA;
                rootA = rootB;
                rootB = temporary;
            }
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
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

        int nextInt() throws IOException {
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
