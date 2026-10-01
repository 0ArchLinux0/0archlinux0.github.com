---
title: BOJ 3190 — ヘビ
author: MINJUN PARK
date: 2022-02-24 15:49:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, シミュレーション, deque, ヘビ]
pin: false
lang: ja
translation_key: boj-3190-snake
permalink: /ja/posts/boj-3190-snake/
source_permalink: /posts/BOJ-3190/
---

[問題: BOJ 3190 — ヘビ](https://www.acmicpc.net/problem/3190) · [English](/posts/BOJ-3190/) · [한국어](/ko/posts/boj-3190-snake/)

ヘビは盤面の左上のマスから右向きにスタートします。毎秒 1 マス進み、頭が盤面の外に出るか、胴体が占めているマスに入ると、その秒にゲームが終了します。進む先にリンゴがあればヘビは伸びます。リンゴがなければ尻尾が 1 マス進みます。移動が完了した後、その秒に予定されている方向転換を適用します。

胴体を尻尾から頭の順に deque に格納し、各マスが占有されているかを真偽値の盤面で管理すると、衝突を定数時間で判定できます。まず新しい頭の移動先にリンゴがあるか調べます。通常、占有済みのマスに進むと衝突ですが、リンゴを食べない場合は今回の移動で尻尾が抜けるため、例外として尻尾のマスに進めます。この場合は新しい頭を追加する前に尻尾を取り除きます。リンゴを食べる場合は尻尾をそのまま残し、ヘビを伸ばします。盤面を参照する前に、盤面の範囲内かを確認してください。

方向転換の予定は時刻順に与えられます。移動に成功した後、現在の秒に一致する転換を適用します。`L` は反時計回り、`D` は時計回りに回転します。衝突した移動の後には方向転換を行いません。入力された転換をすべて適用した後も衝突していなければ、直進を続けることで最終的に壁か胴体に衝突します。

実際にシミュレーションした秒数を `S`（最後の衝突移動を含む）、方向転換数を `K`、盤面の一辺を `N` とすると、入力と盤面の初期化を含む時間計算量は `O(S + K + N^2)` です。盤面と転換予定に `O(N^2 + K)` の空間を使い、deque に格納されるマス数は最大 `N^2` です。

## C++17

```cpp
#include <array>
#include <iostream>
#include <utility>
#include <vector>
#include <deque>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<vector<bool>> apple(n, vector<bool>(n, false));
    int appleCount;
    cin >> appleCount;
    for (int i = 0; i < appleCount; ++i) {
        int row, column;
        cin >> row >> column;
        apple[row - 1][column - 1] = true;
    }

    int turnCount;
    cin >> turnCount;
    vector<pair<int, char>> turns(turnCount);
    for (auto& [time, direction] : turns) cin >> time >> direction;

    // 方向は右、下、左、上の順。deque は尻尾から頭の順に並べる。
    constexpr array<int, 4> dr{0, 1, 0, -1};
    constexpr array<int, 4> dc{1, 0, -1, 0};
    vector<vector<bool>> occupied(n, vector<bool>(n, false));
    deque<pair<int, int>> snake{{0, 0}};
    occupied[0][0] = true;

    int direction = 0;
    int nextTurn = 0;
    int seconds = 0;

    while (true) {
        ++seconds;
        const auto [headRow, headColumn] = snake.back();
        const int nextRow = headRow + dr[direction];
        const int nextColumn = headColumn + dc[direction];

        if (nextRow < 0 || nextRow >= n || nextColumn < 0 || nextColumn >= n) break;

        const bool eatingApple = apple[nextRow][nextColumn];
        const auto [tailRow, tailColumn] = snake.front();
        const bool enteringVacatingTail = !eatingApple &&
                                          nextRow == tailRow &&
                                          nextColumn == tailColumn;
        if (occupied[nextRow][nextColumn] && !enteringVacatingTail) break;

        if (eatingApple) {
            apple[nextRow][nextColumn] = false;
        } else {
            snake.pop_front();
            occupied[tailRow][tailColumn] = false;
        }

        snake.emplace_back(nextRow, nextColumn);
        occupied[nextRow][nextColumn] = true;

        if (nextTurn < turnCount && turns[nextTurn].first == seconds) {
            direction = (direction + (turns[nextTurn].second == 'L' ? 3 : 1)) % 4;
            ++nextTurn;
        }
    }

    cout << seconds << '\n';
    return 0;
}
```
