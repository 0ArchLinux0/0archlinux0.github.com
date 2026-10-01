---
title: BOJ. Tree (4803)
author: MINJUN PARK
date: 2021-01-10 18:01:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    DFS,
    Tree,
    Data Structure,
  ]
pin: false
lang: ja
translation_key: boj-4803-count-trees
permalink: /ja/posts/boj-4803-count-trees/
source_permalink: /posts/BOJ-4803/
---

## 解法

木は連結かつ閉路のない無向グラフです。未訪問の頂点ごとに幅優先探索を開始し、その連結成分全体を訪問します。頂点はキューに追加する時点で訪問済みにするため、同じ頂点が重複してキューに入りません。辺をたどって隣接頂点を確認する際、すでに訪問済みで現在の頂点の親ではない頂点があれば、閉路が存在します。無向グラフでは親へ戻る辺だけを除外します。閉路のない連結成分だけを木として数え、孤立頂点も木に含めます。

各頂点と隣接リストの各要素を定数回確認するため、時間計算量は `O(V + E)` です。グラフと訪問状態・キューの保存領域を含む空間計算量は `O(V + E)` です。

[問題リンク](https://www.acmicpc.net/problem/4803)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        StringBuilder output = new StringBuilder();
        int caseNumber = 1;

        while (true) {
            int vertexCount = input.nextInt();
            int edgeCount = input.nextInt();
            if (vertexCount == 0 && edgeCount == 0) {
                break;
            }

            @SuppressWarnings("unchecked")
            ArrayList<Integer>[] graph = new ArrayList[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                graph[vertex] = new ArrayList<>();
            }

            for (int edge = 0; edge < edgeCount; edge++) {
                int first = input.nextInt() - 1;
                int second = input.nextInt() - 1;
                graph[first].add(second);
                graph[second].add(first);
            }

            boolean[] visited = new boolean[vertexCount];
            int[] parent = new int[vertexCount];
            java.util.Arrays.fill(parent, -1);
            int treeCount = 0;
            for (int start = 0; start < vertexCount; start++) {
                if (visited[start]) {
                    continue;
                }

                visited[start] = true;
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(start);
                boolean hasCycle = false;

                while (!queue.isEmpty()) {
                    int current = queue.remove();
                    for (int neighbor : graph[current]) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            parent[neighbor] = current;
                            queue.add(neighbor);
                        } else if (neighbor != parent[current]) {
                            hasCycle = true;
                        }
                    }
                }

                if (!hasCycle) {
                    treeCount++;
                }
            }

            if (treeCount == 0) {
                output.append("Case ").append(caseNumber).append(": No trees.\n");
            } else if (treeCount == 1) {
                output.append("Case ").append(caseNumber).append(": There is one tree.\n");
            } else {
                output.append("Case ").append(caseNumber).append(": A forest of ")
                        .append(treeCount).append(" trees.\n");
            }
            caseNumber++;
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
