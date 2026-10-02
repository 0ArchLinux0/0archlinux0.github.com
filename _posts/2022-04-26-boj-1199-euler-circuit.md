---
title: BOJ 1199 — Euler Circuit
author: MINJUN PARK
date: 2022-04-26 17:26:02 +0900
categories: [PS, baekjoon]
tags: [PS, Algorithm, BOJ, Graph theory, Euler Circuit, Hierholzer's Algorithm]
pin: false
lang: en
translation_key: boj-1199-euler-circuit
permalink: /posts/boj-1199-euler-circuit/
---

[BOJ 1199 — Euler Circuit](https://www.acmicpc.net/problem/1199)

## Model the input as a multigraph

The adjacency matrix gives the number of undirected edges between each pair of vertices. Parallel edges are distinct and must each appear in the circuit. A diagonal entry represents loops; each loop contributes two to its vertex's degree but is traversed once.

An undirected graph has an Euler circuit only if every vertex has even degree and all vertices incident to an edge belong to one connected component. Isolated vertices do not prevent a circuit. The source solution checked only degree parity, so it could print a partial circuit for two disconnected even-degree components.

## Iterative Hierholzer traversal

Start at any vertex with an incident edge. Repeatedly consume one remaining edge and push its other endpoint onto a stack. When the top vertex has no unused edges, append it to the circuit and backtrack. Reversing that list gives an Euler circuit if every edge was reached.

The implementation stores multiplicities in a flat matrix. It does not recurse, and it verifies connectivity implicitly by checking that the resulting circuit contains exactly $E+1$ vertices. This ignores isolated vertices while rejecting disconnected components that still contain edges.

If the graph has no edges, the zero-edge circuit is reported as vertex 1.

## C++17

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

The running time is $O(N^2+E)$: the matrix is scanned once, each edge copy is consumed once, and each row pointer advances at most $N$ times. The matrix uses $O(N^2)$ space; the traversal stack and circuit use $O(E)$ space.

## Source history

Adapted from [“백준 1199번 - 오일러 회로”](https://ilikechicken.tistory.com/48), originally published on 2022-04-26 by MINJUN PARK and marked CC BY 4.0. This version preserves the multigraph/Hierholzer approach, adds the missing connectivity condition, and replaces recursive traversal with an iterative implementation.

## References

- [BOJ 1199 — Euler Circuit](https://www.acmicpc.net/problem/1199)
- [Eulerian path and circuit](https://cp-algorithms.com/graph/euler_path.html)