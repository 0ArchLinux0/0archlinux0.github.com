---
title: Atcoder. 005 - Restricted Digits(7)
author: MINJUN PARK
date: 2021-12-30 02:44:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Restricted Digits
  ]
pin: true
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_e>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  static int con = 1000000007;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    long n;
    int b, k, ans = 0;
    int[] dp;
    n = Long.parseLong(st.nextToken());
    b = Integer.parseInt(st.nextToken());
    k = Integer.parseInt(st.nextToken());
    st = new StringTokenizer(br.readLine());
    dp = new int[b];
    for(int i = 0; i < k; i++) dp[Integer.parseInt(st.nextToken()) % b] += 1;
    int[][] op = new int[b][b];
    for(int i = 0; i < b; i++) {
      for(int j = 0; j < b; j++) {
        if(dp[j] == 0) continue;
        int idx = (10 * i + j) % b;
        op[idx][i] = dp[j];
      }
    }
    long[][] finalOp = new long[b][b];
    for(int i = 0; i < b; i++) finalOp[i][i] = 1;

    // ArrayList<int[][]> al = new ArrayList<>();
    // al.add(0, op); // 2^i;
    long[][] temp = new long[b][b];
    for(int i = 0; i < b; i++) for(int j = 0; j < b; j++) temp[i][j] = op[i][j];
    int curIdx = 0;
    boolean determined = false;
    long v = 1;
    int idx = 0;
    n--;
    while(n > 0) {
      if(n % 2 == 1) {
        if(idx > curIdx) {
          for(int i = curIdx + 1; i <= idx; i++) {
            temp = sqr(temp);
          }
          curIdx = idx;
        }
        finalOp = mul(finalOp, temp);
      }
      n = n >> 1;
      idx++;
    }
    long ans1 = 0;
    for(int i = 0; i < b; i++) {
      ans1 += finalOp[0][i] * dp[i];
      ans1 %= con;  
    }
    System.out.println(ans1);
  }

  static long[][] mul(long[][] a, long[][] b) {
    int len = b.length;
    long[][] ans = new long[a.length][b[0].length];
    for(int i = 0, la = a.length; i < la; i++) {
      for(int j = 0, lb = b[0].length; j < lb; j++) {
        for(int k = 0; k < len; k++) {
          ans[i][j] += a[i][k]*b[k][j];  
          ans[i][j] %= con;
        }
      }
    }
    return ans;
  }

  static long[] mul(long[][] a, int[] b) {
    long[] ans = new long[b.length];
    for(int i = 0, la = a.length; i < la; i++) {
      for(int j = 0, lab = b.length; j < lab; j++) {
        ans[i] += a[i][j] * b[j];  
        ans[i] %= con;
      }
    }
    return ans;
  }

  static long[][] sqr(long[][] a) {
    return mul(a, a);
  }
}
```
