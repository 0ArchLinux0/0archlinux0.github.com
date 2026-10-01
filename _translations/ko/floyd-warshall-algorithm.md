---
title: 플로이드-워셜 알고리즘
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph theory, Flow network]
tags: [알고리즘, 그래프, 플로이드-워셜, 최단 경로]
lang: ko
translation_key: floyd-warshall-algorithm
permalink: /ko/posts/floyd-warshall-algorithm/
pin: false
---

# 플로이드-워셜 알고리즘

플로이드-워셜 알고리즘은 가중치가 있는 방향 그래프의 모든 순서쌍 정점 사이 최단 거리를 계산한다. 음수 간선 가중치도 처리할 수 있지만, 해당 정점 쌍의 경로에 영향을 주는 음수 사이클이 없어야 유한한 최단 거리가 존재한다. 이 알고리즘은 정해진 순서로 중간 정점 후보를 하나씩 허용하는 동적 계획법이다.

## 동적 계획법 점화식

정점에 `0`부터 `V - 1`까지 번호를 붙이자. `D^(k)[i][j]`를 중간 정점이 `{0, 1, ..., k - 1}`에만 속하는 `i`에서 `j`까지의 경로 중 최소 가중치라고 정의한다. 끝점 `i`, `j`는 이 집합에 제한되지 않는다. 따라서 `D^(0)`은 중간 정점을 허용하지 않으며, 대각선 원소는 0, 각 정점 쌍의 원소는 직접 간선 중 최소 가중치이고 직접 간선이 없으면 무한대다.

중간 정점으로 `k`를 허용하면 최적 경로는 `k`를 지나지 않거나, `k`를 지난다. 후자의 경로는 더 앞서 허용된 중간 정점만 사용하는 `i`에서 `k`까지의 경로와 `k`에서 `j`까지의 경로를 이어 붙인 것이다. 그러므로 `0 <= k < V`에 대해 다음 점화식이 성립한다.

$$
D^{(k+1)}[i][j] = \min\left(D^{(k)}[i][j],\ D^{(k)}[i][k] + D^{(k)}[k][j]\right).
$$

모든 정점을 후보로 고려한 뒤의 `D^(V)[i][j]`가 유한한 최솟값이 존재할 때 그 최단 거리다.

## C++ 구현

큰 `INF` 센티널을 사용하고, 모든 유한 거리와 계산 가능한 유한한 합이 표현 범위 안에 들도록 해야 한다. 모든 원소를 명시적으로 초기화한다. 가중치 0인 간선도 유효하므로 참/거짓 여부로 간선 존재를 판별하면 안 된다. 평행 간선이 있다면 가중치가 가장 작은 간선을 남긴다.

```cpp
#include <algorithm>
#include <cstdint>
#include <tuple>
#include <vector>

using Weight = std::int64_t;
constexpr Weight INF = (Weight{1} << 60);

// n개의 정점. edges는 (시작점, 도착점, 가중치) 튜플의 목록이다.
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

가장 바깥 반복문은 반드시 중간 정점 `k`에 대한 반복문이어야 점화식의 단계를 보존한다. 덧셈 전 두 값이 `INF`인지 검사하므로 도달할 수 없는 경로를 수로 취급하거나 센티널을 더하는 일을 막는다.

시간 복잡도는 `O(V^3)`, 공간 복잡도는 `O(V^2)`다. 모든 정점 쌍의 거리가 필요할 때 적합하며, 희소 그래프에서 한 출발점만 조사한다면 단일 출발점 최단 경로 알고리즘이 더 적절할 수 있다.

## 음수 사이클과 결과 해석

`i`에서 `j`로 가는 경로가 있고, `i`에서 도달 가능하며 그 뒤 `j`에도 도달할 수 있는 음수 사이클이 없을 때에만 유한한 최단 거리가 존재한다. 그런 경로 위의 음수 사이클은 반복해서 돌 수 있어 경로 비용을 한없이 작게 만들므로 유한한 최솟값이 없다. 그래프의 다른 곳에 있는 음수 사이클이라도 해당 정점 쌍의 경로로 연결되지 않는다면 그 쌍의 최단 거리에 영향을 주지 않는다.

알고리즘이 끝난 뒤 어떤 정점 `v`에 대해 `dist[v][v] < 0`이면 음수 사이클이 존재한다. 이는 일부 최단 경로 답이 정의되지 않을 수 있다는 뜻이지, 모든 원소가 유한한 최단 거리라는 뜻은 아니다. 영향을 받는 쌍 `(i, j)`는 `dist[i][v] < INF`, `dist[v][v] < 0`, `dist[v][j] < INF`를 모두 만족하는 정점 `v`가 있을 때다. 그런 쌍에 유한한 최단 거리를 보고해서는 안 된다.
