---
title: BOJ 1725 - ヒストグラム
author: MINJUN PARK
date: 2022-02-18 12:33:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, ヒストグラム, 単調スタック]
pin: false
lang: ja
translation_key: boj-1725-largest-rectangle
permalink: /ja/posts/boj-1725-largest-rectangle/
source_permalink: /posts/BOJ-1725/
---

[問題: BOJ 1725 — ヒストグラム](https://www.acmicpc.net/problem/1725) · [English](/posts/BOJ-1725/) · [한국어](/ko/posts/boj-1725-largest-rectangle/)

各棒を高さとする長方形のうち最も幅広いものは、その棒が区間内で最も低い棒となる範囲にあります。その範囲の左右の境界は、対象の棒より厳密に低い最も近い棒です。左から単調スタックで走査すれば、低い棒に出会った時点で境界を確定できます。

スタックには高さが非減少となるよう棒のインデックスを格納します。現在の棒がスタック頂上の棒より低い場合、頂上のインデックスはそれ以上右へ広がれません。現在のインデックスが、その棒から見て右側で最初に現れる低い棒だからです。これを取り出した後の新しい頂上は、左側で最も近い低い棒です。したがって長方形の幅は `i - left - 1` で、スタックが空なら幅は `i` です。同じ高さの棒も取り出すため、残ったインデックスの左隣はより低くなり、同じ高さも一貫して処理できます。

入力された棒をすべて処理した後、高さ `0` の番兵を使って残りのインデックスをすべて取り出します。再帰呼び出しは不要です。各インデックスは一度追加され、一度取り出されるため、時間計算量は `O(N)`、補助空間計算量は `O(N)` です。面積の乗算が安全に行えるよう、高さと面積には `long long` を使います。

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<long long> height(n);
  for (long long &bar : height) cin >> bar;

  vector<int> st;
  st.reserve(n);
  long long answer = 0;

  for (int i = 0; i <= n; ++i) {
    const long long current = (i == n ? 0 : height[i]);
    while (!st.empty() && height[st.back()] >= current) {
      const long long barHeight = height[st.back()];
      st.pop_back();
      const int width = st.empty() ? i : i - st.back() - 1;
      answer = max(answer, barHeight * width);
    }
    if (i < n) st.push_back(i);
  }

  cout << answer;
}
```
