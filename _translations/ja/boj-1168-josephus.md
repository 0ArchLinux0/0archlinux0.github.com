---
title: BOJ. Josephus problem(2) (1168)
author: MINJUN PARK
date: 2022-01-20 00:36:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
		Segment Tree,
    BOJ,
    Josephus problem(2),
    요세푸스 문제 2,
		Important
  ]
pin: false
lang: ja
translation_key: boj-1168-josephus
permalink: /ja/posts/boj-1168-josephus/
---

[問題: BOJ 1168 — ヨセフス問題 2](https://www.acmicpc.net/problem/1168)

1番から`N`番まで番号の付いた人が円形に並んでいます。次の人から数えて`K`番目の人を順に取り除き、取り除いた順序を`<a, b, ...>`の形式で出力します。

## 生存者数を管理するFenwick tree

各位置には、その番号の人がまだ円にいれば1、取り除かれていれば0を保持します。Fenwick treeの接頭辞和から、その位置までの生存者数が分かります。この木を使うと、1人の削除と現在の生存者順位に対応する人の検索をそれぞれ`O(log N)`で行えます。

`rank`は次に数える人の、生存者内での0始まりの順位、`remaining`は生存者数です。このラウンドで取り除く人の順位は`(rank + K - 1) % remaining`です。Fenwick treeの二分探索で1始まりの順位`rank + 1`に対応する元の番号を求め、その位置の値を減らします。削除後、次の計数はその次の生存者から始まるため、新しい円での開始順位は`rank % (remaining - 1)`です。最後の1人を取り除くときはこの更新を行わず、0による剰余を避けます。`N = 1`なら順位0の1人だけが出力され、結果は`<1>`となります。

各人の削除ごとに順位検索と更新を行うため、時間計算量は`O(N log N)`、空間計算量は`O(N)`です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int k = input.nextInt();

        FenwickTree live = new FenwickTree(n);
        StringBuilder output = new StringBuilder("<");
        int rank = 0;

        for (int remaining = n; remaining > 0; remaining--) {
            rank = (int) ((rank + (long) k - 1) % remaining);
            int person = live.findByOrder(rank + 1);
            live.add(person, -1);
            if (remaining > 1) {
                rank %= remaining - 1;
            }

            if (output.length() > 1) {
                output.append(", ");
            }
            output.append(person);
        }
        output.append('>');
        System.out.print(output);
    }

    private static class FenwickTree {
        private final int size;
        private final int[] tree;
        private final int highestPowerOfTwo;

        FenwickTree(int size) {
            this.size = size;
            this.tree = new int[size + 1];
            for (int i = 1; i <= size; i++) {
                tree[i] = i & -i;
            }

            int power = 1;
            while (power <= size / 2) {
                power <<= 1;
            }
            highestPowerOfTwo = power;
        }

        void add(int index, int delta) {
            for (int i = index; i <= size; i += i & -i) {
                tree[i] += delta;
            }
        }

        int findByOrder(int order) {
            int index = 0;
            for (int step = highestPowerOfTwo; step > 0; step >>= 1) {
                int next = index + step;
                if (next <= size && tree[next] < order) {
                    index = next;
                    order -= tree[next];
                }
            }
            return index + 1;
        }
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
