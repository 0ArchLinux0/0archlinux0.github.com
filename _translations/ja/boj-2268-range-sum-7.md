---
title: BOJ 2268 - 数の合計 7
author: MINJUN PARK
date: 2022-02-18 09:28:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, セグメント木, 区間和]
pin: false
lang: ja
translation_key: boj-2268-range-sum-7
permalink: /ja/posts/boj-2268-range-sum-7/
source_permalink: /posts/BOJ-2268/
---

[問題: BOJ 2268 — 数の合計 7](https://www.acmicpc.net/problem/2268) · [English](/posts/BOJ-2268/) · [한국어](/ko/posts/boj-2268-range-sum-7/)

配列の要素数は `N` で、初期値はすべて 0 です。`0 a b` は、1 始まりで両端を含む区間 `[min(a, b), max(a, b)]` の合計を出力します。`1 a b` は `a` 番目の要素に `b` を代入し、以前の値を置き換えます。問題の代入値は 0 以上で、0 の場合もあります。区間和は 32 ビット整数の範囲を超えることがあるため、木には `long long` を使います。

反復型セグメント木では、0 始まりの添字 `i` の要素を `tree[N + i]` に格納し、内部ノードには 2 つの子の合計を格納します。木を 0 で初期化すれば、配列の初期値がすべて 0 という条件をそのまま表せます。代入では対応する葉を新しい値に置き換え、根まで祖先ノードの合計を再計算します。この処理は `O(log N)` です。

合計クエリでは、まず入力された両端を小さい順に並べます。1 始まりで両端を含む区間 `[a, b]` を、0 始まりの半開区間 `[a - 1, b)` に変換し、両端に `N` を加えて葉の添字にします。`left < right` の間、奇数の左端は区間右端に含まれる完全なノードを示すため、`tree[left]` を加えて左端を進めます。右端が奇数なら、その直前のノードが区間に含まれるため、右端を戻してから `tree[right]` を加えます。両端を親に移し、同じ処理を繰り返します。半開区間にすることで要素 1 つだけの区間も扱え、入力の順序が逆の場合も両端を並べ替えるだけで処理できます。代入と区間和はそれぞれ `O(log N)` 時間で、`2N` 個の要素を持つ木の使用メモリ量は `O(N)` です。

## C++

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    vector<long long> tree(2 * n, 0);

    while (m--) {
        int type, a;
        long long b;
        cin >> type >> a >> b;

        if (type == 1) {
            int position = n + a - 1;
            tree[position] = b;
            for (position >>= 1; position > 0; position >>= 1) {
                tree[position] = tree[position << 1] + tree[position << 1 | 1];
            }
        } else {
            int left = min(a, static_cast<int>(b)) - 1 + n;
            int right = max(a, static_cast<int>(b)) + n;
            long long sum = 0;

            while (left < right) {
                if (left & 1) {
                    sum += tree[left++];
                }
                if (right & 1) {
                    sum += tree[--right];
                }
                left >>= 1;
                right >>= 1;
            }
            cout << sum << '\n';
        }
    }
    return 0;
}
```
