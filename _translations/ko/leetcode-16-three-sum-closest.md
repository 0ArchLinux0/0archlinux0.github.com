---
title: LeetCode 16 - 세 수의 합에 가장 가까운 값
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [JavaScript, 알고리즘, 코딩 인터뷰, LeetCode, 3Sum Closest]
pin: false
lang: ko
translation_key: leetcode-16-three-sum-closest
permalink: /ko/posts/leetcode-16-three-sum-closest/
---

[문제 링크](https://leetcode.com/problems/3sum-closest/)

정수 배열 `nums`와 정수 `target`이 주어질 때, 서로 다른 세 원소의 합 중 `target`에 가장 가까운 값을 반환합니다. 같은 거리의 합이 여러 개라면 어느 것이든 반환할 수 있습니다.

배열을 오름차순으로 정렬하고 각 원소를 차례로 첫 번째 원소로 고정합니다. 나머지 범위의 양 끝에 두 포인터를 두고 세 수의 합을 탐색합니다. 합이 `target`보다 작으면 더 큰 합을 만들기 위해 왼쪽 포인터를 오른쪽으로 이동하고, 더 크면 합을 줄이기 위해 오른쪽 포인터를 왼쪽으로 이동합니다. 매번 현재 합과 지금까지 가장 가까웠던 합의 절댓값 차이를 비교해 더 나은 값을 저장합니다. 정확히 `target`을 찾으면 그보다 가까운 합은 없으므로 즉시 반환합니다.

고정 원소나 포인터가 같은 값인 경우는 건너뛸 수 있습니다. 이는 같은 탐색을 반복하지 않게 하는 최적화이며, 가능한 최솟값이나 최댓값의 합을 놓치지 않으므로 정답에는 영향을 주지 않습니다.

정렬은 `O(N log N)`, 각 고정 원소에서 두 포인터 탐색은 `O(N)`이므로 총 시간 복잡도는 `O(N²)`입니다. 정렬을 제외한 탐색의 추가 공간은 `O(1)`입니다. JavaScript의 `Array.prototype.sort`가 사용하는 메모리는 엔진 구현에 따라 다르므로 정렬까지 포함한 추가 공간을 보편적으로 `O(1)`이라고 단정할 수 없습니다.

```javascript
/**
 * @param {number[]} nums
 * @param {number} target
 * @return {number}
 */
function threeSumClosest(nums, target) {
  nums.sort((a, b) => a - b);

  let closest = nums[0] + nums[1] + nums[2];

  for (let i = 0; i < nums.length - 2; i++) {
    if (i > 0 && nums[i] === nums[i - 1]) continue;

    let left = i + 1;
    let right = nums.length - 1;

    while (left < right) {
      const sum = nums[i] + nums[left] + nums[right];

      if (Math.abs(sum - target) < Math.abs(closest - target)) {
        closest = sum;
      }
      if (sum === target) return sum;

      if (sum < target) {
        const leftValue = nums[left];
        while (left < right && nums[left] === leftValue) left++;
      } else {
        const rightValue = nums[right];
        while (left < right && nums[right] === rightValue) right--;
      }
    }
  }

  return closest;
}
```
