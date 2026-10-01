---
title: BOJ 1240 - 노드사이의 거리
author: MINJUN PARK
date: 2022-03-01 20:29:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 트리, 그래프, DFS]
pin: false
lang: ko
translation_key: boj-1240-tree-distance
permalink: /ko/posts/boj-1240-tree-distance/
source_permalink: /posts/BOJ-1240/
---

[문제: BOJ 1240 — 노드사이의 거리](https://www.acmicpc.net/problem/1240) · [English](/posts/BOJ-1240/) · [日本語](/ja/posts/boj-1240-tree-distance/)

입력 그래프는 트리이므로 임의의 두 정점 사이에는 경로가 정확히 하나만 존재합니다. 따라서 최단 거리는 그 경로에 있는 간선 가중치의 합이며, 일반적인 최단 경로 알고리즘은 필요하지 않습니다.

각 질의마다 시작 정점에서 명시적인 스택으로 탐색합니다. 스택의 각 원소에는 현재 정점, 부모 정점, 시작점부터 현재 정점까지의 누적 거리를 저장합니다. 이미 지나온 간선을 되짚지 않도록 부모 방향의 간선은 건너뜁니다. 목표 정점에 도달하면 누적 거리를 출력하고 해당 질의의 탐색을 끝냅니다. 반복문 기반 탐색이므로 트리가 일렬이어도 재귀 호출 스택을 사용하지 않습니다.

입력 정점 번호는 1부터 시작하므로 `N + 1` 크기의 배열에서 그대로 인덱스로 사용할 수 있습니다. 간선 가중치는 최대 10,000이고 `N <= 1,000`이므로 경로의 간선은 최대 999개, 경로 길이는 최대 9,990,000입니다. 누적 거리는 64비트 정수에 안전하게 저장됩니다. 그래프와 탐색 스택은 `O(N)` 메모리를 사용하며, `M`개 질의를 모두 처리하는 최악 시간은 `O(NM)`입니다.

## C++17

```cpp
#include <iostream>
#include <tuple>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<pair<int, int>>> graph(n + 1);
    for (int i = 0; i < n - 1; ++i) {
        int a, b, weight;
        cin >> a >> b >> weight;
        graph[a].push_back({b, weight});
        graph[b].push_back({a, weight});
    }

    while (m--) {
        int start, target;
        cin >> start >> target;

        vector<tuple<int, int, long long>> pending;
        pending.emplace_back(start, 0, 0);

        while (!pending.empty()) {
            auto [node, parent, distance] = pending.back();
            pending.pop_back();

            if (node == target) {
                cout << distance << '\n';
                break;
            }

            for (auto [next, weight] : graph[node]) {
                if (next != parent) {
                    pending.emplace_back(next, node, distance + weight);
                }
            }
        }
    }
}
```
