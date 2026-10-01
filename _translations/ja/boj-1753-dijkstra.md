---
title: BOJ. Shortest Path (1753)
author: MINJUN PARK
date: 2021-12-31 08:50:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, DFS, Shortest Path, 최단경로]
pin: false
lang: ja
translation_key: boj-1753-dijkstra
permalink: /ja/posts/boj-1753-dijkstra/
---

## 解法

有向グラフなので、各辺は始点の隣接リストだけに格納します。すべての辺の重みが非負であるため、ダイクストラ法を使えます。距離配列には始点から各頂点までの既知の最短距離を保存します。優先度付きキューから暫定距離が最小の要素を取り出して、その頂点から出る辺を緩和すると、隣接頂点までの距離を短縮できます。より短い経路が見つかった場合は新しい要素をキューに追加し、後で距離配列と一致しなくなった古い要素は破棄します。

オーバーフローを避けるため、優先度付きキューの比較には減算ではなく`Long.compare`を使います。距離は`long`で保持し、到達できない頂点は`INF`のまま出力し、始点は`0`と出力します。平行辺はすべて保持しても結果に影響しません。

遅延削除を行う優先度付きキューを使うため、時間計算量は`O((V + E) log V)`、空間計算量は`O(V + E)`です。

[問題リンク](https://www.acmicpc.net/problem/1753)

## Java

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
        int source = input.nextInt() - 1;

        List<List<Edge>> graph = new ArrayList<>(vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long weight = input.nextLong();
            graph.get(from).add(new Edge(to, weight));
        }

        long[] distance = new long[vertexCount];
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
                long candidate = current.distance + edge.weight;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    queue.add(new State(edge.to, candidate));
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (long value : distance) {
            output.append(value == INF ? "INF" : value).append('\n');
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

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            boolean negative = c == '-';
            if (negative) {
                c = read();
            }

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return negative ? -value : value;
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
