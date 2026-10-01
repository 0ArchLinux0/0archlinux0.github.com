---
title: BOJ. 木の親を探す (11725)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, 木の親を探す]
pin: false
lang: ja
translation_key: boj-11725-tree-parents
permalink: /ja/posts/boj-11725-tree-parents/
source_permalink: /posts/BOJ-11725/
---

[BOJ 11725: 木の親を探す](https://www.acmicpc.net/problem/11725)

## 探索で木に根を設定する

入力された無向辺を隣接リストに格納し、頂点 `1` を根とします。幅優先探索のキューに `1` を入れて探索すると、現在の頂点から初めて発見した隣接頂点は現在の頂点の子です。この関係は隣接頂点をキューから取り出すときではなく、キューに追加するときに記録します。発見と同時に訪問済みにすることで、別の辺が同じ頂点の親を再設定することを防ぎます。根も探索前に訪問済みにし、出力する親はありません。

木では根から各頂点への経路が一つだけなので、頂点に最初に到達した辺が唯一の親辺です。探索後、頂点 `2` から `N` まで親を番号順に出力します。キューには整数配列を使うため再帰呼び出しはなく、深さが最大になる一直線の木も安全に処理できます。

辺は `N - 1` 本です。無向隣接リストを作成し、すべての頂点と辺を走査するため、時間計算量は `O(N)` です。隣接リスト、親配列、キューに必要な追加領域も `O(N)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int node = 1; node <= n; node++) {
            graph[node] = new ArrayList<>();
        }

        for (int edge = 0; edge < n - 1; edge++) {
            int a = input.nextInt();
            int b = input.nextInt();
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n + 1];
        int[] queue = new int[n];
        int head = 0;
        int tail = 0;
        parent[1] = 1;
        queue[tail++] = 1;

        while (head < tail) {
            int current = queue[head++];
            for (int neighbor : graph[current]) {
                if (parent[neighbor] != 0) continue;
                parent[neighbor] = current;
                queue[tail++] = neighbor;
            }
        }

        StringBuilder output = new StringBuilder();
        for (int node = 2; node <= n; node++) {
            output.append(parent[node]).append('\n');
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
