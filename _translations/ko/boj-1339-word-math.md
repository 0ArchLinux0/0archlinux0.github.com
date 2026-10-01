---
title: BOJ 1339 - 단어 수학
author: MINJUN PARK
date: 2022-02-26 03:16:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 그리디, 단어 수학]
pin: false
lang: ko
translation_key: boj-1339-word-math
permalink: /ko/posts/boj-1339-word-math/
source_permalink: /posts/BOJ-1339/
---

[문제: BOJ 1339 — 단어 수학](https://www.acmicpc.net/problem/1339) · [English](/posts/BOJ-1339/) · [日本語](/ja/posts/boj-1339-word-math/)

각 알파벳은 그 글자가 나타나는 모든 위치에서 동일한 숫자를 나타냅니다. 단어의 일의 자리에 있는 글자는 해당 숫자를 한 번 더하고, 십의 자리에 있으면 숫자의 10배를 더하며, 그보다 왼쪽 자리도 같은 방식으로 계산합니다. 예를 들어 `ABC`의 값은 `100 * value[A] + 10 * value[B] + value[C]`입니다. 모든 단어의 모든 자릿수 기여분을 합치면 전체 합은 `sum(weight[letter] * digit[letter])`로 쓸 수 있습니다.

가장 큰 가중치에 가장 큰 숫자를 배정해야 합니다. 이를 교환 논증으로 확인할 수 있습니다. 가중치 `w1 > w2`에 숫자 `d1 < d2`가 배정되어 있다고 가정해 봅시다. 두 숫자를 바꾸면 합은 `(w1 * d2 + w2 * d1) - (w1 * d1 + w2 * d2) = (w1 - w2) * (d2 - d1) > 0`만큼 증가합니다. 따라서 이런 역순 배정은 최적일 수 없습니다. 가중치를 내림차순으로 정렬해 `9, 8, ...`을 차례로 배정하면 합이 최대가 됩니다. 가중치가 같은 글자는 알파벳 순으로 정렬해 결과를 결정적으로 만들 수 있으며, 같은 가중치끼리 숫자를 바꾸어도 합은 달라지지 않습니다.

전체 문자 수를 `T`, 서로 다른 글자 수를 `A`라고 하면 가중치 계산에 `O(T)`, 정렬에 `O(A log A)` 시간이 걸립니다. 따라서 전체 시간 복잡도는 `O(T + A log A)`이며, 가중치와 정답에는 `long long`을 사용합니다.

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
