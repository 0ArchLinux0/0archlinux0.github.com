---
title: BOJ. Cheese (2636)
author: MINJUN PARK
date: 2022-03-01 18:29:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, BFS, Implementation, Cheese, 치즈]
pin: false
lang: ko
translation_key: boj-2636-cheese-melting
permalink: /ko/posts/boj-2636-cheese-melting/
---

[문제](https://www.acmicpc.net/problem/2636)

## 풀이

매 시간 시작할 때 보드 바깥의 빈 테두리에서 BFS를 하여 외부 공기와 연결된
빈칸을 찾습니다. 외부 공기와 인접한 치즈 칸은 해당 시간에 녹습니다. 먼저
녹을 칸을 모두 모은 뒤 한꺼번에 제거하므로, 제거 후에야 새로 노출되는 치즈는
다음 시간까지 녹지 않습니다.

각 녹이기 직전의 전체 치즈 개수를 저장합니다. 한 시간 동안 마지막 치즈가
녹으면 저장해 둔 값이 마지막으로 녹기 직전의 치즈 개수입니다. 처음부터 치즈가
없다면 녹이는 과정이 없으므로 정답은 시간 `0`, 치즈 `0`입니다.

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

    // 바깥쪽 빈 테두리를 추가해 안전한 외부 공기 시작점을 둡니다.
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

        // 외부 공기 탐색이 끝난 다음 이번 시간의 치즈를 동시에 제거합니다.
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

`H`번 녹이는 동안 각 단계에서 `R × C` 보드의 각 칸을 최대 상수 번
방문하므로 시간 복잡도는 `O(H·R·C)`입니다. 보드, 방문/녹임 표시 배열과
작업 큐의 공간 복잡도는 `O(R·C)`입니다.
