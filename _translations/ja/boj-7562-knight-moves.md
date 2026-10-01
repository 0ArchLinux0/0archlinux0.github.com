---
title: BOJ. ナイトの移動 (7562)
author: MINJUN PARK
date: 2021-12-30 18:49:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, コーディング面接, BOJ, ナイトの移動]
pin: false
lang: ja
translation_key: boj-7562-knight-moves
permalink: /ja/posts/boj-7562-knight-moves/
---

[問題リンク](https://www.acmicpc.net/problem/7562)

各テストケースについて、`L × L` のチェス盤で開始マスから目標マスまでナイトが移動する最小回数を求めます。1回の移動では、一方の座標が1、もう一方の座標が2変化し、それぞれの符号は異なる場合があります。ナイトは盤の外へは移動できません。

盤上の各マスをグラフの頂点、合法なナイトの移動を辺として考えます。すべての辺のコストは等しいため、幅優先探索（BFS）は開始地点からの距離が小さい順にマスを訪問します。そのため、目標マスが初めて見つかったときの距離が最短移動回数です。距離配列は訪問済みの印も兼ねます。距離が `-1` のマスだけをキューに追加するので、各マスは高々1回しかキューに入りません。開始マスと目標マスが同じなら答えは0です。

入力スキャナーは改行に限らず任意の空白を処理し、プリミティブ型配列のキューをすべてのテストケースで再利用します。盤には `L²` 個のマスがあり、各マスから確認する移動は最大8通りなので、時間計算量は `O(L²)`、補助空間計算量は `O(L²)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int[] DR = {-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] DC = {-1, 1, -2, 2, -2, 2, -1, 1};

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner();
        int testCases = in.nextInt();
        int[] queue = new int[300 * 300];
        StringBuilder answer = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int length = in.nextInt();
            int startRow = in.nextInt();
            int startCol = in.nextInt();
            int targetRow = in.nextInt();
            int targetCol = in.nextInt();
            int[][] distance = new int[length][length];
            for (int row = 0; row < length; row++) {
                for (int col = 0; col < length; col++) {
                    distance[row][col] = -1;
                }
            }

            int head = 0;
            int tail = 0;
            int start = startRow * length + startCol;
            int target = targetRow * length + targetCol;
            distance[startRow][startCol] = 0;
            queue[tail++] = start;

            while (head < tail && distance[targetRow][targetCol] == -1) {
                int square = queue[head++];
                int row = square / length;
                int col = square % length;
                for (int move = 0; move < 8; move++) {
                    int nextRow = row + DR[move];
                    int nextCol = col + DC[move];
                    if (nextRow < 0 || nextRow >= length
                            || nextCol < 0 || nextCol >= length
                            || distance[nextRow][nextCol] != -1) {
                        continue;
                    }
                    distance[nextRow][nextCol] = distance[row][col] + 1;
                    queue[tail++] = nextRow * length + nextCol;
                }
            }
            answer.append(distance[targetRow][targetCol]).append('\n');
        }

        System.out.print(answer);
    }

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

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
