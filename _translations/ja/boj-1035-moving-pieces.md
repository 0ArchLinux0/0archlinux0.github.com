---
title: BOJ 1035 - Moving Pieces（駒を動かす）
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [PS, Algorithm, Baekjoon, BOJ, 全探索]
lang: ja
translation_key: boj-1035-moving-pieces
permalink: /ja/posts/boj-1035-moving-pieces/
---

[BOJ 1035: Moving Pieces](https://www.acmicpc.net/problem/1035)

## 方針: 配置全体を状態とする幅優先探索

盤面は25マスで、駒は最大5個です。状態では、すべての駒の位置をまとめて表します。各マスの占有状態を25ビット整数で表し、`(r, c)` に駒があれば `r * 5 + c` 番目のビットを立てます。駒に区別はないため、駒の並び順を考える必要はありません。

状態グラフの辺は、ルールに従った1回の移動そのものです。駒を1つ選び、上下左右に隣接する空きマスへ移動します。この制約を無視してはいけません。移動先を割り当ててマンハッタン距離の合計を求めるだけでは不十分です。個々の最短経路が互いに衝突することがあり、移動先の集合だけでは、その回数で合法的に到達できるとは限りません。

目標状態は、すべての駒が上下左右の隣接関係で1つの連結成分を作る状態です。駒のあるマスから探索を始め、駒のある隣接マスだけをたどります。到達した駒の数が全体の駒数と等しいかを調べれば判定できます。

初期配置からBFSを行い、状態をキューに追加した時点で訪問済みにします。辺を1本たどるごとに移動回数が1増えるため、BFSは移動回数の小さい状態から順に探索します。よって、キューから最初に取り出した目標状態の移動回数が最小値です。初期配置がすでに連結なら答えは0です。

駒が1個以上5個以下の場合、異なる配置数は最大でも

$$\sum_{k=1}^{5} \binom{25}{k} = 68{,}405$$

です。次のコードでは $2^{25}$ 個すべてのマスクに対する距離配列を確保せず、実際に到達した配置だけを `unordered_set` に保存します。1状態あたりの移動候補は最大20個（駒5個、それぞれ隣接マス最大4個）です。

## C++17

```cpp
#include <array>
#include <cstdint>
#include <iostream>
#include <queue>
#include <string>
#include <unordered_set>
#include <utility>

using namespace std;

using Mask = uint32_t;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    array<uint32_t, 25> neighbors{};
    for (int r = 0; r < 5; ++r) {
        for (int c = 0; c < 5; ++c) {
            const int cell = r * 5 + c;
            if (r > 0) neighbors[cell] |= uint32_t{1} << (cell - 5);
            if (r < 4) neighbors[cell] |= uint32_t{1} << (cell + 5);
            if (c > 0) neighbors[cell] |= uint32_t{1} << (cell - 1);
            if (c < 4) neighbors[cell] |= uint32_t{1} << (cell + 1);
        }
    }

    Mask start = 0;
    for (int r = 0; r < 5; ++r) {
        string row;
        cin >> row;
        for (int c = 0; c < 5; ++c) {
            if (row[c] == '*') start |= Mask{1} << (r * 5 + c);
        }
    }

    auto isConnected = [&](Mask pieces) {
        const int pieceCount = __builtin_popcount(pieces);
        Mask reached = 0;
        Mask frontier = pieces & (~pieces + 1);  // 最下位の占有ビット
        while (frontier != 0) {
            const int cell = __builtin_ctz(frontier);
            const Mask bit = Mask{1} << cell;
            frontier &= ~bit;
            if (reached & bit) continue;
            reached |= bit;
            frontier |= neighbors[cell] & pieces & ~reached;
        }
        return __builtin_popcount(reached) == pieceCount;
    };

    queue<pair<Mask, int>> q;
    unordered_set<Mask> visited;
    q.push({start, 0});
    visited.insert(start);

    while (!q.empty()) {
        const auto [state, moves] = q.front();
        q.pop();

        if (isConnected(state)) {
            cout << moves << '\n';
            return 0;
        }

        for (Mask pieces = state; pieces != 0; pieces &= pieces - 1) {
            const int from = __builtin_ctz(pieces);
            const Mask fromBit = Mask{1} << from;
            Mask destinations = neighbors[from] & ~state;
            while (destinations != 0) {
                const int to = __builtin_ctz(destinations);
                const Mask toBit = Mask{1} << to;
                destinations &= ~toBit;

                const Mask next = (state & ~fromBit) | toBit;
                if (visited.insert(next).second) q.push({next, moves + 1});
            }
        }
    }
}
```

盤面は連結で、空きマスを通って駒を移動できるため、有効な入力であれば連結した配置に到達できます。
