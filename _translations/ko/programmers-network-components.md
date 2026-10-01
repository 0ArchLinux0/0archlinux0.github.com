---
title: Programmers. 네트워크
author: MINJUN PARK
date: 2021-12-31 00:30:00 +0900
categories: [Record, Code]
tags: [Algorithm, JavaScript, Coding Interview, Programmers, Network, 네트워크, 프로그래머스]
pin: false
lang: ko
translation_key: programmers-network-components
permalink: /ko/posts/programmers-network-components/
source_permalink: /posts/Programmers-Network/
---

[문제 링크](https://programmers.co.kr/learn/courses/30/lessons/43162)

## 풀이

컴퓨터들을 정점, 컴퓨터 사이의 직접 연결을 간선으로 보면 무방향 그래프가 됩니다. `computers[i][j] === 1`은 `i`번 컴퓨터와 `j`번 컴퓨터가 직접 연결되어 있다는 뜻입니다. 문제에서 구하는 네트워크 수는 이 그래프의 연결 요소 개수입니다.

모든 컴퓨터를 순서대로 확인하면서 아직 방문하지 않은 컴퓨터를 발견할 때마다 네트워크 수를 하나 늘리고, 명시적인 스택을 이용해 그 컴퓨터에서 도달할 수 있는 모든 컴퓨터를 방문합니다. 컴퓨터를 스택에 넣는 순간 방문 표시를 하므로 같은 컴퓨터가 중복해서 들어가지 않습니다. 대각 원소는 컴퓨터와 자기 자신의 연결을 나타냅니다. 현재 컴퓨터는 이미 방문 처리되어 있으므로 대각선의 자기 연결은 다른 컴퓨터를 방문시키지 않아 결과에 영향을 주지 않습니다.

각 연결 요소에서 처음 발견한 미방문 컴퓨터만 탐색을 시작하므로 요소마다 네트워크 수를 정확히 한 번 증가시킵니다. 탐색은 연결 간선을 따라 반복되므로 시작점과 연결된 모든 컴퓨터를 방문하고, 방문 후에는 그 요소의 어떤 컴퓨터도 다시 탐색을 시작하지 않습니다. 따라서 연결 요소가 빠지거나 중복 계산되지 않습니다.

인접 행렬의 각 행을 확인하므로 시간 복잡도는 `O(n²)`입니다. 각 컴퓨터는 최대 한 번 스택에 들어가므로 방문 배열과 스택의 추가 공간 복잡도는 `O(n)`입니다.

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
