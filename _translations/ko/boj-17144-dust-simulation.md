---
title: BOJ 17144 — 미세먼지 안녕!
author: MINJUN PARK
date: 2022-02-26 13:41:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 구현, 미세먼지 안녕!]
pin: false
lang: ko
translation_key: boj-17144-dust-simulation
permalink: /ko/posts/boj-17144-dust-simulation/
source_permalink: /posts/BOJ-17144/
---

[문제: BOJ 17144 — 미세먼지 안녕!](https://www.acmicpc.net/problem/17144) · [English](/posts/BOJ-17144/) · [日本語](/ja/posts/boj-17144-dust-simulation/)

매초 미세먼지가 있는 각 칸은 `floor(미세먼지 / 5)`만큼의 먼지를 상하좌우 인접 칸 중 범위 안에 있고 공기청정기가 아닌 칸으로 확산시킵니다. 모든 칸은 동시에 확산하므로, 미세먼지 값을 갱신하기 전에 별도의 격자에 각 칸에서 이동하는 양을 누적해야 합니다. 확산한 뒤 원래 칸에는 확산되지 않은 양이 남습니다.

공기청정기 두 대는 첫 번째 열의 연속한 두 행에 놓여 있습니다. 위쪽 공기청정기는 위쪽 가장자리와 그 사이의 칸을 따라 반시계 방향으로 공기를 순환시키고, 아래쪽 공기청정기는 아래쪽 가장자리와 그 사이의 칸을 따라 시계 방향으로 순환시킵니다. 두 경로의 칸을 정확한 순서로 한 칸씩 이동시키고, 공기청정기에서 나오는 먼지의 양은 0으로 둡니다. 따라서 공기청정기의 값 `-1`은 유지되며 공기청정기로 들어간 먼지는 제거됩니다.

확산과 두 순환을 `T`초 동안 반복한 뒤 0보다 큰 칸의 값만 합산합니다. 매초 확산은 `R * C`칸을, 순환은 `O(R + C)`개의 가장자리 칸을 방문하므로 시간 복잡도는 `O(T * R * C)`, 공간 복잡도는 `O(R * C)`입니다.

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
