---
title: BOJ 11378 - 열혈강호 4
author: MINJUN PARK
date: 2022-03-09 18:46:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 네트워크 플로우, 최대 유량, 열혈강호4]
pin: false
lang: ko
translation_key: boj-11378-extra-jobs
permalink: /ko/posts/boj-11378-extra-jobs/
---

[문제 링크](https://www.acmicpc.net/problem/11378)

## 문제 모델

`N`명의 직원과 `M`개의 일이 주어지고, 직원마다 맡을 수 있는 일의 목록이 주어진다. 각 직원은 기본적으로 최대 한 일을 맡을 수 있으며, 전체 추가 배정 횟수는 `K` 이하이다. 추가 배정은 직원 한 명당 최대 한 번만 가능하므로 직원은 최대 두 일을 맡는다. 각 일은 최대 한 직원에게만 배정한다. 이 조건을 만족하면서 배정된 일의 수를 최대화한다.

## 최대 유량 모델

정점은 소스, 직원 `N`명, 보너스 정점, 일 `M`개, 싱크로 구성한다. 간선을 다음과 같이 둔다.

- 소스 → 직원: 용량 1 (각 직원의 기본 배정)
- 소스 → 보너스 정점: 용량 `K` (추가 배정의 전체 한도)
- 보너스 정점 → 직원: 용량 1 (직원별 추가 배정은 최대 한 번)
- 직원 → 맡을 수 있는 일: 용량 1
- 일 → 싱크: 용량 1 (각 일은 최대 한 번 배정)

한 직원으로 들어오는 유량은 직접 간선과 보너스 간선에서 각각 최대 1이므로 최대 2이다. 직원에게 추가로 들어오는 보너스 유량도 최대 1이며, 보너스 정점으로 유입되는 유량은 `K` 이하이다. 따라서 직원은 최대 두 일을 받고, 추가 배정은 전체적으로 `K`번 이하이다. 보너스 경로를 통해서만 유량을 받은 직원이 있더라도 그 배정은 최대 한 번으로 제한된다. 유효한 배정은 각 직원의 첫 일을 직접 간선으로, 두 번째 일을 보너스 경로로 보내는 유량으로 표현할 수 있다. 반대로 정수 유량의 직원-일 간선은 각 일을 중복 없이 배정하며 위 용량 제한을 만족한다. 그러므로 최대 유량이 최대 배정 일 수와 같다.

## 알고리즘과 복잡도

Dinic 알고리즘은 잔여 그래프에서 BFS로 레벨 그래프를 만들고, 레벨을 따라 차단 유량을 보낸다. 더 이상 증가 유량이 없을 때의 유량이 최대 유량이다. 여기서 `V = N + M + 3`, `E = A + 2N + M + 1`이며, `A`는 직원-일 적격 간선 수다. 일반 유량 네트워크에서 Dinic의 시간 복잡도는 `O(V^2 E)`이고 공간 복잡도는 `O(V + E)`이다. 문제의 제한은 `N, M ≤ 1,000`, `K ≤ N`이다. 이 구현은 문제 구조에 대한 더 빠른 최악 시간 보장을 주장하지 않는다.

## C++17 구현

```cpp
#include <algorithm>
#include <iostream>
#include <queue>
#include <vector>
using namespace std;

struct Dinic {
    struct Edge {
        int to;
        int rev;
        int cap;
    };

    vector<vector<Edge>> graph;
    vector<int> level;
    vector<int> nextEdge;

    explicit Dinic(int n)
        : graph(n), level(n), nextEdge(n) {}

    void addEdge(int from, int to, int cap) {
        Edge forward{to, static_cast<int>(graph[to].size()), cap};
        Edge reverse{from, static_cast<int>(graph[from].size()), 0};
        graph[from].push_back(forward);
        graph[to].push_back(reverse);
    }

    bool buildLevels(int source, int sink) {
        fill(level.begin(), level.end(), -1);
        queue<int> q;
        level[source] = 0;
        q.push(source);

        while (!q.empty()) {
            int current = q.front();
            q.pop();
            for (const Edge& edge : graph[current]) {
                if (edge.cap > 0 && level[edge.to] == -1) {
                    level[edge.to] = level[current] + 1;
                    q.push(edge.to);
                }
            }
        }
        return level[sink] != -1;
    }

    int sendFlow(int current, int sink, int pushed) {
        if (current == sink || pushed == 0) return pushed;

        for (int& i = nextEdge[current]; i < static_cast<int>(graph[current].size()); ++i) {
            Edge& edge = graph[current][i];
            if (edge.cap == 0 || level[edge.to] != level[current] + 1) continue;

            int sent = sendFlow(edge.to, sink, min(pushed, edge.cap));
            if (sent == 0) continue;

            edge.cap -= sent;
            graph[edge.to][edge.rev].cap += sent;
            return sent;
        }
        return 0;
    }

    int maxFlow(int source, int sink) {
        int total = 0;
        while (buildLevels(source, sink)) {
            fill(nextEdge.begin(), nextEdge.end(), 0);
            while (int sent = sendFlow(source, sink, 1'000'000'000)) {
                total += sent;
            }
        }
        return total;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, k;
    cin >> n >> m >> k;

    const int source = 0;
    const int bonus = n + 1;
    const int firstJob = n + 2;
    const int sink = n + m + 2;
    Dinic dinic(sink + 1);

    for (int worker = 1; worker <= n; ++worker) {
        dinic.addEdge(source, worker, 1);
    }
    dinic.addEdge(source, bonus, k);
    for (int worker = 1; worker <= n; ++worker) {
        dinic.addEdge(bonus, worker, 1);
    }

    for (int worker = 1; worker <= n; ++worker) {
        int count;
        cin >> count;
        while (count--) {
            int job;
            cin >> job;
            dinic.addEdge(worker, firstJob + job - 1, 1);
        }
    }
    for (int job = 0; job < m; ++job) {
        dinic.addEdge(firstJob + job, sink, 1);
    }

    cout << dinic.maxFlow(source, sink) << '\n';
    return 0;
}
```
