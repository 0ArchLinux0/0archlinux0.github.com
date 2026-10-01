---
title: BOJ 1086 - 박성원
author: MINJUN PARK
date: 2022-02-08 04:32:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 동적 계획법, 비트마스크, 박성원]
pin: false
lang: ko
translation_key: boj-1086-permutation-probability
permalink: /ko/posts/boj-1086-permutation-probability/
source_permalink: /posts/BOJ-1086/
---

[문제: BOJ 1086 — 박성원](https://www.acmicpc.net/problem/1086) · [English](/posts/BOJ-1086/) · [日本語](/ja/posts/boj-1086-permutation-probability/)

## 부분집합 동적 계획법

입력에는 `N`개의 문자열이 있습니다. 순열은 문자열의 **위치**를 하나씩 고르는 순서입니다. 내용이 같은 문자열이 여러 개 있어도 서로 다른 위치이므로 각각 별개의 선택이며, 전체 순열 수는 `N!`입니다.

`dp[mask][r]`를 `mask`에 포함된 문자열을 이어 붙였을 때 나머지가 `r`인 경우의 수라고 정의합니다. 빈 문자열의 나머지는 0이므로 `dp[0][0] = 1`입니다. 각 문자열 `i`의 나머지 `value[i]`를 `K`로 나눈 나머지로 미리 계산하고, 전체 문자열 길이까지 `power[len] = 10^len mod K`도 구합니다.

현재 이어 붙인 문자열의 길이가 `len`이고 나머지가 `r`일 때 문자열 `i`를 뒤에 붙이면 새 나머지는 다음과 같습니다.

`nextRemainder = (r * power[length[i]] + value[i]) mod K`.

따라서 아직 선택하지 않은 각 인덱스 `i`에 대해 `dp[mask][r]`를 `dp[mask | (1 << i)][nextRemainder]`에 더합니다. 서로 같은 내용의 문자열도 인덱스별로 전이하므로 구별되는 위치의 모든 순열을 셉니다. 모든 위치를 선택한 상태에서 `dp[(1 << N) - 1][0]`이 `K`로 나누어떨어지는 순열의 수입니다.

확률은 이 수를 `N!`로 나눈 값입니다. 분자와 분모의 최대공약수로 약분하고, 분자가 0이면 바로 `0/1`을 출력합니다. `N <= 15`이므로 각 상태의 경우의 수와 `15!`은 모두 signed `long` 범위에 들어갑니다. 상태는 `2^N`개이고 각 상태에서 최대 `N`개의 전이를 확인하므로 시간 복잡도는 `O(N K 2^N)`, 공간 복잡도는 `O(K 2^N)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        String[] numbers = new String[n];
        int totalLength = 0;
        for (int i = 0; i < n; i++) {
            numbers[i] = input.next();
            totalLength += numbers[i].length();
        }
        int k = input.nextInt();

        int[] value = new int[n];
        int[] length = new int[n];
        for (int i = 0; i < n; i++) {
            length[i] = numbers[i].length();
            int remainder = 0;
            for (int j = 0; j < length[i]; j++) {
                remainder = (remainder * 10 + numbers[i].charAt(j) - '0') % k;
            }
            value[i] = remainder;
        }

        int[] power = new int[totalLength + 1];
        power[0] = 1 % k;
        for (int len = 1; len <= totalLength; len++) {
            power[len] = (int) ((long) power[len - 1] * 10 % k);
        }

        int states = 1 << n;
        long[][] dp = new long[states][k];
        dp[0][0] = 1;
        for (int mask = 0; mask < states; mask++) {
            for (int remainder = 0; remainder < k; remainder++) {
                long ways = dp[mask][remainder];
                if (ways == 0) {
                    continue;
                }
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) == 0) {
                        int nextRemainder = (int) (
                            ((long) remainder * power[length[i]] + value[i]) % k
                        );
                        dp[mask | (1 << i)][nextRemainder] += ways;
                    }
                }
            }
        }

        long numerator = dp[states - 1][0];
        if (numerator == 0) {
            System.out.println("0/1");
            return;
        }

        long denominator = 1;
        for (int i = 2; i <= n; i++) {
            denominator *= i;
        }
        long divisor = gcd(numerator, denominator);
        System.out.println((numerator / divisor) + "/" + (denominator / divisor));
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    private static class FastScanner {
        private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

        String next() throws IOException {
            StringBuilder token = new StringBuilder();
            int c;
            do {
                c = reader.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                token.append((char) c);
                c = reader.read();
            }
            return token.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
