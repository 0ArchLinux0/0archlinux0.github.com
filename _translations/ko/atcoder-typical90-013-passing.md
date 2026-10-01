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
lang: ko
translation_key: atcoder-typical90-013-passing
permalink: /ko/posts/atcoder-typical90-013-passing/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_m)

각 정점 `i`의 답은 정점 1에서 `i`까지의 최단 거리와 `i`에서 정점 `N`까지의 최단 거리를 더한 값입니다. 그래프는 무방향이므로 두 번째 거리는 `N`에서 `i`까지의 최단 거리와 같습니다. 정점 1에서 한 번, 정점 `N`에서 한 번 다익스트라 알고리즘을 실행한 뒤 각 정점에서 두 거리를 더합니다.

각 간선은 인접 리스트에 저장합니다. 모든 간선의 가중치가 음이 아닌 경우에 다익스트라 알고리즘을 사용할 수 있습니다. 음수 가중치가 있으면 현재 가장 가까운 정점을 확정하는 방식이 안전하지 않습니다. 우선순위 큐에는 오래된 항목이 남을 수 있으므로, 항목의 거리가 거리 배열의 현재 값과 다르면 버립니다. 가중치와 거리는 `long`으로 저장합니다. `INF`는 도달할 수 없는 정점을 나타내며, 두 탐색 중 하나라도 정점에 도달하지 못하면 `-1`을 출력합니다. 간선을 완화하기 전에 가중치 더하기가 `INF` 미만인지 검사해 오버플로와 도달 불가 센티널의 훼손을 막습니다.

인접 리스트와 우선순위 큐를 사용하는 한 번의 실행 시간은 `O((N + M) log N)`입니다. 두 번 실행해도 점근적 시간 복잡도는 같습니다. 그래프와 두 거리 배열의 공간 복잡도는 `O(N + M)`입니다.

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
