---
title: Codeforces 1637B - MEX and Array
author: MINJUN PARK
date: 2022-02-13 11:30:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, Codeforces, Codeforces Global Round, MEX and Array, 数学]
pin: false
lang: ja
translation_key: cf-1637b-mex-and-array
permalink: /ja/posts/cf-1637b-mex-and-array/
source_permalink: /posts/Codeforces-Global-Round-19-B.-MEX-and-Array/
---

[問題: Codeforces 1637B — MEX and Array](https://codeforces.com/contest/1637/problem/B) · [한국어](/ko/posts/cf-1637b-mex-and-array/) · [English](/posts/Codeforces-Global-Round-19-B.-MEX-and-Array/)

配列を連続した空でない区間に分割します。分割のコストは区間数と各区間のMEXの合計であり、配列の値は可能な分割のうち最大のコストです。与えられた配列のすべての空でない部分配列について、その値の合計を求めます。

公式制約は `1 <= t <= 30`、`1 <= n <= 100`、`0 <= a[i] <= 10^9` で、全テストケースの `n` の合計は最大 `100` です。したがって要素は0でもよく、`n`より大きい値も取り得ます。

## 1つの部分配列の値

長さが `m` で、0を `z` 個含む部分配列の値はちょうど `m + z` です。

まず上限を示します。分割中のある区間の長さを `L`、0の個数を `q`、MEXを `x` とします。MEXが `x` なら、`0` から `x - 1` までの整数をすべて含みます。そのため要素数は少なくとも `x` 個であり、`x > 0` なら0も少なくとも1個含みます。よってどの場合も `1 + x <= L + q` です。この不等式を全区間について足すと、コストは配列全体の長さと0の個数の合計、すなわち `m + z` 以下です。

各要素を長さ1の区間に分ければこの上限を達成できます。0以外の要素のMEXは0なのでコストに1を加え、0のMEXは1なのでコストに2を加えます。合計コストは `m + z` となり、主張が証明されます。

## すべての部分配列の合計

長さ `k` の部分配列は `(n - k + 1)` 個あるため、すべての部分配列の長さの合計は

`sum(k * (n - k + 1), k = 1..n) = n * (n + 1) * (n + 2) / 6`

です。

0始まりの添字で位置 `i` にある0は、ちょうど `(i + 1) * (n - i)` 個の部分配列に含まれます。左端は `i` までの `i + 1` 個の位置から選び、右端は `i` から末尾までの `n - i` 個の位置から選べるためです。したがって答えは次の式です。

`n * (n + 1) * (n + 2) / 6 + a[i] == 0となるすべてのiについて (i + 1) * (n - i) の合計`

つまり、元の実装の式は公式問題の定義に対して正しいです。答えは `long` で計算し、`n <= 100` では十分な範囲内です。各テストケースの計算量は時間 `O(n)`、補助領域 `O(1)` です。

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
            long valueSum = (long) n * (n + 1) * (n + 2) / 6;
            for (int i = 0; i < n; i++) {
                if (fs.nextInt() == 0) {
                    valueSum += (long) (i + 1) * (n - i);
                }
            }
            answer.append(valueSum).append('\n');
        }
        System.out.print(answer);
    }
}
```
