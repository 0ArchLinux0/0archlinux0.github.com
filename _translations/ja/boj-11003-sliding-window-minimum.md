---
title: BOJ. 最小値 (11003)
author: MINJUN PARK
date: 2022-02-09 17:40:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Deque, Sliding Window, 最小値]
pin: false
lang: ja
translation_key: boj-11003-sliding-window-minimum
permalink: /ja/posts/boj-11003-sliding-window-minimum/
source_permalink: /posts/BOJ-11003/
---

[問題: BOJ 11003 — 最小値](https://www.acmicpc.net/problem/11003)

[English](/posts/BOJ-11003/) · [한국어](/ko/posts/boj-11003-sliding-window-minimum/)

## 方針

各位置 `i` で求める区間は `[max(0, i - L + 1), i]` です。そのため最初の `L - 1` 個の区間には、そこまでに入力された値だけが含まれ、存在しない要素で埋めることはありません。

候補のインデックスを単調増加する順にデックへ格納し、値は別のプリミティブ配列に保持します。`A[i]`を追加する前に、現在の区間から外れたインデックス `<= i - L` をデックの先頭から削除します。次に、末尾から値が`A[i]`以上の候補を取り除きます。新しい値はそれら以下であり、区間内にもより長く残るため、取り除いた候補が後の最小値になることはありません。したがって、デック先頭の値が現在の区間の最小値です。同じ値も末尾から削除して問題なく、より新しい候補だけを残せます。

各インデックスは一度挿入され、高々一度削除されるため、全体の時間計算量は償却`O(N)`です。入力はバイトを直接読むスキャナーで処理し、デックは2つの`int[]`配列で実装するため、数値ごとの文字列や要素オブジェクトを作りません。配列と出力バッファの空間計算量は`O(N)`です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int windowLength = input.nextInt();
        int[] indices = new int[n];
        int[] values = new int[n];
        int front = 0;
        int back = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int value = input.nextInt();

            while (front < back && indices[front] <= i - windowLength) {
                front++;
            }
            while (front < back && values[back - 1] >= value) {
                back--;
            }

            indices[back] = i;
            values[back] = value;
            back++;

            if (i > 0) {
                output.append(' ');
            }
            output.append(values[front]);
        }

        System.out.println(output);
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

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
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
