---
title: Floyd-Warshall Algorithm - 플로이드 워셜 알고리즘
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
    shortest path,
    최단 비용,
  ]
pin: true
---

**\# Expression**

$ \\forall u,v \\in G: \\ \\ cost(u,v)$

그래프 G에 대해서 모든 정점간의 최소 비용을 계산할때 쓰이며 음의 가중치 간선이 있어도 동작한다.

이러한 최소 비용 문제를 일반적으로 Shortest Path Problem 으로 분류한다.

**\# Algorithm**
pseudo code

```c++
let cost[V+1][V+1] // cost[i][j]: cost of the shortest path from i to j
//init
for(i: 0~V)
  for(j: 0~V)
  	if(i == j) cost[i][j] = 0
  	else cost[i][j] = w[i][j] ? w[i][j] : INF

//relaxation
for(mid: 0~V)
  for(start: 0~V)
  	for(end: 0~V)
      if(cost[start][mid] + cost[mid][end] < cost[start][end])
      	cost[start][end] = cost[start][mid] + cost[mid][end]
```

---

**\# Proof of correctness**

**_Definition)_**

_$path\\\_vertices(path)$_

$path\\\_vertices(path) $ $= \\left\\{ v \\ \\mid v \\in G ,\\ v \\ was \\ included \\ in \\ the \\ path \\right\\}$

경로 path상에서 방문한 모든 정점들의 집합

_$shortest\\\_path(i, j, k) $ :_

$ path\\\_vertices(shortest\\\_path(i, j, k))$ $ \\subset \\left\\{ x \\mid 1 \\leq x \\leq k \\right\\} $

i에서 j로 가는 경로중, 경로상의 모든 점이 {1, 2, ... k }에 포함되며 최소 비용인 경로(또는 그 값)

$w\_{i,j}$ : i에서 j로 가는 간선의 가중치(비용)

**_Proof)_**

$shortest\_path(i, j, k)$에 대하여 생각해보면 두가지 경우가 존재 할 수 있다.

1\. $k \\in $ $path\\\_vertices(shortest\\\_path(i, j, k)) $ (k를 포함하는 경우)

$shortest\\\_path(i, j, k) $ $= shortest\\\_path(i, k - 1, k) + $ $shortest\\\_path(k, j, k - 1)$

2\. $k \\not\\in $ $path\\\_vertices(shortest\\\_path(i, j, k))$ (k를 포함하지 않는 경우)

$shortest\\\_path(i, j, k) $ $= shortest\\\_path(i, j, k-1)$

$ \\therefore shortest\\\_path(i, j, k) $ $= min(shortest\\\_path(i, k - 1, k) $ $+ shortest\\\_path(k, j, k - 1),$ $ shortest\\\_path(i, j, k-1)$

로 나타낼 수 있다.

---

후술할 내용들

#floyds-cycle-detection-algorithm

#Path reconstruction
