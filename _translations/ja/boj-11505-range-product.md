---
title: BOJ. 区間積を求める (11505)
author: MINJUN PARK
date: 2021-12-31 23:13:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, Segment Tree, BOJ, Prefix Product, 구간 곱 구하기]
pin: false
lang: ja
translation_key: boj-11505-range-product
permalink: /ja/posts/boj-11505-range-product/
---

[問題](https://www.acmicpc.net/problem/11505)

配列の1要素を更新しながら、任意の閉区間の積を `1,000,000,007` で割った余りとして求めます。更新で変化するのは1か所だけなので、反復型セグメント木ではその葉と祖先だけを再計算します。各内部ノードには、2つの子の積を `MOD` で割った値を格納します。

`n` 個の葉を `[n, 2n)` に置きます。位置 `p` を更新するときは葉 `p + n` を新しい値で置き換え、親へ上がりながら各ノードを再計算します。クエリ区間 `[left, right]` は半開区間 `[left + n, right + n + 1)` に変換します。各段階で、左端が右の子ならそのノードを左側の積に加え、右端が奇数ならその直前のノードを右側の積に加えてから、両端を親へ移動します。これによりクエリ区間を構成するノードだけを訪れ、両端をそれぞれ一度だけ含められます。

`MOD` における乗法の単位元は `1` なので、左右の積の初期値を `1` にします。これにより1要素だけの区間も、0を含む区間も正しく扱えます。更新では新しい値を葉へ直接格納するため、値が `0` でも除算や特別な0管理は不要です。`MOD` 未満の値同士の積は32ビット整数の範囲を超える可能性があるため、乗算前に一方を `long` に変換します。木の構築は `O(N)`、一点更新と区間クエリはそれぞれ `O(log N)`、空間計算量は `O(N)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int updates = input.nextInt();
        int queries = input.nextInt();
        int operationCount = updates + queries;

        int[] tree = new int[2 * n];
        for (int i = 0; i < n; i++) {
            tree[n + i] = input.nextInt();
        }
        for (int node = n - 1; node > 0; node--) {
            tree[node] = multiply(tree[node << 1], tree[node << 1 | 1]);
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < operationCount; i++) {
            int type = input.nextInt();
            int b = input.nextInt();
            int c = input.nextInt();
            if (type == 1) {
                set(tree, n, b - 1, c);
            } else {
                output.append(product(tree, n, b - 1, c)).append('\n');
            }
        }
        System.out.print(output);
    }

    private static int multiply(int a, int b) {
        return (int) ((long) a * b % MOD);
    }

    private static void set(int[] tree, int n, int index, int value) {
        int node = n + index;
        tree[node] = value;
        while ((node >>= 1) > 0) {
            tree[node] = multiply(tree[node << 1], tree[node << 1 | 1]);
        }
    }

    // Returns the product on the zero-based inclusive interval [left, right].
    private static int product(int[] tree, int n, int left, int right) {
        int l = left + n;
        int r = right + n + 1;
        int leftProduct = 1;
        int rightProduct = 1;
        while (l < r) {
            if ((l & 1) != 0) leftProduct = multiply(leftProduct, tree[l++]);
            if ((r & 1) != 0) rightProduct = multiply(tree[--r], rightProduct);
            l >>= 1;
            r >>= 1;
        }
        return multiply(leftProduct, rightProduct);
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
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
}
```
