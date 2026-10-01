---
title: BOJ 4196 - ドミノ
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, グラフ, 強連結成分, SCC, ドミノ]
pin: false
lang: ja
translation_key: boj-4196-domino-scc
permalink: /ja/posts/boj-4196-domino-scc/
source_permalink: /posts/BOJ-4196/
---

[問題: BOJ 4196 — ドミノ](https://www.acmicpc.net/problem/4196) · [English](/posts/BOJ-4196/) · [한국어](/ko/posts/boj-4196-domino-scc/)

各テストケースでは、有向グラフが与えられます。ドミノ `u` を倒すと、有向辺に沿って `u` から到達できるドミノがすべて倒れます。すべての頂点を倒すために最初に倒すドミノの最小数を求めます。

まず頂点を強連結成分（SCC）に分けます。同じSCC内ではどの頂点からもほかのすべての頂点に到達できるため、その成分のドミノを一つ倒せば成分全体が倒れます。各SCCを一つの頂点にまとめ、異なるSCC間の辺を残すと、縮約グラフ（SCC DAG）ができます。

縮約グラフで入次数が0のSCCには、必ず最初の一押しが必要です。他の成分からそこへ到達できないためです。逆に、入次数0のSCCを一つずつ倒せば十分です。有限DAGの任意の頂点は、ある始点（入次数0の頂点）から到達できます。各頂点から入ってくる辺をたどり続ければ、有限グラフなのでいずれ入次数0の頂点に着くからです。したがって答えは、入次数0のSCCの個数です。

実装では反復型のKosaraju法を使います。最初のDFSでは明示的なスタックで終了順を記録し、逆辺のグラフを終了順の逆順にたどってSCC IDを割り当てます。再帰呼び出しを使わないため、頂点が100,000個ある長い経路でも呼び出しスタックがあふれません。最後に辺を走査し、異なるSCCから辺が入る成分を記録します。各頂点と辺を定数回処理するので、時間計算量は `O(V + E)`、空間計算量も `O(V + E)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int limit;

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

        private int read() throws IOException {
            if (position == limit) {
                limit = input.read(buffer);
                position = 0;
                if (limit == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int m = input.nextInt();
            int[] head = new int[n];
            int[] reverseHead = new int[n];
            Arrays.fill(head, -1);
            Arrays.fill(reverseHead, -1);
            int[] to = new int[m];
            int[] next = new int[m];
            int[] reverseTo = new int[m];
            int[] reverseNext = new int[m];

            for (int edge = 0; edge < m; edge++) {
                int from = input.nextInt() - 1;
                int destination = input.nextInt() - 1;
                to[edge] = destination;
                next[edge] = head[from];
                head[from] = edge;
                reverseTo[edge] = from;
                reverseNext[edge] = reverseHead[destination];
                reverseHead[destination] = edge;
            }

            boolean[] visited = new boolean[n];
            int[] order = new int[n];
            int orderSize = 0;
            int[] nodeStack = new int[n];
            int[] edgeStack = new int[n];

            for (int start = 0; start < n; start++) {
                if (visited[start]) {
                    continue;
                }
                int top = 0;
                nodeStack[0] = start;
                edgeStack[0] = head[start];
                visited[start] = true;

                while (top >= 0) {
                    int edge = edgeStack[top];
                    if (edge == -1) {
                        order[orderSize++] = nodeStack[top--];
                        continue;
                    }
                    edgeStack[top] = next[edge];
                    int neighbor = to[edge];
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        nodeStack[++top] = neighbor;
                        edgeStack[top] = head[neighbor];
                    }
                }
            }

            int[] component = new int[n];
            Arrays.fill(component, -1);
            int[] stack = new int[n];
            int componentCount = 0;
            for (int i = orderSize - 1; i >= 0; i--) {
                int start = order[i];
                if (component[start] != -1) {
                    continue;
                }
                int size = 0;
                stack[size++] = start;
                component[start] = componentCount;
                while (size > 0) {
                    int node = stack[--size];
                    for (int edge = reverseHead[node]; edge != -1; edge = reverseNext[edge]) {
                        int neighbor = reverseTo[edge];
                        if (component[neighbor] == -1) {
                            component[neighbor] = componentCount;
                            stack[size++] = neighbor;
                        }
                    }
                }
                componentCount++;
            }

            boolean[] hasIncoming = new boolean[componentCount];
            for (int from = 0; from < n; from++) {
                for (int edge = head[from]; edge != -1; edge = next[edge]) {
                    int sourceComponent = component[from];
                    int destinationComponent = component[to[edge]];
                    if (sourceComponent != destinationComponent) {
                        hasIncoming[destinationComponent] = true;
                    }
                }
            }

            int pushes = 0;
            for (boolean incoming : hasIncoming) {
                if (!incoming) {
                    pushes++;
                }
            }
            answer.append(pushes).append('\n');
        }

        System.out.print(answer);
    }
}
```
