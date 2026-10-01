---
title: BOJ 11279 - 最大ヒープ
author: MINJUN PARK
date: 2021-12-23 03:42:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, ヒープ, コーディング面接, BOJ, 最大ヒープ]
lang: ja
translation_key: boj-11279-max-heap
permalink: /ja/posts/boj-11279-max-heap/
pin: false
---

[BOJ 11279: 最大ヒープ](https://www.acmicpc.net/problem/11279)

最大ヒープでは、すべての親の値が子の値以上であるため、最大値は常に根にある。値を挿入するときは配列の末尾に追加し、親より大きい間、上へ移動させる。最大値を削除するときは最後の値を根に移し、より大きい子と交換しながら下へ移動させる。各操作で根から葉までの経路をたどるのは最大一度なので、挿入と削除の時間計算量はそれぞれ`O(log N)`である。プリミティブ型の配列を使うため、空間計算量は`O(N)`であり、配列の容量は挿入コマンドの最大数に合わせる。

入力は空白区切りのトークンとして読み取り、結果は`StringBuilder`にまとめて出力する。空のヒープから削除しようとした場合は、問題の仕様どおり`0`を出力する。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static BufferedReader reader =
      new BufferedReader(new InputStreamReader(System.in));
  private static StringTokenizer tokenizer;

  private static int nextInt() throws IOException {
    while (tokenizer == null || !tokenizer.hasMoreTokens()) {
      tokenizer = new StringTokenizer(reader.readLine());
    }
    return Integer.parseInt(tokenizer.nextToken());
  }

  public static void main(String[] args) throws IOException {
    int n = nextInt();
    int[] heap = new int[n + 1];
    int size = 0;
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < n; i++) {
      int value = nextInt();
      if (value == 0) {
        if (size == 0) {
          output.append(0).append('\n');
        } else {
          output.append(heap[1]).append('\n');
          heap[1] = heap[size--];

          int parent = 1;
          while (parent * 2 <= size) {
            int child = parent * 2;
            if (child + 1 <= size && heap[child + 1] > heap[child]) {
              child++;
            }
            if (heap[parent] >= heap[child]) {
              break;
            }
            int temp = heap[parent];
            heap[parent] = heap[child];
            heap[child] = temp;
            parent = child;
          }
        }
      } else {
        int child = ++size;
        while (child > 1 && heap[child / 2] < value) {
          heap[child] = heap[child / 2];
          child /= 2;
        }
        heap[child] = value;
      }
    }

    System.out.print(output);
  }
}
```
