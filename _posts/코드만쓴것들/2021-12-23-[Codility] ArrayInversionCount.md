---
title: Codility. ArrayInversionCount
author: MINJUN PARK
date: 2021-12-23 01:39:00 +0900
categories: [Codility, Java]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Codility,
    ArrayInversionCount
  ]
pin: false
---


[Link] <https://app.codility.com/programmers/trainings/4/array_inversion_count/>

<br>

```java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        ArrayList<Integer> list = new ArrayList<>();
        int cnt = 0;
        list.add(A[0]);
        for(int i = 1; i < A.length; i++) {
            int insertIdx = findIdx(list, A[i]);
            if(insertIdx == list.size()) list.add(A[i]);
            else list.add(insertIdx, A[i]);
            cnt += insertIdx;
        }
        return cnt;
    }

    public int findIdx(ArrayList<Integer> list, int val) {
        int l = 0, r = list.size() - 1, mid;
        while(l <= r) {
            mid = (l + r) / 2;
            if(list.get(mid) > val) l = mid + 1;
            else if(list.get(mid) == val) return mid;
            else r = mid - 1;
        }
        return l;
    }
}
```
