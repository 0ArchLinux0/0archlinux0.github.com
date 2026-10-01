---
title: プログラマーズ — 最大の数
author: MINJUN PARK
date: 2021-12-27 00:56:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, Coding Interview, Programmers, Largest Number, 가장 큰 수, 프로그래머스]
pin: false
lang: ja
translation_key: programmers-largest-number
permalink: /ja/posts/programmers-largest-number/
---

[問題リンク](https://programmers.co.kr/learn/courses/30/lessons/42746)

## 解法

各数値を10進数の文字列に変換し、`a + b > b + a` のとき `a` が `b` より前になるように並べ替えます。たとえば `"330" > "303"` なので、`"3"` は `"30"` より前になります。この順に連結すると、作れる最大の数になります。

この比較規則は交換論法で説明できます。連結結果の隣り合う文字列が `a` と `b` の場合、順序によって結果に加わる部分はそれぞれ `a + b` または `b + a` であり、その前後の桁は変わりません。したがって、より大きい方の並びを選べば結果が悪化することはありません。ソートですべてのペアにこの順序を適用することで、隣接する二つの文字列を交換しても改善できない最大の連結結果が得られます。

ソート後の先頭文字列が `"0"` なら、入力の数値はすべて0です。0を繰り返した文字列ではなく `"0"` を返します。問題の制約どおり、入力には0以上の整数が少なくとも1つ含まれるものとします。

ソートでは `O(N log N)` 回の比較を行い、各比較で連結と比較に `O(L)` の文字処理が必要です。ここで `L` は入力数値の最大桁数です。最終文字列の生成には `O(NL)` 時間と空間がかかります。

## C++

```cpp
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

string solution(vector<int> numbers) {
    vector<string> values;
    values.reserve(numbers.size());
    for (int number : numbers) {
        values.push_back(to_string(number));
    }

    sort(values.begin(), values.end(), [](const string& a, const string& b) {
        return a + b > b + a;
    });

    if (values.front() == "0") {
        return "0";
    }

    string answer;
    for (const string& value : values) {
        answer += value;
    }
    return answer;
}
```
