---
title: BOJ. 특정한 최단 경로 (1504)
author: MINJUN PARK
date: 2022-01-01 03:54:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dijkstra, Graph, Shortest path with specification, 특정한 최단 경로]
pin: false
lang: ko
translation_key: boj-1504-required-path
permalink: /ko/posts/boj-1504-required-path/
---

[문제 링크](https://www.acmicpc.net/problem/1504)

## 풀이

양의 가중치를 갖는 무방향 그래프에서 정점 `v1`, `v2`를 모두 방문하는 최단 경로를 구합니다. 두 필수 정점을 방문하는 순서는 `1 → v1 → v2 → N` 또는 `1 → v2 → v1 → N`뿐입니다. 각 구간의 최단 거리를 더한 두 후보 중 작은 값을 선택하고, 후보 경로가 모두 도달 불가능하면 `-1`을 출력합니다.

시작점 `1`, `v1`, `v2`에서 각각 다익스트라를 실행합니다. 그러면 첫 번째 순서의 길이는 `d(1,v1) + d(v1,v2) + d(v2,N)`, 두 번째는 `d(1,v2) + d(v2,v1) + d(v1,N)`입니다. 간선이 무방향이므로 `d(v1,v2) = d(v2,v1)`입니다. 시작점이나 도착점이 필수 정점과 같아도 거리 `0`으로 자연스럽게 처리됩니다.

거리와 후보 합에는 `long`을 사용하며, 도달 불가능한 구간이 포함된 합은 계산하지 않습니다. 우선순위 큐에 넣은 오래된 항목은 현재 최단 거리와 다르면 버립니다. 시간 복잡도는 `O((V + E) log V)`, 인접 리스트와 거리 배열을 포함한 공간 복잡도는 `O(V + E)`입니다.

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
