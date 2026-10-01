---
title: BOJ. DSLR (9019)
author: MINJUN PARK
date: 2022-01-14 17:58:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    DSLR,
  ]
pin: false
lang: ko
translation_key: boj-9019-dslr-shortest-commands
permalink: /ko/posts/boj-9019-dslr-shortest-commands/
---

## 풀이

`0`부터 `9999`까지의 정수를 각각 그래프의 정점으로 봅니다. DSLR의 네 명령은 현재 값에서 해당 명령의 결과 값으로 향하는 간선입니다. 모든 명령의 비용이 1이므로 너비 우선 탐색(BFS)은 시작 상태로부터 명령 횟수가 적은 상태부터 탐색합니다.

각 상태의 다음 상태는 `D`, `S`, `L`, `R` 순서로 방문합니다. BFS는 최단 경로를 먼저 찾으며, 길이가 같은 경로는 이 명령 우선순위에 따라 탐색합니다. 상태를 큐에 넣을 때 방문 처리하므로 처음 기록된 부모와 명령이 해당 상태로 가는 우선순위가 가장 높은 최단 경로를 나타냅니다. 10,000개 상태는 각각 최대 한 번만 큐에 들어갑니다. 큐에는 정수 상태만 저장하고, 부모 연결을 거슬러 올라가 답을 복원합니다. 시작 값과 목표 값이 같으면 경로 길이가 0이므로 빈 줄을 출력합니다.

각 상태에서 네 가지 전이를 확인하므로 테스트 케이스당 시간 복잡도와 보조 공간 복잡도는 모두 `O(10,000)`입니다.

[문제 링크](https://www.acmicpc.net/problem/9019)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int STATE_COUNT = 10000;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            StringTokenizer input = new StringTokenizer(reader.readLine());
            int start = Integer.parseInt(input.nextToken());
            int target = Integer.parseInt(input.nextToken());

            int[] parent = new int[STATE_COUNT];
            Arrays.fill(parent, -1);
            char[] commandUsed = new char[STATE_COUNT];
            int[] queue = new int[STATE_COUNT];
            int head = 0;
            int tail = 0;

            parent[start] = start;
            queue[tail++] = start;

            while (head < tail && parent[target] == -1) {
                int current = queue[head++];

                int next = current * 2 % STATE_COUNT;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'D';
                    queue[tail++] = next;
                }

                next = current == 0 ? 9999 : current - 1;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'S';
                    queue[tail++] = next;
                }

                next = current % 1000 * 10 + current / 1000;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'L';
                    queue[tail++] = next;
                }

                next = current % 10 * 1000 + current / 10;
                if (parent[next] == -1) {
                    parent[next] = current;
                    commandUsed[next] = 'R';
                    queue[tail++] = next;
                }
            }

            char[] reversedPath = new char[STATE_COUNT];
            int pathLength = 0;
            for (int state = target; state != start; state = parent[state]) {
                reversedPath[pathLength++] = commandUsed[state];
            }
            for (int i = pathLength - 1; i >= 0; i--) {
                output.append(reversedPath[i]);
            }
            output.append('\n');
        }

        System.out.print(output);
    }
}
```
