---
title: AtCoder ABC 238 C - digitnum
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC]
pin: false
lang: ko
translation_key: abc238-c-digitnum
permalink: /ko/posts/abc238-c-digitnum/
source_permalink: /posts/Atcoder-C-digitnum/
---

[문제: AtCoder ABC 238 C — digitnum](https://atcoder.jp/contests/abc238/tasks/abc238_c) · [English](/posts/Atcoder-C-digitnum/) · [日本語](/ja/posts/abc238-c-digitnum/)

`1`부터 `N`까지의 모든 정수에 대해 십진수 자릿수를 더하고, 그 합을 `998244353`으로 나눈 나머지를 출력합니다.

정수들을 자릿수별로 묶습니다. 자릿수가 `d`인 정수의 범위는 `[10^(d-1), min(N, 10^d - 1)]`이므로, 그 범위에 포함되는 정수의 개수에 `d`를 곱해 답에 더합니다. 범위의 끝이 `N`에 도달하면 반복을 끝냅니다.

자릿수 범위는 `O(log N)`개이므로 시간 복잡도는 `O(log N)`, 추가 공간 복잡도는 `O(1)`입니다. 범위의 양 끝은 `long`으로 계산합니다. `10^d` 계산 중 오버플로가 발생하지 않도록, 다음 10의 거듭제곱이 `N` 이하인지 먼저 확인한 뒤 10을 곱합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final long MOD = 998244353L;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(input.readLine().trim());

        long answer = 0;
        long start = 1;
        for (long digits = 1; start <= n; digits++) {
            long end = n;
            if (start <= n / 10) {
                end = start * 10 - 1;
            }
            long count = end - start + 1;
            answer = (answer + (digits % MOD) * (count % MOD)) % MOD;
            if (end == n) {
                break;
            }
            start *= 10;
        }

        System.out.println(answer);
    }
}
```
