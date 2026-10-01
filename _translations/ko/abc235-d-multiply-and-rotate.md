---
title: AtCoder ABC 235 D - Multiply and Rotate
author: MINJUN PARK
date: 2022-01-16 02:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC]
pin: false
lang: ko
translation_key: abc235-d-multiply-and-rotate
permalink: /ko/posts/abc235-d-multiply-and-rotate/
source_permalink: /posts/Atcoder-D-Multiply-and-Rotate/
---

[문제: AtCoder ABC 235 D — Multiply and Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_d) · [English](/posts/Atcoder-D-Multiply-and-Rotate/) · [日本語](/ja/posts/abc235-d-multiply-and-rotate/)

정수 `1`에서 시작해 다음 두 연산 중 하나를 적용합니다. 현재 정수에 `A`를 곱하거나, 마지막 십진 숫자를 맨 앞으로 옮깁니다. 회전은 두 자리 이상이며 끝자리가 0이 아닐 때만 가능합니다. `N`에 도달하는 데 필요한 최소 연산 횟수를 구하고, 도달할 수 없다면 `-1`을 출력합니다. 예를 들어 `120`은 회전할 수 없습니다. 끝의 `0`을 앞으로 옮긴 뒤 정수로 읽으면 `12`가 되지만, 문제에서 이 연산을 허용하지 않습니다.

각 정수를 정점으로 하고 가능한 연산을 간선으로 보는 비가중 방향 그래프를 생각할 수 있습니다. `1`에서 너비 우선 탐색(BFS)을 하면 연산 횟수가 작은 상태부터 방문하므로, `N`에 처음 기록되는 거리가 최소 연산 횟수입니다. `dist` 배열은 최단 거리 저장과 중복 방문 방지를 겸합니다. 제약 `A, N ≤ 10^6`에 대해 탐색 상태는 `1`부터 `10^6`까지로 제한합니다. 곱셈 결과는 `10^6` 이하일 때만 큐에 넣고, 탐색 중인 수를 회전한 값도 `10^6` 이하입니다. 따라서 거리 배열과 큐의 크기는 각각 `10^6 + 1`이면 충분합니다. 각 상태에서 가능한 연산은 최대 두 개이므로 제한된 상태 그래프에서 시간과 공간 복잡도는 `O(10^6)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final int LIMIT = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String[] values = input.readLine().split(" ");
        int a = Integer.parseInt(values[0]);
        int target = Integer.parseInt(values[1]);

        int[] distance = new int[LIMIT + 1];
        Arrays.fill(distance, -1);
        int[] queue = new int[LIMIT + 1];
        int head = 0;
        int tail = 0;

        distance[1] = 0;
        queue[tail++] = 1;

        while (head < tail) {
            int current = queue[head++];
            if (current == target) {
                System.out.println(distance[current]);
                return;
            }

            long product = (long) current * a;
            if (product <= LIMIT && distance[(int) product] == -1) {
                distance[(int) product] = distance[current] + 1;
                queue[tail++] = (int) product;
            }

            if (current >= 10 && current % 10 != 0) {
                int place = 1;
                while (place <= current / 10) {
                    place *= 10;
                }
                int rotated = current % 10 * place + current / 10;
                if (distance[rotated] == -1) {
                    distance[rotated] = distance[current] + 1;
                    queue[tail++] = rotated;
                }
            }
        }

        System.out.println(-1);
    }
}
```
