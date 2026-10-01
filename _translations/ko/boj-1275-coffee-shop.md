---
title: BOJ 1275 - 커피숍2
author: MINJUN PARK
date: 2022-02-18 08:37:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 세그먼트 트리, 커피숍2]
pin: false
lang: ko
translation_key: boj-1275-coffee-shop
permalink: /ko/posts/boj-1275-coffee-shop/
source_permalink: /posts/BOJ-1275/
---

[문제: BOJ 1275 — 커피숍2](https://www.acmicpc.net/problem/1275) · [English](/posts/BOJ-1275/) · [日本語](/ja/posts/boj-1275-coffee-shop/)

배열은 계속 바뀝니다. 각 질의는 `x`부터 `y`까지의 합을 구한 뒤, 위치 `a`의 값을 `b`로 대입합니다. 반복형 세그먼트 트리는 배열의 각 값을 리프에 저장하고, 내부 노드에는 두 자식 노드 값의 합을 저장합니다.

트리에는 크기 `2N`인 배열을 사용합니다. 0부터 시작하는 인덱스 `i`의 리프는 `N + i`에 저장하며, 각 부모 노드는 자식 노드 두 개의 합입니다. 이 구조는 `N`이 2의 거듭제곱이 아니어도 패딩 없이 사용할 수 있습니다. 구간 합을 구할 때는 먼저 양 끝점을 정렬한 다음, 포함 구간을 반열린 구간 `[left, right)`로 바꿉니다. 두 경계를 트리 위로 이동시키면서 남은 구간의 경계 노드가 나타날 때마다 합에 더합니다. 한 점의 값을 대입할 때는 해당 리프를 바꾸고 조상 노드의 합을 다시 계산합니다.

각 질의의 합을 먼저 구한 다음 갱신을 적용하며, 그 갱신은 다음 질의를 처리하기 전에 완료됩니다. 누적 합이 `int` 범위를 넘을 수 있으므로 트리, 입력 값, 합에는 `long`을 사용합니다.

트리 생성에는 `O(N)` 시간이 걸립니다. `Q`개의 각 질의는 구간 합과 점 갱신을 한 번씩 수행하며 각각 `O(log N)`이므로, 전체 시간 복잡도는 `O((N + Q) log N)`입니다. 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] tree;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokens.nextToken());
        int queryCount = Integer.parseInt(tokens.nextToken());

        tree = new long[2 * n];
        tokens = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            tree[n + i] = Long.parseLong(tokens.nextToken());
        }
        for (int node = n - 1; node > 0; node--) {
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            tokens = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokens.nextToken());
            int y = Integer.parseInt(tokens.nextToken());
            int a = Integer.parseInt(tokens.nextToken()) - 1;
            long b = Long.parseLong(tokens.nextToken());

            int left = Math.min(x, y) - 1;
            int right = Math.max(x, y);
            output.append(rangeSum(left, right)).append('\n');
            assign(a, b);
        }
        System.out.print(output);
    }

    static long rangeSum(int left, int right) {
        long sum = 0;
        for (left += n, right += n; left < right; left /= 2, right /= 2) {
            if (left % 2 == 1) sum += tree[left++];
            if (right % 2 == 1) sum += tree[--right];
        }
        return sum;
    }

    static void assign(int position, long value) {
        int node = n + position;
        tree[node] = value;
        while (node > 1) {
            node /= 2;
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }
    }
}
```
