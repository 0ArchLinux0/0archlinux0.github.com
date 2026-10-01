---
title: LeetCode 131. 回文分割
author: MINJUN PARK
date: 2022-01-06 01:57:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Palindrome Partitioning]
pin: false
lang: ja
translation_key: leetcode-131-palindrome-partitioning
permalink: /ja/posts/leetcode-131-palindrome-partitioning/
---

![image](https://user-images.githubusercontent.com/55131164/148258068-2e1914b9-8232-4518-8d93-13770bd229e7.png)

[問題リンク](https://leetcode.com/problems/palindrome-partitioning/)

## 回文テーブルを事前計算してからバックトラック

`palindrome[left][right]` を、両端のインデックスを含む部分文字列 `s[left..right]` が回文かどうかを表す値とする。両端の文字が一致し、長さが 2 以下であるか、内側の部分文字列も回文であれば、その部分文字列は回文である。

`palindrome[left][right] = (s.charAt(left) == s.charAt(right)) && (right - left < 2 || palindrome[left + 1][right - 1])`

`left` を右から左へ動かしてテーブルを埋めると、より短い内側の区間を先に計算できる。テーブルの計算には時間・空間ともに `O(n^2)` かかる。

バックトラックで左から分割を作る。現在の開始インデックスから終了インデックスを順に試し、テーブルで回文と判定された部分文字列を経路に追加して、次のインデックスから再帰する。開始インデックスが `n` に達したら、現在の経路をコピーして完成した分割として保存する。この終了条件は空文字列も処理する。空の経路を結果に追加するため、`partition("")` は空の分割を 1 つ返す。空でない文字列の完成分割数は最大 `2^(n-1)` 個である。結果文字列の生成を除けば、DFS の経路と呼び出しスタックは `O(n)` の空間を使う。結果文字列の生成も含む最悪時間計算量は `O(n^2 + n * 2^n)`、補助空間計算量は `O(n^2)` である。

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] palindrome = new boolean[n][n];

        for (int left = n - 1; left >= 0; left--) {
            for (int right = left; right < n; right++) {
                palindrome[left][right] = s.charAt(left) == s.charAt(right)
                        && (right - left < 2 || palindrome[left + 1][right - 1]);
            }
        }

        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, palindrome, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(
            String s,
            int start,
            boolean[][] palindrome,
            List<String> path,
            List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (!palindrome[start][end]) {
                continue;
            }
            path.add(s.substring(start, end + 1));
            backtrack(s, end + 1, palindrome, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```
