---
title: BOJ 15686 - チキン配達
author: MINJUN PARK
date: 2022-02-22 21:48:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, DFS, 組み合わせ, チキン配達]
pin: false
lang: ja
translation_key: boj-15686-chicken-delivery
permalink: /ja/posts/boj-15686-chicken-delivery/
source_permalink: /posts/BOJ-15686/
---

[問題: BOJ 15686 — チキン配達](https://www.acmicpc.net/problem/15686) · [English](/posts/BOJ-15686/) · [한국어](/ko/posts/boj-15686-chicken-delivery/)

都市には家が `H` 軒、チキン店が `C` 店あります。ちょうど `M` 店を残すとき、各家のチキン距離は、残したチキン店のうち最も近い店までのマンハッタン距離です。都市のチキン距離は全ての家のチキン距離の合計であり、この合計を最小化します。

順列ではなく組み合わせを列挙します。DFSではチキン店のインデックスを昇順に選ぶため、同じ店舗集合を選択順だけ変えて重複計算しません。`start` は次に選べる最小のインデックスで、`selected` は現在選択中のインデックス一覧です。`M` 店を選び終えたら、各家について選択したチキン店までの最短距離を求め、その合計で最小値を更新します。

分岐の前に、`remaining = C - start` をまだ選べる店舗数、`needed = M - selected.size()` をこれから選ぶ必要がある店舗数とします。`remaining < needed` なら必要な数を揃えられないため、その分岐を枝刈りします。残り候補数と必要数が等しい場合、この条件は成立しないので境界の分岐も探索されます。`M = 1` では各店舗を1店ずつ評価し、`M = C` では唯一の組み合わせを評価します。

完成する組み合わせ数は `C` 店から `M` 店を選ぶ場合の数で、各組み合わせの評価には `O(H*M)` 時間かかります。したがって時間計算量は `O((C choose M) * H*M)` です。家の一覧、店舗の一覧、選択中の一覧に使う空間計算量は `O(H+C)` です。距離の合計には `long long` を使います。

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
