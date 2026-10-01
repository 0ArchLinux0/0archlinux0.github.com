---
title: LeetCode 40. 조합의 합 II
author: MINJUN PARK
date: 2021-12-30 17:08:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Combination Sum II]
pin: false
lang: ko
translation_key: leetcode-40-combination-sum-ii
permalink: /ko/posts/leetcode-40-combination-sum-ii/
---

![image](https://user-images.githubusercontent.com/55131164/147734808-855ed472-dce3-45cd-8a7c-54364ea18c93.png)

[문제 링크](https://leetcode.com/problems/combination-sum-ii/)

## 풀이

후보를 정렬한 뒤 인덱스를 기준으로 깊이 우선 탐색을 합니다. 각 재귀 호출은 현재 인덱스보다 뒤에 있는 인덱스(`i + 1`)만 선택하므로 배열의 각 원소는 최대 한 번만 사용할 수 있습니다. 서로 다른 인덱스에 있는 같은 값은 각각 별도의 원소로 사용할 수 있습니다.

같은 탐색 깊이에서 바로 앞 후보와 값이 같으면 건너뜁니다. 이렇게 하면 같은 값으로 시작해 동일한 조합을 만드는 중복 탐색을 막을 수 있습니다. 반면 더 깊은 재귀 호출에서는 다른 인덱스의 같은 값을 선택할 수 있습니다. 후보는 양수이며 정렬되어 있으므로 현재 후보가 남은 목표값보다 커지면 반복을 끝낼 수 있습니다.

남은 목표값이 0이 되면 현재 경로의 복사본을 결과에 추가합니다. 탐색 후 경로를 되돌리므로 복사본을 저장해야 합니다.

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

같은 깊이에서의 중복 건너뛰기는 결과 조합의 중복을 방지하고, 다음 인덱스부터 탐색하는 것은 각 원소를 한 번만 사용하는 규칙을 보장합니다. 정렬에는 `O(N log N)` 시간이 걸립니다. 후보가 `N`개일 때 탐색의 최악 시간 복잡도는 `O(2^N)`이며, 여기에 반환 조합 복사 비용이 추가됩니다. 결과 공간을 제외한 재귀 경로의 보조 공간은 `O(N)`입니다.
