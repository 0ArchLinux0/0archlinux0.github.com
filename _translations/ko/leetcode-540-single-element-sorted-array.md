---
title: LeetCode 540. 정렬된 배열에서 단일 원소 찾기
author: MINJUN PARK
date: 2021-11-17 23:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Binary Search, Single Element in a Sorted Array]
pin: false
lang: ko
translation_key: leetcode-540-single-element-sorted-array
permalink: /ko/posts/leetcode-540-single-element-sorted-array/
---

![image](https://user-images.githubusercontent.com/55131164/142717646-05cb5bbe-41a1-4b39-aa96-3e9ec29a9161.png)

[문제 링크] <https://leetcode.com/problems/single-element-in-a-sorted-array/>

정렬된 배열에서 단일 원소를 제외한 모든 값은 정확히 두 번씩 나타납니다. 단일 원소 앞에서는 각 쌍이 짝수 인덱스에서 시작하고, 단일 원소 뒤에서는 쌍의 정렬이 어긋나 각 쌍이 홀수 인덱스에서 시작합니다.

각 반복에서 `mid`를 쌍의 시작점인 짝수 인덱스로 맞춥니다. `nums[mid] == nums[mid + 1]`이면 이 쌍은 온전하므로 단일 원소는 그 오른쪽에 있습니다. 그렇지 않으면 `mid`까지의 쌍 정렬이 깨졌으므로 단일 원소는 `mid` 또는 그 왼쪽에 있습니다. 따라서 검색 구간을 단일 원소가 있는 절반으로 좁힐 수 있습니다.

입력 배열은 비어 있지 않고 길이가 홀수이며, 단일 원소가 정확히 하나 있다고 보장됩니다. 반복이 끝나면 그 인덱스에 도달하여 값을 반환합니다. 이진 탐색의 시간 복잡도는 $O(\log N)$이고 추가 공간 복잡도는 $O(1)$입니다.

```java
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if ((mid & 1) == 1) mid--;
            if (nums[mid] == nums[mid + 1]) {
                low = mid + 2;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }
}
```
