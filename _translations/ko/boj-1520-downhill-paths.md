---
title: 백준 1520번 - 내리막 길
author: MINJUN PARK
date: 2022-03-11 18:28:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, DP, DAG, 내리막 길]
pin: false
lang: ko
translation_key: boj-1520-downhill-paths
permalink: /ko/posts/boj-1520-downhill-paths/
---

[백준 1520번: 내리막 길](https://www.acmicpc.net/problem/1520)

## 풀이: DAG에서의 동적 계획법

각 칸을 정점으로 생각합니다. 한 칸에서 상하좌우로 인접한 칸 중 높이가 더 낮은 곳으로 향하는 방향 간선을 둡니다. 모든 간선은 높이를 낮추므로 방향 사이클은 존재하지 않으며, 이 그래프는 DAG입니다. 높이가 같은 인접 칸 사이에는 간선이 없습니다.

왼쪽 위에서 오른쪽 아래까지 가는 방향 경로의 수를 구해야 합니다. 메모이제이션을 사용하는 재귀 DFS로 점화식을 바로 표현할 수 있지만, 경로에는 최대 $MN=250{,}000$개의 칸이 포함될 수 있습니다. 이만큼 재귀 호출을 하면 호출 스택이 부족해질 위험이 있습니다. 대신 높이가 낮은 칸부터 처리합니다. 이는 원래 그래프의 간선을 뒤집은 그래프에서의 위상 순서입니다.

`dp[r][c]`를 `(r,c)`에서 목적지까지 가는 내리막 경로의 수라고 합시다. 목적지의 값을 1로 둡니다. 목적지에서 목적지까지 이어지는 빈 경로 하나를 세는 것입니다. 칸 `u`를 처리할 때는 더 낮은 모든 이웃이 이미 처리되었습니다. 범위 안에 있는 각 더 높은 이웃 `v`에 `dp[u]`를 더합니다. 이는 간선을 뒤집은 점화식입니다. `v`에서 목적지로 가는 모든 경로는 먼저 더 낮은 이웃으로 이동하며, `u`는 첫 이동이 `v -> u`인 경로를 정확히 `dp[u]`개 제공합니다. 오름차순 처리 순서에서 칸에 도달했을 때는 모든 더 낮은 이웃의 기여가 확정되어 있습니다. 정답은 `dp[0][0]`입니다.

네 방향을 각각 검사하고, 격자 밖으로 나가는 좌표는 건너뜁니다. 높이를 엄격히 비교하므로 높이가 같은 칸으로 이동하는 경우는 제외됩니다. 그런 칸 사이에는 내리막 이동 간선이 없습니다.

## 정확한 정수와 복잡도

공식 제한은 $1 \le M,N \le 500$이며 높이는 1부터 10,000까지입니다. 경로 수는 칸 수로 제한되지 않습니다. 여러 경로가 한 칸에서 합쳐질 수 있으므로 고정 폭 정수에 정답이 들어간다고 보장할 수 없습니다. 구현은 정확한 경로 수를 위해 `boost::multiprecision::cpp_int`를 사용합니다.

$V=MN$, $E$를 높이가 다른 인접 칸 쌍의 수(각 쌍은 방향 간선 하나를 만듭니다), $B$를 경로 수 중 최대 비트 길이라고 합시다. 정렬에는 $O(V\log V)$회의 비교가 필요하고, 격자의 방향 간선은 최대 $4V$개입니다. 임의 정밀도 정수 덧셈은 통상적인 선형 덧셈 비용 모델에서 $O(B)$ 비트 연산이므로, 전체 시간은 $O(V\log V + EB)$입니다. 격자와 정렬 순서 저장에는 $O(V)$개의 항목이 필요하고, 여기에 정수 저장 공간이 더해집니다. 비트 단위로는 DP 값들이 최악의 경우 최대 $O(VB)$ 비트를 차지할 수 있습니다.

## C++17

```cpp
#include <algorithm>
#include <array>
#include <iostream>
#include <vector>
#include <boost/multiprecision/cpp_int.hpp>

using namespace std;
using boost::multiprecision::cpp_int;

struct Cell {
    int height;
    int row;
    int col;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int rows, cols;
    cin >> rows >> cols;

    vector<vector<int>> height(rows, vector<int>(cols));
    vector<Cell> order;
    order.reserve(static_cast<size_t>(rows) * cols);
    for (int r = 0; r < rows; ++r) {
        for (int c = 0; c < cols; ++c) {
            cin >> height[r][c];
            order.push_back({height[r][c], r, c});
        }
    }

    sort(order.begin(), order.end(), [](const Cell& a, const Cell& b) {
        return a.height < b.height;
    });

    vector<vector<cpp_int>> dp(rows, vector<cpp_int>(cols));
    dp[rows - 1][cols - 1] = 1;

    constexpr array<int, 4> dr = {-1, 1, 0, 0};
    constexpr array<int, 4> dc = {0, 0, -1, 1};

    for (const Cell& cell : order) {
        const int r = cell.row;
        const int c = cell.col;
        for (int direction = 0; direction < 4; ++direction) {
            const int nr = r + dr[direction];
            const int nc = c + dc[direction];
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
            if (height[nr][nc] > height[r][c]) {
                dp[nr][nc] += dp[r][c];
            }
        }
    }

    cout << dp[0][0] << '\n';
    return 0;
}
```
