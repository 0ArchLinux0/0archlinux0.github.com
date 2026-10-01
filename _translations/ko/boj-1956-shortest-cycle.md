---
title: BOJ 1956 — 운동
author: MINJUN PARK
date: 2022-01-05 03:02:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Dijkstra,
    Floyd Warshall,
    Graph,
    Exercise,
    운동,
  ]
pin: false
lang: ko
translation_key: boj-1956-shortest-cycle
permalink: /ko/posts/boj-1956-shortest-cycle/
---

[문제 링크](https://www.acmicpc.net/problem/1956)

`dist[u][v]`를 정점 `u`에서 정점 `v`까지 가는 방향 최단 거리라고 하겠습니다. 모든 값을 무한대로 초기화하고 `dist[i][i]`는 0으로 둡니다. 직접 간선이 여러 개 있으면 그중 가장 작은 가중치를 저장합니다. 대각선의 0은 플로이드–워셜에서 빈 경로를 나타내며, 직접 자기 자신으로 향하는 간선은 사이클 후보 계산을 위해 별도의 간선 행렬에 보존합니다.

플로이드–워셜은 각 정점 `k`를 허용되는 중간 정점으로 고려합니다. 각 `(u, v)`에 대해 최단 경로는 `k`를 거치지 않거나 `u`에서 `k`로 간 뒤 `k`에서 `v`로 이동합니다. 따라서 `dist[u][k] + dist[k][v]`와 기존 값을 비교해 갱신합니다. 두 부분 경로 중 하나라도 도달 불가능하면 덧셈을 건너뜁니다. 이후 가중치 `w`인 방향 간선 `u -> v`는 `v`에서 `u`로 돌아오는 경로가 있을 때 사이클을 만들며, 길이는 `w + dist[v][u]`입니다. 모든 간선에 대해 이 값의 최솟값을 구하면 최단 사이클을 얻습니다. 돌아오는 경로가 없는 간선만 있으면 사이클이 없으므로 `-1`을 출력합니다. 이 방식은 자기 루프(`u == v`)와 평행 간선도 처리합니다.

플로이드–워셜은 `O(V^3)` 시간, 간선 행렬을 살피는 과정은 `O(V^2)` 시간이 걸립니다. 거리 행렬과 간선 행렬의 공간 복잡도는 `O(V^2)`입니다. `long`과 `Long.MAX_VALUE / 4` 센티널을 사용하고 도달 불가능한 항목의 덧셈을 건너뛰어 거리 합을 안전하게 계산합니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream input;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

    FastScanner(InputStream input) {
      this.input = input;
    }

    private int read() throws IOException {
      if (pointer == length) {
        length = input.read(buffer);
        pointer = 0;
        if (length == -1) return -1;
      }
      return buffer[pointer++];
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
    FastScanner input = new FastScanner(System.in);
    int v = input.nextInt();
    int e = input.nextInt();
    long infinity = Long.MAX_VALUE / 4;
    long[][] edge = new long[v][v];
    long[][] dist = new long[v][v];

    for (int i = 0; i < v; i++) {
      Arrays.fill(edge[i], infinity);
      Arrays.fill(dist[i], infinity);
      dist[i][i] = 0;
    }

    for (int i = 0; i < e; i++) {
      int from = input.nextInt() - 1;
      int to = input.nextInt() - 1;
      long weight = input.nextInt();
      edge[from][to] = Math.min(edge[from][to], weight);
      dist[from][to] = Math.min(dist[from][to], weight);
    }

    for (int mid = 0; mid < v; mid++) {
      for (int from = 0; from < v; from++) {
        if (dist[from][mid] == infinity) continue;
        for (int to = 0; to < v; to++) {
          if (dist[mid][to] == infinity) continue;
          dist[from][to] = Math.min(
              dist[from][to], dist[from][mid] + dist[mid][to]);
        }
      }
    }

    long answer = infinity;
    for (int from = 0; from < v; from++) {
      for (int to = 0; to < v; to++) {
        if (edge[from][to] == infinity || dist[to][from] == infinity) continue;
        answer = Math.min(answer, edge[from][to] + dist[to][from]);
      }
    }

    System.out.println(answer == infinity ? -1 : answer);
  }
}
```
