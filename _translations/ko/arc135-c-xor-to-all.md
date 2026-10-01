---
title: AtCoder ARC 135 C - XOR to All
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ARC, XOR]
pin: false
lang: ko
translation_key: arc135-c-xor-to-all
permalink: /ko/posts/arc135-c-xor-to-all/
source_permalink: /posts/Atcoder-C-XOR-to-All/
---

[문제: AtCoder ARC 135 C — XOR to All](https://atcoder.jp/contests/arc135/tasks/arc135_c) · [English](/posts/Atcoder-C-XOR-to-All/) · [日本語](/ja/posts/arc135-c-xor-to-all/)

각 인덱스 `i`에 대해 `sum_j (A[i] XOR A[j])`를 계산하고, 모든 `i` 중 최댓값을 구합니다. 비트 위치 `b` 하나씩 살펴봅시다. `A[i]`의 `b`번째 비트가 0이면 XOR 결과의 해당 비트가 1인 원소는 그 비트가 1인 `count[b]`개입니다. 반대로 `A[i]`의 해당 비트가 1이면 나머지 `N - count[b]`개에서 XOR 결과의 비트가 1입니다. 따라서 이 비트의 기여도는 `2^b`에 해당 개수를 곱한 값이며, 모든 비트의 기여도를 더하면 `i`에 대한 합을 얻습니다.

값의 상한은 `10^8`이므로 0부터 29까지 30개 비트로 모든 값을 표현할 수 있습니다. 입력 값과 비트별 개수를 저장한 다음 각 값에 대해 30개 비트를 계산합니다. 합과 최댓값은 `long`으로 처리하고, 비트 가중치에도 `1L << b`를 사용해야 곱셈 전 `int` 오버플로가 발생하지 않습니다. 최댓값을 0으로 시작하면 모든 값이 0인 경우도 올바르게 처리됩니다. 시간 복잡도는 `B = 30`일 때 `O(N * B)`이며, 저장된 입력 배열을 제외한 추가 공간은 `O(B)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int BITS = 30;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        String[] tokens = input.readLine().split(" ");
        int[] values = new int[n];
        int[] bitCount = new int[BITS];

        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(tokens[i]);
            for (int bit = 0; bit < BITS; bit++) {
                if ((values[i] & (1 << bit)) != 0) {
                    bitCount[bit]++;
                }
            }
        }

        long answer = 0;
        for (int value : values) {
            long sum = 0;
            for (int bit = 0; bit < BITS; bit++) {
                int ones = (value & (1 << bit)) == 0
                        ? bitCount[bit]
                        : n - bitCount[bit];
                sum += (1L << bit) * ones;
            }
            answer = Math.max(answer, sum);
        }

        System.out.println(answer);
    }
}
```
