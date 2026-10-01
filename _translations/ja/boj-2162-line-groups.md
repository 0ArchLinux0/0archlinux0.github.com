---
title: BOJ 2162 - 線分グループ
author: MINJUN PARK
date: 2022-03-10 16:54:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 幾何, CCW, Union-Find, 線分グループ]
pin: false
lang: ja
translation_key: boj-2162-line-groups
permalink: /ja/posts/boj-2162-line-groups/
---

[問題リンク](https://www.acmicpc.net/problem/2162)

## モデル化: 交差グラフと連結成分

入力された各線分をグラフの頂点とし、2つの**閉線分が交差するとき**に限り、その頂点間に辺を張ります。グループはこのグラフの連結成分です。直接交差していなくても、交差する線分の列でつながっていれば同じグループに属します。そこで、すべての線分の組を調べ、交差する組を素集合データ構造（DSU）で併合します。すべての判定後、DSUの根の個数がグループ数、最も大きいDSU成分のサイズが最大グループの大きさです。

## 閉線分の正確な交差判定

3点 `A`、`B`、`C` に対する符号付き外積
`(B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x)`
の符号から、`C` が有向直線 `AB` のどちら側にあるかが分かります。正と負は反対側、0は3点が同一直線上にあることを表します。2つの線分が内部で正しく交差するのは、各線分の両端点がもう一方の線分を含む直線に対して**厳密に反対の符号**を持つ場合です。外積同士を掛けず、符号を直接比較します。個々の外積が範囲内でも、その積はオーバーフローする可能性があるためです。

公式の制約は `1 ≤ N ≤ 3,000`、各座標は `[-5,000, 5,000]` です。座標差の絶対値は最大 `10,000` なので、外積を構成する各積は絶対値最大 `10^8`、2つの積の差は絶対値最大 `2 × 10^8` です。これは符号付き64ビット整数 `long long` に安全に収まります。

線分の組は `O(N^2)` 個です。各交差判定は定数時間で、経路圧縮とサイズによる併合を行うDSU演算は償却 `O(α(N))` 時間です。全体の時間計算量は `O(N^2 α(N))`、入力線分とDSU配列に必要な補助空間は `O(N)` です。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Point {
    long long x;
    long long y;
};

struct Segment {
    Point a;
    Point b;
};

long long cross(const Point& a, const Point& b, const Point& c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
}

int orientation(const Point& a, const Point& b, const Point& c) {
    const long long value = cross(a, b, c);
    if (value > 0) return 1;
    if (value < 0) return -1;
    return 0;
}

bool onSegment(const Point& a, const Point& b, const Point& p) {
    return min(a.x, b.x) <= p.x && p.x <= max(a.x, b.x) &&
           min(a.y, b.y) <= p.y && p.y <= max(a.y, b.y);
}

bool oppositeSigns(int a, int b) {
    return (a < 0 && b > 0) || (a > 0 && b < 0);
}

bool intersects(const Segment& first, const Segment& second) {
    const Point& a = first.a;
    const Point& b = first.b;
    const Point& c = second.a;
    const Point& d = second.b;

    const int abC = orientation(a, b, c);
    const int abD = orientation(a, b, d);
    const int cdA = orientation(c, d, a);
    const int cdB = orientation(c, d, b);

    if (oppositeSigns(abC, abD) && oppositeSigns(cdA, cdB)) return true;

    if (abC == 0 && onSegment(a, b, c)) return true;
    if (abD == 0 && onSegment(a, b, d)) return true;
    if (cdA == 0 && onSegment(c, d, a)) return true;
    if (cdB == 0 && onSegment(c, d, b)) return true;
    return false;
}

class DSU {
public:
    explicit DSU(int n) : parent(n), size(n, 1) {
        for (int i = 0; i < n; ++i) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    void unite(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;
        if (size[a] < size[b]) swap(a, b);
        parent[b] = a;
        size[a] += size[b];
    }

    int componentSize(int root) const {
        return size[root];
    }

private:
    vector<int> parent;
    vector<int> size;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<Segment> segments(n);
    for (Segment& segment : segments) {
        cin >> segment.a.x >> segment.a.y >> segment.b.x >> segment.b.y;
    }

    DSU dsu(n);
    for (int i = 0; i < n; ++i) {
        for (int j = i + 1; j < n; ++j) {
            if (intersects(segments[i], segments[j])) dsu.unite(i, j);
        }
    }

    int groupCount = 0;
    int largestGroup = 0;
    for (int i = 0; i < n; ++i) {
        if (dsu.find(i) == i) {
            ++groupCount;
            largestGroup = max(largestGroup, dsu.componentSize(i));
        }
    }

    cout << groupCount << '\n' << largestGroup << '\n';
    return 0;
}
```
