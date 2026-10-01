---
title: BOJ. 最小全域木 (1197)
author: MINJUN PARK
date: 2022-01-22 01:28:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Graph,
    BOJ,
    MST (Minimum Spanning Tree),
    最小全域木
  ]
pin: false
lang: ja
translation_key: boj-1197-minimum-spanning-tree
permalink: /ja/posts/boj-1197-minimum-spanning-tree/
source_permalink: /posts/BOJ-1197/
---

[問題リンク](https://www.acmicpc.net/problem/1197) · [English](/posts/BOJ-1197/) · [한국어](/ko/posts/boj-1197-minimum-spanning-tree/)

## クラスカル法

全域木は、すべての頂点を閉路なしで連結する木です。最小全域木（MST）は、全域木のうち辺の重みの合計が最小のものです。クラスカル法では、辺を重みの昇順に並べ、両端点が異なる連結成分に属するときだけ辺を採用します。端点がすでに連結されているかどうかの判定と、成分の併合には素集合データ構造（DSU）を使います。

この貪欲な選択は安全です。現在異なる成分を結ぶ辺のうち最も軽い辺は、カットの性質により、あるMSTに含めることができます。この選択を繰り返すと閉路のない森ができ、`V - 1` 本の辺を採用した時点で全域木、つまりMSTになります。辺の重みが負でも並べ替えやカットの性質は変わりません。負の重みの辺も、異なる成分を結ぶなら採用する必要があります。

辺の両端点は対称に扱うため、入力された頂点をそのまま保持すれば十分です。クラスカル法のために端点を入れ替える必要はありません。重みの比較には、減算によるオーバーフローを避ける `Integer.compare` を使います。最大 `V - 1` 本の `int` の重みを合計すると `int` の範囲を超えることがあるため、合計値は `long` に保存します。辺数を `E`、頂点数を `V` とすると、ソートを含む時間計算量は `O(E log E)`、DSU の各操作は償却 `O(α(V))` です。空間計算量は `O(V + E)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();
        List<Edge> edges = new ArrayList<>(edgeCount);

        for (int i = 0; i < edgeCount; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int weight = input.nextInt();
            edges.add(new Edge(a, b, weight));
        }

        edges.sort(Comparator.comparingInt(edge -> edge.weight));
        DisjointSet sets = new DisjointSet(vertexCount);
        long totalWeight = 0;
        int acceptedEdges = 0;

        for (Edge edge : edges) {
            if (!sets.union(edge.a, edge.b)) {
                continue;
            }
            totalWeight += edge.weight;
            acceptedEdges++;
            if (acceptedEdges == vertexCount - 1) {
                break;
            }
        }

        System.out.println(totalWeight);
    }

    private static final class Edge {
        final int a;
        final int b;
        final int weight;

        Edge(int a, int b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
    }

    private static final class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
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
            return sign * value;
        }
    }
}
```
