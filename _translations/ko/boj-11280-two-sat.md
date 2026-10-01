---
title: BOJ 11280 - 2-SAT - 3
author: MINJUN PARK
date: 2022-02-12 11:13:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 강한 연결 요소, 2-SAT]
pin: false
lang: ko
translation_key: boj-11280-two-sat
permalink: /ko/posts/boj-11280-two-sat/
source_permalink: /posts/BOJ-11280/
---

[문제: BOJ 11280 — 2-SAT - 3](https://www.acmicpc.net/problem/11280) · [English](/posts/BOJ-11280/) · [日本語](/ja/posts/boj-11280-two-sat/)

각 절 `(a OR b)`는 `¬a → b`와 `¬b → a`라는 두 함의 간선으로 바꿀 수 있습니다. 부호가 있는 정수로 리터럴을 입력받으며, 변수 `i`의 양수 리터럴 `i`는 정점 `i - 1`, 음수 리터럴 `-i`는 정점 `N + i - 1`로 인코딩합니다. 따라서 부정을 취하는 정점 인덱스는 `v < N`이면 `v + N`, 아니면 `v - N`입니다. 각 함의 간선은 정방향 그래프와 역방향 그래프에 함께 저장합니다.

SCC(강한 연결 요소)를 구하기 위해 재귀 없는 코사라주 알고리즘을 사용합니다. 첫 번째 순회에서는 각 정점의 다음 간선 위치를 스택에 보존해 DFS의 종료 순서를 기록합니다. 정점과 간선이 많아도 호출 스택을 사용하지 않으므로 안전합니다. 종료 순서의 역순으로 역방향 그래프를 순회해 각 정점의 SCC 번호를 매깁니다. 어떤 변수 `i`와 그 부정 리터럴이 같은 SCC에 있으면 서로 함의하므로 둘 다 참이어야 하는 모순이 발생합니다. 그런 변수가 하나라도 있으면 답은 `0`, 없으면 `1`입니다. 입력은 단위 절도 포함할 수 있으며, 예를 들어 `(x OR x)`는 `¬x → x`를 추가하므로 같은 규칙으로 처리됩니다.

정점 수는 `2N`, 함의 간선 수는 `2M`입니다. 두 DFS 순회 모두 각 정점과 간선을 상수 번 처리하므로 시간과 공간 복잡도는 `O(N + M)`입니다. 출력은 정답 숫자 하나뿐입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int literalIndex(int literal, int n) {
        return literal > 0 ? literal - 1 : n - literal - 1;
    }

    private static int negation(int vertex, int n) {
        return vertex < n ? vertex + n : vertex - n;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer first = new StringTokenizer(input.readLine());
        int n = Integer.parseInt(first.nextToken());
        int m = Integer.parseInt(first.nextToken());
        int vertices = 2 * n;

        List<Integer>[] graph = new ArrayList[vertices];
        List<Integer>[] reverse = new ArrayList[vertices];
        for (int v = 0; v < vertices; v++) {
            graph[v] = new ArrayList<>();
            reverse[v] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer clause = new StringTokenizer(input.readLine());
            int a = literalIndex(Integer.parseInt(clause.nextToken()), n);
            int b = literalIndex(Integer.parseInt(clause.nextToken()), n);
            int notA = negation(a, n);
            int notB = negation(b, n);
            graph[notA].add(b);
            reverse[b].add(notA);
            graph[notB].add(a);
            reverse[a].add(notB);
        }

        boolean[] visited = new boolean[vertices];
        int[] order = new int[vertices];
        int orderSize = 0;
        int[] stackVertex = new int[vertices];
        int[] stackNext = new int[vertices];

        for (int start = 0; start < vertices; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stackVertex[top] = start;
            stackNext[top] = 0;
            visited[start] = true;
            while (top >= 0) {
                int v = stackVertex[top];
                if (stackNext[top] < graph[v].size()) {
                    int next = graph[v].get(stackNext[top]++);
                    if (!visited[next]) {
                        visited[next] = true;
                        stackVertex[++top] = next;
                        stackNext[top] = 0;
                    }
                } else {
                    order[orderSize++] = v;
                    top--;
                }
            }
        }

        int[] component = new int[vertices];
        int componentId = 0;
        int[] stack = new int[vertices];
        for (int i = orderSize - 1; i >= 0; i--) {
            int start = order[i];
            if (component[start] != 0) {
                continue;
            }
            componentId++;
            int top = 0;
            stack[top++] = start;
            component[start] = componentId;
            while (top > 0) {
                int v = stack[--top];
                for (int next : reverse[v]) {
                    if (component[next] == 0) {
                        component[next] = componentId;
                        stack[top++] = next;
                    }
                }
            }
        }

        for (int variable = 0; variable < n; variable++) {
            if (component[variable] == component[variable + n]) {
                System.out.println(0);
                return;
            }
        }
        System.out.println(1);
    }
}
```
