---
title: BOJ 17386 - 線分交差 1
author: MINJUN PARK
date: 2022-03-09 22:46:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 外積, CCW, 幾何, 線分交差]
pin: false
lang: ja
translation_key: boj-17386-crossing-lines
permalink: /ja/posts/boj-17386-crossing-lines/
---

[問題ページ](https://www.acmicpc.net/problem/17386)

## 向きの判定と線分交差

3点 `A`、`B`、`C` の向きを表す値を次のように定義する。

```text
cross(A, B, C) = (B.x - A.x)(C.y - A.y) - (B.y - A.y)(C.x - A.x)
```

値が正なら、`C` は有向直線 `AB` の反時計回り側にあり、負なら時計回り側にある。0の場合は3点が一直線上にある。

入力では2本の線分 `AB` と `CD` が与えられる。問題では4つの端点のうち3点が一直線上に並ぶことはないと保証されている。したがって、線分が端点で接したり、同一直線上に重なったりすることはなく、交差する場合は両線分の内部で交差する。線分が交差する必要十分条件は、`A` と `B` が直線 `CD` の反対側にあり、かつ `C` と `D` が直線 `AB` の反対側にあることだ。向きを表す値では、それぞれの組の符号が異なる必要がある。

片方の直線に対して反対側にあることだけでは、有限の線分同士が交差するとは限らないため、両方の条件を確認する。共線の場合はないので、4つの向きの値はいずれも0にならず、符号の厳密な比較だけで判定できる。外積同士を掛けて符号を調べると、各外積が64ビット整数に収まっていても積がオーバーフローする可能性があるため、積は計算しない。

座標の範囲は `-1,000,000 <= x, y <= 1,000,000` である。座標差は最大 `2,000,000`、外積の絶対値は最大 `8 × 10^12` なので、符号付き64ビット整数で保持できる。時間計算量、追加領域計算量はいずれも `O(1)`。

## C++17実装

```cpp
#include <iostream>
using namespace std;

struct Point {
    long long x, y;
};

long long cross(const Point& a, const Point& b, const Point& c) {
    return (b.x - a.x) * (c.y - a.y)
         - (b.y - a.y) * (c.x - a.x);
}

bool oppositeSigns(long long a, long long b) {
    return (a < 0 && b > 0) || (a > 0 && b < 0);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    Point a, b, c, d;
    cin >> a.x >> a.y >> b.x >> b.y
        >> c.x >> c.y >> d.x >> d.y;

    const long long abC = cross(a, b, c);
    const long long abD = cross(a, b, d);
    const long long cdA = cross(c, d, a);
    const long long cdB = cross(c, d, b);

    cout << (oppositeSigns(abC, abD) && oppositeSigns(cdA, cdB));
}
```
