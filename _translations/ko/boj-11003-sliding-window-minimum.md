---
title: BOJ. 최솟값 찾기 (11003)
author: MINJUN PARK
date: 2022-02-09 17:40:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Deque, Sliding Window, 최솟값]
pin: false
lang: ko
translation_key: boj-11003-sliding-window-minimum
permalink: /ko/posts/boj-11003-sliding-window-minimum/
source_permalink: /posts/BOJ-11003/
---

[문제: BOJ 11003 — 최솟값 찾기](https://www.acmicpc.net/problem/11003)

[English](/posts/BOJ-11003/) · [日本語](/ja/posts/boj-11003-sliding-window-minimum/)

## 풀이

각 위치 `i`에서 구해야 하는 구간은 `[max(0, i - L + 1), i]`입니다. 따라서 처음 `L - 1`개의 구간에는 지금까지 입력된 값만 들어가며, 존재하지 않는 값을 채워 넣지 않습니다.

덱에는 후보 인덱스를 오름차순으로 저장하고, 값은 별도의 기본형 배열에 보관합니다. `A[i]`를 넣기 전에 현재 구간에서 벗어난 인덱스 `<= i - L`을 덱 앞에서 제거합니다. 그런 다음 뒤에서부터 값이 `A[i]`보다 크거나 같은 후보를 제거합니다. 새 값은 그 후보보다 작거나 같고 구간에 더 오래 남으므로, 해당 후보는 이후 최솟값이 될 수 없습니다. 따라서 덱 앞의 값이 현재 구간의 최솟값입니다. 같은 값도 뒤에서 제거해도 안전하며, 더 나중에 들어온 값만 남길 수 있습니다.

각 인덱스는 한 번 삽입되고 최대 한 번 제거되므로 전체 시간 복잡도는 분할 상환 `O(N)`입니다. 입력은 바이트를 직접 읽는 스캐너를 사용하고, 덱은 두 개의 `int[]` 배열로 구현해 숫자마다 문자열을 만들거나 원소 객체를 생성하지 않습니다. 배열과 출력 버퍼의 공간 복잡도는 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int windowLength = input.nextInt();
        int[] indices = new int[n];
        int[] values = new int[n];
        int front = 0;
        int back = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int value = input.nextInt();

            while (front < back && indices[front] <= i - windowLength) {
                front++;
            }
            while (front < back && values[back - 1] >= value) {
                back--;
            }

            indices[back] = i;
            values[back] = value;
            back++;

            if (i > 0) {
                output.append(' ');
            }
            output.append(values[front]);
        }

        System.out.println(output);
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```
