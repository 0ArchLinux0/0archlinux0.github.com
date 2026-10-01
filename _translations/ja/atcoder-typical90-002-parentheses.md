---
title: AtCoder. 002 括弧列の図鑑 (3)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Encyclopedia of Parentheses,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-002-parentheses
permalink: /ja/posts/atcoder-typical90-002-parentheses/
---

括弧列を左から順に作ります。どの接頭辞でも、閉じ括弧の数が開き括弧の数を超えてはいけません。そうなると、その後に何を追加しても正しい括弧列にはできません。また、長さ `N` の正しい括弧列には、開き括弧がちょうど `N/2` 個含まれます。

したがって、開き括弧を `N/2` 個より少なく使っている場合に `(` を追加し、`close < open` の場合に限って `)` を追加します。接頭辞で両者の数が等しくても、その後に開き括弧を追加できるため問題ありません。長さが `N` に達した時点で、これらの規則から列全体が正しい括弧列であることが保証されるので出力します。`(` を `)` より先に試すため、正しい括弧列は辞書順に生成されます。`N` が奇数の場合、最後に両者の数が等しくなることはないため、何も出力されません。

接頭辞のバランスに関する不変条件は `0 <= close <= open <= N/2` です。各結果は一度だけ生成され、時間計算量は出力全体のサイズに比例します。再帰呼び出しと現在の列に必要な作業領域は `O(N)` であり、バッファに保存する出力領域は別途必要です。

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_b)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int n;
    private static final StringBuilder current = new StringBuilder();
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        n = input.nextInt();
        generate(0, 0);
        System.out.print(output);
    }

    private static void generate(int open, int close) {
        if (current.length() == n) {
            if (open == close) {
                output.append(current).append('\n');
            }
            return;
        }

        if (open < n / 2) {
            current.append('(');
            generate(open + 1, close);
            current.setLength(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            generate(open, close + 1);
            current.setLength(current.length() - 1);
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
