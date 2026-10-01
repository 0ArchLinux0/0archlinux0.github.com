---
title: LeetCode. 1. Two Sum
author: MINJUN PARK
date: 2021-11-19 23:03:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Two Sum]
pin: false
lang: ko
translation_key: leetcode-1-two-sum
permalink: /ko/posts/leetcode-1-two-sum/
---

[문제](https://leetcode.com/problems/two-sum/)

## 풀이

배열을 왼쪽에서 오른쪽으로 한 번 순회합니다. `nums[i]`를 처리하기 전에 맵에는 더 앞에서 확인한 값과 해당 인덱스만 저장되어 있습니다. 현재 값에 대해 보수 `target - nums[i]`를 맵에서 찾습니다. 보수가 있으면 저장된 인덱스와 `i`가 정답입니다. 찾지 못하면 현재 값과 인덱스를 저장해 이후 원소에서 사용할 수 있게 합니다.

맵에는 이전 인덱스만 저장되므로 현재 원소를 자기 자신과 짝지을 수 없습니다. LeetCode는 정답이 정확히 하나 존재한다고 보장하므로, 순회 중에 해당 인덱스 쌍을 찾을 수 있습니다.

보수를 찾기 전에 `long`으로 계산합니다. 보수가 `int` 범위를 벗어나면 배열의 어떤 값과도 같을 수 없으므로 건너뜁니다. 이렇게 하면 `target - nums[i]`의 정수 오버플로를 방지할 수 있습니다.

평균 시간 복잡도는 `O(N)`이고, 추가 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> earlierIndices = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            long complement = (long) target - nums[i];
            if (complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE) {
                Integer earlierIndex = earlierIndices.get((int) complement);
                if (earlierIndex != null) {
                    return new int[] { earlierIndex, i };
                }
            }

            earlierIndices.put(nums[i], i);
        }

        throw new IllegalStateException("LeetCode guarantees exactly one solution");
    }
}
```
