---
title: LeetCode 39. 組み合わせの合計
author: MINJUN PARK
date: 2021-12-29 20:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Combination Sum]
pin: false
lang: ja
translation_key: leetcode-39-combination-sum
permalink: /ja/posts/leetcode-39-combination-sum/
---

![image](https://user-images.githubusercontent.com/55131164/147656399-9041651d-a9b9-4904-a126-eda9c06466f3.png)

[問題リンク](https://leetcode.com/problems/combination-sum/)

互いに異なる正の整数 `candidates` と目標値が与えられる。合計が目標値になるすべての一意な組み合わせを返す。各候補は何度でも使用できる。

候補をソートしてから、深さ優先のバックトラッキングを行う。ヘルパー関数には開始インデックスと残りの合計を渡す。再帰呼び出しでは選んだ候補のインデックスから次の選択を始めるため、同じ候補を再利用できる。また、より小さい値へ戻らないので、各組み合わせは昇順に構成され、表現方法は一つだけになる。これにより順序だけが異なる重複を避けられる。

値を選んだら残りの合計から差し引いて再帰し、その後、現在の経路を元に戻すため選んだ値を取り除く。候補は正の値でソート済みなので、候補が残りの合計を超えた時点でループを終了する。残りの合計が0になったら、経路を結果にコピーする。

訪問した探索状態の数を `V`、返す組み合わせの数を `S`、最長の組み合わせの長さを `L` とする。探索時間は `O(V + S·L)` であり、各結果のコピーには最大 `L` の時間がかかる。ソートには `O(n log n)` かかる。返却結果を除く再帰呼び出しと現在の経路の追加領域は `O(L)` である。

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(
            int[] candidates,
            int start,
            int remaining,
            List<Integer> path,
            List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            int candidate = candidates[i];
            if (candidate > remaining) {
                break;
            }

            path.add(candidate);
            backtrack(candidates, i, remaining - candidate, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```
