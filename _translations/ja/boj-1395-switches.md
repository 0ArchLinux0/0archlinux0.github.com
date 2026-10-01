---
title: BOJ 1395 - スイッチ
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [アルゴリズム, データ構造, セグメント木, 遅延伝播, BOJ 1395, スイッチ]
lang: ja
translation_key: boj-1395-switches
permalink: /ja/posts/boj-1395-switches/
pin: false
---

[BOJ 1395 - スイッチ](https://www.acmicpc.net/problem/1395)

## 問題とセグメント木

スイッチが $N$ 個あり、最初はすべてオフである。各命令では $1\le S\le T\le N$ を満たす区間を指定する。`0 S T` は両端を含む区間 $[S,T]$ のすべてのスイッチを反転し、`1 S T` はその区間でオンになっているスイッチの個数を出力する。

区間内のスイッチを一つずつ変更すると、命令一回に線形時間がかかる場合がある。そこで再帰的なセグメント木を使い、各ノードに対応する区間のオンの個数と、遅延反転ビットを保持する。区間全体を反転するとき、個々のスイッチを調べる必要はない。

## 反転と遅延値

長さ $L$ の区間にオンのスイッチが `on` 個あるとき、区間を反転した後の個数は $L-\text{on}$ である。したがって、ノードに反転を適用すると `on = L - on` とし、lazy ビットを XOR 1 する。ビットが 1 の場合、そのノードの区間には反転が適用済みだが、子ノードにはまだ伝えていないことを表す。反転を二回行えば元の状態に戻るため、遅延ビットは XOR で合成でき、二回の反転は相殺される。

子に降りて部分区間を処理する前に、`push` で保留中の反転を左右の子へ適用する。子の区間長に合わせてそれぞれのオンの個数を更新し、子の lazy ビットを反転させたあと、親のビットを 0 に戻す。葉には子がないため、反転を伝播する必要はない。

## 更新とクエリ

再帰関数は、現在の区間が要求区間と交差しなければすぐに戻る。現在の区間全体が要求区間に含まれるなら、そのノードを反転して終了する。それ以外の場合は、まず遅延値を子へ伝え、左右の子を再帰的に更新し、最後に子のオンの個数の和で親を更新する。

クエリも同じ交差判定を使う。交差しないノードは 0 を返し、要求区間に完全に含まれるノードは保存しているオンの個数を返す。部分的に交差する場合は遅延値を伝播して、左右の結果を足す。区間は両端を含むため、再帰での交差判定には `queryLeft <= nodeRight` および `nodeLeft <= queryRight` を用いる。

セグメント木の高さに比例して各命令を処理できるので、更新とクエリはいずれも $O(\log N)$ 時間である。サイズ $4N$ の配列を使うため、必要な空間は $O(N)$ である。

## C++17 の実装

```cpp
#include <iostream>
#include <vector>
using namespace std;

class SegmentTree {
    int n;
    vector<int> on;
    vector<bool> lazy;

    void apply(int node, int left, int right) {
        on[node] = (right - left + 1) - on[node];
        lazy[node] = !lazy[node];
    }

    void push(int node, int left, int right) {
        if (!lazy[node] || left == right) return;
        int mid = left + (right - left) / 2;
        apply(node * 2, left, mid);
        apply(node * 2 + 1, mid + 1, right);
        lazy[node] = false;
    }

    void toggle(int node, int left, int right, int ql, int qr) {
        if (qr < left || right < ql) return;
        if (ql <= left && right <= qr) {
            apply(node, left, right);
            return;
        }
        push(node, left, right);
        int mid = left + (right - left) / 2;
        toggle(node * 2, left, mid, ql, qr);
        toggle(node * 2 + 1, mid + 1, right, ql, qr);
        on[node] = on[node * 2] + on[node * 2 + 1];
    }

    int countOn(int node, int left, int right, int ql, int qr) {
        if (qr < left || right < ql) return 0;
        if (ql <= left && right <= qr) return on[node];
        push(node, left, right);
        int mid = left + (right - left) / 2;
        return countOn(node * 2, left, mid, ql, qr)
             + countOn(node * 2 + 1, mid + 1, right, ql, qr);
    }

public:
    explicit SegmentTree(int size) : n(size), on(4 * size, 0), lazy(4 * size, false) {}

    void toggle(int left, int right) {
        toggle(1, 1, n, left, right);
    }

    int countOn(int left, int right) {
        return countOn(1, 1, n, left, right);
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    SegmentTree tree(n);

    while (m--) {
        int operation, left, right;
        cin >> operation >> left >> right;
        if (operation == 0) {
            tree.toggle(left, right);
        } else {
            cout << tree.countOn(left, right) << '\n';
        }
    }
    return 0;
}
```
