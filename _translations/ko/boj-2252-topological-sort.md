---
title: BOJ. 줄 세우기 (2252)
author: MINJUN PARK
date: 2022-01-26 22:09:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Topological Sort,
    Graph,
    BOJ,
    Line up,
    줄 세우기,
    Review
  ]
pin: false
lang: ko
translation_key: boj-2252-topological-sort
permalink: /ko/posts/boj-2252-topological-sort/
source_permalink: /posts/BOJ-2252/
---

[문제: BOJ 2252 — 줄 세우기](https://www.acmicpc.net/problem/2252)

입력된 각 쌍 `A B`는 학생 `A`가 학생 `B`보다 앞에 서야 한다는 뜻입니다. 이를 방향 간선 `A -> B`로 나타냅니다. 유효한 줄은 위상 정렬 순서이며, 모든 간선의 시작점이 도착점보다 앞에 있어야 합니다. 서로 순서 관계가 없는 학생은 어느 쪽이 먼저 와도 되므로, 답은 유일한 순서가 아니라 가능한 순서 하나입니다.

칸의 알고리즘은 진입 차수가 0인 정점(아직 남은 선행 조건이 없는 정점)을 반복해서 선택해 출력하고, 그 정점에서 나가는 간선을 제거합니다. 간선을 제거할 때 도착점의 진입 차수를 감소시키며, 그 값이 0이 되면 도착점을 출력할 준비가 된 것입니다. 준비된 정점은 큐에 여러 개 있을 수 있으며, 어느 정점을 선택하더라도 필요한 부분 순서가 유지됩니다.

모든 입력 간선을 인접 리스트에 저장하고 각 간선마다 도착점의 진입 차수를 증가시킵니다. 따라서 제약 조건이 중복되어도 두 자료 구조의 값이 일치합니다. DAG에서는 모든 정점이 정확히 한 번 출력됩니다. 학생 수를 `N`, 제약 수를 `M`이라 하면 시간 복잡도는 `O(N + M)`, 공간 복잡도도 `O(N + M)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int studentCount = input.nextInt();
        int constraintCount = input.nextInt();

        List<List<Integer>> next = new ArrayList<>(studentCount);
        for (int student = 0; student < studentCount; student++) {
            next.add(new ArrayList<>());
        }

        int[] indegree = new int[studentCount];
        for (int i = 0; i < constraintCount; i++) {
            int before = input.nextInt() - 1;
            int after = input.nextInt() - 1;
            next.get(before).add(after);
            indegree[after]++;
        }

        Queue<Integer> ready = new ArrayDeque<>();
        for (int student = 0; student < studentCount; student++) {
            if (indegree[student] == 0) {
                ready.offer(student);
            }
        }

        StringBuilder output = new StringBuilder();
        boolean first = true;
        while (!ready.isEmpty()) {
            int student = ready.poll();
            if (!first) {
                output.append(' ');
            }
            output.append(student + 1);
            first = false;

            for (int after : next.get(student)) {
                indegree[after]--;
                if (indegree[after] == 0) {
                    ready.offer(after);
                }
            }
        }

        System.out.println(output);
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

        private int nextInt() throws IOException {
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
