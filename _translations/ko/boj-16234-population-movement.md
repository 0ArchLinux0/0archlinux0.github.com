---
title: BOJ 16234 — 인구 이동
author: MINJUN PARK
date: 2022-02-25 19:38:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, BFS, 구현, 인구 이동]
pin: false
lang: ko
translation_key: boj-16234-population-movement
permalink: /ko/posts/boj-16234-population-movement/
source_permalink: /posts/BOJ-16234/
---

[문제: BOJ 16234 — 인구 이동](https://www.acmicpc.net/problem/16234) · [English](/posts/BOJ-16234/) · [日本語](/ja/posts/boj-16234-population-movement/)

하루 동안 인접한 두 나라의 인구 차이가 `L` 이상 `R` 이하이면 국경을 엽니다. 열린 국경을 통해 서로 연결된 나라들이 연합을 이루며, 나라가 둘 이상인 각 연합의 모든 나라는 연합 인구의 평균을 소수점 아래를 버려 적용합니다. 그날의 모든 연합은 인구를 갱신하기 전 격자를 기준으로 찾은 뒤 함께 갱신합니다. 국경이 하나도 열리지 않는 날 시뮬레이션을 멈추며, 답은 연합이 하나 이상 형성된 날의 수입니다.

국경 조건이 `L <= |A - B| <= R`인 이유는 인구 차이가 `L`보다 작지도, `R`보다 크지도 않을 때에만 국경을 열기 때문입니다. 양 끝값도 조건에 포함됩니다. 방문하지 않은 나라에서 BFS를 시작해 열린 국경으로 도달할 수 있는 나라를 모두 모으면 연합 하나를 얻습니다. 모든 연결 요소를 찾은 뒤, 둘 이상의 나라로 이루어진 각 연합의 평균을 계산해 해당 나라들에 적용합니다. 나라 하나뿐인 요소는 그대로 둡니다. 둘 이상의 나라가 포함된 연합이 있을 때만 날짜를 증가시킵니다. 시뮬레이션을 시작하기 전에 `N`, `L`, `R`과 전체 인구 격자를 모두 입력받아야 합니다.

나라가 `N^2`개이고 하루에 각 나라와 인접 국경을 상수 번 검사하므로 하루의 시간 복잡도는 `O(N^2)`입니다. 인구 격자, 방문 표시, 큐와 연결 요소 목록을 포함한 공간 복잡도는 `O(N^2)`입니다.

## C++17

```cpp
#include <cstdlib>
#include <iostream>
#include <queue>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, lower, upper;
    cin >> n >> lower >> upper;

    vector<vector<int>> population(n, vector<int>(n));
    for (auto& row : population) {
        for (int& value : row) cin >> value;
    }

    const int dr[4] = {-1, 1, 0, 0};
    const int dc[4] = {0, 0, -1, 1};
    int days = 0;

    while (true) {
        vector<vector<bool>> visited(n, vector<bool>(n, false));
        vector<vector<pair<int, int>>> unions;

        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col) {
                if (visited[row][col]) continue;

                queue<pair<int, int>> pending;
                vector<pair<int, int>> component;
                pending.push({row, col});
                visited[row][col] = true;

                while (!pending.empty()) {
                    const auto [currentRow, currentCol] = pending.front();
                    pending.pop();
                    component.push_back({currentRow, currentCol});

                    for (int direction = 0; direction < 4; ++direction) {
                        const int nextRow = currentRow + dr[direction];
                        const int nextCol = currentCol + dc[direction];
                        if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n) continue;
                        if (visited[nextRow][nextCol]) continue;

                        const int difference = abs(population[currentRow][currentCol] - population[nextRow][nextCol]);
                        if (lower <= difference && difference <= upper) {
                            visited[nextRow][nextCol] = true;
                            pending.push({nextRow, nextCol});
                        }
                    }
                }

                if (component.size() > 1) unions.push_back(move(component));
            }
        }

        if (unions.empty()) break;

        for (const auto& component : unions) {
            int total = 0;
            for (const auto& [row, col] : component) total += population[row][col];
            const int average = total / static_cast<int>(component.size());
            for (const auto& [row, col] : component) population[row][col] = average;
        }
        ++days;
    }

    cout << days << '\n';
    return 0;
}
```
