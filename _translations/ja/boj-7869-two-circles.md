---
title: BOJ 7869 - 2つの円
author: MINJUN PARK
date: 2022-03-09 23:16:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 幾何, 2つの円]
pin: false
lang: ja
translation_key: boj-7869-two-circles
permalink: /ja/posts/boj-7869-two-circles/
---

[問題リンク](https://www.acmicpc.net/problem/7869)

## 交差部分の面積

2つの円の中心を $C_1=(x_1,y_1)$、$C_2=(x_2,y_2)$、半径を $r_1,r_2$ とし、中心間の距離を
$d=\sqrt{(x_1-x_2)^2+(y_1-y_2)^2}$ とする。円の位置関係に応じて交差部分の面積を求める。

- $d\ge r_1+r_2$ なら、円は離れているか外接しているため、交差部分の面積は $0$ である。
- $d\le |r_1-r_2|$ なら、一方の円が他方の内側にあるか内接している。交差部分は小さい方の円盤なので、面積は $\pi\min(r_1,r_2)^2$ となる。この条件は中心が一致する場合（$d=0$）も含むため、円が一致していても $d$ で割ることなく処理できる。
- それ以外では、円は部分的に重なり、円周は2点で交わる。

部分的に重なる場合、2つの中心と交点の1つでできる三角形に余弦定理を適用すると、それぞれの中心における半角を求められる。

$$
\theta_1=\cos^{-1}\left(\frac{d^2+r_1^2-r_2^2}{2dr_1}\right),\qquad
\theta_2=\cos^{-1}\left(\frac{d^2+r_2^2-r_1^2}{2dr_2}\right).
$$

各円について、中心角 $2\theta_i$ の扇形から、その中にある二等辺三角形の面積を引くと、弧と弦に囲まれた円弓形の面積 $r_i^2\theta_i-\frac12r_i^2\sin(2\theta_i)$ が得られる。2つの円弓形を足したレンズ形の領域の面積は次のとおりである。

$$
A=r_1^2\theta_1+r_2^2\theta_2
-\frac12r_1^2\sin(2\theta_1)-\frac12r_2^2\sin(2\theta_2).
$$

浮動小数点の丸め誤差によって、余弦定理で得た値が数学的な範囲からわずかに外れることがある。そのため `acos` に渡す前に `[-1, 1]` に収める。部分的な重なりの式は先の2つの境界条件を通過した場合だけ使うため、ゼロ除算は起きず、接する場合も境界条件で処理できる。

## C++17 の実装

時間計算量、追加領域計算量はいずれも $O(1)$ である。

```cpp
#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    double x1, y1, r1, x2, y2, r2;
    std::cin >> x1 >> y1 >> r1 >> x2 >> y2 >> r2;

    const double dx = x1 - x2;
    const double dy = y1 - y2;
    const double d = std::hypot(dx, dy);
    const double pi = std::acos(-1.0);
    double area;

    if (d >= r1 + r2) {
        area = 0.0;
    } else if (d <= std::abs(r1 - r2)) {
        const double radius = std::min(r1, r2);
        area = pi * radius * radius;
    } else {
        const double d2 = d * d;
        const double r1_2 = r1 * r1;
        const double r2_2 = r2 * r2;
        const double cos1 = std::clamp((d2 + r1_2 - r2_2) / (2.0 * d * r1), -1.0, 1.0);
        const double cos2 = std::clamp((d2 + r2_2 - r1_2) / (2.0 * d * r2), -1.0, 1.0);
        const double theta1 = std::acos(cos1);
        const double theta2 = std::acos(cos2);

        area = r1_2 * theta1 + r2_2 * theta2
             - 0.5 * r1_2 * std::sin(2.0 * theta1)
             - 0.5 * r2_2 * std::sin(2.0 * theta2);
    }

    std::cout << std::fixed << std::setprecision(3) << area << '\n';
    return 0;
}
```
