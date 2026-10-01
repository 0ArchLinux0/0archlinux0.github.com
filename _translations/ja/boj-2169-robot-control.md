---
title: BOJ 2169 — ロボットコントロール
author: MINJUN PARK
date: 2022-02-26 02:41:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, DP, ロボットコントロール]
pin: false
lang: ja
translation_key: boj-2169-robot-control
permalink: /ja/posts/boj-2169-robot-control/
source_permalink: /posts/BOJ-2169/
---

[問題: BOJ 2169 — ロボットコントロール](https://www.acmicpc.net/problem/2169) · [한국어](/ko/posts/boj-2169-robot-control/) · [English](/posts/BOJ-2169/)

ロボットは `N × M` のグリッドの左上のマスから出発し、右下のマスに到達しなければなりません。通過した各マスの値をスコアに加算します。移動できる方向は左、右、下のみで、上には移動できず、同じマスを二度通ることもできません。通過したマスの値の合計を最大化することが目標です。

ポイントは、グリッドを1行ずつ処理することです。経路は上からその行に入り、行内を進んだ後は下へ出ます。上へ戻れないため、同じ行での水平方向の移動は一方向だけです。両方向に移動すると、すでに通ったマスを再訪してしまいます。したがって、現在の行の各マスへ到達する最良の経路は、上のマスから下りてくるか、同じ行の隣のマスから水平方向に続くかのどちらかです。

ある行について、`above[c]` を前の行から列 `c` まで到達する最良スコアとします。左から右への走査では、上から下りる場合と左から続く場合の最良スコアを計算します。右から左への走査では、上から下りる場合と右から続く場合を計算します。2つの走査結果の各マスでの最大値が、その行での最良スコアです。最初の行は左端のマスだけを開始地点として初期化し、それ以外の位置から経路が始まらないようにします。

到達不能な状態には負の無限大を設定するため、負のマスの値を未訪問や初期スコアと誤認しません。前の行と2方向の走査用配列だけを保持するので、時間計算量は `O(NM)`、補助領域の計算量は `O(M)` です。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <limits>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<long long>> value(n, vector<long long>(m));
    for (auto& row : value) {
        for (long long& cell : row) cin >> cell;
    }

    constexpr long long NEG = numeric_limits<long long>::lowest() / 4;
    vector<long long> above(m, NEG), leftToRight(m), rightToLeft(m);

    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) {
            const long long fromAbove = above[c];
            const long long fromLeft = (c > 0) ? leftToRight[c - 1] : NEG;
            if (r == 0 && c == 0) {
                leftToRight[c] = value[r][c];
            } else {
                leftToRight[c] = max(fromAbove, fromLeft) + value[r][c];
            }
        }

        for (int c = m - 1; c >= 0; --c) {
            const long long fromAbove = above[c];
            const long long fromRight = (c + 1 < m) ? rightToLeft[c + 1] : NEG;
            if (r == 0 && c == 0) {
                rightToLeft[c] = value[r][c];
            } else {
                rightToLeft[c] = max(fromAbove, fromRight) + value[r][c];
            }
        }

        for (int c = 0; c < m; ++c) {
            above[c] = max(leftToRight[c], rightToLeft[c]);
        }
    }

    cout << above[m - 1] << '\n';
    return 0;
}
```
