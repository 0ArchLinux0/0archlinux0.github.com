---
title: BOJ 15686 - 치킨 배달
author: MINJUN PARK
date: 2022-02-22 21:48:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, DFS, 조합, 치킨 배달]
pin: false
lang: ko
translation_key: boj-15686-chicken-delivery
permalink: /ko/posts/boj-15686-chicken-delivery/
source_permalink: /posts/BOJ-15686/
---

[문제: BOJ 15686 — 치킨 배달](https://www.acmicpc.net/problem/15686) · [English](/posts/BOJ-15686/) · [日本語](/ja/posts/boj-15686-chicken-delivery/)

도시에는 집 `H`개와 치킨집 `C`개가 있습니다. 정확히 `M`개의 치킨집을 남길 때, 각 집의 치킨 거리는 남긴 치킨집 중 가장 가까운 곳까지의 맨해튼 거리입니다. 도시의 치킨 거리는 모든 집의 치킨 거리를 합한 값이며, 이 합을 최소화해야 합니다.

순열이 아니라 조합을 열거합니다. DFS는 치킨집 인덱스를 오름차순으로 선택하므로 같은 집합을 순서만 바꾸어 중복 계산하지 않습니다. `start`는 다음 선택에서 허용되는 첫 인덱스이고, `selected`는 현재까지 고른 치킨집 인덱스입니다. `M`개를 모두 골랐으면 각 집을 순회해 선택된 치킨집까지의 최소 거리를 구하고, 그 합으로 최솟값을 갱신합니다.

분기하기 전에 `remaining = C - start`를 아직 선택 가능한 치킨집 수, `needed = M - selected.size()`를 앞으로 골라야 할 수로 둡니다. `remaining < needed`이면 이 분기에서는 필요한 개수를 채울 수 없으므로 가지치기합니다. 남은 후보 수와 필요한 수가 같을 때는 조건을 만족하지 않으므로 경계에 해당하는 분기도 탐색합니다. `M = 1`이면 각 치킨집 하나씩을 평가하고, `M = C`이면 가능한 조합 하나를 평가합니다.

완성된 조합은 `C`개 중 `M`개를 고르는 경우의 수만큼 있으며, 조합 하나를 평가하는 데 `O(H*M)` 시간이 듭니다. 따라서 시간 복잡도는 `O((C choose M) * H*M)`입니다. 집 목록, 치킨집 목록, 현재 선택 목록의 공간 복잡도는 `O(H+C)`입니다. 거리 합산에는 `long long`을 사용합니다.

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <limits>
#include <vector>
using namespace std;

struct Point {
  int row;
  int col;
};

int m;
vector<Point> homes;
vector<Point> stores;
vector<int> selected;
long long answer = numeric_limits<long long>::max();

int manhattan(const Point &a, const Point &b) {
  return abs(a.row - b.row) + abs(a.col - b.col);
}

void search(int start) {
  if (static_cast<int>(selected.size()) == m) {
    long long total = 0;
    for (const Point &home : homes) {
      int nearest = numeric_limits<int>::max();
      for (int index : selected) {
        nearest = min(nearest, manhattan(home, stores[index]));
      }
      total += nearest;
    }
    answer = min(answer, total);
    return;
  }

  const int needed = m - static_cast<int>(selected.size());
  const int remaining = static_cast<int>(stores.size()) - start;
  if (remaining < needed) return;

  for (int i = start; i <= static_cast<int>(stores.size()) - needed; ++i) {
    selected.push_back(i);
    search(i + 1);
    selected.pop_back();
  }
}

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n >> m;
  for (int row = 0; row < n; ++row) {
    for (int col = 0; col < n; ++col) {
      int cell;
      cin >> cell;
      if (cell == 1) homes.push_back({row, col});
      else if (cell == 2) stores.push_back({row, col});
    }
  }

  search(0);
  cout << answer << '\n';
}
```
