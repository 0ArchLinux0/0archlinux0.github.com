---
title: Codeforces 1638A - Reverse
author: MINJUN PARK
date: 2022-02-14 23:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, Codeforces, Codeforces Round, Reverse]
pin: false
lang: ja
translation_key: cf-1638a-reverse
permalink: /ja/posts/cf-1638a-reverse/
source_permalink: /posts/Codeforces-Codeforces-Round-771-(Div.-2)-A.-Reverse/
---

[問題: Codeforces 1638A — Reverse](https://codeforces.com/contest/1638/problem/A) · [한국어](/ko/posts/cf-1638a-reverse/) · [English](/posts/Codeforces-Codeforces-Round-771-(Div.-2)-A.-Reverse/)

配列は `1..n` の順列です。1つの区間を選んで反転し、辞書順で最小の順列を作ります。

左から調べ、`p[i] != i + 1` となる最初の位置 `i` を探します。それより前の位置には、すでに置ける最小の値が入っているため、そのまま固定します。値 `i + 1` は順列中にちょうど1回現れるので、その位置を `j` とします。`[i, j]` を反転すると `i + 1` が最初の不一致位置に移動し、正しい接頭辞は変わりません。よって、この結果が辞書順で最小です。

不一致がなければ順列はすでに整列済みなので、長さ1の区間を反転すればそのままです。最初の不一致と目的の値の位置を探す時間はテストケースごとに `O(n)`、入力順列を保持する補助領域は `O(n)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr;
        private int len;

        private int read() throws IOException {
            if (ptr == len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        int tests = fs.nextInt();
        StringBuilder answer = new StringBuilder();

        while (tests-- > 0) {
            int n = fs.nextInt();
            int[] permutation = new int[n];
            for (int i = 0; i < n; i++) {
                permutation[i] = fs.nextInt();
            }

            int left = 0;
            while (left < n && permutation[left] == left + 1) {
                left++;
            }

            if (left < n) {
                int right = left;
                while (permutation[right] != left + 1) {
                    right++;
                }
                while (left < right) {
                    int value = permutation[left];
                    permutation[left++] = permutation[right];
                    permutation[right--] = value;
                }
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) answer.append(' ');
                answer.append(permutation[i]);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
```
