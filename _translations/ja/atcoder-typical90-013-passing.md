---
title: AtCoder Typical 90 013 — Passing
author: MINJUN PARK
date: 2021-12-30 02:58:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Graph,
    Passing,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-013-passing
permalink: /ja/posts/atcoder-typical90-013-passing/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_m)

頂点 `i` の答えは、頂点1から `i` までの最短距離と、`i` から頂点 `N` までの最短距離の和です。グラフは無向なので、後者は `N` から `i` までの最短距離と等しくなります。頂点1を始点に一度、頂点 `N` を始点に一度ダイクストラ法を実行し、各頂点について二つの距離を足します。

各辺は隣接リストに格納します。辺の重みがすべて非負であるため、ダイクストラ法を使えます。負の重みがある場合、現在最も近い頂点を確定する方法は安全ではありません。優先度付きキューには古い要素が残ることがあるため、その距離が距離配列の現在値と一致しない要素は破棄します。重みと距離は `long` で保持します。`INF` は到達不能な頂点を表し、どちらかの探索で到達できない頂点には `-1` を出力します。辺を緩和する前に、重みを加えても `INF` 未満に収まることを確認し、オーバーフローと到達不能を示す番兵値の破損を防ぎます。

隣接リストと優先度付きキューを使う一回の実行の時間計算量は `O((N + M) log N)` です。二回実行しても漸近計算量は変わりません。グラフと二つの距離配列の空間計算量は `O(N + M)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();

        List<List<Edge>> graph = new ArrayList<>(vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            long weight = input.nextLong();
            graph.get(a).add(new Edge(b, weight));
            graph.get(b).add(new Edge(a, weight));
        }

        long[] fromStart = dijkstra(graph, 0);
        long[] fromGoal = dijkstra(graph, vertexCount - 1);
        StringBuilder output = new StringBuilder();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (fromStart[vertex] == INF || fromGoal[vertex] == INF) {
                output.append(-1);
            } else {
                output.append(fromStart[vertex] + fromGoal[vertex]);
            }
            output.append('\n');
        }
        System.out.print(output);
    }

    private static long[] dijkstra(List<List<Edge>> graph, int source) {
        long[] distance = new long[graph.size()];
        Arrays.fill(distance, INF);
        distance[source] = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                (left, right) -> Long.compare(left.distance, right.distance));
        queue.add(new State(source, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.vertex]) {
                continue;
            }

            for (Edge edge : graph.get(current.vertex)) {
                if (edge.weight >= INF - current.distance) {
                    continue;
                }
                long candidate = current.distance + edge.weight;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    queue.add(new State(edge.to, candidate));
                }
            }
        }
        return distance;
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

        private long nextLong() throws IOException {
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

        private int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
