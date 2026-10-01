---
title: BOJ 14503 — ロボット掃除機
author: MINJUN PARK
date: 2022-02-24 15:49:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 実装, ロボット掃除機]
pin: false
lang: ja
translation_key: boj-14503-robot-vacuum
permalink: /ja/posts/boj-14503-robot-vacuum/
source_permalink: /posts/BOJ-14503/
---

[問題: BOJ 14503 — ロボット掃除機](https://www.acmicpc.net/problem/14503) · [English](/posts/BOJ-14503/) · [한국어](/ko/posts/boj-14503-robot-vacuum/)

ロボットは現在のマスを掃除してから、左へ90度回転して前方を確認します。4方向を順に調べ、未掃除の空きマスが見つかったらその方向へ1マス進み、現在マスの掃除から再開します。4マスすべてを確認しても移動先がなければ、向きを変えずに後方へ1マス下がります。後方が壁、または部屋の範囲外なら停止します。すでに掃除したマスは数え直しません。

現在マスは、掃除済み配列で未掃除と確認できた場合にだけ印を付け、個数を増やします。続いてループ内で左に4回回転し、各前方のマスが部屋内の空きマスで、かつ未掃除かどうかを確認します。移動できたらすぐ次の反復へ進むため、4回転後の向きを誤って次の状態として使うことはありません。4方向すべてに移動できない場合だけ反対方向を計算して後退し、後退しても向きは変えません。後退先が壁なら終了します。

掃除可能な各マスは新たに一度だけ数え、各マスで確認する方向は最大4つなので、時間計算量は `O(NM)` です。部屋の地図と掃除済み配列がそれぞれ `O(NM)` の領域を使います。

## C++17

```cpp
#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    int row, column, direction;
    cin >> n >> m;
    cin >> row >> column >> direction;

    vector<vector<int>> room(n, vector<int>(m));
    for (auto& line : room) {
        for (int& cell : line) cin >> cell;
    }

    const int dr[4] = {-1, 0, 1, 0};
    const int dc[4] = {0, 1, 0, -1};
    vector<vector<bool>> cleaned(n, vector<bool>(m, false));
    int cleanedCount = 0;

    while (true) {
        if (!cleaned[row][column]) {
            cleaned[row][column] = true;
            ++cleanedCount;
        }

        bool moved = false;
        for (int turn = 0; turn < 4; ++turn) {
            direction = (direction + 3) % 4;
            const int nextRow = row + dr[direction];
            const int nextColumn = column + dc[direction];

            if (nextRow < 0 || nextRow >= n || nextColumn < 0 || nextColumn >= m) continue;
            if (room[nextRow][nextColumn] == 1 || cleaned[nextRow][nextColumn]) continue;

            row = nextRow;
            column = nextColumn;
            moved = true;
            break;
        }

        if (moved) continue;

        const int backDirection = (direction + 2) % 4;
        const int backRow = row + dr[backDirection];
        const int backColumn = column + dc[backDirection];
        if (backRow < 0 || backRow >= n || backColumn < 0 || backColumn >= m) break;
        if (room[backRow][backColumn] == 1) break;

        row = backRow;
        column = backColumn;
    }

    cout << cleanedCount << '\n';
    return 0;
}
```
