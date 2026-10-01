---
title: BOJ. 미확인 도착지 (9370)
author: MINJUN PARK
date: 2022-01-02 01:34:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dijkstra, Graph, Unidentified destination, 미확인 도착지]
pin: false
lang: ko
translation_key: boj-9370-unidentified-destination
permalink: /ko/posts/boj-9370-unidentified-destination/
---

[문제 링크](https://www.acmicpc.net/problem/9370)

## 풀이

각 테스트 케이스에서 출발점 `s`와 지정 간선의 양 끝점 `g`, `h`를 시작점으로 다익스트라를 실행합니다. 도착 후보 `x`는 `s`에서 `x`로 가는 최단 경로 중 지정된 간선 `g-h`를 지나는 경로가 있을 때 정답입니다. 따라서 최단 거리가 다음 두 경로 길이 중 하나와 같아야 합니다.

- `dist(s, g) + w(g, h) + dist(h, x)`
- `dist(s, h) + w(g, h) + dist(g, x)`

두 식은 무방향 간선 `g-h`를 각각 반대 방향으로 지나는 경우를 나타냅니다. `g`와 `h` 사이에 평행 간선이 여러 개라면 그중 최소 가중치를 사용합니다. 더 무거운 평행 간선은 최단 경로를 만들 수 없고, 최소 간선은 그래프에서 사용할 수 있기 때문입니다. 구간 중 도달할 수 없는 곳이 있으면 해당 식은 유효하지 않으므로 거리를 더하기 전에 `INF`인지 확인합니다.

정확성은 다음과 같이 보일 수 있습니다. 최단 경로가 `g-h`를 사용한다면, 해당 간선에 도달하기 전의 구간과 반대편 끝점에서 목적지까지의 구간은 각각 최단이어야 합니다. 그렇지 않으면 그 구간을 더 짧은 경로로 바꿔 전체 경로를 단축할 수 있습니다. 따라서 전체 길이는 위 두 식 중 하나와 같습니다. 반대로 두 식 중 하나가 `dist(s, x)`와 같다면, 그 식의 최단 구간들을 `g-h` 간선과 이어 붙인 경로도 최단 경로이며 해당 간선을 사용합니다.

그래프는 인접 리스트로 저장하고, 지연 삭제를 사용하는 최소 우선순위 큐로 다익스트라를 수행합니다. 거리와 간선 가중치는 `long`으로 저장하며, 큐 정렬에는 뺄셈 대신 `Long.compare`를 사용해 비교 오버플로를 방지합니다. 후보 정점은 번호순으로 정렬해 출력합니다. 테스트 케이스당 시간 복잡도는 `O((V + E) log V + C log C)`이며, `C`는 후보 수입니다. 공간 복잡도는 `O(V + E)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int n = input.nextInt();
            int m = input.nextInt();
            int candidateCount = input.nextInt();
            int source = input.nextInt() - 1;
            int g = input.nextInt() - 1;
            int h = input.nextInt() - 1;

            List<Edge>[] graph = new ArrayList[n];
            for (int vertex = 0; vertex < n; vertex++) {
                graph[vertex] = new ArrayList<>();
            }

            long ghWeight = INF;
            for (int i = 0; i < m; i++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                long weight = input.nextLong();
                graph[a].add(new Edge(b, weight));
                graph[b].add(new Edge(a, weight));
                if ((a == g && b == h) || (a == h && b == g)) {
                    ghWeight = Math.min(ghWeight, weight);
                }
            }

            long[] fromSource = dijkstra(graph, source);
            long[] fromG = dijkstra(graph, g);
            long[] fromH = dijkstra(graph, h);

            List<Integer> candidates = new ArrayList<>(candidateCount);
            for (int i = 0; i < candidateCount; i++) {
                int candidate = input.nextInt() - 1;
                long shortest = fromSource[candidate];
                if (shortest != INF
                        && ghWeight != INF
                        && (equalsRoute(shortest, fromSource[g], ghWeight, fromH[candidate])
                        || equalsRoute(shortest, fromSource[h], ghWeight, fromG[candidate]))) {
                    candidates.add(candidate + 1);
                }
            }

            Collections.sort(candidates);
            for (int candidate : candidates) {
                output.append(candidate).append(' ');
            }
            output.append('\n');
        }
        System.out.print(output);
    }

    private static boolean equalsRoute(long shortest, long first, long edge, long last) {
        return first != INF && edge != INF && last != INF
                && shortest == first + edge + last;
    }

    private static long[] dijkstra(List<Edge>[] graph, int start) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, INF);
        distance[start] = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                (left, right) -> Long.compare(left.distance, right.distance));
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

        int nextInt() {
            return (int) nextLong();
        }
    }
}
```
