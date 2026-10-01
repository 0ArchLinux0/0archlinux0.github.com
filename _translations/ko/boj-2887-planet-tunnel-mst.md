---
title: BOJ. 행성 터널 (2887)
author: MINJUN PARK
date: 2022-01-22 17:09:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Graph,
    Kruskal,
    BOJ,
    Planet Tunnel,
    행성 터널,
    Review
  ]
pin: false
lang: ko
translation_key: boj-2887-planet-tunnel-mst
permalink: /ko/posts/boj-2887-planet-tunnel-mst/
source_permalink: /posts/BOJ-2887/
---

[문제: BOJ 2887 — 행성 터널](https://www.acmicpc.net/problem/2887)

## 크루스칼을 위한 희소 후보 간선

행성 `u`, `v`를 잇는 터널의 비용은 `min(|x[u] - x[v]|, |y[u] - y[v]|, |z[u] - z[v]|)`입니다. 행성 쌍을 모두 간선으로 만들면 `N(N - 1) / 2`개가 되어 큰 입력에서 비효율적입니다. 대신 각 좌표축별로 행성을 정렬하고, 정렬 순서에서 이웃한 행성 쌍만 후보 간선으로 추가합니다. 후보는 최대 `3(N - 1)`개입니다.

이 후보만으로 충분한 이유를 살펴봅니다. 최소 신장 트리의 임의의 간선 하나를 잡고, 그 간선 비용과 같은 절댓값 차이를 만드는 좌표축을 하나 선택합니다. 그 차이를 `d`라고 하겠습니다. 선택한 축에서 두 끝점 사이에 다른 행성이 있다면 둘은 정렬 순서상 이웃이 아닙니다. 두 끝점을 잇는 정렬된 연속 간선들의 해당 축 차이는 모두 `d`보다 작습니다. 실제 터널 비용은 세 축 차이의 최솟값이므로 각 연속 행성 쌍의 간선 비용은 그 축의 차이 이하입니다. 따라서 이 간선들은 원래 간선보다 모두 싼 경로를 이룹니다. 원래 간선을 트리에서 제거하면 두 컴포넌트로 나뉘고, 그 경로는 반드시 두 컴포넌트 사이를 가로지릅니다. 더 싼 간선으로 다시 연결할 수 있으므로 최소 신장 트리라는 가정에 모순입니다. 따라서 최소 신장 트리의 모든 간선은 적어도 한 좌표축의 정렬에서 이웃한 행성 사이에 있으며, 후보에 포함됩니다.

후보 간선의 비용은 후보를 찾을 때 사용한 축의 차이가 아니라 반드시 세 축 차이의 최솟값으로 계산합니다. 크루스칼 알고리즘은 후보 간선을 비용순으로 정렬하고, 서로 다른 컴포넌트를 연결하는 간선만 서로소 집합 자료구조로 선택합니다. `N = 1`이면 후보 간선이 없고 답은 0입니다. 좌표 차이는 절댓값을 구하기 전에 `long`으로 변환하고, 전체 비용도 `long`으로 누적합니다.

세 번의 좌표 정렬과 후보 간선 정렬은 모두 `O(N log N)`이며 후보 수는 `O(N)`입니다. 크루스칼의 서로소 집합 연산은 `O(N α(N))`이므로 전체 시간 복잡도는 `O(N log N)`, 공간 복잡도는 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static class Planet {
        int x;
        int y;
        int z;
        int id;

        Planet(int x, int y, int z, int id) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.id = id;
        }
    }

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        long cost;

        Edge(int from, int to, long cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return Long.compare(cost, other.cost);
        }
    }

    static class DisjointSet {
        int[] parent;
        int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }

        int find(int value) {
            if (parent[value] != value) parent[value] = find(parent[value]);
            return parent[value];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) return false;
            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
            return true;
        }
    }

    static long difference(int a, int b) {
        return Math.abs((long) a - b);
    }

    static long tunnelCost(Planet a, Planet b) {
        return Math.min(difference(a.x, b.x),
                Math.min(difference(a.y, b.y), difference(a.z, b.z)));
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        Planet[] planets = new Planet[n];
        for (int i = 0; i < n; i++) {
            planets[i] = new Planet(input.nextInt(), input.nextInt(), input.nextInt(), i);
        }

        List<Edge> candidates = new ArrayList<>(Math.max(0, 3 * (n - 1)));
        Planet[] ordered = planets.clone();
        for (int axis = 0; axis < 3; axis++) {
            final int coordinateAxis = axis;
            Arrays.sort(ordered, (a, b) -> Integer.compare(
                    coordinate(a, coordinateAxis), coordinate(b, coordinateAxis)));
            for (int i = 0; i + 1 < n; i++) {
                Planet a = ordered[i];
                Planet b = ordered[i + 1];
                candidates.add(new Edge(a.id, b.id, tunnelCost(a, b)));
            }
        }

        candidates.sort(null);
        DisjointSet sets = new DisjointSet(n);
        long total = 0;
        int used = 0;
        for (Edge edge : candidates) {
            if (sets.union(edge.from, edge.to)) {
                total += edge.cost;
                if (++used == n - 1) break;
            }
        }
        System.out.println(total);
    }

    static int coordinate(Planet planet, int axis) {
        if (axis == 0) return planet.x;
        if (axis == 1) return planet.y;
        return planet.z;
    }

    static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr;
        private int len;

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

        private int read() throws IOException {
            if (ptr == len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) return -1;
            }
            return buffer[ptr++];
        }
    }
}
```

[English source](/posts/BOJ-2887/) · [日本語版](/ja/posts/boj-2887-planet-tunnel-mst/)
