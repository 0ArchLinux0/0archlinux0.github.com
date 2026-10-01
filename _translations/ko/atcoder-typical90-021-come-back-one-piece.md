---
title: AtCoder Typical 90 021 — Come Back in One Piece
author: MINJUN PARK
date: 2021-11-28 3:02:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Graph,
    SCC,
    Kosaraju's algorithm,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-021-come-back-one-piece
permalink: /ko/posts/atcoder-typical90-021-come-back-one-piece/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_u)

이 문제는 서로에게 도달할 수 있는 서로 다른 두 정점으로 이루어진 순서 없는 쌍의 개수를 구합니다. 두 정점이 서로에게 도달할 수 있는 것은 두 정점이 같은 강한 연결 요소(SCC)에 속할 때, 그리고 그때뿐입니다. SCC 안에서는 모든 정점에서 다른 모든 정점으로 향하는 방향 경로가 존재합니다.

크기가 `s`인 각 SCC에서는 `s * (s - 1) / 2`개의 모든 쌍이 조건을 만족합니다. 반복형 코사라주 알고리즘을 사용합니다. 첫 번째 깊이 우선 탐색은 명시적인 프레임을 이용해 정점의 종료 순서를 기록하고, 두 번째 탐색은 역방향 그래프에서 종료 순서의 역순으로 진행하여 SCC를 모읍니다. 명시적 스택을 사용하므로 깊은 그래프에서도 Java 호출 스택 오버플로를 피할 수 있습니다. 시간 복잡도는 `O(N + M)`, 공간 복잡도도 `O(N + M)`입니다.

입력의 정점 번호는 1부터 N까지입니다. 파서는 모든 정수 사이의 임의 개수의 공백 문자를 처리합니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;

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

    private int read() throws IOException {
      if (ptr == len) {
        len = in.read(buffer);
        ptr = 0;
        if (len == -1) return -1;
      }
      return buffer[ptr++];
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner fs = new FastScanner();
    int n = fs.nextInt();
    int m = fs.nextInt();
    List<Integer>[] graph = new ArrayList[n + 1];
    List<Integer>[] reverse = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<>();
      reverse[i] = new ArrayList<>();
    }
    for (int i = 0; i < m; i++) {
      int from = fs.nextInt();
      int to = fs.nextInt();
      graph[from].add(to);
      reverse[to].add(from);
    }

    boolean[] visited = new boolean[n + 1];
    int[] nextEdge = new int[n + 1];
    int[] stack = new int[n];
    int[] order = new int[n];
    int orderSize = 0;

    for (int start = 1; start <= n; start++) {
      if (visited[start]) continue;
      int top = 0;
      stack[0] = start;
      visited[start] = true;
      while (top >= 0) {
        int v = stack[top];
        if (nextEdge[v] < graph[v].size()) {
          int to = graph[v].get(nextEdge[v]++);
          if (!visited[to]) {
            visited[to] = true;
            stack[++top] = to;
          }
        } else {
          order[orderSize++] = v;
          top--;
        }
      }
    }

    Arrays.fill(visited, false);
    long answer = 0;
    for (int i = orderSize - 1; i >= 0; i--) {
      int start = order[i];
      if (visited[start]) continue;
      int top = 0;
      int size = 0;
      stack[0] = start;
      visited[start] = true;
      while (top >= 0) {
        int v = stack[top--];
        size++;
        for (int to : reverse[v]) {
          if (!visited[to]) {
            visited[to] = true;
            stack[++top] = to;
          }
        }
      }
      answer += (long) size * (size - 1) / 2;
    }

    System.out.println(answer);
  }
}
```
