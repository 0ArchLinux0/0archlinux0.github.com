---
title: BOJ 13275 - 가장 긴 팰린드롬 부분 문자열
author: MINJUN PARK
date: 2022-02-22 18:13:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 마나커 알고리즘, 문자열]
pin: false
lang: ko
translation_key: boj-13275-longest-palindrome
permalink: /ko/posts/boj-13275-longest-palindrome/
source_permalink: /posts/BOJ-13275/
---

[문제: BOJ 13275 — 가장 긴 팰린드롬 부분 문자열](https://www.acmicpc.net/problem/13275) · [English](/posts/BOJ-13275/) · [日本語](/ja/posts/boj-13275-longest-palindrome/)

마나커 알고리즘은 가능한 각 중심에서 팰린드롬의 반지름을 저장합니다. 홀수 길이 팰린드롬의 중심은 문자 하나입니다. `radiusOdd[i]`는 중심 문자까지 포함한 길이의 절반이므로 팰린드롬의 길이는 `2 * radiusOdd[i] - 1`입니다. 짝수 길이 팰린드롬의 중심은 `i` 바로 앞의 간격입니다. `radiusEven[i]`는 그 간격을 중심으로 일치하는 문자 쌍의 수이며, 팰린드롬 길이는 `2 * radiusEven[i]`입니다.

홀수 중심과 짝수 중심을 각각 처리하면서 지금까지 찾은 가장 오른쪽 팰린드롬의 구간 `[left, right]`를 유지합니다. 다음 중심 `i`가 이 구간 안에 있으면 구간의 중심을 기준으로 대칭인 위치를 찾아 그곳에서 이미 계산한 반지름을 재사용합니다. 다만 알려진 팰린드롬 안에서 확실히 보장되는 범위인 `right - i + 1`까지만 재사용할 수 있습니다. `i`가 구간 밖이면 가장 작은 반지름(홀수 중심은 중심 문자 하나, 짝수 중심은 문자 쌍 0개)에서 시작합니다. 현재 반지름 바로 바깥의 문자들을 비교해 같으면 계속 확장합니다. 확장한 팰린드롬이 기존 구간보다 오른쪽으로 더 나아가면 `[left, right]`를 갱신합니다.

확장에 성공할 때마다 오른쪽 경계가 앞으로 이동하므로 전체 수행 시간은 선형입니다. 답은 모든 홀수·짝수 팰린드롬 길이 중 최댓값입니다. 시간 복잡도는 `O(N)`, 공간 복잡도는 `O(N)`이며 길이 `10^6` 이하의 문자열을 처리할 수 있습니다.

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  string s;
  cin >> s;
  const int n = static_cast<int>(s.size());

  vector<int> radiusOdd(n);
  vector<int> radiusEven(n);
  int longest = 1;

  int left = 0;
  int right = -1;
  for (int i = 0; i < n; ++i) {
    int radius = (i > right)
                     ? 1
                     : min(radiusOdd[left + right - i], right - i + 1);
    while (i - radius >= 0 && i + radius < n &&
           s[i - radius] == s[i + radius]) {
      ++radius;
    }
    radiusOdd[i] = radius;
    longest = max(longest, 2 * radius - 1);

    if (i + radius - 1 > right) {
      left = i - radius + 1;
      right = i + radius - 1;
    }
  }

  left = 0;
  right = -1;
  for (int i = 0; i < n; ++i) {
    int radius = (i > right)
                     ? 0
                     : min(radiusEven[left + right - i + 1], right - i + 1);
    while (i - radius - 1 >= 0 && i + radius < n &&
           s[i - radius - 1] == s[i + radius]) {
      ++radius;
    }
    radiusEven[i] = radius;
    longest = max(longest, 2 * radius);

    if (i + radius - 1 > right) {
      left = i - radius;
      right = i + radius - 1;
    }
  }

  cout << longest << '\n';
}
```
