---
title: BOJ. 未確認の目的地 (9370)
author: MINJUN PARK
date: 2022-01-02 01:34:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dijkstra, Graph, Unidentified destination, 미확인 도착지]
pin: false
lang: ja
translation_key: boj-9370-unidentified-destination
permalink: /ja/posts/boj-9370-unidentified-destination/
---

[問題リンク](https://www.acmicpc.net/problem/9370)

## 解法

各テストケースで、始点 `s` と指定された辺の両端 `g`、`h` を始点としてダイクストラ法を実行します。候補頂点 `x` が答えになるのは、`s` から `x` への最短経路の中に指定された辺 `g-h` を通るものが存在する場合です。したがって最短距離は、次のどちらかの経路長と一致します。

- `dist(s, g) + w(g, h) + dist(h, x)`
- `dist(s, h) + w(g, h) + dist(g, x)`

2つの式は、無向辺 `g-h` を反対向きに通る場合を表します。`g` と `h` の間に平行辺が複数ある場合は、その最小の重みを使います。より重い平行辺では最短経路にならず、最小の辺はグラフ上で利用できるためです。区間のいずれかに到達できない場合はその式は無効なので、距離を加算する前に `INF` であるか確認します。

正しさは次のように示せます。最短経路が `g-h` を通るなら、その辺に到達するまでの区間と反対側の端点から目的地までの区間は、それぞれ最短でなければなりません。そうでなければ、その区間をより短い経路に置き換えて全体を短縮できるからです。よって全体の長さは上記2式のいずれかになります。逆に、どちらかの式が `dist(s, x)` と等しければ、その式にある最短区間を `g-h` の辺でつないだ経路も最短経路であり、その辺を通ります。

グラフは隣接リストで保持し、遅延削除を使う最小優先度付きキューでダイクストラ法を行います。距離と辺の重みには `long` を使い、キューの比較には減算ではなく `Long.compare` を使って比較時のオーバーフローを防ぎます。候補は頂点番号の昇順に並べて出力します。テストケースごとの時間計算量は `O((V + E) log V + C log C)`（`C` は候補数）、空間計算量は `O(V + E)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int n = input.nextInt();
            int m = input.nextInt();
            int candidateCount = input.nextInt();
            int source = input.nextInt() - 1;
            int g = input.nextInt() - 1;
            int h = input.nextInt() - 1;

            List<Edge>[] graph = new ArrayList[n];
            for (int vertex = 0; vertex < n; vertex++) {
                graph[vertex] = new ArrayList<>();
            }

            long ghWeight = INF;
            for (int i = 0; i < m; i++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                long weight = input.nextLong();
                graph[a].add(new Edge(b, weight));
                graph[b].add(new Edge(a, weight));
                if ((a == g && b == h) || (a == h && b == g)) {
                    ghWeight = Math.min(ghWeight, weight);
                }
            }

            long[] fromSource = dijkstra(graph, source);
            long[] fromG = dijkstra(graph, g);
            long[] fromH = dijkstra(graph, h);

            List<Integer> candidates = new ArrayList<>(candidateCount);
            for (int i = 0; i < candidateCount; i++) {
                int candidate = input.nextInt() - 1;
                long shortest = fromSource[candidate];
                if (shortest != INF
                        && ghWeight != INF
                        && (equalsRoute(shortest, fromSource[g], ghWeight, fromH[candidate])
                        || equalsRoute(shortest, fromSource[h], ghWeight, fromG[candidate]))) {
                    candidates.add(candidate + 1);
                }
            }

            Collections.sort(candidates);
            for (int candidate : candidates) {
                output.append(candidate).append(' ');
            }
            output.append('\n');
        }
        System.out.print(output);
    }

    private static boolean equalsRoute(long shortest, long first, long edge, long last) {
        return first != INF && edge != INF && last != INF
                && shortest == first + edge + last;
    }

    private static long[] dijkstra(List<Edge>[] graph, int start) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, INF);
        distance[start] = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                (left, right) -> Long.compare(left.distance, right.distance));
        queue.offer(new State(start, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.vertex]) {
                continue;
            }
            for (Edge edge : graph[current.vertex]) {
                long nextDistance = current.distance + edge.weight;
                if (nextDistance < distance[edge.to]) {
                    distance[edge.to] = nextDistance;
                    queue.offer(new State(edge.to, nextDistance));
                }
            }
        }
        return distance;
    }

    private static class Edge {
        final int to;
        final long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class State {
        final int vertex;
        final long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
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

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        int nextInt() {
            return (int) nextLong();
        }
    }
}
```
