---
title: LeetCode 40. 組み合わせの合計 II
author: MINJUN PARK
date: 2021-12-30 17:08:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Combination Sum II]
pin: false
lang: ja
translation_key: leetcode-40-combination-sum-ii
permalink: /ja/posts/leetcode-40-combination-sum-ii/
---

![image](https://user-images.githubusercontent.com/55131164/147734808-855ed472-dce3-45cd-8a7c-54364ea18c93.png)

[問題リンク](https://leetcode.com/problems/combination-sum-ii/)

## 方針

候補をソートし、インデックスを使って深さ優先探索します。各再帰呼び出しでは現在より後ろのインデックス（`i + 1`）だけを選ぶため、配列中の各要素は最大1回しか使えません。異なるインデックスにある同じ値は、それぞれ別の要素として利用できます。

同じ探索階層で直前の候補と値が同じなら、その候補をスキップします。これにより同じ値から始まる同一の組み合わせを重複して探索せずに済みます。一方、より深い再帰呼び出しでは、別のインデックスにある同じ値を選べます。候補は正の数でソート済みなので、現在の候補が残りの目標値を超えたらループを終了できます。

残りの目標値が0になったら、現在のパスのコピーを結果に追加します。探索後にパスはバックトラックで再利用されるため、コピーを保存する必要があります。

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        search(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void search(
            int[] candidates,
            int remaining,
            int start,
            List<Integer> path,
            List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if (candidates[i] > remaining) {
                break;
            }

            path.add(candidates[i]);
            search(candidates, remaining - candidates[i], i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

同じ階層での重複スキップは結果の重複を防ぎ、次のインデックスから探索することで各要素を1回だけ使う規則を守ります。ソートには `O(N log N)` 時間がかかります。候補数を `N` とすると探索の最悪時間計算量は `O(2^N)` で、返却する各組み合わせのコピー時間が加わります。出力を除いた再帰パスの補助空間は `O(N)` です。
