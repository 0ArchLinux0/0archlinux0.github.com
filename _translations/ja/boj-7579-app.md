---
title: BOJ 7579 — アプリ
author: MINJUN PARK
date: 2022-04-18 20:05:45 +0900
categories: [PS, baekjoon]
tags: [アルゴリズム, 動的計画法, ナップサック問題, BOJ 7579, アプリ]
lang: ja
translation_key: boj-7579-app
permalink: /ja/posts/boj-7579-app/
pin: false
---

[BOJ 7579 — App](https://www.acmicpc.net/problem/7579)

## 停止コストを最小化する

アプリ $i$ を停止するとメモリ $m_i$ を解放でき、コスト $c_i$ がかかる。解放したメモリの合計を $M$ 以上にしながら、コストの合計を最小化する。

必要メモリを状態にすると配列が大きくなりすぎる。一方、アプリ数は最大100、各停止コストは最大100なので、コストの合計は最大10,000である。そこでコストをDPの状態にする。

## DPの不変条件

`best[b]` を、コストを**最大** $b$ まで使って解放できる最大メモリ量と定義する。コストがちょうど $b$ の状態ではなく、$b$ 以下の予算を表す。そのため、すべての要素を0で初期化できる。

各アプリについて、停止しない場合と停止する場合を比較する。

$$
\text{best}[b] = \max(\text{best}[b],\ \text{best}[b-c_i] + m_i).
$$

同じアプリを二度選ばないよう、予算を大きい方から更新する。コストが0のアプリも、そのアプリについて各予算を一度だけ更新するため正しく扱える。`best[b] >= M` を満たす最小の $b$ が答えとなる。

### 正しさ

最初の $i$ 個のアプリだけを考えたとき、`best[b]` はコストを最大 $b$ まで使って得られる最大メモリ量だと仮定する。次のアプリを停止しない場合は従来の状態を保ち、停止する場合はコスト $c_{i+1}$ 分を引いた予算の状態にメモリ $m_{i+1}$ を加える。漸化式はこの二つのケースの大きい方を選ぶため、不変条件が保たれる。予算を降順に更新することで、現在のアプリを反映する前の状態だけを参照し、同じアプリの再利用を防ぐ。よってすべてのアプリを処理した後、初めて $M$ 以上を得る予算が最小コストである。

時間計算量は $O(NC)$、空間計算量は $O(C)$ である。ここで $C=\sum_i c_i$ とする。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <numeric>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, required_memory;
    cin >> n >> required_memory;

    vector<int> memory(n), cost(n);
    for (int& value : memory) cin >> value;
    for (int& value : cost) cin >> value;

    const int total_cost = accumulate(cost.begin(), cost.end(), 0);
    vector<int> best(total_cost + 1, 0);

    for (int i = 0; i < n; ++i) {
        for (int budget = total_cost; budget >= cost[i]; --budget) {
            best[budget] = max(best[budget], best[budget - cost[i]] + memory[i]);
        }
    }

    for (int budget = 0; budget <= total_cost; ++budget) {
        if (best[budget] >= required_memory) {
            cout << budget << '\n';
            return 0;
        }
    }

    cout << -1 << '\n';
}
```

## 出典と改訂履歴

MINJUN PARKが2022-04-18に公開し、CC BY 4.0を表示した[「백준 7579번 - 앱」](https://ilikechicken.tistory.com/39)を基にした。コストを状態にする0/1ナップサックの方針は維持し、DP状態が「費用ちょうど」ではなく「予算以下」であることを明確にした。GNUの可変長配列は標準C++17のコードに置き換えた。