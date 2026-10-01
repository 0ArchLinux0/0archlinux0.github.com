---
title: BOJ 13511 - 木とクエリ 2
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 木, 最小共通祖先, LCA, 疎テーブル]
pin: false
lang: ja
translation_key: boj-13511-tree-query-2
permalink: /ja/posts/boj-13511-tree-query-2/
source_permalink: /posts/BOJ-13511/
---

[問題: BOJ 13511 — 木とクエリ 2](https://www.acmicpc.net/problem/13511) · [English](/posts/BOJ-13511/) · [한국어](/ko/posts/boj-13511-tree-query-2/)

頂点1を根とし、反復処理で木を探索します。各頂点の深さと親を記録します。根の親は根自身とすることで、祖先テーブルの値をすべて有効に保ちます。`up[v][j]` は `v` から辺を `2^j` 本たどって上がった祖先で、`weight[v][j]` はその辺の重みの合計です。`2^j` 本のジャンプを、連続する二つの `2^(j-1)` 本のジャンプに分けて両テーブルを構築します。

経路クエリでは、まず深い方の端点をもう一方と同じ深さまで上げます。その後、親が一致するまで大きなジャンプから両方の頂点を同時に上げ、最小共通祖先（LCA）を求めます。経路の距離は、各端点からLCAまで上がる二つの重みの合計を足して求めます。正の辺重みの合計は大きくなる可能性があるため、累積値には `long` を使います。

タイプ2のクエリでは、`upEdges` を `a` からLCAまでの辺数、`downEdges` をLCAから `b` までの辺数とします。経路上の頂点数は `upEdges + downEdges + 1` です。1始まりの `k` が `upEdges + 1` 以下なら、`a` から `k - 1` 本上がった頂点が答えです。それ以外の場合は、経路の後ろ側から数え、`b` から `upEdges + downEdges + 1 - k` 本上がります。この方法ならLCAと両端点も特別な経路処理なしで扱えます。

探索とテーブル構築はすべて反復処理なので、頂点100,000個が一直線につながった木でも呼び出しスタックを使い切りません。前処理は時間 `O(N log N)`、領域 `O(N log N)`、各クエリは `O(log N)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

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

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) return -1;
            }
            return buffer[pointer++];
        }
    }

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int[][] up;
    static long[][] weight;
    static int[] depth;

    static int lift(int vertex, int steps) {
        for (int j = 0; steps > 0; j++, steps >>= 1) {
            if ((steps & 1) != 0) vertex = up[vertex][j];
        }
        return vertex;
    }

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        a = lift(a, depth[a] - depth[b]);
        if (a == b) return a;

        for (int j = up[0].length - 1; j >= 0; j--) {
            if (up[a][j] != up[b][j]) {
                a = up[a][j];
                b = up[b][j];
            }
        }
        return up[a][0];
    }

    static long climbWeight(int vertex, int steps) {
        long total = 0;
        for (int j = 0; steps > 0; j++, steps >>= 1) {
            if ((steps & 1) != 0) {
                total += weight[vertex][j];
                vertex = up[vertex][j];
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int levels = 32 - Integer.numberOfLeadingZeros(n);
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int w = input.nextInt();
            graph[a].add(new Edge(b, w));
            graph[b].add(new Edge(a, w));
        }

        up = new int[n][levels];
        weight = new long[n][levels];
        depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        visited[0] = true;
        up[0][0] = 0;

        while (top > 0) {
            int vertex = stack[--top];
            for (Edge edge : graph[vertex]) {
                if (visited[edge.to]) continue;
                visited[edge.to] = true;
                up[edge.to][0] = vertex;
                weight[edge.to][0] = edge.weight;
                depth[edge.to] = depth[vertex] + 1;
                stack[top++] = edge.to;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                int middle = up[vertex][j - 1];
                up[vertex][j] = up[middle][j - 1];
                weight[vertex][j] = weight[vertex][j - 1] + weight[middle][j - 1];
            }
        }

        int queryCount = input.nextInt();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            int type = input.nextInt();
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int ancestor = lca(a, b);

            if (type == 1) {
                output.append(climbWeight(a, depth[a] - depth[ancestor])
                        + climbWeight(b, depth[b] - depth[ancestor])).append('\n');
            } else {
                int k = input.nextInt();
                int upEdges = depth[a] - depth[ancestor];
                int downEdges = depth[b] - depth[ancestor];
                int answer = k <= upEdges + 1
                        ? lift(a, k - 1)
                        : lift(b, upEdges + downEdges + 1 - k);
                output.append(answer + 1).append('\n');
            }
        }
        System.out.print(output);
    }
}
```
