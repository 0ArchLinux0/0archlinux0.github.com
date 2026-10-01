---
title: BOJ 3176 - 道路ネットワーク
author: MINJUN PARK
date: 2022-02-09 08:03:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 木, 疎テーブル, 最小共通祖先, LCA, 道路ネットワーク]
pin: false
lang: ja
translation_key: boj-3176-road-network
permalink: /ja/posts/boj-3176-road-network/
source_permalink: /posts/BOJ-3176/
---

[問題: BOJ 3176 — 道路ネットワーク](https://www.acmicpc.net/problem/3176) · [English](/posts/BOJ-3176/) · [한국어](/ko/posts/boj-3176-road-network/)

頂点1を根とし、明示的なスタックで木を走査して、各頂点の深さ、直近の親、1つ上へ登る辺の最小・最大重みを記録します。その後、`2^j` 個分のジャンプを2つの `2^(j-1)` 個分のジャンプに分け、祖先・最小値・最大値のテーブルを構築します。根の祖先は根自身とし、根の空の経路には中立となる極値を設定します。これらは実際の経路の答えに影響しません。

経路クエリでは、まず深い方の端点を同じ深さまで上げ、その間に通った辺の最小値と最大値を答えに取り込みます。次に大きなジャンプから順に、両端点の祖先が異なる場合は両方を同時に上げ、通過した両側の区間の極値を反映します。最後に1辺ずつ上がると、最小共通祖先で合流します。両端点が同じ頂点なら経路に辺はないため、この実装では中立値 `0 0` を出力します（元の制約では通常、異なる頂点が指定されます）。

根からの走査は反復処理なので、頂点100,000個が一直線につながった木でも呼び出しスタックを使い切りません。前処理の時間・領域計算量は `O(N log N)`、各経路クエリは `O(log N)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int limit;

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
            if (position == limit) {
                limit = input.read(buffer);
                position = 0;
                if (limit == -1) return -1;
            }
            return buffer[position++];
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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int levels = 32 - Integer.numberOfLeadingZeros(n);
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int vertex = 0; vertex < n; vertex++) graph[vertex] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int weight = input.nextInt();
            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }

        int[][] up = new int[n][levels];
        int[][] minEdge = new int[n][levels];
        int[][] maxEdge = new int[n][levels];
        int[] depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int size = 0;
        stack[size++] = 0;
        visited[0] = true;
        up[0][0] = 0;
        minEdge[0][0] = Integer.MAX_VALUE;
        maxEdge[0][0] = Integer.MIN_VALUE;

        while (size > 0) {
            int vertex = stack[--size];
            for (Edge edge : graph[vertex]) {
                if (visited[edge.to]) continue;
                visited[edge.to] = true;
                up[edge.to][0] = vertex;
                minEdge[edge.to][0] = edge.weight;
                maxEdge[edge.to][0] = edge.weight;
                depth[edge.to] = depth[vertex] + 1;
                stack[size++] = edge.to;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                int middle = up[vertex][j - 1];
                up[vertex][j] = up[middle][j - 1];
                minEdge[vertex][j] = Math.min(minEdge[vertex][j - 1], minEdge[middle][j - 1]);
                maxEdge[vertex][j] = Math.max(maxEdge[vertex][j - 1], maxEdge[middle][j - 1]);
            }
        }

        int queryCount = input.nextInt();
        StringBuilder output = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            if (a == b) {
                output.append("0 0\n");
                continue;
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            if (depth[a] < depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int difference = depth[a] - depth[b];
            for (int j = levels - 1; j >= 0; j--) {
                if ((difference & (1 << j)) != 0) {
                    min = Math.min(min, minEdge[a][j]);
                    max = Math.max(max, maxEdge[a][j]);
                    a = up[a][j];
                }
            }

            if (a != b) {
                for (int j = levels - 1; j >= 0; j--) {
                    if (up[a][j] != up[b][j]) {
                        min = Math.min(min, Math.min(minEdge[a][j], minEdge[b][j]));
                        max = Math.max(max, Math.max(maxEdge[a][j], maxEdge[b][j]));
                        a = up[a][j];
                        b = up[b][j];
                    }
                }
                min = Math.min(min, Math.min(minEdge[a][0], minEdge[b][0]));
                max = Math.max(max, Math.max(maxEdge[a][0], maxEdge[b][0]));
            }
            output.append(min).append(' ').append(max).append('\n');
        }
        System.out.print(output);
    }
}
```
