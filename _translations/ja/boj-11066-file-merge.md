---
title: BOJ. ファイルの合併 (11066)
author: MINJUN PARK
date: 2022-01-05 12:20:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Merge Files, 파일 합치기]
pin: false
lang: ja
translation_key: boj-11066-file-merge
permalink: /ja/posts/boj-11066-file-merge/
---

[問題](https://www.acmicpc.net/problem/11066)

隣り合う2つのファイルを結合するコストは、2つのファイルサイズの合計です。結合後も順序は保たれるため、最後の結合位置で最適な手順を分割できます。まず `[i, k]` のファイルを1つにまとめ、次に `[k + 1, j]` を1つにまとめてから、その2つを結合します。最後の結合コストは区間 `[i, j]` の合計サイズです。

`dp[i][j]` を0始まりの添字で `i` 番目から `j` 番目までのファイルを結合する最小コストとし、`prefix` をファイルサイズの累積和とします。

```text
dp[i][i] = 0
dp[i][j] = prefix[j + 1] - prefix[i]
           + min(dp[i][k] + dp[k + 1][j]), i <= k < j
```

累積和により区間サイズは定数時間で求められます。すべての分割位置を調べると時間計算量は `O(K^3)` です。ファイルサイズが正であれば、区間コストは四角不等式（quadrangle inequality）を満たし、最適分割位置には次の単調性不変条件が成り立ちます。

```text
opt[i][j - 1] <= opt[i][j] <= opt[i + 1][j]
```

したがって `dp[i][j]` を求める際、分割位置を区間全体 `[i, j)` でなく `opt[i][j - 1]` から `opt[i + 1][j]` までに限定できます。`opt[i][i] = i` と初期化し、区間長の短い順に計算すれば、必要な隣接区間の最適分割位置は既に求まっています。各区間では最初に見つかった最小値を選び、最適分割位置の表を一貫させます。時間計算量は `O(K^2)`、`dp` と `opt` に必要な空間計算量は `O(K^2)`（累積和は `O(K)`）です。コストと累積和には `long` を使います。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int k = input.nextInt();
            long[] prefix = new long[k + 1];
            for (int i = 0; i < k; i++) {
                prefix[i + 1] = prefix[i] + input.nextInt();
            }

            long[][] dp = new long[k][k];
            int[][] opt = new int[k][k];
            for (int i = 0; i < k; i++) {
                opt[i][i] = i;
            }

            for (int length = 2; length <= k; length++) {
                for (int i = 0; i + length <= k; i++) {
                    int j = i + length - 1;
                    int firstSplit = opt[i][j - 1];
                    int lastSplit = opt[i + 1][j];
                    long intervalSize = prefix[j + 1] - prefix[i];
                    long best = Long.MAX_VALUE;
                    int bestSplit = firstSplit;

                    for (int split = firstSplit; split <= lastSplit; split++) {
                        long cost = dp[i][split] + dp[split + 1][j] + intervalSize;
                        if (cost < best) {
                            best = cost;
                            bestSplit = split;
                        }
                    }
                    dp[i][j] = best;
                    opt[i][j] = bestSplit;
                }
            }

            output.append(dp[0][k - 1]).append('\n');
        }

        System.out.print(output);
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
