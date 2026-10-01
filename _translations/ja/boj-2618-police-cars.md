---
title: BOJ. 警察車両 (2618)
author: MINJUN PARK
date: 2022-01-18 19:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Dynamic Programming, BOJ, Police Car, 경찰차, Review]
pin: false
lang: ja
translation_key: boj-2618-police-cars
permalink: /ja/posts/boj-2618-police-cars/
source_permalink: /posts/BOJ-2618/
---

[BOJ 2618: 警察車両](https://www.acmicpc.net/problem/2618)

## 2台の警察車両が最後に担当した事件による動的計画法

`dp[a][b]` を、警察車両1が最後に担当した事件番号が `a`、警察車両2が最後に担当した事件番号が `b` のとき、残りの事件をすべて担当する最小移動距離とします。`0` はまだ事件を担当していないことを表します。そのため `a = 0` の車両1の位置は `(1, 1)`、`b = 0` の車両2の位置は `(N, N)` で、それ以外は該当する事件の位置です。

両車両が合わせて `max(a, b)` 番目まで担当しているため、次の事件は常に `max(a, b) + 1` です。この事件を車両1または2に割り当てる2通りだけを考えます。各場合の移動距離は、現在位置から次の事件までのマンハッタン距離です。

```text
dp[a][b] = 0                                            if max(a, b) = W
next = max(a, b) + 1

dp[a][b] = min(
    distance(car 1 position(a), incident[next]) + dp[next][b],
    distance(car 2 position(b), incident[next]) + dp[a][next]
)
```

各遷移で次の事件を1つ担当するため、再帰状態は終了状態に到達します。2つの割り当てのうち小さい方を選べば最適性が保たれ、メモ化により各 `(a, b)` 状態は一度だけ計算されます。答えを求めた後、同じ2つの費用を比較して各事件の担当車両を出力します。費用が同じ場合はどちらを選んでも最適で、このコードでは車両1を選択します。`W = 0` のとき開始状態が終了状態なので、費用 `0` のみを出力し、担当車両の行はありません。時間計算量、空間計算量はいずれも `O(W²)` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int n;
    private static int w;
    private static int[][] incidents;
    private static int[][] memo;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        n = input.nextInt();
        w = input.nextInt();
        incidents = new int[w + 1][2];
        for (int i = 1; i <= w; i++) {
            incidents[i][0] = input.nextInt();
            incidents[i][1] = input.nextInt();
        }

        memo = new int[w + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            for (int j = 0; j <= w; j++) {
                memo[i][j] = -1;
            }
        }

        StringBuilder output = new StringBuilder();
        output.append(minimumDistance(0, 0)).append('\n');
        int car1Last = 0;
        int car2Last = 0;
        while (Math.max(car1Last, car2Last) < w) {
            int next = Math.max(car1Last, car2Last) + 1;
            int car1Cost = distance(1, car1Last, next)
                    + minimumDistance(next, car2Last);
            int car2Cost = distance(2, car2Last, next)
                    + minimumDistance(car1Last, next);

            if (car1Cost <= car2Cost) {
                output.append(1).append('\n');
                car1Last = next;
            } else {
                output.append(2).append('\n');
                car2Last = next;
            }
        }
        System.out.print(output);
    }

    private static int minimumDistance(int car1Last, int car2Last) {
        if (Math.max(car1Last, car2Last) == w) return 0;
        if (memo[car1Last][car2Last] != -1) return memo[car1Last][car2Last];

        int next = Math.max(car1Last, car2Last) + 1;
        int assignToCar1 = distance(1, car1Last, next)
                + minimumDistance(next, car2Last);
        int assignToCar2 = distance(2, car2Last, next)
                + minimumDistance(car1Last, next);
        memo[car1Last][car2Last] = Math.min(assignToCar1, assignToCar2);
        return memo[car1Last][car2Last];
    }

    private static int distance(int car, int lastIncident, int nextIncident) {
        int startRow = lastIncident == 0 ? (car == 1 ? 1 : n) : incidents[lastIncident][0];
        int startColumn = lastIncident == 0 ? (car == 1 ? 1 : n) : incidents[lastIncident][1];
        return Math.abs(startRow - incidents[nextIncident][0])
                + Math.abs(startColumn - incidents[nextIncident][1]);
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
