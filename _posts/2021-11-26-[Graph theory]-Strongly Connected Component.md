---
title: Graph theory. Strongly Connected Component(SCC)
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
lang: en
translation_key: strongly-connected-components
permalink: /posts/Graph-theory-Strongly-Connected-Component/
---

# Strongly Connected Components

In a directed graph, a strongly connected component (SCC) is a maximal set of vertices in which every vertex is reachable from every other vertex. Reachability is mutual: for any two vertices `u` and `v` in the same SCC, there is a directed path from `u` to `v` and a directed path from `v` to `u`. A one-way path alone does not put its endpoints in the same component.

## Tarjan's algorithm

Tarjan's algorithm finds all SCCs in one depth-first traversal. The function below accepts an adjacency list: `graph[u]` contains the 0-based vertex indices reached by outgoing edges from `u`. It returns an array of components, each of which is an array of 0-based vertex indices. Component and vertex order are not significant.

For each vertex, `index` records its discovery order and `lowLink` records the smallest discovery index reachable from it by following DFS-tree edges and, when applicable, one edge to a vertex still on the active stack. A tree edge propagates the child's `lowLink` after that child is explored. A back edge to an active vertex uses that vertex's `index`, not its `lowLink`; edges to vertices already removed from the stack are ignored. This on-stack rule prevents a completed SCC from incorrectly joining a later one.

When `lowLink[v] === index[v]`, `v` is the root of an SCC. Pop vertices from the stack through `v`; those popped vertices form one component. The outer loop starts DFS from every undiscovered vertex, so disconnected parts of the graph are included.

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

The algorithm takes `O(V + E)` time and `O(V)` auxiliary space, where `V` is the number of vertices and `E` is the number of directed edges. The implementation is recursive, so its call depth can be `O(V)`; extremely deep graphs may exceed the JavaScript runtime's call-stack limit and may need an iterative implementation.
