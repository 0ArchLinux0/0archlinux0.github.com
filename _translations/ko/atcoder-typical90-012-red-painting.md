---
title: AtCoder Typical 90 012 — Red Painting (4)
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Red Painting,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-012-red-painting
permalink: /ko/posts/atcoder-typical90-012-red-painting/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_l)

처음에는 모든 칸이 흰색입니다. 칸을 빨간색으로 칠하거나 두 칸을 지정하는 질의가 주어집니다. 두 칸이 모두 빨간색이고 빨간 칸만 상하좌우로 이동하여 서로 도달할 수 있으면 `Yes`, 그렇지 않으면 `No`를 출력합니다.

각 격자 칸을 분리 집합 자료구조(DSU)의 원소로 취급하고, 빨간 칸을 나타내는 `red` 배열을 따로 관리합니다. 칸을 칠할 때 아직 빨간색이 아니라면 빨간색으로 활성화한 뒤, 상하좌우의 빨간 이웃과 집합을 합칩니다. 한 번 빨간색이 된 칸은 계속 빨간색이므로 연결 관계는 합치기만 발생하며, DSU가 현재 연결 요소를 계속 나타냅니다.

질의에서는 두 칸이 모두 빨간색인지 확인한 다음, 두 칸의 대표 원소가 같은지 비교합니다. 이 순서 덕분에 아직 칠하지 않은 칸은 같은 위치를 질의하더라도 연결되었다고 판단하지 않습니다. 경계를 확인한 뒤 이웃을 검사하므로 격자 밖 칸은 접근하지 않습니다.

경로 압축과 union by size를 사용하는 DSU의 각 연산은 상각 `O(α(HW)` 시간이며, 각 칸은 최대 한 번 활성화되고 네 이웃만 확인하므로 전체 시간은 `O((HW + Q) α(HW))`입니다. DSU와 빨간색 배열에 `O(HW)` 공간을 사용합니다.

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

  static class DSU {
    private final int[] parent;
    private final int[] size;

    DSU(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int x) {
      if (parent[x] != x) parent[x] = find(parent[x]);
      return parent[x];
    }

    void unite(int a, int b) {
      int rootA = find(a);
      int rootB = find(b);
      if (rootA == rootB) return;
      if (size[rootA] < size[rootB]) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
      }
      parent[rootB] = rootA;
      size[rootA] += size[rootB];
    }
  }

  static final int[] DR = {-1, 1, 0, 0};
  static final int[] DC = {0, 0, -1, 1};

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int h = input.nextInt();
    int w = input.nextInt();
    int q = input.nextInt();
    int cells = h * w;
    boolean[] red = new boolean[cells];
    DSU dsu = new DSU(cells);
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < q; i++) {
      int type = input.nextInt();
      if (type == 1) {
        int r = input.nextInt() - 1;
        int c = input.nextInt() - 1;
        int cell = r * w + c;
        if (!red[cell]) {
          red[cell] = true;
          for (int direction = 0; direction < 4; direction++) {
            int nr = r + DR[direction];
            int nc = c + DC[direction];
            if (nr >= 0 && nr < h && nc >= 0 && nc < w) {
              int neighbor = nr * w + nc;
              if (red[neighbor]) dsu.unite(cell, neighbor);
            }
          }
        }
      } else {
        int r1 = input.nextInt() - 1;
        int c1 = input.nextInt() - 1;
        int r2 = input.nextInt() - 1;
        int c2 = input.nextInt() - 1;
        int first = r1 * w + c1;
        int second = r2 * w + c2;
        output.append(red[first] && red[second] && dsu.find(first) == dsu.find(second) ? "Yes" : "No").append('\n');
      }
    }
    System.out.print(output);
  }
}
```
