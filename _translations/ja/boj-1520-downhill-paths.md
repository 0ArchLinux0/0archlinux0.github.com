---
title: BOJ 1520 - 下り坂の道
author: MINJUN PARK
date: 2022-03-11 18:28:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, DP, DAG, 내리막 길]
pin: false
lang: ja
translation_key: boj-1520-downhill-paths
permalink: /ja/posts/boj-1520-downhill-paths/
---

[BOJ 1520 - 下り坂の道](https://www.acmicpc.net/problem/1520)

## 解法：DAG上の動的計画法

各マスを頂点とみなします。上下左右に隣接するマスのうち、現在のマスより低いマスへ向かう有向辺を張ります。辺をたどるたびに高さが下がるため、有向サイクルは存在せず、このグラフはDAGです。高さが等しい隣接マスの間には辺を張りません。

左上から右下までの有向経路数を求めます。メモ化付きの再帰DFSなら漸化式をそのまま表せますが、経路には最大で $MN=250{,}000$ 個のマスが含まれます。これほど深い再帰はコールスタックを使い切る危険があります。そこで、マスを高さの昇順に処理します。これは辺を逆向きにしたグラフのトポロジカル順序です。

`dp[r][c]`を`(r,c)`から目的地までの下り経路数とします。目的地の値を1にします。これは目的地から目的地への空の続き方を1通りと数えるためです。マス`u`を処理するとき、より低い隣接マスはすべて処理済みです。範囲内にある各より高い隣接マス`v`に`dp[u]`を加算します。これは辺を逆向きにした漸化式です。`v`から目的地への経路は、最初に必ずより低い隣接マスへ進みます。そのうち最初の移動が`v -> u`となる経路はちょうど`dp[u]`通りです。昇順処理であるマスに到達した時点で、すべての低い隣接マスからの寄与が確定しています。答えは`dp[0][0]`です。

4方向を個別に確認し、グリッドの外へ出る座標は無視します。高さを厳密に比較するため、同じ高さのマスへの移動は除外されます。同じ高さのマス間には下り移動の辺がありません。

## 正確な整数と計算量

公式制約は $1 \le M,N \le 500$、高さは1から10,000までです。経路数はマス数以下とは限りません。複数の異なる経路が同じマスに合流するため、固定幅整数に答えが収まる保証はありません。実装では経路数を正確に扱うため、`boost::multiprecision::cpp_int`を使います。

$V=MN$、$E$を高さの異なる隣接マス対の数（各対は有向辺を1本作ります）、$B$を経路数の最大ビット長とします。ソートには$O(V\log V)$回の比較が必要で、グリッドの有向辺数は最大$4V$です。任意精度整数の加算は通常の線形加算コストモデルで$O(B)$ビット演算となるため、全体の時間計算量は$O(V\log V + EB)$です。グリッドと順序の保存に$O(V)$個の要素が必要で、それに整数の保存領域が加わります。ビット単位では、DP値は最悪の場合合計で最大$O(VB)$ビットを占めます。

## C++17

```cpp
#include <algorithm>
#include <array>
#include <iostream>
#include <vector>
#include <boost/multiprecision/cpp_int.hpp>

using namespace std;
using boost::multiprecision::cpp_int;

struct Cell {
    int height;
    int row;
    int col;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int rows, cols;
    cin >> rows >> cols;

    vector<vector<int>> height(rows, vector<int>(cols));
    vector<Cell> order;
    order.reserve(static_cast<size_t>(rows) * cols);
    for (int r = 0; r < rows; ++r) {
        for (int c = 0; c < cols; ++c) {
            cin >> height[r][c];
            order.push_back({height[r][c], r, c});
        }
    }

    sort(order.begin(), order.end(), [](const Cell& a, const Cell& b) {
        return a.height < b.height;
    });

    vector<vector<cpp_int>> dp(rows, vector<cpp_int>(cols));
    dp[rows - 1][cols - 1] = 1;

    constexpr array<int, 4> dr = {-1, 1, 0, 0};
    constexpr array<int, 4> dc = {0, 0, -1, 1};

    for (const Cell& cell : order) {
        const int r = cell.row;
        const int c = cell.col;
        for (int direction = 0; direction < 4; ++direction) {
            const int nr = r + dr[direction];
            const int nc = c + dc[direction];
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
            if (height[nr][nc] > height[r][c]) {
                dp[nr][nc] += dp[r][c];
            }
        }
    }

    cout << dp[0][0] << '\n';
    return 0;
}
```
