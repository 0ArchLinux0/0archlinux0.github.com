---
title: BOJ. Floyd(2) (11780)
author: MINJUN PARK
date: 2022-01-15 15:11:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Graph,
    Floyd-Warshall,
		플로이드 워셜,
    BOJ,
    Floyd(2),
    플로이드 2
  ]
pin: false
lang: ko
translation_key: boj-11780-all-pairs-paths
permalink: /ko/posts/boj-11780-all-pairs-paths/
---

[문제: BOJ 11780 — 플로이드 2](https://www.acmicpc.net/problem/11780)

양의 비용을 가진 방향 그래프에서 모든 순서쌍 도시 사이의 최소 비용과 그 최소 경로 하나를 구합니다. 같은 방향으로 여러 간선이 있을 수 있으므로 가장 싼 간선만 유지하면 됩니다. 대각선은 도시에서 자기 자신으로 가는 빈 경로를 나타내도록 0으로 초기화합니다.

## 다음 정점 행렬을 이용한 플로이드–워셜

`distance[i][j]`는 `i`에서 `j`까지 현재까지 알려진 최소 비용입니다. `next[i][j]`는 그 경로에서 `i` 다음에 방문할 첫 도시이며, 경로가 없으면 `-1`입니다. 직접 간선 `i -> j`의 첫 도시는 `j`로 기록합니다.

중간 도시 `k`를 거치는 경로의 비용은 `distance[i][k] + distance[k][j]`입니다. 이 값이 기존 경로보다 엄격히 작으면 비용과 첫 도시를 함께 갱신합니다. `i`에서 `k`로 가는 경로의 첫 도시는 `next[i][k]`이므로 새 경로 `i`에서 `j`의 첫 도시도 같습니다. 같은 비용일 때 기존 경로를 유지하도록 엄격히 개선될 때만 갱신합니다. 간선 비용이 양수이고 대각선 비용이 0이므로 최소 경로는 순환을 포함하지 않도록 선택할 수 있으며, 첫 도시 정보를 따라가면 목적지에 도달합니다. 문제에서 자기 자신으로 가는 경로를 출력하지 않으므로 `i == j`는 경로 대신 `0`을 출력합니다.

`long`의 유한한 큰 값으로 `INF`를 두고, 둘 중 한 구간이라도 도달 불가능하면 완화를 건너뜁니다. 따라서 센티널과 실제 비용을 더하지 않습니다. 비용 행렬 계산은 `O(N^3)` 시간과 `O(N^2)` 공간을 사용합니다. 모든 경로 출력은 `O(N^2 + 경로 출력량)` 시간, 즉 도시 번호를 출력하는 횟수에 비례하는 시간이 추가됩니다.

출력은 먼저 비용 행렬을 출력하며 도달 불가능한 칸은 `0`입니다. 이어서 모든 순서쌍의 경로 정보를 행 우선 순서로 출력합니다. 도달 불가능한 쌍과 시작·도착 도시가 같은 쌍은 각각 한 줄에 `0`만 출력합니다. 나머지 도달 가능한 쌍은 경로에 포함된 도시 수와 시작점 및 도착점을 포함한 도시 순서를 출력합니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int cityCount = input.nextInt();
        int routeCount = input.nextInt();

        long[][] distance = new long[cityCount][cityCount];
        int[][] next = new int[cityCount][cityCount];
        for (int i = 0; i < cityCount; i++) {
            java.util.Arrays.fill(distance[i], INF);
            java.util.Arrays.fill(next[i], -1);
            distance[i][i] = 0;
        }

        for (int i = 0; i < routeCount; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            long cost = input.nextLong();
            if (cost < distance[from][to]) {
                distance[from][to] = cost;
                next[from][to] = to;
            }
        }

        for (int middle = 0; middle < cityCount; middle++) {
            for (int from = 0; from < cityCount; from++) {
                if (distance[from][middle] == INF) {
                    continue;
                }
                for (int to = 0; to < cityCount; to++) {
                    if (distance[middle][to] == INF) {
                        continue;
                    }
                    long candidate = distance[from][middle] + distance[middle][to];
                    if (candidate < distance[from][to]) {
                        distance[from][to] = candidate;
                        next[from][to] = next[from][middle];
                    }
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (int from = 0; from < cityCount; from++) {
            for (int to = 0; to < cityCount; to++) {
                if (to > 0) {
                    output.append(' ');
                }
                output.append(distance[from][to] == INF ? 0 : distance[from][to]);
            }
            output.append('\n');
        }

        int[] path = new int[cityCount + 1];
        for (int from = 0; from < cityCount; from++) {
            for (int to = 0; to < cityCount; to++) {
                if (from == to || next[from][to] == -1) {
                    output.append("0\n");
                    continue;
                }

                int length = 0;
                int current = from;
                path[length++] = current;
                while (current != to) {
                    current = next[current][to];
                    path[length++] = current;
                }

                output.append(length);
                for (int i = 0; i < length; i++) {
                    output.append(' ').append(path[i] + 1);
                }
                output.append('\n');
            }
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
