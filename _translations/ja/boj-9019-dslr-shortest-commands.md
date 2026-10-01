---
title: BOJ. DSLR (9019)
author: MINJUN PARK
date: 2022-01-14 17:58:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    DSLR,
  ]
pin: false
lang: ja
translation_key: boj-9019-dslr-shortest-commands
permalink: /ja/posts/boj-9019-dslr-shortest-commands/
---

## 解法

`0`から`9999`までの整数をそれぞれグラフの頂点として扱います。DSLRの4つの命令は、現在の値からその命令を適用した値へ向かう辺です。すべての命令のコストは1なので、幅優先探索（BFS）は開始状態からの命令数が少ない状態から順に探索します。

各状態からの遷移先を`D`、`S`、`L`、`R`の順に訪問します。BFSは最短経路を先に見つけ、同じ長さの経路はこの命令の優先順位に従って探索します。状態をキューに入れる時点で訪問済みにするため、最初に記録された親と命令が、その状態への優先順位が最も高い最短経路を表します。10,000個の状態はそれぞれ最大1回だけキューに追加されます。キューには整数の状態だけを格納し、親へのリンクをたどって答えを復元します。開始値と目標値が同じ場合、経路の長さは0なので空行を出力します。

各状態で4つの遷移を調べるため、テストケースごとの時間計算量と補助空間計算量はともに`O(10,000)`です。

[問題リンク](https://www.acmicpc.net/problem/9019)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int STATE_COUNT = 10000;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            StringTokenizer input = new StringTokenizer(reader.readLine());
            int start = Integer.parseInt(input.nextToken());
            int target = Integer.parseInt(input.nextToken());

            int[] parent = new int[STATE_COUNT];
            Arrays.fill(parent, -1);
            char[] commandUsed = new char[STATE_COUNT];
            int[] queue = new int[STATE_COUNT];
            int head = 0;
            int tail = 0;

            parent[start] = start;
            queue[tail++] = start;

            while (head < tail && parent[target] == -1) {
                int current = queue[head++];

                int next = current * 2 % STATE_COUNT;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'D';
                    queue[tail++] = next;
                }

                next = current == 0 ? 9999 : current - 1;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'S';
                    queue[tail++] = next;
                }

                next = current % 1000 * 10 + current / 1000;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'L';
                    queue[tail++] = next;
                }

                next = current % 10 * 1000 + current / 10;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'R';
                    queue[tail++] = next;
                }
            }

            char[] reversedPath = new char[STATE_COUNT];
            int pathLength = 0;
            for (int state = target; state != start; state = parent[state]) {
                reversedPath[pathLength++] = commandUsed[state];
            }
            for (int i = pathLength - 1; i >= 0; i--) {
                output.append(reversedPath[i]);
            }
            output.append('\n');
        }

        System.out.print(output);
    }
}
```
