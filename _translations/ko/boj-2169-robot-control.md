---
title: BOJ 2169 — 로봇 조종하기
author: MINJUN PARK
date: 2022-02-26 02:41:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, DP, 로봇 조종하기]
pin: false
lang: ko
translation_key: boj-2169-robot-control
permalink: /ko/posts/boj-2169-robot-control/
source_permalink: /posts/BOJ-2169/
---

[문제: BOJ 2169 — 로봇 조종하기](https://www.acmicpc.net/problem/2169) · [English](/posts/BOJ-2169/) · [日本語](/ja/posts/boj-2169-robot-control/)

로봇은 `N × M` 격자의 왼쪽 위 칸에서 출발해 오른쪽 아래 칸에 도착해야 합니다. 방문한 각 칸의 값을 점수에 더합니다. 로봇은 왼쪽, 오른쪽, 아래쪽으로만 이동할 수 있고 위쪽으로는 이동할 수 없으며, 같은 칸을 두 번 방문할 수도 없습니다. 목표는 방문한 칸의 값 합을 최대로 만드는 것입니다.

핵심은 격자를 한 행씩 처리하는 것입니다. 경로는 위쪽에서 현재 행으로 들어오며, 현재 행을 지나면 아래쪽으로 나가야 합니다. 위로 이동할 수 없으므로 한 행 안에서의 수평 이동은 한 방향으로만 이루어집니다. 양쪽 방향으로 모두 움직이면 이미 방문한 칸을 다시 지나게 되기 때문입니다. 따라서 현재 행의 각 칸에 도달하는 최선의 경로는 위쪽 칸에서 내려오거나, 현재 행의 이웃 칸에서 수평으로 이어집니다.

행마다 `above[c]`를 이전 행에서 열 `c`까지 도달하는 최선의 점수라고 합시다. 왼쪽에서 오른쪽으로 훑으면 위에서 내려오거나 왼쪽에서 이어지는 최선의 점수를 계산할 수 있습니다. 오른쪽에서 왼쪽으로 훑으면 위에서 내려오거나 오른쪽에서 이어지는 경우를 계산합니다. 두 훑기의 결과를 각 칸에서 원소별 최댓값으로 합치면 그 행의 최선 점수가 됩니다. 첫 행은 가장 왼쪽 칸에서만 시작하도록 초기화해 시작점 이외의 위치에서 경로가 시작되는 일을 막습니다.

도달할 수 없는 상태는 음의 무한대 값으로 초기화하므로, 음수 칸도 미방문 또는 기본 점수로 잘못 취급되지 않습니다. 이전 행과 두 방향 훑기 배열만 유지하므로 시간 복잡도는 `O(NM)`, 보조 공간 복잡도는 `O(M)`입니다.

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <limits>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<long long>> value(n, vector<long long>(m));
    for (auto& row : value) {
        for (long long& cell : row) cin >> cell;
    }

    constexpr long long NEG = numeric_limits<long long>::lowest() / 4;
    vector<long long> above(m, NEG), leftToRight(m), rightToLeft(m);

    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) {
            const long long fromAbove = above[c];
            const long long fromLeft = (c > 0) ? leftToRight[c - 1] : NEG;
            if (r == 0 && c == 0) {
                leftToRight[c] = value[r][c];
            } else {
                leftToRight[c] = max(fromAbove, fromLeft) + value[r][c];
            }
        }

        for (int c = m - 1; c >= 0; --c) {
            const long long fromAbove = above[c];
            const long long fromRight = (c + 1 < m) ? rightToLeft[c + 1] : NEG;
            if (r == 0 && c == 0) {
                rightToLeft[c] = value[r][c];
            } else {
                rightToLeft[c] = max(fromAbove, fromRight) + value[r][c];
            }
        }

        for (int c = 0; c < m; ++c) {
            above[c] = max(leftToRight[c], rightToLeft[c]);
        }
    }

    cout << above[m - 1] << '\n';
    return 0;
}
```
