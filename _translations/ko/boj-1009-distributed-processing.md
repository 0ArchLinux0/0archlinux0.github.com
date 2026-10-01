---
title: BOJ 1009 - 분산 처리
author: MINJUN PARK
date: 2022-02-06 00:39:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 분산 처리, 거듭제곱]
pin: false
lang: ko
translation_key: boj-1009-distributed-processing
permalink: /ko/posts/boj-1009-distributed-processing/
source_permalink: /posts/BOJ-1009/
---

[문제: BOJ 1009 — 분산 처리](https://www.acmicpc.net/problem/1009) · [English](/posts/BOJ-1009/) · [日本語](/ja/posts/boj-1009-distributed-processing/)

각 테스트 케이스에서 `a^b`의 일의 자리를 10으로 나눈 나머지에 대한 이진 거듭제곱으로 계산합니다. 반복 주기를 따로 찾을 필요가 없으며, 10으로 나누어떨어지는 밑도 처리할 수 있습니다. 계산한 나머지는 컴퓨터 번호를 나타냅니다. 단, 나머지가 0이면 컴퓨터 10번을 뜻합니다(컴퓨터 번호는 1부터 10까지입니다).

프로그램은 테스트 케이스 수를 읽은 뒤 각 `(a, b)` 쌍을 독립적으로 처리합니다. 케이스당 시간 복잡도는 `O(log b)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(input.readLine().trim());
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            String[] values = input.readLine().trim().split("\\s+");
            int a = Integer.parseInt(values[0]);
            long b = Long.parseLong(values[1]);
            int lastDigit = (int) powerModuloTen(a, b);
            output.append(lastDigit == 0 ? 10 : lastDigit).append('\n');
        }

        System.out.print(output);
    }

    static long powerModuloTen(int base, long exponent) {
        long result = 1;
        long factor = base % 10;
        while (exponent > 0) {
            if ((exponent & 1) != 0) result = (result * factor) % 10;
            factor = (factor * factor) % 10;
            exponent >>= 1;
        }
        return result;
    }
}
```
