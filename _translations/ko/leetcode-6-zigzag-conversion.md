---
title: LeetCode. 6. ZigZag Conversion
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, ZigZag Conversion]
pin: false
lang: ko
translation_key: leetcode-6-zigzag-conversion
permalink: /ko/posts/leetcode-6-zigzag-conversion/
---

[문제](https://leetcode.com/problems/zigzag-conversion/)

## 풀이

문자열을 한 글자씩 순회하며 현재 행의 빌더에 문자를 추가합니다. 행 인덱스는 아래쪽이나
위쪽으로 한 칸씩 이동하고, 첫 번째 행이나 마지막 행에 도달하면 이동 방향을 반대로
바꿉니다. 모든 문자를 배치한 뒤 행 빌더를 위에서 아래 순서로 이어 붙이면 변환된
문자열이 됩니다. 행이 하나뿐이거나 행 수가 문자 수 이상이면 대각선 이동이 없으므로
입력을 그대로 반환합니다.

이 방법은 지그재그 순회를 직접 시뮬레이션합니다. 각 문자를 순회에서 차지하는 행에
배치하고 행 순서대로 읽으므로 요구되는 결과와 일치합니다. 문자 `N`개를 한 번씩
추가하고 읽기 때문에 시간 복잡도는 `O(N)`이며, 행 빌더와 결과에 `O(N)` 공간을
사용합니다.

## Java

```java
import java.util.ArrayList;

class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s;
        }

        ArrayList<StringBuilder> rows = new ArrayList<>(numRows);
        for (int row = 0; row < numRows; row++) {
            rows.add(new StringBuilder());
        }

        int currentRow = 0;
        int direction = 1;
        for (int i = 0; i < s.length(); i++) {
            rows.get(currentRow).append(s.charAt(i));
            if (currentRow == 0) {
                direction = 1;
            } else if (currentRow == numRows - 1) {
                direction = -1;
            }
            currentRow += direction;
        }

        StringBuilder result = new StringBuilder(s.length());
        for (StringBuilder row : rows) {
            result.append(row);
        }
        return result.toString();
    }
}
```
