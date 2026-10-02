---
title: BOJ 1199번 — 오일러 회로
author: MINJUN PARK
date: 2022-04-26 17:26:02 +0900
categories: [PS, baekjoon]
tags: [알고리즘, 그래프 이론, 오일러 회로, Hierholzer 알고리즘, BOJ 1199]
lang: ko
translation_key: boj-1199-euler-circuit
permalink: /ko/posts/boj-1199-euler-circuit/
pin: false
---

[BOJ 1199번 — 오일러 회로](https://www.acmicpc.net/problem/1199)

## 입력을 다중 그래프로 해석하기

인접 행렬의 각 원소는 무방향 정점 쌍을 잇는 간선의 개수다. 평행 간선은 서로 다른 간선이므로 회로에서 각각 사용해야 한다. 대각 원소는 루프의 개수이며, 루프 하나는 차수에 2를 더하지만 탐색에서는 한 번 지나간다.

무방향 그래프에 오일러 회로가 있으려면 모든 정점의 차수가 짝수이고, 간선에 닿는 모든 정점이 하나의 연결 요소에 속해야 한다. 고립 정점은 회로 존재 여부에 영향을 주지 않는다. 원문 코드는 차수의 홀짝만 확인했기 때문에, 서로 분리된 두 짝수 차수 성분이 있으면 일부 간선만 사용한 회로를 출력할 수 있었다.

## 반복형 Hierholzer 알고리즘

간선이 있는 정점에서 시작해 아직 사용하지 않은 간선을 하나씩 소비하고, 도착 정점을 스택에 넣는다. 스택 맨 위 정점에 남은 간선이 없으면 회로에 추가한 뒤 되돌아간다. 이렇게 만든 정점열을 뒤집으면 모든 간선을 포함한 오일러 회로가 된다.

간선 개수를 평탄화한 인접 행렬에 저장한다. 재귀를 사용하지 않으며, 만들어진 회로의 정점 수가 $E+1$인지 확인해 연결 여부를 판정한다. 이 검사는 고립 정점을 무시하면서 간선이 있는 분리 성분은 거부한다.

간선이 하나도 없으면 길이 0인 회로를 정점 1로 출력한다.

## C++17 코드

```cpp
#include <algorithm>
#include <cstddef>
#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> remaining(static_cast<size_t>(n) * n, 0);
    vector<long long> degree(n, 0);
    long long edge_count = 0;

    for (int u = 0; u < n; ++u) {
        for (int v = 0; v < n; ++v) {
            int count;
            cin >> count;
            if (u > v || count == 0) continue;

            remaining[static_cast<size_t>(u) * n + v] = count;
            edge_count += count;
            if (u == v) {
                degree[u] += 2LL * count;
            } else {
                remaining[static_cast<size_t>(v) * n + u] = count;
                degree[u] += count;
                degree[v] += count;
            }
        }
    }

    for (long long value : degree) {
        if (value % 2 != 0) {
            cout << -1 << '\n';
            return 0;
        }
    }

    int start = -1;
    for (int u = 0; u < n; ++u) {
        if (degree[u] > 0) {
            start = u;
            break;
        }
    }
    if (start == -1) {
        cout << 1 << '\n';
        return 0;
    }

    vector<int> next_neighbor(n, 0);
    vector<int> stack;
    vector<int> circuit;
    stack.reserve(static_cast<size_t>(edge_count) + 1);
    circuit.reserve(static_cast<size_t>(edge_count) + 1);
    stack.push_back(start);

    while (!stack.empty()) {
        int u = stack.back();
        int& v = next_neighbor[u];
        while (v < n && remaining[static_cast<size_t>(u) * n + v] == 0) {
            ++v;
        }

        if (v == n) {
            circuit.push_back(u);
            stack.pop_back();
        } else {
            int w = v;
            --remaining[static_cast<size_t>(u) * n + w];
            if (u != w) {
                --remaining[static_cast<size_t>(w) * n + u];
            }
            stack.push_back(w);
        }
    }

    if (static_cast<long long>(circuit.size()) != edge_count + 1) {
        cout << -1 << '\n';
        return 0;
    }

    reverse(circuit.begin(), circuit.end());
    for (size_t i = 0; i < circuit.size(); ++i) {
        if (i > 0) cout << ' ';
        cout << circuit[i] + 1;
    }
    cout << '\n';
}
```

시간 복잡도는 $O(N^2+E)$다. 인접 행렬을 한 번 입력하고, 각 간선을 한 번 소비하며, 각 행의 다음 이웃 포인터는 최대 $N$번 증가한다. 행렬은 $O(N^2)$ 공간을 쓰고, 스택과 회로는 $O(E)$ 공간을 쓴다.

## 출처와 수정 이력

MINJUN PARK이 2022-04-26에 게시하고 CC BY 4.0을 표시한 [“백준 1199번 - 오일러 회로”](https://ilikechicken.tistory.com/48)를 바탕으로 작성했다. 다중 그래프와 Hierholzer 접근은 유지하면서 빠진 연결 조건을 추가하고 재귀를 반복형 구현으로 바꿨다.

## 참고 자료

- [BOJ 1199 — Euler Circuit](https://www.acmicpc.net/problem/1199)
- [오일러 경로와 회로](https://cp-algorithms.com/graph/euler_path.html)