---
title: LeetCode. 11. 물을 담을 수 있는 가장 많은 컨테이너
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Container With Most Water,
  ]
pin: false
lang: ko
translation_key: leetcode-11-container-with-most-water
permalink: /ko/posts/leetcode-11-container-with-most-water/
---

![image](https://user-images.githubusercontent.com/88752447/130302060-dbc8a9ac-6d5e-46d6-8d19-e4426a918370.png)

[문제: Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

## 투 포인터

`l < r`인 두 인덱스를 고르면 두 선 중 더 낮은 선까지만 물을 담을 수 있습니다. 컨테이너의 너비는 `r - l`, 높이는 `min(height[l], height[r])`이며 넓이는 다음과 같습니다.

$$
(r-l)\times\min(\text{height}[l],\text{height}[r]).
$$

배열 양 끝의 두 선으로 시작합니다. 이 쌍은 가능한 가장 넓은 컨테이너입니다. 넓이를 기록한 다음 더 낮은 선을 가리키는 포인터를 안쪽으로 옮깁니다. 두 높이가 같다면 둘 중 어느 쪽을 옮겨도 되며, 아래 구현은 오른쪽 포인터를 옮깁니다.

## 더 낮은 쪽을 버려도 되는 이유

`height[l] <= height[r]`라고 합시다. 현재 컨테이너의 높이는 `height[l]`에 의해 제한됩니다. `l`을 그대로 두고 오른쪽 끝을 `r' < r`로 선택하면 너비는 `r-l`보다 작고, 왼쪽 벽은 여전히 `height[l]`이므로 컨테이너 높이도 `height[l]`을 넘을 수 없습니다. 따라서 넓이는 현재 넓이보다 클 수 없습니다. `l`을 사용하면서 `r`보다 안쪽에 끝점을 둔 더 나은 답은 없으므로 `l`을 버리고 오른쪽으로 이동해도 됩니다. 오른쪽이 더 낮은 경우도 대칭적으로 같습니다. 이 과정을 반복하면 더 나은 가능성이 있는 쌍은 모두 검사됩니다.

## Java 구현

제약은 `2 <= height.length <= 10^5`, `0 <= height[i] <= 10^4`입니다. 따라서 가능한 최대 넓이는 `(10^5 - 1) * 10^4 = 999,990,000` 이하이며, 부호 있는 32비트 `int`에 들어갑니다. 곱셈과 결과 모두 `int`로 계산해도 안전합니다.

```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;

        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            best = Math.max(best, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return best;
    }
}
```

각 포인터는 최대 `N`번 안쪽으로 이동하므로 시간 복잡도는 `O(N)`, 추가 공간 복잡도는 `O(1)`입니다.
