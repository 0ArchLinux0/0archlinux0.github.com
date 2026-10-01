---
title: BOJ 11404 — 플로이드
author: MINJUN PARK
date: 2022-01-04 19:12:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Floyd-Warshall,
    Graph,
  ]
pin: false
lang: ko
translation_key: boj-11404-floyd-warshall
permalink: /ko/posts/boj-11404-floyd-warshall/
---

[문제 링크](https://www.acmicpc.net/problem/11404)

`dist[i][j]`를 도시 `i`에서 도시 `j`로 가는 현재까지 알려진 최단 비용이라고 하겠습니다. 처음에는 같은 도시에 머무르는 비용 0과 직접 연결된 버스 노선만 알려져 있습니다. 출발 도시와 도착 도시가 같은 노선이 여러 개라면 그중 비용이 가장 작은 노선만 저장합니다.

각 도시 `k`를 중간 경유지로 차례대로 고려합니다. `k`를 처리하기 전에는 이미 처리한 도시만 중간 경유지로 사용하는 최단 경로가 `dist[i][j]`에 들어 있습니다. 새 경로는 `k`를 거치지 않는 기존 경로이거나, `i`에서 `k`까지 간 뒤 `k`에서 `j`로 가는 경로입니다. 따라서 `dist[i][k] + dist[k][j]`와 기존 값을 비교해 갱신합니다. 어느 한쪽 구간이라도 도달할 수 없다면 덧셈을 건너뛰어 무한대 센티널을 경로 비용에 더하지 않습니다. 모든 도시를 중간 경유지로 확인하면 가능한 최단 경로를 모두 고려한 것입니다.

도시를 중간 경유지, 출발지, 도착지로 순회하는 세 겹의 반복문을 사용하므로 시간 복잡도는 `O(N^3)`입니다. 거리 행렬의 공간 복잡도는 `O(N^2)`입니다. `long` 행렬과 큰 센티널로 도달 불가능한 상태와 경로 비용을 안전하게 표현합니다. 문제에서 도달할 수 없는 도시 쌍의 출력은 `0`입니다.

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
    int n = input.nextInt();
    int m = input.nextInt();
    long infinity = Long.MAX_VALUE / 4;
    long[][] dist = new long[n][n];

    for (int i = 0; i < n; i++) {
      Arrays.fill(dist[i], infinity);
      dist[i][i] = 0;
    }

    for (int i = 0; i < m; i++) {
      int from = input.nextInt() - 1;
      int to = input.nextInt() - 1;
      long cost = input.nextInt();
      dist[from][to] = Math.min(dist[from][to], cost);
    }

    for (int mid = 0; mid < n; mid++) {
      for (int from = 0; from < n; from++) {
        if (dist[from][mid] == infinity) continue;
        for (int to = 0; to < n; to++) {
          if (dist[mid][to] == infinity) continue;
          dist[from][to] = Math.min(
              dist[from][to], dist[from][mid] + dist[mid][to]);
        }
      }
    }

    StringBuilder output = new StringBuilder();
    for (int from = 0; from < n; from++) {
      for (int to = 0; to < n; to++) {
        if (to > 0) output.append(' ');
        output.append(dist[from][to] == infinity ? 0 : dist[from][to]);
      }
      output.append('\n');
    }
    System.out.print(output);
  }
}
```
