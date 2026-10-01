---
title: BOJ. 두 용액 (2470)
author: MINJUN PARK
date: 2022-01-27 22:56:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Two Liquid, 두 용액]
pin: false
lang: ko
translation_key: boj-2470-closest-to-zero-pair
permalink: /ko/posts/boj-2470-closest-to-zero-pair/
source_permalink: /posts/BOJ-2470/
---

[BOJ 2470: 두 용액](https://www.acmicpc.net/problem/2470) · [English](/posts/BOJ-2470/) · [日本語](/ja/posts/boj-2470-closest-to-zero-pair/)

서로 다른 두 용액을 골라 합의 절댓값이 가장 작게 만드는 문제입니다. 용액 값을 정렬한 다음 양 끝에 두 포인터를 둡니다. 매 단계에서 두 포인터가 가리키는 서로 다른 원소의 합을 후보로 보고, 지금까지의 최소 절댓값보다 작으면 두 인덱스를 저장합니다. 합이 음수이면 합을 키우기 위해 왼쪽 포인터를 오른쪽으로 옮기고, 양수이면 합을 줄이기 위해 오른쪽 포인터를 왼쪽으로 옮깁니다. 합이 0이면 절댓값을 더 줄일 수 없으므로 즉시 종료합니다.

모든 값이 같은 부호인 경우에도 같은 규칙으로 포인터가 진행하며 가능한 쌍을 확인합니다. 항상 `left < right`이므로 같은 원소를 두 번 선택하지 않습니다. 덧셈 전에 값을 `long`으로 확장하고, 절댓값 비교도 `long` 범위에서 수행해 큰 합의 오버플로를 피합니다. 정렬은 `O(N log N)`, 투 포인터 순회는 `O(N)`이므로 전체 시간 복잡도는 `O(N log N)`이고, 정렬된 복사본을 위한 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }
        Arrays.sort(values);

        int left = 0;
        int right = n - 1;
        int bestLeft = left;
        int bestRight = right;
        long bestAbs = Long.MAX_VALUE;

        while (left < right) {
            long sum = (long) values[left] + values[right];
            long absSum = Math.abs(sum);
            if (absSum < bestAbs) {
                bestAbs = absSum;
                bestLeft = left;
                bestRight = right;
            }

            if (sum == 0) {
                break;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(values[bestLeft] + " " + values[bestRight]);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = input.read();
            }
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return sign * value;
        }
    }
}
```

## JavaScript

```javascript
const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split(/\s+/).map(BigInt);
const n = Number(input[0]);
const values = input.slice(1, n + 1).sort((a, b) => (a < b ? -1 : a > b ? 1 : 0));

let left = 0;
let right = n - 1;
let bestLeft = left;
let bestRight = right;
let bestAbs = null;

while (left < right) {
    const sum = values[left] + values[right];
    const absSum = sum < 0n ? -sum : sum;
    if (bestAbs === null || absSum < bestAbs) {
        bestAbs = absSum;
        bestLeft = left;
        bestRight = right;
    }

    if (sum === 0n) {
        break;
    } else if (sum < 0n) {
        left++;
    } else {
        right--;
    }
}

console.log(`${values[bestLeft]} ${values[bestRight]}`);
```

JavaScript에서는 `BigInt`를 사용해 덧셈과 절댓값 비교를 정확하게 처리합니다. 저장한 인덱스는 정렬된 배열에서 앞뒤 순서이므로 출력은 정렬된 두 값이며, 입력에서 서로 다른 두 원소입니다.
