---
title: BOJ 14500 — テトロミノ
author: MINJUN PARK
date: 2022-02-24 21:48:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, DFS, 実装, テトロミノ]
pin: false
lang: ja
translation_key: boj-14500-tetromino
permalink: /ja/posts/boj-14500-tetromino/
source_permalink: /posts/BOJ-14500/
---

[問題: BOJ 14500 — テトロミノ](https://www.acmicpc.net/problem/14500) · [English](/posts/BOJ-14500/) · [한국어](/ko/posts/boj-14500-tetromino/)

`N × M` の盤面で、辺を共有してつながる4マスを覆うテトロミノを置きます。覆ったマスの値の合計の最大値を求めます。5種類のテトロミノについて、すべての回転・反転を考慮します。

隣接するマスを1つずつ追加する単純パスの深さ優先探索では、棒・L・S・Z型は見つけられますが、T型は作れません。T型の分岐点では、1本のパスをたどるだけではなく枝が必要になるためです。そこで長さ4の単純パスをすべて探索し、各マスを中心とするT型の4方向を別に確認します。マスの値を読む前に盤面内か確認するため、幅の狭い盤面や端の配置も正しく扱えます。

各マスからの深さ4の探索は分岐数が定数で、T型の確認も4方向だけです。そのため、形の数による定数倍を含む時間計算量は `O(NM)` です。マスの値と合計には `long long` を使い、加算時のオーバーフローを避けます。

{% raw %}
```cpp
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int n, m;
vector<vector<long long>> board;
vector<vector<bool>> visited;
long long answer = 0;

const int dr[4] = {-1, 1, 0, 0};
const int dc[4] = {0, 0, -1, 1};

void dfs(int r, int c, int count, long long sum) {
    if (count == 4) {
        answer = max(answer, sum);
        return;
    }

    for (int d = 0; d < 4; ++d) {
        int nr = r + dr[d];
        int nc = c + dc[d];
        if (nr < 0 || nr >= n || nc < 0 || nc >= m || visited[nr][nc]) continue;

        visited[nr][nc] = true;
        dfs(nr, nc, count + 1, sum + board[nr][nc]);
        visited[nr][nc] = false;
    }
}

void checkT(int r, int c) {
    const int shapes[4][3][2] = {
        {{0, -1}, {0, 1}, {-1, 0}},
        {{0, -1}, {0, 1}, {1, 0}},
        {{-1, 0}, {1, 0}, {0, -1}},
        {{-1, 0}, {1, 0}, {0, 1}}
    };

    for (const auto& shape : shapes) {
        long long sum = board[r][c];
        bool valid = true;
        for (const auto& offset : shape) {
            int nr = r + offset[0];
            int nc = c + offset[1];
            if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                valid = false;
                break;
            }
            sum += board[nr][nc];
        }
        if (valid) answer = max(answer, sum);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> m;
    board.assign(n, vector<long long>(m));
    visited.assign(n, vector<bool>(m, false));
    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) cin >> board[r][c];
    }

    for (int r = 0; r < n; ++r) {
        for (int c = 0; c < m; ++c) {
            visited[r][c] = true;
            dfs(r, c, 1, board[r][c]);
            visited[r][c] = false;
            checkT(r, c);
        }
    }

    cout << answer << '\n';
    return 0;
}
```
{% endraw %}
