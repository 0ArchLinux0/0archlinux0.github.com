---
title: LeetCode. 15. 3Sum
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, 3Sum]
pin: false
lang: ko
translation_key: leetcode-15-three-sum
permalink: /ko/posts/leetcode-15-three-sum/
---

![image](https://user-images.githubusercontent.com/88752447/130299575-af2573e3-49a8-4230-815f-04b01f832386.png)

[문제](https://leetcode.com/problems/3sum/)

## 풀이

배열을 정렬한 뒤 각 인덱스 `i`를 차례로 고정하고, 나머지 구간에서 두 포인터
`left = i + 1`, `right = nums.length - 1`로 탐색합니다. 정렬되어 있으므로
포인터를 한 방향으로만 이동할 수 있습니다. 세 수의 합이 0보다 작다면 현재
`left`와 더 작은 오른쪽 값으로 만드는 합도 더 작으므로 `left`를 증가시켜도
해를 놓치지 않습니다. 반대로 합이 0보다 크다면 현재 `right`와 더 큰 왼쪽 값의
합도 더 크므로 `right`를 감소시키는 것이 안전합니다. 합이 0이면 세 값을
결과에 추가한 뒤 두 포인터를 이동합니다.

고정한 값이 직전 고정 값과 같으면 해당 인덱스는 건너뛰어 같은 첫 번째 값을
다시 탐색하지 않습니다. 결과를 추가한 뒤에는 양쪽 포인터의 중복 값도 건너뛰어
동일한 값의 세 쌍이 여러 번 나오지 않게 합니다. 정렬된 배열에서 `nums[i] > 0`이면
이후 값도 모두 양수이므로 탐색을 종료할 수 있습니다.

## Java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                long sum = (long) nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }
}
```

정렬은 `O(N log N)`, 고정 인덱스마다 수행하는 두 포인터 탐색은 전체 `O(N²)`의
시간 복잡도를 가집니다. 정렬은 입력 배열 내부에서 수행하며 정렬 구현의 작업
공간은 `O(log N)`입니다. 반환할 삼중항이 `K`개라면 결과에 필요한 공간은 `O(K)`입니다.
