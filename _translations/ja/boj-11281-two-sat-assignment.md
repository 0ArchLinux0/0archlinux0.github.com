---
title: BOJ 11281 - 2-SAT - 4
author: MINJUN PARK
date: 2022-02-14 17:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 強連結成分, グラフ, 2-SAT]
pin: false
lang: ja
translation_key: boj-11281-two-sat-assignment
permalink: /ja/posts/boj-11281-two-sat-assignment/
source_permalink: /posts/BOJ-11281/
---

[問題: BOJ 11281 — 2-SAT - 4](https://www.acmicpc.net/problem/11281) · [English](/posts/BOJ-11281/) · [한국어](/ko/posts/boj-11281-two-sat-assignment/)

## 反復型Kosaraju法による2-SAT

入力節 `(a ∨ b)` は、含意 `¬a → b` と `¬b → a` に変換できます。符号付きリテラルをそれぞれ頂点として表します。正のリテラル `x` のインデックスは `x - 1`、負のリテラル `¬x` のインデックスは `N + x - 1` です。リテラルの否定はインデックス `index ^ N` で表せます。各含意辺は元のグラフと逆グラフの両方に登録します。

変数とその否定が同じ強連結成分に属する場合、式は充足不能です。一方のリテラルからもう一方へ、さらに戻る経路が存在するため、両方の真偽値が同時に強制されるからです。それ以外の場合はKosaraju法で強連結成分を求めます。第1パスでは元のグラフを探索して頂点の終了順を記録し、第2パスでは終了順の逆順に逆グラフを探索します。どちらのパスも明示的なスタックを使うため、長い含意経路があってもJavaの呼び出しスタックを使い切りません。

第2パスで成分を発見した順に番号を付けると、元の含意グラフにおける異なる成分間の辺は、すべて小さい番号から大きい番号へ向かいます。したがって `x` の成分番号が `¬x` より大きいとき、`x` を真に設定します。これはこの番号付けに対応した逆トポロジカル順の割り当て規則です。探索順や番号付けの規則を変更する場合は、比較の向きも変更する必要があります。

第1パスでは頂点ごとに次に調べる辺のインデックスを保持し、再帰DFSを反復処理で再現します。出辺をすべて調べてから頂点を終了順に追加します。第2パスではスタックへ追加する時点で訪問済みにすることで、同じ頂点の重複追加を防ぎます。各頂点と含意辺を定数回処理するため、時間計算量と空間計算量はともに `O(N + M)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }

    private static int literalIndex(int literal, int n) {
        return literal > 0 ? literal - 1 : n - literal - 1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();
        int vertexCount = 2 * n;

        List<Integer>[] graph = new ArrayList[vertexCount];
        List<Integer>[] reverseGraph = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            graph[vertex] = new ArrayList<>();
            reverseGraph[vertex] = new ArrayList<>();
        }

        for (int clause = 0; clause < m; clause++) {
            int a = input.nextInt();
            int b = input.nextInt();
            int notA = literalIndex(-a, n);
            int indexA = literalIndex(a, n);
            int notB = literalIndex(-b, n);
            int indexB = literalIndex(b, n);

            graph[notA].add(indexB);
            reverseGraph[indexB].add(notA);
            graph[notB].add(indexA);
            reverseGraph[indexA].add(notB);
        }

        boolean[] visited = new boolean[vertexCount];
        int[] nextEdge = new int[vertexCount];
        int[] stack = new int[vertexCount];
        int[] order = new int[vertexCount];
        int orderSize = 0;

        for (int start = 0; start < vertexCount; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stack[top++] = start;
            visited[start] = true;

            while (top > 0) {
                int vertex = stack[top - 1];
                if (nextEdge[vertex] < graph[vertex].size()) {
                    int next = graph[vertex].get(nextEdge[vertex]++);
                    if (!visited[next]) {
                        visited[next] = true;
                        stack[top++] = next;
                    }
                } else {
                    order[orderSize++] = vertex;
                    top--;
                }
            }
        }

        int[] component = new int[vertexCount];
        int componentCount = 0;
        for (int index = orderSize - 1; index >= 0; index--) {
            int start = order[index];
            if (component[start] != 0) {
                continue;
            }
            int top = 0;
            stack[top++] = start;
            component[start] = ++componentCount;

            while (top > 0) {
                int vertex = stack[--top];
                for (int next : reverseGraph[vertex]) {
                    if (component[next] == 0) {
                        component[next] = componentCount;
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

        StringBuilder output = new StringBuilder("1\n");
        for (int variable = 0; variable < n; variable++) {
            output.append(component[variable] > component[variable + n] ? '1' : '0');
            if (variable + 1 < n) {
                output.append(' ');
            }
        }
        output.append('\n');
        System.out.print(output);
    }
}
```
