---
title: BOJ 11376 - 熱血江湖 2
author: MINJUN PARK
date: 2022-03-09 02:09:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, ネットワークフロー, 二部マッチング, 熱血江湖2]
pin: false
lang: ja
translation_key: boj-11376-job-assignment
permalink: /ja/posts/boj-11376-job-assignment/
---

[問題ページ](https://www.acmicpc.net/problem/11376)

## 問題のモデル

`N`人の社員と`M`件の仕事があり、各社員が担当できる仕事の一覧が与えられる。各仕事は高々1人の社員に割り当て、各社員には高々2件の仕事を割り当てる。この条件で、割り当てる仕事数を最大化する。

二部グラフで社員ごとにマッチング用のスロットを2つ作り、両方のスロットをその社員が担当できるすべての仕事につなぐ。マッチングでは各スロットと各仕事を高々1回しか使わないため、社員が担当する仕事は最大2件で、同じ仕事が重複して割り当てられることもない。逆に、条件を満たす割り当てがあれば、各社員に割り当てられた2件以下の仕事をその社員の2つのスロットに入れられる。したがって、最大マッチングのサイズが求める最大値と一致する。

## 増加路

すべてのスロットを順に処理し、標準的な増加路探索を行う。1回の探索では仕事を訪問済みにする。候補の仕事がすでにマッチされている場合、その仕事を担当しているスロットを別の担当可能な仕事へ移せるか再帰的に試す。再割り当てに成功すれば、候補の仕事を現在のスロットに割り当てる。1回の探索で各仕事を高々1回しか訪問しないため循環を避けられ、成功した再割り当て後もマッチングの条件は保たれる。増加路定理より、左側の全スロットを処理すると最大マッチングが得られる。

スロットは`2N`個、社員と仕事の適格性を表す辺は`E`本（社員ごとの辺数の合計）である。DFS 1回の時間計算量は`O(E)`なので、全体では`O(N E)`となる。グラフとマッチング配列の空間計算量は`O(N + M + E)`である。

## C++17実装

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<int>> jobs(n);
    for (int worker = 0; worker < n; ++worker) {
        int count;
        cin >> count;
        jobs[worker].resize(count);
        for (int& job : jobs[worker]) {
            cin >> job;
            --job;
        }
    }

    // matchedSlot[job] は現在この仕事を担当するスロット。
    vector<int> matchedSlot(m, -1);
    vector<char> visitedJob(m);

    auto augment = [&](auto&& self, int slot) -> bool {
        for (int job : jobs[slot / 2]) {
            if (visitedJob[job]) continue;
            visitedJob[job] = true;

            if (matchedSlot[job] == -1 ||
                self(self, matchedSlot[job])) {
                matchedSlot[job] = slot;
                return true;
            }
        }
        return false;
    };

    int assigned = 0;
    for (int slot = 0; slot < 2 * n; ++slot) {
        fill(visitedJob.begin(), visitedJob.end(), false);
        if (augment(augment, slot)) ++assigned;
    }

    cout << assigned << '\n';
    return 0;
}
```

再帰呼び出しは増加路に沿って、マッチ済みの仕事の担当スロットだけを変更する。そのため探索が成功するたびにマッチングサイズはちょうど1増え、仕事とスロットの一意性も維持される。
