---
title: BOJ 7469 - K番目の数
author: MINJUN PARK
date: 2022-03-11 22:44:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, セグメント木, 永続セグメント木, K番目の数]
pin: false
lang: ja
translation_key: boj-7469-kth-number
permalink: /ja/posts/boj-7469-kth-number/
---

[問題リンク](https://www.acmicpc.net/problem/7469)

## 元の方法が遅い理由

`(値, インデックス)` の組を一度ソートする処理は `O(N log N)` ですが、その後、各区間クエリで全 `N` 要素を走査するため、クエリ処理には最悪 `O(NM)` 時間がかかります。変数をローカルではなくグローバルに宣言しても保存期間が変わるだけで、必要な処理量は変わらず、漸近計算量にも影響しません。

## 永続セグメント木

配列の値を、ソート済みの異なる値 `0 ... U-1` の順位に座標圧縮します。配列の各 prefix に対して永続セグメント木の root を作ります。`root[i]` は先頭から `i` 個の要素を表し、各ノードは担当する値区間に含まれる要素数を保持します。次の配列値を追加するときは、root から該当する葉までのノードだけをコピーして個数を増やし、それ以外のノードは前のバージョンと共有します。

クエリ `[L, R]` である値区間に属する要素数は、`root[R]` の個数から `root[L-1]` の個数を引いて求めます。全順位区間から始め、2つの root の左の子の個数の差を `k` と比較します。k 番目の値が左側にあるなら左へ進み、そうでなければその個数だけ `k` を減らして右へ進みます。葉に到達したときの順位が k 番目に小さい値です。重複値も出現回数ごとにカウントされます。

座標圧縮と永続 root の構築には `O(N log U)`、各クエリには `O(log U)` 時間がかかる。ノード、入力配列、圧縮座標、prefix root が `O(N log U)` の空間を使い、クエリは保存せず1件ずつ処理する。

## C++17 実装

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Node {
    int left = 0;
    int right = 0;
    int count = 0;
};


vector<Node> tree(1);

int update(int previous, int low, int high, int position) {
    const int current = static_cast<int>(tree.size());
    tree.push_back(tree[previous]);
    ++tree[current].count;

    if (low != high) {
        const int middle = low + (high - low) / 2;
        if (position <= middle) {
            tree[current].left = update(tree[previous].left, low, middle, position);
        } else {
            tree[current].right = update(tree[previous].right, middle + 1, high, position);
        }
    }
    return current;
}

int kth(int leftRoot, int rightRoot, int low, int high, int k) {
    if (low == high) return low;

    const int leftCount =
        tree[tree[rightRoot].left].count - tree[tree[leftRoot].left].count;
    const int middle = low + (high - low) / 2;

    if (k <= leftCount) {
        return kth(tree[leftRoot].left, tree[rightRoot].left, low, middle, k);
    }
    return kth(tree[leftRoot].right, tree[rightRoot].right, middle + 1, high,
               k - leftCount);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<int> values(n);
    for (int& value : values) cin >> value;

    vector<int> coordinates = values;
    sort(coordinates.begin(), coordinates.end());
    coordinates.erase(unique(coordinates.begin(), coordinates.end()), coordinates.end());
    const int distinctCount = static_cast<int>(coordinates.size());

    vector<int> roots(n + 1, 0);
    for (int i = 0; i < n; ++i) {
        const int rank = static_cast<int>(
            lower_bound(coordinates.begin(), coordinates.end(), values[i]) - coordinates.begin());
        roots[i + 1] = update(roots[i], 0, distinctCount - 1, rank);
    }

    int left, right, k;
    while (m--) {
        cin >> left >> right >> k;
        const int rank = kth(roots[left - 1], roots[right],
                             0, distinctCount - 1, k);
        cout << coordinates[rank] << '\n';
    }
    return 0;
}
```
