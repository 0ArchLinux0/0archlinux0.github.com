---
title: BOJ. Time Machine (11657)
author: MINJUN PARK
date: 2022-01-03 11:45:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Bellman Ford,
    벨만 포드,
    Graph,
    Time Machine,
    타임머신,
    Review
  ]
pin: false
lang: ko
translation_key: boj-11657-time-machine
permalink: /ko/posts/boj-11657-time-machine/
---

## 풀이

이 문제는 방향 그래프이며 음수 가중치 간선이 있으므로 다익스트라 알고리즘을 사용할 수 없습니다. 벨만–포드는 1번 정점에서 각 정점까지 알려진 최단 거리를 저장하고, 모든 간선을 최대 `V - 1`회 완화합니다. 음수 사이클을 포함하지 않는 최단 경로는 최대 `V - 1`개의 간선으로 이루어지므로, 한 번의 순회에서 거리가 바뀌지 않으면 조기에 종료해도 됩니다.

거리 배열은 실제 `long` 무한대 값으로 초기화하고 1번 정점만 0으로 둡니다. 간선을 완화하기 전에 시작 정점의 거리가 무한대인지 확인하고, 그렇다면 건너뜁니다. 이 방식은 무한대 센티널에 산술 연산을 하지 않으며, 1번 정점에서 도달할 수 없는 음수 사이클이 오탐되는 것도 막습니다. 완화 과정을 마친 뒤 한 번 더 간선을 확인해, 유한한 거리에서 여전히 완화할 수 있다면 도달 가능한 음수 사이클이므로 `-1`을 출력합니다. 그런 사이클이 없다면 2번부터 `N`번 정점까지의 거리를 출력하고, 도달할 수 없는 정점은 `-1`로 출력합니다.

벨만–포드는 음수 간선을 처리할 수 있습니다. 최악 시간 복잡도는 `O(VE)`, 공간 복잡도는 `O(V + E)`입니다.

[문제 링크](https://www.acmicpc.net/problem/11657)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static class Edge {
        final int from;
        final int to;
        final long weight;

        Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();
        List<Edge> edges = new ArrayList<>(edgeCount);

        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long weight = input.nextLong();
            edges.add(new Edge(from, to, weight));
        }

        long[] distance = new long[vertexCount];
        Arrays.fill(distance, INF);
        distance[0] = 0;

        for (int pass = 0; pass < vertexCount - 1; pass++) {
            boolean changed = false;
            for (Edge edge : edges) {
                if (distance[edge.from] == INF) {
                    continue;
                }
                long candidate = distance[edge.from] + edge.weight;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }

        for (Edge edge : edges) {
            if (distance[edge.from] != INF
                    && distance[edge.from] + edge.weight < distance[edge.to]) {
                System.out.println(-1);
                return;
            }
        }

        StringBuilder output = new StringBuilder();
        for (int vertex = 1; vertex < vertexCount; vertex++) {
            output.append(distance[vertex] == INF ? -1 : distance[vertex]).append('\n');
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
