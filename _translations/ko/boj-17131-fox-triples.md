---
title: BOJ 17131 - 여우가 정보섬에 올라온 이유
author: MINJUN PARK
date: 2022-02-21 18:01:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 좌표 압축, 펜윅 트리, 스위핑]
pin: false
lang: ko
translation_key: boj-17131-fox-triples
permalink: /ko/posts/boj-17131-fox-triples/
source_permalink: /posts/BOJ-17131/
---

[문제: BOJ 17131 — 여우가 정보섬에 올라온 이유](https://www.acmicpc.net/problem/17131) · [English](/posts/BOJ-17131/) · [日本語](/ja/posts/boj-17131-fox-triples/)

여우 트리플에서 가운데 점을 `p`라고 하면, 한 점은 `p`보다 엄격히 왼쪽에 있고 다른 한 점은 엄격히 오른쪽에 있어야 합니다. 두 점의 `y` 좌표는 모두 `p`보다 커야 합니다. 이 조건을 만족하며 왼쪽에 있는 점의 수를 `L(p)`, 오른쪽에 있는 점의 수를 `R(p)`라 하면, `p`를 가운데로 하는 트리플은 `L(p) * R(p)`개입니다. 양쪽에서 하나씩 고르는 모든 조합이 하나의 트리플을 이루므로, 이 값을 모든 점에 대해 합하면 각 트리플을 정확히 한 번 셉니다.

점을 `x` 오름차순으로 정렬하고, 같은 `x`를 가진 점을 한 그룹으로 처리합니다. 펜윅 트리에 그룹의 점을 넣기 전에 그룹 내 모든 점을 먼저 질의하면, 트리에는 `x`가 엄격히 작은 점만 들어 있습니다. 오른쪽 개수를 구할 때는 반대 방향으로 같은 절차를 수행합니다. 같은 그룹을 질의한 뒤 한꺼번에 추가하므로 두 방향 모두 `x`가 같은 점은 제외됩니다. 입력의 각 점은 별개의 기록이므로 좌표가 완전히 같은 점도 서로 다른 점으로 셉니다.

입력된 `y` 좌표를 순위로 좌표 압축합니다. 순위가 `r`인 점에 대해 이미 처리된 점 중 `y`가 엄격히 큰 점의 수는 `전체 개수 - prefix(r)`입니다. `prefix(r)`는 순위 `r`까지 포함하므로 같은 `y` 좌표도 제외됩니다. 좌표 압축 덕분에 임의의 오프셋 없이 넓은 좌표 범위를 처리할 수 있습니다. 두 번의 스위핑은 `O(N log N)` 시간, `O(N)` 공간을 사용합니다. 개수와 곱은 `long long`으로 계산하고, 누적 답은 `1,000,000,007`로 나머지 연산합니다.

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

constexpr long long MOD = 1'000'000'007LL;

struct Point {
  int x;
  int y;
  int rank;
  long long left = 0;
  long long right = 0;
};

class Fenwick {
 public:
  explicit Fenwick(int n) : tree(n + 1, 0) {}

  void add(int index) {
    for (int i = index; i < static_cast<int>(tree.size()); i += i & -i) {
      ++tree[i];
    }
  }

  long long prefixSum(int index) const {
    long long result = 0;
    for (int i = index; i > 0; i -= i & -i) result += tree[i];
    return result;
  }

 private:
  vector<long long> tree;
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<Point> points(n);
  vector<int> ys;
  ys.reserve(n);
  for (Point &point : points) {
    cin >> point.x >> point.y;
    ys.push_back(point.y);
  }

  sort(ys.begin(), ys.end());
  ys.erase(unique(ys.begin(), ys.end()), ys.end());
  for (Point &point : points) {
    point.rank = lower_bound(ys.begin(), ys.end(), point.y) - ys.begin() + 1;
  }
  sort(points.begin(), points.end(),
       [](const Point &a, const Point &b) { return a.x < b.x; });

  Fenwick bit(static_cast<int>(ys.size()));
  long long total = 0;
  for (int first = 0; first < n;) {
    int last = first;
    while (last < n && points[last].x == points[first].x) ++last;
    for (int i = first; i < last; ++i) {
      points[i].left = total - bit.prefixSum(points[i].rank);
    }
    for (int i = first; i < last; ++i) {
      bit.add(points[i].rank);
      ++total;
    }
    first = last;
  }

  bit = Fenwick(static_cast<int>(ys.size()));
  total = 0;
  for (int last = n; last > 0;) {
    int first = last - 1;
    while (first > 0 && points[first - 1].x == points[last - 1].x) --first;
    for (int i = first; i < last; ++i) {
      points[i].right = total - bit.prefixSum(points[i].rank);
    }
    for (int i = first; i < last; ++i) {
      bit.add(points[i].rank);
      ++total;
    }
    last = first;
  }

  long long answer = 0;
  for (const Point &point : points) {
    answer = (answer + point.left * point.right) % MOD;
  }
  cout << answer << '\n';
}
```
