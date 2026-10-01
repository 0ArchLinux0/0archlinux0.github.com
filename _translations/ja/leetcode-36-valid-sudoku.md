---
title: LeetCode 36. 有効な数独
author: MINJUN PARK
date: 2021-12-24 06:58:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Valid Sudoku]
pin: false
lang: ja
translation_key: leetcode-36-valid-sudoku
permalink: /ja/posts/leetcode-36-valid-sudoku/
---

[問題へのリンク](https://leetcode.com/problems/valid-sudoku/)

9×9の数独盤面が有効かどうかを判定する。確認するのは埋まっているマスだけでよい。空マス（`.`）は無視し、現在の途中状態から数独を完成できるかどうかまでは判定しない。

各行・各列・各3×3ボックスで使用済みの数字を、それぞれ9個の整数ビットマスクに記録する。数字 `d` は `1 << (d - '1')` のビットで表す。マス `(r, c)` が属するボックスのインデックスは `(r / 3) * 3 + c / 3` である。そのビットが行・列・ボックスのいずれかのマスクにすでに含まれていれば重複なので無効となる。重複がなければ、3つのマスクすべてにそのビットを設定する。

各マスを一度ずつ調べるため、時間計算量は `O(81)`。固定サイズのマスクを9個ずつ使うだけなので、追加の空間計算量は `O(1)` である。

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] columns = new int[9];
        int[] boxes = new int[9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char value = board[r][c];
                if (value == '.') {
                    continue;
                }

                int bit = 1 << (value - '1');
                int box = (r / 3) * 3 + c / 3;
                if ((rows[r] & bit) != 0
                        || (columns[c] & bit) != 0
                        || (boxes[box] & bit) != 0) {
                    return false;
                }

                rows[r] |= bit;
                columns[c] |= bit;
                boxes[box] |= bit;
            }
        }
        return true;
    }
}
```
