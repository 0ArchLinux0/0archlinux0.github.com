---
title: BOJ 3648 - アイドル
author: MINJUN PARK
date: 2022-02-13 10:10:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 強連結成分, 2-SAT, アイドル]
pin: false
lang: ja
translation_key: boj-3648-idol-two-sat
permalink: /ja/posts/boj-3648-idol-two-sat/
source_permalink: /posts/BOJ-3648/
---

[問題: BOJ 3648 — アイドル](https://www.acmicpc.net/problem/3648) · [English](/posts/BOJ-3648/) · [한국어](/ko/posts/boj-3648-idol-two-sat/)

各テストケースでは、すべての節を満たし、変数 `1` を真にする必要があります。節 `(a OR b)` は、含意 `¬a → b` と `¬b → a` の2本の辺に変換します。変数 `1` を真にする条件は単位節 `(1 OR 1)` として追加し、他の節と同じように `¬1 → 1` の辺を作ります。

入力リテラルは符号付き整数です。変数が `n` 個の場合、グラフの頂点数は `2n` で、インデックスは `0` から `2n - 1` です。正リテラル `i` は `i - 1`、負リテラル `-i` は `n + i - 1` に対応します。否定を取ると、この2つのインデックス領域が入れ替わります。各含意辺は順方向グラフと逆方向グラフの両方に保存します。

強連結成分（SCC）は、明示的なスタックを使う反復型コサラジュ法で求めます。1回目の探索では、現在の頂点と次に調べる辺の位置をスタックに保持します。すべての辺を処理した後に頂点を終了順に追加することで、再帰DFSと同じ順序を得ます。したがって、含意辺が長い鎖を作っても呼び出しスタックはあふれません。2回目は終了順の逆順に逆方向グラフを探索してSCC番号を割り当てます。ある変数とその否定リテラルが同じSCCに入る場合、式は充足不能です。

各ケースの頂点数は `2n`、含意辺数は `(1 OR 1)` の分を含めて `2m + 2` です。時間計算量・空間計算量はいずれもケースごとに `O(n + m)` です。入力はEOFまで読み、各ケースの答えとして小文字の `yes` または `no` を出力します。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

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
            do { c = read(); } while (c <= ' ' && c != -1);
            if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int value = 0;
            while (c > ' ') { value = value * 10 + c - '0'; c = read(); }
            return value * sign;
        }
    }

    private static int literalIndex(int literal, int n) {
        int variable = Math.abs(literal) - 1;
        return literal > 0 ? variable : variable + n;
    }

    private static int negation(int vertex, int n) {
        return vertex < n ? vertex + n : vertex - n;
    }

    @SuppressWarnings("unchecked")
    private static boolean satisfiable(int n, int m, FastScanner input) throws IOException {
        int vertices = 2 * n;
        List<Integer>[] graph = new ArrayList[vertices];
        List<Integer>[] reverse = new ArrayList[vertices];
        for (int v = 0; v < vertices; v++) {
            graph[v] = new ArrayList<>();
            reverse[v] = new ArrayList<>();
        }

        for (int i = 0; i <= m; i++) {
            int a;
            int b;
            if (i == m) {
                a = literalIndex(1, n);
                b = a;
            } else {
                a = literalIndex(input.nextInt(), n);
                b = literalIndex(input.nextInt(), n);
            }
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
            if (visited[start]) continue;
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
            if (component[start] != 0) continue;
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
            if (component[variable] == component[variable + n]) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        StringBuilder output = new StringBuilder();
        while (true) {
            int n = input.nextInt();
            if (n == Integer.MIN_VALUE) break;
            int m = input.nextInt();
            output.append(satisfiable(n, m, input) ? "yes" : "no").append('\n');
        }
        System.out.print(output);
    }
}
```
