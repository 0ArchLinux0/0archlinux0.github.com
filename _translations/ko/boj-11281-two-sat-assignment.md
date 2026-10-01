---
title: BOJ 11281 - 2-SAT - 4
author: MINJUN PARK
date: 2022-02-14 17:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 강한 연결 요소, 그래프, 2-SAT]
pin: false
lang: ko
translation_key: boj-11281-two-sat-assignment
permalink: /ko/posts/boj-11281-two-sat-assignment/
source_permalink: /posts/BOJ-11281/
---

[문제: BOJ 11281 — 2-SAT - 4](https://www.acmicpc.net/problem/11281) · [English](/posts/BOJ-11281/) · [日本語](/ja/posts/boj-11281-two-sat-assignment/)

## 반복형 코사라주 알고리즘을 이용한 2-SAT

입력 절 `(a ∨ b)`는 함의 `¬a → b`와 `¬b → a`로 바꿀 수 있습니다. 부호가 있는 각 리터럴을 정점으로 표현합니다. 양수 리터럴 `x`의 인덱스는 `x - 1`, 음수 리터럴 `¬x`의 인덱스는 `N + x - 1`입니다. 리터럴의 부정은 인덱스 `index ^ N`으로 구할 수 있습니다. 각 함의 간선을 그래프와 역방향 그래프에 모두 저장합니다.

변수와 그 부정이 같은 강한 연결 요소에 속하면 식은 만족 불가능합니다. 이 경우 한쪽 리터럴에서 다른 쪽으로, 다시 되돌아가는 경로가 있어 두 리터럴이 서로를 강제하고 동시에 참이어야 하기 때문입니다. 그렇지 않으면 코사라주 알고리즘으로 강한 연결 요소를 구합니다. 첫 번째 탐색은 원래 그래프에서 정점의 종료 순서를 기록하고, 두 번째 탐색은 종료 순서의 역순으로 역방향 그래프를 탐색합니다. 두 탐색 모두 명시적인 스택을 사용하므로 긴 함의 경로가 있어도 Java 호출 스택이 넘치지 않습니다.

두 번째 탐색에서 요소를 발견하는 순서대로 번호를 붙이면, 원래 함의 그래프에서 서로 다른 요소 사이의 모든 간선은 작은 번호에서 큰 번호로 향합니다. 따라서 `x`의 요소 번호가 `¬x`의 번호보다 클 때 `x`를 참으로 정합니다. 이는 이 구현의 번호 부여 방식에 맞춘 역위상 정렬 순서의 값 할당 규칙입니다. 탐색 순서나 번호 규칙을 바꾸면 비교 방향도 바뀌어야 합니다.

첫 번째 탐색에서는 정점마다 다음에 확인할 간선의 인덱스를 저장해 재귀 DFS를 반복문으로 모사합니다. 정점의 모든 나가는 간선을 처리한 뒤에만 종료 순서에 추가합니다. 두 번째 탐색은 스택에 넣을 때 방문 표시를 하여 중복 삽입을 막습니다. 각 정점과 함의 간선을 상수 번만 처리하므로 시간과 공간 복잡도는 모두 `O(N + M)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }

    private static int literalIndex(int literal, int n) {
        return literal > 0 ? literal - 1 : n - literal - 1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();
        int vertexCount = 2 * n;

        List<Integer>[] graph = new ArrayList[vertexCount];
        List<Integer>[] reverseGraph = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            graph[vertex] = new ArrayList<>();
            reverseGraph[vertex] = new ArrayList<>();
        }

        for (int clause = 0; clause < m; clause++) {
            int a = input.nextInt();
            int b = input.nextInt();
            int notA = literalIndex(-a, n);
            int indexA = literalIndex(a, n);
            int notB = literalIndex(-b, n);
            int indexB = literalIndex(b, n);

            graph[notA].add(indexB);
            reverseGraph[indexB].add(notA);
            graph[notB].add(indexA);
            reverseGraph[indexA].add(notB);
        }

        boolean[] visited = new boolean[vertexCount];
        int[] nextEdge = new int[vertexCount];
        int[] stack = new int[vertexCount];
        int[] order = new int[vertexCount];
        int orderSize = 0;

        for (int start = 0; start < vertexCount; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stack[top++] = start;
            visited[start] = true;

            while (top > 0) {
                int vertex = stack[top - 1];
                if (nextEdge[vertex] < graph[vertex].size()) {
                    int next = graph[vertex].get(nextEdge[vertex]++);
                    if (!visited[next]) {
                        visited[next] = true;
                        stack[top++] = next;
                    }
                } else {
                    order[orderSize++] = vertex;
                    top--;
                }
            }
        }

        int[] component = new int[vertexCount];
        int componentCount = 0;
        for (int index = orderSize - 1; index >= 0; index--) {
            int start = order[index];
            if (component[start] != 0) {
                continue;
            }
            int top = 0;
            stack[top++] = start;
            component[start] = ++componentCount;

            while (top > 0) {
                int vertex = stack[--top];
                for (int next : reverseGraph[vertex]) {
                    if (component[next] == 0) {
                        component[next] = componentCount;
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

        StringBuilder output = new StringBuilder("1\n");
        for (int variable = 0; variable < n; variable++) {
            output.append(component[variable] > component[variable + n] ? '1' : '0');
            if (variable + 1 < n) {
                output.append(' ');
            }
        }
        output.append('\n');
        System.out.print(output);
    }
}
```
