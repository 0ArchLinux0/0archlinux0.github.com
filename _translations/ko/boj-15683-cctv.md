---
title: BOJ 15683 — 감시
author: MINJUN PARK
date: 2022-02-25 21:07:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, DFS, 구현, 감시]
pin: false
lang: ko
translation_key: boj-15683-cctv
permalink: /ko/posts/boj-15683-cctv/
source_permalink: /posts/BOJ-15683/
---

[문제: BOJ 15683 — 감시](https://www.acmicpc.net/problem/15683) · [English](/posts/BOJ-15683/) · [日本語](/ja/posts/boj-15683-cctv/)

각 CCTV는 종류에 따라 정해진 방향을 감시하며, 90도씩 회전할 수 있습니다. 1번은 한 방향, 2번은 서로 반대인 두 방향, 3번은 인접한 두 방향, 4번은 세 방향, 5번은 네 방향을 모두 감시합니다. 서로 다른 회전 상태의 수는 각각 4, 2, 4, 4, 1개입니다. 감시 광선은 다른 CCTV와 빈칸을 통과하지만 벽이나 보드의 경계에서 멈춥니다.

깊이 우선 탐색으로 CCTV마다 하나의 회전 상태를 선택해 모든 조합을 확인합니다. 하나의 조합을 완성하면 새 감시 배열에 각 방향의 광선을 표시하고, 감시되지 않은 빈칸의 수를 셉니다. 그중 최솟값을 답으로 유지합니다. 벽과 CCTV는 사각지대에 포함되지 않으므로 값이 0인 칸만 셉니다. CCTV는 최대 8대이므로 조합 수는 최대 `4^8`이며, 조합마다 광선 추적과 칸 세기에 `O(NM)`이 걸려 전체 시간 복잡도는 `O(4^8 NM)`, 추가 공간 복잡도는 `O(NM)`입니다.

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
