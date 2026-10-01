---
title: BOJ 1005 - ACM Craft
author: MINJUN PARK
date: 2022-02-03 22:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, BOJ, Topological Sort, Graph, ACM Craft]
pin: false
lang: ja
translation_key: boj-1005-acm-craft
permalink: /ja/posts/boj-1005-acm-craft/
source_permalink: /posts/BOJ-1005/
---

[問題: BOJ 1005 — ACM Craft](https://www.acmicpc.net/problem/1005) · [English](/posts/BOJ-1005/) · [한국어](/ko/posts/boj-1005-acm-craft/)

各規則 `A B` は、建物 `A` の完成後に建物 `B` を建設できることを表します。規則を有向辺として表すと、有向非巡回グラフになります。トポロジカル順に処理すれば、各建物を扱う時点で先行する建物はすべて処理済みです。

`finish[v]` を建物 `v` の最早完成時刻とします。先行建物 `p` に対する漸化式は `finish[v] = duration[v] + max(finish[p])` です。先行建物がない始点では最大値を `0` とします。各辺 `u -> v` について `finish[u] + duration[v]` を候補にし、`v` のすべての先行建物から得られる値の最大値を保ちます。先行経路が複数ある場合、最も時間のかかる経路が終わるまで待つ必要があります。そのため最初に `finish[v] = duration[v]` としておけば、始点も、始点である目標建物も正しく扱えます。

Kahn のアルゴリズムで入次数が 0 の建物をキューに入れます。キューから建物を取り出したら、出辺ごとに完成時刻を更新し、入次数を減らします。すべての先行建物が処理された建物だけがキューに追加されます。各テストケースでは、目標建物の `finish` を出力します。

建物数を `N`、規則数を `K` とすると、時間計算量と空間計算量はいずれも `O(N + K)` です。完成時刻は `long` で保持します。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int k = input.nextInt();
            long[] duration = new long[n];
            long[] finish = new long[n];
            int[] indegree = new int[n];
            List<Integer>[] graph = new ArrayList[n];

            for (int building = 0; building < n; building++) {
                duration[building] = input.nextInt();
                finish[building] = duration[building];
                graph[building] = new ArrayList<>();
            }

            for (int rule = 0; rule < k; rule++) {
                int prerequisite = input.nextInt() - 1;
                int building = input.nextInt() - 1;
                graph[prerequisite].add(building);
                indegree[building]++;
            }

            int target = input.nextInt() - 1;
            int[] queue = new int[n];
            int front = 0;
            int back = 0;
            for (int building = 0; building < n; building++) {
                if (indegree[building] == 0) {
                    queue[back++] = building;
                }
            }

            while (front < back) {
                int prerequisite = queue[front++];
                for (int building : graph[prerequisite]) {
                    finish[building] = Math.max(
                        finish[building],
                        finish[prerequisite] + duration[building]
                    );
                    if (--indegree[building] == 0) {
                        queue[back++] = building;
                    }
                }
            }

            output.append(finish[target]).append('\n');
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        int nextInt() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokenizer.nextToken());
        }
    }
}
```
