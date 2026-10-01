---
title: AtCoder. ABC 237 E Skiing
author: MINJUN PARK
date: 2022-01-31 08:48:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    Graph,
    Dijkstra,
    Review,
    ABC contest
  ]
pin: false
lang: en
translation_key: abc237-e-skiing
---

[Problem: AtCoder ABC 237 E — Skiing](https://atcoder.jp/contests/abc237/tasks/abc237_e) · [한국어](/ko/posts/abc237-e-skiing/) · [日本語](/ja/posts/abc237-e-skiing/)

For a route from vertex 1 to a vertex `v`, let `U` be the total amount climbed and `D` the total amount descended. The happiness change along the route is `D - 2U`: descending by a unit gains one, while climbing by a unit loses two. Since the net height change is `H[v] - H[1] = U - D`, we have `D = U + H[1] - H[v]`, so the happiness is `H[1] - H[v] - U`. For a fixed destination, its height is fixed, so maximizing happiness is equivalent to minimizing the total climb `U`.

Assign each undirected road `u-v` a directed cost in each direction: going from `u` to `v` costs `max(0, H[v] - H[u])`, the climb on that step. Going from `v` to `u` similarly costs `max(0, H[u] - H[v])`. Descending and equal-height steps cost zero. All costs are nonnegative, so Dijkstra's algorithm finds the minimum accumulated climb from vertex 1 to every reachable vertex. The edge costs are directed even though the roads are undirected; in particular, an equal-height road has zero cost in both directions.

For each reachable vertex `v`, compute `H[1] - H[v] - dist[v]`, where `dist[v]` is the minimum climb. Unreachable vertices have no route from the start and must not be included. Initialize the answer to zero because vertex 1 is reachable from itself with happiness zero. Distances, height differences, and happiness are stored as `long` to avoid overflow when adding costs. The running time is `O((N + M) log N)` and the space usage is `O(N + M)`.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static class Edge {
        int to;
        long climb;

        Edge(int to, long climb) {
            this.to = to;
            this.climb = climb;
        }
    }

    private static class State implements Comparable<State> {
        int vertex;
        long distance;

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
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

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
    }

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int n = (int) scanner.nextLong();
        int m = (int) scanner.nextLong();
        long[] height = new long[n];
        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextLong();
        }

        List<List<Edge>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = (int) scanner.nextLong() - 1;
            int v = (int) scanner.nextLong() - 1;
            graph.get(u).add(new Edge(v, Math.max(0L, height[v] - height[u])));
            graph.get(v).add(new Edge(u, Math.max(0L, height[u] - height[v])));
        }

        long[] distance = new long[n];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[0] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(0, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.vertex]) {
                continue;
            }
            for (Edge edge : graph.get(current.vertex)) {
                long nextDistance = current.distance + edge.climb;
                if (nextDistance < distance[edge.to]) {
                    distance[edge.to] = nextDistance;
                    queue.add(new State(edge.to, nextDistance));
                }
            }
        }

        long answer = 0;
        for (int v = 0; v < n; v++) {
            if (distance[v] != Long.MAX_VALUE) {
                answer = Math.max(answer, height[0] - height[v] - distance[v]);
            }
        }
        System.out.println(answer);
    }
}
```
