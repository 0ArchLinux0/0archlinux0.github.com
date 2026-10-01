---
title: AtCoder ABC 235 D - Multiply and Rotate
author: MINJUN PARK
date: 2022-01-16 02:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC]
pin: false
lang: ja
translation_key: abc235-d-multiply-and-rotate
permalink: /ja/posts/abc235-d-multiply-and-rotate/
source_permalink: /posts/Atcoder-D-Multiply-and-Rotate/
---

[問題: AtCoder ABC 235 D — Multiply and Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_d) · [English](/posts/Atcoder-D-Multiply-and-Rotate/) · [한국어](/ko/posts/abc235-d-multiply-and-rotate/)

整数 `1` から始め、次のいずれかの操作を行います。現在の整数に `A` を掛けるか、最後の10進数字を先頭に移します。回転操作は、2桁以上で末尾の数字が0ではない場合だけ行えます。`N` に到達するための最小操作回数を求め、到達できなければ `-1` を出力します。たとえば `120` は回転できません。末尾の `0` を先頭に移して整数として読むと `12` になりますが、この操作は問題文で許可されていません。

各整数を頂点、可能な操作を辺とする重みなし有向グラフとして考えます。`1` から幅優先探索 (BFS) を行うと、操作回数の小さい状態から順に訪れるため、`N` に最初に設定される距離が最小操作回数です。`dist` 配列は距離の記録と再訪防止を兼ねます。制約 `A, N ≤ 10^6` のもとで、探索する状態は `1` から `10^6` までに限定します。積は `10^6` 以下の場合だけキューに追加し、探索中の整数を回転した値も `10^6` 以下です。そのため距離配列とキューには、それぞれ `10^6 + 1` 個の要素があれば十分です。各状態から出る操作は最大2つなので、限定した状態グラフでの時間計算量と空間計算量は `O(10^6)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final int LIMIT = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String[] values = input.readLine().split(" ");
        int a = Integer.parseInt(values[0]);
        int target = Integer.parseInt(values[1]);

        int[] distance = new int[LIMIT + 1];
        Arrays.fill(distance, -1);
        int[] queue = new int[LIMIT + 1];
        int head = 0;
        int tail = 0;

        distance[1] = 0;
        queue[tail++] = 1;

        while (head < tail) {
            int current = queue[head++];
            if (current == target) {
                System.out.println(distance[current]);
                return;
            }

            long product = (long) current * a;
            if (product <= LIMIT && distance[(int) product] == -1) {
                distance[(int) product] = distance[current] + 1;
                queue[tail++] = (int) product;
            }

            if (current >= 10 && current % 10 != 0) {
                int place = 1;
                while (place <= current / 10) {
                    place *= 10;
                }
                int rotated = current % 10 * place + current / 10;
                if (distance[rotated] == -1) {
                    distance[rotated] = distance[current] + 1;
                    queue[tail++] = rotated;
                }
            }
        }

        System.out.println(-1);
    }
}
```
