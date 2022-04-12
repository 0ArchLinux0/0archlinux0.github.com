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
---

[Link] <https://AtCoder.jp/contests/typical90/tasks/typical90_f>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] argA = br.readLine().split("\\s");
    int n = Integer.parseInt(argA[0]);
    int k = Integer.parseInt(argA[1]);
    String s = br.readLine();
    int idx = 0, tempIdx = 0, len = 0;
    char temp = 0;
    while(len != k) {
      temp = s.charAt(idx);
      tempIdx = idx;
      for(; idx <= n - k + len; idx++) {
        if(temp > s.charAt(idx)) {
          temp = s.charAt(idx);
          tempIdx = idx;
        }
      }
      idx = tempIdx + 1;
      lem++;
      sb.append(temp);
    }
    System.out.println(sb);
  }
}
```
