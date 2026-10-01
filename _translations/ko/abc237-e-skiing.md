---
title: AtCoder ABC 237 E — Skiing
author: MINJUN PARK
date: 2022-01-31 08:48:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 237, 그래프, 다익스트라]
pin: false
lang: ko
translation_key: abc237-e-skiing
permalink: /ko/posts/abc237-e-skiing/
source_permalink: /posts/Atcoder-E-Skiing/
---

[문제: AtCoder ABC 237 E — Skiing](https://atcoder.jp/contests/abc237/tasks/abc237_e) · [English](/posts/Atcoder-E-Skiing/) · [日本語](/ja/posts/abc237-e-skiing/)

정점 1에서 정점 `v`까지 가는 경로에서 오른 총 높이를 `U`, 내려간 총 높이를 `D`라고 하겠습니다. 경로의 행복도 변화량은 `D - 2U`입니다. 1만큼 내려가면 행복도가 1 증가하고, 1만큼 오르면 2 감소하기 때문입니다. 전체 높이 변화는 `H[v] - H[1] = U - D`이므로 `D = U + H[1] - H[v]`이고, 행복도는 `H[1] - H[v] - U`로 정리됩니다. 목적지 `v`를 고정하면 높이는 정해져 있으므로, 행복도를 최대화하려면 총 상승량 `U`를 최소화해야 합니다.

각 양방향 도로 `u-v`에 두 방향의 비용을 부여합니다. `u`에서 `v`로 갈 때의 비용은 해당 구간에서 오른 높이인 `max(0, H[v] - H[u])`이고, 반대 방향 비용은 `max(0, H[u] - H[v])`입니다. 내리막과 높이가 같은 구간은 비용이 0입니다. 모든 비용이 음수가 아니므로 다익스트라 알고리즘으로 정점 1에서 각 도달 가능한 정점까지의 최소 누적 상승량을 구할 수 있습니다. 도로 자체는 양방향이어도 방향별 비용은 다를 수 있으며, 특히 같은 높이의 도로는 양쪽 모두 비용이 0입니다.

도달 가능한 각 정점 `v`의 행복도는 `H[1] - H[v] - dist[v]`입니다. `dist[v]`는 최소 누적 상승량입니다. 시작점에서 갈 수 없는 정점은 경로가 없으므로 계산에서 제외해야 합니다. 시작점은 자기 자신에게 행복도 0으로 도달 가능하므로 답을 0으로 초기화합니다. 높이 차이와 거리, 행복도에는 `long`을 사용해 누적 중 오버플로를 방지합니다. 시간 복잡도는 `O((N + M) log N)`, 공간 복잡도는 `O(N + M)`입니다.

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
