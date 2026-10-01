---
title: BOJ 2213 - 트리의 독립집합
author: MINJUN PARK
date: 2022-02-02 13:02:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트리, 동적 계획법, 독립집합]
pin: false
lang: ko
translation_key: boj-2213-tree-independent-set
permalink: /ko/posts/boj-2213-tree-independent-set/
source_permalink: /posts/BOJ-2213/
---

[문제: BOJ 2213 — 트리의 독립집합](https://www.acmicpc.net/problem/2213) · [English](/posts/BOJ-2213/) · [日本語](/ja/posts/boj-2213-tree-independent-set/)

정점 1을 루트로 트리를 구성합니다. 각 정점 `v`에 대해 `in[v]`는 `v`를 포함하는 `v`의 서브트리 독립집합 중 최대 가중치이고, `out[v]`는 `v`를 제외했을 때의 최대 가중치입니다. 정점의 양수 가중치를 `w[v]`라고 하면 다음과 같습니다.

- `in[v] = w[v] + sum(out[child])`: `v`를 선택하면 인접한 모든 자식은 선택할 수 없습니다.
- `out[v] = sum(max(in[child], out[child]))`: `v`를 제외하면 각 자식은 포함하거나 제외하는 쪽 중 더 좋은 경우를 택할 수 있습니다.

트리에서는 자식 서브트리 사이에 간선이 없으므로 각 서브트리의 최적 선택을 합쳐도 독립집합 조건이 유지됩니다. 부모 배열과 루트부터 시작하는 정점 순서를 반복문으로 만든 뒤, 그 순서를 역순으로 처리하면 부모보다 자식의 DP 값이 먼저 계산됩니다. 재귀를 사용하지 않으므로 정점 100,000개가 일렬인 트리에서도 호출 스택이 넘치지 않습니다.

복원은 루트에서부터 트리를 순회합니다. 부모를 선택했다면 현재 정점은 반드시 제외합니다. 부모를 선택하지 않은 경우에는 `in[v] > out[v]`일 때만 현재 정점을 선택하고, 두 값이 같으면 제외합니다. 루트에도 같은 규칙을 적용합니다. 동률에서는 어느 쪽을 골라도 최적해이므로, 이 규칙은 출력 결과를 결정적으로 만들기 위한 것입니다. 마지막으로 선택한 정점 번호를 정렬해 출력합니다. 첫 번째 줄에는 루트의 두 DP 값 중 큰 값인 최댓값을, 두 번째 줄에는 그 값에 대응하는 독립집합 하나를 출력합니다.

각 정점과 간선을 상수 번 처리하므로 시간 복잡도와 공간 복잡도는 모두 `O(N)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        int[] weight = new int[n + 1];
        StringTokenizer weights = new StringTokenizer(input.readLine());
        for (int vertex = 1; vertex <= n; vertex++) {
            weight[vertex] = Integer.parseInt(weights.nextToken());
        }

        List<Integer>[] graph = new ArrayList[n + 1];
        for (int vertex = 1; vertex <= n; vertex++) {
            graph[vertex] = new ArrayList<>();
        }
        for (int edge = 0; edge < n - 1; edge++) {
            StringTokenizer tokens = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n + 1];
        int[] order = new int[n];
        int size = 0;
        order[size++] = 1;
        parent[1] = -1;
        for (int index = 0; index < size; index++) {
            int vertex = order[index];
            for (int next : graph[vertex]) {
                if (next == parent[vertex]) {
                    continue;
                }
                parent[next] = vertex;
                order[size++] = next;
            }
        }

        long[] in = new long[n + 1];
        long[] out = new long[n + 1];
        for (int index = n - 1; index >= 0; index--) {
            int vertex = order[index];
            in[vertex] = weight[vertex];
            for (int next : graph[vertex]) {
                if (parent[next] == vertex) {
                    in[vertex] += out[next];
                    out[vertex] += Math.max(in[next], out[next]);
                }
            }
        }

        boolean[] selected = new boolean[n + 1];
        int[] traversal = new int[n];
        int top = 0;
        traversal[top++] = 1;
        long optimum = Math.max(in[1], out[1]);
        while (top > 0) {
            int vertex = traversal[--top];
            int p = parent[vertex];
            selected[vertex] = p == -1
                    ? in[vertex] > out[vertex]
                    : !selected[p] && in[vertex] > out[vertex];
            for (int next : graph[vertex]) {
                if (parent[next] == vertex) {
                    traversal[top++] = next;
                }
            }
        }

        List<Integer> answer = new ArrayList<>();
        for (int vertex = 1; vertex <= n; vertex++) {
            if (selected[vertex]) {
                answer.add(vertex);
            }
        }
        Collections.sort(answer);

        StringBuilder output = new StringBuilder();
        output.append(optimum).append('\n');
        for (int vertex : answer) {
            output.append(vertex).append(' ');
        }
        System.out.println(output);
    }
}
```
