---
title: Programmers. Disk Controller
author: MINJUN PARK
date: 2021-12-17 00:56:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    PriorityQueue,
    Coding Interview,
    Programmers,
    Disk Controller,
    디스크 컨트롤러,
    프로그래머스,
  ]
pin: false
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/42627?language=java#>

<br>

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
	public int solution(int[][] jobs) {
        Arrays.sort(jobs, (l, r) -> l[0] - r[0]);
        int endT = 0, jobIdx = 0, sum = 0, len = jobs.length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((l,r)->l[1]-r[1]);

        while(jobIdx < len) {
            while(jobIdx < len && endT >= jobs[jobIdx][0]) pq.add(jobs[jobIdx++]);
            if(pq.size() == 0) endT = jobs[jobIdx][0];
            else {
                int[] timeInfo = pq.poll();
                endT = endT + timeInfo[1];
                sum += endT - timeInfo[0];
            }
        }
        while(pq.size() != 0) {
            int[] timeInfo = pq.poll();
            endT = endT + timeInfo[1];
            sum += endT - timeInfo[0];
        }
        return sum / len;
	}
}
```
