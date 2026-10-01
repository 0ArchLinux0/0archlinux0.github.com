---
title: BOJ 1725 - 히스토그램
author: MINJUN PARK
date: 2022-02-18 12:33:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 히스토그램, 단조 스택]
pin: false
lang: ko
translation_key: boj-1725-largest-rectangle
permalink: /ko/posts/boj-1725-largest-rectangle/
source_permalink: /posts/BOJ-1725/
---

[문제: BOJ 1725 — 히스토그램](https://www.acmicpc.net/problem/1725) · [English](/posts/BOJ-1725/) · [日本語](/ja/posts/boj-1725-largest-rectangle/)

각 막대를 높이로 하는 직사각형 중 가장 넓은 것은 해당 막대가 가장 낮은 막대인 구간에서 찾을 수 있습니다. 이 구간의 양쪽 경계는 해당 막대보다 높이가 엄격히 낮은 가장 가까운 막대입니다. 왼쪽부터 단조 스택으로 훑으면 더 낮은 막대를 만나는 순간 경계를 확정할 수 있습니다.

스택에는 높이가 비내림차순이 되도록 막대의 인덱스를 저장합니다. 현재 막대가 스택 맨 위의 막대보다 낮으면, 맨 위 인덱스는 더 오른쪽으로 확장할 수 없습니다. 현재 인덱스가 그 막대의 오른쪽에서 처음 만나는 더 낮은 막대이기 때문입니다. 해당 인덱스를 꺼낸 뒤의 새 스택 맨 위는 왼쪽에서 가장 가까운 더 낮은 막대입니다. 따라서 직사각형의 너비는 `i - left - 1`이고, 스택이 비었다면 너비는 `i`입니다. 높이가 같은 막대도 꺼내므로 남은 인덱스의 왼쪽 스택 이웃은 더 낮으며, 같은 높이도 일관되게 처리됩니다.

입력 막대를 모두 처리한 뒤 높이 `0`인 센티널을 넣어 남아 있는 인덱스를 전부 꺼냅니다. 재귀 호출이 필요하지 않습니다. 각 인덱스는 한 번 들어가고 한 번 나오므로 시간 복잡도는 `O(N)`, 보조 공간 복잡도는 `O(N)`입니다. 넓이와 면적 계산의 오버플로를 피하도록 높이와 면적에는 `long long`을 사용합니다.

## C++

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<long long> height(n);
  for (long long &bar : height) cin >> bar;

  vector<int> st;
  st.reserve(n);
  long long answer = 0;

  for (int i = 0; i <= n; ++i) {
    const long long current = (i == n ? 0 : height[i]);
    while (!st.empty() && height[st.back()] >= current) {
      const long long barHeight = height[st.back()];
      st.pop_back();
      const int width = st.empty() ? i : i - st.back() - 1;
      answer = max(answer, barHeight * width);
    }
    if (i < n) st.push_back(i);
  }

  cout << answer;
}
```
