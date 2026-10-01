---
title: BOJ 3665 - 최종 순위
author: MINJUN PARK
date: 2022-02-06 22:48:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 위상 정렬, 최종 순위]
pin: false
lang: ko
translation_key: boj-3665-final-ranking
permalink: /ko/posts/boj-3665-final-ranking/
source_permalink: /posts/BOJ-3665/
---

[문제: BOJ 3665 — 최종 순위](https://www.acmicpc.net/problem/3665) · [English](/posts/BOJ-3665/) · [日本語](/ja/posts/boj-3665-final-ranking/)

작년 순위는 모든 팀 쌍의 순서를 알려 줍니다. 순위가 높은 팀에서 낮은 팀으로 방향 간선을 만들면 완전한 방향 그래프가 됩니다. 올해 순위가 바뀐 두 팀은 그 쌍의 간선 방향을 뒤집습니다. 인접 행렬과 도착 정점의 진입 차수를 함께 갱신하면 위상 정렬에 필요한 정보가 유지됩니다.

Kahn 알고리즘은 진입 차수가 0인 팀을 하나씩 제거합니다. 어떤 단계에서 선택 가능한 팀이 둘 이상이면 다음 팀을 여러 방식으로 고를 수 있으므로 최종 순위가 확정되지 않습니다. 반대로 제거한 팀 수가 `N`보다 적으면 순환이 있어 모든 팀의 순위를 정할 수 없습니다. 순환 여부를 먼저 판정해, 모든 팀을 출력하지 못한 경우 `IMPOSSIBLE`을 출력합니다. 모든 팀을 출력할 수 있으면 여러 선택이 있었을 때 `?`, 선택이 항상 하나뿐이었을 때는 유일한 순위를 출력합니다.

초기 그래프에는 `N(N - 1) / 2`개의 간선이 있습니다. 그래프 구성과 Kahn 알고리즘에서 가능한 모든 간선을 확인하는 데 `O(N²)`, 순위 변경을 처리하는 데 `O(M)`이 걸리므로 전체 시간 복잡도는 `O(N² + M)`이고 공간 복잡도는 `O(N²)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int[] previous = new int[n];
            for (int i = 0; i < n; i++) {
                previous[i] = input.nextInt() - 1;
            }

            boolean[][] edge = new boolean[n][n];
            int[] indegree = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    edge[previous[i]][previous[j]] = true;
                    indegree[previous[j]]++;
                }
            }

            int changes = input.nextInt();
            for (int i = 0; i < changes; i++) {
                int a = input.nextInt() - 1;
                int b = input.nextInt() - 1;
                if (edge[a][b]) {
                    edge[a][b] = false;
                    edge[b][a] = true;
                    indegree[b]--;
                    indegree[a]++;
                } else {
                    edge[b][a] = false;
                    edge[a][b] = true;
                    indegree[a]--;
                    indegree[b]++;
                }
            }

            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for (int team = 0; team < n; team++) {
                if (indegree[team] == 0) {
                    queue.addLast(team);
                }
            }

            int[] ranking = new int[n];
            int count = 0;
            boolean ambiguous = false;
            while (!queue.isEmpty()) {
                if (queue.size() > 1) {
                    ambiguous = true;
                }
                int team = queue.removeFirst();
                ranking[count++] = team;
                for (int next = 0; next < n; next++) {
                    if (edge[team][next] && --indegree[next] == 0) {
                        queue.addLast(next);
                    }
                }
            }

            if (count < n) {
                answer.append("IMPOSSIBLE\n");
            } else if (ambiguous) {
                answer.append("?\n");
            } else {
                for (int team : ranking) {
                    answer.append(team + 1).append(' ');
                }
                answer.setLength(answer.length() - 1);
                answer.append('\n');
            }
        }

        System.out.print(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
