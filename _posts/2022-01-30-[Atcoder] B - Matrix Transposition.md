---
title: AtCoder. ABC 237 B Matrix Transposition
author: MINJUN PARK
date: 2022-01-30 21:10:00 +0900
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
translation_key: abc237-b-matrix-transposition
---

[Problem: AtCoder ABC 237 B — Matrix Transposition](https://atcoder.jp/contests/abc237/tasks/abc237_b)

Given an `H` by `W` matrix `A`, output its transpose `B`, which has `W` rows and `H` columns. Transposition swaps each element's row and column indices: for every `0 <= i < H` and `0 <= j < W`, set `B[j][i] = A[i][j]`. The dimensions need not be equal, so the output must be built with `W` rows of `H` values each; this also handles a single row or column naturally.

The constraints are small (`1 <= H, W <= 100`), and every input value can be copied once into its transposed position. The time and storage required are both `O(HW)`. The code reads the dimensions and matrix, then prints each output row with spaces between values and a newline after the row.

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int[][] a = new int[h][w];

        for (int i = 0; i < h; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < w; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        StringBuilder out = new StringBuilder();
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                if (i > 0) out.append(' ');
                out.append(a[i][j]);
            }
            out.append('\n');
        }
        System.out.print(out);
    }
}
```
