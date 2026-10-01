---
title: BOJ 1766 - 문제집
author: MINJUN PARK
date: 2022-02-06 18:10:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 위상 정렬, 문제집]
pin: false
lang: ko
translation_key: boj-1766-workbook
permalink: /ko/posts/boj-1766-workbook/
source_permalink: /posts/BOJ-1766/
---

[문제: BOJ 1766 — 문제집](https://www.acmicpc.net/problem/1766) · [English](/posts/BOJ-1766/) · [日本語](/ja/posts/boj-1766-workbook/)

방향 간선 `A -> B`는 문제 `A`를 문제 `B`보다 먼저 풀어야 한다는 뜻입니다. 따라서 모든 정점을 정확히 한 번씩 포함하고, 모든 간선의 시작 정점이 도착 정점보다 앞서는 위상 정렬 순서를 만들어야 합니다. 여러 문제를 다음에 풀 수 있다면 번호가 가장 작은 문제를 먼저 선택합니다.

Kahn 알고리즘은 아직 풀지 않은 선행 문제의 수인 진입 차수를 관리합니다. 진입 차수가 0인 문제를 모두 최소 힙에 넣고, 힙에서 가장 작은 문제를 꺼내 답에 추가한 뒤 그 문제에서 나가는 각 간선의 도착 정점 진입 차수를 하나씩 줄입니다. 진입 차수가 0이 된 정점은 이제 풀 수 있으므로 힙에 넣습니다. 이 과정을 반복하면 힙이 매 순간 합법적으로 다음에 풀 수 있는 문제 중 번호가 가장 작은 것을 고릅니다. FIFO 큐는 이 선택을 보장하지 않습니다. 예를 들어 `1 -> 4`, `2 -> 3`인 경우 1을 푼 뒤에는 2와 4가 모두 가능하므로 최소 힙은 2를 먼저 선택합니다.

입력 정수는 어떤 공백 문자로든 구분될 수 있으므로, 줄 단위 분할 대신 바이트를 읽고 공백을 건너뛰는 스캐너를 사용합니다. 중복 간선도 인접 리스트와 진입 차수에 각각 그대로 반영합니다. 따라서 같은 선행 조건 간선이 여러 번 주어져도 모든 간선을 처리한 뒤에야 진입 차수가 0이 됩니다. 다른 문제와 연결되지 않은 정점은 처음부터 힙에 들어가며, `N = 1`도 별도 처리 없이 동작합니다.

그래프 구성에는 `O(N + M)`이 걸립니다. 각 정점은 힙에 한 번 들어갔다가 한 번 나오고 각 간선도 한 번 처리하므로 시간 복잡도는 `O((N + M) log N)`, 공간 복잡도는 `O(N + M)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position = 0;
        private int length = 0;

        private int read() throws IOException {
            if (position == length) {
                length = in.read(buffer);
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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n];

        for (int i = 0; i < m; i++) {
            int from = input.nextInt() - 1;
            int to = input.nextInt() - 1;
            graph[from].add(to);
            indegree[to]++;
        }

        PriorityQueue<Integer> available = new PriorityQueue<>();
        for (int problem = 0; problem < n; problem++) {
            if (indegree[problem] == 0) available.add(problem);
        }

        StringBuilder answer = new StringBuilder();
        while (!available.isEmpty()) {
            int current = available.remove();
            answer.append(current + 1).append(' ');
            for (int next : graph[current]) {
                if (--indegree[next] == 0) available.add(next);
            }
        }

        System.out.println(answer);
    }
}
```
