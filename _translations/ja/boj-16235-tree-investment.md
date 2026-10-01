---
title: BOJ 16235 — 木の投資
author: MINJUN PARK
date: 2022-03-04 00:29:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, シミュレーション, 木の投資]
pin: false
lang: ja
translation_key: boj-16235-tree-investment
permalink: /ja/posts/boj-16235-tree-investment/
source_permalink: /posts/BOJ-16235/
---

[問題: BOJ 16235 — 木の投資](https://www.acmicpc.net/problem/16235) · [한국어](/ko/posts/boj-16235-tree-investment/) · [English](/posts/BOJ-16235/)

毎年、春、夏、秋、冬の順に処理します。春には各マスの木を若い順に処理します。木は年齢と同じ量の栄養を消費してから、年齢が1増えます。栄養が足りなくなると、その木と同じマスでまだ処理していないより年上の木はすべて枯れるため、そのマスの処理を止めます。夏には枯れた木ごとに、年齢の `floor(年齢 / 2)` に相当する栄養が戻ります。秋には年齢が5の倍数である木が、農場内の隣接する8マスに年齢1の木を1本ずつ増やします。最後に冬には、各マスに決められた量の栄養を加えます。

各マスの木の年齢を昇順に保てば、春は先頭から処理できます。栄養が不足した後は、その木と後ろに残った木がすべて枯れるため、後ろから年齢を読み取り、夏に戻す栄養を合計します。これにより途中要素の削除を避けながら、枯れる木を正しく扱えます。以下のコードでは各マスに年齢順の `vector` を使い、春に生き残った木の数までサイズを縮めて枯れた木を除きます。秋の繁殖数は別の配列に集計してから追加するため、生まれた木が同じ秋に再び繁殖することはありません。年齢1の木を先頭に挿入し、次の春も順序を保ちます。

制約は `N <= 10`, `K <= 1000` です。各年には木のないマスも含めて `O(N^2)` の走査を行い、木の処理量は木の本数に比例します。秋の繁殖ではマスごとに本数を数え、`vector` へ一括挿入します。繁殖する木1本あたり最大8本の新しい木が生まれるため、挿入も含めた全体の時間計算量は `O(N^2K + Σ_y T_y)` です。`T_y` はy年の開始時点の木の本数です。空間計算量は `O(N^2 + T_max)` で、木の本数が急増し得るため、`N`, `M`, `K` だけで固定上限を示すことはできません。

## C++17

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, years;
    cin >> n >> m >> years;

    vector<vector<int>> winterFood(n, vector<int>(n));
    for (auto& row : winterFood) {
        for (int& food : row) cin >> food;
    }

    vector<vector<int>> food(n, vector<int>(n, 5));
    vector<vector<vector<int>>> trees(n, vector<vector<int>>(n));
    for (int i = 0; i < m; ++i) {
        int row, column, age;
        cin >> row >> column >> age;
        trees[--row][--column].push_back(age);
    }
    for (auto& row : trees) {
        for (auto& cell : row) sort(cell.begin(), cell.end());
    }

    constexpr int dr[8] = {-1, -1, -1, 0, 0, 1, 1, 1};
    constexpr int dc[8] = {-1, 0, 1, -1, 1, -1, 0, 1};
    vector<vector<int>> deadFood(n, vector<int>(n));

    for (int year = 0; year < years; ++year) {
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                auto& cell = trees[r][c];
                int alive = 0;
                deadFood[r][c] = 0;
                while (alive < static_cast<int>(cell.size()) &&
                       food[r][c] >= cell[alive]) {
                    food[r][c] -= cell[alive];
                    ++cell[alive];
                    ++alive;
                }
                for (int i = static_cast<int>(cell.size()) - 1; i >= alive; --i) {
                    deadFood[r][c] += cell[i] / 2;
                }
                cell.resize(alive);
            }
        }

        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) food[r][c] += deadFood[r][c];
        }

        vector<vector<int>> offspring(n, vector<int>(n));
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                for (int age : trees[r][c]) {
                    if (age % 5 != 0) continue;
                    for (int d = 0; d < 8; ++d) {
                        const int nr = r + dr[d];
                        const int nc = c + dc[d];
                        if (0 <= nr && nr < n && 0 <= nc && nc < n) {
                            ++offspring[nr][nc];
                        }
                    }
                }
            }
        }
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                trees[r][c].insert(trees[r][c].begin(), offspring[r][c], 1);
                food[r][c] += winterFood[r][c];
            }
        }
    }

    int answer = 0;
    for (const auto& row : trees) {
        for (const auto& cell : row) answer += cell.size();
    }
    cout << answer << '\n';
    return 0;
}
```
