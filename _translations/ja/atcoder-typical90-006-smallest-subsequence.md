---
title: AtCoder Typical 90 006 — Smallest Subsequence (5)
author: MINJUN PARK
date: 2021-12-30 02:45:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Smallest Subsequence,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-006-smallest-subsequence
permalink: /ja/posts/atcoder-typical90-006-smallest-subsequence/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_f)

`S` の文字の順序を保ったままちょうど `K` 文字を選び、辞書順で最小の部分列を作ります。

ちょうど `N - K` 文字を削除できます。`S` を左から走査し、選んだ文字をスタックに保持します。現在の文字がスタック末尾の文字より小さく、まだ削除できる文字数が残っている間は、末尾の文字を削除します。これは辞書順に関する交換です。前の位置にある大きい文字を現在の小さい文字に置き換えると結果は小さくなり、削除した文字がそれ以降の接頭辞を改善することはありません。条件を満たす間は削除を続け、その後で現在の文字をスタックに追加します。

削除できる残り回数の管理が重要です。`N - K` 文字を削除した後は、それ以上削除できません。走査後に削除回数が残っている場合は、スタックの末尾から残りの文字数だけ削除します。これによりスタックにはちょうど `K` 文字が残ります。各文字は一度追加され、削除されるのは最大一度なので、時間計算量と空間計算量はいずれも `O(N)` です。

```java
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String[] firstLine = input.readLine().trim().split("\\s+");
    int n = Integer.parseInt(firstLine[0]);
    int k = Integer.parseInt(firstLine[1]);
    String s = input.readLine().trim();

    char[] stack = new char[n];
    int size = 0;
    int removalsLeft = n - k;

    for (int i = 0; i < n; i++) {
      char current = s.charAt(i);
      while (removalsLeft > 0 && size > 0 && stack[size - 1] > current) {
        size--;
        removalsLeft--;
      }
      stack[size++] = current;
    }

    size -= removalsLeft;
    System.out.println(new String(stack, 0, size));
  }
}
```
