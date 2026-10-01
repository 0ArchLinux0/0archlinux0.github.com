---
title: BOJ. Floyd(2) (11780)
author: MINJUN PARK
date: 2022-01-15 15:11:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Graph,
    Floyd-Warshall,
		플로이드 워셜,
    BOJ,
    Floyd(2),
    플로이드 2
  ]
pin: false
lang: ja
translation_key: boj-11780-all-pairs-paths
permalink: /ja/posts/boj-11780-all-pairs-paths/
---

[問題: BOJ 11780 — フロイド 2](https://www.acmicpc.net/problem/11780)

正のコストを持つ有向グラフについて、すべての都市の順序対間の最小コストと、その最小経路を1つ求めます。同じ向きに複数の辺がある場合は、最も安い辺だけを保持すれば十分です。対角成分は、都市から自分自身への空の経路を表す0で初期化します。

## 次の都市の行列を使うフロイド–ワーシャル法

`distance[i][j]` は、`i` から `j` までに見つかった最小コストを保持します。`next[i][j]` はその経路で `i` の次に訪れる都市で、経路がなければ `-1` です。直接辺 `i -> j` がある場合、最初の都市は `j` です。

中継都市 `k` を通る経路のコストは `distance[i][k] + distance[k][j]` です。これが現在の経路より厳密に小さい場合、コストと最初の都市を両方更新します。`i` から `k` への経路の最初の都市は `next[i][k]` なので、新しい `i` から `j` への経路も同じ最初の都市から始まります。同じコストの場合は既存の経路を維持し、厳密に改善したときだけ更新します。辺のコストが正で対角成分が0のため、最短経路はサイクルを含まないものを選べます。したがって、次の都市をたどると目的地に到達します。問題では同じ都市への経路を出力しないため、`i == j` の場合は経路の代わりに `0` を出力します。

`long` 型で有限の大きな値を `INF` とし、どちらかの区間に到達できない場合は緩和をスキップします。これにより、センチネル値と実際のコストを加算しません。コスト行列の計算量は時間 `O(N^3)`、空間 `O(N^2)` です。全経路の出力には `O(N^2 + 経路出力量)` の時間、すなわち出力する都市番号の総数に比例する時間が追加で必要です。

まずコスト行列を出力し、到達できない組の値は `0` とします。続いて、すべての順序対について行優先順に経路情報を出力します。到達できない組と始点・終点が同じ組は、それぞれ1行に `0` だけを出力します。それ以外の到達可能な組は、経路に含まれる都市数と、始点・終点を含む都市列を出力します。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int cityCount = input.nextInt();
        int routeCount = input.nextInt();

        long[][] distance = new long[cityCount][cityCount];
        int[][] next = new int[cityCount][cityCount];
        for (int i = 0; i < cityCount; i++) {
            java.util.Arrays.fill(distance[i], INF);
            java.util.Arrays.fill(next[i], -1);
            distance[i][i] = 0;
        }

        for (int i = 0; i < routeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long cost = input.nextLong();
            if (cost < distance[from][to]) {
                distance[from][to] = cost;
                next[from][to] = to;
            }
        }

        for (int middle = 0; middle < cityCount; middle++) {
            for (int from = 0; from < cityCount; from++) {
                if (distance[from][middle] == INF) {
                    continue;
                }
                for (int to = 0; to < cityCount; to++) {
                    if (distance[middle][to] == INF) {
                        continue;
                    }
                    long candidate = distance[from][middle] + distance[middle][to];
                    if (candidate < distance[from][to]) {
                        distance[from][to] = candidate;
                        next[from][to] = next[from][middle];
                    }
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (int from = 0; from < cityCount; from++) {
            for (int to = 0; to < cityCount; to++) {
                if (to > 0) {
                    output.append(' ');
                }
                output.append(distance[from][to] == INF ? 0 : distance[from][to]);
            }
            output.append('\n');
        }

        int[] path = new int[cityCount + 1];
        for (int from = 0; from < cityCount; from++) {
            for (int to = 0; to < cityCount; to++) {
                if (from == to || next[from][to] == -1) {
                    output.append("0\n");
                    continue;
                }

                int length = 0;
                int current = from;
                path[length++] = current;
                while (current != to) {
                    current = next[current][to];
                    path[length++] = current;
                }

                output.append(length);
                for (int i = 0; i < length; i++) {
                    output.append(' ').append(path[i] + 1);
                }
                output.append('\n');
            }
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
