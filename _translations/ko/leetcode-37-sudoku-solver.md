---
title: LeetCode. 37. Sudoku Solver
author: MINJUN PARK
date: 2021-12-26 08:40:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Sudoku Solver]
pin: false
lang: ko
translation_key: leetcode-37-sudoku-solver
permalink: /ko/posts/leetcode-37-sudoku-solver/
---

![image](https://user-images.githubusercontent.com/55131164/147395541-744d69fb-dc7f-4c11-b5d5-5180248f6a3b.png)

유효한 스도쿠 보드에는 해가 하나만 있습니다. 각 행, 열, 3 × 3 박스에 `1`부터 `9`까지의 숫자가 정확히 한 번씩 들어가도록 빈 칸을 제자리에서 채웁니다.

[문제 링크](https://leetcode.com/problems/sudoku-solver/)

## 접근 방법

각 행, 열, 박스마다 이미 사용 중인 숫자를 9비트 마스크로 기록합니다. 비트 `d`는 숫자 `d + 1`을 나타내며, 비트가 설정되어 있으면 해당 칸에 그 숫자를 놓을 수 없습니다. 따라서 빈 칸에 놓을 수 있는 숫자는 전체 마스크 중 해당 행·열·박스의 마스크에 모두 포함되지 않은 비트입니다.

재귀 단계마다 남은 빈 칸을 살펴보고 놓을 수 있는 숫자가 가장 적은 칸을 선택합니다(최소 잔여값, MRV). 제약이 강한 칸부터 선택하면 분기 수를 일찍 줄일 수 있습니다. 가능한 숫자가 없는 칸이 있으면 현재 탐색 경로는 실패입니다. 그렇지 않으면 각 후보를 차례로 시도합니다. 보드에 숫자를 쓰고 해당 행·열·박스의 마스크를 설정한 뒤 재귀 호출합니다. 경로가 실패하면 비트를 지우고 칸을 `'.'`로 되돌립니다. 성공하면 즉시 반환하므로 완성된 해가 입력 보드에 그대로 남습니다.

빈 칸은 최대 81개이며, 최악의 경우 탐색은 지수 시간입니다(각 빈 칸에서 최대 9개 선택). MRV는 모순을 일찍 찾고 분기 수를 줄여 실제 탐색을 효율적으로 만듭니다. 보조 공간은 재귀 스택과 고정 크기 마스크를 포함해 `O(81)`이고, 보드는 제자리에서 수정합니다.

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
