---
title: BOJ 10868 - 최솟값
author: MINJUN PARK
date: 2022-02-18 01:25:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 세그먼트 트리, 최솟값]
pin: false
lang: ko
translation_key: boj-10868-range-minimum-query
permalink: /ko/posts/boj-10868-range-minimum-query/
source_permalink: /posts/BOJ-10868/
---

[문제: BOJ 10868 — 최솟값](https://www.acmicpc.net/problem/10868) · [English](/posts/BOJ-10868/) · [日本語](/ja/posts/boj-10868-range-minimum-query/)

각 질의는 1부터 시작하는 양 끝 포함 구간 `[a, b]`를 주며, 그 구간의 최솟값을 구해야 합니다. 반복형 세그먼트 트리는 `N`개의 값을 `tree[N..2N)`에 저장합니다. 내부 노드는 두 자식의 최솟값으로 아래에서 위로 채웁니다. 이 간결한 배열 배치는 `N`이 2의 거듭제곱이 아니어도 사용할 수 있습니다.

질의를 0부터 시작하는 반열린 구간 `[a - 1, b)`로 바꾼 뒤, 양 끝에 `N`을 더해 리프 인덱스로 변환합니다. 왼쪽 끝이 오른쪽 끝보다 작은 동안, `left`가 오른쪽 자식이면 `tree[left]`를 답에 반영하고 `left`를 증가시킵니다. 오른쪽 끝이 홀수이면 `tree[right - 1]`을 반영하고 `right`를 감소시킵니다. 그런 다음 두 끝을 부모로 옮겨 반복합니다. 이 경계 검사는 요청한 구간을 정확히 덮는 서로 겹치지 않는 트리 노드들을 선택합니다. 답을 `Integer.MAX_VALUE`로 초기화하므로 원소 하나만 포함하는 질의를 비롯한 모든 유효한 구간을 처리할 수 있습니다. 반복은 `O(log N)`에 끝납니다.

트리 구성에는 `O(N)`, `M`개 질의에는 `O(M log N)` 시간이 걸려 전체 시간 복잡도는 `O(N + M log N)`입니다. 트리는 정수 `2N`개를 저장하므로 보조 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input =
                new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

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
            return sign * value;
        }

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
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();
        int[] tree = new int[2 * n];

        for (int i = 0; i < n; i++) {
            tree[n + i] = input.nextInt();
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
        }

        BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(System.out));
        while (m-- > 0) {
            int left = input.nextInt() - 1 + n;
            int right = input.nextInt() + n;
            int minimum = Integer.MAX_VALUE;

            while (left < right) {
                if ((left & 1) != 0) {
                    minimum = Math.min(minimum, tree[left++]);
                }
                if ((right & 1) != 0) {
                    minimum = Math.min(minimum, tree[--right]);
                }
                left >>= 1;
                right >>= 1;
            }

            output.write(Integer.toString(minimum));
            output.newLine();
        }
        output.flush();
    }
}
```
