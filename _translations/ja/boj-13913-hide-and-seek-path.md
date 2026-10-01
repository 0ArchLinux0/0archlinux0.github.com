---
title: BOJ 13913 — かくれんぼ 4
author: MINJUN PARK
date: 2022-01-14 13:57:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Dynamic Programming,
    BOJ,
    Hide And Sick(4),
    숨바꼭질 4
  ]
pin: false
lang: ja
translation_key: boj-13913-hide-and-seek-path
permalink: /ja/posts/boj-13913-hide-and-seek-path/
source_permalink: /posts/BOJ-13913/
---

[問題リンク](https://www.acmicpc.net/problem/13913)

スビンの位置を `x` とすると、範囲 `[0, 100000]` 内での移動先は `x - 1`、`x + 1`、`2 * x` です。各移動のコストは1秒なので、幅優先探索（BFS）は開始位置からの移動回数が少ない位置から訪問します。位置を初めて発見したときの距離が最小であり、その位置を発見した直前の位置を親として記録すれば最短経路も保存できます。

開始位置を距離0として一度だけキューに入れます。範囲内の位置を新たに発見したら、キューに入れる前に訪問済みにするため、重複登録や循環（たとえば `0 -> 0`）は起きません。目標に到達したら、目標から開始位置まで親をたどり、その列を反転します。これにより最小の移動回数と、開始位置から目標までの位置の順序が得られます。位置数を `R = 100001` とすると、探索と経路復元にかかる時間・空間はいずれも `O(R)` です。

```java
import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer values = new StringTokenizer(input.readLine());
    int start = Integer.parseInt(values.nextToken());
    int target = Integer.parseInt(values.nextToken());

    int range = 100001;
    int[] distance = new int[range];
    int[] parent = new int[range];
    int[] queue = new int[range];
    for (int i = 0; i < range; i++) {
      distance[i] = -1;
      parent[i] = -1;
    }

    int front = 0;
    int back = 0;
    queue[back++] = start;
    distance[start] = 0;

    while (front < back && distance[target] == -1) {
      int current = queue[front++];
      int nextDistance = distance[current] + 1;

      if (current > 0 && distance[current - 1] == -1) {
        distance[current - 1] = nextDistance;
        parent[current - 1] = current;
        queue[back++] = current - 1;
      }
      if (current < 100000 && distance[current + 1] == -1) {
        distance[current + 1] = nextDistance;
        parent[current + 1] = current;
        queue[back++] = current + 1;
      }
      int doubled = current * 2;
      if (doubled <= 100000 && distance[doubled] == -1) {
        distance[doubled] = nextDistance;
        parent[doubled] = current;
        queue[back++] = doubled;
      }
    }

    int[] path = new int[distance[target] + 1];
    int length = path.length;
    int position = target;
    for (int i = length - 1; i >= 0; i--) {
      path[i] = position;
      if (i > 0) {
        position = parent[position];
      }
    }

    StringBuilder output = new StringBuilder();
    output.append(distance[target]).append('\n');
    for (int value : path) {
      output.append(value).append(' ');
    }
    System.out.println(output);
  }
}
```
