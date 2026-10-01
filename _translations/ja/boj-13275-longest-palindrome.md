---
title: BOJ 13275 - 最長回文部分文字列
author: MINJUN PARK
date: 2022-02-22 18:13:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, Manacherアルゴリズム, 文字列]
pin: false
lang: ja
translation_key: boj-13275-longest-palindrome
permalink: /ja/posts/boj-13275-longest-palindrome/
source_permalink: /posts/BOJ-13275/
---

[問題: BOJ 13275 — 最長回文部分文字列](https://www.acmicpc.net/problem/13275) · [한국어](/ko/posts/boj-13275-longest-palindrome/) · [English](/posts/BOJ-13275/)

Manacher アルゴリズムでは、考えられる各中心について回文の半径を記録します。奇数長の回文の中心は1文字です。`radiusOdd[i]` は中心の文字を含む半径なので、回文の長さは `2 * radiusOdd[i] - 1` です。偶数長の回文の中心は `i` の直前にある隙間です。`radiusEven[i]` はその隙間を中心に一致する文字ペアの数であり、回文の長さは `2 * radiusEven[i]` です。

奇数中心と偶数中心をそれぞれ処理し、これまでに見つかった最も右側の回文区間 `[left, right]` を保持します。次の中心 `i` がこの区間内にある場合、区間の中心に対して対称な位置を探し、そこで計算済みの半径を再利用します。ただし、既知の回文の内側に確実に収まる範囲である `right - i + 1` までしか再利用できません。`i` が区間の外側なら、最小の半径（奇数中心では中心の1文字、偶数中心ではペア0個）から始めます。現在の半径のすぐ外側にある文字を比較し、一致する間は拡張します。拡張した回文が既存区間より右に伸びたら、`[left, right]` を更新します。

拡張に成功するたびに右端が前進するため、全体の実行時間は線形です。答えは、奇数長と偶数長の回文のうち最も長いものです。時間計算量は `O(N)`、空間計算量は `O(N)` で、長さ `10^6` 以下の文字列を処理できます。

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  string s;
  cin >> s;
  const int n = static_cast<int>(s.size());

  vector<int> radiusOdd(n);
  vector<int> radiusEven(n);
  int longest = 1;

  int left = 0;
  int right = -1;
  for (int i = 0; i < n; ++i) {
    int radius = (i > right)
                     ? 1
                     : min(radiusOdd[left + right - i], right - i + 1);
    while (i - radius >= 0 && i + radius < n &&
           s[i - radius] == s[i + radius]) {
      ++radius;
    }
    radiusOdd[i] = radius;
    longest = max(longest, 2 * radius - 1);

    if (i + radius - 1 > right) {
      left = i - radius + 1;
      right = i + radius - 1;
    }
  }

  left = 0;
  right = -1;
  for (int i = 0; i < n; ++i) {
    int radius = (i > right)
                     ? 0
                     : min(radiusEven[left + right - i + 1], right - i + 1);
    while (i - radius - 1 >= 0 && i + radius < n &&
           s[i - radius - 1] == s[i + radius]) {
      ++radius;
    }
    radiusEven[i] = radius;
    longest = max(longest, 2 * radius);

    if (i + radius - 1 > right) {
      left = i - radius;
      right = i + radius - 1;
    }
  }

  cout << longest << '\n';
}
```
