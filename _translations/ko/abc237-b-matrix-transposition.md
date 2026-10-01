---
title: AtCoder ABC 237 B - 행렬 전치
author: MINJUN PARK
date: 2022-01-30 21:10:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC]
pin: false
lang: ko
translation_key: abc237-b-matrix-transposition
permalink: /ko/posts/abc237-b-matrix-transposition/
source_permalink: /posts/Atcoder-B-Matrix-Transposition/
---

[문제: AtCoder ABC 237 B — 행렬 전치](https://atcoder.jp/contests/abc237/tasks/abc237_b) · [English](/posts/Atcoder-B-Matrix-Transposition/) · [日本語](/ja/posts/abc237-b-matrix-transposition/)

`H`행 `W`열 행렬 `A`가 주어지면 전치 행렬 `B`를 출력합니다. 전치 행렬의 크기는 `W`행 `H`열이며, 원소의 행과 열 인덱스를 서로 바꿉니다. 즉 모든 `0 <= i < H`, `0 <= j < W`에 대해 `B[j][i] = A[i][j]`입니다. 정사각형이 아닌 행렬도 가능하므로 결과는 `W`개의 행에 각각 `H`개의 값을 출력해야 합니다. 행이나 열이 하나인 경우도 같은 규칙으로 처리됩니다.

제한에서 `1 <= H, W <= 100`이므로 각 원소를 새 위치에 한 번씩 복사하면 충분합니다. 시간 복잡도와 공간 복잡도는 모두 `O(HW)`입니다. 코드는 차원과 행렬을 읽은 뒤, 결과의 각 행에서 값 사이에 공백을 넣고 행마다 줄바꿈해 출력합니다.

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
