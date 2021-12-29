---
title: Atcoder. 002 - Encyclopedia of Parentheses(3)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Encyclopedia of Parentheses
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_s>

<br>

```java
import java.util.*;
import java.io.*;

public class Main{
  static int n;
  static StringBuilder sb = new StringBuilder();
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();
    if(n%2 == 1) return;

    char[] charArr = new char[n];
    bfs(charArr, 0, 0, 0);
    System.out.println(sb.toString());
  }

  static void bfs(char[] charArr, int index, int open, int close) {
    if(index == n) {
      sb.append(new String(charArr)).append("\n");
      return;
    }
    if(open < n/2 && close < n/2) {
      charArr[index] = '(';
      open++;
      bfs(charArr, index + 1 , open, close);
      open--;
      if(open > close) {
        charArr[index] = ')';
        close++;
        bfs(charArr, index + 1 , open, close);
        close--;
      }
    } else if(open == n/2) {
      for(int i = index; i < n; i++) charArr[i] = ')';
      bfs(charArr, n, n/2, n/2);
    }
  }
}
```
