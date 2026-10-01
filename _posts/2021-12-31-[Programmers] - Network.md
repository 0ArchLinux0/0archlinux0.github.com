---
title: Programmers. Network
author: MINJUN PARK
date: 2021-12-31 00:30:00 +0900
categories: [Record, Code]
tags:
  [
    Algorithm,
    JavaScript,
    Coding Interview,
    Programmers,
    Network,
    네트워크,
    프로그래머스,
  ]
pin: false
lang: en
translation_key: programmers-network-components
permalink: /posts/Programmers-Network/
---

[Problem](https://programmers.co.kr/learn/courses/30/lessons/43162)

## Approach

The computers form an undirected graph: `computers[i][j] === 1` means computer `i` can connect directly to computer `j`. The requested number of networks is the number of connected components.

Scan every computer. Whenever one has not yet been visited, it starts a new component. Mark it visited and use an explicit stack to visit every unvisited computer reachable through a connection. Mark each computer when it is pushed, so it can enter the stack only once. The diagonal entries represent links from a computer to itself; they do not discover another computer and are harmless because the current computer is already marked visited.

This counts each component exactly once: the first unvisited computer encountered in a component starts its traversal, and that traversal marks every computer in that component. Afterward, none of its members can start another traversal. Conversely, every computer connected to that start is reached by repeatedly following edges, so no member of the component is missed.

The adjacency matrix takes `O(n²)` time to inspect, and each computer is pushed and popped at most once. Thus total time is `O(n²)` and the auxiliary stack and visited array use `O(n)` space.

## JavaScript

```javascript
function solution(n, computers) {
  const visited = new Array(n).fill(false);
  const stack = [];
  let networkCount = 0;

  for (let start = 0; start < n; start++) {
    if (visited[start]) continue;

    networkCount++;
    visited[start] = true;
    stack.push(start);

    while (stack.length > 0) {
      const current = stack.pop();

      for (let next = 0; next < n; next++) {
        if (computers[current][next] === 1 && !visited[next]) {
          visited[next] = true;
          stack.push(next);
        }
      }
    }
  }

  return networkCount;
}
```
