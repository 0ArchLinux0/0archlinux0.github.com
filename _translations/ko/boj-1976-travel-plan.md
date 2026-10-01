---
title: BOJ 1976 — 여행 가자
author: MINJUN PARK
date: 2022-01-05 13:30:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Union Find, Let's go on a trip, 여행 가자, Review]
pin: false
lang: ko
translation_key: boj-1976-travel-plan
permalink: /ko/posts/boj-1976-travel-plan/
---

[문제 링크](https://www.acmicpc.net/problem/1976)

도시를 정점, 도로를 무방향 간선으로 생각합니다. 여행 계획에 포함된 모든 도시가 같은 연결 요소에 속할 때, 그리고 그때만 여행이 가능합니다. 그러면 계획에서 연속한 두 도시 사이에 경로가 존재하고, 그 경로들을 이어 여행할 수 있습니다. 따라서 도시 하나만 있는 계획은 항상 가능합니다.

분리 집합(DSU) 자료구조를 사용합니다. 처음에는 각 도시가 각각 하나의 집합입니다. 인접 행렬에서 `1`인 모든 칸에 해당하는 도시를 합칩니다. 무방향 행렬의 양방향을 모두 처리해도 이미 같은 집합인 두 도시를 다시 합치는 연산은 아무 효과가 없으므로 안전합니다. 이후 여행 계획을 읽고 각 도시의 대표 원소를 첫 번째 도시의 대표 원소와 비교합니다. 경로 압축은 이후 탐색을 빠르게 하고, 크기 기준 합치기는 트리의 높이를 낮게 유지합니다.

`N × N` 인접 행렬을 읽는 데 `O(N^2)` 시간이 걸립니다. DSU 연산은 분할 상환 `O(α(N))`이므로 전체 시간 복잡도는 `O(N^2 α(N) + M α(N))`, 공간 복잡도는 `O(N)`입니다. 입력기는 줄 단위 분할 대신 공백 문자를 건너뛰므로 공백과 줄바꿈의 배치에 영향을 받지 않습니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner();
    int cityCount = input.nextInt();
    int planLength = input.nextInt();
    DisjointSet dsu = new DisjointSet(cityCount);

    for (int from = 0; from < cityCount; from++) {
      for (int to = 0; to < cityCount; to++) {
        if (input.nextInt() == 1) {
          dsu.union(from, to);
        }
      }
    }

    int firstCity = input.nextInt() - 1;
    int representative = dsu.find(firstCity);
    for (int i = 1; i < planLength; i++) {
      int city = input.nextInt() - 1;
      if (dsu.find(city) != representative) {
        System.out.println("NO");
        return;
      }
    }

    System.out.println("YES");
  }

  private static final class DisjointSet {
    private final int[] parent;
    private final int[] size;

    DisjointSet(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int city) {
      if (parent[city] != city) {
        parent[city] = find(parent[city]);
      }
      return parent[city];
    }

    void union(int a, int b) {
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

  private static final class FastScanner {
    private final BufferedInputStream input = new BufferedInputStream(System.in);
    private final byte[] buffer = new byte[1 << 16];
    private int length;
    private int position;

    private int read() throws IOException {
      if (position == length) {
        length = input.read(buffer);
        position = 0;
        if (length == -1) return -1;
      }
      return buffer[position++];
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
}
```
