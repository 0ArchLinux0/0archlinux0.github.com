---
title: BOJ. 木とクエリ (15681)
author: MINJUN PARK
date: 2022-01-24 21:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Binary Tree,
    Dynamic Programming,
    BOJ,
    Tree And Query,
    트리와 쿼리
  ]
pin: false
lang: ja
translation_key: boj-15681-tree-subtree-queries
permalink: /ja/posts/boj-15681-tree-subtree-queries/
source_permalink: /posts/BOJ-15681/
---

[問題: BOJ 15681 — 木とクエリ](https://www.acmicpc.net/problem/15681) · [English](/posts/BOJ-15681/) · [한국어](/ko/posts/boj-15681-tree-subtree-queries/)

## 反復による根付けと逆順の集計

無向木を `R` を根として探索します。スタックを使った走査で各頂点の親を記録し、訪問順を `order` 配列に保存します。頂点は親から発見されたときだけスタックに追加し、現在の頂点から親へ戻る辺は無視します。そのため各頂点はスタックに一度だけ入り、順序配列にも一度だけ記録されます。親は子を発見する前に処理されるので、`order` では必ず親が子より前に現れます。

各部分木のサイズを、自分自身を含めて1に初期化します。その後 `order` を逆順にたどり、各頂点に蓄積したサイズを親に加算します。逆順では子が親より先に処理されるため、親に集計を渡す前にすべての子のサイズが反映されます。したがって前処理後の `subtreeSize[u]` は、`u` を根とする部分木の頂点数です。クエリには配列を参照して `O(1)` で答えられます。根へのクエリは `N`、葉へのクエリは `1` を返します。

走査と集計はそれぞれ全頂点を一度ずつ処理し、`N - 1` 本の辺の読み込みも `O(N)` 時間です。前処理全体は `O(N)`、クエリ処理は `O(Q)` です。隣接リスト、親・順序配列、スタック、部分木サイズ配列の空間計算量は `O(N)` です。再帰呼び出しを使わないため、頂点数100,000の一本道でもJavaの呼び出しスタックを使い切りません。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int nodeCount = input.nextInt();
        int root = input.nextInt();
        int queryCount = input.nextInt();

        List<Integer>[] graph = new List[nodeCount + 1];
        for (int node = 1; node <= nodeCount; node++) {
            graph[node] = new ArrayList<>();
        }
        for (int i = 0; i < nodeCount - 1; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }

        int[] parent = new int[nodeCount + 1];
        int[] order = new int[nodeCount];
        int[] stack = new int[nodeCount];
        int orderSize = 0;
        int top = 0;
        stack[top] = root;
        parent[root] = -1;

        while (top >= 0) {
            int node = stack[top--];
            order[orderSize++] = node;
            for (int neighbor : graph[node]) {
                if (neighbor == parent[node]) {
                    continue;
                }
                parent[neighbor] = node;
                stack[++top] = neighbor;
            }
        }

        int[] subtreeSize = new int[nodeCount + 1];
        for (int node = 1; node <= nodeCount; node++) {
            subtreeSize[node] = 1;
        }
        for (int i = orderSize - 1; i > 0; i--) {
            int node = order[i];
            subtreeSize[parent[node]] += subtreeSize[node];
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            output.append(subtreeSize[input.nextInt()]).append('\n');
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

        private int nextInt() throws IOException {
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
