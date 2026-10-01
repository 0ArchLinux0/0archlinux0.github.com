---
title: BOJ 1339 - 単語の数学
author: MINJUN PARK
date: 2022-02-26 03:16:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 貪欲法, 単語の数学]
pin: false
lang: ja
translation_key: boj-1339-word-math
permalink: /ja/posts/boj-1339-word-math/
source_permalink: /posts/BOJ-1339/
---

[問題: BOJ 1339 — 単語の数学](https://www.acmicpc.net/problem/1339) · [English](/posts/BOJ-1339/) · [한국어](/ko/posts/boj-1339-word-math/)

各文字には、その文字が現れるすべての位置で同じ数字を割り当てます。単語の一の位にある文字はその数字を1回分だけ加算し、十の位なら数字の10倍を加算します。さらに左の位も同様です。たとえば `ABC` の値は `100 * value[A] + 10 * value[B] + value[C]` です。すべての単語について各桁の寄与を合計すると、全体の合計は `sum(weight[letter] * digit[letter])` と表せます。

最大の重みには最大の数字を割り当てます。交換論法で理由を確認できます。重み `w1 > w2` に対し、数字 `d1 < d2` が割り当てられていたとします。数字を入れ替えると、合計は `(w1 * d2 + w2 * d1) - (w1 * d1 + w2 * d2) = (w1 - w2) * (d2 - d1) > 0` だけ増加します。したがって、このような逆順の割り当てが最適になることはありません。重みを降順に並べて `9, 8, ...` を順に割り当てれば、合計を最大化できます。重みが等しい文字は文字順に並べると結果が決定的になります。同じ重み同士で数字を入れ替えても合計は変わりません。

総文字数を `T`、異なる文字数を `A` とすると、重みの計算に `O(T)`、ソートに `O(A log A)` の時間がかかります。したがって全体の時間計算量は `O(T + A log A)` です。重みと答えには `long long` を使います。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    long long weight[26] = {};
    for (int i = 0; i < n; ++i) {
        string word;
        cin >> word;

        long long place = 1;
        for (int j = static_cast<int>(word.size()) - 1; j >= 0; --j) {
            weight[word[j] - 'A'] += place;
            place *= 10;
        }
    }

    vector<pair<long long, int>> letters;
    for (int letter = 0; letter < 26; ++letter) {
        if (weight[letter] > 0) {
            letters.emplace_back(weight[letter], letter);
        }
    }

    sort(letters.begin(), letters.end(), [](const auto& a, const auto& b) {
        if (a.first != b.first) {
            return a.first > b.first;
        }
        return a.second < b.second;
    });

    long long answer = 0;
    int digit = 9;
    for (const auto& [letter_weight, letter] : letters) {
        answer += letter_weight * digit--;
    }

    cout << answer << '\n';
}
```
