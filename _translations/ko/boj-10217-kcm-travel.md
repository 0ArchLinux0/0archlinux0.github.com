---
title: BOJ. KCM Travel (10217)
author: MINJUN PARK
date: 2022-01-05 01:58:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Dijkstra,
    Graph,
    KCM Travel,
    Review,
    difficult
  ]
pin: false
lang: ko
translation_key: boj-10217-kcm-travel
permalink: /ko/posts/boj-10217-kcm-travel/
---

## 풀이

`best[v][c]`는 총비용을 `c` 이하로 사용해 공항 `v`에 도착하는 최소 시간을 나타냅니다. 시작점은 비용을 쓰지 않고 도달 가능하므로 모든 비용 한도에서 `best[0][c] = 0`으로 두고, 나머지는 무한대로 초기화합니다. 각 티켓의 새 비용이 `M` 이하일 때만 완화합니다. 어떤 비용 한도에서 경로가 더 좋은 시간을 만들면 더 큰 비용 한도의 값에도 그 시간을 전파합니다. 사용할 수 있는 예산이 늘어도 최소 시간은 나빠지지 않습니다.

`(공항, 비용)` 상태를 정점으로 보고 다익스트라 알고리즘을 실행합니다. 우선순위 큐는 `Integer.compare`를 사용해 이동 시간이 작은 순으로 정렬하므로 뺄셈으로 인한 비교 오버플로를 피합니다. 큐에서 꺼낸 시간이 해당 비용 한도에서 이미 더 나은 시간보다 크면 건너뜁니다. 더 적은 비용으로 같은 시간 이하에 도달한 경로가 이를 지배하기 때문입니다. 모든 티켓의 이동 시간이 음수가 아니므로 목적지가 큐에서 꺼내지는 순간 예산 내 최소 시간이 확정되어 탐색을 끝낼 수 있습니다. 각 테스트 케이스 안에서 그래프, 테이블, 큐를 새로 만들어 케이스 간 상태가 섞이지 않게 합니다.

불변식은 유한한 테이블 값이 해당 비용 한도 안에서 공항에 도착하는 지금까지의 최소 시간이고, 큐의 각 상태는 기록된 비용으로 실제 도달 가능한 경로라는 것입니다. 완화는 입력 예산을 넘지 않는 다음 티켓을 검사하고, 더 큰 비용 한도까지 개선 값을 전파해 이 의미를 유지합니다. 티켓 시간이 음수가 아니므로 다익스트라가 가능한 경로를 최소 시간 순으로 확정합니다. 따라서 오래된 상태가 아닌 목적지 상태가 처음 큐에서 꺼내질 때 예산 내 최소 시간이 됩니다.

테이블 상태는 최대 `N(M + 1)`개이므로 공간 복잡도는 `O(NM)`입니다. 티켓이 `K`개일 때 희소 상태에서 우선순위 큐 처리량은 대략 `O(K log(NM))`이며, 최악에는 여러 예산 상태에서 티켓을 검사하므로 더 명시적으로 `O(KM log(NM))`입니다.

[문제 링크](https://www.acmicpc.net/problem/10217)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static class Ticket {
        final int destination;
        final int cost;
        final int time;

        Ticket(int destination, int cost, int time) {
            this.destination = destination;
            this.cost = cost;
            this.time = time;
        }
    }

    private static class State {
        final int airport;
        final int cost;
        final int time;

        State(int airport, int cost, int time) {
            this.airport = airport;
            this.cost = cost;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int airportCount = input.nextInt();
            int budget = input.nextInt();
            int ticketCount = input.nextInt();

            List<Ticket>[] graph = new ArrayList[airportCount];
            for (int airport = 0; airport < airportCount; airport++) {
                graph[airport] = new ArrayList<>();
            }
            for (int i = 0; i < ticketCount; i++) {
                int from = input.nextInt() - 1;
                int to = input.nextInt() - 1;
                int cost = input.nextInt();
                int time = input.nextInt();
                graph[from].add(new Ticket(to, cost, time));
            }

            int[][] best = new int[airportCount][budget + 1];
            for (int[] times : best) {
                Arrays.fill(times, Integer.MAX_VALUE);
            }
            for (int budgetLimit = 0; budgetLimit <= budget; budgetLimit++) {
                best[0][budgetLimit] = 0;
            }

            PriorityQueue<State> queue = new PriorityQueue<>(
                    (left, right) -> Integer.compare(left.time, right.time));
            queue.add(new State(0, 0, 0));

            int answer = -1;
            while (!queue.isEmpty()) {
                State current = queue.poll();
                if (current.time > best[current.airport][current.cost]) {
                    continue;
                }
                if (current.airport == airportCount - 1) {
                    answer = current.time;
                    break;
                }

                for (Ticket ticket : graph[current.airport]) {
                    int nextCost = current.cost + ticket.cost;
                    if (nextCost > budget) {
                        continue;
                    }
                    int nextTime = current.time + ticket.time;
                    if (nextTime < best[ticket.destination][nextCost]) {
                        for (int limit = nextCost; limit <= budget; limit++) {
                            if (nextTime >= best[ticket.destination][limit]) {
                                break;
                            }
                            best[ticket.destination][limit] = nextTime;
                        }
                        queue.add(new State(ticket.destination, nextCost, nextTime));
                    }
                }
            }

            if (answer == -1) {
                output.append("Poor KCM\n");
            } else {
                output.append(answer).append('\n');
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
