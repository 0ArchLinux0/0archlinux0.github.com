---
title: BOJ. Hide and Seek (1697)
author: MINJUN PARK
date: 2021-12-28 00:15:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Hide and Seek, 숨바꼭질]
pin: false
lang: ja
translation_key: boj-1697-hide-and-seek
permalink: /ja/posts/boj-1697-hide-and-seek/
---

## 解法

`0`から`100000`までの整数をそれぞれ状態として扱います。状態`x`からは、結果が許可された範囲内であれば、1回の移動で`x - 1`、`x + 1`、`2 * x`へ移動できます。すべての辺のコストは1なので、幅優先探索（BFS）は開始地点からの距離が小さい状態から順に訪問します。そのため、目標を初めて発見したときの移動回数が最短距離です。

距離配列は訪問済みかどうかの記録も兼ねています。`-1`は未訪問の状態を表します。各状態はキューに最大1回だけ追加されるため、時間計算量は`O(100000)`、空間計算量は`O(100000)`です。

[問題リンク](https://www.acmicpc.net/problem/1697)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX = 100000;

    public static void main(String[] args) throws IOException {
        StringTokenizer input = new StringTokenizer(
                new BufferedReader(new InputStreamReader(System.in)).readLine());
        int start = Integer.parseInt(input.nextToken());
        int target = Integer.parseInt(input.nextToken());
        if (start == target) {
            System.out.println(0);
            return;
        }

        int[] distance = new int[MAX + 1];
        Arrays.fill(distance, -1);
        int[] queue = new int[MAX + 1];
        int head = 0;
        int tail = 0;

        distance[start] = 0;
        queue[tail++] = start;

        while (head < tail) {
            int current = queue[head++];
            int nextDistance = distance[current] + 1;

            if (current - 1 >= 0 && distance[current - 1] == -1) {
                if (current - 1 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current - 1] = nextDistance;
                queue[tail++] = current - 1;
            }
            if (current + 1 <= MAX && distance[current + 1] == -1) {
                if (current + 1 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current + 1] = nextDistance;
                queue[tail++] = current + 1;
            }
            if (current * 2 <= MAX && distance[current * 2] == -1) {
                if (current * 2 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current * 2] = nextDistance;
                queue[tail++] = current * 2;
            }
        }
    }
}
```
