---
title: BOJ 1094 - 막대기
author: MINJUN PARK
date: 2022-01-27 01:49:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 막대기, 비트]
pin: false
lang: ko
translation_key: boj-1094-stick
permalink: /ko/posts/boj-1094-stick/
source_permalink: /posts/BOJ-1094/
---

[문제: BOJ 1094 — 막대기](https://www.acmicpc.net/problem/1094)
[English](/posts/BOJ-1094/) · [日本語](/ja/posts/boj-1094-stick/)

목표 길이는 `1`부터 `64`까지입니다. 처음 막대의 길이는 `64`이며, 막대를 절반씩 자르면 만들 수 있는 조각의 길이는 모두 `64, 32, 16, 8, 4, 2, 1`과 같은 2의 거듭제곱입니다.

목표 길이를 이진수로 나타내면, 켜진 비트에 해당하는 서로 다른 2의 거듭제곱의 합으로 표현됩니다. 선택된 각 조각 크기는 정확히 한 개씩 필요합니다. 예를 들어 `23 = 16 + 4 + 2 + 1`이므로 조각은 네 개 필요합니다. 따라서 답은 목표값의 켜진 비트 수입니다. Java의 `Integer.bitCount`를 사용하면 자르는 과정을 시뮬레이션하지 않고 바로 계산할 수 있습니다.

시간 복잡도는 `O(1)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(input.readLine());

        System.out.println(Integer.bitCount(target));
    }
}
```
