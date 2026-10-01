---
title: BOJ 15683 — 監視
author: MINJUN PARK
date: 2022-02-25 21:07:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, DFS, 実装, 監視]
pin: false
lang: ja
translation_key: boj-15683-cctv
permalink: /ja/posts/boj-15683-cctv/
source_permalink: /posts/BOJ-15683/
---

[問題: BOJ 15683 — 監視](https://www.acmicpc.net/problem/15683) · [한국어](/ko/posts/boj-15683-cctv/) · [English](/posts/BOJ-15683/)

CCTVは種類ごとに定められた方向を監視し、90度ずつ回転できます。タイプ1は1方向、タイプ2は互いに反対の2方向、タイプ3は隣り合う2方向、タイプ4は3方向、タイプ5は4方向すべてを監視します。異なる向きの数はそれぞれ4、2、4、4、1通りです。監視の光線はほかのCCTVや空きマスを通過しますが、壁または盤面の端で止まります。

深さ優先探索で各CCTVの向きを1つずつ選び、すべての組み合わせを調べます。向きの組み合わせが決まったら、新しい監視配列に各方向の光線を記録し、監視されない空きマスの数を数えます。その最小値を答えとします。壁とCCTVは死角に数えないため、値が0のマスだけを数えます。CCTVは最大8台なので、組み合わせ数は最大`4^8`です。各組み合わせで光線を追跡してマスを数えるのに`O(NM)`かかるため、全体の時間計算量は`O(4^8 NM)`、追加領域計算量は`O(NM)`です。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Camera {
    int row;
    int column;
    int type;
};

int n, m;
vector<vector<int>> board;
vector<Camera> cameras;
vector<int> orientation;
int minimumBlindSpots;

const int dr[4] = {-1, 0, 1, 0};
const int dc[4] = {0, 1, 0, -1};
const vector<vector<int>> directions[5] = {
    {{0}, {1}, {2}, {3}},
    {{0, 2}, {1, 3}},
    {{0, 1}, {1, 2}, {2, 3}, {3, 0}},
    {{0, 1, 3}, {0, 1, 2}, {1, 2, 3}, {0, 2, 3}},
    {{0, 1, 2, 3}}
};

void evaluate() {
    vector<vector<bool>> watched(n, vector<bool>(m, false));

    for (int i = 0; i < static_cast<int>(cameras.size()); ++i) {
        const Camera& camera = cameras[i];
        const auto& dirs = directions[camera.type - 1][orientation[i]];
        for (int dir : dirs) {
            int row = camera.row + dr[dir];
            int column = camera.column + dc[dir];
            while (row >= 0 && row < n && column >= 0 && column < m && board[row][column] != 6) {
                watched[row][column] = true;
                row += dr[dir];
                column += dc[dir];
            }
        }
    }

    int blindSpots = 0;
    for (int row = 0; row < n; ++row) {
        for (int column = 0; column < m; ++column) {
            if (board[row][column] == 0 && !watched[row][column]) ++blindSpots;
        }
    }
    minimumBlindSpots = min(minimumBlindSpots, blindSpots);
}

void search(int index) {
    if (index == static_cast<int>(cameras.size())) {
        evaluate();
        return;
    }

    const int type = cameras[index].type;
    const int count = static_cast<int>(directions[type - 1].size());
    for (int turn = 0; turn < count; ++turn) {
        orientation[index] = turn;
        search(index + 1);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> m;
    board.assign(n, vector<int>(m));
    for (int row = 0; row < n; ++row) {
        for (int column = 0; column < m; ++column) {
            cin >> board[row][column];
            if (1 <= board[row][column] && board[row][column] <= 5) {
                cameras.push_back({row, column, board[row][column]});
            }
        }
    }

    orientation.resize(cameras.size());
    minimumBlindSpots = n * m;
    search(0);
    cout << minimumBlindSpots << '\n';
}
```
