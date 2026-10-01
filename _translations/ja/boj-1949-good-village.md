---
title: BOJ 1949 - 優秀な村
author: MINJUN PARK
date: 2022-02-02 02:50:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, グラフ, 動的計画法, 優秀な村]
pin: false
lang: ja
translation_key: boj-1949-good-village
permalink: /ja/posts/boj-1949-good-village/
source_permalink: /posts/BOJ-1949/
---

[問題: BOJ 1949 — 優秀な村](https://www.acmicpc.net/problem/1949) · [English](/posts/BOJ-1949/) · [한국어](/ko/posts/boj-1949-good-village/)

各村には人口が与えられます。隣接する村を同時に選ばないという条件のもとで、選んだ村の人口合計を最大化します。入力は `N`、各村の人口 `N` 個、そして双方向の道路 `N - 1` 本です。

村1を根として、木を反復処理でたどります。訪問順を保存し、その逆順で処理すれば、再帰を使わずに子から親の順で計算できます。そのため、長い一本道の木でも呼び出しスタックがあふれません。各村 `u` について、次の2つの値を管理します。

- `take[u]`: `u` を選んだ場合の人口合計の最大値です。子は選べないため、`take[u] = population[u] + sum(skip[child])` です。
- `skip[u]`: `u` を選ばない場合の人口合計の最大値です。各子は選んでも選ばなくてもよいため、`skip[u] = sum(max(take[child], skip[child]))` です。

答えは根の `max(take[root], skip[root])` です。人口は正なので、根だけを選んでも有効な正の合計になります。したがって、何も選ばない場合が正の答えを上回ることはありません。村が1つだけなら、その村の人口を返します。一本道では隣接する選択が2つの状態間で競合し、星形では中心を選ぶ場合と葉を選ぶ場合を比較します。

隣接リスト、訪問順、DP配列はいずれも `O(N)` の領域を使います。各頂点と辺を定数回処理するため、時間計算量は `O(N)` です。人口の合計が `int` の範囲を超えないよう、DPの合計には `long` を使います。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        long[] population = new long[n];
        StringTokenizer values = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            population[i] = Long.parseLong(values.nextToken());
        }

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer edge = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(edge.nextToken()) - 1;
            int b = Integer.parseInt(edge.nextToken()) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n];
        int[] order = new int[n];
        int size = 0;
        order[size++] = 0;
        parent[0] = -1;
        for (int i = 0; i < size; i++) {
            int node = order[i];
            for (int neighbor : graph[node]) {
                if (neighbor == parent[node]) {
                    continue;
                }
                parent[neighbor] = node;
                order[size++] = neighbor;
            }
        }

        long[] take = new long[n];
        long[] skip = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            int node = order[i];
            take[node] = population[node];
            for (int child : graph[node]) {
                if (parent[child] == node) {
                    take[node] += skip[child];
                    skip[node] += Math.max(take[child], skip[child]);
                }
            }
        }

        System.out.println(Math.max(take[0], skip[0]));
    }
}
```
