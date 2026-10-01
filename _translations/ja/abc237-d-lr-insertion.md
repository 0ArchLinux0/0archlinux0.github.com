---
title: AtCoder ABC 237 D — LR の挿入
author: MINJUN PARK
date: 2022-01-30 21:50:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 237]
pin: false
lang: ja
translation_key: abc237-d-lr-insertion
permalink: /ja/posts/abc237-d-lr-insertion/
source_permalink: /posts/Atcoder-D-LR-insertion/
---

[問題: AtCoder ABC 237 D — LR insertion](https://atcoder.jp/contests/abc237/tasks/abc237_d) · [English](/posts/Atcoder-D-LR-insertion/) · [한국어](/ko/posts/abc237-d-lr-insertion/)

最大 500,000 個のノードを再帰で中順巡回すると、呼び出しスタックがあふれる可能性があります。代わりに、両端キュー（deque）で答えを直接構築します。まず `N` を入れ、`S` を右から左へ処理します。各インデックス `i` について、`S[i]` が `L` なら `i` を deque の末尾に追加し、そうでなければ先頭に追加します。処理後の deque が求める順序になります。

これは挿入を逆順にたどる方法です。最後に挿入される値は `N` なので、これを deque の初期値にします。それより前の各 `i` について、`L` は `i + 1` が `i` の直前に挿入されたことを意味するため、`i` は末尾に置きます。`R` は `i + 1` が `i` の直後に挿入されたことを意味するため、`i` は先頭に置きます。各操作で対応する端に要素を一つ追加し、必要な相対順序を保ちます。各値を一度ずつ追加するので、時間計算量と空間計算量はいずれも `O(N)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        String s = input.readLine().trim();

        Deque<Integer> order = new ArrayDeque<>();
        order.addLast(n);
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == 'L') {
                order.addLast(i);
            } else {
                order.addFirst(i);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int value : order) {
            if (answer.length() > 0) {
                answer.append(' ');
            }
            answer.append(value);
        }
        System.out.println(answer);
    }
}
```
