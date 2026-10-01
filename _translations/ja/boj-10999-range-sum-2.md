---
title: BOJ 10999 - 区間和を求める 2
author: MINJUN PARK
date: 2022-02-21 05:12:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, セグメント木, 区間和, 遅延伝播]
pin: false
lang: ja
translation_key: boj-10999-range-sum-2
permalink: /ja/posts/boj-10999-range-sum-2/
source_permalink: /posts/BOJ-10999/
---

[問題: BOJ 10999 — 区間和を求める 2](https://www.acmicpc.net/problem/10999) · [English](/posts/BOJ-10999/) · [한국어](/ko/posts/boj-10999-range-sum-2/)

配列には `N` 個の値があります。タイプ1の操作では、1-indexed で両端を含む区間 `[B, C]` のすべての要素に `D` を加算します。タイプ2では同じ形式の区間の合計を出力します。配列サイズは最大100万で、区間和は32ビット整数の範囲を超えるため、セグメント木とすべての計算に `long long` を使います。

再帰セグメント木では、`tree[node]` に担当区間の合計を格納し、子の区間にはまだ反映していない要素ごとの加算値を `lazy[node]` に保持します。`apply(node, start, end, value)` は区間内の全要素が変化するため、合計に `value * (end - start + 1)` を加え、遅延タグにも `value` を加算します。一部だけが対象となるノードから子へ進む前に、`push` は親のタグをそれぞれの子の区間長に応じて適用し、親のタグをクリアします。子の区間を更新した後は、`pull` に相当する処理で2つの子の合計から親の合計を再計算します。完全に含まれる区間は子へ進まず、直接 `apply` します。

区間和クエリでは、クエリ区間に完全に含まれるノードの保存済み合計を返します。それ以外の場合は子へ進む前に保留中の加算を伝播し、クエリ区間と重なる子の結果を合計します。各操作が訪れるのはセグメント木の境界経路とその周辺のノードだけなので、更新とクエリはいずれも `O(log N)` 時間です。木と遅延タグの配列は `O(N)` メモリを使用し、再帰の深さは `O(log N)` なので、`N <= 1,000,000` でも安全です。

入力区間は1-indexedで、両端を含みます。実装では両端を0-indexedに変換します。クエリ区間を半開区間に変換する必要はありません。

## C++

```cpp
#include <iostream>
#include <vector>
using namespace std;

using int64 = long long;

int n;
vector<int64> tree;
vector<int64> lazy;

void build(int node, int start, int end, const vector<int64>& values) {
    if (start == end) {
        tree[node] = values[start];
        return;
    }
    int mid = start + (end - start) / 2;
    build(node * 2, start, mid, values);
    build(node * 2 + 1, mid + 1, end, values);
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

void apply(int node, int start, int end, int64 value) {
    tree[node] += value * (end - start + 1);
    lazy[node] += value;
}

void push(int node, int start, int end) {
    if (lazy[node] == 0 || start == end) return;

    int mid = start + (end - start) / 2;
    apply(node * 2, start, mid, lazy[node]);
    apply(node * 2 + 1, mid + 1, end, lazy[node]);
    lazy[node] = 0;
}

void update(int node, int start, int end, int left, int right, int64 value) {
    if (right < start || end < left) return;
    if (left <= start && end <= right) {
        apply(node, start, end, value);
        return;
    }

    push(node, start, end);
    int mid = start + (end - start) / 2;
    update(node * 2, start, mid, left, right, value);
    update(node * 2 + 1, mid + 1, end, left, right, value);
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

int64 query(int node, int start, int end, int left, int right) {
    if (right < start || end < left) return 0;
    if (left <= start && end <= right) return tree[node];

    push(node, start, end);
    int mid = start + (end - start) / 2;
    return query(node * 2, start, mid, left, right)
         + query(node * 2 + 1, mid + 1, end, left, right);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int m, k;
    cin >> n >> m >> k;

    vector<int64> values(n);
    for (int i = 0; i < n; ++i) cin >> values[i];

    tree.assign(4 * n, 0);
    lazy.assign(4 * n, 0);
    build(1, 0, n - 1, values);

    for (int i = 0; i < m + k; ++i) {
        int type, b, c;
        cin >> type >> b >> c;
        --b;
        --c;

        if (type == 1) {
            int64 d;
            cin >> d;
            update(1, 0, n - 1, b, c, d);
        } else {
            cout << query(1, 0, n - 1, b, c) << '\n';
        }
    }
    return 0;
}
```
