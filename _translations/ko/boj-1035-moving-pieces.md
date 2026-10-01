---
title: 백준 1035번 - 조각 움직이기
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [PS, Algorithm, Baekjoon, BOJ, 완전탐색, 백준]
lang: ko
translation_key: boj-1035-moving-pieces
permalink: /ko/posts/boj-1035-moving-pieces/
---

[백준 1035번: 조각 움직이기](https://www.acmicpc.net/problem/1035)

## 풀이: 배치 상태를 정점으로 하는 BFS

보드는 25칸이고 조각은 최대 5개입니다. 한 상태는 조각 전체의 위치를 나타내야 합니다. 각 칸의 점유 여부를 25비트 정수로 표현하고, `(r, c)` 칸에 조각이 있으면 `r * 5 + c`번째 비트를 1로 둡니다. 조각은 서로 구별되지 않으므로 순서를 따로 정할 필요가 없습니다.

상태 그래프의 간선은 합법적인 한 번의 이동입니다. 조각 하나를 선택해 상하좌우로 인접한 빈칸으로 옮깁니다. 이 조건을 무시하면 안 됩니다. 목표 칸을 정한 뒤 맨해튼 거리의 합을 구하는 것만으로는 충분하지 않습니다. 각 조각의 최단 경로가 서로 충돌할 수 있고, 목적지 집합만으로는 그만큼의 합법적인 이동으로 실제 도달 가능한지 알 수 없습니다.

목표 상태는 모든 조각이 상하좌우 인접 관계로 하나의 연결 요소를 이루는 상태입니다. 점유된 칸 하나에서 시작해 조각이 있는 이웃 칸만 따라가며 탐색하고, 방문한 조각 수가 전체 조각 수와 같은지 확인합니다.

초기 배치에서 BFS를 시작하고, 상태를 큐에 넣을 때 방문 처리합니다. 간선마다 이동 횟수가 정확히 1 증가하므로 BFS는 이동 횟수가 작은 상태부터 방문합니다. 따라서 큐에서 처음 꺼낸 목표 상태의 이동 횟수가 최솟값입니다. 처음 배치가 이미 연결되어 있으면 답은 0입니다.

조각이 1개 이상 5개 이하인 서로 다른 배치는 최대

$$\sum_{k=1}^{5} \binom{25}{k} = 68{,}405$$

개입니다. 아래 코드는 가능한 모든 $2^{25}$개 마스크에 대한 거리 배열을 만들지 않고, 실제 도달한 배치만 `unordered_set`에 저장합니다. 상태마다 후보 이동은 최대 20개(조각 5개 × 이웃 4칸)입니다.

## C++17

```cpp
#include <array>
#include <cstdint>
#include <iostream>
#include <queue>
#include <string>
#include <unordered_set>
#include <utility>

using namespace std;

using Mask = uint32_t;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    array<uint32_t, 25> neighbors{};
    for (int r = 0; r < 5; ++r) {
        for (int c = 0; c < 5; ++c) {
            const int cell = r * 5 + c;
            if (r > 0) neighbors[cell] |= uint32_t{1} << (cell - 5);
            if (r < 4) neighbors[cell] |= uint32_t{1} << (cell + 5);
            if (c > 0) neighbors[cell] |= uint32_t{1} << (cell - 1);
            if (c < 4) neighbors[cell] |= uint32_t{1} << (cell + 1);
        }
    }

    Mask start = 0;
    for (int r = 0; r < 5; ++r) {
        string row;
        cin >> row;
        for (int c = 0; c < 5; ++c) {
            if (row[c] == '*') start |= Mask{1} << (r * 5 + c);
        }
    }

    auto isConnected = [&](Mask pieces) {
        const int pieceCount = __builtin_popcount(pieces);
        Mask reached = 0;
        Mask frontier = pieces & (~pieces + 1);  // 가장 낮은 점유 비트
        while (frontier != 0) {
            const int cell = __builtin_ctz(frontier);
            const Mask bit = Mask{1} << cell;
            frontier &= ~bit;
            if (reached & bit) continue;
            reached |= bit;
            frontier |= neighbors[cell] & pieces & ~reached;
        }
        return __builtin_popcount(reached) == pieceCount;
    };

    queue<pair<Mask, int>> q;
    unordered_set<Mask> visited;
    q.push({start, 0});
    visited.insert(start);

    while (!q.empty()) {
        const auto [state, moves] = q.front();
        q.pop();

        if (isConnected(state)) {
            cout << moves << '\n';
            return 0;
        }

        for (Mask pieces = state; pieces != 0; pieces &= pieces - 1) {
            const int from = __builtin_ctz(pieces);
            const Mask fromBit = Mask{1} << from;
            Mask destinations = neighbors[from] & ~state;
            while (destinations != 0) {
                const int to = __builtin_ctz(destinations);
                const Mask toBit = Mask{1} << to;
                destinations &= ~toBit;

                const Mask next = (state & ~fromBit) | toBit;
                if (visited.insert(next).second) q.push({next, moves + 1});
            }
        }
    }
}
```

보드가 연결되어 있고 빈칸을 통해 조각을 옮길 수 있으므로, 유효한 입력에서는 연결된 배치에 도달할 수 있습니다.
