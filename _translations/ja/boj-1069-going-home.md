---
title: BOJ 1069 - 家に帰る
author: MINJUN PARK
date: 2022-03-09 23:16:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 幾何]
pin: false
lang: ja
translation_key: boj-1069-going-home
permalink: /ja/posts/boj-1069-going-home/
---

[問題リンク](https://www.acmicpc.net/problem/1069)

出発地点から目的地までの距離を $D_0=\sqrt{x^2+y^2}$ とする。歩いて直行する場合の時間は $D_0$ である。ジャンプは方向にかかわらず必ず距離 $D$ だけ移動し、時間 $T$ がかかる。残りの距離は歩いて移動できる。

$q=\lfloor D_0/D\rfloor$、$r=D_0-qD$ とおくと、$0\le r<D$ である。候補となる時間は次のとおり。

- 最初から最後まで歩く: $D_0$。
- 目的地の方向へ $q$ 回ジャンプし、残りの $r$ を歩く: $qT+r$。
- 目的地の方向へ $q+1$ 回ジャンプし、行き過ぎた距離 $D-r$ を戻って歩く: $(q+1)T+D-r$。
- $q+1$ 回ジャンプして目的地に到達する: $(q+1)T$。$q\ge1$ なら、目的地の方向へ $q-1$ 回ジャンプしたあと、2回のジャンプの合成変位を $D+r$ にする。長さ $D$ のジャンプ2回で作れる合成変位の長さは $0$ から $2D$ までであり、$D+r<2D$ なので実現できる。$q=0$ の場合も、2回のジャンプで合成変位を $D_0<D$ にできるため、同じ時間で到達できる。

この候補で最小値を求められる。$n\le q$ 回ジャンプする場合、残りの距離は少なくとも $D_0-nD$ なので、時間は少なくとも $nT+D_0-nD$ である。これは $n$ の一次式だから、この範囲での最小値は端点の $n=0$ または $n=q$ で得られる。$q+1$ 回以上ジャンプすると、ジャンプ時間だけで少なくとも $(q+1)T$ が必要であり、上記の方法で実際にその時間で到達できる。$q=0$ の場合も同様に扱える。1回のジャンプ後に残る距離の最小値は $|D-D_0|=D-r$ で、2回のジャンプなら目的地に到達できる。したがって、$D_0<D$ の場合や $r=0$ の場合も含め、列挙した候補の最小値が答えとなる。

## C++17 の実装

`hypot` を使ってユークリッド距離を安定して計算する。時間計算量、追加領域計算量はいずれも $O(1)$ である。

```cpp
#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    double x, y, jumpDistance, jumpTime;
    std::cin >> x >> y >> jumpDistance >> jumpTime;

    const double distance = std::hypot(x, y);
    const auto q = static_cast<long long>(std::floor(distance / jumpDistance));
    const double remainder = distance - q * jumpDistance;

    double answer = distance;
    if (q == 0) {
        answer = std::min({answer,
                           jumpTime + jumpDistance - distance,
                           2.0 * jumpTime});
    } else {
        answer = std::min({answer,
                           q * jumpTime + remainder,
                           (q + 1) * jumpTime + jumpDistance - remainder,
                           (q + 1) * jumpTime});
    }

    std::cout << std::fixed << std::setprecision(10) << answer << '\n';
    return 0;
}
```
