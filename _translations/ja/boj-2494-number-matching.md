---
title: BOJ 2494 - 数字合わせ
author: MINJUN PARK
date: 2022-03-11 05:00:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, DP, 数字合わせ]
pin: false
lang: ja
translation_key: boj-2494-number-matching
permalink: /ja/posts/boj-2494-number-matching/
---

[問題](https://www.acmicpc.net/problem/2494)

## ダイヤルの操作規則と動的計画法

ダイヤルには左から順に番号を付けます。ダイヤル`i`を正の回数だけ回すと、`i`番目とその下にあるすべてのダイヤルが左に回転します。負の回数だけ回すと、`i`番目のダイヤルだけが右に回転します。出力する回転数は符号付き整数で、正数は左回転、負数は右回転を表します。

左から順に処理します。`dp[i][carry]`を、`i`番目まで処理したときの最小回転数とします。`carry`はそれ以前のダイヤルで行った左回転数の合計を10で割った余りです。つまり、以前の左回転によって現在のダイヤルにすでに適用されている回転量です。現在の数字は`(source[i] + carry) mod 10`です。これを目標の数字に合わせるために必要な、最小の非負左回転数を`left`とします。

選択肢は2つです。`left`だけ左に回すと、コストは`left`で、次のダイヤルに渡す回転量は`(carry + left) mod 10`になります。`left`が0でなければ、`left - 10`だけ右に回すこともできます。このコストは`10 - left`で、carryは変わりません。`left`が0の場合は回さない選択だけを使います。10回転しても解は改善しません。各状態について、選択した直前のcarryと符号付き回転数を保存します。最後のダイヤルで最小コストの状態を選び、predecessorを逆にたどって各ダイヤルの回転数を出力します。

各ダイヤルのcarry状態は10個で、各状態からの遷移は最大2つです。したがって時間計算量とメモリ計算量はいずれも`O(N · 10)`です。

## C++17

```cpp
#include <array>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    string source, target;
    cin >> n >> source >> target;

    constexpr int INF = 1'000'000'000;
    vector<array<int, 10>> dp(n + 1);
    vector<array<int, 10>> previousCarry(n + 1);
    vector<array<int, 10>> chosenTurn(n + 1);
    for (auto& row : dp) row.fill(INF);
    dp[0][0] = 0;

    for (int i = 0; i < n; ++i) {
        for (int carry = 0; carry < 10; ++carry) {
            if (dp[i][carry] == INF) continue;

            const int current = (source[i] - '0' + carry) % 10;
            const int left = (target[i] - '0' - current + 10) % 10;

            const int nextCarry = (carry + left) % 10;
            const int leftCost = dp[i][carry] + left;
            if (leftCost < dp[i + 1][nextCarry]) {
                dp[i + 1][nextCarry] = leftCost;
                previousCarry[i + 1][nextCarry] = carry;
                chosenTurn[i + 1][nextCarry] = left;
            }

            if (left != 0) {
                const int rightCost = dp[i][carry] + 10 - left;
                if (rightCost < dp[i + 1][carry]) {
                    dp[i + 1][carry] = rightCost;
                    previousCarry[i + 1][carry] = carry;
                    chosenTurn[i + 1][carry] = left - 10;
                }
            }
        }
    }

    int carry = 0;
    for (int state = 1; state < 10; ++state) {
        if (dp[n][state] < dp[n][carry]) carry = state;
    }
    const int bestCost = dp[n][carry];

    vector<int> turns(n);
    for (int i = n; i > 0; --i) {
        turns[i - 1] = chosenTurn[i][carry];
        carry = previousCarry[i][carry];
    }

    cout << bestCost << '\n';
    for (int i = 0; i < n; ++i) {
        cout << i + 1 << ' ' << turns[i] << '\n';
    }
    return 0;
}
```
