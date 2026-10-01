---
title: BOJ. 橋の建設 2 (17472)
author: MINJUN PARK
date: 2022-01-23 04:39:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Graph, Union Find, BOJ, 橋の建設 2]
pin: false
lang: ja
translation_key: boj-17472-island-bridges
permalink: /ja/posts/boj-17472-island-bridges/
source_permalink: /posts/BOJ-17472/
---

[問題: BOJ 17472 — 橋の建設 2](https://www.acmicpc.net/problem/17472)

[English](/posts/BOJ-17472/) · [한국어](/ko/posts/boj-17472-island-bridges/)

## 方針

連結した陸地の各まとまりを、グラフの頂点として扱います。まずフラッドフィルで島ごとに番号を付けます。次にすべての行と列を調べます。各島のマスから一方向へ水上を進み、次の陸地または地図の端まで確認します。異なる島に到達し、間の水マスが2個以上の場合だけ橋の候補になります。この処理で横・縦の両方向をすべて調べられます。同じ島の組を結ぶ候補のうち最短のものだけを残します。同じ2頂点を結ぶ長い辺は、最小全域木に役立ちません。

候補の橋は重み付き無向グラフを作ります。クラスカル法で異なる連結成分を結ぶ最短の辺を選びます。選んだ橋がちょうど`島の数 - 1`本でなければ、すべての島を接続できないため`-1`を出力します。島が1つだけなら橋は不要なので答えは`0`です。

格子の大きさは最大`10 × 10`、島の数は最大6です。フラッドフィルと2軸の走査は`O(NM)`時間です。候補辺は最大`I(I - 1) / 2`本なので、ソートを含む全体の時間計算量は`O(NM + I² log I)`、空間計算量は`O(NM + I²)`です。ここで`I`は島の数です。

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
