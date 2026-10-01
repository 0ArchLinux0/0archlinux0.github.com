---
title: BOJ 1240 - ノード間の距離
author: MINJUN PARK
date: 2022-03-01 20:29:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 木, グラフ, DFS]
pin: false
lang: ja
translation_key: boj-1240-tree-distance
permalink: /ja/posts/boj-1240-tree-distance/
source_permalink: /posts/BOJ-1240/
---

[問題: BOJ 1240 — ノード間の距離](https://www.acmicpc.net/problem/1240) · [한국어](/ko/posts/boj-1240-tree-distance/) · [English](/posts/BOJ-1240/)

入力グラフは木なので、任意の2頂点間には経路がちょうど1つだけ存在します。そのため、最短距離はその唯一の経路に含まれる辺の重みの合計です。一般的な最短経路アルゴリズムを使う必要はありません。

各クエリでは、開始頂点から明示的なスタックを使って探索します。スタックの各要素には現在の頂点、親頂点、開始点から現在の頂点までの累積距離を保持します。通過済みの辺を戻らないように、親への辺はスキップします。目標頂点に到達したら累積距離を出力し、そのクエリの探索を終了します。反復処理による探索なので、木が一直線でも再帰呼び出しスタックを消費しません。

入力の頂点番号は1始まりなので、`N + 1` 個の要素を持つ配列の添字としてそのまま使えます。辺の重みは最大10,000、`N <= 1,000` なので、経路に含まれる辺は最大999本、経路長は最大9,990,000です。累積距離は64ビット整数で安全に扱えます。グラフと探索スタックのメモリ使用量は `O(N)`、`M` 個すべてのクエリを処理する最悪計算量は `O(NM)` です。

## C++17

```cpp
#include <iostream>
#include <tuple>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<pair<int, int>>> graph(n + 1);
    for (int i = 0; i < n - 1; ++i) {
        int a, b, weight;
        cin >> a >> b >> weight;
        graph[a].push_back({b, weight});
        graph[b].push_back({a, weight});
    }

    while (m--) {
        int start, target;
        cin >> start >> target;

        vector<tuple<int, int, long long>> pending;
        pending.emplace_back(start, 0, 0);

        while (!pending.empty()) {
            auto [node, parent, distance] = pending.back();
            pending.pop_back();

            if (node == target) {
                cout << distance << '\n';
                break;
            }

            for (auto [next, weight] : graph[node]) {
                if (next != parent) {
                    pending.emplace_back(next, node, distance + weight);
                }
            }
        }
    }
}
```
