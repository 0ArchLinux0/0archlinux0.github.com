---
title: BOJ 2213 - 木の独立集合
author: MINJUN PARK
date: 2022-02-02 13:02:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 木, 動的計画法, 独立集合]
pin: false
lang: ja
translation_key: boj-2213-tree-independent-set
permalink: /ja/posts/boj-2213-tree-independent-set/
source_permalink: /posts/BOJ-2213/
---

[問題: BOJ 2213 — 木の独立集合](https://www.acmicpc.net/problem/2213) · [English](/posts/BOJ-2213/) · [한국어](/ko/posts/boj-2213-tree-independent-set/)

頂点 1 を根として木を根付き木にします。各頂点 `v` について、`in[v]` は `v` を含む `v` の部分木の独立集合の最大重み、`out[v]` は `v` を含まない場合の最大重みです。頂点の正の重みを `w[v]` とすると、次のようになります。

- `in[v] = w[v] + sum(out[child])`: `v` を選ぶと、隣接する子はすべて選べません。
- `out[v] = sum(max(in[child], out[child]))`: `v` を選ばない場合、各子について選ぶ場合と選ばない場合の良い方を選べます。

木では子の部分木同士を結ぶ辺がないため、各部分木の最適な選択を組み合わせても独立集合の条件を保てます。親配列と根から始まる頂点順序を反復処理で作り、その順序を逆向きに処理すれば、親より先に子の DP 値を計算できます。再帰を使わないため、100,000 個の頂点が一直線につながる木でも呼び出しスタックを使い切りません。

復元では根から木をたどります。親を選んだ場合、現在の頂点は必ず選びません。親を選んでいない場合は `in[v] > out[v]` のときだけ現在の頂点を選び、同値なら選びません。根にも同じ規則を適用します。同値の場合はどちらを選んでも最適解なので、この規則は出力を決定的にするためのものです。最後に選んだ頂点番号をソートして出力します。1 行目には根の 2 つの DP 値の大きい方、つまり最大重みを出力し、2 行目にはその最大値を実現する独立集合を 1 つ出力します。

各頂点と辺を定数回処理するため、時間計算量と空間計算量はいずれも `O(N)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        int[] weight = new int[n + 1];
        StringTokenizer weights = new StringTokenizer(input.readLine());
        for (int vertex = 1; vertex <= n; vertex++) {
            weight[vertex] = Integer.parseInt(weights.nextToken());
        }

        List<Integer>[] graph = new ArrayList[n + 1];
        for (int vertex = 1; vertex <= n; vertex++) {
            graph[vertex] = new ArrayList<>();
        }
        for (int edge = 0; edge < n - 1; edge++) {
            StringTokenizer tokens = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n + 1];
        int[] order = new int[n];
        int size = 0;
        order[size++] = 1;
        parent[1] = -1;
        for (int index = 0; index < size; index++) {
            int vertex = order[index];
            for (int next : graph[vertex]) {
                if (next == parent[vertex]) {
                    continue;
                }
                parent[next] = vertex;
                order[size++] = next;
            }
        }

        long[] in = new long[n + 1];
        long[] out = new long[n + 1];
        for (int index = n - 1; index >= 0; index--) {
            int vertex = order[index];
            in[vertex] = weight[vertex];
            for (int next : graph[vertex]) {
                if (parent[next] == vertex) {
                    in[vertex] += out[next];
                    out[vertex] += Math.max(in[next], out[next]);
                }
            }
        }

        boolean[] selected = new boolean[n + 1];
        int[] traversal = new int[n];
        int top = 0;
        traversal[top++] = 1;
        long optimum = Math.max(in[1], out[1]);
        while (top > 0) {
            int vertex = traversal[--top];
            int p = parent[vertex];
            selected[vertex] = p == -1
                    ? in[vertex] > out[vertex]
                    : !selected[p] && in[vertex] > out[vertex];
            for (int next : graph[vertex]) {
                if (parent[next] == vertex) {
                    traversal[top++] = next;
                }
            }
        }

        List<Integer> answer = new ArrayList<>();
        for (int vertex = 1; vertex <= n; vertex++) {
            if (selected[vertex]) {
                answer.add(vertex);
            }
        }
        Collections.sort(answer);

        StringBuilder output = new StringBuilder();
        output.append(optimum).append('\n');
        for (int vertex : answer) {
            output.append(vertex).append(' ');
        }
        System.out.println(output);
    }
}
```
