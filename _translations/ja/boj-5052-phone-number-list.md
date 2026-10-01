---
title: BOJ 5052 - 電話番号リスト
author: MINJUN PARK
date: 2022-02-28 17:43:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, ソート, 電話番号リスト]
pin: false
lang: ja
translation_key: boj-5052-phone-number-list
permalink: /ja/posts/boj-5052-phone-number-list/
source_permalink: /posts/BOJ-5052/
---

[問題: BOJ 5052 — 電話番号リスト](https://www.acmicpc.net/problem/5052) · [English](/posts/BOJ-5052/) · [한국어](/ko/posts/boj-5052-phone-number-list/)

電話番号をすべて辞書順にソートし、隣り合う番号だけを比較します。ある番号が別の番号の接頭辞なら、短い番号が長い番号より辞書順で前に並びます。短い番号が終わった位置で、長い番号にはまだ数字が残っているためです。したがって、接頭辞の関係にある番号の組は、ソート後には必ず隣り合います。2 つの番号が完全に同じ場合も、一方は他方の接頭辞なので、以下の比較で重複も正しく検出できます。

各テストケースで番号リストを新しく作るため、前のケースの番号や判定状態が次のケースに残ることはありません。最大長が `L` の文字列 `N` 個を比較しながらソートする時間は `O(N log N * L)`、ソート済みの隣接番号を調べる時間は `O(NL)` です。番号の保存に必要な空間は `O(NL)` です。

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

    int test_cases;
    cin >> test_cases;

    while (test_cases--) {
        int n;
        cin >> n;

        vector<string> numbers(n);
        for (string& number : numbers) {
            cin >> number;
        }

        sort(numbers.begin(), numbers.end());

        bool consistent = true;
        for (int i = 0; i + 1 < n; ++i) {
            if (numbers[i + 1].compare(0, numbers[i].size(), numbers[i]) == 0) {
                consistent = false;
                break;
            }
        }

        cout << (consistent ? "YES" : "NO") << '\n';
    }
}
```
