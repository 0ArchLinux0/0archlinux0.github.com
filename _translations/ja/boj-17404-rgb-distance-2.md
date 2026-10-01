---
title: BOJ. RGB거리 2 (17404)
author: MINJUN PARK
date: 2022-02-07 02:30:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, RGB Distance 2, RGB거리 2]
pin: false
lang: ja
translation_key: boj-17404-rgb-distance-2
permalink: /ja/posts/boj-17404-rgb-distance-2/
source_permalink: /posts/BOJ-17404/
---

[問題: BOJ 17404 — RGB距離 2](https://www.acmicpc.net/problem/17404)

[English](/posts/BOJ-17404/) · [한국어](/ko/posts/boj-17404-rgb-distance-2/)

## 方針

各家は赤・緑・青のいずれかで塗り、隣り合う家は異なる色にする必要があります。家は円形につながっているため、最初の家と最後の家も隣同士です。この2軒も異なる色にします。

最初の家の色を1つに固定して、線形の動的計画法を実行します。`dp[c]`を、現在の家まで塗ったときに現在の家を色`c`にする最小費用とします。3つの状態を無限大で初期化し、固定した最初の色の状態だけを最初の家の塗装費用にします。次の家では、色`c`にする費用に、直前の家で別の2色を選んだ場合の最小費用を加えます。

`next[c] = cost[i][c] + min(dp[(c + 1) % 3], dp[(c + 2) % 3])`

すべての家を処理したら、固定した最初の色とは異なる最後の色だけを答えの候補にします。これにより円の最後と最初の隣接条件を満たします。最初の色を3通りすべて固定して実行し、その最小値を選びます。有効な塗り方は必ずこの3通りのどれかに含まれ、無効な塗り方は最後の色の判定で除外されます。

`N = 2`の場合も同じ規則で扱えます。円では2軒が互いに隣り合うため、異なる色にする必要があります。最後の色の判定が最初と同じ色を除外し、有効な組み合わせだけを残します。

DP状態には`long`を3個ずつ持つ2つのローリング配列を使います。入力の費用は配列に保持しますが、DP自体の追加領域は`O(1)`です。3回の実行を合わせた時間計算量は`O(N)`です。合計費用を`long`で計算し、十分小さい無限大値を使うことで加算時のオーバーフローを防ぎます。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[][] cost = new int[n][3];
        for (int house = 0; house < n; house++) {
            for (int color = 0; color < 3; color++) {
                cost[house][color] = input.nextInt();
            }
        }

        long answer = INF;
        for (int firstColor = 0; firstColor < 3; firstColor++) {
            long[] previous = {INF, INF, INF};
            long[] current = new long[3];
            previous[firstColor] = cost[0][firstColor];

            for (int house = 1; house < n; house++) {
                for (int color = 0; color < 3; color++) {
                    current[color] = cost[house][color]
                            + Math.min(previous[(color + 1) % 3], previous[(color + 2) % 3]);
                }
                long[] temp = previous;
                previous = current;
                current = temp;
            }

            for (int lastColor = 0; lastColor < 3; lastColor++) {
                if (lastColor != firstColor) {
                    answer = Math.min(answer, previous[lastColor]);
                }
            }
        }

        System.out.println(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```
