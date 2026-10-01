---
title: BOJ 15927 - 回文は回文ではない
author: MINJUN PARK
date: 2022-03-09 01:07:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 文字列, 回文]
pin: false
lang: ja
translation_key: boj-15927-non-palindrome
permalink: /ja/posts/boj-15927-non-palindrome/
---

[問題ページ](https://www.acmicpc.net/problem/15927)

## 着眼点

文字列 `S` の部分文字列のうち、回文ではないものの最大長を求める。`S` 自体が回文でなければ、文字列全体が答えなので `N`。`S` が回文で、すべての文字が同じなら、すべての部分文字列も回文なので `-1`。それ以外、つまり回文だが文字がすべて同じではない場合の答えは `N - 1` である。

## 証明

文字列全体が回文でなければ、`S` が長さ `N` の回文ではない部分文字列なので答えは `N`。全体が回文で、すべての文字が同じなら、どの部分文字列も同じ文字だけで構成されるため回文となり、答えは `-1` である。

次に、`S` は回文だが文字がすべて同じではないとする。長さ `N` の部分文字列は `S` 自身しかないため、それより長い回文ではない部分文字列は存在しない。また長さ `N - 1` の部分文字列は、先頭または末尾の文字を取り除いた2つだけである。`S` は回文なので、この2つは互いに逆順であり、一方が回文ならもう一方も回文となる。

両方が回文だと仮定すると、特に末尾を取り除いた接頭辞も回文である。文字列全体が回文であることから `S[i] = S[N - 1 - i]`、接頭辞が回文であることから `S[i] = S[N - 2 - i]` が成り立つ。したがって `i = 0, …, N - 2` について `S[N - 1 - i] = S[N - 2 - i]` となり、すべての隣り合う文字が等しい。これは全ての文字が同じではないという仮定に矛盾する。よって長さ `N - 1` の2つの部分文字列は回文ではなく、答えは `N - 1` である。

文字列を1回走査し、回文かどうかと全ての文字が同じかどうかを調べる。時間計算量は `O(N)`、入力文字列以外の補助空間計算量は `O(1)` である。

## C++17実装

```cpp
#include <iostream>
#include <string>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string s;
    cin >> s;

    const int n = static_cast<int>(s.size());
    bool isPalindrome = true;
    bool allSame = true;

    for (int i = 0; i < n; ++i) {
        if (s[i] != s[n - 1 - i]) isPalindrome = false;
        if (s[i] != s[0]) allSame = false;
    }

    if (!isPalindrome) cout << n << '\n';
    else if (allSame) cout << -1 << '\n';
    else cout << n - 1 << '\n';

    return 0;
}
```
