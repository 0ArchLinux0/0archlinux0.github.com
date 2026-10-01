---
title: AtCoder. ABC 237 C kasaka
author: MINJUN PARK
date: 2022-01-30 21:20:00 +0900
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
translation_key: abc237-c-kasaka
---

[Problem: AtCoder ABC 237 C — kasaka](https://atcoder.jp/contests/abc237/tasks/abc237_c) · [한국어](/ko/posts/abc237-c-kasaka/) · [日本語](/ja/posts/abc237-c-kasaka/)

The only operation is prepending `a`, so the characters at the end cannot be changed. Let `leadingA` and `trailingA` be the lengths of the runs of `a` at the beginning and end of the string. If `leadingA > trailingA`, the string cannot become a palindrome: the existing leading `a`s would need more matching `a`s at the end than are available.

Otherwise, prepending `trailingA - leadingA` copies of `a` balances those runs. The remaining part between the leading and trailing runs must already be a palindrome. Skip both runs with two pointers and compare the characters that remain. If the pointers cross, that middle part is empty or has one character, so it is a palindrome.

This also covers strings made entirely of `a` (the middle is empty) and strings with no `a` at either end (the entire string is checked). The scan takes `O(|S|)` time and uses `O(1)` extra space.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();

        int left = 0;
        while (left < s.length() && s.charAt(left) == 'a') {
            left++;
        }

        int right = s.length() - 1;
        while (right >= 0 && s.charAt(right) == 'a') {
            right--;
        }

        int leadingA = left;
        int trailingA = s.length() - 1 - right;
        if (leadingA > trailingA) {
            System.out.println("No");
            return;
        }

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                System.out.println("No");
                return;
            }
            left++;
            right--;
        }
        System.out.println("Yes");
    }
}
```
