---
title: BOJ. Friend Network (4195)
author: MINJUN PARK
date: 2022-01-05 16:11:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Union Find,
    Friend Network,
    친구 네트워크,
		Review
  ]
pin: false
lang: ko
translation_key: boj-4195-friend-network
permalink: /ko/posts/boj-4195-friend-network/
source_permalink: /posts/BOJ-4195/
---

## 풀이

각 사람의 이름을 부모 이름에 대응시키고, 각 루트에는 컴포넌트 크기를 저장합니다. 처음 등장한 이름은 자기 자신을 루트로 하며 크기는 1입니다. 친구 관계를 입력받으면 두 사람의 루트를 찾아 작은 컴포넌트를 큰 컴포넌트 아래에 연결하고, 살아남은 루트의 크기를 합칩니다. 이미 루트가 같으면 구조를 변경하지 않고 기존 크기를 출력합니다. 테스트 케이스마다 새로운 서로소 집합을 만들어 이전 케이스의 상태가 남지 않게 합니다.

불변 조건은 같은 친구 관계 컴포넌트에 속한 모든 이름이 같은 루트에 도달하고, 그 루트에 저장된 크기가 실제 사람 수와 일치한다는 것입니다. 경로 압축은 루트를 유지하면서 이후 탐색 경로를 짧게 하고, 크기 기준 합치기는 트리가 지나치게 깊어지지 않게 합니다. 두 연산을 함께 사용하면 친구 관계 한 건마다 분리 집합 연산은 상환 `O(α(V))`이며, `V`는 해당 케이스의 서로 다른 이름 수입니다. 해시 맵 접근이 평균 `O(1)`이므로 `F`건의 친구 관계 전체는 평균 `O(F α(V))` 시간, 맵은 `O(V)` 공간을 사용합니다. `find`는 반복문으로 구현해 부모 경로가 깊더라도 재귀 호출 스택을 사용하지 않습니다.

[문제 링크](https://www.acmicpc.net/problem/4195)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int friendshipCount = input.nextInt();
            DisjointSet friends = new DisjointSet();

            for (int i = 0; i < friendshipCount; i++) {
                String first = input.next();
                String second = input.next();
                output.append(friends.union(first, second)).append('\n');
            }
        }

        System.out.print(output);
    }

    private static class DisjointSet {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> size = new HashMap<>();

        private void add(String name) {
            if (!parent.containsKey(name)) {
                parent.put(name, name);
                size.put(name, 1);
            }
        }

        private String find(String name) {
            String root = name;
            while (!parent.get(root).equals(root)) {
                root = parent.get(root);
            }

            while (!name.equals(root)) {
                String next = parent.get(name);
                parent.put(name, root);
                name = next;
            }
            return root;
        }

        int union(String first, String second) {
            add(first);
            add(second);

            String firstRoot = find(first);
            String secondRoot = find(second);
            if (firstRoot.equals(secondRoot)) {
                return size.get(firstRoot);
            }

            if (size.get(firstRoot) < size.get(secondRoot)) {
                String temporary = firstRoot;
                firstRoot = secondRoot;
                secondRoot = temporary;
            }

            parent.put(secondRoot, firstRoot);
            int combinedSize = size.get(firstRoot) + size.get(secondRoot);
            size.put(firstRoot, combinedSize);
            size.remove(secondRoot);
            return combinedSize;
        }
    }

    private static class FastScanner {
        private final BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
