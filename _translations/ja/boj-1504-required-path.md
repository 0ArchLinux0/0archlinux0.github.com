---
title: BOJ. 特定の最短経路 (1504)
author: MINJUN PARK
date: 2022-01-01 03:54:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dijkstra, Graph, Shortest path with specification, 특정한 최단 경로]
pin: false
lang: ja
translation_key: boj-1504-required-path
permalink: /ja/posts/boj-1504-required-path/
---

[問題ページ](https://www.acmicpc.net/problem/1504)

## 解法

正の重みを持つ無向グラフで、頂点`v1`と`v2`の両方を通る最短経路を求めます。必須の2頂点を訪れる順序は`1 → v1 → v2 → N`または`1 → v2 → v1 → N`の2通りだけです。それぞれの区間の最短距離を足した2つの候補から小さい方を選び、どちらの候補も到達不能なら`-1`を出力します。

始点`1`、`v1`、`v2`からそれぞれダイクストラ法を実行します。1つ目の順序の距離は`d(1,v1) + d(v1,v2) + d(v2,N)`、2つ目は`d(1,v2) + d(v2,v1) + d(v1,N)`です。辺は無向なので`d(v1,v2) = d(v2,v1)`です。始点または終点が必須頂点と同じ場合も、距離`0`として自然に処理できます。

距離と候補の合計には`long`を使い、到達不能な区間を含む合計は計算しません。優先度付きキューに残った古い項目は、現在の最短距離と異なれば破棄します。時間計算量は`O((V + E) log V)`、隣接リストと距離配列を含む空間計算量は`O(V + E)`です。

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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int e = input.nextInt();

        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            long weight = input.nextLong();
            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }
        int v1 = input.nextInt() - 1;
        int v2 = input.nextInt() - 1;

        long[] fromStart = dijkstra(graph, 0);
        long[] fromV1 = dijkstra(graph, v1);
        long[] fromV2 = dijkstra(graph, v2);

        long viaV1ThenV2 = routeLength(
                fromStart[v1], fromV1[v2], fromV2[n - 1]);
        long viaV2ThenV1 = routeLength(
                fromStart[v2], fromV2[v1], fromV1[n - 1]);
        long answer = Math.min(viaV1ThenV2, viaV2ThenV1);
        System.out.println(answer == INF ? -1 : answer);
    }

    private static long[] dijkstra(List<Edge>[] graph, int start) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, INF);
        distance[start] = 0;

        PriorityQueue<State> queue = new PriorityQueue<>();
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

    private static long routeLength(long first, long middle, long last) {
        if (first == INF || middle == INF || last == INF) {
            return INF;
        }
        return first + middle + last;
    }

    private static class Edge {
        final int to;
        final long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class State implements Comparable<State> {
        final int vertex;
        final long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(distance, other.distance);
        }
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int index;
        private int size;

        private int read() throws IOException {
            if (index == size) {
                size = in.read(buffer);
                index = 0;
                if (size == -1) {
                    return -1;
                }
            }
            return buffer[index++];
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

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
