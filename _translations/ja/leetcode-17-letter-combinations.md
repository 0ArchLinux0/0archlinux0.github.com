---
title: LeetCode. 17. Letter Combinations of a Phone Number
author: MINJUN PARK
date: 2021-12-04 02:44:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, DFS, Letter Combinations of a Phone Number]
pin: false
lang: ja
translation_key: leetcode-17-letter-combinations
permalink: /ja/posts/leetcode-17-letter-combinations/
---

![image](https://user-images.githubusercontent.com/55131164/144647989-bdb91fe5-3725-409c-b187-a37ffe84882d.png)

[問題](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

## バックトラッキング

入力には `2` から `9` までの数字が与えられます。それぞれの数字は電話のキーパッド上の文字に直接対応します。入力が空の場合、作れる組み合わせはないため空のリストを返します。

再帰では数字を1桁ずつ処理します。インデックス `i` の呼び出しに入った時点で、`StringBuilder` には先頭から `i` 個の数字に対して選んだ文字が、それぞれ1文字ずつ順番に格納されている、というのが不変条件です。現在の数字に対応する各文字について、文字を追加して次のインデックスを再帰呼び出しし、次の文字を試す前に追加した文字を削除します。深さが `N` に達すると、接頭辞には入力の各数字に対する文字が1文字ずつそろっているので、完成した組み合わせとして保存します。

組み合わせ数は最大で `4^N` であり、完成した文字列のコピーにはそれぞれ `O(N)` 時間がかかるため、出力サイズを考慮した最悪時の時間計算量は `O(N * 4^N)` です。出力リストを除くと、ビルダーと再帰呼び出しスタックに必要な補助領域は `O(N)` です。

## Java

```java
class Solution {
    private static final String[] KEYPAD = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        List<String> answer = new ArrayList<>();
        if (digits.isEmpty()) {
            return answer;
        }

        backtrack(digits, 0, new StringBuilder(), answer);
        return answer;
    }

    private void backtrack(String digits, int index, StringBuilder prefix, List<String> answer) {
        if (index == digits.length()) {
            answer.add(prefix.toString());
            return;
        }

        String letters = KEYPAD[digits.charAt(index) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            prefix.append(letters.charAt(i));
            backtrack(digits, index + 1, prefix, answer);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
```
