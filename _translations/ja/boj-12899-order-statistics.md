---
title: BOJ. Data Structure (12899)
author: MINJUN PARK
date: 2022-01-19 20:42:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
		Segment Tree,
    BOJ,
    Data Structure,
    데이터 구조
  ]
pin: false
lang: ja
translation_key: boj-12899-order-statistics
permalink: /ja/posts/boj-12899-order-statistics/
---

固定された値域 `[1, 2_000_000]` に挿入された値の多重集合を、フェニック木で管理します。`tree[i]` には最下位ビット `i & -i` によって定まる区間の頻度の合計を格納します。値を挿入するときはその頻度に 1 を加え、指定された順位の値を見つけて削除するときは 1 を引くため、同じ値も別々の要素として数えられます。

`k` 番目に小さい値を選ぶには、フェニック木の二分探索（二進リフティング）で、最も大きい 2 の冪から順に答えを組み立てます。各段階で候補となる接頭区間の頻度が残りの順位より小さい場合に限ってその区間を飛ばし、その頻度分だけ `k` を減らします。すべての段階を終えると、累積頻度が元の順位に初めて達する値のインデックスが得られます。重複する頻度があっても、最初と最後の有効な順位を正しく扱えます。挿入、選択、削除はそれぞれ `O(log V)` 時間で、`V = 2_000_000` です。木の使用領域は `O(V)` です。入力で操作回数が制限されているため、頻度は `int` に格納できます。

[問題リンク](https://www.acmicpc.net/problem/12899)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int MAX_VALUE = 2_000_000;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int operationCount = input.nextInt();
        FenwickTree frequencies = new FenwickTree(MAX_VALUE);
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < operationCount; i++) {
            int operation = input.nextInt();
            int valueOrRank = input.nextInt();
            if (operation == 1) {
                frequencies.add(valueOrRank, 1);
            } else {
                int value = frequencies.kth(valueOrRank);
                output.append(value).append('\n');
                frequencies.add(value, -1);
            }
        }
        System.out.print(output);
    }

    private static final class FenwickTree {
        private final int[] tree;

        FenwickTree(int size) {
            tree = new int[size + 1];
        }

        void add(int index, int delta) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i] += delta;
            }
        }

        int kth(int rank) {
            int index = 0;
            for (int step = Integer.highestOneBit(tree.length - 1); step != 0; step >>= 1) {
                int next = index + step;
                if (next < tree.length && tree[next] < rank) {
                    index = next;
                    rank -= tree[next];
                }
            }
            return index + 1;
        }
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
