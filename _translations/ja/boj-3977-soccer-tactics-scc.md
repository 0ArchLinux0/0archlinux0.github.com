---
title: BOJ 3977 - サッカー戦術
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, グラフ, 強連結成分, サッカー戦術]
pin: false
lang: ja
translation_key: boj-3977-soccer-tactics-scc
permalink: /ja/posts/boj-3977-soccer-tactics-scc/
source_permalink: /posts/BOJ-3977/
---

[問題: BOJ 3977 — サッカー戦術](https://www.acmicpc.net/problem/3977) · [English](/posts/BOJ-3977/) · [한국어](/ko/posts/boj-3977-soccer-tactics-scc/)

グラフを強連結成分（SCC）に分けると、各SCCを頂点とし、異なるSCC間の辺を残した縮約グラフが得られます。入次数が0のSCCは、ほかのSCCから到達できない始点です。そのようなSCCが1つだけなら、その中の頂点すべてが答えです。2つ以上ある場合は `Confused` を出力します。

Kosarajuの2回の探索はどちらも反復処理で実装します。1回目は元のグラフでDFSを行い、各頂点の探索が終了した順序を記録します。明示的な頂点スタックと頂点ごとの次の辺の位置を使うことで、再帰呼び出しを使わずにDFSの終了順序を再現できます。2回目は逆向きグラフを終了順序の逆順に探索し、1回の探索ごとに1つのSCCを特定します。

辺を読み込む際に元のグラフと逆向きグラフを作り、元の辺一覧も保持します。SCC番号を割り当てた後、異なるSCCを結ぶ辺の到着側SCCに入辺があることを記録します。入辺のないSCC数を数え、1つだけの場合は頂点を昇順に走査してそのSCCの頂点を出力します。孤立頂点や辺が1本もないグラフでも、各頂点はそれぞれ独立した始点SCCになります。

時間計算量・空間計算量はいずれも `O(V + E)` です。再帰を使わないため、頂点数100,000の長い一本道でも呼び出しスタックがあふれません。テストケースの間には空行を出力します。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int n = input.nextInt();
            int m = input.nextInt();
            ArrayList<Integer>[] graph = new ArrayList[n];
            ArrayList<Integer>[] reverse = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
                reverse[i] = new ArrayList<>();
            }

            int[] from = new int[m];
            int[] to = new int[m];
            for (int i = 0; i < m; i++) {
                int a = input.nextInt();
                int b = input.nextInt();
                from[i] = a;
                to[i] = b;
                graph[a].add(b);
                reverse[b].add(a);
            }

            boolean[] visited = new boolean[n];
            int[] nextEdge = new int[n];
            int[] stack = new int[n];
            int[] order = new int[n];
            int orderSize = 0;

            for (int start = 0; start < n; start++) {
                if (visited[start]) {
                    continue;
                }
                int top = 0;
                stack[0] = start;
                visited[start] = true;
                while (top >= 0) {
                    int node = stack[top];
                    if (nextEdge[node] < graph[node].size()) {
                        int neighbor = graph[node].get(nextEdge[node]++);
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            stack[++top] = neighbor;
                        }
                    } else {
                        order[orderSize++] = node;
                        top--;
                    }
                }
            }

            int[] component = new int[n];
            int componentCount = 0;
            for (int i = orderSize - 1; i >= 0; i--) {
                int start = order[i];
                if (component[start] != 0) {
                    continue;
                }
                int top = 0;
                stack[0] = start;
                component[start] = ++componentCount;
                while (top >= 0) {
                    int node = stack[top--];
                    for (int neighbor : reverse[node]) {
                        if (component[neighbor] == 0) {
                            component[neighbor] = componentCount;
                            stack[++top] = neighbor;
                        }
                    }
                }
            }

            boolean[] hasIncoming = new boolean[componentCount + 1];
            for (int i = 0; i < m; i++) {
                if (component[from[i]] != component[to[i]]) {
                    hasIncoming[component[to[i]]] = true;
                }
            }

            int source = 0;
            int sourceCount = 0;
            for (int id = 1; id <= componentCount; id++) {
                if (!hasIncoming[id]) {
                    source = id;
                    sourceCount++;
                }
            }

            if (test > 0) {
                answer.append('\n');
            }
            if (sourceCount != 1) {
                answer.append("Confused\n");
            } else {
                for (int vertex = 0; vertex < n; vertex++) {
                    if (component[vertex] == source) {
                        answer.append(vertex).append('\n');
                    }
                }
            }
        }

        System.out.print(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
