---
title: BOJ. トマト (7569)
author: MINJUN PARK
date: 2021-12-26 10:37:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, ヒープ, コーディング面接, BOJ, トマト]
pin: false
lang: ja
translation_key: boj-7569-tomato
permalink: /ja/posts/boj-7569-tomato/
---

[問題ページ](https://www.acmicpc.net/problem/7569)

トマトの箱は3次元の格子です。熟したトマトは、軸に沿った6方向に隣接する未熟なトマトを熟させます。この変化は1日ごとに同時に起こります。未熟なトマトがすべて熟すまでの日数を求め、熟すことのできないトマトが残る場合は `-1` を出力します。

複数の始点を使う幅優先探索（BFS）を行います。探索を始める前に、最初から熟しているトマトをすべてキューに入れます。キューには各マスを平坦化した整数インデックスを格納し、マスの総数分のプリミティブな `int[]` を使います。未熟なマスが熟した状態に変わったときだけキューに追加するため、各マスが追加されるのは最大1回です。

格子の値を日数ラベルとしても使います。最初から熟しているマスは `1` とし、新たに熟したマスには直前のマスの値に1を加えた値を設定します。そのためBFSの各層は、1日の間に同時に起こる変化を表します。入力時に未熟なトマトの数を数え、熟すたびにその数を減らします。最初から未熟なトマトがなければ答えは `0` です。キューが空になるまで探索しても未熟なトマトが残っていれば `-1`、そうでなければ格子内の最大値から1を引いた値が経過日数です。

入力リーダーは行の区切りに依存せず、空白区切りの整数を読み取ります。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int m, n, h;
    private static final int[] DZ = {1, -1, 0, 0, 0, 0};
    private static final int[] DY = {0, 0, 1, -1, 0, 0};
    private static final int[] DX = {0, 0, 0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner();
        m = in.nextInt();
        n = in.nextInt();
        h = in.nextInt();

        int volume = m * n * h;
        int[][][] box = new int[h][n][m];
        int[] queue = new int[volume];
        int tail = 0;
        int unripe = 0;

        for (int z = 0; z < h; z++) {
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < m; x++) {
                    int value = in.nextInt();
                    box[z][y][x] = value;
                    if (value == 1) {
                        queue[tail++] = (z * n + y) * m + x;
                    } else if (value == 0) {
                        unripe++;
                    }
                }
            }
        }

        System.out.println(ripeningDays(box, queue, tail, unripe));
    }

    private static int ripeningDays(int[][][] box, int[] queue, int tail, int unripe) {
        if (unripe == 0) {
            return 0;
        }

        int maxDay = 1;
        for (int head = 0; head < tail; head++) {
            int index = queue[head];
            int z = index / (n * m);
            int remainder = index % (n * m);
            int y = remainder / m;
            int x = remainder % m;
            int nextDay = box[z][y][x] + 1;

            for (int direction = 0; direction < 6; direction++) {
                int nz = z + DZ[direction];
                int ny = y + DY[direction];
                int nx = x + DX[direction];
                if (nz < 0 || nz >= h || ny < 0 || ny >= n || nx < 0 || nx >= m
                        || box[nz][ny][nx] != 0) {
                    continue;
                }

                box[nz][ny][nx] = nextDay;
                maxDay = nextDay;
                unripe--;
                queue[tail++] = (nz * n + ny) * m + nx;
            }
        }

        return unripe == 0 ? maxDay - 1 : -1;
    }

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer, length;

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

各マスは最大1回だけキューに入り、隣接マスを最大6回確認するため、実行時間は `O(MNH)` です。格子とプリミティブキューはそれぞれ `O(MNH)` の空間を使います。
