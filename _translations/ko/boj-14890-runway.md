---
title: BOJ 14890 — 경사로
author: MINJUN PARK
date: 2022-02-28 22:20:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 구현, 경사로]
pin: false
lang: ko
translation_key: boj-14890-runway
permalink: /ko/posts/boj-14890-runway/
source_permalink: /posts/BOJ-14890/
---

[문제: BOJ 14890 — 경사로](https://www.acmicpc.net/problem/14890) · [English](/posts/BOJ-14890/) · [日本語](/ja/posts/boj-14890-runway/)

각 행이나 열에서 인접한 칸의 높이 차이가 0 또는 1이고, 높이 차이가 1인 곳마다 길이 `L`의 경사로를 놓을 수 있으면 길을 만들 수 있습니다. 경사로 하나는 높이가 낮은 쪽의 `L`개 칸을 차지합니다. 해당 칸들은 모두 같은 높이여야 하고, 길의 범위를 벗어나면 안 되며, 다른 경사로가 이미 사용한 칸이어서도 안 됩니다.

한 줄을 왼쪽에서 오른쪽으로 검사하면서 경사로가 놓인 칸을 기록합니다. 높이가 같은 이웃 칸은 그대로 넘어갑니다. 오르막에서는 경계 바로 앞의 `L`개 칸이 낮은 쪽이고, 내리막에서는 다음 칸부터 `L`개 칸이 낮은 쪽입니다. 범위 안에 있는지, 해당 구간의 높이가 모두 같은지, 이미 사용된 칸과 겹치지 않는지 확인한 뒤 그 칸들을 사용 처리합니다. 이 명시적인 점유 검사는 연속된 내리막에서 같은 칸을 재사용하는 문제를 막습니다. 높이 차이가 1보다 크거나 경사로를 놓을 구간이 없거나 겹침이 있으면 그 줄은 불가능합니다. 이 검사를 모든 행과 열에 적용합니다.

각 구간은 경사로 하나만 놓을 수 있습니다. 경사로 경계의 낮은 쪽 구간은 모두 같은 높이여야 하며, 인접 칸의 높이 차이가 1보다 크면 항상 불가능합니다. `N <= 100`일 때 각 줄의 `N`개 경계를 검사하며 경계마다 최대 `L`칸을 확인하므로 시간 복잡도는 `O(N^2 * L)`입니다. 높이 격자는 `O(N^2)` 공간을 사용하고, 한 줄과 경사로 점유 표시용 임시 공간은 `O(N)`입니다.

## C++17

```cpp
#include <iostream>
#include <vector>

using namespace std;

bool canBuildRunway(const vector<int>& line, int length) {
    const int n = static_cast<int>(line.size());
    vector<bool> used(n, false);

    for (int i = 0; i + 1 < n; ++i) {
        const int difference = line[i + 1] - line[i];
        if (difference == 0) continue;
        if (difference < -1 || difference > 1) return false;

        const int start = difference == 1 ? i - length + 1 : i + 1;
        const int end = difference == 1 ? i : i + length;
        if (start < 0 || end >= n) return false;

        const int lowerHeight = difference == 1 ? line[i] : line[i + 1];
        for (int cell = start; cell <= end; ++cell) {
            if (line[cell] != lowerHeight || used[cell]) return false;
        }
        for (int cell = start; cell <= end; ++cell) {
            used[cell] = true;
        }
    }

    return true;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, length;
    cin >> n >> length;

    vector<vector<int>> height(n, vector<int>(n));
    for (auto& row : height) {
        for (int& cell : row) cin >> cell;
    }

    int answer = 0;
    vector<int> line(n);

    for (int row = 0; row < n; ++row) {
        if (canBuildRunway(height[row], length)) ++answer;
    }

    for (int column = 0; column < n; ++column) {
        for (int row = 0; row < n; ++row) {
            line[row] = height[row][column];
        }
        if (canBuildRunway(line, length)) ++answer;
    }

    cout << answer << '\n';
    return 0;
}
```
