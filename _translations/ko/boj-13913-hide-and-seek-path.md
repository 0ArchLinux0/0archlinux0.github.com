---
title: BOJ 13913 — 숨바꼭질 4
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
lang: ko
translation_key: boj-13913-hide-and-seek-path
permalink: /ko/posts/boj-13913-hide-and-seek-path/
source_permalink: /posts/BOJ-13913/
---

[문제 링크](https://www.acmicpc.net/problem/13913)

수빈이가 있는 위치를 `x`라고 할 때, 범위 `[0, 100000]` 안에서 이동할 수 있는 위치는 `x - 1`, `x + 1`, `2 * x`입니다. 각 이동의 비용은 1초이므로 너비 우선 탐색(BFS)은 시작점으로부터 이동 횟수가 적은 위치부터 방문합니다. 어떤 위치를 처음 발견한 순간의 거리는 최소이며, 그 위치를 발견한 직전 위치를 부모로 저장하면 최단 경로도 함께 기록할 수 있습니다.

시작 위치를 거리 0으로 큐에 한 번 넣습니다. 범위 안의 위치를 새로 발견할 때 큐에 넣기 전에 방문 표시를 하므로 중복 삽입과 순환(예를 들어 `0 -> 0`)이 생기지 않습니다. 목표에 도달하면 목표에서 시작점까지 부모를 따라간 뒤 경로를 뒤집습니다. 따라서 최소 이동 횟수와 시작부터 목표까지의 위치 순서를 모두 얻습니다. 위치 개수를 `R = 100001`이라 하면 탐색과 경로 복원에 필요한 시간 및 공간은 `O(R)`입니다.

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
