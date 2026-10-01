---
title: BOJ 19565 - 数列の作成
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [アルゴリズム, 構成的アルゴリズム, オイラー閉路, Hierholzer のアルゴリズム, BOJ 19565, 数列]
lang: ja
translation_key: boj-19565-sequence
permalink: /ja/posts/boj-19565-sequence/
pin: false
---

[BOJ 19565 - 数列の作成](https://www.acmicpc.net/problem/19565)

## 有向グラフによるモデル化

数列の各要素は $1,\dots,N$ のいずれかで、先頭と末尾はどちらも 1 でなければならない。また、同じ順序付き隣接ペアを二度以上使うことはできない。$(x,y)$ と $(y,x)$ は異なるペアであり、$(x,x)$ のように同じ値からなるペアも使える。

値ごとに頂点を一つ作り、すべての順序付きペア $(x,y)$ に対して有向辺 $x\to y$ を追加する。各頂点から自分自身へのループも含める。1 から始まり 1 に戻る数列は、頂点 1 から始まる閉じた歩道に対応し、数列の各隣接ペアは歩道で通った辺そのものとなる。したがって、辺を重複して通らなければ、隣接ペアも重複しない。

各頂点の入次数と出次数はともに $N$ である。任意の頂点から任意の頂点へ辺があるため、このグラフは強連結である。よってオイラー閉路が存在する。Hierholzer のアルゴリズムで $N^2$ 本の辺をすべて一度ずつ使う閉路を求められる。頂点 1 から探索を始めれば、得られる数列は 1 から始まり 1 で終わる。

## 正しさと最大長

オイラー閉路は各有向辺をちょうど一度通るため、連続する頂点のペアはすべて異なり、可能な順序付きペアをすべて含む。辺は $N^2$ 本なので、頂点列としての数列の長さは $N^2+1$ となる。

一方、$1,\dots,N$ から作れる順序付きペアは、$N$ 個のループを含めても合計 $N^2$ 個しかない。有効な数列では各ペアを高々一度しか使えないため、隣接ペアは最大 $N^2$ 個、数列の長さは最大 $N^2+1$ である。オイラー閉路による構成はこの上限に達するので、最大長である。

各頂点から出る辺を隣接リストに格納する。Hierholzer のアルゴリズムは各辺を一度処理するため、時間計算量は $O(N^2)$、グラフと結果の数列に必要な空間も $O(N^2)$ である。

## C++17 コード

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<vector<int>> adj(n + 1);
    for (int from = 1; from <= n; ++from) {
        for (int to = 1; to <= n; ++to) {
            adj[from].push_back(to);
        }
    }

    vector<int> stack = {1};
    vector<int> circuit;
    circuit.reserve(n * n + 1);

    while (!stack.empty()) {
        int cur = stack.back();
        if (!adj[cur].empty()) {
            int next = adj[cur].back();
            adj[cur].pop_back();
            stack.push_back(next);
        } else {
            circuit.push_back(cur);
            stack.pop_back();
        }
    }

    reverse(circuit.begin(), circuit.end());

    cout << circuit.size() << '\n';
    for (int vertex : circuit) {
        cout << vertex << ' ';
    }
    cout << '\n';
    return 0;
}
```
