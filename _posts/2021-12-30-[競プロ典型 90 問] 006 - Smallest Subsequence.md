---
title: AtCoder. 006 Smallest Subsequence(5)
author: MINJUN PARK
date: 2021-12-30 02:45:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Smallest Subsequence,
  ]
pin: false
lang: en
translation_key: atcoder-typical90-006-smallest-subsequence
permalink: /posts/競プロ典型-90-問-006-Smallest-Subsequence/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_f)

Choose exactly `K` characters from `S` without changing their order, so that the resulting subsequence is lexicographically smallest.

We can discard exactly `N - K` characters. Scan `S` from left to right while keeping the chosen characters in a stack. Whenever the current character is smaller than the stack's last character and a discard is still available, remove that last character. This is a lexicographic exchange: replacing a larger character at an earlier position with the smaller current character makes the result smaller, and the removed character can no longer improve the prefix. Continue popping while the condition holds, then append the current character.

The discard budget is essential: a character may be removed only while fewer than `N - K` characters have been removed. If the scan ends with unused removals, the remaining removals must come from the end of the stack. The stack then contains exactly `K` characters. Each character is appended once and removed at most once, so the time and space complexities are both `O(N)`.

```java
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String[] firstLine = input.readLine().trim().split("\\s+");
    int n = Integer.parseInt(firstLine[0]);
    int k = Integer.parseInt(firstLine[1]);
    String s = input.readLine().trim();

    char[] stack = new char[n];
    int size = 0;
    int removalsLeft = n - k;

    for (int i = 0; i < n; i++) {
      char current = s.charAt(i);
      while (removalsLeft > 0 && size > 0 && stack[size - 1] > current) {
        size--;
        removalsLeft--;
      }
      stack[size++] = current;
    }

    size -= removalsLeft;
    System.out.println(new String(stack, 0, size));
  }
}
```
