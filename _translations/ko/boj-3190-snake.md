---
title: BOJ 3190 — 뱀
author: MINJUN PARK
date: 2022-02-24 15:49:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 시뮬레이션, 덱, 뱀]
pin: false
lang: ko
translation_key: boj-3190-snake
permalink: /ko/posts/boj-3190-snake/
source_permalink: /posts/BOJ-3190/
---

[문제: BOJ 3190 — 뱀](https://www.acmicpc.net/problem/3190) · [English](/posts/BOJ-3190/) · [日本語](/ja/posts/boj-3190-snake/)

뱀은 보드의 왼쪽 위 칸에서 오른쪽을 향해 출발합니다. 매 초 한 칸 앞으로 이동하며, 머리가 보드 밖으로 나가거나 몸이 차지하고 있는 칸으로 들어가면 그 초에 게임이 끝납니다. 이동할 칸에 사과가 있으면 뱀의 길이가 늘어납니다. 사과가 없으면 꼬리가 한 칸 이동합니다. 이동을 완료한 뒤 해당 초에 예약된 방향 전환을 적용합니다.

몸통을 꼬리부터 머리 순서로 덱에 저장하고, 각 칸의 점유 여부를 불리언 격자로 관리하면 충돌을 상수 시간에 확인할 수 있습니다. 새 머리가 향하는 칸에 사과가 있는지 먼저 확인합니다. 일반적으로 이미 점유된 칸으로 이동하면 충돌이지만, 사과를 먹지 않는 경우에는 현재 꼬리가 이번 이동에서 빠져나가므로 예외적으로 꼬리 칸으로 이동할 수 있습니다. 이 경우 새 머리를 넣기 전에 꼬리를 제거합니다. 사과를 먹는 경우에는 꼬리가 그대로 유지되고 뱀이 길어집니다. 격자를 참조하기 전에 보드 경계를 먼저 검사합니다.

방향 전환 정보는 시간순으로 주어집니다. 이동이 성공한 뒤 현재 초와 일치하는 전환을 적용합니다. `L`은 반시계 방향, `D`는 시계 방향으로 회전합니다. 충돌한 이동 뒤에는 방향을 바꾸지 않습니다. 입력된 전환이 모두 끝나도 충돌하지 않았다면, 계속 직진하여 결국 벽이나 몸에 부딪힙니다.

실제로 시뮬레이션한 초 수를 `S`(마지막 충돌 이동 포함), 방향 전환 수를 `K`, 보드 한 변의 길이를 `N`이라 하면 입력 및 보드 초기화까지 포함한 시간 복잡도는 `O(S + K + N^2)`입니다. 보드와 전환 정보에 `O(N^2 + K)` 공간을 사용하며, 덱에는 최대 `N^2`개의 칸이 들어갑니다.

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

    // 방향은 오른쪽, 아래, 왼쪽, 위 순서이며 덱은 꼬리부터 머리 순서다.
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
