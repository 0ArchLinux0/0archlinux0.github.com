---
title: BOJ 11438 - LCA 2
author: MINJUN PARK
date: 2022-02-09 05:28:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, BOJ, Tree, Lowest Common Ancestor, LCA, Sparse Table]
pin: false
lang: ja
translation_key: boj-11438-lca-binary-lifting
permalink: /ja/posts/boj-11438-lca-binary-lifting/
source_permalink: /posts/BOJ-11438/
---

[問題: BOJ 11438 — LCA 2](https://www.acmicpc.net/problem/11438) · [English](/posts/BOJ-11438/) · [한국어](/ko/posts/boj-11438-lca-binary-lifting/)

頂点1を根とし、各頂点 `v` の深さと `up[v][j]` を記録します。`up[v][j]` は `v` から辺を `2^j` 本たどって上がった祖先です。根はすべての段階で自分自身を祖先とします。この規則により祖先テーブルの値は常に有効になり、根でのジャンプも安全です。

祖先テーブルは `up[v][0] = parent[v]` および `up[v][j] = up[up[v][j - 1]][j - 1]` で構築します。列数は `N` から求めます。正の `N` に対して `32 - Integer.numberOfLeadingZeros(N)` 列あれば、取り得るすべての深さを扱えます。

`a` と `b` のLCAを求めるには、まず `a` の方が深くなるように入れ替えます。深さの差をビットごとに分解し、該当する分だけ `a` を上へ移動して両頂点の深さをそろえます。この時点で一致すれば、その頂点がLCAです。一致しない場合は、大きなジャンプから順に調べ、`2^j` 個上の祖先が異なるときに両頂点を同時に上げます。この間、両頂点は共通祖先より下にとどまり、最後にそれぞれの直上にある親がLCAになります。

最初の木の探索には再帰ではなく明示的なスタックを使うため、頂点100,000個が一直線につながった木でもJavaの呼び出しスタックを使い切りません。前処理は時間・空間ともに `O(N log N)`、各クエリは `O(log N)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            String[] edge = input.readLine().split(" ");
            int a = Integer.parseInt(edge[0]) - 1;
            int b = Integer.parseInt(edge[1]) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        int levels = 32 - Integer.numberOfLeadingZeros(n);
        int[][] up = new int[n][levels];
        int[] depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        visited[0] = true;
        up[0][0] = 0;

        while (top > 0) {
            int vertex = stack[--top];
            for (int neighbor : graph[vertex]) {
                if (visited[neighbor]) {
                    continue;
                }
                visited[neighbor] = true;
                up[neighbor][0] = vertex;
                depth[neighbor] = depth[vertex] + 1;
                stack[top++] = neighbor;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                up[vertex][j] = up[up[vertex][j - 1]][j - 1];
            }
        }

        int queryCount = Integer.parseInt(input.readLine());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            String[] query = input.readLine().split(" ");
            int a = Integer.parseInt(query[0]) - 1;
            int b = Integer.parseInt(query[1]) - 1;
            output.append(lca(a, b, depth, up)).append('\n');
        }
        System.out.print(output);
    }

    private static int lca(int a, int b, int[] depth, int[][] up) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int difference = depth[a] - depth[b];
        for (int j = 0; j < up[0].length; j++) {
            if ((difference & (1 << j)) != 0) {
                a = up[a][j];
            }
        }
        if (a == b) {
            return a + 1;
        }

        for (int j = up[0].length - 1; j >= 0; j--) {
            if (up[a][j] != up[b][j]) {
                a = up[a][j];
                b = up[b][j];
            }
        }
        return up[a][0] + 1;
    }
}
```
