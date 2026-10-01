---
title: BOJ 3653 - 映画コレクション
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [アルゴリズム, データ構造, Fenwick Tree, BOJ 3653, 映画コレクション]
lang: ja
translation_key: boj-3653-movie-collection
permalink: /ja/posts/boj-3653-movie-collection/
pin: false
---

[BOJ 3653: 映画コレクション](https://www.acmicpc.net/problem/3653)

## 映画番号ではなく位置を管理する

映画をリクエストするたびにDVDの位置が変わるため、映画番号だけを添字にしても、その映画より上に何枚あるかを直接表せない。そこで、各位置が使用中かどうかを管理する。DVDがある位置を `1`、空いている位置を `0` とし、Fenwick treeでその区間和を求める。

1ケースの映画数を `N`、リクエスト数を `M` とする。先頭へ移動するための位置 `1` から `M` は空けておき、最初は映画 `i` を位置 `M + i` に置く。初期状態では、スタックの上から下へ位置 `M + 1` から `M + N` を使う。`position[i]` に映画 `i` の現在位置を保存し、使用中の各位置に対応するFenwick treeの値を `1` にする。

位置番号が小さいほどスタックの上にある。映画 `x` が位置 `p` にあるとき、その上にあるDVDの枚数は、`p` より小さい位置を占めるDVDの数である。つまり、Fenwick treeで `p - 1` までの累積和を求めればよい。

映画 `x` がリクエストされたら、まずその枚数を計算して出力する。次に、現在位置を木から取り除き、映画を `next_top` に移して `position[x]` を更新する。`next_top` は `M` から始め、リクエストごとに1ずつ減らす。これにより、新しい位置はそれまでに使ったどの位置よりも上になる。同じ映画が再度リクエストされても、保存した現在位置はすでに更新済みなので正しく処理できる。映画がまだ一番上にあれば、その上のDVDは0枚である。

## 計算量

各リクエストでは累積和クエリを1回、点更新を2回行い、それぞれ $O(\log(N+M))$ 時間かかる。以下の実装では初期位置も1つずつ更新するため、初期化には $O(N\log(N+M))$ 時間かかる。1ケースあたりの合計時間計算量は $O((N+M)\log(N+M))$、空間計算量は $O(N+M)$ である。

## C++17 の実装

```cpp
#include <iostream>
#include <vector>
using namespace std;

class FenwickTree {
    vector<int> tree;

public:
    explicit FenwickTree(int size) : tree(size + 1, 0) {}

    void add(int index, int delta) {
        for (int i = index; i < static_cast<int>(tree.size()); i += i & -i) {
            tree[i] += delta;
        }
    }

    int prefixSum(int index) const {
        int sum = 0;
        for (int i = index; i > 0; i -= i & -i) {
            sum += tree[i];
        }
        return sum;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int testCases;
    cin >> testCases;

    while (testCases--) {
        int n, m;
        cin >> n >> m;

        FenwickTree occupied(n + m);
        vector<int> position(n + 1);

        for (int movie = 1; movie <= n; ++movie) {
            position[movie] = m + movie;
            occupied.add(position[movie], 1);
        }

        int nextTop = m;
        for (int request = 0; request < m; ++request) {
            int movie;
            cin >> movie;

            int current = position[movie];
            cout << occupied.prefixSum(current - 1) << (request + 1 == m ? '\n' : ' ');

            occupied.add(current, -1);
            position[movie] = nextTop;
            occupied.add(nextTop, 1);
            --nextTop;
        }
    }

    return 0;
}
```
