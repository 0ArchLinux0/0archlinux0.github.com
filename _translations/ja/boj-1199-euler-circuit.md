---
title: BOJ 1199 — オイラー回路
author: MINJUN PARK
date: 2022-04-26 17:26:02 +0900
categories: [PS, baekjoon]
tags: [アルゴリズム, グラフ理論, オイラー回路, Hierholzer のアルゴリズム, BOJ 1199]
lang: ja
translation_key: boj-1199-euler-circuit
permalink: /ja/posts/boj-1199-euler-circuit/
pin: false
---

[BOJ 1199 — オイラー回路](https://www.acmicpc.net/problem/1199)

## 入力を多重グラフとして扱う

隣接行列の各要素は、無向グラフの頂点間にある辺の本数を表す。平行辺は別々の辺なので、回路ではそれぞれ一度ずつ使う。対角要素はループの本数であり、ループは次数に2を加える一方、探索では一度通る。

無向グラフにオイラー回路が存在するには、すべての頂点の次数が偶数であり、辺に接続するすべての頂点が一つの連結成分に含まれる必要がある。孤立頂点は条件に影響しない。元の記事のコードは次数の偶奇だけを確認していたため、互いに分離した偶数次数の成分がある場合に、一部の辺だけを含む回路を出力してしまう。

## 反復版 Hierholzer アルゴリズム

辺を持つ頂点から始め、未使用の辺を一つ消費するたびに到達先の頂点をスタックへ積む。スタック最上部の頂点に未使用の辺がなければ、その頂点を回路へ追加して戻る。最後に頂点列を逆順にすると、すべての辺を通るオイラー回路になる。

辺の本数を一次元化した隣接行列に格納する。再帰は使わず、得られた回路の頂点数が $E+1$ かどうかで連結性を確認する。この検査は孤立頂点を無視しながら、辺を持つ分離成分がある場合は拒否する。

辺が一本もない場合は、長さ0の回路として頂点1を出力する。

## C++17

```cpp
#include <algorithm>
#include <cstddef>
#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> remaining(static_cast<size_t>(n) * n, 0);
    vector<long long> degree(n, 0);
    long long edge_count = 0;

    for (int u = 0; u < n; ++u) {
        for (int v = 0; v < n; ++v) {
            int count;
            cin >> count;
            if (u > v || count == 0) continue;

            remaining[static_cast<size_t>(u) * n + v] = count;
            edge_count += count;
            if (u == v) {
                degree[u] += 2LL * count;
            } else {
                remaining[static_cast<size_t>(v) * n + u] = count;
                degree[u] += count;
                degree[v] += count;
            }
        }
    }

    for (long long value : degree) {
        if (value % 2 != 0) {
            cout << -1 << '\n';
            return 0;
        }
    }

    int start = -1;
    for (int u = 0; u < n; ++u) {
        if (degree[u] > 0) {
            start = u;
            break;
        }
    }
    if (start == -1) {
        cout << 1 << '\n';
        return 0;
    }

    vector<int> next_neighbor(n, 0);
    vector<int> stack;
    vector<int> circuit;
    stack.reserve(static_cast<size_t>(edge_count) + 1);
    circuit.reserve(static_cast<size_t>(edge_count) + 1);
    stack.push_back(start);

    while (!stack.empty()) {
        int u = stack.back();
        int& v = next_neighbor[u];
        while (v < n && remaining[static_cast<size_t>(u) * n + v] == 0) {
            ++v;
        }

        if (v == n) {
            circuit.push_back(u);
            stack.pop_back();
        } else {
            int w = v;
            --remaining[static_cast<size_t>(u) * n + w];
            if (u != w) {
                --remaining[static_cast<size_t>(w) * n + u];
            }
            stack.push_back(w);
        }
    }

    if (static_cast<long long>(circuit.size()) != edge_count + 1) {
        cout << -1 << '\n';
        return 0;
    }

    reverse(circuit.begin(), circuit.end());
    for (size_t i = 0; i < circuit.size(); ++i) {
        if (i > 0) cout << ' ';
        cout << circuit[i] + 1;
    }
    cout << '\n';
}
```

時間計算量は $O(N^2+E)$ である。隣接行列を一度読み込み、各辺を一度消費し、各行の次の隣接頂点ポインタは最大 $N$ 回進む。行列に $O(N^2)$、スタックと回路に $O(E)$ の空間を使う。

## 出典と改訂履歴

MINJUN PARKが2022-04-26に公開し、CC BY 4.0を表示した[「백준 1199번 - 오일러 회로」](https://ilikechicken.tistory.com/48)を基にした。多重グラフとHierholzerの考え方は維持し、不足していた連結条件を加え、再帰を反復実装に置き換えた。

## 参考資料

- [BOJ 1199 — Euler Circuit](https://www.acmicpc.net/problem/1199)
- [Eulerian path and circuit](https://cp-algorithms.com/graph/euler_path.html)