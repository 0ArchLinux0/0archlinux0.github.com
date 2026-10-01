---
title: BOJ 16234 — 人口移動
author: MINJUN PARK
date: 2022-02-25 19:38:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, BFS, 実装, 人口移動]
pin: false
lang: ja
translation_key: boj-16234-population-movement
permalink: /ja/posts/boj-16234-population-movement/
source_permalink: /posts/BOJ-16234/
---

[問題: BOJ 16234 — 人口移動](https://www.acmicpc.net/problem/16234) · [English](/posts/BOJ-16234/) · [한국어](/ko/posts/boj-16234-population-movement/)

1 日の間、隣り合う 2 つの国の人口差が `L` 以上 `R` 以下なら国境を開きます。開いた国境を通じてつながった国々が連合となり、2 か国以上からなる各連合では、連合の平均人口の小数点以下を切り捨てた値を全ての国に適用します。その日の連合はすべて、人口を更新する前の盤面をもとに判定してから一斉に更新します。国境が一つも開かない日にシミュレーションを終了し、連合が一つ以上できた日数を答えます。

国境を開く条件が `L <= |A - B| <= R` なのは、人口差が `L` 未満でも `R` より大きくてもいけないためです。両端の値も条件に含まれます。未訪問の国から BFS を行い、開いた国境を通って到達できる国をすべて集めると、一つの連合が得られます。すべての連結成分を見つけた後、2 か国以上からなる各連合の平均を計算して、その国々に適用します。1 か国だけの成分は変更しません。2 か国以上の連合が一つでもあるときだけ日数を増やします。シミュレーションを始める前に `N`、`L`、`R` と人口の盤面全体を入力しておきます。

国は `N^2` 個あり、1 日ごとに各国と隣接する国境を定数回調べるため、1 日の時間計算量は `O(N^2)` です。人口の盤面、訪問済みの記録、キュー、連結成分の一覧を合わせた空間計算量は `O(N^2)` です。

## C++17

```cpp
#include <cstdlib>
#include <iostream>
#include <queue>
#include <utility>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, lower, upper;
    cin >> n >> lower >> upper;

    vector<vector<int>> population(n, vector<int>(n));
    for (auto& row : population) {
        for (int& value : row) cin >> value;
    }

    const int dr[4] = {-1, 1, 0, 0};
    const int dc[4] = {0, 0, -1, 1};
    int days = 0;

    while (true) {
        vector<vector<bool>> visited(n, vector<bool>(n, false));
        vector<vector<pair<int, int>>> unions;

        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col) {
                if (visited[row][col]) continue;

                queue<pair<int, int>> pending;
                vector<pair<int, int>> component;
                pending.push({row, col});
                visited[row][col] = true;

                while (!pending.empty()) {
                    const auto [currentRow, currentCol] = pending.front();
                    pending.pop();
                    component.push_back({currentRow, currentCol});

                    for (int direction = 0; direction < 4; ++direction) {
                        const int nextRow = currentRow + dr[direction];
                        const int nextCol = currentCol + dc[direction];
                        if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n) continue;
                        if (visited[nextRow][nextCol]) continue;

                        const int difference = abs(population[currentRow][currentCol] - population[nextRow][nextCol]);
                        if (lower <= difference && difference <= upper) {
                            visited[nextRow][nextCol] = true;
                            pending.push({nextRow, nextCol});
                        }
                    }
                }

                if (component.size() > 1) unions.push_back(move(component));
            }
        }

        if (unions.empty()) break;

        for (const auto& component : unions) {
            int total = 0;
            for (const auto& [row, col] : component) total += population[row][col];
            const int average = total / static_cast<int>(component.size());
            for (const auto& [row, col] : component) population[row][col] = average;
        }
        ++days;
    }

    cout << days << '\n';
    return 0;
}
```
