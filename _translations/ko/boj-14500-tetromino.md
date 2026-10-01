---
title: BOJ 14500 - 테트로미노
author: MINJUN PARK
date: 2022-02-24 21:48:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, DFS, 구현, 테트로미노]
pin: false
lang: ko
translation_key: boj-14500-tetromino
permalink: /ko/posts/boj-14500-tetromino/
source_permalink: /posts/BOJ-14500/
---

[문제: BOJ 14500 — 테트로미노](https://www.acmicpc.net/problem/14500) · [English](/posts/BOJ-14500/) · [日本語](/ja/posts/boj-14500-tetromino/)

`N × M` 크기의 보드에서 변을 공유하며 연결된 네 칸을 덮는 테트로미노를 놓을 때, 덮인 칸의 합 중 최댓값을 구합니다. 다섯 가지 테트로미노의 모든 회전과 대칭 배치를 고려해야 합니다.

인접한 칸을 하나씩 추가하며 단순 경로를 깊이 우선 탐색하면 막대, L, S, Z 모양은 찾을 수 있지만 T 모양은 만들 수 없습니다. T 모양의 세 갈래 접점에서는 한 경로만 따라가는 대신 가지가 필요하기 때문입니다. 따라서 길이 4인 모든 단순 경로를 탐색하고, 각 칸을 중심으로 하는 T 모양 네 방향을 별도로 검사합니다. 칸을 읽기 전에 보드 범위를 확인하므로 폭이 얇은 보드와 가장자리 배치도 안전하게 처리됩니다.

각 칸에서 깊이가 4인 탐색의 분기 수는 상수이며, T 방향도 네 개뿐이므로 전체 시간 복잡도는 모양 개수에 따른 상수를 포함한 `O(NM)`입니다. 칸의 값과 합은 `long long`으로 다뤄 합산 중 오버플로를 방지합니다.

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int n, m;
vector<vector<long long>> board;
vector<vector<bool>> visited;
long long answer = 0;

const int dr[4] = {-1, 1, 0, 0};
const int dc[4] = {0, 0, -1, 1};

void dfs(int r, int c, int count, long long sum) {
    if (count == 4) {
        answer = max(answer, sum);
        return;
    }

    for (int d = 0; d < 4; ++d) {
        int nr = r + dr[d];
        int nc = c + dc[d];
        if (nr < 0 || nr >= n || nc < 0 || nc >= m || visited[nr][nc]) continue;

        visited[nr][nc] = true;
        dfs(nr, nc, count + 1, sum + board[nr][nc]);
        visited[nr][nc] = false;
    }
}

void checkT(int r, int c) {
    const int shapes[4][3][2] = {
        {{0, -1}, {0, 1}, {-1, 0}},
        {{0, -1}, {0, 1}, {1, 0}},
        {{-1, 0}, {1, 0}, {0, -1}},
        {{-1, 0}, {1, 0}, {0, 1}}
    };

    for (const auto& shape : shapes) {
        long long sum = board[r][c];
        bool valid = true;
        for (const auto& offset : shape) {
            int nr = r + offset[0];
            int nc = c + offset[1];
            if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                valid = false;
                break;
            }
            sum += board[nr][nc];
        }
        if (valid) answer = max(answer, sum);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> m;
    board.assign(n, vector<long long>(m));
    visited.assign(n, vector<bool>(m, false));
    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) cin >> board[r][c];
    }

    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) {
            visited[r][c] = true;
            dfs(r, c, 1, board[r][c]);
            visited[r][c] = false;
            checkT(r, c);
        }
    }

    cout << answer << '\n';
    return 0;
}
```
