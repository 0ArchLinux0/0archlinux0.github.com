---
title: Floyd-Warshall Algorithm
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph theory, Flow network]
tags:
  [
    Algorithm,
    Graph Threory,
    그래프,
    Floyd-Warshall,
    플로이드 워셜,
    flow network,
    네트워크 플로우,
    Shortest Path,
    최단 비용,
  ]
pin: false
lang: en
translation_key: floyd-warshall-algorithm
---

# Floyd–Warshall algorithm

The Floyd–Warshall algorithm computes shortest-path distances between every ordered pair of vertices in a weighted directed graph. It supports negative edge weights, provided the relevant paths are not affected by a negative-weight cycle. Its dynamic program considers possible intermediate vertices in a fixed order.

## Dynamic programming recurrence

Number the vertices from `0` to `V - 1`. Let `D^(k)[i][j]` be the minimum weight of a path from `i` to `j` whose intermediate vertices are drawn only from `{0, 1, ..., k - 1}`. The endpoints `i` and `j` are not restricted by this set. `D^(0)` therefore allows no intermediate vertices: it is zero on the diagonal, the minimum direct-edge weight for each pair, and infinity when no direct edge exists.

When vertex `k` becomes available as an intermediate, an optimal path either avoids `k`, or goes through `k`. In the latter case it consists of a path from `i` to `k` and one from `k` to `j`, each using only earlier intermediate vertices. Thus, for `0 <= k < V`:

$$
D^{(k+1)}[i][j] = \min\left(D^{(k)}[i][j],\ D^{(k)}[i][k] + D^{(k)}[k][j]\right).
$$

After all vertices have been considered, `D^(V)[i][j]` is the shortest-path distance when a finite minimum exists.

## C++ implementation

Use a large `INF` sentinel and ensure every finite distance and every finite sum that can be formed stays strictly within its range. Initialize all entries explicitly: a zero-weight edge is valid, so truthiness is not a safe way to distinguish an edge from a missing edge. For parallel edges, retain the lightest weight.

```cpp
#include <algorithm>
#include <cstdint>
#include <tuple>
#include <vector>

using Weight = std::int64_t;
constexpr Weight INF = (Weight{1} << 60);

// n vertices; edges contains (from, to, weight) triples.
std::vector<std::vector<Weight>> floydWarshall(
    int n, const std::vector<std::tuple<int, int, Weight>>& edges) {
  std::vector<std::vector<Weight>> dist(
      n, std::vector<Weight>(n, INF));

  for (int i = 0; i < n; ++i) dist[i][i] = 0;
  for (const auto& [from, to, weight] : edges) {
    dist[from][to] = std::min(dist[from][to], weight);
  }

  for (int k = 0; k < n; ++k) {
    for (int i = 0; i < n; ++i) {
      if (dist[i][k] == INF) continue;
      for (int j = 0; j < n; ++j) {
        if (dist[k][j] == INF) continue;
        dist[i][j] = std::min(dist[i][j], dist[i][k] + dist[k][j]);
      }
    }
  }
  return dist;
}
```

The outer loop must be the intermediate-vertex loop (`k`); this order enforces the recurrence's stages. The checks before addition avoid treating an unreachable subpath as a numeric distance (and avoid unsafe sentinel arithmetic). Include `<tuple>` as well when compiling this standalone snippet, since it uses `std::tuple` and structured bindings.

The algorithm takes `O(V^3)` time and `O(V^2)` space. It is useful when distances for many or all vertex pairs are needed; for a single source in a sparse graph, a single-source algorithm may be more appropriate.

## Negative cycles and interpreting results

A finite shortest distance from `i` to `j` exists exactly when there is a path from `i` to `j` and no negative cycle is reachable from `i` and can then reach `j`. A negative cycle on such a route can be traversed repeatedly to make the walk's total weight arbitrarily small, so there is no finite minimum for that pair. A negative cycle elsewhere in the graph does not invalidate pairs that cannot route through it.

After the algorithm, `dist[v][v] < 0` for some vertex `v` detects a negative cycle. This detection tells you that some shortest-path answers may be undefined; it does not make all entries valid finite distances. To identify affected pairs, a pair `(i, j)` is affected if some `v` has `dist[i][v] < INF`, `dist[v][v] < 0`, and `dist[v][j] < INF`. Do not report a finite shortest distance for those pairs.
