---
title: Atcoder. 009 - Three Point Angle(6)
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Three Point Angle,
    review
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_i>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    int n = Integer.parseInt(reader.readLine());
    double min = Double.MAX_VALUE;
    int[][] p = new int[n][2];

    for(int i = 0; i < n; i++) {
      st = new StringTokenizer(reader.readLine());
      p[i][0] = Integer.parseInt(st.nextToken());
      p[i][1] = Integer.parseInt(st.nextToken());
    }

    for(int i = 0 ; i < n ; i++) {
      double[] sort = new double[n - 1]; // n-1
      int idx = 0;
      for(int j = 0; j < n; j++) {
        if(i == j) continue;
        sort[idx++] = getAngle(p[i], p[j]);
        getAngle(p[i], p[j]);
      }
      Arrays.sort(sort);

      for(int k = 0; k < n - 1; k++) {
        double toFind = (180 + sort[k]) % 360;
        int l = 0, r = n-2, mid;
        while(l < r) {
          mid = (l + r) / 2;
          if(l == r - 1) {
            double smaller = Math.min(Math.abs(sort[l] - toFind), Math.abs(sort[r] - toFind));
            min = Math.min(smaller, min);
            break;
          }
          if(sort[mid] < toFind) l = mid; 
          else if(sort[mid] == toFind) {
            min = 0;
            break;
          } else r = mid;
        }
      }
    }
    if(min == Double.valueOf((int)min)) System.out.println(180 - (int)min);
    else System.out.println(180 - min);
  }

  static double getAngle(int[] zeroP, int[] p) {
    int dx, dy;
    dx = p[0] - zeroP[0];
    dy = p[1] - zeroP[1];
    double theta = Math.atan2(dy, dx);
    return theta >= 0 ? theta : (360 + theta);
  }

  // static double getAngle(int[] zeroP, int[] p) {
  //   long dx, dy;
  //   dx = p[0] - zeroP[0];
  //   dy = p[1] - zeroP[1];
  //   double theta = Math.acos((double)dx / Math.sqrt(dx*dx + dy*dy)) * 180 / Math.PI;
  //   return dy >= 0 ? theta : (360 - theta);
  // }
}
```
