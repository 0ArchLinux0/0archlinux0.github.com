---
title: BOJ. 합성함수와 쿼리 (17435)
author: MINJUN PARK
date: 2022-02-08 17:13:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Sparse table, Composite Function And Query, 합성함수와 쿼리]
pin: false
lang: ko
translation_key: boj-17435-function-composition
permalink: /ko/posts/boj-17435-function-composition/
source_permalink: /posts/BOJ-17435/
---

[문제: BOJ 17435 — 합성함수와 쿼리](https://www.acmicpc.net/problem/17435)

[English](/posts/BOJ-17435/) · [日本語](/ja/posts/boj-17435-function-composition/)

## 풀이

입력은 정수 `1..M`에서 정의된 함수 `f`를 주며, 각 쿼리는 시작값 `x`에 함수를 `K`번 적용한 값, 즉 `f^K(x)`를 묻습니다. 함수를 한 번씩 적용하면 쿼리마다 최대 500,000번의 연산이 필요할 수 있으므로, 이진 리프팅으로 함수의 거듭제곱을 미리 계산합니다.

`up[b][x]`를 `x`에 `f`를 정확히 `2^b`번 적용한 결과라고 정의합니다. 기본 행은 `up[0][x] = f(x)`입니다. 길이가 `2^(b-1)`인 점프를 연속해서 두 번 하면 길이 `2^b`인 점프가 되므로 다음 점화식을 얻습니다.

`up[b][x] = up[b - 1][up[b - 1][x]]`

쿼리에서는 `K`의 각 비트를 확인합니다. 비트 `b`가 켜져 있으면 현재 값을 `up[b][현재 값]`으로 갱신합니다. 선택한 점프 길이의 합이 `K`이므로 정확히 `f^K(x)`를 계산합니다. `K = 0`이면 켜진 비트가 없어 답은 그대로 `x`이고, `K = 1`이면 기본 행만 사용합니다. 표는 20개 행이며, `K <= 500000`에서 가장 높은 비트는 18번 비트이므로 모든 쿼리를 처리할 때 인덱스 범위를 넘지 않습니다.

전처리 시간과 메모리는 `Kmax = 500000`일 때 `O(M log Kmax)`입니다. 각 쿼리는 `O(log Kmax)` 시간에 처리합니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int LEVELS = 20;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int m = input.nextInt();
        int[][] up = new int[LEVELS][m + 1];

        for (int x = 1; x <= m; x++) {
            up[0][x] = input.nextInt();
        }

        for (int bit = 1; bit < LEVELS; bit++) {
            for (int x = 1; x <= m; x++) {
                up[bit][x] = up[bit - 1][up[bit - 1][x]];
            }
        }

        int queryCount = input.nextInt();
        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            int k = input.nextInt();
            int value = input.nextInt();
            for (int bit = 0; bit < LEVELS; bit++) {
                if ((k & (1 << bit)) != 0) {
                    value = up[bit][value];
                }
            }
            answer.append(value).append('\n');
        }

        System.out.print(answer);
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
