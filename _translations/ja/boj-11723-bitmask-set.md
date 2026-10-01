---
title: BOJ. 集合 (11723)
author: MINJUN PARK
date: 2022-01-26 04:59:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Bitmask,
    BOJ,
    Set,
    집합
  ]
pin: false
lang: ja
translation_key: boj-11723-bitmask-set
permalink: /ja/posts/boj-11723-bitmask-set/
source_permalink: /posts/BOJ-11723/
---

## ビットマスク

集合に含まれる整数は `1` から `20` までなので、1つの `int` で各要素の有無を表せます。値 `x` をビット位置 `x - 1` に対応させます。`1 << (x - 1)` はそのビットだけが1のマスクを作ります。シフト位置は0から始まるため、値から1を引きます。全要素を表すマスク `(1 << 20) - 1` は下位20ビットがすべて `1` で、`0` は空集合です。

`add` はマスクとの OR で要素を追加し、`remove` はマスクの反転との AND で要素を削除します。`check` は AND の結果が0以外かを調べ、`toggle` は XOR で該当ビットを反転します。`all` は全要素マスクを代入し、`empty` は集合を空にします。要素を指定するコマンドでは `1 <= x <= 20` が保証されます。要素がすでに希望する状態でも `add` と `remove` は安全です。各コマンドは時間 `O(1)`、集合は空間 `O(1)` を使います。

[問題リンク](https://www.acmicpc.net/problem/11723)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int FULL_SET = (1 << 20) - 1;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int commandCount = input.nextInt();
        int set = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < commandCount; i++) {
            String command = input.next();
            if (command.equals("all")) {
                set = FULL_SET;
            } else if (command.equals("empty")) {
                set = 0;
            } else {
                int x = input.nextInt();
                int mask = 1 << (x - 1);
                switch (command) {
                    case "add":
                        set |= mask;
                        break;
                    case "remove":
                        set &= ~mask;
                        break;
                    case "check":
                        output.append((set & mask) != 0 ? 1 : 0).append('\n');
                        break;
                    case "toggle":
                        set ^= mask;
                        break;
                }
            }
        }

        System.out.print(output);
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            StringBuilder token = new StringBuilder();
            while (c > ' ') {
                token.append((char) c);
                c = read();
            }
            return token.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
