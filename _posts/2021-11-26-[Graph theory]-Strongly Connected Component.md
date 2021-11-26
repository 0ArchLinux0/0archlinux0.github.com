---
title: [Graph theory] Strongly Connected Component(SCC)
author: MINJUN PARK
date: 2021-11-26 9:00 +0900
categories: [Algorithm, Graph Theory]
tags:
  [
    Graph theory,
    Algorithm,
    Java,
    Strongly Connected Component,
    SCC
  ]
pin: false
---

#Strongly Connected Component 
In directed graph,
SCC means two components which are reachable in both ways 

#Algorithm (to get SCC)  
-- Kosaraju's algorithm(DFS)
-- Tarjan's algorithm


```java
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int start = 0, end = nums.length - 1, mid;
        while(start <= end) {
            mid = (start + end) / 2;
            boolean pre = false, post = false;
            if(mid > 0 && nums[mid - 1] == nums[mid]) {
                if((mid & 1) == 0) pre = true;
                else post = true;
            }
            else if(mid < nums.length - 1 && nums[mid + 1] == nums[mid]) {
                if((mid & 1) == 0) post = true;
                else pre = true;
            }
            if(pre) end = mid - 1;
            else if(post) start = mid + 1;
            else return nums[mid];
        }
        return 0;
    }
}
```