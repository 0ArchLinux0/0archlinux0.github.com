---
title: AtCoder. ABC 237 D LR insertion
author: MINJUN PARK
date: 2022-01-30 21:50:00 +0900
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
translation_key: abc237-d-lr-insertion
permalink: /posts/Atcoder-D-LR-insertion/
---

[Problem: AtCoder ABC 237 D — LR insertion](https://atcoder.jp/contests/abc237/tasks/abc237_d) · [한국어](/ko/posts/abc237-d-lr-insertion/) · [日本語](/ja/posts/abc237-d-lr-insertion/)

The recursive inorder traversal of a tree with up to 500,000 nodes can overflow the call stack. Instead, construct the answer directly with a deque. Start with `N`, then process `S` from right to left. For each index `i`, append `i` to the back if `S[i]` is `L`; otherwise prepend it to the front. The deque then contains the required order.

This works because we undo the insertions in reverse order. The last value to be inserted is `N`, so it is the initial deque. For each earlier `i`, `L` means `i + 1` was inserted immediately before `i`, so `i` belongs at the right end; `R` means `i + 1` was inserted immediately after `i`, so `i` belongs at the left end. Each operation extends the corresponding end and preserves the required relative order. Each value is added once, so the time and space complexities are both `O(N)`.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        String s = input.readLine().trim();

        Deque<Integer> order = new ArrayDeque<>();
        order.addLast(n);
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == 'L') {
                order.addLast(i);
            } else {
                order.addFirst(i);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int value : order) {
            if (answer.length() > 0) {
                answer.append(' ');
            }
            answer.append(value);
        }
        System.out.println(answer);
    }
}
```
