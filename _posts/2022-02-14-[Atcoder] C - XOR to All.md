---
title: AtCoder. Regular Contest 135 C XOR to All
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    XOR,
    Regular Contest
  ]
pin: false
lang: en
translation_key: arc135-c-xor-to-all
---

[Link] <https://AtCoder.jp/contests/arc135/tasks/arc135_c>
<br>

## Idea

For each index `i`, compute `sum_j (A[i] XOR A[j])`, then take the maximum over all `i`. Consider one bit position `b` at a time. If bit `b` of `A[i]` is zero, its XOR has that bit set exactly for the `count[b]` array elements whose bit is one. If its bit is one, the XOR has that bit set for the other `N - count[b]` elements. Thus the contribution of this bit is `2^b` times the corresponding count, and summing these contributions over all bit positions gives the requested sum for `i`.

The values are at most `10^8`, so 30 bit positions (0 through 29) cover every value. The input values and bit counts are stored once, then each value is evaluated across all 30 bits. The running answer is a `long`; use `1L << b` so the bit weight and its product are computed as `long`, not overflowing as `int`. Initializing the maximum to zero handles arrays containing zero as well as cases where every candidate sum is zero. The time complexity is `O(N * B)` for `B = 30`; auxiliary space is `O(B)` beyond the stored input array.

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
