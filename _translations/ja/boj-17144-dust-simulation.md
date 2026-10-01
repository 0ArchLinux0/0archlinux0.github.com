---
title: BOJ 17144 — 微細粉塵シミュレーション
author: MINJUN PARK
date: 2022-02-26 13:41:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 実装, 微細粉塵シミュレーション]
pin: false
lang: ja
translation_key: boj-17144-dust-simulation
permalink: /ja/posts/boj-17144-dust-simulation/
source_permalink: /posts/BOJ-17144/
---

[問題: BOJ 17144 — Fine Dust Simulation](https://www.acmicpc.net/problem/17144) · [English](/posts/BOJ-17144/) · [한국어](/ko/posts/boj-17144-dust-simulation/)

毎秒、ほこりのあるマスは `floor(ほこり / 5)` の量を、上下左右に隣接するマスのうち盤面内にあり空気清浄機ではないマスへ拡散します。すべてのマスは同時に拡散するため、ほこりの量を書き換える前に、別の配列へ各マスから移動する量を加算します。拡散後、元のマスには移動しなかった分が残ります。

2 台の空気清浄機は最初の列の連続する 2 行を占めます。上側の清浄機は上端とその清浄機との間のマスに沿って反時計回りに、下側の清浄機は下端とその清浄機との間のマスに沿って時計回りに空気を循環させます。それぞれの経路上で、ほこりを正確な順番に 1 マスずつ移動させ、清浄機から出るほこりは 0 とします。これにより、清浄機のマスは `-1` のまま保たれ、清浄機に入ったほこりは取り除かれます。

拡散と 2 つの循環を `T` 秒繰り返し、最後に 0 より大きいマスだけを合計します。1 秒あたり、拡散で `R * C` マス、循環で `O(R + C)` 個の周辺マスを調べるため、時間計算量は `O(T * R * C)`、空間計算量は `O(R * C)` です。

## C++17

```cpp
#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int rows, columns, seconds;
    cin >> rows >> columns >> seconds;

    vector<vector<int>> dust(rows, vector<int>(columns));
    int upper = -1;
    int lower = -1;
    for (int row = 0; row < rows; ++row) {
        for (int column = 0; column < columns; ++column) {
            cin >> dust[row][column];
            if (dust[row][column] == -1) {
                if (upper == -1) upper = row;
                else lower = row;
            }
        }
    }

    vector<vector<int>> change(rows, vector<int>(columns));
    constexpr int rowStep[] = {-1, 1, 0, 0};
    constexpr int columnStep[] = {0, 0, -1, 1};

    for (int second = 0; second < seconds; ++second) {
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                change[row][column] = 0;
            }
        }

        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (dust[row][column] <= 0) continue;
                const int spread = dust[row][column] / 5;
                if (spread == 0) continue;

                int neighbors = 0;
                for (int direction = 0; direction < 4; ++direction) {
                    const int nextRow = row + rowStep[direction];
                    const int nextColumn = column + columnStep[direction];
                    if (nextRow < 0 || nextRow >= rows ||
                        nextColumn < 0 || nextColumn >= columns ||
                        dust[nextRow][nextColumn] == -1) {
                        continue;
                    }
                    change[nextRow][nextColumn] += spread;
                    ++neighbors;
                }
                change[row][column] -= spread * neighbors;
            }
        }

        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                dust[row][column] += change[row][column];
            }
        }

        for (int row = upper - 1; row > 0; --row) dust[row][0] = dust[row - 1][0];
        for (int column = 0; column + 1 < columns; ++column) {
            dust[0][column] = dust[0][column + 1];
        }
        for (int row = 0; row < upper; ++row) {
            dust[row][columns - 1] = dust[row + 1][columns - 1];
        }
        for (int column = columns - 1; column > 1; --column) {
            dust[upper][column] = dust[upper][column - 1];
        }
        dust[upper][1] = 0;

        for (int row = lower + 1; row + 1 < rows; ++row) {
            dust[row][0] = dust[row + 1][0];
        }
        for (int column = 0; column + 1 < columns; ++column) {
            dust[rows - 1][column] = dust[rows - 1][column + 1];
        }
        for (int row = rows - 1; row > lower; --row) {
            dust[row][columns - 1] = dust[row - 1][columns - 1];
        }
        for (int column = columns - 1; column > 1; --column) {
            dust[lower][column] = dust[lower][column - 1];
        }
        dust[lower][1] = 0;
    }

    int total = 0;
    for (const auto& row : dust) {
        for (int amount : row) {
            if (amount > 0) total += amount;
        }
    }
    cout << total << '\n';
    return 0;
}
```
