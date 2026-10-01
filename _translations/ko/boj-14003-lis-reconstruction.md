---
title: BOJ. 가장 긴 증가하는 부분 수열 5 (14003)
author: MINJUN PARK
date: 2022-01-12 03:31:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Binary Search, Longest Increasing Subsequence(5), 가장 긴 증가하는 부분 수열 5]
pin: false
lang: ko
translation_key: boj-14003-lis-reconstruction
permalink: /ko/posts/boj-14003-lis-reconstruction/
source_permalink: /posts/BOJ-14003/
---

[BOJ 14003: 가장 긴 증가하는 부분 수열 5](https://www.acmicpc.net/problem/14003)

## 풀이

각 입력 값에 대해 `tails[length]`에는 길이가 `length + 1`인 증가 부분 수열이 가질 수 있는 가장 작은 끝값을 저장하고, `tailIndices[length]`에는 그 끝값을 제공하는 입력 인덱스를 저장합니다. 현재 값 이상인 첫 번째 끝값을 이분 탐색(`lower_bound`)하여 현재 값으로 교체합니다. `tails`의 값 자체는 하나의 부분 수열을 이루지 않을 수 있지만, 각 끝값의 인덱스와 선행 인덱스를 기록하면 실제 부분 수열을 복원할 수 있습니다.

첫 번째로 현재 값 이상인 위치를 찾으므로 수열은 엄격히 증가합니다. 같은 값은 기존 끝값을 교체할 뿐 길이를 늘리지 않습니다. `tails`를 바꾸기 전에 현재 원소의 선행 인덱스를 바로 앞 길이의 `tailIndices` 값으로 기록합니다. 해당 끝값은 현재 값보다 작고 현재 원소보다 앞에서 처리되었으므로, 모든 선행 인덱스는 더 이르면서 값도 더 작습니다. 탐색 위치가 0이면 선행 원소가 없는 새 시작점입니다. 마지막 최장 길이의 끝 인덱스에서 선행 인덱스를 따라가면 역순으로 한 최장 부분 수열을 얻습니다.

불변식은 각 입력 접두 구간을 처리한 뒤 `tails[k]`가 길이 `k + 1`인 증가 부분 수열의 가능한 최소 끝값이고, `tailIndices[k]`가 실제로 그 값을 갖는 입력 위치라는 것입니다. 더 작은 끝값은 이후 원소를 이어 붙이기 적어도 그만큼 유리하므로 lower-bound 위치를 교체해도 불변식이 유지됩니다. 현재 길이의 끝에서만 최장 길이가 하나 늘어날 수 있습니다. 끝값이 나중에 교체되어도 그 전에 저장한 선행 연결은 이미 처리된 유효한 사슬을 가리키므로 복원은 깨지지 않습니다. 입력에는 원소가 하나 이상 있으므로 `length`는 항상 1 이상이며 `N = 1`에서도 `tailIndices[length - 1]`에 유효하게 접근합니다.

원소마다 이분 탐색 한 번과 상수 시간 갱신을 하므로 시간 복잡도는 `O(N log N)`입니다. 입력, 끝값, 끝 인덱스, 선행 인덱스, 복원 배열은 각각 `O(N)` 메모리를 사용합니다.

## Java

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
