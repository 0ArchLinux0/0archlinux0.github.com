---
title: Codeforces 1638A - Reverse
author: MINJUN PARK
date: 2022-02-14 23:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, Codeforces, Codeforces Round, Reverse]
pin: false
lang: ko
translation_key: cf-1638a-reverse
permalink: /ko/posts/cf-1638a-reverse/
source_permalink: /posts/Codeforces-Codeforces-Round-771-(Div.-2)-A.-Reverse/
---

[문제: Codeforces 1638A — Reverse](https://codeforces.com/contest/1638/problem/A) · [English](/posts/Codeforces-Codeforces-Round-771-(Div.-2)-A.-Reverse/) · [日本語](/ja/posts/cf-1638a-reverse/)

배열은 `1..n`의 순열입니다. 하나의 구간을 선택해 뒤집을 수 있을 때, 사전순으로 가장 작은 순열을 만들어야 합니다.

왼쪽부터 살펴보며 `p[i] != i + 1`인 첫 번째 위치 `i`를 찾습니다. 그보다 앞선 위치는 이미 가능한 가장 작은 값이므로 그대로 두어야 합니다. 값 `i + 1`은 순열에 정확히 한 번 나타나므로 그 위치를 `j`라고 합시다. `[i, j]`를 뒤집으면 `i + 1`이 첫 불일치 위치로 이동하고, 이미 올바른 앞부분은 변하지 않습니다. 따라서 이 결과가 사전순으로 가장 작습니다.

불일치가 없다면 순열은 이미 정렬되어 있으므로 길이 1인 구간을 뒤집으면 그대로입니다. 첫 불일치와 목표 값의 위치를 찾는 데 테스트 케이스마다 시간 `O(n)`이 걸리며, 입력 순열을 저장하는 보조 공간은 `O(n)`입니다.

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
            int[] permutation = new int[n];
            for (int i = 0; i < n; i++) {
                permutation[i] = fs.nextInt();
            }

            int left = 0;
            while (left < n && permutation[left] == left + 1) {
                left++;
            }

            if (left < n) {
                int right = left;
                while (permutation[right] != left + 1) {
                    right++;
                }
                while (left < right) {
                    int value = permutation[left];
                    permutation[left++] = permutation[right];
                    permutation[right--] = value;
                }
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) answer.append(' ');
                answer.append(permutation[i]);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
```
