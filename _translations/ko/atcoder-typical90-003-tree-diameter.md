---
title: AtCoder Typical 90 003 — 가장 긴 원형 도로 (4)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Longest Circular Road,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-003-tree-diameter
permalink: /ko/posts/atcoder-typical90-003-tree-diameter/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_c)

이 문제는 트리에서 경로에 포함될 수 있는 마을 수의 최댓값을 구합니다. 트리에서는 임의의 두 정점 사이의 경로가 유일하며, 가장 긴 경로를 트리의 지름이라고 합니다.

두 번 탐색하는 방법으로 지름을 구합니다. 임의의 정점에서 시작해 트리를 탐색하고 가장 멀리 있는 끝점 `u`를 찾은 다음, `u`에서 다시 탐색합니다. `u`에서 가장 멀리 있는 정점은 지름의 다른 끝점이므로, 두 번째 탐색에서 얻은 최댓거리가 간선 수 기준 지름의 길이입니다. 정답은 양쪽 끝점을 포함한 정점 수이므로 거리에 1을 더해 출력합니다. `N = 1`이면 두 탐색 모두 최대 거리가 0이므로 출력은 `1`입니다.

각 탐색은 큐와 거리 배열을 사용합니다. 트리에서는 각 정점으로 가는 경로가 유일하므로 처음 방문했을 때의 거리가 시작점으로부터의 경로 길이입니다. 시간 복잡도는 `O(N)`, 공간 복잡도는 `O(N)`입니다. 반복 방식으로 구현해 Java 호출 스택 깊이에 의존하지 않습니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static int farthest(List<Integer>[] graph, int start, int[] distance) {
    Arrays.fill(distance, -1);
    int[] queue = new int[graph.length];
    int head = 0;
    int tail = 0;
    queue[tail++] = start;
    distance[start] = 0;
    int farthest = start;

    while (head < tail) {
      int town = queue[head++];
      if (distance[town] > distance[farthest]) {
        farthest = town;
      }
      for (int next : graph[town]) {
        if (distance[next] == -1) {
          distance[next] = distance[town] + 1;
          queue[tail++] = next;
        }
      }
    }
    return farthest;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(input.readLine());
    List<Integer>[] graph = new ArrayList[n];
    for (int town = 0; town < n; town++) {
      graph[town] = new ArrayList<>();
    }

    for (int i = 0; i < n - 1; i++) {
      StringTokenizer edge = new StringTokenizer(input.readLine());
      int a = Integer.parseInt(edge.nextToken()) - 1;
      int b = Integer.parseInt(edge.nextToken()) - 1;
      graph[a].add(b);
      graph[b].add(a);
    }

    int[] distance = new int[n];
    int endpoint = farthest(graph, 0, distance);
    farthest(graph, endpoint, distance);
    int diameterEdges = 0;
    for (int d : distance) {
      diameterEdges = Math.max(diameterEdges, d);
    }
    System.out.println(diameterEdges + 1);
  }
}
```
