---
title: AtCoder ABC 237 B - 行列の転置
author: MINJUN PARK
date: 2022-01-30 21:10:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC]
pin: false
lang: ja
translation_key: abc237-b-matrix-transposition
permalink: /ja/posts/abc237-b-matrix-transposition/
source_permalink: /posts/Atcoder-B-Matrix-Transposition/
---

[問題: AtCoder ABC 237 B — 行列の転置](https://atcoder.jp/contests/abc237/tasks/abc237_b) · [English](/posts/Atcoder-B-Matrix-Transposition/) · [한국어](/ko/posts/abc237-b-matrix-transposition/)

`H` 行 `W` 列の行列 `A` が与えられるので、その転置行列 `B` を出力します。転置後の行列は `W` 行 `H` 列で、各要素の行番号と列番号を入れ替えます。つまり、すべての `0 <= i < H`、`0 <= j < W` について `B[j][i] = A[i][j]` とします。正方行列とは限らないため、出力は `W` 行で各行に `H` 個の値を並べます。この方法なら、行や列が 1 つの場合もそのまま扱えます。

制約は `1 <= H, W <= 100` と小さいため、各要素を転置後の位置に一度ずつコピーすれば十分です。時間計算量と空間計算量はいずれも `O(HW)` です。コードでは行列を読み込み、出力行ごとに値の間へ空白を入れ、各行の末尾で改行します。

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
