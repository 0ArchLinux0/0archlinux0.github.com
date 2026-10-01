---
title: 백준 19565번 - 수열 만들기
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [알고리즘, 구성적 알고리즘, 오일러 회로, Hierholzer 알고리즘, 백준 19565, 수열]
lang: ko
translation_key: boj-19565-sequence
permalink: /ko/posts/boj-19565-sequence/
pin: false
---

[백준 19565번 - 수열 만들기](https://www.acmicpc.net/problem/19565)

## 방향 그래프로 모델링하기

수열의 각 원소는 $1,\dots,N$ 중 하나이며, 첫 원소와 마지막 원소는 모두 1이어야 한다. 또한 같은 순서쌍이 인접한 원소로 두 번 이상 나타나면 안 된다. 순서쌍 $(x,y)$와 $(y,x)$는 서로 다르며, $(x,x)$처럼 같은 값으로 이루어진 쌍도 허용된다.

값마다 정점 하나를 두고, 모든 순서쌍 $(x,y)$에 대해 방향 간선 $x\to y$를 만든다. 각 정점에서 자기 자신으로 향하는 루프도 포함한다. 1에서 시작해 1로 끝나는 수열은 정점 1에서 시작해 돌아오는 닫힌 보행에 대응하며, 수열의 각 인접 쌍은 그 보행에서 지나간 간선 하나와 같다. 따라서 간선을 중복해서 지나지 않으면 인접 순서쌍도 중복되지 않는다.

각 정점의 진입 차수와 진출 차수는 모두 $N$이다. 모든 정점 쌍 사이에 방향 간선이 있으므로 그래프는 강연결이다. 따라서 오일러 회로가 존재한다. Hierholzer 알고리즘으로 모든 $N^2$개 간선을 정확히 한 번씩 사용하는 회로를 구할 수 있고, 정점 1에서 시작하면 결과 수열은 1에서 시작해 1로 끝난다.

## 정당성 및 최대 길이

오일러 회로는 각 방향 간선을 정확히 한 번씩 지나므로 연속한 정점 쌍이 모두 서로 다르며, 가능한 모든 순서쌍을 포함한다. 간선이 $N^2$개이므로 회로를 정점열로 나타낸 수열의 길이는 $N^2+1$이다.

반대로 $1,\dots,N$에서 만들 수 있는 순서쌍은 루프 $N$개를 포함해 총 $N^2$개뿐이다. 유효한 수열은 각 쌍을 최대 한 번만 사용할 수 있으므로 인접 쌍은 최대 $N^2$개이고, 수열의 길이는 최대 $N^2+1$이다. 오일러 회로가 이 상한에 도달하므로 구성한 수열은 최대 길이다.

각 정점의 나가는 간선을 인접 리스트에 저장한다. Hierholzer 알고리즘은 간선마다 한 번씩 처리하므로 시간 복잡도는 $O(N^2)$이며, 그래프와 결과 수열의 공간 복잡도도 $O(N^2)$이다.

## C++17 코드

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

    vector<vector<int>> adj(n + 1);
    for (int from = 1; from <= n; ++from) {
        for (int to = 1; to <= n; ++to) {
            adj[from].push_back(to);
        }
    }

    vector<int> stack = {1};
    vector<int> circuit;
    circuit.reserve(n * n + 1);

    while (!stack.empty()) {
        int cur = stack.back();
        if (!adj[cur].empty()) {
            int next = adj[cur].back();
            adj[cur].pop_back();
            stack.push_back(next);
        } else {
            circuit.push_back(cur);
            stack.pop_back();
        }
    }

    reverse(circuit.begin(), circuit.end());

    cout << circuit.size() << '\n';
    for (int vertex : circuit) {
        cout << vertex << ' ';
    }
    cout << '\n';
    return 0;
}
```
