---
title: AtCoder. 001 ようかんパーティー (4)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, AtCoder, Yokan Party, ようかんパーティー]
pin: false
lang: ja
translation_key: atcoder-typical90-001-yokan-party
permalink: /ja/posts/atcoder-typical90-001-yokan-party/
---

長さ `L` のようかんと、切ることのできる位置 `N` 個が与えられます。そのうちちょうど `K` 個を選んでようかんを `K + 1` 個のピースに分け、最も短いピースの長さを最大化します。

最小長の候補 `d` に対し、切断可能な位置を左から順に調べます。前回の切断位置（最初は `0`）からの距離が `d` 以上になったら切断し、`K` 回切断した時点で探索を終了します。各切断位置を可能な限り左に置くことで、その後のピースのための長さを最大限残します。そのため、どの `K` 個の選び方で条件を満たせる場合でも、この最も早い位置での貪欲な切断で条件を満たせます。候補が実現可能なのは、`K` 回切断でき、最後の切断位置から終点 `L` までの長さも `d` 以上の場合です。`K = 0` の場合は切断が不要なので、`L >= d` なら実現可能です。

実現可能性は単調です。最小長 `d` が実現可能なら、それより小さい正の長さもすべて実現可能です。よって `[0, L]` の範囲で実現可能な最大値を二分探索します。各判定には `O(N)` 時間かかるため、全体の時間計算量は `O(N log L)`、入力位置を保存する追加領域は `O(N)` です。

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_a)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int length = input.nextInt();
        int k = input.nextInt();

        int[] positions = new int[n];
        for (int i = 0; i < n; i++) {
            positions[i] = input.nextInt();
        }

        int low = 0;
        int high = length + 1;
        while (high - low > 1) {
            int middle = low + (high - low) / 2;
            if (canAchieve(middle, positions, k, length)) {
                low = middle;
            } else {
                high = middle;
            }
        }
        System.out.println(low);
    }

    private static boolean canAchieve(int minimumLength, int[] positions, int k, int length) {
        if (k == 0) {
            return length >= minimumLength;
        }

        int previousCut = 0;
        int cuts = 0;
        for (int position : positions) {
            if (position - previousCut >= minimumLength) {
                previousCut = position;
                if (++cuts == k) {
                    return length - previousCut >= minimumLength;
                }
            }
        }
        return false;
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
                if (length == -1) {
                    return -1;
                }
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
