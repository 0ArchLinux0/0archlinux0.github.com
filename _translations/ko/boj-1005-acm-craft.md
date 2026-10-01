---
title: BOJ 1005 - ACM Craft
author: MINJUN PARK
date: 2022-02-03 22:17:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 위상 정렬, 그래프, ACM Craft]
pin: false
lang: ko
translation_key: boj-1005-acm-craft
permalink: /ko/posts/boj-1005-acm-craft/
source_permalink: /posts/BOJ-1005/
---

[문제: BOJ 1005 — ACM Craft](https://www.acmicpc.net/problem/1005) · [English](/posts/BOJ-1005/) · [日本語](/ja/posts/boj-1005-acm-craft/)

각 규칙 `A B`는 건물 `A`를 완성한 뒤에 `B`를 지을 수 있다는 뜻입니다. 규칙을 방향 간선으로 표현하면 비순환 방향 그래프가 됩니다. 위상 순서로 처리하면 각 건물의 모든 선행 건물이 먼저 처리됩니다.

`finish[v]`를 건물 `v`를 가장 빨리 완성하는 시각이라고 합시다. 점화식은 선행 건물 `p`에 대해 `finish[v] = duration[v] + max(finish[p])`입니다. 선행 건물이 없는 시작 건물은 최댓값을 0으로 정의합니다. 각 간선 `u -> v`를 처리할 때 `finish[u] + duration[v]`를 후보로 계산하고, `v`의 모든 선행 건물 중 최댓값을 유지합니다. 선행 경로가 여러 개라면 가장 늦게 끝나는 경로가 완료될 때까지 기다려야 합니다. 따라서 시작 시 `finish[v] = duration[v]`로 초기화하면 시작 건물과 시작 건물인 목표도 올바르게 처리됩니다.

Kahn 알고리즘으로 진입 차수가 0인 건물들을 큐에 넣습니다. 큐에서 건물을 꺼내 나가는 간선마다 완료 시각을 갱신하고 진입 차수를 감소시킵니다. 모든 선행 건물이 처리된 건물만 큐에 들어갑니다. 각 테스트 케이스에서 목표 건물의 `finish` 값을 출력합니다.

건물 수를 `N`, 규칙 수를 `K`라 할 때 시간 복잡도와 공간 복잡도는 각각 `O(N + K)`입니다. 완료 시각은 `long`에 저장합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int k = input.nextInt();
            long[] duration = new long[n];
            long[] finish = new long[n];
            int[] indegree = new int[n];
            List<Integer>[] graph = new ArrayList[n];

            for (int building = 0; building < n; building++) {
                duration[building] = input.nextInt();
                finish[building] = duration[building];
                graph[building] = new ArrayList<>();
            }

            for (int rule = 0; rule < k; rule++) {
                int prerequisite = input.nextInt() - 1;
                int building = input.nextInt() - 1;
                graph[prerequisite].add(building);
                indegree[building]++;
            }

            int target = input.nextInt() - 1;
            int[] queue = new int[n];
            int front = 0;
            int back = 0;
            for (int building = 0; building < n; building++) {
                if (indegree[building] == 0) {
                    queue[back++] = building;
                }
            }

            while (front < back) {
                int prerequisite = queue[front++];
                for (int building : graph[prerequisite]) {
                    finish[building] = Math.max(
                        finish[building],
                        finish[prerequisite] + duration[building]
                    );
                    if (--indegree[building] == 0) {
                        queue[back++] = building;
                    }
                }
            }

            output.append(finish[target]).append('\n');
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        int nextInt() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokenizer.nextToken());
        }
    }
}
```
