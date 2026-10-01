---
title: BOJ 14499 - 주사위 굴리기
author: MINJUN PARK
date: 2022-02-24 14:22:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 구현, 주사위 굴리기]
pin: false
lang: ko
translation_key: boj-14499-rolling-dice
permalink: /ko/posts/boj-14499-rolling-dice/
source_permalink: /posts/BOJ-14499/
---

[문제: BOJ 14499 — 주사위 굴리기](https://www.acmicpc.net/problem/14499) · [English](/posts/BOJ-14499/) · [日本語](/ja/posts/boj-14499-rolling-dice/)

주사위의 여섯 면에 적힌 값을 고정된 방향 배열 `TOP`, `BOTTOM`, `NORTH`, `SOUTH`, `EAST`, `WEST`에 저장합니다. 불변식은 배열의 각 항목이 항상 해당 방향을 향하고 있는 면의 값을 나타낸다는 것입니다. 굴릴 때마다 회전축을 기준으로 네 면만 바뀌고, 나머지 두 면은 그대로 유지됩니다.

동쪽으로 굴리면 기존 서쪽 면이 위쪽이 되고, 기존 위쪽 면은 동쪽, 기존 동쪽 면은 아래쪽, 기존 아래쪽 면은 서쪽이 됩니다. 서쪽 굴리기는 이 순환의 역순입니다. 북쪽으로 굴리면 기존 남쪽 면이 위쪽이 되고, 기존 위쪽 면은 북쪽, 기존 북쪽 면은 아래쪽, 기존 아래쪽 면은 남쪽이 됩니다. 남쪽 굴리기는 이 순환의 역순입니다. 네 항목을 덮어쓰기 전에 한 면의 값을 임시 변수에 저장하면 각 순열을 상수 시간에 처리할 수 있습니다.

각 명령을 실행하기 전에 인접 칸이 보드 안에 있는지 확인합니다. 범위를 벗어나면 명령 전체를 무시합니다. 위치를 이동하거나 주사위를 회전하거나 칸을 읽고 쓰지 않으며, 아무것도 출력하지 않습니다. 유효한 이동이면 주사위를 굴린 뒤 도착 칸과 주사위의 바닥 면을 동기화합니다. 칸의 값이 0이 아니면 그 값을 바닥 면에 복사하고 칸을 0으로 비웁니다. 칸의 값이 0이면 바닥 면의 값을 칸에 복사합니다. 이후 윗면의 값을 출력합니다.

명령은 각각 상수 시간에 처리하므로 전체 시간 복잡도는 `O(K)`입니다. 보드는 `O(NM)` 공간을 사용하고, 주사위 여섯 면은 추가로 `O(1)` 공간을 사용합니다.

## C++17

```cpp
#include <iostream>

using namespace std;

enum Face { TOP, BOTTOM, NORTH, SOUTH, EAST, WEST };
enum Direction { ROLL_EAST = 1, ROLL_WEST, ROLL_NORTH, ROLL_SOUTH };

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, row, col, k;
    cin >> n >> m >> row >> col >> k;

    int board[20][20] = {};
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            cin >> board[i][j];
        }
    }

    int die[6] = {};
    for (int i = 0; i < k; ++i) {
        int command;
        cin >> command;

        int next_row = row;
        int next_col = col;
        if (command == ROLL_EAST) {
            ++next_col;
        } else if (command == ROLL_WEST) {
            --next_col;
        } else if (command == ROLL_NORTH) {
            --next_row;
        } else {
            ++next_row;
        }

        if (next_row < 0 || next_row >= n || next_col < 0 || next_col >= m) {
            continue;
        }

        if (command == ROLL_EAST) {
            const int old_west = die[WEST];
            die[WEST] = die[BOTTOM];
            die[BOTTOM] = die[EAST];
            die[EAST] = die[TOP];
            die[TOP] = old_west;
        } else if (command == ROLL_WEST) {
            const int old_east = die[EAST];
            die[EAST] = die[BOTTOM];
            die[BOTTOM] = die[WEST];
            die[WEST] = die[TOP];
            die[TOP] = old_east;
        } else if (command == ROLL_NORTH) {
            const int old_south = die[SOUTH];
            die[SOUTH] = die[BOTTOM];
            die[BOTTOM] = die[NORTH];
            die[NORTH] = die[TOP];
            die[TOP] = old_south;
        } else {
            const int old_north = die[NORTH];
            die[NORTH] = die[BOTTOM];
            die[BOTTOM] = die[SOUTH];
            die[SOUTH] = die[TOP];
            die[TOP] = old_north;
        }

        row = next_row;
        col = next_col;
        if (board[row][col] != 0) {
            die[BOTTOM] = board[row][col];
            board[row][col] = 0;
        } else {
            board[row][col] = die[BOTTOM];
        }

        cout << die[TOP] << '\n';
    }
}
```
