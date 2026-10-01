---
title: BOJ. Bipartite Graph (1707)
author: MINJUN PARK
date: 2021-12-30 20:51:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, DFS, Bipartite Graph, 이분 그래프]
pin: false
lang: ko
translation_key: boj-1707-bipartite-graph
permalink: /ko/posts/boj-1707-bipartite-graph/
---

각 테스트 그래프의 정점에 두 가지 색을 칠합니다. 모든 간선이 서로 다른 색의 정점을 연결할 때, 그리고 그때에만 그래프는 이분 그래프입니다. 그래프가 비연결일 수 있으므로 아직 색칠하지 않은 모든 정점에서 너비 우선 탐색을 시작합니다. 탐색 중 새로 발견한 이웃에는 현재 정점과 반대 색을 지정합니다. 이미 같은 색인 두 정점을 잇는 간선을 발견하면 이분 그래프가 아니며, 이 방식으로 자기 자신을 잇는 간선도 검출할 수 있습니다.

불변식은 지금까지 확인한 모든 간선이 서로 다른 색의 정점을 연결한다는 것입니다. BFS는 각 컴포넌트에서 이 유효한 두 색 분할을 확장합니다. 같은 색 정점을 잇는 간선을 발견하면 모든 간선을 만족하는 분할이 존재할 수 없음을 알 수 있습니다. 각 정점과 간선을 상수 번 처리하므로 시간 복잡도는 `O(V + E)`입니다. 색 배열과 큐의 보조 공간은 `O(V)`이며, 인접 리스트는 `O(V + E)` 공간을 사용합니다.

[문제 링크](https://www.acmicpc.net/problem/1707)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int vertexCount = input.nextInt();
            int edgeCount = input.nextInt();
            int[] head = new int[vertexCount];
            java.util.Arrays.fill(head, -1);
            int[] to = new int[2 * edgeCount];
            int[] next = new int[2 * edgeCount];

            for (int edge = 0; edge < edgeCount; edge++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                to[2 * edge] = b;
                next[2 * edge] = head[a];
                head[a] = 2 * edge;
                to[2 * edge + 1] = a;
                next[2 * edge + 1] = head[b];
                head[b] = 2 * edge + 1;
            }

            int[] color = new int[vertexCount];
            int[] queue = new int[vertexCount];
            boolean bipartite = true;

            for (int start = 0; start < vertexCount && bipartite; start++) {
                if (color[start] != 0) {
                    continue;
                }

                int front = 0;
                int back = 0;
                color[start] = 1;
                queue[back++] = start;
                while (front < back && bipartite) {
                    int vertex = queue[front++];
                    for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                        int neighbor = to[edge];
                        if (color[neighbor] == 0) {
                            color[neighbor] = 3 - color[vertex];
                            queue[back++] = neighbor;
                        } else if (color[neighbor] == color[vertex]) {
                            bipartite = false;
                            break;
                        }
                    }
                }
            }
            output.append(bipartite ? "YES\n" : "NO\n");
        }
        System.out.print(output);
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```
