---
title: BOJ 11376 - 열혈강호 2
author: MINJUN PARK
date: 2022-03-09 02:09:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 네트워크 플로우, 이분 매칭, 열혈강호2]
pin: false
lang: ko
translation_key: boj-11376-job-assignment
permalink: /ko/posts/boj-11376-job-assignment/
---

[문제 링크](https://www.acmicpc.net/problem/11376)

## 문제 모델

`N`명의 직원과 `M`개의 일이 있다. 각 직원에게는 맡을 수 있는 일의 목록이 주어진다. 각 일은 최대 한 명에게만 배정하고, 직원 한 명에게는 최대 두 개의 일을 배정하면서 배정한 일의 수를 최대화해야 한다.

이분 그래프에서 직원마다 매칭용 슬롯을 두 개 만들고, 두 슬롯 모두 해당 직원이 할 수 있는 모든 일과 연결한다. 매칭에서는 각 슬롯과 각 일이 최대 한 번씩만 사용되므로 직원은 최대 두 개의 일을 맡고 각 일은 중복 배정되지 않는다. 반대로 유효한 배정이 주어지면 각 직원에게 배정된 두 개 이하의 일을 그 직원의 두 슬롯에 넣을 수 있다. 따라서 최대 매칭의 크기가 구하려는 최댓값과 같다.

## 증가 경로

모든 슬롯을 차례로 처리하면서 표준 증가 경로 탐색을 수행한다. 한 번의 탐색에서는 일을 방문 표시한다. 후보 일이 이미 매칭되어 있다면, 그 일을 맡은 슬롯을 다른 가능한 일로 옮길 수 있는지 재귀적으로 시도한다. 재배정에 성공하면 후보 일을 현재 슬롯에 배정한다. 한 번의 탐색에서 각 일을 최대 한 번만 방문하므로 순환을 방지하며, 성공적인 재배정은 매칭의 유효성을 유지한다. 증가 경로 정리에 따라 왼쪽 정점의 모든 슬롯을 처리하면 최대 매칭을 얻는다.

슬롯은 `2N`개이고, 직원-일 가능 간선은 `E`개(직원별 간선 수를 합산)다. DFS 한 번의 시간은 `O(E)`이므로 전체 시간 복잡도는 `O(N E)`이다. 그래프와 매칭 배열의 공간 복잡도는 `O(N + M + E)`이다.

## C++17 구현

```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<int>> jobs(n);
    for (int worker = 0; worker < n; ++worker) {
        int count;
        cin >> count;
        jobs[worker].resize(count);
        for (int& job : jobs[worker]) {
            cin >> job;
            --job;
        }
    }

    // matchedSlot[job]는 현재 이 일을 맡은 슬롯이다.
    vector<int> matchedSlot(m, -1);
    vector<char> visitedJob(m);

    auto augment = [&](auto&& self, int slot) -> bool {
        for (int job : jobs[slot / 2]) {
            if (visitedJob[job]) continue;
            visitedJob[job] = true;

            if (matchedSlot[job] == -1 ||
                self(self, matchedSlot[job])) {
                matchedSlot[job] = slot;
                return true;
            }
        }
        return false;
    };

    int assigned = 0;
    for (int slot = 0; slot < 2 * n; ++slot) {
        fill(visitedJob.begin(), visitedJob.end(), false);
        if (augment(augment, slot)) ++assigned;
    }

    cout << assigned << '\n';
    return 0;
}
```

재귀 호출은 증가 경로를 따라 매칭된 일의 소유 슬롯만 변경한다. 따라서 탐색이 성공할 때마다 매칭 크기가 정확히 1 증가하며, 모든 일과 슬롯의 유일성이 유지된다.
