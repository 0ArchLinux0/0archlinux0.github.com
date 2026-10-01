---
title: 백준 1006번 - 습격자 초라기
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [PS, 알고리즘, 백준, BOJ, DP]
lang: ko
translation_key: boj-1006-defense
permalink: /ko/posts/boj-1006-defense/
---

[문제 링크](https://www.acmicpc.net/problem/1006)

## 문제 모델

적이 배치된 2행 `N`열의 원형 격자가 있다. 소대 하나는 한 칸을 맡거나, 적의 수 합이 `W` 이하인 인접한 두 칸을 함께 맡을 수 있다. 모든 칸을 맡는 데 필요한 소대의 최소 수를 구한다. 인접한 두 칸은 같은 열의 위아래 칸이거나, 같은 행의 이웃 열 칸이다. 같은 행에서는 `N`열과 1열도 이웃한다.

원형으로 이어지는 두 쌍이 일반적인 프로필 DP의 걸림돌이다. 이를 해결하기 위해 경계를 가로지르는 쌍을 선택하지 않는 경우, 위쪽 쌍만 선택하는 경우, 아래쪽 쌍만 선택하는 경우, 두 쌍을 모두 선택하는 경우의 네 가지를 열거한다. 선택한 쌍은 미리 배치하고 양 끝의 해당 칸을 남은 문제에서 제외한다. 나머지는 선형 띠가 되어 열마다 상수 개 상태만으로 처리할 수 있다.

## 선형 프로필 DP

경계 쌍 선택을 하나 고정하고, `forced[c]`를 열 `c`에서 이미 덮인 칸을 나타내는 2비트 마스크로 둔다. 비트 0은 위쪽, 비트 1은 아래쪽이다. 미리 덮이는 칸은 0열과 `N-1`열에만 있다. 선택한 경계 쌍의 소대 수는 선형 DP 결과에 마지막으로 더한다.

왼쪽에서 오른쪽으로 열을 처리한다. 열 `c` 시작 시의 DP 상태는 `incoming`이라는 2비트 마스크이며, 이전 열에서 오는 가로 소대가 이미 덮은 칸을 나타낸다. 들어온 칸과 미리 덮인 칸이 겹치면 그 상태는 버린다. 그 외에는 `incoming | forced[c]`를 현재 열에서 이미 덮인 칸으로 놓고 채우기를 시작한다.

현재 열에서 아직 덮이지 않은 첫 번째 칸을 골라 다음을 시도한다.

- 해당 칸만 소대 하나로 맡는다.
- 위쪽 칸이며 두 칸의 적 수 합이 `W` 이하라면, 같은 열의 위아래 칸을 소대 하나로 맡는다.
- 두 칸의 적 수 합이 `W` 이하라면, 다음 열의 같은 행 칸과 함께 맡는다. 이때 다음 열의 칸은 `outgoing` 마스크에 표시한다. 다음 칸이 선택한 경계 쌍으로 이미 덮이는 칸이면 이 가로 쌍은 만들지 않는다.

열의 두 칸을 모두 처리하면 `outgoing` 마스크를 다음 열의 DP 상태로 전달한다. 열당 마스크는 네 개뿐이며 한 열에서 채우는 재귀는 최대 두 칸을 다루므로 시간 복잡도는 `O(N)`, 공간 복잡도는 `O(N)`이다. 경계 선택 네 가지를 열거해도 상수 배만 늘어난다.

`N = 1`이면 서로 다른 가로 이웃 칸은 없다. 유일한 가능한 두 칸 소대는 그 열의 위아래 칸을 함께 맡는 경우다. 두 적 수 합이 `W` 이하이면 답은 1, 아니면 2이다. 한 열 원의 양 끝을 서로 다른 두 칸으로 잘못 취급하지 않도록 별도 처리한다.

`N >= 2`에서는 `top[0] + top[N-1] <= W`일 때만 위쪽 경계 쌍을 선택할 수 있고, 아래쪽도 마찬가지다. 경계 쌍을 선택하지 않는 경우는 항상 확인한다. 각 선택에서 양 끝 마스크를 사용해 이미 맡긴 칸이 다른 소대에 다시 배정되지 않게 하고, 나머지 칸은 선형 DP로 덮는다. `N = 2`에서도 선택된 경계 쌍이 차지한 칸은 선형 변을 따라 다시 짝지을 수 없다.

## C++17 구현

```cpp
#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int N, W;
        cin >> N >> W;
        vector<array<int, 2>> enemy(N);
        for (int r = 0; r < 2; ++r)
            for (int c = 0; c < N; ++c)
                cin >> enemy[c][r];

        if (N == 1) {
            cout << (enemy[0][0] + enemy[0][1] <= W ? 1 : 2) << '\n';
            continue;
        }

        const int INF = 1e9;
        int answer = INF;

        // wrap 비트 0은 위쪽, 비트 1은 아래쪽 경계 쌍이다.
        for (int wrap = 0; wrap < 4; ++wrap) {
            bool valid = true;
            int wrapCost = 0;
            vector<int> forced(N, 0);
            for (int r = 0; r < 2; ++r) {
                if ((wrap >> r) & 1) {
                    if (enemy[0][r] + enemy[N - 1][r] > W) {
                        valid = false;
                        break;
                    }
                    forced[0] |= 1 << r;
                    forced[N - 1] |= 1 << r;
                    ++wrapCost;
                }
            }
            if (!valid) continue;

            // dp[incoming mask] = 이 열을 채우기 전까지 사용한 최소 소대 수.
            array<int, 4> dp{0, INF, INF, INF};
            for (int c = 0; c < N; ++c) {
                array<int, 4> next{INF, INF, INF, INF};
                for (int incoming = 0; incoming < 4; ++incoming) {
                    if (dp[incoming] == INF || (incoming & forced[c])) continue;
                    int occupied = incoming | forced[c];

                    auto fill = [&](auto&& self, int mask, int outgoing, int cost) -> void {
                        if (mask == 3) {
                            next[outgoing] = min(next[outgoing], dp[incoming] + cost);
                            return;
                        }
                        int row = (mask & 1) ? 1 : 0;
                        int bit = 1 << row;

                        // 이 칸만 소대 하나로 맡긴다.
                        self(self, mask | bit, outgoing, cost + 1);

                        // 같은 열의 위아래 칸을 소대 하나로 맡긴다.
                        if (row == 0 && mask == 0 &&
                            enemy[c][0] + enemy[c][1] <= W) {
                            self(self, 3, outgoing, cost + 1);
                        }

                        // 다음 열 같은 행의 칸과 함께 맡긴다.
                        if (c + 1 < N && !(outgoing & bit) &&
                            !(forced[c + 1] & bit) &&
                            enemy[c][row] + enemy[c + 1][row] <= W) {
                            self(self, mask | bit, outgoing | bit, cost + 1);
                        }
                    };
                    fill(fill, occupied, 0, 0);
                }
                dp = next;
            }
            answer = min(answer, dp[0] + wrapCost);
        }

        cout << answer << '\n';
    }
}
```

각 단계에서 선택한 첫 미배정 칸은 혼자 맡거나, 같은 열의 다른 칸과 세로로 맡거나, 다음 열의 같은 행 칸과 가로로 맡아야 하므로 전이가 모든 경우를 빠짐없이 포함한다. 사용하는 칸이 이미 배정되지 않았는지 확인하고, 두 칸 소대에는 수용 한도를 적용한다. 따라서 겹침 없는 모든 배치를 프로필이 표현하고 각 전이는 정확히 소대 하나를 더한다. 마지막으로 경계 쌍 선택 네 가지 중 최소를 택하면 원형 배치의 최적해가 된다.