---
title: BOJ 16236 - 아기 상어
author: MINJUN PARK
date: 2022-02-23 19:22:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 아기 상어, BFS]
pin: false
lang: ko
translation_key: boj-16236-baby-shark
permalink: /ko/posts/boj-16236-baby-shark/
source_permalink: /posts/BOJ-16236/
---

[문제: BOJ 16236 — 아기 상어](https://www.acmicpc.net/problem/16236) · [English](/posts/BOJ-16236/) · [日本語](/ja/posts/boj-16236-baby-shark/)

아기 상어가 현재 위치에서 출발해 먹을 수 있는 물고기를 찾을 때마다 BFS를 실행합니다. 상어보다 큰 물고기가 있는 칸은 지나갈 수 없고, 빈칸과 상어보다 작거나 같은 물고기가 있는 칸은 지나갈 수 있습니다. 먹을 수 있는 물고기는 상어보다 크기가 엄격히 작아야 합니다. 따라서 크기가 같은 물고기는 지나갈 수만 있고 먹을 수는 없습니다.

BFS는 거리가 가까운 칸부터 방문합니다. 먹을 수 있는 물고기를 처음 발견한 뒤에는 같은 거리의 나머지 칸만 확인하면 최단 거리 후보를 모두 비교할 수 있습니다. 후보 중 행이 가장 작고, 행도 같으면 열이 가장 작은 물고기를 선택합니다. 즉 거리, 위쪽, 왼쪽 순서의 조건을 그대로 적용합니다. 물고기를 먹으면 해당 칸을 비우고 이동 거리를 총 시간에 더한 뒤, 그 위치에서 새 BFS를 시작합니다. 방문 정보는 매번 새로 초기화합니다.

현재 크기와 같은 수의 물고기를 먹으면 상어의 크기가 1 커지고 먹은 수를 0으로 되돌립니다. BFS로 먹을 수 있는 물고기를 찾지 못하면 시뮬레이션을 종료합니다. 한 번의 BFS는 `O(N^2)` 시간과 `O(N^2)` 공간을 사용합니다. 먹은 물고기의 수를 `F`라 하면 전체 시간 복잡도는 `O(F N^2)`입니다.

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <queue>
#include <utility>
#include <vector>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<vector<int>> grid(n, vector<int>(n));
  int sharkRow = 0;
  int sharkCol = 0;

  for (int r = 0; r < n; ++r) {
    for (int c = 0; c < n; ++c) {
      cin >> grid[r][c];
      if (grid[r][c] == 9) {
        sharkRow = r;
        sharkCol = c;
        grid[r][c] = 0;
      }
    }
  }

  const int dr[] = {-1, 0, 0, 1};
  const int dc[] = {0, -1, 1, 0};
  int sharkSize = 2;
  int eaten = 0;
  int elapsed = 0;

  while (true) {
    vector<vector<int>> distance(n, vector<int>(n, -1));
    queue<pair<int, int>> q;
    q.push({sharkRow, sharkCol});
    distance[sharkRow][sharkCol] = 0;

    int preyRow = -1;
    int preyCol = -1;
    int preyDistance = -1;

    while (!q.empty()) {
      const auto [r, c] = q.front();
      q.pop();
      const int d = distance[r][c];
      if (preyDistance != -1 && d > preyDistance) break;

      if (grid[r][c] > 0 && grid[r][c] < sharkSize) {
        if (preyRow == -1 || r < preyRow || (r == preyRow && c < preyCol)) {
          preyRow = r;
          preyCol = c;
          preyDistance = d;
        }
        continue;
      }

      for (int direction = 0; direction < 4; ++direction) {
        const int nr = r + dr[direction];
        const int nc = c + dc[direction];
        if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
        if (distance[nr][nc] != -1 || grid[nr][nc] > sharkSize) continue;
        distance[nr][nc] = d + 1;
        q.push({nr, nc});
      }
    }

    if (preyRow == -1) break;

    elapsed += preyDistance;
    sharkRow = preyRow;
    sharkCol = preyCol;
    grid[sharkRow][sharkCol] = 0;
    ++eaten;
    if (eaten == sharkSize) {
      ++sharkSize;
      eaten = 0;
    }
  }

  cout << elapsed;
}
```
