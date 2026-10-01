---
title: BOJ 13460 — ビーズ脱出 2
author: MINJUN PARK
date: 2022-02-25 00:59:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, BFS, 実装, ビーズ脱出 2]
pin: false
lang: ja
translation_key: boj-13460-marble-escape
permalink: /ja/posts/boj-13460-marble-escape/
source_permalink: /posts/BOJ-13460/
---

[問題: BOJ 13460 — ビーズ脱出 2](https://www.acmicpc.net/problem/13460) · [English](/posts/BOJ-13460/) · [한국어](/ko/posts/boj-13460-marble-escape/)

盤面には壁、穴、赤と青のビーズがあります。盤面を一方向に傾けると、ビーズは壁に当たるか穴に落ちるまで移動します。10 回以内の傾斜で赤いビーズを穴に入れ、青いビーズは落とさないことが目標です。

状態は 2 つのビーズの現在位置 `(赤, 青)` です。各状態から 4 方向をそれぞれ試します。傾ける方向により前方にあるビーズを先に動かします。そうしないと、後ろのビーズが先に動いて前のビーズを誤って止めてしまう可能性があります。前方のビーズを壁か穴に当たるまで転がし、その最終位置を障害物として後方のビーズを転がします。穴に落ちたビーズは盤面から消えるため、もう一方のビーズを妨げません。この順序で動かすことで、2 つのビーズが同じマスに重なることはありません。どちらも動かない傾斜も遷移として扱えますが、訪問済み記録により無限の繰り返しは起きません。

青いビーズが落ちる状態は破棄します。赤だけが落ちた場合、現在の BFS 距離（傾斜回数）が答えです。どちらも落ちなければ、まだ訪問していない位置の組をキューに追加します。BFS は傾斜回数の少ない状態から探索するため、最初に見つかる成功が最小回数です。深さ 10 の状態からは展開せず、成功がなければ `-1` を出力します。

各ビーズの位置候補は最大 `N * M` 個なので、位置の組は最大 `O((NM)^2)` 個です。1 方向への移動では最大 `O(NM)` マスを調べます。盤面は最大 10 行 10 列であり、この有限な状態グラフは小さいです。一般に、1 状態あたり 4 方向の遷移確認に `O(4 * NM)`、訪問状態の記録に `O((NM)^2)` の空間が必要です。

## C++17

```cpp
#include <array>
#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct State {
    int red;
    int blue;
    int moves;
};

struct Roll {
    int cell;
    bool fell;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<string> board(n);
    int red = -1;
    int blue = -1;
    int hole = -1;
    for (int row = 0; row < n; ++row) {
        cin >> board[row];
        for (int col = 0; col < m; ++col) {
            const int cell = row * m + col;
            if (board[row][col] == 'R') red = cell;
            else if (board[row][col] == 'B') blue = cell;
            else if (board[row][col] == 'O') hole = cell;
        }
    }

    const array<int, 4> dr = {-1, 1, 0, 0};
    const array<int, 4> dc = {0, 0, -1, 1};
    vector<vector<bool>> visited(n * m, vector<bool>(n * m, false));
    queue<State> q;
    q.push({red, blue, 0});
    visited[red][blue] = true;

    auto roll = [&](int cell, int blocker, int direction) {
        int row = cell / m;
        int col = cell % m;
        while (true) {
            const int nextRow = row + dr[direction];
            const int nextCol = col + dc[direction];
            if (board[nextRow][nextCol] == '#' || nextRow * m + nextCol == blocker) {
                return Roll{row * m + col, false};
            }
            row = nextRow;
            col = nextCol;
            if (row * m + col == hole) return Roll{-1, true};
        }
    };

    while (!q.empty()) {
        const State current = q.front();
        q.pop();
        if (current.moves == 10) continue;

        for (int direction = 0; direction < 4; ++direction) {
            const int redCoordinate = direction < 2 ? current.red / m : current.red % m;
            const int blueCoordinate = direction < 2 ? current.blue / m : current.blue % m;
            const bool redFirst = (direction == 0 || direction == 2)
                                      ? redCoordinate < blueCoordinate
                                      : redCoordinate > blueCoordinate;

            const Roll first = redFirst
                                   ? roll(current.red, current.blue, direction)
                                   : roll(current.blue, current.red, direction);
            const int firstCell = first.fell ? -1 : first.cell;
            const Roll second = redFirst
                                    ? roll(current.blue, firstCell, direction)
                                    : roll(current.red, firstCell, direction);

            const bool redFell = redFirst ? first.fell : second.fell;
            const bool blueFell = redFirst ? second.fell : first.fell;
            if (blueFell) continue;
            if (redFell) {
                cout << current.moves + 1 << '\n';
                return 0;
            }

            const int nextRed = redFirst ? first.cell : second.cell;
            const int nextBlue = redFirst ? second.cell : first.cell;
            if (!visited[nextRed][nextBlue]) {
                visited[nextRed][nextBlue] = true;
                q.push({nextRed, nextBlue, current.moves + 1});
            }
        }
    }

    cout << -1 << '\n';
    return 0;
}
```
