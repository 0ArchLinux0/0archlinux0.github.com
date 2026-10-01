---
title: BOJ 5052 - 전화번호 목록
author: MINJUN PARK
date: 2022-02-28 17:43:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 정렬, 전화번호 목록]
pin: false
lang: ko
translation_key: boj-5052-phone-number-list
permalink: /ko/posts/boj-5052-phone-number-list/
source_permalink: /posts/BOJ-5052/
---

[문제: BOJ 5052 — 전화번호 목록](https://www.acmicpc.net/problem/5052) · [English](/posts/BOJ-5052/) · [日本語](/ja/posts/boj-5052-phone-number-list/)

모든 전화번호를 사전순으로 정렬한 뒤, 서로 이웃한 번호만 비교합니다. 한 번호가 다른 번호의 접두사라면 짧은 번호가 긴 번호보다 사전순으로 먼저 옵니다. 짧은 번호가 끝난 다음 위치에서 긴 번호에는 숫자가 남아 있으므로 긴 번호가 뒤에 놓이기 때문입니다. 따라서 접두사 관계가 있는 번호 쌍은 정렬된 목록에서 반드시 이웃하게 됩니다. 두 번호가 완전히 같아도 한 번호는 다른 번호의 접두사이므로, 아래 접두사 비교는 중복 번호도 올바르게 찾아냅니다.

각 테스트 케이스마다 번호 목록을 새로 만들기 때문에 앞선 케이스의 번호나 판정 상태가 다음 케이스에 남지 않습니다. 최대 길이가 `L`인 문자열 `N`개를 비교하며 정렬하는 데 `O(N log N * L)`, 정렬된 이웃 번호를 검사하는 데 `O(NL)` 시간이 걸립니다. 번호 저장 공간은 `O(NL)`입니다.

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
