---
title: AtCoder ARC 135 B - Sum of Three Terms
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ARC]
pin: false
lang: ja
translation_key: arc135-b-sum-three-terms
permalink: /ja/posts/arc135-b-sum-three-terms/
source_permalink: /posts/Atcoder-B-Sum-of-Three-Terms/
---

[問題: AtCoder ARC 135 B — Sum of Three Terms](https://atcoder.jp/contests/arc135/tasks/arc135_b) · [English](/posts/Atcoder-B-Sum-of-Three-Terms/) · [한국어](/ko/posts/arc135-b-sum-three-terms/)

長さ `N` の配列 `A` が与えられます。長さ `N + 2` の非負整数配列 `B` が存在し、すべての `0 <= i < N` について

`A[i] = B[i] + B[i + 1] + B[i + 2]`

を満たすか判定します。存在すれば任意の配列を `Yes` とともに、存在しなければ `No` を出力します。

隣り合う2つの式を引くと、次の関係が得られます。

`A[i + 1] - A[i] = B[i + 3] - B[i]`。

したがって、`B` の添字を3で割った余りが同じ要素は、それぞれ独立した列を作ります。各列の最初の値を決めれば、後続の値は `A` の差から決まります。余り `r` の列で差を累積した和を `prefix[r]` とし、初期値の0も含めた最小値を `minPrefix[r]` とします。列の各値は `B[r] + prefix[r]` なので、すべてを非負にする条件は `B[r] >= -minPrefix[r]` です。

余り0と1の列の開始値は最小値にします。つまり `B[0] = -minPrefix[0]`、`B[1] = -minPrefix[1]` とします。最初の式 `B[0] + B[1] + B[2] = A[0]` によって、`B[2] = A[0] - B[0] - B[1]` と決まります。`B[2] < -minPrefix[2]` なら解はありません。3つの列で必要な最小開始値の合計が、すでに `A[0]` を超えているからです。そうでなければ、3つの列の値はすべて非負になり、漸化式で構成した `B` は条件を満たします。

`N = 1` の場合も同じ処理で扱えます。差が存在しないため、3列それぞれの最小累積値は0です。`A[0]` を非負の3値に分ければよく、特に `A[0] = 0` なら構成される値はすべて0です。

隣接する `A` の要素を一度ずつ処理するため、時間計算量は `O(N)` です。`A` と構成した配列を保存するため、空間計算量は `O(N)` です。差、累積和、構成する値には `long` を使います。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        long[] a = new long[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(tokens.nextToken());
        }

        long[] prefix = new long[3];
        long[] minPrefix = new long[3];
        for (int i = 0; i + 1 < n; i++) {
            int residue = i % 3;
            prefix[residue] += a[i + 1] - a[i];
            minPrefix[residue] = Math.min(minPrefix[residue], prefix[residue]);
        }

        long[] b = new long[n + 2];
        b[0] = -minPrefix[0];
        b[1] = -minPrefix[1];
        b[2] = a[0] - b[0] - b[1];
        if (b[2] < -minPrefix[2]) {
            System.out.println("No");
            return;
        }

        for (int i = 0; i + 3 < n + 2; i++) {
            b[i + 3] = b[i] + a[i + 1] - a[i];
        }

        StringBuilder output = new StringBuilder("Yes\n");
        for (int i = 0; i < b.length; i++) {
            if (i > 0) {
                output.append(' ');
            }
            output.append(b[i]);
        }
        System.out.println(output);
    }
}
```
