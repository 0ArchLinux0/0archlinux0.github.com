---
title: BOJ 5419 - 北西風
author: MINJUN PARK
date: 2022-02-17 10:57:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, スイープ, Fenwick tree, 北西風]
pin: false
lang: ja
translation_key: boj-5419-northwest-wind
permalink: /ja/posts/boj-5419-northwest-wind/
source_permalink: /posts/BOJ-5419/
---

[問題: BOJ 5419 — 北西風](https://www.acmicpc.net/problem/5419) · [English](/posts/BOJ-5419/) · [한국어](/ko/posts/boj-5419-northwest-wind/)

各テストケースで、`x1 <= x2` かつ `y1 >= y2` を満たす点の組 `(x1, y1)`、`(x2, y2)` の数を数えます。左から右へのスイープ順で各組を一度だけ数えます。そのため、x 座標が同じ点は y 座標の大きい順に処理します。また、座標が完全に同じ点も入力中の別々の点なので、同じ座標のコピーを 2 つ選ぶごとに 1 組として数えます。

点を x 座標の昇順、次に y 座標の降順でソートします。これにより現在の点を処理するとき、すでに処理したすべての点は x 座標が現在の点以下です。x 座標が同じ場合は y 座標の大きい点が先に処理されるので、先行する点の y 座標も現在の点以上になります。この順序なら、同じ x 座標の点を別途まとめずに条件を満たし、重複点も正しく数えられます。

y 座標をすべて `1..K` の順位に昇順で座標圧縮します。Fenwick tree には各順位に属する処理済みの点の個数を記録します。現在の順位を `r` とすると、`r` から `K` までの区間和を求めることで、y 座標が現在の点以上である先行点の数が分かります。その個数を `long` 型の答えに加えてから、現在の点を木に追加します。追加前に問い合わせるため、点自身とは組になりません。

ソートと座標圧縮に `O(N log N)`、各点の Fenwick tree の問い合わせと更新にもそれぞれ `O(log N)` かかるため、全体の時間計算量は `O(N log N)` です。補助空間計算量は `O(N)` です。最大で `N(N - 1) / 2` 組を数える可能性があるため、答えには `long` を使います。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    private static final class Point {
        final int x;
        final int y;
        int yRank;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final class FenwickTree {
        private final int[] tree;

        FenwickTree(int size) {
            tree = new int[size + 1];
        }

        void add(int index) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i]++;
            }
        }

        int prefixSum(int index) {
            int sum = 0;
            for (int i = index; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }
    }

    private static final class FastScanner {
        private final BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens;

        int nextInt() throws IOException {
            while (tokens == null || !tokens.hasMoreTokens()) {
                tokens = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokens.nextToken());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            Point[] points = new Point[n];
            int[] ys = new int[n];
            for (int i = 0; i < n; i++) {
                int x = input.nextInt();
                int y = input.nextInt();
                points[i] = new Point(x, y);
                ys[i] = y;
            }

            Arrays.sort(ys);
            int uniqueCount = 0;
            for (int y : ys) {
                if (uniqueCount == 0 || ys[uniqueCount - 1] != y) {
                    ys[uniqueCount++] = y;
                }
            }

            for (Point point : points) {
                point.yRank = Arrays.binarySearch(ys, 0, uniqueCount, point.y) + 1;
            }

            Arrays.sort(points, Comparator
                    .comparingInt((Point point) -> point.x)
                    .thenComparing(Comparator.comparingInt((Point point) -> point.y).reversed()));

            FenwickTree fenwick = new FenwickTree(uniqueCount);
            long answer = 0;
            for (Point point : points) {
                int lessThanY = fenwick.prefixSum(point.yRank - 1);
                int atLeastY = fenwick.prefixSum(uniqueCount) - lessThanY;
                answer += atLeastY;
                fenwick.add(point.yRank);
            }
            output.append(answer).append('\n');
        }
        System.out.print(output);
    }
}
```
