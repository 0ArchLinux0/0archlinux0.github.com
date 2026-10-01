---
title: BOJ 16236 - 赤ちゃんザメ
author: MINJUN PARK
date: 2022-02-23 19:22:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 赤ちゃんザメ, BFS]
pin: false
lang: ja
translation_key: boj-16236-baby-shark
permalink: /ja/posts/boj-16236-baby-shark/
source_permalink: /posts/BOJ-16236/
---

[問題: BOJ 16236 — 赤ちゃんザメ](https://www.acmicpc.net/problem/16236) · [한국어](/ko/posts/boj-16236-baby-shark/) · [English](/posts/BOJ-16236/)

サメが現在いる位置から、食べられる魚を探すたびに BFS を行います。サメより大きな魚がいるマスには入れません。空きマスとサメ以下の大きさの魚がいるマスには入れます。食べられる魚はサメより厳密に小さい魚です。つまり、同じ大きさの魚は通過できますが、食べることはできません。

BFS は距離の小さいマスから順に訪問します。食べられる魚を見つけたら、同じ距離にある残りのマスだけを確認すれば、最短距離の候補をすべて比較できます。その中から行番号が最小の魚を選び、行も同じなら列番号が最小の魚を選びます。これで距離、上、左の優先順位をそのまま適用できます。魚を食べたらそのマスを空にし、移動距離を経過時間に加えて、その位置から新しい BFS を開始します。訪問情報は探索ごとに初期化します。

サメは現在の大きさと同じ数の魚を食べると大きさが 1 増え、食べた数は 0 に戻ります。BFS で食べられる魚が見つからなければシミュレーションを終了します。1 回の BFS は時間 `O(N^2)`、空間 `O(N^2)` です。食べた魚の数を `F` とすると、全体の時間計算量は `O(F N^2)` です。

## C++

```cpp
#include <algorithm>
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
  vector<vector<int>> grid(n, vector<int>(n));
  int sharkRow = 0;
  int sharkCol = 0;

  for (int r = 0; r < n; ++r) {
    for (int c = 0; c < n; ++c) {
      cin >> grid[r][c];
      if (grid[r][c] == 9) {
        sharkRow = r;
        sharkCol = c;
        grid[r][c] = 0;
      }
    }
  }

  const int dr[] = {-1, 0, 0, 1};
  const int dc[] = {0, -1, 1, 0};
  int sharkSize = 2;
  int eaten = 0;
  int elapsed = 0;

  while (true) {
    vector<vector<int>> distance(n, vector<int>(n, -1));
    queue<pair<int, int>> q;
    q.push({sharkRow, sharkCol});
    distance[sharkRow][sharkCol] = 0;

    int preyRow = -1;
    int preyCol = -1;
    int preyDistance = -1;

    while (!q.empty()) {
      const auto [r, c] = q.front();
      q.pop();
      const int d = distance[r][c];
      if (preyDistance != -1 && d > preyDistance) break;

      if (grid[r][c] > 0 && grid[r][c] < sharkSize) {
        if (preyRow == -1 || r < preyRow || (r == preyRow && c < preyCol)) {
          preyRow = r;
          preyCol = c;
          preyDistance = d;
        }
        continue;
      }

      for (int direction = 0; direction < 4; ++direction) {
        const int nr = r + dr[direction];
        const int nc = c + dc[direction];
        if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
        if (distance[nr][nc] != -1 || grid[nr][nc] > sharkSize) continue;
        distance[nr][nc] = d + 1;
        q.push({nr, nc});
      }
    }

    if (preyRow == -1) break;

    elapsed += preyDistance;
    sharkRow = preyRow;
    sharkCol = preyCol;
    grid[sharkRow][sharkCol] = 0;
    ++eaten;
    if (eaten == sharkSize) {
      ++sharkSize;
      eaten = 0;
    }
  }

  cout << elapsed;
}
```
