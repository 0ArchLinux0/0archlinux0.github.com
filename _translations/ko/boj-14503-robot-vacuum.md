---
title: BOJ 14503 — 로봇 청소기
author: MINJUN PARK
date: 2022-02-24 15:49:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 구현, 로봇 청소기]
pin: false
lang: ko
translation_key: boj-14503-robot-vacuum
permalink: /ko/posts/boj-14503-robot-vacuum/
source_permalink: /posts/BOJ-14503/
---

[문제: BOJ 14503 — 로봇 청소기](https://www.acmicpc.net/problem/14503) · [English](/posts/BOJ-14503/) · [日本語](/ja/posts/boj-14503-robot-vacuum/)

로봇은 현재 칸을 청소한 다음 왼쪽으로 90도 회전해 앞쪽을 확인합니다. 네 방향을 차례로 확인하며, 청소되지 않은 빈 칸을 찾으면 그쪽으로 한 칸 이동하고 다시 현재 칸 청소부터 시작합니다. 네 칸을 모두 확인했는데 이동할 곳이 없다면 방향을 유지한 채 뒤쪽으로 한 칸 물러납니다. 뒤쪽이 벽이거나 방 범위 밖이면 작동을 멈춥니다. 이미 청소한 칸은 다시 세지 않습니다.

현재 칸은 청소 여부 배열에 처음 도달했을 때만 표시하고 개수를 늘립니다. 이어서 반복문에서 방향을 네 번 왼쪽으로 돌려 각 앞칸이 방 안의 빈 칸이며 아직 청소되지 않았는지 확인합니다. 이동에 성공하면 그 즉시 다음 반복으로 넘어가므로 네 번 모두 회전한 뒤의 방향을 다음 상태로 잘못 사용하지 않습니다. 네 방향 모두 이동할 수 없을 때에만 반대 방향을 계산해 후진하며, 후진 시 바라보는 방향은 변경하지 않습니다. 후진 칸이 벽이면 종료합니다.

청소 가능한 각 칸은 한 번만 새로 방문해 세고, 각 칸에서 최대 네 방향을 확인하므로 시간 복잡도는 `O(NM)`입니다. 방의 지도와 청소 여부 배열이 각각 `O(NM)` 공간을 사용합니다.

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
