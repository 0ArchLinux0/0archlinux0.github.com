---
title: BOJ. Cheese (2636)
author: MINJUN PARK
date: 2022-03-01 18:29:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, BFS, Implementation, Cheese, 치즈]
pin: false
lang: ja
translation_key: boj-2636-cheese-melting
permalink: /ja/posts/boj-2636-cheese-melting/
---

[問題](https://www.acmicpc.net/problem/2636)

## 解説

各時間の開始時に、ボード外側に追加した空白の枠からBFSを行い、外気と
つながっている空気マスを調べます。外気に隣接するチーズはその時間に溶けます。
まず溶けるマスをすべて集めてから同時に取り除くため、取り除いた後に初めて
露出するチーズが溶けるのは次の時間です。

各融解ラウンドの直前に残っているチーズの総数を保存します。あるラウンドで
最後のチーズが溶けたとき、保存した数が最後に溶ける直前のチーズ数になります。
最初からチーズがない場合はラウンドを実行せず、答えは時間 `0`、チーズ `0`です。

## C++17

```cpp
#include <iostream>
#include <queue>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int rows, cols;
    cin >> rows >> cols;

    // 外側に空白の枠を加え、安全な外気の開始地点を確保します。
    vector<vector<int>> cheese(rows + 2, vector<int>(cols + 2, 0));
    int remaining = 0;
    for (int r = 1; r <= rows; ++r) {
        for (int c = 1; c <= cols; ++c) {
            cin >> cheese[r][c];
            remaining += cheese[r][c];
        }
    }

    int hours = 0;
    int beforeLastMelt = 0;
    const int dr[] = {-1, 1, 0, 0};
    const int dc[] = {0, 0, -1, 1};

    while (remaining > 0) {
        beforeLastMelt = remaining;
        vector<vector<char>> visited(rows + 2, vector<char>(cols + 2, false));
        vector<vector<char>> melts(rows + 2, vector<char>(cols + 2, false));
        queue<pair<int, int>> air;
        vector<pair<int, int>> toMelt;

        air.push({0, 0});
        visited[0][0] = true;
        while (!air.empty()) {
            auto [r, c] = air.front();
            air.pop();

            for (int d = 0; d < 4; ++d) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= rows + 2 || nc < 0 || nc >= cols + 2) {
                    continue;
                }
                if (cheese[nr][nc]) {
                    if (!melts[nr][nc]) {
                        melts[nr][nc] = true;
                        toMelt.push_back({nr, nc});
                    }
                } else if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    air.push({nr, nc});
                }
            }
        }

        // 外気の探索が完了してから、この時間に溶けるチーズを同時に取り除きます。
        for (auto [r, c] : toMelt) {
            cheese[r][c] = 0;
        }
        remaining -= static_cast<int>(toMelt.size());
        ++hours;
    }

    cout << hours << '\n' << beforeLastMelt << '\n';
    return 0;
}
```

`H`回の融解ラウンドそれぞれで、`R × C`ボードの各マスを定数回だけ
調べるため、時間計算量は `O(H·R·C)` です。ボード、訪問/融解マーカーと
作業キューに必要な空間計算量は `O(R·C)` です。
