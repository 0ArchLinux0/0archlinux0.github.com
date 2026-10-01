---
title: Codility - 배열 역전 쌍 개수
author: MINJUN PARK
date: 2021-12-23 01:39:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 코딩 인터뷰, Codility, 배열 역전 쌍]
pin: false
lang: ko
translation_key: codility-array-inversion-count
permalink: /ko/posts/codility-array-inversion-count/
---

[문제 링크](https://app.codility.com/programmers/trainings/4/array_inversion_count/)

배열의 역전 쌍 개수는 `i < j`이면서 `A[i] > A[j]`인 인덱스 쌍의 수다. 값이 같은 쌍은 역전 쌍이 아니다. 정렬된 `ArrayList`에 각 값을 삽입하면 이진 탐색으로 위치를 찾을 수 있지만, 삽입 과정에서 원소를 이동하는 데 한 번에 `O(N)`이 걸리므로 전체 시간 복잡도는 `O(N²)`이다.

병합 정렬을 이용하면 `O(N log N)` 시간에 역전 쌍을 셀 수 있다. 배열을 재귀적으로 두 구간으로 나누어 각각 정렬한 다음, 두 구간을 하나의 정렬된 구간으로 병합한다. 병합 도중 아직 처리하지 않은 왼쪽과 오른쪽 구간은 각각 정렬되어 있다. 오른쪽의 다음 값이 왼쪽의 다음 값보다 작다면, 그 값은 왼쪽에 남은 모든 값보다 작다. 따라서 왼쪽에 남은 값의 개수만큼 역전 쌍을 더하고 오른쪽 값을 가져온다. 그 외에는 왼쪽 값을 가져오며, 같은 값일 때 왼쪽 값을 먼저 선택하므로 같은 값은 역전 쌍으로 세지 않는다. 재귀 전체에서 보조 배열 하나를 재사용한다.

개수는 오버플로를 막기 위해 `long`으로 누적하고, 총 개수가 `1,000,000,000`을 초과할 때만 `-1`을 반환한다. 길이가 0 또는 1인 배열에는 쌍이 없으므로 `0`을 반환한다. 시간 복잡도는 `O(N log N)`, 보조 공간 복잡도는 `O(N)`이며 재귀 호출 스택은 `O(log N)`이다.

```java
class Solution {
    private static final long LIMIT = 1_000_000_000L;

    public int solution(int[] A) {
        if (A.length < 2) {
            return 0;
        }

        int[] auxiliary = new int[A.length];
        long inversions = sortAndCount(A, auxiliary, 0, A.length);
        return inversions > LIMIT ? -1 : (int) inversions;
    }

    // [start, end) 구간을 정렬하고 그 구간의 역전 쌍 개수를 반환한다.
    private long sortAndCount(int[] values, int[] auxiliary, int start, int end) {
        if (end - start < 2) {
            return 0;
        }

        int middle = start + (end - start) / 2;
        long inversions = sortAndCount(values, auxiliary, start, middle)
                + sortAndCount(values, auxiliary, middle, end);

        int left = start;
        int right = middle;
        int output = start;

        while (left < middle && right < end) {
            if (values[left] <= values[right]) {
                auxiliary[output++] = values[left++];
            } else {
                auxiliary[output++] = values[right++];
                inversions += middle - left;
            }
        }

        while (left < middle) {
            auxiliary[output++] = values[left++];
        }
        while (right < end) {
            auxiliary[output++] = values[right++];
        }
        System.arraycopy(auxiliary, start, values, start, end - start);

        return inversions;
    }
}
```
