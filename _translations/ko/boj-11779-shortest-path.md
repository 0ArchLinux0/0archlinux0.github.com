---
title: BOJ. 최소비용 구하기 2 (11779)
author: MINJUN PARK
date: 2022-01-15 02:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    Get Miminum Cost(2),
    최소비용 구하기 2
  ]
pin: false
lang: ko
translation_key: boj-11779-shortest-path
permalink: /ko/posts/boj-11779-shortest-path/
---

[문제: BOJ 11779 — 최소비용 구하기 2](https://www.acmicpc.net/problem/11779)

음수가 아닌 비용을 가진 방향 그래프에서 지정된 출발점부터 도착점까지의 최소 비용과 해당 경로 하나를 구합니다. 각 방향 간선을 인접 리스트에 저장합니다. 평행 간선도 유효하며 별도로 처리할 필요 없이 모두 보관하면 됩니다.

## 다익스트라 알고리즘

`distance[v]`에는 출발점에서 `v`까지 발견한 최소 비용을, `parent[v]`에는 그 거리를 마지막으로 갱신한 경로에서 직전 정점을 저장합니다. 우선순위 큐에는 `(정점, 거리)` 후보를 넣습니다. 가장 작은 후보를 꺼냈을 때 그 거리가 현재 `distance[정점]`과 다르면 더 좋은 경로로 대체된 오래된 항목이므로 버립니다. 그렇지 않으면 해당 정점의 모든 출발 간선을 완화하고, 이웃까지의 거리가 줄어들 때 새 후보를 큐에 넣습니다.

불변식은 저장된 각 거리가 실제로 발견한 경로의 비용이며, 큐의 각 항목도 경로 후보를 나타낸다는 것입니다. 모든 간선 비용이 음수가 아니므로 현재 유효한 거리 중 가장 작은 상태를 꺼냈을 때, 아직 처리하지 않은 정점을 거치는 경로가 그 거리를 더 줄일 수 없습니다. 따라서 완화를 반복하면 도달 가능한 각 정점의 최소 거리를 구합니다. 거리가 개선될 때마다 부모를 그 경로의 직전 정점으로 기록하므로 도착점에서 출발점까지 부모를 따라가면 최소 비용 경로가 역순으로 복원되고, 이를 뒤집으면 출력 순서가 됩니다. 출발점과 도착점이 같으면 경로에는 그 정점 하나만 포함됩니다.

거리와 간선 비용은 `long`으로 처리하고, 우선순위 큐는 뺄셈 대신 `Long.compare`로 비교해 비교식의 오버플로 위험을 피합니다. 출력은 정확히 세 줄로, 최소 비용, 경로에 포함된 정점 수, 경로 순서의 정점을 출력합니다.

지연 삭제를 사용하는 이진 힙 다익스트라의 시간 복잡도는 `O((V + E) log(E + 1))`, 공간 복잡도는 `O(V + E)`입니다. 단순 그래프에서는 흔히 시간 복잡도를 `O((V + E) log(V + 1))`로 씁니다. 평행 간선을 포함하는 일반적인 경우에는 위의 `log(E + 1)` 표기가 더 넓게 적용됩니다.

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
        final long cost;

        Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
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
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long cost = input.nextLong();
            graph.get(from).add(new Edge(to, cost));
        }

        int source = input.nextInt() - 1;
        int destination = input.nextInt() - 1;

        long[] distance = new long[vertexCount];
        int[] parent = new int[vertexCount];
        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);
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
                long candidate = current.distance + edge.cost;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    parent[edge.to] = current.vertex;
                    queue.add(new State(edge.to, candidate));
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int vertex = destination; vertex != -1; vertex = parent[vertex]) {
            path.add(vertex);
            if (vertex == source) {
                break;
            }
        }
        java.util.Collections.reverse(path);

        StringBuilder output = new StringBuilder();
        output.append(distance[destination]).append('\n');
        output.append(path.size()).append('\n');
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                output.append(' ');
            }
            output.append(path.get(i) + 1);
        }
        output.append('\n');
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
