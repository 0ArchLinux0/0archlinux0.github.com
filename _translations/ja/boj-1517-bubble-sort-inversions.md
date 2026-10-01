---
title: BOJ 1517 — バブルソート
author: MINJUN PARK
date: 2022-01-08 08:20:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Bubble Sort, Inversion, バブルソート]
pin: false
lang: ja
translation_key: boj-1517-bubble-sort-inversions
permalink: /ja/posts/boj-1517-bubble-sort-inversions/
---

[問題リンク](https://www.acmicpc.net/problem/1517)

`i < j` かつ `A[i] > A[j]` となる添字の組 `(i, j)` を転倒（inversion）と呼びます。バブルソートは、順序が逆になっている隣接要素を交換します。一度交換された組は正しい順序になり、再び交換されることはありません。すべての転倒が1回ずつ取り除かれるため、バブルソートの交換回数は転倒数と等しくなります。

マージソートを使えば、交換を実際に行わずに転倒数を数えられます。左右の区間をそれぞれソートしてからマージするとき、現在の右側の値が左側の値より小さければ、その右側の値は左側に残っているすべての値より小さいことになります。よって残っている左側の要素数を答えに加えてから、右側の値をコピーします。値が等しい場合は左側を先に選びます。同じ値は転倒ではないためです。再帰呼び出しで各半区間内の転倒を数え、マージで左右の半区間をまたぐ転倒を数えます。

時間計算量は `O(N log N)`、入力配列とマージ用バッファの空間計算量は `O(N)` です。転倒数の最大値は `N(N - 1) / 2` なので、答えには `long` 型を使います。

```java
import java.io.*;

public class Main {
  static class FastScanner {
    private final InputStream input;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

    FastScanner(InputStream input) {
      this.input = input;
    }

    private int read() throws IOException {
      if (pointer == length) {
        length = input.read(buffer);
        pointer = 0;
        if (length == -1) return -1;
      }
      return buffer[pointer++];
    }

    int nextInt() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);

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
  }

  static int[] values;
  static int[] buffer;

  static long sortAndCount(int left, int right) {
    if (right - left <= 1) return 0;

    int middle = left + (right - left) / 2;
    long count = sortAndCount(left, middle) + sortAndCount(middle, right);

    int i = left;
    int j = middle;
    int out = left;
    while (i < middle && j < right) {
      if (values[i] <= values[j]) {
        buffer[out++] = values[i++];
      } else {
        buffer[out++] = values[j++];
        count += middle - i;
      }
    }
    while (i < middle) buffer[out++] = values[i++];
    while (j < right) buffer[out++] = values[j++];
    System.arraycopy(buffer, left, values, left, right - left);

    return count;
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    values = new int[n];
    buffer = new int[n];
    for (int i = 0; i < n; i++) values[i] = input.nextInt();

    System.out.println(sortAndCount(0, n));
  }
}
```
