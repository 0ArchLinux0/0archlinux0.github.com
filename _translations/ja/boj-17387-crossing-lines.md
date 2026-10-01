---
title: BOJ 17387 - 線分の交差 2
author: MINJUN PARK
date: 2022-03-09 22:16:00 +0900
categories: [Record, Code]
tags: [PS, Algorithm, Baekjoon, BOJ, 幾何, CCW, 線分交差]
pin: false
lang: ja
translation_key: boj-17387-crossing-lines
permalink: /ja/posts/boj-17387-crossing-lines/
---

[BOJ 17387: Crossing Lines 2（線分の交差 2）](https://www.acmicpc.net/problem/17387)

## 方針: 向き判定と閉じた線分の範囲

3点 $P$、$Q$、$R$ に対する外積 $(Q-P) \times (R-P)$ の符号から、点 $R$ が有向直線 $PQ$ のどちら側にあるかが分かります。値が正なら反時計回り、負なら時計回り、0なら3点は同一直線上です。

線分を $AB$、$CD$ とします。$C$ と $D$ が直線 $AB$ の厳密に反対側にあり、さらに $A$ と $B$ が直線 $CD$ の厳密に反対側にあれば、2つの線分は内部で交差します。「厳密に反対側」であることが重要です。向き判定が0の場合は内部での交差ではないため、境界ケースとして別に確認します。

向き判定が0となる各ケースでは、その点がもう一方の線分の**閉じたバウンディングボックス**内にあるかを調べます。つまり、点のx座標とy座標の両方が、線分の両端点のそれぞれの座標の間（端点を含む）になければなりません。この判定は同一直線上にある線分で不可欠です。同じ無限直線上にあるだけでは、有限な線分同士が重なるとは限りません。境界を含めることで端点での接触や重なりを認め、離れた共線線分を除外できます。4通りすべての「端点が線分上にあるか」の判定により、1点だけからなる退化線分も扱えます。

座標範囲が $[-10^9, 10^9]$ のとき、座標差の絶対値は最大 $2 \cdot 10^9$ です。外積を構成する各積の絶対値は最大 $4 \cdot 10^{18}$、その差は最大 $8 \cdot 10^{18}$ となり、符号付き64ビット整数の範囲に収まります。コードでは座標と外積に `long long` を使います。

境界ケースの確認:

- 線分が内部で交差する場合は `1`。
- 端点1つで接する場合は `1`。
- 共線線分が端点を共有する場合、または正の長さで重なる場合は `1`。
- 4点が共線でも、線分間に隙間があれば `0`。
- 点線分がもう一方の線分上にあれば `1`、外にあれば `0`。

## C++17

```cpp
#include <algorithm>
#include <iostream>

using namespace std;

struct Point {
    long long x;
    long long y;
};

long long cross(const Point& a, const Point& b, const Point& c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
}

int orientation(const Point& a, const Point& b, const Point& c) {
    const long long value = cross(a, b, c);
    if (value > 0) return 1;
    if (value < 0) return -1;
    return 0;
}

bool onSegment(const Point& a, const Point& b, const Point& p) {
    return min(a.x, b.x) <= p.x && p.x <= max(a.x, b.x) &&
           min(a.y, b.y) <= p.y && p.y <= max(a.y, b.y);
}

bool intersects(const Point& a, const Point& b, const Point& c, const Point& d) {
    const int abC = orientation(a, b, c);
    const int abD = orientation(a, b, d);
    const int cdA = orientation(c, d, a);
    const int cdB = orientation(c, d, b);

    if (abC * abD < 0 && cdA * cdB < 0) return true;

    if (abC == 0 && onSegment(a, b, c)) return true;
    if (abD == 0 && onSegment(a, b, d)) return true;
    if (cdA == 0 && onSegment(c, d, a)) return true;
    if (cdB == 0 && onSegment(c, d, b)) return true;
    return false;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    Point a, b, c, d;
    cin >> a.x >> a.y >> b.x >> b.y >> c.x >> c.y >> d.x >> d.y;

    cout << (intersects(a, b, c, d) ? 1 : 0) << '\n';
    return 0;
}
```
