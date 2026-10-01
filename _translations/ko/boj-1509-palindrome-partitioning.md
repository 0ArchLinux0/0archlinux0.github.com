---
title: BOJ 1509 - 팰린드롬 분할
author: MINJUN PARK
date: 2022-02-10 01:42:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 동적 계획법, 문자열, 팰린드롬]
pin: false
lang: ko
translation_key: boj-1509-palindrome-partitioning
permalink: /ko/posts/boj-1509-palindrome-partitioning/
source_permalink: /posts/BOJ-1509/
---

[문제: BOJ 1509 — 팰린드롬 분할](https://www.acmicpc.net/problem/1509) · [English](/posts/BOJ-1509/) · [日本語](/ja/posts/boj-1509-palindrome-partitioning/)

길이 `N`인 문자열 `s`를 연속된 팰린드롬 조각으로 나눌 때, 조각 수의 최솟값을 구합니다. 먼저 `palindrome[l][r]`를 계산합니다. 이는 양 끝 인덱스를 포함하는 부분 문자열 `s[l..r]`가 팰린드롬인지 나타냅니다. 부분 문자열 길이가 짧은 것부터 계산하면, 양 끝 문자가 같아야 하고 길이가 2 이하이거나 안쪽 부분 문자열도 이미 팰린드롬이어야 합니다. 따라서 `O(N²)`개의 부분 문자열을 각각 한 번씩 처리합니다.

접두사 DP를 사용합니다. `dp[r]`를 반열린 구간 `s[0..r)`을 덮는 팰린드롬 조각의 최소 개수라고 정의하고 `dp[0] = 0`으로 둡니다. 끝 경계 `r`을 1부터 `N`까지 순회하면서 이전 경계 `l`을 모두 살펴봅니다. `s[l..r)`가 팰린드롬이면 앞쪽 접두사의 최적 분할 뒤에 이 조각을 붙일 수 있으므로 `dp[r] = min(dp[r], dp[l] + 1)`로 갱신합니다. `l = 0`은 첫 글자부터 시작하는 조각을 처리하며, 문자열 전체가 팰린드롬이면 `dp[N] = 1`이 됩니다. 모든 분할에는 마지막 팰린드롬 조각이 있으므로 가능한 `l`을 전부 확인하면 최솟값을 얻습니다. 같은 문자가 반복되거나 서로 다른 팰린드롬 조각이 섞여도 별도 처리는 필요하지 않습니다.

팰린드롬 표 계산은 `O(N²)` 시간, 접두사 DP도 가능한 구간을 모두 확인하므로 `O(N²)` 시간이 걸립니다. 공간 복잡도는 팰린드롬 표에 `O(N²)`, `dp`에 `O(N)`입니다. 반복문만 사용하므로 재귀 깊이에 의존하지 않습니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();
        int n = s.length();

        boolean[][] palindrome = new boolean[n][n];
        for (int length = 1; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;
                palindrome[left][right] = s.charAt(left) == s.charAt(right)
                        && (length <= 2 || palindrome[left + 1][right - 1]);
            }
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);
        dp[0] = 0;
        for (int right = 1; right <= n; right++) {
            for (int left = 0; left < right; left++) {
                if (palindrome[left][right - 1]) {
                    dp[right] = Math.min(dp[right], dp[left] + 1);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
```
