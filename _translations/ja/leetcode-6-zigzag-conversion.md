---
title: LeetCode. 6. ZigZag Conversion
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, ZigZag Conversion]
pin: false
lang: ja
translation_key: leetcode-6-zigzag-conversion
permalink: /ja/posts/leetcode-6-zigzag-conversion/
---

[問題](https://leetcode.com/problems/zigzag-conversion/)

## 解説

文字列を1文字ずつ走査し、現在の行のビルダーに文字を追加します。行インデックスは
下または上へ1つずつ進み、最初または最後の行に到達したら進行方向を反転します。
すべての文字を配置した後、行ビルダーを上から順に連結すると変換後の文字列になります。
行が1つだけの場合、または行数が文字数以上の場合は斜めの移動がないため、入力を
そのまま返します。

この方法ではジグザグの走査を直接シミュレーションします。各文字を走査中に占める行へ
配置し、行順に読み出すため、求める結果と一致します。`N` 文字をそれぞれ一度ずつ
追加して読み出すので、時間計算量は `O(N)` です。行ビルダーと結果に `O(N)` の
空間を使用します。

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
