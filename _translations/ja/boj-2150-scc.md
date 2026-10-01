---
title: BOJ. Strongly Connected Component (2150)
author: MINJUN PARK
date: 2022-01-26 00:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Graph, SCC, BOJ, 強連結成分]
pin: false
lang: ja
translation_key: boj-2150-scc
permalink: /ja/posts/boj-2150-scc/
source_permalink: /posts/BOJ-2150/
---

[問題: BOJ 2150 — Strongly Connected Component](https://www.acmicpc.net/problem/2150)

## 反復型コサラジュ法

コサラジュ法は2回の深さ優先探索で強連結成分（SCC）を求めます。この実装では再帰呼び出しの代わりに明示的な整数スタックを使うため、頂点数10,000の長い経路でもJavaの呼び出しスタックを使い切りません。

1回目の探索は元のグラフで行います。各スタックフレームに頂点と次に調べる辺のインデックスを保存します。すべての辺を調べ終えた頂点を`finishOrder`に追加します。これは再帰DFSと同じ帰りがけ順です。2回目は辺を逆向きにしたグラフで、終了順の逆順に頂点を訪問します。この順序で始める各探索は、ちょうど1つのSCCを集めます。終了順の性質により、逆グラフで未訪問の別の成分へ抜け出すことはなく、開始頂点と相互に到達できる頂点はすべて同じ探索で訪問されます。

各成分内の頂点を昇順に並べ、成分も最小頂点の昇順で並べます。出力の先頭に成分数を出し、各成分を1行に出力して、頂点列の後ろに`-1`を付けます。これはBOJ 2150の出力形式です。

2回の探索では各頂点と辺を定数回だけ調べます。各SCC内の頂点の並べ替えにかかる合計時間は最大`O(V log V)`です。したがって、全体の時間計算量は`O(V + E + V log V)`、空間計算量は`O(V + E)`です。

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();

        List<Integer>[] graph = new List[vertexCount + 1];
        List<Integer>[] reversed = new List[vertexCount + 1];
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            graph[vertex] = new ArrayList<>();
            reversed[vertex] = new ArrayList<>();
        }
        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt();
            int to = input.nextInt();
            graph[from].add(to);
            reversed[to].add(from);
        }

        boolean[] visited = new boolean[vertexCount + 1];
        int[] stack = new int[vertexCount];
        int[] nextEdge = new int[vertexCount];
        int[] finishOrder = new int[vertexCount];
        int finishCount = 0;

        for (int start = 1; start <= vertexCount; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stack[0] = start;
            nextEdge[0] = 0;
            visited[start] = true;

            while (top >= 0) {
                int vertex = stack[top];
                if (nextEdge[top] < graph[vertex].size()) {
                    int neighbor = graph[vertex].get(nextEdge[top]++);
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[++top] = neighbor;
                        nextEdge[top] = 0;
                    }
                } else {
                    finishOrder[finishCount++] = vertex;
                    top--;
                }
            }
        }

        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();
        for (int i = finishCount - 1; i >= 0; i--) {
            int start = finishOrder[i];
            if (visited[start]) {
                continue;
            }

            List<Integer> component = new ArrayList<>();
            int top = 0;
            stack[0] = start;
            visited[start] = true;
            while (top >= 0) {
                int vertex = stack[top--];
                component.add(vertex);
                for (int neighbor : reversed[vertex]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[++top] = neighbor;
                    }
                }
            }
            Collections.sort(component);
            components.add(component);
        }

        components.sort(Comparator.comparingInt(component -> component.get(0)));
        StringBuilder output = new StringBuilder().append(components.size()).append('\n');
        for (List<Integer> component : components) {
            for (int vertex : component) {
                output.append(vertex).append(' ');
            }
            output.append(-1).append('\n');
        }
        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
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
    }
}
```
