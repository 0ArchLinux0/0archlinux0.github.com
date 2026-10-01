---
title: Programmers. ネットワーク
author: MINJUN PARK
date: 2021-12-31 00:30:00 +0900
categories: [Record, Code]
tags: [Algorithm, JavaScript, Coding Interview, Programmers, Network, 네트워크, 프로그래머스]
pin: false
lang: ja
translation_key: programmers-network-components
permalink: /ja/posts/programmers-network-components/
source_permalink: /posts/Programmers-Network/
---

[問題リンク](https://programmers.co.kr/learn/courses/30/lessons/43162)

## 解法

コンピューターを頂点、コンピューター間の直接接続を辺と考えると、無向グラフとして表せます。`computers[i][j] === 1` は、コンピューター `i` と `j` が直接接続されていることを示します。求めるネットワーク数は、このグラフの連結成分数です。

すべてのコンピューターを順に調べ、未訪問のものを見つけるたびにネットワーク数を 1 増やします。そのコンピューターから到達できる頂点は、明示的なスタックを使ってすべて訪問します。スタックに入れる時点で訪問済みにするため、同じコンピューターが重複してスタックに入りません。対角要素はコンピューター自身への接続を表しますが、現在のコンピューターはすでに訪問済みなので、自己ループによって別の頂点を訪問することはなく、結果に影響しません。

各連結成分で最初に見つかった未訪問頂点だけが探索を開始するため、成分ごとにネットワーク数をちょうど一度だけ増やします。辺をたどる探索によって開始点とつながった頂点はすべて訪問され、探索後はその成分内のどの頂点も再び探索を開始しません。したがって、成分を取りこぼしたり二重に数えたりすることはありません。

隣接行列の各行を調べるので、時間計算量は `O(n²)` です。各コンピューターは高々一度だけスタックに入るため、訪問配列とスタックの追加領域は `O(n)` です。

## JavaScript

```javascript
function solution(n, computers) {
  const visited = new Array(n).fill(false);
  const stack = [];
  let networkCount = 0;

  for (let start = 0; start < n; start++) {
    if (visited[start]) continue;

    networkCount++;
    visited[start] = true;
    stack.push(start);

    while (stack.length > 0) {
      const current = stack.pop();

      for (let next = 0; next < n; next++) {
        if (computers[current][next] === 1 && !visited[next]) {
          visited[next] = true;
          stack.push(next);
        }
      }
    }
  }

  return networkCount;
}
```
