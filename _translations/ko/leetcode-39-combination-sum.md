---
title: LeetCode 39. 조합의 합
author: MINJUN PARK
date: 2021-12-29 20:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Combination Sum]
pin: false
lang: ko
translation_key: leetcode-39-combination-sum
permalink: /ko/posts/leetcode-39-combination-sum/
---

![image](https://user-images.githubusercontent.com/55131164/147656399-9041651d-a9b9-4904-a126-eda9c06466f3.png)

[문제 링크](https://leetcode.com/problems/combination-sum/)

서로 다른 양의 정수 `candidates`와 목표값이 주어질 때, 합이 목표값이 되는 모든 고유한 조합을 반환한다. 각 후보 숫자는 횟수 제한 없이 사용할 수 있다.

후보를 정렬한 뒤 깊이 우선 백트래킹을 수행한다. 헬퍼 함수는 시작 인덱스와 남은 합을 전달받는다. 재귀 호출에서 선택한 후보의 인덱스부터 다시 선택하므로 같은 후보를 재사용할 수 있다. 또한 더 작은 값으로 되돌아가지 않으므로 각 조합은 오름차순으로 구성되고 표현 방법이 하나뿐이다. 따라서 순서만 다른 중복 조합이 생기지 않는다.

값을 선택하면 남은 합에서 그 값을 빼고 재귀 호출한 다음, 현재 경로를 복구하기 위해 선택한 값을 제거한다. 후보가 양수이고 정렬되어 있으므로 후보가 남은 합보다 커지는 순간 반복을 종료한다. 남은 합이 0이 되면 경로를 결과에 복사한다.

`V`를 방문한 탐색 상태의 수, `S`를 반환하는 조합의 수, `L`을 가장 긴 조합의 길이라 하자. 탐색 시간은 `O(V + S·L)`이며, 각 결과를 복사하는 데 최대 `L`의 시간이 든다. 정렬에는 `O(n log n)`이 걸린다. 반환 결과를 제외한 재귀 호출과 현재 경로의 추가 공간은 `O(L)`이다.

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
