---
title: BOJ. 집합의 표현 (1717)
author: MINJUN PARK
date: 2021-12-29 21:08:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Union Find, Expression of Set, 집합의 표현]
pin: false
lang: ko
translation_key: boj-1717-disjoint-set
permalink: /ko/posts/boj-1717-disjoint-set/
---

서로소 집합 합집합(DSU) 자료구조는 `0`부터 `n`까지의 정수 집합을 여러 부분집합으로 관리합니다. 각 집합은 루트로 표현하며, `find(x)`는 원소 `x`가 속한 집합의 대표 원소를 반환합니다. 합집합 연산은 두 집합을 하나로 합치고, 연결 여부 질의는 두 원소의 대표 원소가 같은지 확인합니다.

`parent` 배열은 포리스트를 이룹니다. 자기 자신을 부모로 가리키는 원소가 루트이며, 같은 집합의 모든 원소는 부모 링크를 따라 대표 원소에 도달합니다. `find`는 반복형 경로 절반 줄이기를 사용해 방문한 노드의 부모를 조부모로 바꿉니다. `union`은 작은 트리를 큰 트리 아래에 붙입니다. 경로 압축과 크기 기준 합치기를 함께 사용하면 연산당 분할 상환 시간은 역 아커만 함수 `α(N)`에 대한 `O(α(N))`이고, 공간 복잡도는 `O(N)`입니다.

연산 유형 `0`은 `a`와 `b`가 속한 집합을 합치고, 그 외의 유형은 두 원소가 연결되어 있는지 질의합니다. 입력은 공백으로 구분된 정수로 읽으므로 줄바꿈이나 공백 배치에 영향을 받지 않습니다.

[문제 링크](https://www.acmicpc.net/problem/1717)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();

        DisjointSet sets = new DisjointSet(n + 1);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int type = input.nextInt();
            int a = input.nextInt();
            int b = input.nextInt();
            if (type == 0) {
                sets.union(a, b);
            } else {
                output.append(sets.find(a) == sets.find(b) ? "YES" : "NO").append('\n');
            }
        }
        System.out.print(output);
    }

    private static final class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int count) {
            parent = new int[count];
            size = new int[count];
            for (int i = 0; i < count; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int element) {
            while (element != parent[element]) {
                parent[element] = parent[parent[element]];
                element = parent[element];
            }
            return element;
        }

        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return;
            }
            if (size[rootA] < size[rootB]) {
                int temporary = rootA;
                rootA = rootB;
                rootB = temporary;
            }
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        }
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
