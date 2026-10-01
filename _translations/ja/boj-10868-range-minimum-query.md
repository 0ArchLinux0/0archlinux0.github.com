---
title: BOJ 10868 - 最小値
author: MINJUN PARK
date: 2022-02-18 01:25:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, セグメント木, 最小値]
pin: false
lang: ja
translation_key: boj-10868-range-minimum-query
permalink: /ja/posts/boj-10868-range-minimum-query/
source_permalink: /posts/BOJ-10868/
---

[問題: BOJ 10868 — 最小値](https://www.acmicpc.net/problem/10868) · [English](/posts/BOJ-10868/) · [한국어](/ko/posts/boj-10868-range-minimum-query/)

各クエリでは 1 始まりの両端を含む区間 `[a, b]` が与えられ、その区間の最小値を求めます。反復型セグメント木では、`N` 個の値を `tree[N..2N)` に格納します。内部ノードは 2 つの子の最小値として下から順に構築します。このコンパクトな配列配置は、`N` が 2 のべき乗でなくても使えます。

クエリを 0 始まりの半開区間 `[a - 1, b)` に変換し、両端に `N` を加えて葉の添字にします。左端が右端より小さい間、`left` が右の子なら `tree[left]` を答えに反映して `left` を 1 増やします。右端が奇数なら `tree[right - 1]` を反映して `right` を 1 減らします。その後、両端を親に移して繰り返します。この境界判定により、求める区間をちょうど覆う互いに重ならないノードが選ばれます。答えを `Integer.MAX_VALUE` で初期化するため、要素 1 つだけの区間を含むすべての有効なクエリを処理できます。各クエリは `O(log N)` で処理されます。

木の構築に `O(N)`、`M` 個のクエリに `O(M log N)` かかるため、全体の時間計算量は `O(N + M log N)` です。木は整数 `2N` 個を保持するので、補助空間計算量は `O(N)` です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input =
                new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return sign * value;
        }

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int m = input.nextInt();
        int[] tree = new int[2 * n];

        for (int i = 0; i < n; i++) {
            tree[n + i] = input.nextInt();
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
        }

        BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(System.out));
        while (m-- > 0) {
            int left = input.nextInt() - 1 + n;
            int right = input.nextInt() + n;
            int minimum = Integer.MAX_VALUE;

            while (left < right) {
                if ((left & 1) != 0) {
                    minimum = Math.min(minimum, tree[left++]);
                }
                if ((right & 1) != 0) {
                    minimum = Math.min(minimum, tree[--right]);
                }
                left >>= 1;
                right >>= 1;
            }

            output.write(Integer.toString(minimum));
            output.newLine();
        }
        output.flush();
    }
}
```
