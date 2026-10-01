---
title: BOJ. 트리의 지름 (1167)
author: MINJUN PARK
date: 2022-01-06 11:06:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Diameter of Tree, 트리의 지름]
pin: false
lang: ko
translation_key: boj-1167-tree-diameter
permalink: /ko/posts/boj-1167-tree-diameter/
source_permalink: /posts/BOJ-1167/
---

[문제 링크](https://www.acmicpc.net/problem/1167)

가중치가 음이 아닌 트리에서 임의의 정점으로부터 가장 먼 정점 `a`를 찾고, 이어서 `a`에서 가장 먼 정점을 찾으면 두 번째 탐색에서 얻은 거리가 트리의 지름입니다. 임의의 시작점에서 가장 먼 정점은 지름의 한 끝점입니다. 지름 경로와 시작점에서 뻗는 경로를 함께 살펴보면, 트리의 유일한 경로와 음이 아닌 가중치의 성질에 따라 지름의 양 끝점 중 하나는 시작점에서 적어도 그만큼 멀고, 가장 먼 정점에서 다시 탐색하면 지름의 다른 끝점에 도달합니다. 이는 가중치 트리에 대한 표준적인 두 번 탐색 성질입니다.

입력에는 각 정점의 인접 목록이 주어지며, 각 무방향 간선은 양 끝 정점의 목록에 이미 한 번씩 등장합니다. 따라서 입력에 있는 방향의 인접 정보만 추가해야 합니다. 역방향 간선을 다시 추가하면 간선이 중복됩니다. 각 목록은 정점 번호 `-1`로 끝나므로, 간선 가중치를 읽기 전에 이를 먼저 확인해야 합니다. 두 탐색 모두 명시적인 스택으로 구현해 긴 경로에서도 재귀 깊이 제한을 피합니다. 경로 길이 누적에는 `long`을 사용합니다. 정점이 하나뿐인 경우 간선이 없어 두 탐색 모두 해당 정점만 방문하고 지름 `0`을 반환합니다.

그래프 구성과 두 번의 탐색은 `V`개 정점에 대해 `O(V)` 시간(`E = V - 1`)이 걸립니다. 인접 목록, 스택, 방문 및 거리 배열의 공간 복잡도는 `O(V)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        List<Edge>[] graph = new List[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < vertexCount; i++) {
            int vertex = input.nextInt() - 1;
            while (true) {
                int neighbor = input.nextInt();
                if (neighbor == -1) break;
                long weight = input.nextInt();
                graph[vertex].add(new Edge(neighbor - 1, weight));
            }
        }

        int endpoint = farthestVertex(graph, 0).vertex;
        long diameter = farthestVertex(graph, endpoint).distance;
        System.out.println(diameter);
    }

    private static Result farthestVertex(List<Edge>[] graph, int start) {
        int[] stack = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        long[] distance = new long[graph.length];
        int size = 0;
        stack[size++] = start;
        visited[start] = true;
        int farthest = start;

        while (size > 0) {
            int vertex = stack[--size];
            if (distance[vertex] > distance[farthest]) {
                farthest = vertex;
            }
            for (Edge edge : graph[vertex]) {
                if (visited[edge.to]) continue;
                visited[edge.to] = true;
                distance[edge.to] = distance[vertex] + edge.weight;
                stack[size++] = edge.to;
            }
        }
        return new Result(farthest, distance[farthest]);
    }

    private static final class Edge {
        final int to;
        final long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static final class Result {
        final int vertex;
        final long distance;

        Result(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }
}
```
