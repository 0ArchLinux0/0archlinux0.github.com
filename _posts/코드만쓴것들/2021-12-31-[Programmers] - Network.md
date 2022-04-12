---
title: Programmers. Network
author: MINJUN PARK
date: 2021-12-31 00:30:00 +0900
categories: [Programmers, JavaScript]
tags:
  [
    Algorithm,
    JavaScript,
    Coding Interview,
    Programmers,
    Network,
    네트워크,
    프로그래머스
  ]
pin: false
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/43162>

<br>

```javascript
function solution(n, computers) {
    let cnt = 0;
    const visit = new Array(computers.length).fill(false);
    
    for(let i = 0; i < n; i++) {
        if(!visit[i]) {
            cnt++;
            dfs(computers, visit, i, n);
        }
    }
    return cnt;
}

function dfs(computers, visit, idx, len) {
    visit[idx] = true;
    for(let i = 0; i < len; i++) {
        if(computers[idx][i] == 1 && !visit[i] && i != idx) 
            dfs(computers, visit, i, len);
    }
}
```
