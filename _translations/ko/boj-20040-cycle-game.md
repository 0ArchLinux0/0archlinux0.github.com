---
title: BOJ 20040 — 사이클 게임
author: MINJUN PARK
date: 2022-01-06 03:45:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Union Find, Cycle game, 사이클 게임]
pin: false
lang: ko
translation_key: boj-20040-cycle-game
permalink: /ko/posts/boj-20040-cycle-game/
---

[문제 링크](https://www.acmicpc.net/problem/20040)

간선을 입력 순서대로 처리합니다. 간선 `(a, b)`를 추가하기 전에, 앞서 처리한 간선으로 이루어진 그래프에서 두 정점의 연결 요소 대표를 찾습니다. 대표가 같다면 이미 `a`와 `b`를 잇는 경로가 있으므로 이 간선을 추가할 때 사이클이 생깁니다. 따라서 그 차례를 1부터 세어 즉시 출력합니다. 대표가 다르면 두 연결 요소를 합칩니다. 경로로 이미 연결된 두 정점을 잇는 간선이 추가될 때만 사이클이 생기므로, 이 방법은 처음 사이클을 만드는 간선을 찾습니다. 자기 자신으로 향하는 간선도 입력된 차례에 검출되며, 같은 두 정점을 잇는 평행 간선은 두 번째 간선이 사이클을 만듭니다.

서로소 집합 자료구조는 루트의 연결 요소 크기를 음수로 저장합니다. `find`는 경로 압축을 적용하고, `union`은 크기가 작은 연결 요소를 큰 연결 요소 아래에 붙입니다. 두 최적화를 함께 사용하면 연산당 분할 상환 시간은 `O(α(N))`이며, 간선 `M`개를 처리하는 시간은 `O(M α(N))`, 자료구조의 공간은 `O(N)`입니다. 사이클이 생기는 간선이 없으면 `0`을 출력합니다.

```java
import java.io.*;

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

  static class DisjointSet {
    private final int[] parentOrSize;

    DisjointSet(int n) {
      parentOrSize = new int[n];
      for (int i = 0; i < n; i++) parentOrSize[i] = -1;
    }

    int find(int node) {
      if (parentOrSize[node] < 0) return node;
      return parentOrSize[node] = find(parentOrSize[node]);
    }

    boolean union(int a, int b) {
      int rootA = find(a);
      int rootB = find(b);
      if (rootA == rootB) return false;

      if (parentOrSize[rootA] > parentOrSize[rootB]) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
      }
      parentOrSize[rootA] += parentOrSize[rootB];
      parentOrSize[rootB] = rootA;
      return true;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    int m = input.nextInt();
    DisjointSet dsu = new DisjointSet(n);

    for (int turn = 1; turn <= m; turn++) {
      int a = input.nextInt() - 1;
      int b = input.nextInt() - 1;
      if (!dsu.union(a, b)) {
        System.out.println(turn);
        return;
      }
    }

    System.out.println(0);
  }
}
```
