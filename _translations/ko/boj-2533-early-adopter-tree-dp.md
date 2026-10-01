---
title: BOJ. 사회망 서비스(SNS) (2533)
author: MINJUN PARK
date: 2022-02-01 13:16:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Graph, Dynamic Programming, 사회망 서비스(SNS)]
pin: false
lang: ko
translation_key: boj-2533-early-adopter-tree-dp
permalink: /ko/posts/boj-2533-early-adopter-tree-dp/
source_permalink: /posts/BOJ-2533/
---

[문제: BOJ 2533 — 사회망 서비스(SNS)](https://www.acmicpc.net/problem/2533)

[English](/posts/BOJ-2533/) · [日本語](/ja/posts/boj-2533-early-adopter-tree-dp/)

## 풀이

정점 `0`을 루트로 정하고 반복문으로 루트 우선 순서를 만듭니다. 이 순서를 역순으로 처리하면 모든 자식을 먼저 계산하므로 재귀 호출이 필요 없습니다. 따라서 정점이 최대 `10^6`개인 일자형 트리에서도 호출 스택이 넘치지 않습니다.

각 정점 `u`에 대해 `notAdopter[u]`는 `u`가 얼리 어답터가 아닐 때 서브트리에 필요한 얼리 어답터의 최소 수입니다. 이 경우 모든 자식은 얼리 어답터여야 하므로 자식들의 `adopter` 상태를 더합니다. `adopter[u]`는 `u`가 얼리 어답터일 때의 최소 수이며, 각 자식은 두 상태 중 더 작은 값을 선택할 수 있고 여기에 `u` 자신을 포함합니다. 따라서 리프의 상태는 `(0, 1)`입니다. 정점이 하나뿐인 트리도 같은 방식으로 처리되어 답은 `min(0, 1) = 0`입니다.

인접 리스트, 순회 순서, 두 DP 상태는 기본형 배열로 저장합니다. 각 정점과 간선을 상수 횟수만큼 처리하므로 시간 복잡도는 `O(N)`, 공간 복잡도는 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] head = new int[n];
        int[] to = new int[2 * (n - 1)];
        int[] next = new int[2 * (n - 1)];
        java.util.Arrays.fill(head, -1);

        int edgeCount = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            to[edgeCount] = b;
            next[edgeCount] = head[a];
            head[a] = edgeCount++;
            to[edgeCount] = a;
            next[edgeCount] = head[b];
            head[b] = edgeCount++;
        }

        int[] parent = new int[n];
        int[] order = new int[n];
        int size = 1;
        order[0] = 0;
        parent[0] = -1;

        // Build a root-first order without recursion.
        for (int i = 0; i < size; i++) {
            int node = order[i];
            for (int edge = head[node]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (child == parent[node]) {
                    continue;
                }
                parent[child] = node;
                order[size++] = child;
            }
        }

        int[] notAdopter = new int[n];
        int[] adopter = new int[n];
        for (int i = size - 1; i >= 0; i--) {
            int node = order[i];
            adopter[node] = 1;
            for (int edge = head[node]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (child == parent[node]) {
                    continue;
                }
                notAdopter[node] += adopter[child];
                adopter[node] += Math.min(notAdopter[child], adopter[child]);
            }
        }

        System.out.println(Math.min(notAdopter[0], adopter[0]));
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

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
    }
}
```
