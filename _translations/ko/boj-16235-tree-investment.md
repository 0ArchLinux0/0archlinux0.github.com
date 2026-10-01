---
title: BOJ 16235 — 나무 재테크
author: MINJUN PARK
date: 2022-03-04 00:29:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 구현, 나무 재테크]
pin: false
lang: ko
translation_key: boj-16235-tree-investment
permalink: /ko/posts/boj-16235-tree-investment/
source_permalink: /posts/BOJ-16235/
---

[문제: BOJ 16235 — 나무 재테크](https://www.acmicpc.net/problem/16235) · [English](/posts/BOJ-16235/) · [日本語](/ja/posts/boj-16235-tree-investment/)

매년 봄, 여름, 가을, 겨울 순서로 진행합니다. 봄에는 각 칸의 나무를 나이가 어린 순으로 처리합니다. 나무는 나이만큼 양분을 소비한 뒤 나이가 1 증가합니다. 양분이 부족하면 그 나무와 같은 칸에서 아직 처리하지 않은 더 나이 많은 나무가 모두 죽으므로, 그 칸의 처리를 멈춥니다. 여름에는 죽은 나무마다 나이의 `floor(나이 / 2)`만큼 양분을 되돌립니다. 가을에는 나이가 5의 배수인 나무가 농장 안의 인접한 여덟 칸에 나이 1인 나무를 하나씩 번식시킵니다. 마지막으로 겨울에는 각 칸에 정해진 양분을 추가합니다.

각 칸의 나이를 오름차순으로 저장하면 봄 처리는 앞에서부터 진행할 수 있습니다. 양분이 모자라지는 시점부터는 그 나무와 뒤에 남은 나무가 모두 죽으므로, 뒤에서부터 죽은 나무의 나이를 읽어 여름에 돌려줄 양분을 합산합니다. 이 방식은 중간 삭제를 피하면서 죽는 나무를 정확히 처리합니다. 아래 코드는 칸마다 나이 정렬된 `vector`를 사용하고, 봄에 살아남은 나무의 수만큼 크기를 줄여 죽은 나무를 제거합니다. 가을 번식 수는 별도 격자에 먼저 모으므로 새로 태어난 나무가 같은 가을에 다시 번식하지 않습니다. 번식한 나이 1 나무는 앞쪽에 넣어 다음 봄에도 정렬 순서를 유지합니다.

`N <= 10`, `K <= 1000`입니다. 각 해마다 나무가 없는 칸까지 방문하는 데 `O(N^2)`이 걸리고, 나무 처리는 나무 수에 비례합니다. 가을 번식은 칸별로 횟수를 모아 `vector`에 한 번에 삽입합니다. 번식하는 나무 한 그루는 최대 8그루를 만들 수 있으므로 삽입을 포함한 전체 시간은 `O(N^2K + Σ_y T_y)`입니다. 여기서 `T_y`는 y년의 시작 시점 나무 수입니다. 공간 복잡도는 `O(N^2 + T_max)`이며, 나무 개체 수가 급격히 늘 수 있어 `N`, `M`, `K`만으로 고정 상한을 제시할 수 없습니다.

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, years;
    cin >> n >> m >> years;

    vector<vector<int>> winterFood(n, vector<int>(n));
    for (auto& row : winterFood) {
        for (int& food : row) cin >> food;
    }

    vector<vector<int>> food(n, vector<int>(n, 5));
    vector<vector<vector<int>>> trees(n, vector<vector<int>>(n));
    for (int i = 0; i < m; ++i) {
        int row, column, age;
        cin >> row >> column >> age;
        trees[--row][--column].push_back(age);
    }
    for (auto& row : trees) {
        for (auto& cell : row) sort(cell.begin(), cell.end());
    }

    constexpr int dr[8] = {-1, -1, -1, 0, 0, 1, 1, 1};
    constexpr int dc[8] = {-1, 0, 1, -1, 1, -1, 0, 1};
    vector<vector<int>> deadFood(n, vector<int>(n));

    for (int year = 0; year < years; ++year) {
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                auto& cell = trees[r][c];
                int alive = 0;
                deadFood[r][c] = 0;
                while (alive < static_cast<int>(cell.size()) &&
                       food[r][c] >= cell[alive]) {
                    food[r][c] -= cell[alive];
                    ++cell[alive];
                    ++alive;
                }
                for (int i = static_cast<int>(cell.size()) - 1; i >= alive; --i) {
                    deadFood[r][c] += cell[i] / 2;
                }
                cell.resize(alive);
            }
        }

        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) food[r][c] += deadFood[r][c];
        }

        vector<vector<int>> offspring(n, vector<int>(n));
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                for (int age : trees[r][c]) {
                    if (age % 5 != 0) continue;
                    for (int d = 0; d < 8; ++d) {
                        const int nr = r + dr[d];
                        const int nc = c + dc[d];
                        if (0 <= nr && nr < n && 0 <= nc && nc < n) {
                            ++offspring[nr][nc];
                        }
                    }
                }
            }
        }
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                trees[r][c].insert(trees[r][c].begin(), offspring[r][c], 1);
                food[r][c] += winterFood[r][c];
            }
        }
    }

    int answer = 0;
    for (const auto& row : trees) {
        for (const auto& cell : row) answer += cell.size();
    }
    cout << answer << '\n';
    return 0;
}
```
