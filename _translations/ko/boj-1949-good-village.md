---
title: BOJ 1949 - 우수 마을
author: MINJUN PARK
date: 2022-02-02 02:50:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 그래프, 동적 계획법, 우수 마을]
pin: false
lang: ko
translation_key: boj-1949-good-village
permalink: /ko/posts/boj-1949-good-village/
source_permalink: /posts/BOJ-1949/
---

[문제: BOJ 1949 — 우수 마을](https://www.acmicpc.net/problem/1949) · [English](/posts/BOJ-1949/) · [日本語](/ja/posts/boj-1949-good-village/)

각 마을에는 인구수가 주어집니다. 서로 인접한 두 마을을 동시에 선택하지 않으면서 선택한 마을들의 인구수 합을 최대로 만드는 문제입니다. 입력은 `N`, 각 마을의 인구수 `N`개, 그리고 양방향 도로 `N - 1`개로 이루어집니다.

마을 1을 루트로 삼아 트리를 반복문으로 순회합니다. 방문 순서를 저장한 뒤 역순으로 처리하면 재귀 없이 자식부터 부모 순으로 계산할 수 있으므로, 긴 경로에서 호출 스택이 넘치는 문제도 피합니다. 각 마을 `u`에 대해 두 값을 관리합니다.

- `take[u]`: `u`를 선택했을 때 가능한 최대 인구수 합입니다. 자식 마을은 선택할 수 없으므로 `take[u] = population[u] + sum(skip[child])`입니다.
- `skip[u]`: `u`를 선택하지 않았을 때 가능한 최대 인구수 합입니다. 각 자식은 선택하거나 선택하지 않을 수 있으므로 `skip[u] = sum(max(take[child], skip[child]))`입니다.

정답은 루트의 `max(take[root], skip[root])`입니다. 인구수는 양수이므로 루트 하나만 선택해도 유효한 양수 합을 얻습니다. 따라서 아무 마을도 선택하지 않는 경우가 양수인 답보다 커질 수 없습니다. 마을이 하나뿐이면 그 인구수를 반환합니다. 경로에서는 인접한 선택이 두 상태 사이에서 경쟁하고, 별 모양에서는 중심 마을 하나를 선택하는 경우와 잎 마을들을 선택하는 경우를 비교합니다.

인접 리스트, 방문 순서, DP 배열은 각각 `O(N)` 공간을 사용합니다. 각 정점과 간선을 상수 횟수만큼 처리하므로 시간 복잡도는 `O(N)`입니다. 인구수 합이 `int` 범위를 넘지 않도록 DP 합계에는 `long`을 사용합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        long[] population = new long[n];
        StringTokenizer values = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            population[i] = Long.parseLong(values.nextToken());
        }

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer edge = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(edge.nextToken()) - 1;
            int b = Integer.parseInt(edge.nextToken()) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n];
        int[] order = new int[n];
        int size = 0;
        order[size++] = 0;
        parent[0] = -1;
        for (int i = 0; i < size; i++) {
            int node = order[i];
            for (int neighbor : graph[node]) {
                if (neighbor == parent[node]) {
                    continue;
                }
                parent[neighbor] = node;
                order[size++] = neighbor;
            }
        }

        long[] take = new long[n];
        long[] skip = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            int node = order[i];
            take[node] = population[node];
            for (int child : graph[node]) {
                if (parent[child] == node) {
                    take[node] += skip[child];
                    skip[node] += Math.max(take[child], skip[child]);
                }
            }
        }

        System.out.println(Math.max(take[0], skip[0]));
    }
}
```
