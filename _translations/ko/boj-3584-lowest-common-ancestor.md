---
title: BOJ 3584 - 가장 가까운 공통 조상
author: MINJUN PARK
date: 2022-02-08 14:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트리, 가장 가까운 공통 조상, LCA]
pin: false
lang: ko
translation_key: boj-3584-lowest-common-ancestor
permalink: /ko/posts/boj-3584-lowest-common-ancestor/
source_permalink: /posts/BOJ-3584/
---

[문제: BOJ 3584 — 가장 가까운 공통 조상](https://www.acmicpc.net/problem/3584) · [English](/posts/BOJ-3584/) · [日本語](/ja/posts/boj-3584-lowest-common-ancestor/)

각 테스트 케이스에는 `N`개의 정점으로 이루어진 루트 트리와 가장 가까운 공통 조상(LCA)을 구할 정점 쌍이 주어집니다. 테스트 케이스마다 질의는 정확히 하나입니다. 입력 간선은 부모에서 자식 방향으로 주어지므로 각 자식의 부모를 `parent` 배열에 저장합니다. 부모가 없는 유일한 정점이 루트입니다.

질의의 첫 번째 정점부터 시작해 `parent`를 따라 루트까지 올라가며 조상들을 표시합니다. 그런 다음 두 번째 정점에서 시작해 표시된 정점을 만날 때까지 부모를 따라 올라갑니다. 이때 처음 만나는 표시된 정점이 LCA입니다. 두 번째 정점의 위쪽 경로에 있는 각 정점은 두 번째 정점의 조상이고, 첫 번째 정점의 조상 경로와 처음 만나는 곳이 공통 조상 중 가장 깊은 정점이기 때문입니다. 따라서 한 정점이 다른 정점의 조상인 경우도 처리하며, 루트가 답인 경우도 포함됩니다.

`parent` 배열 구성은 `O(N)` 시간입니다. 조상 표시와 부모를 따라 올라가는 과정도 합쳐서 최대 `O(N)` 시간이며, 공간은 `O(N)`입니다. 반복문만 사용하므로 정점 100,000개가 한 줄로 이어진 트리에서도 호출 스택이 넘치지 않습니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        int testCases = Integer.parseInt(input.readLine().trim());

        for (int test = 0; test < testCases; test++) {
            int n = Integer.parseInt(input.readLine().trim());
            int[] parent = new int[n + 1];
            for (int edge = 0; edge < n - 1; edge++) {
                StringTokenizer tokens = new StringTokenizer(input.readLine());
                int from = Integer.parseInt(tokens.nextToken());
                int to = Integer.parseInt(tokens.nextToken());
                parent[to] = from;
            }

            int root = 1;
            while (parent[root] != 0) {
                root++;
            }

            StringTokenizer query = new StringTokenizer(input.readLine());
            int first = Integer.parseInt(query.nextToken());
            int second = Integer.parseInt(query.nextToken());

            boolean[] ancestors = new boolean[n + 1];
            for (int node = first; ; node = parent[node]) {
                ancestors[node] = true;
                if (node == root) {
                    break;
                }
            }
            while (!ancestors[second]) {
                second = parent[second];
            }
            output.append(second).append('\n');
        }

        System.out.print(output);
    }
}
```
