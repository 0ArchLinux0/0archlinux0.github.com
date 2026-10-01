---
title: BOJ 11280 - 2-SAT - 3
author: MINJUN PARK
date: 2022-02-12 11:13:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 強連結成分, 2-SAT]
pin: false
lang: ja
translation_key: boj-11280-two-sat
permalink: /ja/posts/boj-11280-two-sat/
source_permalink: /posts/BOJ-11280/
---

[問題: BOJ 11280 — 2-SAT - 3](https://www.acmicpc.net/problem/11280) · [English](/posts/BOJ-11280/) · [한국어](/ko/posts/boj-11280-two-sat/)

各節 `(a OR b)` は、含意 `¬a → b` と `¬b → a` の2本の辺に変換できます。入力のリテラルは符号付き整数です。変数 `i` の正リテラル `i` は頂点 `i - 1`、負リテラル `-i` は頂点 `N + i - 1` に対応させます。したがって否定リテラルの頂点は、`v < N` なら `v + N`、それ以外なら `v - N` です。各含意辺は順方向グラフと逆方向グラフの両方に追加します。

強連結成分（SCC）は、再帰を使わないコサラジュ法で求めます。最初の走査では、頂点ごとの次の辺の位置をスタックに保持し、DFSの終了順を記録します。頂点や辺が多くても呼び出しスタックを使わないため安全です。終了順の逆順に逆方向グラフを走査し、各頂点のSCC番号を割り当てます。変数 `i` とその否定リテラルが同じSCCに属する場合、互いに含意されて両方が真になる矛盾が生じます。そのような変数があれば答えは `0`、なければ `1` です。単位節も通常の規則で扱えます。たとえば `(x OR x)` は `¬x → x` を追加します。

頂点数は `2N`、含意辺数は `2M` です。2回のDFSはそれぞれ各頂点と辺を定数回だけ処理するため、時間計算量・空間計算量はいずれも `O(N + M)` です。出力は答えの数字1つだけです。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int literalIndex(int literal, int n) {
        return literal > 0 ? literal - 1 : n - literal - 1;
    }

    private static int negation(int vertex, int n) {
        return vertex < n ? vertex + n : vertex - n;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer first = new StringTokenizer(input.readLine());
        int n = Integer.parseInt(first.nextToken());
        int m = Integer.parseInt(first.nextToken());
        int vertices = 2 * n;

        List<Integer>[] graph = new ArrayList[vertices];
        List<Integer>[] reverse = new ArrayList[vertices];
        for (int v = 0; v < vertices; v++) {
            graph[v] = new ArrayList<>();
            reverse[v] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer clause = new StringTokenizer(input.readLine());
            int a = literalIndex(Integer.parseInt(clause.nextToken()), n);
            int b = literalIndex(Integer.parseInt(clause.nextToken()), n);
            int notA = negation(a, n);
            int notB = negation(b, n);
            graph[notA].add(b);
            reverse[b].add(notA);
            graph[notB].add(a);
            reverse[a].add(notB);
        }

        boolean[] visited = new boolean[vertices];
        int[] order = new int[vertices];
        int orderSize = 0;
        int[] stackVertex = new int[vertices];
        int[] stackNext = new int[vertices];

        for (int start = 0; start < vertices; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stackVertex[top] = start;
            stackNext[top] = 0;
            visited[start] = true;
            while (top >= 0) {
                int v = stackVertex[top];
                if (stackNext[top] < graph[v].size()) {
                    int next = graph[v].get(stackNext[top]++);
                    if (!visited[next]) {
                        visited[next] = true;
                        stackVertex[++top] = next;
                        stackNext[top] = 0;
                    }
                } else {
                    order[orderSize++] = v;
                    top--;
                }
            }
        }

        int[] component = new int[vertices];
        int componentId = 0;
        int[] stack = new int[vertices];
        for (int i = orderSize - 1; i >= 0; i--) {
            int start = order[i];
            if (component[start] != 0) {
                continue;
            }
            componentId++;
            int top = 0;
            stack[top++] = start;
            component[start] = componentId;
            while (top > 0) {
                int v = stack[--top];
                for (int next : reverse[v]) {
                    if (component[next] == 0) {
                        component[next] = componentId;
                        stack[top++] = next;
                    }
                }
            }
        }

        for (int variable = 0; variable < n; variable++) {
            if (component[variable] == component[variable + n]) {
                System.out.println(0);
                return;
            }
        }
        System.out.println(1);
    }
}
```
