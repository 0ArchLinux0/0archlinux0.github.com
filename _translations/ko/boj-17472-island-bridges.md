---
title: BOJ. 다리 만들기 2 (17472)
author: MINJUN PARK
date: 2022-01-23 04:39:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Graph, Union Find, BOJ, 다리 만들기 2]
pin: false
lang: ko
translation_key: boj-17472-island-bridges
permalink: /ko/posts/boj-17472-island-bridges/
source_permalink: /posts/BOJ-17472/
---

[문제: BOJ 17472 — 다리 만들기 2](https://www.acmicpc.net/problem/17472)

[English](/posts/BOJ-17472/) · [日本語](/ja/posts/boj-17472-island-bridges/)

## 풀이

각각의 연결된 땅을 그래프의 정점으로 봅니다. 먼저 플러드 필로 섬마다 번호를 붙입니다. 그런 다음 모든 행과 열을 살펴봅니다. 각 섬 칸에서 한 방향으로 물을 따라가 다음 땅이나 지도의 경계까지 진행합니다. 서로 다른 섬에 도달하고 물 칸을 두 칸 이상 지났을 때만 다리 후보가 됩니다. 이 과정은 가로와 세로, 양 방향을 모두 확인합니다. 같은 섬 쌍을 잇는 후보 중 가장 짧은 다리만 남깁니다. 같은 두 정점을 잇는 더 긴 간선은 최소 신장 트리에 도움이 되지 않습니다.

후보 다리들은 가중치가 있는 무방향 그래프를 이룹니다. 크루스칼 알고리즘으로 서로 다른 컴포넌트를 잇는 가장 짧은 간선을 선택합니다. 선택한 다리가 정확히 `섬의 수 - 1`개가 아니라면 모든 섬을 연결할 수 없으므로 `-1`을 출력합니다. 섬이 하나뿐이면 다리가 필요 없으므로 답은 `0`입니다.

격자 크기는 최대 `10 × 10`, 섬의 수는 최대 6개입니다. 플러드 필과 두 축의 스캔은 `O(NM)` 시간이 걸립니다. 후보 간선은 최대 `I(I - 1) / 2`개이므로 정렬을 포함한 전체 시간 복잡도는 `O(NM + I² log I)`, 공간 복잡도는 `O(NM + I²)`입니다. 여기서 `I`는 섬의 수입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Main {
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int rows = input.nextInt();
        int columns = input.nextInt();
        int[][] map = new int[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                map[r][c] = input.nextInt();
            }
        }

        int islandCount = labelIslands(map, rows, columns);
        if (islandCount == 1) {
            System.out.println(0);
            return;
        }
        if (islandCount == 0) {
            System.out.println(-1);
            return;
        }

        int[][] shortest = new int[islandCount][islandCount];
        for (int i = 0; i < islandCount; i++) {
            for (int j = 0; j < islandCount; j++) {
                shortest[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (map[r][c] == 0) {
                    continue;
                }
                for (int direction = 0; direction < 4; direction++) {
                    int nextRow = r + DR[direction];
                    int nextColumn = c + DC[direction];
                    int length = 0;
                    while (inside(nextRow, nextColumn, rows, columns)
                            && map[nextRow][nextColumn] == 0) {
                        length++;
                        nextRow += DR[direction];
                        nextColumn += DC[direction];
                    }
                    if (length >= 2 && inside(nextRow, nextColumn, rows, columns)
                            && map[nextRow][nextColumn] != map[r][c]) {
                        int from = map[r][c] - 2;
                        int to = map[nextRow][nextColumn] - 2;
                        shortest[from][to] = Math.min(shortest[from][to], length);
                        shortest[to][from] = Math.min(shortest[to][from], length);
                    }
                }
            }
        }

        List<Edge> edges = new ArrayList<>();
        for (int a = 0; a < islandCount; a++) {
            for (int b = a + 1; b < islandCount; b++) {
                if (shortest[a][b] != Integer.MAX_VALUE) {
                    edges.add(new Edge(a, b, shortest[a][b]));
                }
            }
        }
        edges.sort(Comparator.comparingInt(edge -> edge.length));

        DisjointSet sets = new DisjointSet(islandCount);
        int totalLength = 0;
        int bridgesUsed = 0;
        for (Edge edge : edges) {
            if (sets.union(edge.from, edge.to)) {
                totalLength += edge.length;
                bridgesUsed++;
                if (bridgesUsed == islandCount - 1) {
                    break;
                }
            }
        }

        System.out.println(bridgesUsed == islandCount - 1 ? totalLength : -1);
    }

    private static int labelIslands(int[][] map, int rows, int columns) {
        int islandCount = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (map[r][c] != 1) {
                    continue;
                }
                int label = ++islandCount + 1;
                map[r][c] = label;
                queue.offer(r * columns + c);
                while (!queue.isEmpty()) {
                    int cell = queue.poll();
                    int currentRow = cell / columns;
                    int currentColumn = cell % columns;
                    for (int direction = 0; direction < 4; direction++) {
                        int nextRow = currentRow + DR[direction];
                        int nextColumn = currentColumn + DC[direction];
                        if (inside(nextRow, nextColumn, rows, columns)
                                && map[nextRow][nextColumn] == 1) {
                            map[nextRow][nextColumn] = label;
                            queue.offer(nextRow * columns + nextColumn);
                        }
                    }
                }
            }
        }
        return islandCount;
    }

    private static boolean inside(int row, int column, int rows, int columns) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    private static class Edge {
        final int from;
        final int to;
        final int length;

        Edge(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int count) {
            parent = new int[count];
            size = new int[count];
            for (int i = 0; i < count; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int value) {
            if (parent[value] != value) {
                parent[value] = find(parent[value]);
            }
            return parent[value];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return false;
            }
            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
            return true;
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
