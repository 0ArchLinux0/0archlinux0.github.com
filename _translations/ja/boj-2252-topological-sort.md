---
title: BOJ. 列を並べる (2252)
author: MINJUN PARK
date: 2022-01-26 22:09:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Topological Sort,
    Graph,
    BOJ,
    Line up,
    줄 세우기,
    Review
  ]
pin: false
lang: ja
translation_key: boj-2252-topological-sort
permalink: /ja/posts/boj-2252-topological-sort/
source_permalink: /posts/BOJ-2252/
---

[問題: BOJ 2252 — 列を並べる](https://www.acmicpc.net/problem/2252)

入力される各組 `A B` は、学生 `A` が学生 `B` より前に並ぶ必要があることを表します。この条件を有向辺 `A -> B` として表します。有効な列はトポロジカル順序であり、すべての辺で始点が終点より前に並びます。順序関係のない学生同士はどちらが先でもよいため、答えは一意な順序ではなく、条件を満たす順序の1つです。

カーンのアルゴリズムでは、入次数が0（まだ残っている先行条件がない）の頂点を繰り返し選んで出力し、その頂点から出る辺を取り除きます。辺を取り除くたびに終点の入次数を減らし、値が0になった終点は出力可能になります。準備できた頂点が複数ある場合もあり、どれを選んでも必要な部分順序は保たれます。

すべての入力辺を隣接リストに保存し、辺ごとに終点の入次数を増やします。そのため、同じ制約が重複していても両方の表現が一致します。DAGではすべての頂点がちょうど1回出力されます。学生数を `N`、制約数を `M` とすると、時間計算量は `O(N + M)`、空間計算量も `O(N + M)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int studentCount = input.nextInt();
        int constraintCount = input.nextInt();

        List<List<Integer>> next = new ArrayList<>(studentCount);
        for (int student = 0; student < studentCount; student++) {
            next.add(new ArrayList<>());
        }

        int[] indegree = new int[studentCount];
        for (int i = 0; i < constraintCount; i++) {
            int before = input.nextInt() - 1;
            int after = input.nextInt() - 1;
            next.get(before).add(after);
            indegree[after]++;
        }

        Queue<Integer> ready = new ArrayDeque<>();
        for (int student = 0; student < studentCount; student++) {
            if (indegree[student] == 0) {
                ready.offer(student);
            }
        }

        StringBuilder output = new StringBuilder();
        boolean first = true;
        while (!ready.isEmpty()) {
            int student = ready.poll();
            if (!first) {
                output.append(' ');
            }
            output.append(student + 1);
            first = false;

            for (int after : next.get(student)) {
                indegree[after]--;
                if (indegree[after] == 0) {
                    ready.offer(after);
                }
            }
        }

        System.out.println(output);
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
