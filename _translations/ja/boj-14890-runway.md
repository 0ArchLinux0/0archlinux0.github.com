---
title: BOJ 14890 — 滑走路
author: MINJUN PARK
date: 2022-02-28 22:20:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 実装, 滑走路]
pin: false
lang: ja
translation_key: boj-14890-runway
permalink: /ja/posts/boj-14890-runway/
source_permalink: /posts/BOJ-14890/
---

[問題: BOJ 14890 — 滑走路](https://www.acmicpc.net/problem/14890) · [English](/posts/BOJ-14890/) · [한국어](/ko/posts/boj-14890-runway/)

隣り合うマスの高低差が 0 または 1 で、高さの差が 1 の場所すべてに長さ `L` の傾斜路を置けるなら、その行または列に道を作れます。傾斜路 1 つは低い側の `L` マスを占有します。その区間の高さはすべて同じで、線の範囲内に収まり、ほかの傾斜路がすでに使ったマスと重なってはいけません。

1 本の線を左から右へ調べ、傾斜路を置いたマスを記録します。隣り合うマスの高さが同じなら何もしません。上り坂では境界の直前にある `L` マスが低い側で、下り坂では次のマスから `L` マスが低い側です。範囲内か、その区間の高さがすべて同じか、使用済みのマスと重ならないかを確認してから、そのマスを使用済みにします。この明示的な占有確認により、連続する下り坂で同じマスを再利用することを防げます。高低差が 1 より大きい場合、置ける区間がない場合、または重複がある場合、その線には道を作れません。この判定をすべての行と列に適用します。

各区間には傾斜路を 1 つだけ置けます。傾斜路の境界にある低い側の区間は同じ高さでなければならず、隣り合うマスの高低差が 1 より大きければ常に不可能です。`N <= 100` のとき、各線の `N` 個の境界を調べ、各境界で最大 `L` マスを確認するため、時間計算量は `O(N^2 * L)` です。高さの盤面は `O(N^2)` の空間を使い、1 本の線と傾斜路の占有記録には `O(N)` の一時空間を使います。

## C++17

```cpp
#include <iostream>
#include <vector>

using namespace std;

bool canBuildRunway(const vector<int>& line, int length) {
    const int n = static_cast<int>(line.size());
    vector<bool> used(n, false);

    for (int i = 0; i + 1 < n; ++i) {
        const int difference = line[i + 1] - line[i];
        if (difference == 0) continue;
        if (difference < -1 || difference > 1) return false;

        const int start = difference == 1 ? i - length + 1 : i + 1;
        const int end = difference == 1 ? i : i + length;
        if (start < 0 || end >= n) return false;

        const int lowerHeight = difference == 1 ? line[i] : line[i + 1];
        for (int cell = start; cell <= end; ++cell) {
            if (line[cell] != lowerHeight || used[cell]) return false;
        }
        for (int cell = start; cell <= end; ++cell) {
            used[cell] = true;
        }
    }

    return true;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, length;
    cin >> n >> length;

    vector<vector<int>> height(n, vector<int>(n));
    for (auto& row : height) {
        for (int& cell : row) cin >> cell;
    }

    int answer = 0;
    vector<int> line(n);

    for (int row = 0; row < n; ++row) {
        if (canBuildRunway(height[row], length)) ++answer;
    }

    for (int column = 0; column < n; ++column) {
        for (int row = 0; row < n; ++row) {
            line[row] = height[row][column];
        }
        if (canBuildRunway(line, length)) ++answer;
    }

    cout << answer << '\n';
    return 0;
}
```
