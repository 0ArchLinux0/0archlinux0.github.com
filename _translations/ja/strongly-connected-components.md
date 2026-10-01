---
title: グラフ理論. 強連結成分(SCC)
author: MINJUN PARK
date: 2021-11-26 9:00 +0900
categories: [Record, Code]
tags:
  [
    Graph theory,
    Algorithm,
    Strongly Connected Component,
    SCC,
    Tarjan's algorithm,
    Kosaraju's algorithm,
  ]
pin: false
lang: ja
translation_key: strongly-connected-components
permalink: /ja/posts/strongly-connected-components/
---

# 強連結成分

有向グラフにおける強連結成分（SCC）とは、すべての頂点が互いに到達可能である頂点の極大集合です。互いに到達可能であるとは、双方向に到達できることを意味します。同じ SCC に属する任意の 2 頂点 `u` と `v` について、`u` から `v` への有向パスと `v` から `u` への有向パスの両方が存在します。一方向にしかパスがない場合、両頂点は同じ成分には属しません。

## タージャンのアルゴリズム

タージャンのアルゴリズムは、1 回の深さ優先探索ですべての SCC を求めます。以下の関数は隣接リストを受け取ります。`graph[u]` には、頂点 `u` から出る辺が到達する 0 始まりの頂点インデックスが格納されています。戻り値は、各要素が 0 始まりの頂点インデックスの配列である配列です。成分と頂点の順序は問いません。

各頂点について、`index` は発見順を記録し、`lowLink` は DFS 木の辺をたどり、必要な場合は探索中のスタックに残っている頂点への辺を 1 本たどって到達できる最小の発見インデックスを記録します。子の探索が完了した後、木の辺では子の `lowLink` を引き継ぎます。探索中の頂点への後退辺では、その頂点の `lowLink` ではなく `index` を使います。すでにスタックから取り除かれた頂点への辺は無視します。このスタック上にあるかどうかの規則により、探索を終えた SCC が後続の SCC に誤って結合されるのを防ぎます。

`lowLink[v] === index[v]` の場合、`v` は SCC の根です。スタックから `v` が出るまで頂点を取り出すと、取り出された頂点が 1 つの成分を構成します。外側のループは未発見の各頂点から DFS を開始するため、グラフ内で互いに分離した部分もすべて処理されます。

```javascript
function stronglyConnectedComponents(graph) {
  const n = graph.length;
  const index = Array(n).fill(-1);
  const lowLink = Array(n).fill(0);
  const onStack = Array(n).fill(false);
  const stack = [];
  const components = [];
  let nextIndex = 0;

  function visit(v) {
    index[v] = nextIndex;
    lowLink[v] = nextIndex;
    nextIndex += 1;
    stack.push(v);
    onStack[v] = true;

    for (const w of graph[v]) {
      if (index[w] === -1) {
        visit(w);
        lowLink[v] = Math.min(lowLink[v], lowLink[w]);
      } else if (onStack[w]) {
        lowLink[v] = Math.min(lowLink[v], index[w]);
      }
    }

    if (lowLink[v] === index[v]) {
      const component = [];
      let w;
      do {
        w = stack.pop();
        onStack[w] = false;
        component.push(w);
      } while (w !== v);
      components.push(component);
    }
  }

  for (let v = 0; v < n; v += 1) {
    if (index[v] === -1) visit(v);
  }

  return components;
}
```

頂点数を `V`、有向辺の数を `E` とすると、時間計算量は `O(V + E)`、補助空間計算量は `O(V)` です。この実装は再帰を使うため、呼び出しの深さは `O(V)` になることがあります。非常に深いグラフでは JavaScript 実行環境のコールスタック上限を超える可能性があるため、反復型の実装が必要になる場合があります。
