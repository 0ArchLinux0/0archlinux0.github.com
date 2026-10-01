---
title: BOJ 3648 - 아이돌
author: MINJUN PARK
date: 2022-02-13 10:10:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 강한 연결 요소, 2-SAT, 아이돌]
pin: false
lang: ko
translation_key: boj-3648-idol-two-sat
permalink: /ko/posts/boj-3648-idol-two-sat/
source_permalink: /posts/BOJ-3648/
---

[문제: BOJ 3648 — 아이돌](https://www.acmicpc.net/problem/3648) · [English](/posts/BOJ-3648/) · [日本語](/ja/posts/boj-3648-idol-two-sat/)

각 테스트 케이스에서 모든 절을 만족시키고 변수 `1`을 참으로 만들어야 합니다. 절 `(a OR b)`는 함의 `¬a → b`, `¬b → a`라는 두 간선으로 바꿉니다. 변수 `1`을 참으로 만드는 조건은 단위 절 `(1 OR 1)`로 추가하며, 다른 절과 마찬가지로 `¬1 → 1` 간선을 생성합니다.

입력 리터럴은 부호가 있는 정수입니다. 변수 `n`개에 대해 그래프 정점은 `2n`개이며 인덱스 범위는 `0`부터 `2n - 1`까지입니다. 양수 리터럴 `i`는 `i - 1`, 음수 리터럴 `-i`는 `n + i - 1`로 매핑합니다. 부정은 이 두 인덱스 영역을 서로 바꿉니다. 각 함의 간선은 정방향 그래프와 역방향 그래프에 함께 저장합니다.

강한 연결 요소(SCC)는 반복문과 명시적 스택을 사용하는 코사라주 알고리즘으로 구합니다. 첫 번째 순회에서는 스택에 현재 정점뿐 아니라 다음에 방문할 간선의 위치도 저장합니다. 모든 간선을 처리한 뒤에야 정점을 종료 순서에 추가해 재귀 DFS의 동작을 정확히 재현합니다. 따라서 간선이 긴 사슬을 이루어도 호출 스택이 넘치지 않습니다. 두 번째 순회는 종료 순서의 역순으로 역방향 그래프를 방문해 SCC 번호를 붙입니다. 변수와 그 부정 리터럴이 같은 SCC에 속하는 변수가 하나라도 있으면 만족 불가능합니다.

각 케이스의 정점은 `2n`개, 함의 간선은 `(1 OR 1)`을 포함해 `2m + 2`개입니다. 시간 및 공간 복잡도는 케이스마다 `O(n + m)`입니다. 입력은 EOF까지 읽으며 각 케이스마다 소문자 `yes` 또는 `no`를 출력합니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

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
            do { c = read(); } while (c <= ' ' && c != -1);
            if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int value = 0;
            while (c > ' ') { value = value * 10 + c - '0'; c = read(); }
            return value * sign;
        }
    }

    private static int literalIndex(int literal, int n) {
        int variable = Math.abs(literal) - 1;
        return literal > 0 ? variable : variable + n;
    }

    private static int negation(int vertex, int n) {
        return vertex < n ? vertex + n : vertex - n;
    }

    @SuppressWarnings("unchecked")
    private static boolean satisfiable(int n, int m, FastScanner input) throws IOException {
        int vertices = 2 * n;
        List<Integer>[] graph = new ArrayList[vertices];
        List<Integer>[] reverse = new ArrayList[vertices];
        for (int v = 0; v < vertices; v++) {
            graph[v] = new ArrayList<>();
            reverse[v] = new ArrayList<>();
        }

        for (int i = 0; i <= m; i++) {
            int a;
            int b;
            if (i == m) {
                a = literalIndex(1, n);
                b = a;
            } else {
                a = literalIndex(input.nextInt(), n);
                b = literalIndex(input.nextInt(), n);
            }
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
            if (visited[start]) continue;
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
            if (component[start] != 0) continue;
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
            if (component[variable] == component[variable + n]) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        StringBuilder output = new StringBuilder();
        while (true) {
            int n = input.nextInt();
            if (n == Integer.MIN_VALUE) break;
            int m = input.nextInt();
            output.append(satisfiable(n, m, input) ? "yes" : "no").append('\n');
        }
        System.out.print(output);
    }
}
```
