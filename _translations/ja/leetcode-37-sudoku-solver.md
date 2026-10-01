---
title: LeetCode. 37. Sudoku Solver
author: MINJUN PARK
date: 2021-12-26 08:40:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Sudoku Solver]
pin: false
lang: ja
translation_key: leetcode-37-sudoku-solver
permalink: /ja/posts/leetcode-37-sudoku-solver/
---

![image](https://user-images.githubusercontent.com/55131164/147395541-744d69fb-dc7f-4c11-b5d5-5180248f6a3b.png)

有効な数独盤面には解が1つだけあります。各行・各列・3 × 3 のボックスに `1` から `9` までの数字がそれぞれ一度だけ現れるよう、空欄を盤面上で埋めます。

[問題へのリンク](https://leetcode.com/problems/sudoku-solver/)

## アプローチ

各行・各列・各ボックスについて、すでに使われている数字を9ビットのマスクで記録します。ビット `d` は数字 `d + 1` を表し、ビットが立っていればその数字は配置できません。したがって、空欄に置ける数字は、全ビットのマスクのうち対応する行・列・ボックスのいずれのマスクにも含まれていないビットです。

各再帰ステップで残りの空欄を調べ、候補数字が最も少ないマスを選びます（最小残余値、MRV）。制約の強いマスを先に選ぶことで、分岐数を早い段階で減らせます。候補がないマスがあれば、その探索経路は失敗です。そうでなければ候補を一つずつ試します。盤面に数字を書き込み、対応する3つのマスクのビットを立ててから再帰します。失敗した経路ではビットを消し、マスを `'.'` に戻します。成功した場合はすぐに返るため、完成した盤面が入力に残ります。

空欄は最大81個で、最悪の場合の探索時間は指数時間です（各空欄で最大9通り）。MRVにより矛盾を早く検出し、分岐を絞ることで実際の探索を効率化します。補助領域は再帰スタックと固定サイズのマスクを合わせて `O(81)` で、盤面はその場で更新します。

## Java

```java
class Solution {
    private static final int FULL = (1 << 9) - 1;

    private char[][] board;
    private final int[] rows = new int[9];
    private final int[] columns = new int[9];
    private final int[] boxes = new int[9];

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            rows[i] = 0;
            columns[i] = 0;
            boxes[i] = 0;
        }
        this.board = board;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                char cell = board[row][column];
                if (cell != '.') {
                    int bit = 1 << (cell - '1');
                    int box = (row / 3) * 3 + column / 3;
                    rows[row] |= bit;
                    columns[column] |= bit;
                    boxes[box] |= bit;
                }
            }
        }
        solve();
    }

    private boolean solve() {
        int bestRow = -1;
        int bestColumn = -1;
        int bestCandidates = 0;
        int fewestChoices = 10;

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] != '.') {
                    continue;
                }

                int box = (row / 3) * 3 + column / 3;
                int candidates = FULL & ~(rows[row] | columns[column] | boxes[box]);
                int choices = Integer.bitCount(candidates);
                if (choices < fewestChoices) {
                    bestRow = row;
                    bestColumn = column;
                    bestCandidates = candidates;
                    fewestChoices = choices;
                    if (choices <= 1) {
                        break;
                    }
                }
            }
            if (fewestChoices <= 1) {
                break;
            }
        }

        if (bestRow == -1) {
            return true;
        }
        if (bestCandidates == 0) {
            return false;
        }

        int box = (bestRow / 3) * 3 + bestColumn / 3;
        while (bestCandidates != 0) {
            int bit = bestCandidates & -bestCandidates;
            bestCandidates -= bit;
            int digit = Integer.numberOfTrailingZeros(bit);

            board[bestRow][bestColumn] = (char) ('1' + digit);
            rows[bestRow] |= bit;
            columns[bestColumn] |= bit;
            boxes[box] |= bit;

            if (solve()) {
                return true;
            }

            rows[bestRow] &= ~bit;
            columns[bestColumn] &= ~bit;
            boxes[box] &= ~bit;
            board[bestRow][bestColumn] = '.';
        }
        return false;
    }
}
```
