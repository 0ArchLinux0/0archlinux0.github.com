---
title: BOJ. Bipartite Graph (1707)
author: MINJUN PARK
date: 2021-12-30 20:51:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, DFS, Bipartite Graph, 이분 그래프]
pin: false
lang: ja
translation_key: boj-1707-bipartite-graph
permalink: /ja/posts/boj-1707-bipartite-graph/
---

各テストグラフの頂点を2色で塗り分けます。すべての辺が異なる色の頂点同士を結ぶ場合に限り、そのグラフは二部グラフです。グラフが非連結の場合もあるため、まだ色が付いていない頂点すべてから幅優先探索を開始します。探索中に新しく見つけた隣接頂点には、現在の頂点と反対の色を割り当てます。同じ色の頂点同士を結ぶ辺が見つかった場合は二部グラフではありません。この方法で自己ループも検出できます。

不変条件は、確認済みのすべての辺が異なる色の頂点同士を結んでいることです。BFSは各連結成分で、この有効な2分割を広げていきます。同じ色の頂点を結ぶ辺が見つかれば、すべての辺を満たす分割が存在しないと分かります。各頂点と辺を定数回だけ処理するため、時間計算量は `O(V + E)` です。色配列とキューの補助領域は `O(V)`、隣接リストは `O(V + E)` の領域を使います。

[問題リンク](https://www.acmicpc.net/problem/1707)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int vertexCount = input.nextInt();
            int edgeCount = input.nextInt();
            int[] head = new int[vertexCount];
            java.util.Arrays.fill(head, -1);
            int[] to = new int[2 * edgeCount];
            int[] next = new int[2 * edgeCount];

            for (int edge = 0; edge < edgeCount; edge++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                to[2 * edge] = b;
                next[2 * edge] = head[a];
                head[a] = 2 * edge;
                to[2 * edge + 1] = a;
                next[2 * edge + 1] = head[b];
                head[b] = 2 * edge + 1;
            }

            int[] color = new int[vertexCount];
            int[] queue = new int[vertexCount];
            boolean bipartite = true;

            for (int start = 0; start < vertexCount && bipartite; start++) {
                if (color[start] != 0) {
                    continue;
                }

                int front = 0;
                int back = 0;
                color[start] = 1;
                queue[back++] = start;
                while (front < back && bipartite) {
                    int vertex = queue[front++];
                    for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                        int neighbor = to[edge];
                        if (color[neighbor] == 0) {
                            color[neighbor] = 3 - color[vertex];
                            queue[back++] = neighbor;
                        } else if (color[neighbor] == color[vertex]) {
                            bipartite = false;
                            break;
                        }
                    }
                }
            }
            output.append(bipartite ? "YES\n" : "NO\n");
        }
        System.out.print(output);
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
