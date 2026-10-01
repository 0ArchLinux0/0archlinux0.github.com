---
title: BOJ 4354 - 문자열 제곱
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 문자열 제곱, KMP]
pin: false
lang: ko
translation_key: boj-4354-string-power
permalink: /ko/posts/boj-4354-string-power/
source_permalink: /posts/BOJ-4354/
---

[문제: BOJ 4354 — 문자열 제곱](https://www.acmicpc.net/problem/4354)

[English](/posts/BOJ-4354/) · [한국어](/ko/posts/boj-4354-string-power/) · [日本語](/ja/posts/boj-4354-string-power/)

## 접두사 함수와 후보 주기

길이가 `L`인 각 입력 문자열 `s`에 대해 접두사 함수 `pi`를 계산합니다. `pi[i]`는 `s[0..i]`의 접미사이기도 한 가장 긴 진접두사의 길이입니다. 마지막 값 `pi[L - 1]`은 문자열 전체의 가장 긴 테두리(border)의 길이입니다. 이 테두리를 제외한 길이 `p = L - pi[L - 1]`를 후보 주기로 삼습니다.

후보가 실제 반복 블록이 되려면 `L`이 `p`로 나누어 떨어져야 합니다. 이때 문자열은 해당 블록을 정확히 `L / p`번 반복한 것이므로 답은 `L / p`입니다. 나누어 떨어지지 않으면 더 짧은 블록이 문자열 전체에 걸쳐 일정하게 반복되지 않으므로 답은 `1`입니다. 한 글자 문자열도 처리됩니다. 접두사 함수 값이 `0`이어서 `p = 1`, 답도 `1`입니다.

입력은 `.` 한 글자만 있는 줄이 나올 때까지 처리합니다. 입력이 끝나는 경우에도 null 검사를 통해 안전하게 종료하며, 문제의 유효한 입력에는 종료 문자가 포함됩니다. 종료 문자가 아닌 문자열은 비어 있지 않으므로 접두사 함수 배열과 마지막 원소가 항상 존재합니다.

접두사 함수 계산의 시간 복잡도는 문자열마다 `O(L)`, 공간 복잡도는 `O(L)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        String s;
        while ((s = input.readLine()) != null && !s.equals(".")) {
            output.append(repetitionCount(s)).append('\n');
        }
        System.out.print(output);
    }

    private static int repetitionCount(String s) {
        int length = s.length();
        int[] prefix = new int[length];

        for (int i = 1, matched = 0; i < length; i++) {
            while (matched > 0 && s.charAt(i) != s.charAt(matched)) {
                matched = prefix[matched - 1];
            }
            if (s.charAt(i) == s.charAt(matched)) {
                matched++;
                prefix[i] = matched;
            }
        }

        int period = length - prefix[length - 1];
        return length % period == 0 ? length / period : 1;
    }
}
```
