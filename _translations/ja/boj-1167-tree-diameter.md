---
title: BOJ. 木の直径 (1167)
author: MINJUN PARK
date: 2022-01-06 11:06:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Diameter of Tree, 트리의 지름]
pin: false
lang: ja
translation_key: boj-1167-tree-diameter
permalink: /ja/posts/boj-1167-tree-diameter/
source_permalink: /posts/BOJ-1167/
---

[問題リンク](https://www.acmicpc.net/problem/1167)

辺の重みが非負の木では、任意の頂点から最も遠い頂点 `a` を見つけ、次に `a` から最も遠い頂点を探すと、2回目の探索で得られる距離が木の直径です。任意の始点から最も遠い頂点は直径の端点になります。直径の経路と始点から伸びる経路を考えると、木では経路が一意で重みが非負であるため、直径の端点の少なくとも一方は始点から同じだけ以上遠く、そこから再度探索すれば直径のもう一方の端点に到達します。これは重み付き木に対する標準的な二重走査の性質です。

入力には各頂点の隣接リストがあり、無向辺は両端のリストにすでに一度ずつ記載されています。そのため、入力にある向きの隣接情報だけを追加します。逆向きの辺も追加すると重複します。各リストは頂点番号 `-1` で終わるので、辺の重みを読む前に判定します。2回の探索は明示的なスタックで実装し、長い一本道でも再帰の深さ制限を避けます。経路長の合計には `long` を使います。頂点が1つだけの場合は辺がなく、両方の探索でその頂点だけを訪れて直径 `0` を返します。

グラフの構築と2回の探索の計算量は、頂点数を `V` として `O(V)`（`E = V - 1`）です。隣接リスト、スタック、訪問・距離配列の空間計算量は `O(V)` です。

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
