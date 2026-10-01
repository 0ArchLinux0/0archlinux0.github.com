---
title: BOJ. 惑星トンネル (2887)
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
    惑星トンネル,
    Review
  ]
pin: false
lang: ja
translation_key: boj-2887-planet-tunnel-mst
permalink: /ja/posts/boj-2887-planet-tunnel-mst/
source_permalink: /posts/BOJ-2887/
---

[問題: BOJ 2887 — 惑星トンネル](https://www.acmicpc.net/problem/2887)

## クラスカル法のための疎な候補辺

惑星 `u` と `v` を結ぶトンネルの費用は `min(|x[u] - x[v]|, |y[u] - y[v]|, |z[u] - z[v]|)` です。すべての惑星ペアを辺にすると `N(N - 1) / 2` 本になり、`N` が大きい場合には効率的ではありません。そこで、各座標軸ごとに惑星をソートし、その順序で隣り合うペアだけを候補辺として追加します。候補辺は最大 `3(N - 1)` 本です。

候補辺だけで十分な理由を考えます。最小全域木に含まれる任意の辺を取り、その辺の費用と等しい座標差を持つ軸を選びます。その差を `d` とします。選んだ軸の順序で両端点の間に別の惑星があれば、両端点は隣り合っていません。両端点を結ぶソート済みの連続ペアでは、その軸の差はすべて `d` より小さくなります。実際のトンネル費用は三つの座標差の最小値なので、各連続ペアの辺の費用はその軸の差以下です。したがって、元の辺よりすべて安い辺からなる経路ができます。元の辺を木から取り除くと二つの連結成分に分かれますが、この経路は必ずその二成分をまたぎます。より安い辺で再接続できるため、元の木が最小全域木であるという仮定に矛盾します。よって、最小全域木の各辺は少なくとも一つの座標軸のソート順で隣り合う惑星同士を結び、候補に含まれます。

候補辺の費用には、ペアを見つけるために使った軸の差ではなく、必ず三つの軸の差の最小値を使います。クラスカル法では候補辺を費用順に並べ、互いに素な集合を管理して異なる連結成分を結ぶ辺だけを採用します。`N = 1` なら候補辺はなく、合計費用は 0 です。座標差は絶対値を取る前に `long` に変換し、最小全域木の合計も `long` で保持します。

三つの座標ソートと候補辺のソートはそれぞれ `O(N log N)`、候補辺数は `O(N)` です。クラスカル法の互いに素な集合の操作は `O(N α(N))` なので、全体の時間計算量は `O(N log N)`、空間計算量は `O(N)` です。

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

[English source](/posts/BOJ-2887/) · [한국어 버전](/ko/posts/boj-2887-planet-tunnel-mst/)
