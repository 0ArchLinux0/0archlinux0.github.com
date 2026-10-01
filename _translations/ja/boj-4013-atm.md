---
title: BOJ 4013 - ATM
author: MINJUN PARK
date: 2022-02-15 19:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 強連結成分, グラフ, ATM]
pin: false
lang: ja
translation_key: boj-4013-atm
permalink: /ja/posts/boj-4013-atm/
source_permalink: /posts/BOJ-4013/
---

[問題: BOJ 4013 — ATM](https://www.acmicpc.net/problem/4013) · [English](/posts/BOJ-4013/) · [한국어](/ko/posts/boj-4013-atm/)

## 解法

開始地点から出発し、レストランに到着する経路で集められる金額の最大値を求めます。強連結成分（SCC）の内部ではすべての頂点を相互に行き来できるため、その成分にある金額をすべて回収できます。各SCCを構成頂点の金額の合計を重みとする1頂点に縮約すると、有向非巡回グラフ（DAG）になります。

開始SCCから到達できる成分だけを考えます。縮約DAGをトポロジカル順に走査して、成分 `c` に到着したときの最大金額 `best[c]` を計算します。開始SCCはその成分の金額合計で初期化し、ほかの成分は到達不能としておきます。辺 `c → next` ごとに `best[c] + money[next]` で更新します。到達可能でレストランを含む成分の `best[c]` の最大値が答えです。複数の経路がある場合は最大値を選び、開始地点から到達できないレストランは除外します。

コサラジュ法の2回の走査はどちらも反復処理で実装します。1回目は明示的なスタックと頂点ごとの辺カーソルでDFSの終了順を記録し、2回目は逆辺グラフをスタックで探索してSCC番号を割り当てます。頂点ごとにオブジェクトのリストを作らず、プリミティブ配列による隣接リストを使います。元グラフ、逆グラフ、縮約グラフの配列はいずれも入力辺数に応じて確保します。時間・空間計算量は `O(N + M)` で、金額とDP値には `long` を使います。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final long UNREACHABLE = Long.MIN_VALUE;

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length, position;

        private int read() throws IOException {
            if (position == length) {
                length = in.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do c = read(); while (c <= ' ' && c != -1);
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();

        int[] head = new int[n];
        int[] reverseHead = new int[n];
        Arrays.fill(head, -1);
        Arrays.fill(reverseHead, -1);
        int[] to = new int[m];
        int[] next = new int[m];
        int[] reverseTo = new int[m];
        int[] reverseNext = new int[m];

        for (int edge = 0; edge < m; edge++) {
            int from = fs.nextInt() - 1;
            int dest = fs.nextInt() - 1;
            to[edge] = dest;
            next[edge] = head[from];
            head[from] = edge;
            reverseTo[edge] = from;
            reverseNext[edge] = reverseHead[dest];
            reverseHead[dest] = edge;
        }

        long[] vertexMoney = new long[n];
        for (int v = 0; v < n; v++) vertexMoney[v] = fs.nextInt();
        int start = fs.nextInt() - 1;
        int restaurantCount = fs.nextInt();
        boolean[] isRestaurant = new boolean[n];
        for (int i = 0; i < restaurantCount; i++) {
            isRestaurant[fs.nextInt() - 1] = true;
        }

        // Iterative first Kosaraju pass: record DFS finishing order.
        boolean[] visited = new boolean[n];
        int[] order = new int[n];
        int orderSize = 0;
        int[] stackVertex = new int[n];
        int[] stackEdge = new int[n];
        for (int root = 0; root < n; root++) {
            if (visited[root]) continue;
            int top = 0;
            stackVertex[0] = root;
            stackEdge[0] = head[root];
            visited[root] = true;
            while (top >= 0) {
                int edge = stackEdge[top];
                if (edge == -1) {
                    order[orderSize++] = stackVertex[top--];
                    continue;
                }
                stackEdge[top] = next[edge];
                int neighbor = to[edge];
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stackVertex[++top] = neighbor;
                    stackEdge[top] = head[neighbor];
                }
            }
        }

        // Iterative second pass on the reversed graph.
        int[] component = new int[n];
        Arrays.fill(component, -1);
        int componentCount = 0;
        int[] stack = new int[n];
        for (int i = orderSize - 1; i >= 0; i--) {
            int root = order[i];
            if (component[root] != -1) continue;
            int top = 0;
            stack[0] = root;
            component[root] = componentCount;
            while (top >= 0) {
                int vertex = stack[top--];
                for (int edge = reverseHead[vertex]; edge != -1; edge = reverseNext[edge]) {
                    int neighbor = reverseTo[edge];
                    if (component[neighbor] == -1) {
                        component[neighbor] = componentCount;
                        stack[++top] = neighbor;
                    }
                }
            }
            componentCount++;
        }

        long[] componentMoney = new long[componentCount];
        boolean[] componentHasRestaurant = new boolean[componentCount];
        for (int v = 0; v < n; v++) {
            int c = component[v];
            componentMoney[c] += vertexMoney[v];
            if (isRestaurant[v]) componentHasRestaurant[c] = true;
        }

        // Keep cross-component edges in primitive adjacency arrays; parallel edges are harmless.
        int[] dagHead = new int[componentCount];
        Arrays.fill(dagHead, -1);
        int[] dagTo = new int[m];
        int[] dagNext = new int[m];
        int[] indegree = new int[componentCount];
        int dagEdges = 0;
        for (int v = 0; v < n; v++) {
            for (int edge = head[v]; edge != -1; edge = next[edge]) {
                int fromComponent = component[v];
                int toComponent = component[to[edge]];
                if (fromComponent != toComponent) {
                    dagTo[dagEdges] = toComponent;
                    dagNext[dagEdges] = dagHead[fromComponent];
                    dagHead[fromComponent] = dagEdges++;
                    indegree[toComponent]++;
                }
            }
        }

        // Kahn's algorithm processes every DAG edge after its source component.
        int[] queue = new int[componentCount];
        int front = 0, back = 0;
        for (int c = 0; c < componentCount; c++) {
            if (indegree[c] == 0) queue[back++] = c;
        }
        long[] best = new long[componentCount];
        Arrays.fill(best, UNREACHABLE);
        best[component[start]] = componentMoney[component[start]];
        while (front < back) {
            int current = queue[front++];
            for (int edge = dagHead[current]; edge != -1; edge = dagNext[edge]) {
                int following = dagTo[edge];
                if (best[current] != UNREACHABLE) {
                    best[following] = Math.max(best[following],
                            best[current] + componentMoney[following]);
                }
                if (--indegree[following] == 0) queue[back++] = following;
            }
        }

        long answer = 0;
        for (int c = 0; c < componentCount; c++) {
            if (componentHasRestaurant[c] && best[c] != UNREACHABLE) {
                answer = Math.max(answer, best[c]);
            }
        }
        System.out.println(answer);
    }
}
```
