---
title: AtCoder. 001 요칸 파티 (4)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, AtCoder, Yokan Party, 요칸 파티]
pin: false
lang: ko
translation_key: atcoder-typical90-001-yokan-party
permalink: /ko/posts/atcoder-typical90-001-yokan-party/
---

길이 `L`인 막대와 `N`개의 가능한 절단 위치가 주어집니다. 가능한 위치 중 정확히 `K`개를 선택해 막대를 `K + 1`개의 조각으로 나누고, 가장 짧은 조각의 길이를 최대화합니다.

최솟값 후보 `d`에 대해 가능한 절단 위치를 왼쪽부터 확인합니다. 마지막 절단 위치(처음에는 `0`)로부터 거리가 `d` 이상이 되는 즉시 절단하고, `K`번 절단하면 탐색을 멈춥니다. 각 절단을 가능한 한 앞쪽에 두므로 이후 조각을 위한 공간을 가장 많이 남깁니다. 따라서 어떤 `K`개 선택이 조건을 만족한다면 이처럼 가장 앞선 탐욕적 절단도 만족합니다. 후보가 가능하려면 `K`번 절단할 수 있고 마지막 절단 위치부터 `L`까지 남은 길이도 `d` 이상이어야 합니다. `K = 0`이면 절단이 필요 없으므로 `L >= d`일 때 가능합니다.

가능성은 단조적입니다. 최소 길이 `d`가 가능하다면 그보다 작은 양의 길이도 모두 가능합니다. 따라서 `[0, L]` 범위에서 가능한 최댓값을 이분 탐색합니다. 각 가능성 검사는 `O(N)` 시간이 걸리므로 총 시간 복잡도는 `O(N log L)`이고, 입력 위치를 저장하는 추가 공간은 `O(N)`입니다.

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_a)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int length = input.nextInt();
        int k = input.nextInt();

        int[] positions = new int[n];
        for (int i = 0; i < n; i++) {
            positions[i] = input.nextInt();
        }

        int low = 0;
        int high = length + 1;
        while (high - low > 1) {
            int middle = low + (high - low) / 2;
            if (canAchieve(middle, positions, k, length)) {
                low = middle;
            } else {
                high = middle;
            }
        }
        System.out.println(low);
    }

    private static boolean canAchieve(int minimumLength, int[] positions, int k, int length) {
        if (k == 0) {
            return length >= minimumLength;
        }

        int previousCut = 0;
        int cuts = 0;
        for (int position : positions) {
            if (position - previousCut >= minimumLength) {
                previousCut = position;
                if (++cuts == k) {
                    return length - previousCut >= minimumLength;
                }
            }
        }
        return false;
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

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
}
```
