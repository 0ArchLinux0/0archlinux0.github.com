---
title: BOJ. 파일 합치기 (11066)
author: MINJUN PARK
date: 2022-01-05 12:20:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Merge Files, 파일 합치기]
pin: false
lang: ko
translation_key: boj-11066-file-merge
permalink: /ko/posts/boj-11066-file-merge/
---

[문제 링크](https://www.acmicpc.net/problem/11066)

인접한 두 파일을 합치는 비용은 두 파일 크기의 합입니다. 파일의 순서는 유지되므로, 마지막으로 합치는 시점을 기준으로 최적의 과정을 나눌 수 있습니다. `[i, k]` 구간을 먼저 하나로 합치고 `[k + 1, j]` 구간도 하나로 합친 다음, 두 결과를 합치는 비용은 `[i, j]` 전체 크기입니다.

`dp[i][j]`를 0부터 시작하는 인덱스에서 `i`번부터 `j`번 파일까지 합치는 최소 비용으로 정의하고, `prefix`를 파일 크기의 누적 합으로 둡니다.

```text
dp[i][i] = 0
dp[i][j] = prefix[j + 1] - prefix[i]
           + min(dp[i][k] + dp[k + 1][j]), i <= k < j
```

누적 합으로 구간 크기를 상수 시간에 계산할 수 있습니다. 모든 분할점을 탐색하면 시간 복잡도는 `O(K^3)`입니다. 파일 크기가 양수이면 구간 크기 비용은 사각 부등식(quadrangle inequality)을 만족하고, 그에 따라 최적 분할점은 다음 단조성 불변식을 만족합니다.

```text
opt[i][j - 1] <= opt[i][j] <= opt[i + 1][j]
```

따라서 `dp[i][j]`를 계산할 때 분할점은 전체 `[i, j)`가 아니라 `opt[i][j - 1]`부터 `opt[i + 1][j]`까지만 확인하면 됩니다. `opt[i][i] = i`로 초기화하고 구간 길이가 짧은 것부터 계산하면 필요한 두 최적 분할점은 이미 구해져 있습니다. 각 구간에서 처음 발견한 최소값을 선택해 최적 분할점 표를 일관되게 유지합니다. 총 시간 복잡도는 `O(K^2)`, `dp`와 `opt`를 위한 공간 복잡도는 `O(K^2)`이며 누적 합에는 `O(K)`가 필요합니다. 비용과 누적 합에는 모두 `long`을 사용합니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int k = input.nextInt();
            long[] prefix = new long[k + 1];
            for (int i = 0; i < k; i++) {
                prefix[i + 1] = prefix[i] + input.nextInt();
            }

            long[][] dp = new long[k][k];
            int[][] opt = new int[k][k];
            for (int i = 0; i < k; i++) {
                opt[i][i] = i;
            }

            for (int length = 2; length <= k; length++) {
                for (int i = 0; i + length <= k; i++) {
                    int j = i + length - 1;
                    int firstSplit = opt[i][j - 1];
                    int lastSplit = opt[i + 1][j];
                    long intervalSize = prefix[j + 1] - prefix[i];
                    long best = Long.MAX_VALUE;
                    int bestSplit = firstSplit;

                    for (int split = firstSplit; split <= lastSplit; split++) {
                        long cost = dp[i][split] + dp[split + 1][j] + intervalSize;
                        if (cost < best) {
                            best = cost;
                            bestSplit = split;
                        }
                    }
                    dp[i][j] = best;
                    opt[i][j] = bestSplit;
                }
            }

            output.append(dp[0][k - 1]).append('\n');
        }

        System.out.print(output);
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
                if (length == -1) return -1;
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
