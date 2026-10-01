---
title: 그래프 이론. 강한 연결 요소(SCC)
author: MINJUN PARK
date: 2021-11-26 9:00 +0900
categories: [Record, Code]
tags:
  [
    Graph theory,
    Algorithm,
    Strongly Connected Component,
    SCC,
    Tarjan's algorithm,
    Kosaraju's algorithm,
  ]
pin: false
lang: ko
translation_key: strongly-connected-components
permalink: /ko/posts/strongly-connected-components/
---

# 강한 연결 요소

방향 그래프에서 강한 연결 요소(SCC)는 모든 정점이 서로 도달 가능한 정점들의 최대 집합입니다. 서로 도달 가능하다는 것은 양방향 도달을 뜻합니다. 같은 SCC에 속한 임의의 두 정점 `u`와 `v`에 대해 `u`에서 `v`로 가는 방향 경로와 `v`에서 `u`로 가는 방향 경로가 모두 존재합니다. 한쪽 방향으로만 경로가 있다고 해서 두 정점이 같은 요소에 속하는 것은 아닙니다.

## 타잔 알고리즘

타잔 알고리즘은 한 번의 깊이 우선 탐색으로 모든 SCC를 찾습니다. 아래 함수는 인접 리스트를 입력으로 받습니다. `graph[u]`에는 정점 `u`에서 나가는 간선이 도달하는 0부터 시작하는 정점 인덱스가 들어 있습니다. 반환값은 각 요소가 0부터 시작하는 정점 인덱스 배열인 배열입니다. 요소와 정점의 순서는 중요하지 않습니다.

각 정점에서 `index`는 발견 순서를 기록하고, `lowLink`는 DFS 트리 간선을 따라가고 필요한 경우 아직 활성 스택에 남아 있는 정점으로 간선을 하나 따라갔을 때 도달할 수 있는 가장 작은 발견 인덱스를 기록합니다. 자식 탐색이 끝나면 트리 간선은 자식의 `lowLink` 값을 전달합니다. 활성 정점으로 향하는 역방향 간선은 그 정점의 `lowLink`가 아니라 `index`를 사용합니다. 이미 스택에서 제거된 정점으로 향하는 간선은 무시합니다. 이 스택 포함 여부 규칙은 탐색이 끝난 SCC가 이후 SCC에 잘못 합쳐지는 것을 방지합니다.

`lowLink[v] === index[v]`이면 `v`는 SCC의 루트입니다. 스택에서 `v`가 나올 때까지 정점을 꺼내면, 꺼낸 정점들이 하나의 요소를 이룹니다. 바깥 반복문은 아직 발견하지 않은 모든 정점에서 DFS를 시작하므로 그래프의 서로 분리된 부분도 빠짐없이 처리됩니다.

```javascript
function stronglyConnectedComponents(graph) {
  const n = graph.length;
  const index = Array(n).fill(-1);
  const lowLink = Array(n).fill(0);
  const onStack = Array(n).fill(false);
  const stack = [];
  const components = [];
  let nextIndex = 0;

  function visit(v) {
    index[v] = nextIndex;
    lowLink[v] = nextIndex;
    nextIndex += 1;
    stack.push(v);
    onStack[v] = true;

    for (const w of graph[v]) {
      if (index[w] === -1) {
        visit(w);
        lowLink[v] = Math.min(lowLink[v], lowLink[w]);
      } else if (onStack[w]) {
        lowLink[v] = Math.min(lowLink[v], index[w]);
      }
    }

    if (lowLink[v] === index[v]) {
      const component = [];
      let w;
      do {
        w = stack.pop();
        onStack[w] = false;
        component.push(w);
      } while (w !== v);
      components.push(component);
    }
  }

  for (let v = 0; v < n; v += 1) {
    if (index[v] === -1) visit(v);
  }

  return components;
}
```

정점 수를 `V`, 방향 간선 수를 `E`라고 할 때 시간 복잡도는 `O(V + E)`, 보조 공간 복잡도는 `O(V)`입니다. 이 구현은 재귀를 사용하므로 호출 깊이가 `O(V)`까지 늘어날 수 있습니다. 매우 깊은 그래프에서는 JavaScript 실행 환경의 호출 스택 한도를 넘을 수 있으므로 반복형 구현이 필요할 수 있습니다.
