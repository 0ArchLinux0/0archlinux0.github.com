---
title: Codility. ArrayInversionCount
author: MINJUN PARK
date: 2021-12-23 01:39:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Codility, ArrayInversionCount]
pin: false
permalink: /posts/Codility-ArrayInversionCount/
lang: en
translation_key: codility-array-inversion-count
---

The inversion count of an array is the number of pairs of indices `i < j` for which `A[i] > A[j]`. Equal values do not form an inversion. Inserting each value into a sorted `ArrayList` uses binary search to find a position, but shifting elements still takes `O(N)` per insertion, for `O(N²)` total time.

Merge sort counts inversions in `O(N log N)`. Recursively sort each half, then merge the halves into one sorted range. At every step of the merge, both unmerged portions are sorted. If the next right-side value is smaller than the next left-side value, it is smaller than every remaining value on the left, so all of those left-side values form inversions with it. Add their count and take the right value. Otherwise take the left value; choosing the left value on equality ensures equal elements are not counted. A single auxiliary array is reused throughout the recursion.

The count is accumulated as a `long` to avoid overflow and the method returns `-1` only when the total exceeds `1,000,000,000`. Empty and one-element arrays have no pairs, so the method returns `0`. The running time is `O(N log N)` and the auxiliary space is `O(N)` (with `O(log N)` recursion depth).

<br>

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

    // Sorts the half-open range [start, end) and returns its inversion count.
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

[Link] <https://app.codility.com/programmers/trainings/4/array_inversion_count/>

<br>
