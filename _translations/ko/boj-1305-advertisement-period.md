---
title: BOJ 1305 - 광고
author: MINJUN PARK
date: 2022-02-03 07:33:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 문자열, KMP, 광고]
pin: false
lang: ko
translation_key: boj-1305-advertisement-period
permalink: /ko/posts/boj-1305-advertisement-period/
source_permalink: /posts/BOJ-1305/
---

[문제: BOJ 1305 — 광고](https://www.acmicpc.net/problem/1305) · [English](/posts/BOJ-1305/) · [日本語](/ja/posts/boj-1305-advertisement-period/)

길이 `L`인 광고 문구가 주어집니다. 이 문구 전체가 앞부분으로 나타나도록 무한히 반복할 수 있는 가장 짧은 문자열의 길이를 구합니다. KMP 접두사 함수 `pi`를 계산합니다. `pi[i]`는 `s[0..i]`의 proper prefix(문자열 전체가 아닌 접두사)이면서 suffix인 문자열 중 가장 긴 것의 길이입니다. 따라서 `pi[L - 1]`은 전체 문자열의 가장 긴 경계(border) 길이입니다.

가장 긴 경계는 광고 문자열을 다음 복사본과 이어 붙일 때 겹칠 수 있는 최대 길이입니다. 그러므로 가장 짧은 광고 길이는 `L - pi[L - 1]`입니다. 이 길이가 `L`의 약수가 아니어도 정답이 될 수 있습니다. 예를 들어 `ababa`의 가장 긴 경계 길이는 3이므로 `ab`를 반복하면 그 시작 부분에 `ababa`가 나타나고 답은 2입니다. 비어 있지 않은 경계가 없으면 답은 `L`이며, 문자열이 짧은 문자열의 반복으로 이루어졌다면 식은 그 기본 길이를 반환합니다.

입력 조건은 `1 <= L <= 1,000,000`이며 문자열 길이는 정확히 `L`입니다. 코드는 접두사 배열을 참조하기 전에 이 길이 조건을 확인합니다. 접두사 함수 계산과 답 계산은 `O(L)` 시간, 접두사 배열은 `O(L)` 공간을 사용합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(input.readLine());
        String advertisement = input.readLine();

        if (length <= 0 || advertisement == null || advertisement.length() != length) {
            throw new IllegalArgumentException("Invalid advertisement length");
        }

        int[] pi = prefixFunction(advertisement);
        System.out.println(length - pi[length - 1]);
    }

    private static int[] prefixFunction(String s) {
        int[] pi = new int[s.length()];
        int matched = 0;
        for (int i = 1; i < s.length(); i++) {
            while (matched > 0 && s.charAt(i) != s.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (s.charAt(i) == s.charAt(matched)) {
                pi[i] = ++matched;
            }
        }
        return pi;
    }
}
```
