---
title: BOJ. Hide and Seek (1697)
author: MINJUN PARK
date: 2021-12-28 00:15:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Hide and Seek, 숨바꼭질]
pin: false
lang: ko
translation_key: boj-1697-hide-and-seek
permalink: /ko/posts/boj-1697-hide-and-seek/
---

## 풀이

`0`부터 `100000`까지의 정수를 각각 하나의 상태로 봅니다. 상태 `x`에서는 결과가 허용 범위 안에 있을 때 `x - 1`, `x + 1`, `2 * x`로 한 번에 이동할 수 있습니다. 모든 간선의 비용이 1이므로 너비 우선 탐색(BFS)은 시작점으로부터의 거리가 작은 상태부터 방문합니다. 따라서 목표를 처음 발견했을 때의 이동 횟수가 최단 거리입니다.

거리 배열은 방문 여부도 표시합니다. `-1`은 아직 방문하지 않은 상태를 뜻합니다. 각 상태는 큐에 최대 한 번만 들어가므로 시간 복잡도는 `O(100000)`, 공간 복잡도는 `O(100000)`입니다.

[문제 링크](https://www.acmicpc.net/problem/1697)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX = 100000;

    public static void main(String[] args) throws IOException {
        StringTokenizer input = new StringTokenizer(
                new BufferedReader(new InputStreamReader(System.in)).readLine());
        int start = Integer.parseInt(input.nextToken());
        int target = Integer.parseInt(input.nextToken());
        if (start == target) {
            System.out.println(0);
            return;
        }

        int[] distance = new int[MAX + 1];
        Arrays.fill(distance, -1);
        int[] queue = new int[MAX + 1];
        int head = 0;
        int tail = 0;

        distance[start] = 0;
        queue[tail++] = start;

        while (head < tail) {
            int current = queue[head++];
            int nextDistance = distance[current] + 1;

            if (current - 1 >= 0 && distance[current - 1] == -1) {
                if (current - 1 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current - 1] = nextDistance;
                queue[tail++] = current - 1;
            }
            if (current + 1 <= MAX && distance[current + 1] == -1) {
                if (current + 1 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current + 1] = nextDistance;
                queue[tail++] = current + 1;
            }
            if (current * 2 <= MAX && distance[current * 2] == -1) {
                if (current * 2 == target) {
                    System.out.println(nextDistance);
                    return;
                }
                distance[current * 2] = nextDistance;
                queue[tail++] = current * 2;
            }
        }
    }
}
```
