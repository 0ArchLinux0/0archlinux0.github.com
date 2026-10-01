---
title: BOJ. Shortest Path (1753)
author: MINJUN PARK
date: 2021-12-31 08:50:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, DFS, Shortest Path, 최단경로]
pin: false
lang: ko
translation_key: boj-1753-dijkstra
permalink: /ko/posts/boj-1753-dijkstra/
---

## 풀이

방향 그래프이므로 각 간선은 출발 정점의 인접 리스트에만 저장합니다. 모든 간선의 가중치가 음수가 아니므로 다익스트라 알고리즘을 사용할 수 있습니다. 거리 배열에는 시작점에서 각 정점까지 알려진 최솟값을 저장합니다. 우선순위 큐에서 현재 임시 거리가 가장 작은 항목을 꺼내 해당 정점의 간선을 완화하면 이웃 정점의 거리를 더 짧게 만들 수 있습니다. 더 짧은 경로를 찾으면 큐에 새 항목을 추가하고, 이후 거리 배열의 값과 일치하지 않는 오래된 항목은 버립니다.

오버플로를 피하기 위해 우선순위 큐의 비교에는 뺄셈 대신 `Long.compare`를 사용합니다. 거리는 `long`으로 저장하고, 도달할 수 없는 정점은 `INF`로 유지해 출력하며 시작점은 `0`으로 출력합니다. 평행 간선은 모두 보관해도 결과에 영향을 주지 않습니다.

지연 삭제 방식의 우선순위 큐를 사용하므로 시간 복잡도는 `O((V + E) log V)`, 공간 복잡도는 `O(V + E)`입니다.

[문제 링크](https://www.acmicpc.net/problem/1753)

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
