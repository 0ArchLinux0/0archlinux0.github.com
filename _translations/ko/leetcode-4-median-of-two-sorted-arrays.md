---
title: LeetCode. 4. 두 정렬 배열의 중앙값
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Median of Two Sorted Arrays]
pin: false
lang: ko
translation_key: leetcode-4-median-of-two-sorted-arrays
permalink: /ko/posts/leetcode-4-median-of-two-sorted-arrays/
---

![image](https://user-images.githubusercontent.com/88752447/130302411-78bf9bf2-ad00-4dcb-a19f-37fa1ce7d3c6.png)

[문제](https://leetcode.com/problems/median-of-two-sorted-arrays/)

## 이진 탐색으로 분할하기

두 배열이 모두 정렬되어 있으므로 병합하지 않고도 각 배열을 왼쪽과 오른쪽 절반으로 나눌 수 있습니다. 왼쪽 절반에는 전체 원소 수의 절반(올림)에 해당하는 원소가 있어야 하며, 왼쪽의 모든 값은 오른쪽의 모든 값보다 작거나 같아야 합니다.

더 짧은 배열인 `nums1`에서 분할 위치를 이진 탐색합니다. `nums1`의 `i`개 원소가 왼쪽 절반에 들어간다면, `nums2`에서는 `j = (m + n + 1) / 2 - i`개가 왼쪽에 들어가야 합니다. 여기서 `m`, `n`은 각 배열의 길이입니다. 다음 두 경계 비교를 만족할 때까지 `i`를 조정합니다.

- `left1 <= right2`
- `left2 <= right1`

`left1`, `left2`는 각 배열의 왼쪽 부분에서 가장 큰 값이고, `right1`, `right2`는 오른쪽 부분에서 가장 작은 값입니다. 두 비교가 모두 성립하면 두 배열을 합친 왼쪽 부분에는 전체 값 중 작은 절반이 들어 있습니다. 전체 원소 수가 홀수이면 왼쪽에서 가장 큰 값이 중앙값입니다. 짝수이면 왼쪽에서 가장 큰 값과 합친 오른쪽 부분에서 가장 작은 값의 평균이 중앙값입니다.

분할 결과 배열의 한쪽이 비어 있으면 해당 경계에 `Integer.MIN_VALUE` 또는 `Integer.MAX_VALUE`를 사용합니다. 이 센티널 덕분에 양 끝에서의 분할도 별도 조건 없이 같은 비교로 처리할 수 있습니다. 문제 조건에 따라 입력 배열 중 적어도 하나는 비어 있지 않습니다.

각 이진 탐색 단계에서 더 짧은 배열의 탐색 범위가 절반으로 줄어들므로 시간 복잡도는 `O(log(min(m, n)))`입니다. 상수 개수의 값만 저장하므로 추가 공간 복잡도는 `O(1)`입니다.

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;
        int half = (m + n + 1) / 2;
        int low = 0;
        int high = m;

        while (low <= high) {
            int i = low + (high - low) / 2;
            int j = half - i;

            int left1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = i == m ? Integer.MAX_VALUE : nums1[i];
            int left2 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = j == n ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }
                long middleValues = (long) Math.max(left1, left2)
                        + Math.min(right1, right2);
                return middleValues / 2.0;
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays must be sorted.");
    }
}
```
