---
title: Codeforces 1637B - MEX and Array
author: MINJUN PARK
date: 2022-02-13 11:30:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, Codeforces, Codeforces Global Round, MEX and Array, 수학]
pin: false
lang: ko
translation_key: cf-1637b-mex-and-array
permalink: /ko/posts/cf-1637b-mex-and-array/
source_permalink: /posts/Codeforces-Global-Round-19-B.-MEX-and-Array/
---

[문제: Codeforces 1637B — MEX and Array](https://codeforces.com/contest/1637/problem/B) · [English](/posts/Codeforces-Global-Round-19-B.-MEX-and-Array/) · [日本語](/ja/posts/cf-1637b-mex-and-array/)

배열을 여러 개의 연속된 비어 있지 않은 구간으로 분할합니다. 분할의 비용은 구간 개수와 각 구간의 MEX 합이며, 배열의 값은 가능한 분할 중 최대 비용입니다. 주어진 배열의 모든 비어 있지 않은 부분 배열에 대해 그 값을 합산합니다.

공식 제약은 `1 <= t <= 30`, `1 <= n <= 100`, `0 <= a[i] <= 10^9`이며, 모든 테스트 케이스의 `n` 합은 최대 `100`입니다. 따라서 원소는 0일 수 있고 `n`보다 훨씬 클 수도 있습니다.

## 부분 배열 하나의 값

길이가 `m`이고 0을 `z`개 포함하는 부분 배열의 값은 정확히 `m + z`입니다.

먼저 상한을 보겠습니다. 분할의 한 구간의 길이를 `L`, 0의 개수를 `q`, MEX를 `x`라고 합시다. MEX가 `x`라면 `0`부터 `x - 1`까지의 모든 정수가 구간에 있어야 합니다. 따라서 원소가 적어도 `x`개이고, `x > 0`이면 0도 적어도 하나 들어 있습니다. 그러므로 어느 경우든 `1 + x <= L + q`입니다. 이 부등식을 모든 구간에 대해 더하면 비용은 전체 길이와 전체 0의 개수의 합인 `m + z`를 넘지 않습니다.

이 상한은 각 원소를 길이 1인 구간으로 분할하면 달성됩니다. 0이 아닌 원소의 MEX는 0이어서 비용에 1을 기여하고, 0의 MEX는 1이어서 비용에 2를 기여합니다. 따라서 총 비용은 `m + z`이며 주장을 증명합니다.

## 모든 부분 배열의 합

길이가 `k`인 부분 배열은 `(n - k + 1)`개이므로 모든 부분 배열 길이의 합은

`sum(k * (n - k + 1), k = 1..n) = n * (n + 1) * (n + 2) / 6`

입니다.

인덱스가 0부터 시작할 때 위치 `i`의 0은 `(i + 1) * (n - i)`개의 부분 배열에 포함됩니다. 왼쪽 끝은 `i`까지의 `i + 1`개 위치에서 고르고, 오른쪽 끝은 `i`부터 마지막까지의 `n - i`개 위치에서 고를 수 있기 때문입니다. 따라서 답은 다음과 같습니다.

`n * (n + 1) * (n + 2) / 6 + a[i] == 0인 모든 i에 대해 (i + 1) * (n - i)의 합`

즉, 기존 구현의 공식은 공식 문제의 의미에 맞습니다. 답은 `long`으로 계산하며 `n <= 100`에서 충분한 범위입니다. 테스트 케이스마다 시간 복잡도는 `O(n)`, 보조 공간 복잡도는 `O(1)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr;
        private int len;

        private int read() throws IOException {
            if (ptr == len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        int tests = fs.nextInt();
        StringBuilder answer = new StringBuilder();

        while (tests-- > 0) {
            int n = fs.nextInt();
            long valueSum = (long) n * (n + 1) * (n + 2) / 6;
            for (int i = 0; i < n; i++) {
                if (fs.nextInt() == 0) {
                    valueSum += (long) (i + 1) * (n - i);
                }
            }
            answer.append(valueSum).append('\n');
        }
        System.out.print(answer);
    }
}
```
