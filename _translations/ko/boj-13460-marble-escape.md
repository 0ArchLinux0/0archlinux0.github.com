---
title: BOJ 13460 — 구슬 탈출 2
author: MINJUN PARK
date: 2022-02-25 00:59:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, BFS, 구현, 구슬 탈출 2]
pin: false
lang: ko
translation_key: boj-13460-marble-escape
permalink: /ko/posts/boj-13460-marble-escape/
source_permalink: /posts/BOJ-13460/
---

[문제: BOJ 13460 — 구슬 탈출 2](https://www.acmicpc.net/problem/13460) · [English](/posts/BOJ-13460/) · [日本語](/ja/posts/boj-13460-marble-escape/)

보드에는 벽, 구멍, 빨간 구슬과 파란 구슬이 있습니다. 한 방향으로 기울이면 구슬은 벽에 막히거나 구멍에 빠질 때까지 움직입니다. 최대 10번 기울여 빨간 구슬을 구멍에 넣되 파란 구슬은 빠뜨리지 않는 것이 목표입니다.

상태는 두 구슬의 현재 칸 `(빨간 구슬, 파란 구슬)`입니다. 각 상태에서 네 방향을 각각 시도합니다. 기울이는 방향으로 더 앞에 있는 구슬을 먼저 움직여야 합니다. 그렇지 않으면 뒤 구슬이 움직이기 전에 앞 구슬을 잘못 막을 수 있습니다. 앞 구슬을 벽이나 구멍에 닿을 때까지 굴린 다음, 앞 구슬의 최종 위치를 장애물로 취급해 뒤 구슬을 굴립니다. 구멍에 빠진 구슬은 보드에서 사라지므로 다른 구슬의 이동을 막지 않습니다. 이렇게 순서대로 움직이면 두 구슬이 같은 칸에 겹치지 않습니다. 구슬이 전혀 움직이지 않는 기울임도 전이로 볼 수 있지만, 방문 기록이 반복 탐색을 막습니다.

파란 구슬이 빠지는 상태는 버립니다. 빨간 구슬만 빠졌다면 현재 BFS 거리(기울인 횟수)가 정답입니다. 둘 다 빠지지 않았다면 아직 방문하지 않은 두 위치 쌍을 큐에 넣습니다. BFS는 기울인 횟수가 작은 상태부터 탐색하므로 처음 찾은 성공이 최소 횟수입니다. 깊이가 10인 상태에서는 더 확장하지 않으며, 성공을 찾지 못하면 `-1`을 출력합니다.

구슬마다 가능한 칸은 최대 `N * M`개이므로 위치 쌍은 최대 `O((NM)^2)`개이고, 방향 하나로 굴리는 데는 최대 `O(NM)`칸을 확인합니다. 보드는 최대 10행 10열이므로 상태 그래프는 작고 유한합니다. 일반적인 전이 탐색 비용은 상태마다 `O(4 * NM)`, 방문 상태 공간은 `O((NM)^2)`입니다.

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
