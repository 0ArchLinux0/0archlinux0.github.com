---
title: BOJ 17131 - キツネが情報島にやってきた理由
author: MINJUN PARK
date: 2022-02-21 18:01:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 座標圧縮, フェンウィック木, スイープ]
pin: false
lang: ja
translation_key: boj-17131-fox-triples
permalink: /ja/posts/boj-17131-fox-triples/
source_permalink: /posts/BOJ-17131/
---

[問題: BOJ 17131 — キツネが情報島にやってきた理由](https://www.acmicpc.net/problem/17131) · [한국어](/ko/posts/boj-17131-fox-triples/) · [English](/posts/BOJ-17131/)

キツネのトリプルで中央となる点を `p` とすると、1点は `p` より厳密に左、もう1点は厳密に右にあり、どちらの点も `y` 座標が `p` より大きくなければなりません。この条件を満たす左側の点の数を `L(p)`、右側の点の数を `R(p)` とすると、`p` を中央とするトリプルは `L(p) * R(p)` 個です。左右からそれぞれ1点ずつ選ぶ組み合わせが1つのトリプルになるため、この積をすべての点について合計すれば各トリプルをちょうど1回数えられます。

点を `x` の昇順に並べ、同じ `x` を持つ点を1つのグループとして処理します。グループ内の点をフェンウィック木に追加する前に全点をクエリするため、木に含まれるのは `x` が厳密に小さい点だけです。右側の個数は逆順で同じ手順を行って求めます。グループ内をすべてクエリしてからまとめて追加するので、どちらの走査でも `x` が等しい点は除外されます。入力された各点は独立したレコードであり、座標が完全に同じ点も別々の点として数えます。

入力された `y` 座標を順位に座標圧縮します。順位 `r` の点について、処理済みの点のうち `y` が厳密に大きい点の数は `全体数 - prefix(r)` です。`prefix(r)` は順位 `r` までを含むため、同じ `y` 座標も除外できます。座標圧縮により、任意のオフセットを使わず広い座標範囲を扱えます。2回のスイープの計算量は `O(N log N)`、空間計算量は `O(N)` です。個数と積には `long long` を使い、答えは `1,000,000,007` で剰余を取ります。

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
