---
title: AtCoder. ABC 238 B Pizza
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    ABC contest
  ]
pin: false
lang: en
translation_key: abc238-b-pizza
---

[Problem: AtCoder ABC 238 B — Pizza](https://atcoder.jp/contests/abc238/tasks/abc238_b) · [한국어](/ko/posts/abc238-b-pizza/) · [日本語](/ja/posts/abc238-b-pizza/)

Start with a cut at `0°`. Each instruction rotates the knife clockwise by the given angle, so the new cut is the previous angle plus that rotation, modulo `360`. Store all `N` resulting cut positions together with `0°`. Repeated positions are valid: they mean that a cut falls on an existing cut and create a zero-width gap.

Sort the positions. The gaps between consecutive positions are slice sizes, and the last slice wraps around from the final cut to `360°` (the same point as `0°`). The largest of these gaps is the largest possible slice. With `N + 1` positions, sorting takes `O(N log N)` time and the array uses `O(N)` space.

For example, with rotations `90, 180, 45, 195`, the cut positions are `0, 90, 270, 315, 150`. After sorting they are `0, 90, 150, 270, 315`; the gaps are `90, 60, 120, 45`, and the wraparound gap is `45`, so the answer is `120`.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        StringTokenizer rotations = new StringTokenizer(input.readLine());

        int[] cuts = new int[n + 1];
        int angle = 0;
        for (int i = 1; i <= n; i++) {
            angle = (angle + Integer.parseInt(rotations.nextToken())) % 360;
            cuts[i] = angle;
        }

        Arrays.sort(cuts);

        int largest = 0;
        for (int i = 1; i <= n; i++) {
            largest = Math.max(largest, cuts[i] - cuts[i - 1]);
        }
        largest = Math.max(largest, 360 - cuts[n] + cuts[0]);

        System.out.println(largest);
    }
}
```
