---
title: BOJ 3584 - 最近共通祖先
author: MINJUN PARK
date: 2022-02-08 14:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 木, 最近共通祖先, LCA]
pin: false
lang: ja
translation_key: boj-3584-lowest-common-ancestor
permalink: /ja/posts/boj-3584-lowest-common-ancestor/
source_permalink: /posts/BOJ-3584/
---

[問題: BOJ 3584 — 最近共通祖先](https://www.acmicpc.net/problem/3584) · [English](/posts/BOJ-3584/) · [한국어](/ko/posts/boj-3584-lowest-common-ancestor/)

各テストケースでは、`N` 個の頂点を持つ根付き木と、最近共通祖先（LCA）を求める頂点のペアが与えられます。テストケースごとにクエリはちょうど 1 つです。入力の辺は親から子の向きで与えられるため、各子の親を `parent` 配列に保存します。親を持たない唯一の頂点が根です。

クエリの最初の頂点から `parent` をたどって根まで進み、その祖先をすべて印付けします。次に、2 番目の頂点から親をたどり、印の付いた頂点に到達するまで上ります。最初に見つかる印付き頂点が LCA です。2 番目の頂点から上る経路上の各頂点はその祖先であり、最初の頂点の祖先経路と最初に交わる頂点が、共通祖先の中で最も深いからです。一方の頂点が他方の祖先である場合も、根が答えになる場合も処理できます。

`parent` 配列の構築に `O(N)` 時間かかります。祖先の印付けと親をたどる処理も合わせて最大 `O(N)` 時間、空間は `O(N)` です。反復処理だけを使うため、100,000 個の頂点が一直線につながる木でも呼び出しスタックを使い切りません。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        int testCases = Integer.parseInt(input.readLine().trim());

        for (int test = 0; test < testCases; test++) {
            int n = Integer.parseInt(input.readLine().trim());
            int[] parent = new int[n + 1];
            for (int edge = 0; edge < n - 1; edge++) {
                StringTokenizer tokens = new StringTokenizer(input.readLine());
                int from = Integer.parseInt(tokens.nextToken());
                int to = Integer.parseInt(tokens.nextToken());
                parent[to] = from;
            }

            int root = 1;
            while (parent[root] != 0) {
                root++;
            }

            StringTokenizer query = new StringTokenizer(input.readLine());
            int first = Integer.parseInt(query.nextToken());
            int second = Integer.parseInt(query.nextToken());

            boolean[] ancestors = new boolean[n + 1];
            for (int node = first; ; node = parent[node]) {
                ancestors[node] = true;
                if (node == root) {
                    break;
                }
            }
            while (!ancestors[second]) {
                second = parent[second];
            }
            output.append(second).append('\n');
        }

        System.out.print(output);
    }
}
```
