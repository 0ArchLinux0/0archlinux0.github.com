---
title: BOJ 4013 - ATM
author: MINJUN PARK
date: 2022-02-15 19:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, SCC, 그래프, ATM]
pin: false
lang: ko
translation_key: boj-4013-atm
permalink: /ko/posts/boj-4013-atm/
source_permalink: /posts/BOJ-4013/
---

[문제: BOJ 4013 — ATM](https://www.acmicpc.net/problem/4013) · [English](/posts/BOJ-4013/) · [日本語](/ja/posts/boj-4013-atm/)

## 풀이

시작점에서 출발해 식당에 도착하는 경로 중 모을 수 있는 돈의 최댓값을 구합니다. 강한 연결 요소(SCC) 안에서는 모든 정점을 서로 오갈 수 있으므로 그 요소의 돈을 전부 모을 수 있습니다. 각 SCC를 구성 정점의 돈 합으로 가중치를 둔 정점 하나로 압축하면 방향 비순환 그래프(DAG)가 됩니다.

시작 SCC에서 도달할 수 있는 요소만 고려합니다. 압축 DAG의 위상 순회로 `best[c]`, 즉 SCC `c`에 도착할 때 얻을 수 있는 최대 금액을 계산합니다. 시작 SCC는 그 SCC의 돈 합으로 초기화하고, 다른 SCC는 도달 불가로 둡니다. 간선 `c → next`마다 `best[c] + money[next]`로 갱신합니다. 도달 가능한 식당을 포함하는 SCC 중 `best[c]`의 최댓값이 정답입니다. 이 방식은 여러 경로 중 최댓값을 선택하고, 시작점에서 도달할 수 없는 식당은 제외합니다.

코사라주 알고리즘의 두 순회는 모두 반복문으로 구현합니다. 첫 번째 순회는 명시적 스택과 정점별 간선 커서로 DFS 종료 순서를 기록하고, 두 번째 순회는 역방향 그래프를 스택으로 탐색해 SCC 번호를 매깁니다. 정점마다 객체 리스트를 두지 않고 원시 배열 인접 리스트를 사용합니다. 원 그래프, 역방향 그래프, 압축 그래프의 배열은 입력 간선 수에 맞춰 할당합니다. 시간과 메모리 복잡도는 `O(N + M)`이며, 돈과 DP 값은 `long`으로 처리합니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final long UNREACHABLE = Long.MIN_VALUE;

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length, position;

        private int read() throws IOException {
            if (position == length) {
                length = in.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do c = read(); while (c <= ' ' && c != -1);
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();

        int[] head = new int[n];
        int[] reverseHead = new int[n];
        Arrays.fill(head, -1);
        Arrays.fill(reverseHead, -1);
        int[] to = new int[m];
        int[] next = new int[m];
        int[] reverseTo = new int[m];
        int[] reverseNext = new int[m];

        for (int edge = 0; edge < m; edge++) {
            int from = fs.nextInt() - 1;
            int dest = fs.nextInt() - 1;
            to[edge] = dest;
            next[edge] = head[from];
            head[from] = edge;
            reverseTo[edge] = from;
            reverseNext[edge] = reverseHead[dest];
            reverseHead[dest] = edge;
        }

        long[] vertexMoney = new long[n];
        for (int v = 0; v < n; v++) vertexMoney[v] = fs.nextInt();
        int start = fs.nextInt() - 1;
        int restaurantCount = fs.nextInt();
        boolean[] isRestaurant = new boolean[n];
        for (int i = 0; i < restaurantCount; i++) {
            isRestaurant[fs.nextInt() - 1] = true;
        }

        // Iterative first Kosaraju pass: record DFS finishing order.
        boolean[] visited = new boolean[n];
        int[] order = new int[n];
        int orderSize = 0;
        int[] stackVertex = new int[n];
        int[] stackEdge = new int[n];
        for (int root = 0; root < n; root++) {
            if (visited[root]) continue;
            int top = 0;
            stackVertex[0] = root;
            stackEdge[0] = head[root];
            visited[root] = true;
            while (top >= 0) {
                int edge = stackEdge[top];
                if (edge == -1) {
                    order[orderSize++] = stackVertex[top--];
                    continue;
                }
                stackEdge[top] = next[edge];
                int neighbor = to[edge];
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stackVertex[++top] = neighbor;
                    stackEdge[top] = head[neighbor];
                }
            }
        }

        // Iterative second pass on the reversed graph.
        int[] component = new int[n];
        Arrays.fill(component, -1);
        int componentCount = 0;
        int[] stack = new int[n];
        for (int i = orderSize - 1; i >= 0; i--) {
            int root = order[i];
            if (component[root] != -1) continue;
            int top = 0;
            stack[0] = root;
            component[root] = componentCount;
            while (top >= 0) {
                int vertex = stack[top--];
                for (int edge = reverseHead[vertex]; edge != -1; edge = reverseNext[edge]) {
                    int neighbor = reverseTo[edge];
                    if (component[neighbor] == -1) {
                        component[neighbor] = componentCount;
                        stack[++top] = neighbor;
                    }
                }
            }
            componentCount++;
        }

        long[] componentMoney = new long[componentCount];
        boolean[] componentHasRestaurant = new boolean[componentCount];
        for (int v = 0; v < n; v++) {
            int c = component[v];
            componentMoney[c] += vertexMoney[v];
            if (isRestaurant[v]) componentHasRestaurant[c] = true;
        }

        // Keep cross-component edges in primitive adjacency arrays; parallel edges are harmless.
        int[] dagHead = new int[componentCount];
        Arrays.fill(dagHead, -1);
        int[] dagTo = new int[m];
        int[] dagNext = new int[m];
        int[] indegree = new int[componentCount];
        int dagEdges = 0;
        for (int v = 0; v < n; v++) {
            for (int edge = head[v]; edge != -1; edge = next[edge]) {
                int fromComponent = component[v];
                int toComponent = component[to[edge]];
                if (fromComponent != toComponent) {
                    dagTo[dagEdges] = toComponent;
                    dagNext[dagEdges] = dagHead[fromComponent];
                    dagHead[fromComponent] = dagEdges++;
                    indegree[toComponent]++;
                }
            }
        }

        // Kahn's algorithm processes every DAG edge after its source component.
        int[] queue = new int[componentCount];
        int front = 0, back = 0;
        for (int c = 0; c < componentCount; c++) {
            if (indegree[c] == 0) queue[back++] = c;
        }
        long[] best = new long[componentCount];
        Arrays.fill(best, UNREACHABLE);
        best[component[start]] = componentMoney[component[start]];
        while (front < back) {
            int current = queue[front++];
            for (int edge = dagHead[current]; edge != -1; edge = dagNext[edge]) {
                int following = dagTo[edge];
                if (best[current] != UNREACHABLE) {
                    best[following] = Math.max(best[following],
                            best[current] + componentMoney[following]);
                }
                if (--indegree[following] == 0) queue[back++] = following;
            }
        }

        long answer = 0;
        for (int c = 0; c < componentCount; c++) {
            if (componentHasRestaurant[c] && best[c] != UNREACHABLE) {
                answer = Math.max(answer, best[c]);
            }
        }
        System.out.println(answer);
    }
}
```
