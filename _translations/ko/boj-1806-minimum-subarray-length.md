---
title: BOJ. 부분합 (1806)
author: MINJUN PARK
date: 2022-01-27 03:45:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    JavaScript,
    Algorithm,
    Coding Interview,
    Two Pointer,
    BOJ,
    Subsequence sum,
    부분합,
    Review
  ]
pin: false
lang: ko
translation_key: boj-1806-minimum-subarray-length
permalink: /ko/posts/boj-1806-minimum-subarray-length/
source_permalink: /posts/BOJ-1806/
---

[문제: BOJ 1806 — 부분합](https://www.acmicpc.net/problem/1806)

모든 수가 양수이므로 오른쪽 경계를 바깥으로 옮기면 구간의 합은 증가하고, 왼쪽 경계를 오른쪽으로 옮기면 합은 감소합니다. 배타적 오른쪽 경계를 하나씩 확장하고, 합이 `S` 이상이 되면 그 구간을 답 후보로 기록한 뒤 합이 계속 `S` 이상인 동안 왼쪽 경계를 이동합니다. 같은 끝점으로 끝나는 더 긴 구간은 답을 더 작게 만들 수 없습니다. 구간을 `[left, right)`로 표현하므로 길이는 정확히 `right - left`이며, 길이 1인 구간이나 배열의 첫/마지막 구간도 별도 처리 없이 올바르게 계산됩니다. 조건을 만족하는 구간이 없으면 답은 `0`입니다. 두 경계가 앞으로만 이동하므로 시간 복잡도는 `O(N)`, 입력 배열을 저장하는 공간 복잡도는 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long target = input.nextLong();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        int minLength = n + 1;
        int left = 0;
        long sum = 0;
        int right = 0;
        while (right < n) {
            sum += values[right++];
            while (sum >= target) {
                minLength = Math.min(minLength, right - left);
                sum -= values[left++];
            }
        }

        System.out.println(minLength == n + 1 ? 0 : minLength);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
            return (int) nextLong();
        }

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```

```javascript
const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split(/\s+/).map(Number);
const n = input[0];
const target = input[1];
const values = input.slice(2);

let minLength = n + 1;
let left = 0;
let sum = 0;
let right = 0;
while (right < n) {
  sum += values[right++];
  while (sum >= target) {
    minLength = Math.min(minLength, right - left);
    sum -= values[left++];
  }
}

console.log(minLength === n + 1 ? 0 : minLength);
```
