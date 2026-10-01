---
title: BOJ 11437 - 최소 공통 조상
author: MINJUN PARK
date: 2022-02-28 20:29:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 트리, 최소 공통 조상, LCA]
pin: false
lang: ko
translation_key: boj-11437-lowest-common-ancestor
permalink: /ko/posts/boj-11437-lowest-common-ancestor/
source_permalink: /posts/BOJ-11437/
---

[문제: BOJ 11437 — 최소 공통 조상](https://www.acmicpc.net/problem/11437) · [English](/posts/BOJ-11437/) · [日本語](/ja/posts/boj-11437-lowest-common-ancestor/)

트리의 루트를 정점 `1`로 잡습니다. 재귀가 아닌 너비 우선 탐색으로 각 정점의 깊이와 바로 위 부모를 기록합니다. 루트의 부모는 `0`으로 두며, 이 센티널 정점의 조상도 모두 `0`입니다. 입력이 트리이므로 루트가 아닌 각 정점은 부모로부터 정확히 한 번 방문됩니다. 재귀 DFS 대신 큐를 사용하므로 정점 50,000개가 일렬로 연결된 경우에도 호출 스택이 넘치지 않습니다.

`ancestor[v][j]`를 정점 `v`에서 위로 `2^j`개의 간선을 이동한 정점이라고 정의합니다. 부모 탐색으로 0단계를 채운 다음 `ancestor[v][j] = ancestor[ancestor[v][j - 1]][j - 1]` 점화식으로 더 높은 단계를 계산합니다. 센티널 덕분에 루트에서도 이 점화식을 그대로 적용할 수 있습니다.

질의에서는 깊은 정점을 깊이 차이만큼 먼저 올려 두 정점의 높이를 맞춥니다. 두 정점이 같아졌다면 그 정점이 LCA입니다. 그렇지 않으면 가장 큰 점프 단계부터 검사하면서, 해당 단계의 조상이 서로 다를 때 두 정점을 함께 올립니다. 이렇게 하면 두 정점은 최소 공통 조상 바로 아래에 남으므로, 둘의 바로 위 부모가 답입니다.

전처리 시간과 메모리는 각각 `O(N log N)`이고, 질의 하나의 시간은 `O(log N)`입니다.

## C++17

```cpp
#include <iostream>
#include <queue>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<vector<int>> graph(n + 1);
    for (int i = 0; i < n - 1; ++i) {
        int a, b;
        cin >> a >> b;
        graph[a].push_back(b);
        graph[b].push_back(a);
    }

    constexpr int LOG = 17;  // 2^16 >= 50,000
    vector<int> depth(n + 1, -1);
    vector<vector<int>> ancestor(LOG, vector<int>(n + 1, 0));

    queue<int> pending;
    depth[1] = 0;
    pending.push(1);

    while (!pending.empty()) {
        int node = pending.front();
        pending.pop();

        for (int next : graph[node]) {
            if (next == ancestor[0][node]) {
                continue;
            }
            ancestor[0][next] = node;
            depth[next] = depth[node] + 1;
            pending.push(next);
        }
    }

    for (int level = 1; level < LOG; ++level) {
        for (int node = 1; node <= n; ++node) {
            ancestor[level][node] =
                ancestor[level - 1][ancestor[level - 1][node]];
        }
    }

    auto lca = [&](int a, int b) {
        if (depth[a] < depth[b]) {
            swap(a, b);
        }

        int difference = depth[a] - depth[b];
        for (int level = 0; level < LOG; ++level) {
            if (difference & (1 << level)) {
                a = ancestor[level][a];
            }
        }

        if (a == b) {
            return a;
        }

        for (int level = LOG - 1; level >= 0; --level) {
            if (ancestor[level][a] != ancestor[level][b]) {
                a = ancestor[level][a];
                b = ancestor[level][b];
            }
        }
        return ancestor[0][a];
    };

    int queries;
    cin >> queries;
    while (queries--) {
        int a, b;
        cin >> a >> b;
        cout << lca(a, b) << '\n';
    }
}
```
