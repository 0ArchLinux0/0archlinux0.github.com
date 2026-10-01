---
title: BOJ 3665 - 最終順位
author: MINJUN PARK
date: 2022-02-06 22:48:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, トポロジカルソート, 最終順位]
pin: false
lang: ja
translation_key: boj-3665-final-ranking
permalink: /ja/posts/boj-3665-final-ranking/
source_permalink: /posts/BOJ-3665/
---

[問題: BOJ 3665 — 最終順位](https://www.acmicpc.net/problem/3665) · [English](/posts/BOJ-3665/) · [한국어](/ko/posts/boj-3665-final-ranking/)

前年の順位から、すべてのチームの組み合わせについて順序が分かります。順位の高いチームから低いチームへ有向辺を張ると、完全な有向グラフになります。今年順位が入れ替わった2チームについては、その組の辺の向きを反転します。隣接行列と辺の到着先の入次数を同時に更新し、トポロジカルソートに使う情報を保ちます。

Kahnのアルゴリズムでは、入次数が0のチームを1つずつ取り除きます。ある時点で選べるチームが複数あれば、次のチームを複数の方法で選べるため、最終順位は一意に定まりません。取り除いたチーム数が `N` 未満なら、閉路があり全チームの順位を決められません。閉路の判定を優先し、全チームを出力できない場合は `IMPOSSIBLE` を出力します。全チームを出力できる場合、選択肢が複数あったなら `?`、すべての段階で選択肢が1つだけなら一意の順位を出力します。

初期グラフの辺数は `N(N - 1) / 2` です。グラフの構築とKahnのアルゴリズムで全候補辺を確認する処理に `O(N²)`、順位変更の処理に `O(M)` かかるため、全体の時間計算量は `O(N² + M)`、空間計算量は `O(N²)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int[] previous = new int[n];
            for (int i = 0; i < n; i++) {
                previous[i] = input.nextInt() - 1;
            }

            boolean[][] edge = new boolean[n][n];
            int[] indegree = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    edge[previous[i]][previous[j]] = true;
                    indegree[previous[j]]++;
                }
            }

            int changes = input.nextInt();
            for (int i = 0; i < changes; i++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                if (edge[a][b]) {
                    edge[a][b] = false;
                    edge[b][a] = true;
                    indegree[b]--;
                    indegree[a]++;
                } else {
                    edge[b][a] = false;
                    edge[a][b] = true;
                    indegree[a]--;
                    indegree[b]++;
                }
            }

            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for (int team = 0; team < n; team++) {
                if (indegree[team] == 0) {
                    queue.addLast(team);
                }
            }

            int[] ranking = new int[n];
            int count = 0;
            boolean ambiguous = false;
            while (!queue.isEmpty()) {
                if (queue.size() > 1) {
                    ambiguous = true;
                }
                int team = queue.removeFirst();
                ranking[count++] = team;
                for (int next = 0; next < n; next++) {
                    if (edge[team][next] && --indegree[next] == 0) {
                        queue.addLast(next);
                    }
                }
            }

            if (count < n) {
                answer.append("IMPOSSIBLE\n");
            } else if (ambiguous) {
                answer.append("?\n");
            } else {
                for (int team : ranking) {
                    answer.append(team + 1).append(' ');
                }
                answer.setLength(answer.length() - 1);
                answer.append('\n');
            }
        }

        System.out.print(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
