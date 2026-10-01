---
title: AtCoder ABC 235 B — 타카하시의 등산
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 235]
pin: false
lang: ko
translation_key: abc235-b-climbing-takahashi
permalink: /ko/posts/abc235-b-climbing-takahashi/
source_permalink: /posts/Atcoder-B-Climbing-Takahashi/
---

[문제: AtCoder ABC 235 B — Climbing Takahashi](https://atcoder.jp/contests/abc235/tasks/abc235_b)
[English](/posts/Atcoder-B-Climbing-Takahashi/) · [日本語](/ja/posts/abc235-b-climbing-takahashi/)

각 지점의 높이는 경로를 따라 주어집니다. 타카하시는 첫 번째 지점에서 출발해 다음 지점의 높이가 현재 지점보다 **엄격히 높을 때만** 계속 이동합니다. 높이가 같거나 낮은 지점을 처음 만나면 그 지점에는 도착하지 않고 멈춥니다. 따라서 답은 마지막으로 도착한 지점의 높이입니다.

답을 첫 번째 높이로 초기화한 뒤, 두 번째 높이부터 순서대로 확인합니다. 높이가 계속 증가하는 동안 답을 갱신하고, 처음으로 증가하지 않는 높이를 만나면 탐색을 멈춥니다. 모든 높이가 계속 증가한다면 마지막 높이가 답이고, 두 번째 높이부터 같거나 낮다면 첫 번째 높이가 답이 됩니다.

시간 복잡도는 `O(N)`, 높이 배열을 저장하는 추가 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        int[] heights = new int[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(tokens.nextToken());
        }

        int answer = heights[0];
        for (int i = 1; i < n; i++) {
            if (heights[i] <= heights[i - 1]) {
                break;
            }
            answer = heights[i];
        }

        System.out.println(answer);
    }
}
```
