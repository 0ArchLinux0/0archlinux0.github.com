---
title: BOJ 11437 - 最近共通祖先
author: MINJUN PARK
date: 2022-02-28 20:29:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 木, 最近共通祖先, LCA]
pin: false
lang: ja
translation_key: boj-11437-lowest-common-ancestor
permalink: /ja/posts/boj-11437-lowest-common-ancestor/
source_permalink: /posts/BOJ-11437/
---

[問題: BOJ 11437 — 最近共通祖先](https://www.acmicpc.net/problem/11437) · [English](/posts/BOJ-11437/) · [한국어](/ko/posts/boj-11437-lowest-common-ancestor/)

木の根を頂点 `1` とします。再帰ではなく幅優先探索を使い、各頂点の深さと直上の親を記録します。根の親は `0` とし、この番兵頂点の祖先もすべて `0` です。入力は木なので、根以外の各頂点は親からちょうど一度だけ訪問されます。再帰 DFS の代わりにキューを使うため、頂点 50,000 個が一直線につながった木でも呼び出しスタックがあふれません。

`ancestor[v][j]` を、頂点 `v` から上へ `2^j` 本の辺をたどった頂点と定義します。親を記録する探索でレベル 0 を埋め、その後 `ancestor[v][j] = ancestor[ancestor[v][j - 1]][j - 1]` という漸化式で高いレベルを計算します。番兵のおかげで、根に対しても同じ漸化式をそのまま使えます。

クエリでは、深い方の頂点を深さの差だけ先に上げ、両方の高さをそろえます。この時点で頂点が一致すれば、その頂点が LCA です。一致しない場合は、大きなジャンプレベルから順に調べ、そのレベルでの祖先が異なるとき両方の頂点を同時に上げます。これにより、2 頂点は最小共通祖先のすぐ下に残るため、両者の直上の親が答えになります。

前処理の時間計算量とメモリ計算量はそれぞれ `O(N log N)`、クエリ 1 件の時間計算量は `O(log N)` です。

## C++17

```cpp
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

    vector<vector<int>> graph(n + 1);
    for (int i = 0; i < n - 1; ++i) {
        int a, b;
        cin >> a >> b;
        graph[a].push_back(b);
        graph[b].push_back(a);
    }

    constexpr int LOG = 17;  // 2^16 >= 50,000
    vector<int> depth(n + 1, -1);
    vector<vector<int>> ancestor(LOG, vector<int>(n + 1, 0));

    queue<int> pending;
    depth[1] = 0;
    pending.push(1);

    while (!pending.empty()) {
        int node = pending.front();
        pending.pop();

        for (int next : graph[node]) {
            if (next == ancestor[0][node]) {
                continue;
            }
            ancestor[0][next] = node;
            depth[next] = depth[node] + 1;
            pending.push(next);
        }
    }

    for (int level = 1; level < LOG; ++level) {
        for (int node = 1; node <= n; ++node) {
            ancestor[level][node] =
                ancestor[level - 1][ancestor[level - 1][node]];
        }
    }

    auto lca = [&](int a, int b) {
        if (depth[a] < depth[b]) {
            swap(a, b);
        }

        int difference = depth[a] - depth[b];
        for (int level = 0; level < LOG; ++level) {
            if (difference & (1 << level)) {
                a = ancestor[level][a];
            }
        }

        if (a == b) {
            return a;
        }

        for (int level = LOG - 1; level >= 0; --level) {
            if (ancestor[level][a] != ancestor[level][b]) {
                a = ancestor[level][a];
                b = ancestor[level][b];
            }
        }
        return ancestor[0][a];
    };

    int queries;
    cin >> queries;
    while (queries--) {
        int a, b;
        cin >> a >> b;
        cout << lca(a, b) << '\n';
    }
}
```
