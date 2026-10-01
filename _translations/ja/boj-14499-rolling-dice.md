---
title: BOJ 14499 - サイコロを転がす
author: MINJUN PARK
date: 2022-02-24 14:22:00 +0900
categories: [Record, Code]
tags: [C++, アルゴリズム, BOJ, 実装, サイコロを転がす]
pin: false
lang: ja
translation_key: boj-14499-rolling-dice
permalink: /ja/posts/boj-14499-rolling-dice/
source_permalink: /posts/BOJ-14499/
---

[問題: BOJ 14499 — サイコロを転がす](https://www.acmicpc.net/problem/14499) · [한국어](/ko/posts/boj-14499-rolling-dice/) · [English](/posts/BOJ-14499/)

サイコロの6面の値を、固定方向の配列 `TOP`、`BOTTOM`、`NORTH`、`SOUTH`、`EAST`、`WEST` に保持します。不変条件は、各配列要素が常にその方向を向いている面の値を表すことです。転がすたびに回転軸まわりの4面だけが入れ替わり、残りの2面はそのままです。

東へ転がすと、元の西面が上面になり、元の上面が東面、元の東面が下面、元の下面が西面になります。西への回転はこの循環を逆にしたものです。北へ転がすと、元の南面が上面になり、元の上面が北面、元の北面が下面、元の下面が南面になります。南への回転はこの循環を逆にしたものです。代入で値が上書きされる前に1面分を一時変数へ保存すれば、それぞれの置換を定数時間で行えます。

各コマンドを実行する前に、隣接マスが盤面内かどうかを確認します。盤面外へ出るコマンドは完全に無視します。位置を移動せず、サイコロも回転せず、マスの読み書きも出力もしません。有効な移動ではサイコロを転がし、移動先のマスと下面の値を同期します。マスが0以外なら、その値を下面へコピーしてマスを0にします。マスが0なら、下面の値をマスへコピーします。その後、上面の値を出力します。

各コマンドを定数時間で処理するため、全体の時間計算量は `O(K)` です。盤面に `O(NM)` の領域を使い、サイコロの6面には追加で `O(1)` の領域を使います。

## C++17

```cpp
#include <iostream>

using namespace std;

enum Face { TOP, BOTTOM, NORTH, SOUTH, EAST, WEST };
enum Direction { ROLL_EAST = 1, ROLL_WEST, ROLL_NORTH, ROLL_SOUTH };

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, row, col, k;
    cin >> n >> m >> row >> col >> k;

    int board[20][20] = {};
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            cin >> board[i][j];
        }
    }

    int die[6] = {};
    for (int i = 0; i < k; ++i) {
        int command;
        cin >> command;

        int next_row = row;
        int next_col = col;
        if (command == ROLL_EAST) {
            ++next_col;
        } else if (command == ROLL_WEST) {
            --next_col;
        } else if (command == ROLL_NORTH) {
            --next_row;
        } else {
            ++next_row;
        }

        if (next_row < 0 || next_row >= n || next_col < 0 || next_col >= m) {
            continue;
        }

        if (command == ROLL_EAST) {
            const int old_west = die[WEST];
            die[WEST] = die[BOTTOM];
            die[BOTTOM] = die[EAST];
            die[EAST] = die[TOP];
            die[TOP] = old_west;
        } else if (command == ROLL_WEST) {
            const int old_east = die[EAST];
            die[EAST] = die[BOTTOM];
            die[BOTTOM] = die[WEST];
            die[WEST] = die[TOP];
            die[TOP] = old_east;
        } else if (command == ROLL_NORTH) {
            const int old_south = die[SOUTH];
            die[SOUTH] = die[BOTTOM];
            die[BOTTOM] = die[NORTH];
            die[NORTH] = die[TOP];
            die[TOP] = old_south;
        } else {
            const int old_north = die[NORTH];
            die[NORTH] = die[BOTTOM];
            die[BOTTOM] = die[SOUTH];
            die[SOUTH] = die[TOP];
            die[TOP] = old_north;
        }

        row = next_row;
        col = next_col;
        if (board[row][col] != 0) {
            die[BOTTOM] = board[row][col];
            board[row][col] = 0;
        } else {
            board[row][col] = die[BOTTOM];
        }

        cout << die[TOP] << '\n';
    }
}
```
