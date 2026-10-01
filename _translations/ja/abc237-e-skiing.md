---
title: AtCoder ABC 237 E — Skiing
author: MINJUN PARK
date: 2022-01-31 08:48:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 237, グラフ, ダイクストラ法]
pin: false
lang: ja
translation_key: abc237-e-skiing
permalink: /ja/posts/abc237-e-skiing/
source_permalink: /posts/Atcoder-E-Skiing/
---

[問題: AtCoder ABC 237 E — Skiing](https://atcoder.jp/contests/abc237/tasks/abc237_e) · [English](/posts/Atcoder-E-Skiing/) · [한국어](/ko/posts/abc237-e-skiing/)

頂点 1 から頂点 `v` までの経路で、登った高さの合計を `U`、下った高さの合計を `D` とします。この経路での幸福度の変化は `D - 2U` です。1 だけ下ると幸福度が 1 増え、1 だけ登ると 2 減るためです。経路全体の高さの変化は `H[v] - H[1] = U - D` なので、`D = U + H[1] - H[v]` となり、幸福度は `H[1] - H[v] - U` と表せます。目的地 `v` を固定するとその高さは一定です。したがって、幸福度を最大化するには登った高さの合計 `U` を最小化すればよいです。

各双方向道路 `u-v` に、方向ごとのコストを設定します。`u` から `v` へ進むコストは、その区間で登る高さ `max(0, H[v] - H[u])` です。逆向きのコストは `max(0, H[u] - H[v])` です。下り坂と同じ高さの区間のコストは 0 になります。すべてのコストが非負なので、ダイクストラ法で頂点 1 から各到達可能な頂点までの最小累積上昇量を求められます。道路は双方向でも、方向ごとのコストは異なることがあります。特に高さが等しい道路は両方向ともコスト 0 です。

到達可能な各頂点 `v` の幸福度は `H[1] - H[v] - dist[v]` です。ここで `dist[v]` は最小累積上昇量です。始点から到達できない頂点には経路がないため、答えの計算から除外します。始点自身には幸福度 0 で到達できるので、答えを 0 で初期化します。高さの差、距離、幸福度には `long` を使い、加算によるオーバーフローを防ぎます。時間計算量は `O((N + M) log N)`、空間計算量は `O(N + M)` です。

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
