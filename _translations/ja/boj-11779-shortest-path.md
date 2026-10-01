---
title: BOJ. 最小費用の求め 2 (11779)
author: MINJUN PARK
date: 2022-01-15 02:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    Get Miminum Cost(2),
    최소비용 구하기 2
  ]
pin: false
lang: ja
translation_key: boj-11779-shortest-path
permalink: /ja/posts/boj-11779-shortest-path/
---

[問題: BOJ 11779 — 最小費用の求め 2](https://www.acmicpc.net/problem/11779)

辺の重みが非負の有向グラフで、指定された始点から終点までの最小費用と、その費用を実現する経路を一つ求めます。各有向辺は隣接リストに格納します。平行辺も有効であり、特別な処理をせずすべて保持できます。

## ダイクストラ法

`distance[v]` は始点から `v` までに見つかった最小費用、`parent[v]` はその距離を最後に更新した経路での直前の頂点です。優先度付きキューには `(頂点, 距離)` の候補を格納します。最小の候補を取り出したとき、その距離が現在の `distance[頂点]` と異なれば、より良い経路に置き換えられた古い候補なので破棄します。そうでなければ、その頂点から出るすべての辺を緩和し、隣接頂点までの距離が短くなる場合は新たな候補をキューに追加します。

不変条件は、配列に記録された距離が実際に見つかった経路の費用であり、キュー内の各要素も経路候補を表すことです。すべての辺の重みが非負なので、現在有効な距離のうち最小の状態を取り出した後、未処理の頂点を経由する経路によってその距離がさらに小さくなることはありません。したがって、緩和を繰り返すことで到達可能な各頂点の最小距離が得られます。距離を更新するたびに、その経路の直前の頂点を親として記録します。終点から始点まで親をたどると最小費用経路を逆順に復元でき、それを反転すると必要な順序になります。始点と終点が同じ場合、経路にはその頂点だけが含まれます。

距離と辺の重みには `long` を使い、優先度付きキューの比較には減算ではなく `Long.compare` を使います。これにより比較時のオーバーフローを避け、距離の加算も `long` で行えます。出力はちょうど3行で、最小費用、経路に含まれる頂点数、経路上の頂点を順に出力します。

遅延削除を使う二分ヒープのダイクストラ法では、時間計算量は `O((V + E) log(E + 1))`、空間計算量は `O(V + E)` です。単純グラフでは時間計算量を一般に `O((V + E) log(V + 1))` と表します。平行辺も含む一般的な場合には、上記の `log(E + 1)` の境界が適用できます。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    private static class Edge {
        final int to;
        final long cost;

        Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    private static class State {
        final int vertex;
        final long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();

        List<List<Edge>> graph = new ArrayList<>(vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long cost = input.nextLong();
            graph.get(from).add(new Edge(to, cost));
        }

        int source = input.nextInt() - 1;
        int destination = input.nextInt() - 1;

        long[] distance = new long[vertexCount];
        int[] parent = new int[vertexCount];
        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);
        distance[source] = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                (left, right) -> Long.compare(left.distance, right.distance));
        queue.add(new State(source, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.vertex]) {
                continue;
            }

            for (Edge edge : graph.get(current.vertex)) {
                long candidate = current.distance + edge.cost;
                if (candidate < distance[edge.to]) {
                    distance[edge.to] = candidate;
                    parent[edge.to] = current.vertex;
                    queue.add(new State(edge.to, candidate));
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int vertex = destination; vertex != -1; vertex = parent[vertex]) {
            path.add(vertex);
            if (vertex == source) {
                break;
            }
        }
        java.util.Collections.reverse(path);

        StringBuilder output = new StringBuilder();
        output.append(distance[destination]).append('\n');
        output.append(path.size()).append('\n');
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                output.append(' ');
            }
            output.append(path.get(i) + 1);
        }
        output.append('\n');
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

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
