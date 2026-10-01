---
title: BOJ. Time Machine (11657)
author: MINJUN PARK
date: 2022-01-03 11:45:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Bellman Ford,
    벨만 포드,
    Graph,
    Time Machine,
    타임머신,
    Review
  ]
pin: false
lang: ja
translation_key: boj-11657-time-machine
permalink: /ja/posts/boj-11657-time-machine/
---

## 解法

この問題は有向グラフで負の重みを持つ辺があるため、ダイクストラ法は使えません。ベルマン–フォード法では、頂点1から各頂点までの既知の最短距離を保持し、すべての辺を最大 `V - 1` 回緩和します。負閉路を含まない最短路は高々 `V - 1` 本の辺で構成されるため、ある反復で距離が変化しなければ早期終了できます。

距離配列は本当の `long` の無限大値で初期化し、頂点1だけを0にします。辺を緩和する前に始点の距離が無限大か確認し、そうであればその辺を飛ばします。これにより無限大センチネルに対する演算を避け、頂点1から到達できない負閉路を誤検出することも防げます。緩和後に辺をもう一度調べ、有限距離からまだ緩和できる辺があれば、頂点1から到達可能な負閉路があるため `-1` を出力します。負閉路がなければ、頂点2から `N` までの距離を出力し、到達できない頂点は `-1` とします。

ベルマン–フォード法は負の辺を扱えます。最悪時の時間計算量は `O(VE)`、空間計算量は `O(V + E)` です。

[問題リンク](https://www.acmicpc.net/problem/11657)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static class Edge {
        final int from;
        final int to;
        final long weight;

        Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();
        List<Edge> edges = new ArrayList<>(edgeCount);

        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long weight = input.nextLong();
            edges.add(new Edge(from, to, weight));
        }

        long[] distance = new long[vertexCount];
        Arrays.fill(distance, INF);
        distance[0] = 0;

        for (int pass = 0; pass < vertexCount - 1; pass++) {
            boolean changed = false;
            for (Edge edge : edges) {
                if (distance[edge.from] == INF) {
                    continue;
                }
                long candidate = distance[edge.from] + edge.weight;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }

        for (Edge edge : edges) {
            if (distance[edge.from] != INF
                    && distance[edge.from] + edge.weight < distance[edge.to]) {
                System.out.println(-1);
                return;
            }
        }

        StringBuilder output = new StringBuilder();
        for (int vertex = 1; vertex < vertexCount; vertex++) {
            output.append(distance[vertex] == INF ? -1 : distance[vertex]).append('\n');
        }
        System.out.print(output);
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

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            boolean negative = c == '-';
            if (negative) {
                c = read();
            }

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return negative ? -value : value;
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
