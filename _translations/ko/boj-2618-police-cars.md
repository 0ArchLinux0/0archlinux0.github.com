---
title: BOJ. 경찰차 (2618)
author: MINJUN PARK
date: 2022-01-18 19:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Dynamic Programming, BOJ, Police Car, 경찰차, Review]
pin: false
lang: ko
translation_key: boj-2618-police-cars
permalink: /ko/posts/boj-2618-police-cars/
source_permalink: /posts/BOJ-2618/
---

[BOJ 2618: 경찰차](https://www.acmicpc.net/problem/2618)

## 두 경찰차의 마지막 사건을 이용한 동적 계획법

`dp[a][b]`를 경찰차 1이 마지막으로 처리한 사건 번호가 `a`, 경찰차 2가 마지막으로 처리한 사건 번호가 `b`일 때 남은 사건을 모두 처리하는 최소 이동 거리라고 정의합니다. `0`은 아직 사건을 맡지 않았음을 뜻합니다. 따라서 `a = 0`일 때 경찰차 1의 위치는 `(1, 1)`, `b = 0`일 때 경찰차 2의 위치는 `(N, N)`이고, 그 외에는 해당 번호 사건의 위치입니다.

두 경찰차가 함께 `max(a, b)`번 사건까지 처리했으므로 다음 사건은 항상 `max(a, b) + 1`입니다. 이 사건을 경찰차 1 또는 2에 배정하는 두 경우만 고려하면 됩니다. 각 경우의 이동 거리는 현재 위치에서 다음 사건까지의 맨해튼 거리입니다.

```text
dp[a][b] = 0                                            if max(a, b) = W
next = max(a, b) + 1

dp[a][b] = min(
    distance(car 1 position(a), incident[next]) + dp[next][b],
    distance(car 2 position(b), incident[next]) + dp[a][next]
)
```

각 전이는 다음 사건 하나를 맡기므로 재귀 상태는 종료 상태에 도달하고, 두 배정 중 더 작은 값을 고르면 최적성을 유지합니다. 메모이제이션으로 각 `(a, b)` 상태를 한 번만 계산합니다. 답을 구한 뒤에도 같은 두 비용을 비교해 사건마다 담당 경찰차를 출력합니다. 비용이 같으면 어느 쪽을 골라도 최적이며, 코드는 경찰차 1을 선택합니다. `W = 0`이면 시작 상태가 바로 종료 상태이므로 비용 `0`만 출력하고 사건 담당 줄은 없습니다. 시간과 공간 복잡도는 각각 `O(W²)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int n;
    private static int w;
    private static int[][] incidents;
    private static int[][] memo;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        n = input.nextInt();
        w = input.nextInt();
        incidents = new int[w + 1][2];
        for (int i = 1; i <= w; i++) {
            incidents[i][0] = input.nextInt();
            incidents[i][1] = input.nextInt();
        }

        memo = new int[w + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            for (int j = 0; j <= w; j++) {
                memo[i][j] = -1;
            }
        }

        StringBuilder output = new StringBuilder();
        output.append(minimumDistance(0, 0)).append('\n');
        int car1Last = 0;
        int car2Last = 0;
        while (Math.max(car1Last, car2Last) < w) {
            int next = Math.max(car1Last, car2Last) + 1;
            int car1Cost = distance(1, car1Last, next)
                    + minimumDistance(next, car2Last);
            int car2Cost = distance(2, car2Last, next)
                    + minimumDistance(car1Last, next);

            if (car1Cost <= car2Cost) {
                output.append(1).append('\n');
                car1Last = next;
            } else {
                output.append(2).append('\n');
                car2Last = next;
            }
        }
        System.out.print(output);
    }

    private static int minimumDistance(int car1Last, int car2Last) {
        if (Math.max(car1Last, car2Last) == w) return 0;
        if (memo[car1Last][car2Last] != -1) return memo[car1Last][car2Last];

        int next = Math.max(car1Last, car2Last) + 1;
        int assignToCar1 = distance(1, car1Last, next)
                + minimumDistance(next, car2Last);
        int assignToCar2 = distance(2, car2Last, next)
                + minimumDistance(car1Last, next);
        memo[car1Last][car2Last] = Math.min(assignToCar1, assignToCar2);
        return memo[car1Last][car2Last];
    }

    private static int distance(int car, int lastIncident, int nextIncident) {
        int startRow = lastIncident == 0 ? (car == 1 ? 1 : n) : incidents[lastIncident][0];
        int startColumn = lastIncident == 0 ? (car == 1 ? 1 : n) : incidents[lastIncident][1];
        return Math.abs(startRow - incidents[nextIncident][0])
                + Math.abs(startColumn - incidents[nextIncident][1]);
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
