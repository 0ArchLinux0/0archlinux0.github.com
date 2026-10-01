---
title: BOJ 1766 - 問題集
author: MINJUN PARK
date: 2022-02-06 18:10:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, トポロジカルソート, 問題集]
pin: false
lang: ja
translation_key: boj-1766-workbook
permalink: /ja/posts/boj-1766-workbook/
source_permalink: /posts/BOJ-1766/
---

[問題: BOJ 1766 — 問題集](https://www.acmicpc.net/problem/1766) · [English](/posts/BOJ-1766/) · [한국어](/ko/posts/boj-1766-workbook/)

有向辺 `A -> B` は、問題 `A` を問題 `B` より先に解く必要があることを表します。したがって、各頂点をちょうど一度ずつ含み、すべての辺について始点が終点より前に来るトポロジカル順序を求めます。次に解ける問題が複数ある場合は、番号が最も小さい問題を選びます。

Kahn のアルゴリズムでは、未解決の前提問題数である入次数を管理します。入次数が 0 の問題をすべて最小ヒープに入れ、ヒープから番号が最小の問題を取り出して答えに追加し、その問題から出る各辺の行き先の入次数を 1 ずつ減らします。入次数が 0 になった頂点は解ける状態になったため、ヒープに追加します。この操作を繰り返すと、ヒープは常に次に合法的に解ける問題のうち最小番号を選びます。FIFO キューではこの条件を保証できません。例えば `1 -> 4` と `2 -> 3` があるとき、1 を解いた後は 2 と 4 の両方が解けるため、最小ヒープは先に 2 を選びます。

入力の整数は任意の空白文字で区切られる可能性があるため、行ごとに分割するのではなく、バイトを読み込んで空白を読み飛ばすスキャナーを使います。重複辺も隣接リストと入次数の両方にそのまま反映します。そのため同じ前提辺が複数入力されても、すべての辺を処理するまでは入次数が 0 になりません。他の問題とつながっていない頂点は最初からヒープに入り、`N = 1` も特別扱いなしで処理できます。

グラフの構築に `O(N + M)` かかります。各頂点はヒープに一度追加され一度取り出され、各辺も一度処理されるため、時間計算量は `O((N + M) log N)`、空間計算量は `O(N + M)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position = 0;
        private int length = 0;

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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n];

        for (int i = 0; i < m; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            graph[from].add(to);
            indegree[to]++;
        }

        PriorityQueue<Integer> available = new PriorityQueue<>();
        for (int problem = 0; problem < n; problem++) {
            if (indegree[problem] == 0) available.add(problem);
        }

        StringBuilder answer = new StringBuilder();
        while (!available.isEmpty()) {
            int current = available.remove();
            answer.append(current + 1).append(' ');
            for (int next : graph[current]) {
                if (--indegree[next] == 0) available.add(next);
            }
        }

        System.out.println(answer);
    }
}
```
