---
title: LeetCode 997. 町の判事を探す
author: MINJUN PARK
date: 2022-01-04 01:14:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Find the Town Judge]
pin: false
lang: ja
translation_key: leetcode-997-town-judge
permalink: /ja/posts/leetcode-997-town-judge/
source_permalink: /posts/Leetcode-997.-Find-the-Town-Judge/
---

![image](https://user-images.githubusercontent.com/55131164/147953490-dea0cee6-92dc-4589-aa27-32dccbde624f.png)

[問題リンク](https://leetcode.com/problems/find-the-town-judge/)

## 次数による判定

信頼関係 `[a, b]` を、人 `a` から人 `b` への有向辺として考える。町の判事は誰も信頼しないため、出次数は `0` である。また、それ以外の全員が判事を信頼するため、入次数は `n - 1` となる。両方の条件を確認する必要がある。入次数だけで判定すると、ほかの人を信頼している人を誤って判事に選ぶ可能性がある。

全員の入次数と出次数を数え、両方の条件を満たす人を返す。`n == 1` の場合、唯一の人の入次数と出次数はいずれも `0` なので、信頼関係がないときに限り条件を満たす。特別な分岐を設けなくても同じ走査で処理できる。該当する人がいなければ `-1` を返す。

信頼関係を `m = trust.length` 個それぞれ一度処理し、さらに `n` 人を確認するため、時間計算量は `O(n + m)` である。二つの次数配列に必要な追加領域は `O(n)` となる。

```java
class Solution {
    public int findJudge(int n, int[][] trust) {
        int[] indegree = new int[n + 1];
        int[] outdegree = new int[n + 1];

        for (int[] relation : trust) {
            outdegree[relation[0]]++;
            indegree[relation[1]]++;
        }

        for (int person = 1; person <= n; person++) {
            if (outdegree[person] == 0 && indegree[person] == n - 1) {
                return person;
            }
        }
        return -1;
    }
}
```
