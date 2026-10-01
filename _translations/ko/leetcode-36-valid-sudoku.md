---
title: LeetCode 36. 유효한 스도쿠
author: MINJUN PARK
date: 2021-12-24 06:58:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Valid Sudoku]
pin: false
lang: ko
translation_key: leetcode-36-valid-sudoku
permalink: /ko/posts/leetcode-36-valid-sudoku/
---

[문제 링크](https://leetcode.com/problems/valid-sudoku/)

9×9 스도쿠 보드가 유효한지 확인한다. 채워진 칸만 검사하면 된다. 빈칸(`.`)은 무시하며, 현재 부분 보드가 스도쿠를 완성할 수 있는지는 확인하지 않는다.

각 행, 열, 3×3 박스에서 이미 사용된 숫자를 각각 9개의 정수 마스크에 기록한다. 숫자 `d`는 `1 << (d - '1')` 비트로 나타낸다. 칸 `(r, c)`의 박스 인덱스는 `(r / 3) * 3 + c / 3`이다. 해당 비트가 행·열·박스 마스크 중 하나에 이미 있으면 중복이므로 유효하지 않다. 그렇지 않으면 세 마스크 모두에 비트를 설정한다.

각 칸을 한 번씩 방문하므로 시간 복잡도는 `O(81)`이고, 9개씩의 고정 크기 마스크만 사용하므로 추가 공간 복잡도는 `O(1)`이다.

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
