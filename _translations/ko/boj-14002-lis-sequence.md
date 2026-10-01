---
title: BOJ 14002 — 가장 긴 증가하는 부분 수열 4
author: MINJUN PARK
date: 2022-01-11 18:36:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, LIS, Longest Increasing Subsequence(4), 가장 긴 증가하는 부분 수열 4]
pin: false
lang: ko
translation_key: boj-14002-lis-sequence
permalink: /ko/posts/boj-14002-lis-sequence/
source_permalink: /posts/BOJ-14002/
---

[문제 링크](https://www.acmicpc.net/problem/14002)

각 입력값에 대해 `tails[length]`에는 해당 길이의 증가 부분 수열이 가질 수 있는 가장 작은 끝값을 저장하고, 그 값을 만든 입력 인덱스도 함께 저장합니다. 현재 값 이상인 첫 번째 꼬리값(`lower_bound`)을 이 값으로 교체합니다. 꼬리값 배열 자체가 실제 부분 수열을 나타내는 것은 아닙니다. 실제 수열을 복원하기 위해 인덱스를 보관합니다.

첫 번째 꼬리값 `>= value`를 찾으면 엄격한 증가 조건이 보장됩니다. 기존 꼬리값과 같은 값은 수열을 연장하지 않고 해당 위치를 교체하기 때문입니다. 현재 인덱스의 이전 인덱스는 꼬리값을 갱신하기 전에 저장한 바로 앞 길이의 꼬리 인덱스입니다. 가장 긴 꼬리 인덱스에서 이전 인덱스 연결을 따라가면 선택된 수열을 역순으로 얻으며, 이를 뒤집으면 답이 됩니다.

각 값마다 이진 탐색과 갱신을 수행하므로 시간 복잡도는 `O(N log N)`입니다. 입력, 꼬리값, 꼬리 인덱스, 이전 인덱스, 복원 배열은 각각 `O(N)` 공간을 사용합니다. 원소가 하나면 길이 1의 수열이 나오며, 중복되거나 감소하는 값은 수열 길이를 잘못 늘리지 않습니다.

```java
import java.io.*;

public class Main {
    private static final class FastScanner {
        private final InputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

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
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] values = new int[n];
        int[] tails = new int[n];
        int[] tailIndices = new int[n];
        int[] predecessor = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
            predecessor[i] = -1;
        }

        int length = 0;
        for (int i = 0; i < n; i++) {
            int value = values[i];
            int left = 0;
            int right = length;
            while (left < right) {
                int middle = left + (right - left) / 2;
                if (tails[middle] >= value) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }

            int position = left;
            if (position > 0) predecessor[i] = tailIndices[position - 1];
            tails[position] = value;
            tailIndices[position] = i;
            if (position == length) length++;
        }

        int[] answer = new int[length];
        int index = tailIndices[length - 1];
        for (int i = length - 1; i >= 0; i--) {
            answer[i] = values[index];
            index = predecessor[index];
        }

        StringBuilder output = new StringBuilder();
        output.append(length).append('\n');
        for (int value : answer) output.append(value).append(' ');
        System.out.println(output);
    }
}
```
