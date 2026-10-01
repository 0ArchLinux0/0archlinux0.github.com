---
title: BOJ 11378 - 熱血江湖 4
author: MINJUN PARK
date: 2022-03-09 18:46:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, ネットワークフロー, 最大フロー, 熱血江湖4]
pin: false
lang: ja
translation_key: boj-11378-extra-jobs
permalink: /ja/posts/boj-11378-extra-jobs/
---

[問題ページ](https://www.acmicpc.net/problem/11378)

## 問題モデル

`N`人の社員と`M`件の仕事があり、各社員が担当できる仕事の一覧が与えられる。各社員は通常最大1件を担当でき、追加割り当ての総数は`K`以下である。追加割り当ては社員1人につき最大1件なので、各社員の担当数は最大2件となる。各仕事は高々1人にだけ割り当てる。この条件のもとで、割り当てる仕事数を最大化する。

## 最大フローモデル

頂点はソース、`N`人の社員、ボーナス頂点、`M`件の仕事、シンクで構成する。辺を次のように張る。

- ソース → 社員: 容量1（各社員の通常割り当て）
- ソース → ボーナス頂点: 容量`K`（追加割り当ての総上限）
- ボーナス頂点 → 社員: 容量1（社員ごとの追加割り当ては最大1件）
- 社員 → 担当可能な仕事: 容量1
- 仕事 → シンク: 容量1（各仕事は最大1回だけ割り当て）

各社員に入るフローは通常辺とボーナス辺からそれぞれ最大1なので、合計で最大2である。社員に入るボーナスフローも最大1で、ボーナス頂点へ入るフローは`K`以下に制限される。したがって、社員が担当できる仕事は最大2件、追加割り当ては全体で`K`件以下となる。ボーナス経路だけからフローを受け取った社員がいても、その割り当ては1件に限られる。有効な割り当ては、各社員の1件目を通常辺、2件目をボーナス経路に通すことでフローとして表せる。逆に、整数フローの社員-仕事辺は各仕事を重複なく割り当て、上記の容量制約を満たす。よって最大フロー値は割り当て可能な仕事数の最大値に等しい。

## アルゴリズムと計算量

Dinic法では、残余グラフ上でBFSによりレベルグラフを作り、レベルに沿ってブロッキングフローを送る。これ以上増加できなくなったときのフローが最大フローである。ここで`V = N + M + 3`、`E = A + 2N + M + 1`とし、`A`は社員-仕事の適格辺数とする。一般のフローネットワークに対するDinic法の時間計算量は`O(V^2 E)`、空間計算量は`O(V + E)`である。問題の制約は`N, M ≤ 1,000`、`K ≤ N`である。この実装は問題固有の、より高速な最悪計算量を主張しない。

## C++17実装

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
