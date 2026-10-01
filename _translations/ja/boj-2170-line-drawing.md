---
title: BOJ 2170 - 線分の長さの合計
author: MINJUN PARK
date: 2022-02-16 02:27:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, ソート, 掃き出し法, 線分]
pin: false
lang: ja
translation_key: boj-2170-line-drawing
permalink: /ja/posts/boj-2170-line-drawing/
source_permalink: /posts/BOJ-2170/
---

[問題: BOJ 2170 — 線分の長さの合計](https://www.acmicpc.net/problem/2170) · [English](/posts/BOJ-2170/) · [한국어](/ko/posts/boj-2170-line-drawing/)

各線分は、両端の座標の間にあるすべての点を覆います。少なくとも1本の線分に覆われる部分の合計の長さを求めます。区間を左端の昇順に並べ、左から順に見ながら、現在まとめている区間の最も右の端点を保持します。次の区間の左端が現在の右端以下なら、重なるか端点が接しているため、必要に応じて右端を伸ばします。次の左端が現在の右端より大きければ、完成した区間の長さを加算して新しい区間を始めます。端点が接していても隙間はなく、まとめても長さは変わりません。

入力された端点の順序が逆の場合にも対応できるよう、各組を `left <= right` となるよう正規化します。長さ0の区間は合計に影響しません。走査が終わった後、最後に保持している区間の長さも忘れずに加算します。座標差やその合計が `int` の範囲を超える可能性があるため、合計には `long` を使います。

ソートに `O(N log N)`、走査に `O(N)` かかるため、全体の時間計算量は `O(N log N)` です。区間の一覧を保存するため、空間計算量は `O(N)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int limit;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }

        private int read() throws IOException {
            if (position == limit) {
                limit = input.read(buffer);
                position = 0;
                if (limit == -1) return -1;
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long[][] intervals = new long[n][2];

        for (int i = 0; i < n; i++) {
            long a = input.nextInt();
            long b = input.nextInt();
            intervals[i][0] = Math.min(a, b);
            intervals[i][1] = Math.max(a, b);
        }

        Arrays.sort(intervals, (a, b) -> Long.compare(a[0], b[0]));

        long left = intervals[0][0];
        long right = intervals[0][1];
        long totalLength = 0;
        for (int i = 1; i < n; i++) {
            long nextLeft = intervals[i][0];
            long nextRight = intervals[i][1];
            if (nextLeft <= right) {
                right = Math.max(right, nextRight);
            } else {
                totalLength += right - left;
                left = nextLeft;
                right = nextRight;
            }
        }
        totalLength += right - left;

        System.out.println(totalLength);
    }
}
```
