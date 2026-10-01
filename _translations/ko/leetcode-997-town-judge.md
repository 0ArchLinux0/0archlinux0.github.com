---
title: LeetCode 997. 마을 판사 찾기
author: MINJUN PARK
date: 2022-01-04 01:14:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Find the Town Judge]
pin: false
lang: ko
translation_key: leetcode-997-town-judge
permalink: /ko/posts/leetcode-997-town-judge/
source_permalink: /posts/Leetcode-997.-Find-the-Town-Judge/
---

![image](https://user-images.githubusercontent.com/55131164/147953490-dea0cee6-92dc-4589-aa27-32dccbde624f.png)

[문제 링크](https://leetcode.com/problems/find-the-town-judge/)

## 차수로 판별하기

신뢰 관계 `[a, b]`를 사람 `a`에서 사람 `b`로 향하는 방향 간선으로 보자. 마을 판사는 누구도 신뢰하지 않으므로 진출 차수가 `0`이다. 또한 나머지 모든 사람이 판사를 신뢰하므로 진입 차수는 `n - 1`이다. 두 조건을 모두 확인해야 한다. 진입 차수만으로 판별하면 다른 사람을 신뢰하는 사람을 판사로 잘못 고를 수 있다.

모든 사람의 진입 차수와 진출 차수를 센 다음 두 조건을 만족하는 사람을 반환한다. `n == 1`이면 유일한 사람의 진입·진출 차수가 모두 `0`이므로, 신뢰 관계가 없을 때에만 조건이 성립한다. 따라서 별도 분기 없이 같은 탐색으로 이 경우를 처리할 수 있다. 조건에 맞는 사람이 없으면 `-1`을 반환한다.

신뢰 관계 `m = trust.length`개를 한 번씩 처리하고 사람 `n`명을 확인하므로 시간 복잡도는 `O(n + m)`이다. 두 차수 배열의 공간 복잡도는 `O(n)`이다.

```java
class Solution {
    public int findJudge(int n, int[][] trust) {
        int[] indegree = new int[n + 1];
        int[] outdegree = new int[n + 1];

        for (int[] relation : trust) {
            outdegree[relation[0]]++;
            indegree[relation[1]]++;
        }

        for (int person = 1; person <= n; person++) {
            if (outdegree[person] == 0 && indegree[person] == n - 1) {
                return person;
            }
        }
        return -1;
    }
}
```
