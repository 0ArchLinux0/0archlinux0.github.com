---
title: フロイド–ワーシャル法
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph theory, Flow network]
tags: [アルゴリズム, グラフ, フロイド–ワーシャル法, 最短経路]
lang: ja
translation_key: floyd-warshall-algorithm
permalink: /ja/posts/floyd-warshall-algorithm/
pin: false
---

# フロイド–ワーシャル法

フロイド–ワーシャル法は、重み付き有向グラフにおけるすべての順序付き頂点対の最短距離を求める。負の辺重みも扱えるが、対象の頂点対の経路に影響する負閉路がない場合に限り、最短距離は有限値になる。この動的計画法では、中継頂点の候補を決められた順に一つずつ許可していく。

## 動的計画法の漸化式

頂点に `0` から `V - 1` まで番号を付ける。`D^(k)[i][j]` を、中継頂点が `{0, 1, ..., k - 1}` の中に限られる、`i` から `j` への経路の最小重みとする。始点 `i` と終点 `j` はこの集合に制限されない。したがって `D^(0)` では中継頂点を許さず、対角成分は 0、各頂点対の成分は直接辺の最小重み、直接辺がなければ無限大となる。

中継頂点として `k` を許すと、最適経路は `k` を通らないか、`k` を通るかのいずれかである。後者は、それぞれ以前に許可された中継頂点だけを使う `i` から `k` への経路と `k` から `j` への経路をつないだものになる。よって `0 <= k < V` について、次の漸化式が成り立つ。

$$
D^{(k+1)}[i][j] = \min\left(D^{(k)}[i][j],\ D^{(k)}[i][k] + D^{(k)}[k][j]\right).
$$

すべての頂点を候補として検討した後の `D^(V)[i][j]` が、有限の最小値が存在する場合の最短距離である。

## C++ 実装

大きな `INF` センチネルを使い、すべての有限距離と計算されうる有限値の和が型の表現範囲内に収まるようにする。各要素は明示的に初期化する。重み 0 の辺も有効なので、真偽値で辺の有無を判定してはいけない。平行辺がある場合は、最も小さい重みを残す。

```cpp
#include <algorithm>
#include <cstdint>
#include <tuple>
#include <vector>

using Weight = std::int64_t;
constexpr Weight INF = (Weight{1} << 60);

// n 個の頂点。edges は (始点, 終点, 重み) の組の一覧。
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

漸化式の各段階を保つため、最外側のループは中継頂点 `k` のループにする。加算前に両方の値が `INF` でないことを確認することで、到達不能な部分経路を数値として扱ったり、センチネルを加算したりするのを防ぐ。

時間計算量は `O(V^3)`、空間計算量は `O(V^2)` である。すべての頂点対の距離が必要な場合に適しており、疎グラフで単一始点からの距離だけが必要なら、単一始点最短経路アルゴリズムのほうが適切な場合がある。

## 負閉路と結果の解釈

`i` から `j` への経路があり、`i` から到達可能で、その後 `j` にも到達できる負閉路が存在しない場合に限り、最短距離は有限値となる。そのような経路上の負閉路は何度でも通れるため、経路の重みをいくらでも小さくでき、有限の最小値は存在しない。グラフ内の別の場所にある負閉路でも、対象の頂点対からそこを経由できないなら、その対の最短距離には影響しない。

アルゴリズムの終了後、ある頂点 `v` について `dist[v][v] < 0` なら負閉路が検出される。これは一部の最短経路の答えが定義できない可能性を示すだけで、すべての要素が有限の最短距離であることを意味しない。頂点対 `(i, j)` が影響を受けるのは、`dist[i][v] < INF`、`dist[v][v] < 0`、`dist[v][j] < INF` をすべて満たす頂点 `v` がある場合である。そのような対について有限の最短距離を報告してはならない。
