---
title: BOJ. 개미굴 (14725)
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Ant tunnel, 개미굴, Review]
pin: false
lang: ko
translation_key: boj-14725-ant-tunnel
permalink: /ko/posts/boj-14725-ant-tunnel/
source_permalink: /posts/BOJ-14725/
---

[문제: BOJ 14725 — 개미굴](https://www.acmicpc.net/problem/14725) · [English](/posts/BOJ-14725/) · [日本語](/ja/posts/boj-14725-ant-tunnel/)

각 입력 줄은 개미굴의 루트에서 시작하는 하나의 경로입니다. 각 토큰을 트라이에 삽입하면 이미 존재하는 접두사는 합쳐집니다. 노드의 자식은 그 접두사 다음에 올 수 있는 먹이들입니다. `TreeMap`은 자식을 사전순으로 저장하므로, 깊이 우선 탐색은 자식을 먼저 정렬하거나 복사하지 않고도 형제 노드를 요구되는 순서대로 방문합니다.

탐색 중에는 루트에서 현재 노드까지의 간선 수만큼 `--`를 한 번씩 출력한 뒤, 현재 토큰과 줄바꿈을 붙입니다. 따라서 루트의 자식 앞에는 대시가 없고, 깊이가 한 단계 늘 때마다 `--`가 정확히 하나씩 추가됩니다.

전체 토큰 수를 `S`, 트라이 노드 수를 `V`라고 하면, 정렬 맵을 사용하는 삽입의 최악 시간 복잡도는 `O(S log V)`이고 순회는 `O(V)`입니다. 트라이의 공간 복잡도는 `O(V)`입니다.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        Trie root = new Trie();

        for (int i = 0; i < n; i++) {
            String[] foods = input.readLine().split(" ");
            Trie node = root;
            for (int j = 1; j < foods.length; j++) {
                node = node.children.computeIfAbsent(foods[j], key -> new Trie());
            }
        }

        root.print(0);
        System.out.print(output);
    }

    private static class Trie {
        private final TreeMap<String, Trie> children = new TreeMap<>();

        private void print(int depth) {
            for (Map.Entry<String, Trie> child : children.entrySet()) {
                for (int i = 0; i < depth; i++) {
                    output.append("--");
                }
                output.append(child.getKey()).append('\n');
                child.getValue().print(depth + 1);
            }
        }
    }
}
```
