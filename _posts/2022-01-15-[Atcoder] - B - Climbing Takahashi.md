---
title: AtCoder. ABC 235 B Climbing Takahashi
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
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
translation_key: abc235-b-climbing-takahashi
---

[Problem: AtCoder ABC 235 B — Climbing Takahashi](https://atcoder.jp/contests/abc235/tasks/abc235_b)
[한국어](/ko/posts/abc235-b-climbing-takahashi/) · [日本語](/ja/posts/abc235-b-climbing-takahashi/)

The heights form a sequence along the route. Takahashi starts at the first point and continues only while each next height is **strictly greater** than the current height. At the first equal or lower height, he stops before reaching that point. The answer is the height of the last point he reached.

Initialize the answer with the first height, then scan from the second height onward. Update the answer for each increase and stop at the first non-increase. This also handles both edge cases: if every height increases, the answer becomes the final height; if the second height is already equal or lower, the answer remains the first height.

The scan takes `O(N)` time and `O(N)` extra space for the heights.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        int[] heights = new int[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(tokens.nextToken());
        }

        int answer = heights[0];
        for (int i = 1; i < n; i++) {
            if (heights[i] <= heights[i - 1]) {
                break;
            }
            answer = heights[i];
        }

        System.out.println(answer);
    }
}
```
