---
title: LeetCode 312. 풍선 터뜨리기
author: MINJUN PARK
date: 2022-01-01 22:15:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Dynamic Programming, Burst Balloons, Review]
pin: false
lang: ko
translation_key: leetcode-312-burst-balloons
permalink: /ko/posts/leetcode-312-burst-balloons/
---

![image](https://user-images.githubusercontent.com/55131164/147873064-4d275273-184b-49da-8890-7d8116039bba.png)

[문제 링크](https://leetcode.com/problems/burst-balloons/)

## 구간 동적 계획법

배열 양 끝에 값이 `1`인 가상 풍선을 추가한다. `dp[l][r]`를 양쪽 경계 풍선 `l`, `r`은 터뜨리지 않은 채 그 사이에 있는 풍선을 모두 터뜨려 얻을 수 있는 최대 코인 수라고 하자.

열린 구간에서 마지막으로 터뜨릴 풍선 `k`를 고른다. 그 시점에는 구간 안의 다른 풍선이 이미 모두 사라졌으므로 `k`의 양옆에는 정확히 `l`과 `r`이 남는다. 마지막으로 얻는 코인은 `values[l] * values[k] * values[r]`이며, 양쪽 부분 구간은 그보다 먼저 처리되므로 각각 독립적으로 최적화할 수 있다. 따라서 점화식은 다음과 같다.

`dp[l][r] = max(dp[l][k] + values[l] * values[k] * values[r] + dp[k][r])`

여기서 `l < k < r`이다. 너비가 작은 구간부터 계산하면 두 부분 구간의 답을 이미 구한 상태에서 점화식을 적용할 수 있다. 풍선이 `n`개일 때 구간은 `O(n^2)`개이고 각 구간에서 `k`를 `O(n)`개 시도하므로 시간 복잡도는 `O(n^3)`, 공간 복잡도는 `O(n^2)`이다. 입력이 비어 있으면 내부 풍선이 없으므로 답은 `0`이다.

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] values = new int[n + 2];
        values[0] = values[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            values[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];
        for (int width = 2; width < n + 2; width++) {
            for (int left = 0; left + width < n + 2; left++) {
                int right = left + width;
                for (int last = left + 1; last < right; last++) {
                    int coins = dp[left][last]
                            + values[left] * values[last] * values[right]
                            + dp[last][right];
                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }

        return dp[0][n + 1];
    }
}
```
