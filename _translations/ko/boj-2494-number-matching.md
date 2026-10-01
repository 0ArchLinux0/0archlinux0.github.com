---
title: BOJ 2494 - 숫자 맞추기
author: MINJUN PARK
date: 2022-03-11 05:00:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, DP, 숫자 맞추기]
pin: false
lang: ko
translation_key: boj-2494-number-matching
permalink: /ko/posts/boj-2494-number-matching/
---

[문제 링크](https://www.acmicpc.net/problem/2494)

## 다이얼 조작 규칙과 동적 계획법

다이얼은 왼쪽부터 번호를 매깁니다. 다이얼 `i`를 양수만큼 돌리면 `i`번 다이얼과 그 아래의 모든 다이얼이 함께 왼쪽으로 회전합니다. 음수만큼 돌리면 `i`번 다이얼만 오른쪽으로 회전합니다. 출력하는 회전량은 부호가 있는 정수이며 양수는 왼쪽, 음수는 오른쪽 회전입니다.

왼쪽부터 다이얼을 처리합니다. `dp[i][carry]`를 `i`번 다이얼까지 처리했을 때의 최소 회전 횟수라고 하고, `carry`는 이전 다이얼에서 발생한 왼쪽 회전량의 합을 10으로 나눈 나머지라고 합시다. 즉, 이전의 왼쪽 회전으로 현재 다이얼에 이미 적용된 회전량입니다. 현재 숫자는 `(source[i] + carry) mod 10`입니다. 이 숫자를 목표 숫자로 만들기 위해 필요한 가장 작은 음이 아닌 왼쪽 회전량을 `left`라고 합니다.

두 가지 선택이 있습니다. `left`만큼 왼쪽으로 돌리면 비용은 `left`이고 다음 다이얼에 전달되는 회전량은 `(carry + left) mod 10`입니다. `left`가 0이 아니라면 `left - 10`만큼 오른쪽으로 돌릴 수도 있습니다. 비용은 `10 - left`이며 다음 다이얼에 전달되는 회전량은 그대로입니다. `left`가 0이면 아무것도 돌리지 않는 선택만 필요합니다. 10회전은 해를 개선하지 않습니다. 각 상태에 선택된 이전 carry와 부호 있는 회전량을 저장합니다. 마지막 다이얼의 최소 비용 상태를 찾은 뒤 predecessor를 역추적하여 모든 다이얼의 회전량을 출력합니다.

각 다이얼에는 carry 상태가 10개이고 상태마다 전이는 최대 2개이므로 시간 및 메모리 복잡도는 모두 `O(N · 10)`입니다.

## C++17 구현

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
