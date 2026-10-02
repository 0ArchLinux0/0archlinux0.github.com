---
title: BOJ 7579번 — 앱
author: MINJUN PARK
date: 2022-04-18 20:05:45 +0900
categories: [PS, baekjoon]
tags: [알고리즘, 다이나믹 프로그래밍, 배낭 문제, BOJ 7579, 앱]
lang: ko
translation_key: boj-7579-app
permalink: /ko/posts/boj-7579-app/
pin: false
---

[BOJ 7579번 — 앱](https://www.acmicpc.net/problem/7579)

## 비활성화 비용 최소화

앱 $i$를 비활성화하면 메모리 $m_i$를 확보하고 비용 $c_i$가 든다. 확보한 메모리의 합이 $M$ 이상이 되도록 앱을 골라 총 비용을 최소화한다.

필요 메모리를 기준으로 DP를 만들면 배열이 너무 커질 수 있다. 반면 앱 수는 100 이하이고 각 비활성화 비용은 100 이하이므로 전체 비용 합은 최대 10,000이다. 따라서 비용을 DP의 상태로 사용한다.

## DP의 불변식

`best[b]`를 비용을 **최대** $b$까지 사용해 확보할 수 있는 최대 메모리라고 정의한다. 정확히 $b$를 쓰는 상태가 아니라, $b$ 이하의 예산을 뜻한다. 그러므로 모든 원소를 0으로 초기화해도 된다.

각 앱에 대해 비활성화하지 않는 경우와 비활성화하는 경우를 비교한다.

$$
\text{best}[b] = \max(\text{best}[b],\ \text{best}[b-c_i] + m_i).
$$

현재 앱을 두 번 고르지 않도록 예산을 큰 값부터 순회한다. 비용이 0인 앱도 이 앱에 대해 각 예산을 한 번씩만 갱신하므로 문제없이 처리된다. `best[b] >= M`을 만족하는 가장 작은 $b$가 답이다.

### 정당성

앞의 $i$개 앱만 고려했을 때 `best[b]`는 비용을 최대 $b$까지 써서 확보할 수 있는 최대 메모리라고 가정하자. 다음 앱을 선택하지 않으면 기존 상태를 유지하고, 선택하면 비용 $c_{i+1}$을 제외한 예산에서 메모리 $m_{i+1}$을 더한다. 점화식은 두 경우 중 큰 값을 취하므로 불변식을 유지한다. 예산을 내림차순으로 갱신하면 현재 앱을 반영하기 전 상태만 읽으므로 같은 앱을 재사용하지 않는다. 따라서 모든 앱을 처리한 뒤 처음으로 $M$ 이상을 확보하는 예산이 최소 비용이다.

시간 복잡도는 $O(NC)$, 공간 복잡도는 $O(C)$이다. 여기서 $C=\sum_i c_i$이다.

## C++17 코드

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

## 출처와 수정 이력

MINJUN PARK이 2022-04-18에 게시하고 CC BY 4.0을 표시한 [“백준 7579번 - 앱”](https://ilikechicken.tistory.com/39)을 바탕으로 작성했다. 비용 기준 0/1 배낭 접근은 유지하되, DP가 ‘정확히 든 비용’이 아니라 ‘예산 이하’의 의미임을 명확히 하고 GNU 가변 길이 배열을 표준 C++17 코드로 교체했다.