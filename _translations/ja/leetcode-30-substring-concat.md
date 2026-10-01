---
title: LeetCode. 30. Substring with Concatenation of All Words
author: MINJUN PARK
date: 2021-12-21 02:42:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Substring with Concatenation of All Words,
  ]
pin: false
lang: ja
translation_key: leetcode-30-substring-concat
permalink: /ja/posts/leetcode-30-substring-concat/
---

![image](https://user-images.githubusercontent.com/55131164/146821438-9874ee73-c746-4fd3-adf0-658e277a52e3.png)

[問題](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)

## 解法

すべての単語の長さは等しく、0 ではないため、単語の長さ `L` ずつ進むスライディングウィンドウを使えます。開始オフセットを `0` から `L - 1` までそれぞれ処理します。`target` は `words` に含まれる各単語の必要個数を保持し、`window` は現在のウィンドウ内の単語数を保持します。これにより、重複する単語も正しく扱えます。

右ポインターで新しい単語を追加したとき、それが `target` に存在しなければ、そのウィンドウは有効になり得ません。`window` と単語数をクリアし、左ポインターを右ポインターまで進めます。既知の単語ならウィンドウに加え、その単語が必要個数を超えている間、左側から単語を取り除きます。ウィンドウ内の単語数が `words.length` と一致したら、開始位置を答えに追加し、左端の単語を一つ取り除きます。この一つ分の縮小により、次に重なり合う答えも検出できます。各オフセットで見つけた位置は最後に昇順でソートします。

単語の長さを `L`、入力文字列の長さを `N`、`words` の単語数を `W`、異なる単語数を `U`、返す位置の数を `R` とします。各オフセットで右ポインターは一度だけ進むため、処理する単語区間は合計 `O(N)` 個です。各区間の文字列抽出に `O(L)`、最後のソートに `O(R log R)` かかるので、時間計算量は `O(N * L + R log R)` です。必要個数とウィンドウ内個数を保存するマップおよびウィンドウには、それぞれ最大 `U` 個、`W` 個の項目があるため、これらの空間計算量は `O(U + W)` です。返却結果の `O(R)` の領域は別途必要です。

## Java

```java
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (words.length == 0) {
            return result;
        }

        int wordLength = words[0].length();
        int wordCount = words.length;
        int totalLength = wordLength * wordCount;
        if (s.length() < totalLength) {
            return result;
        }

        Map<String, Integer> target = new HashMap<>();
        for (String word : words) {
            target.put(word, target.getOrDefault(word, 0) + 1);
        }

        for (int offset = 0; offset < wordLength; offset++) {
            Map<String, Integer> window = new HashMap<>();
            int left = offset;
            int right = offset;
            int count = 0;

            while (right + wordLength <= s.length()) {
                String word = s.substring(right, right + wordLength);
                right += wordLength;

                if (!target.containsKey(word)) {
                    window.clear();
                    count = 0;
                    left = right;
                    continue;
                }

                window.put(word, window.getOrDefault(word, 0) + 1);
                count++;

                while (window.get(word) > target.get(word)) {
                    String removed = s.substring(left, left + wordLength);
                    window.put(removed, window.get(removed) - 1);
                    left += wordLength;
                    count--;
                }

                if (count == wordCount) {
                    result.add(left);
                    String removed = s.substring(left, left + wordLength);
                    window.put(removed, window.get(removed) - 1);
                    left += wordLength;
                    count--;
                }
            }
        }

        Collections.sort(result);
        return result;
    }
}
```
