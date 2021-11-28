---
title: Graph theory. Strongly Connected Component(SCC)
author: MINJUN PARK
date: 2021-11-26 9:00 +0900
categories: [Algorithm, Graph Theory]
tags:
  [
    Graph theory,
    Algorithm,
    Strongly Connected Component,
    SCC,
    Tarjan's algorithm,
    Kosaraju's algorithm
  ]
pin: true
---

#Strongly Connected Component 
In directed graph,
SCC means two components which are reachable in both ways 

#Algorithm (to get SCC)  
-- Kosaraju's algorithm(DFS)
-- Tarjan's algorithm

#Tarjan's algorithm(DFS)

```javascript
/**
 edges = {
   ...fromVertex: [ ... toVertex ]  
 }
*/
stack_global // stack
sccIdx = 1; //to distinguish the node we haven't visit. 
parent // parent sccIdx of node
end // if formed an SCC and poped out of stack
for(vertex of vertexs) if(parent[vertex] != 0) dfs(vertex);

function dfs(vertex) {
  parent[vertex] = tmpParent = sccIdx++; //every time dfs is called, sccIdx increase and the value is idential
  stack_global.push(vertex);
  for(toVertex of edges[vertex]) {
    if(parent[toVertex] !== 0) tmpParent = min(tmpParent, dfs(toVertex));
    else if(!end[toVertex]) tmpParent = min(tmpParent, parent[toVertex]);
   }

  if(tmpParent == parent[vertex]) { //pop out nodes that form one SCC group
    while(true) {
      top = stack.pop();
      localSCC.push(top);
      end[top] = true; 
      /*  
      the reason we don't do this before return this function
      lets' assume we do this before return and 
      think of dfs called: 
      parent => ...node1 => node2=> parent
      then, parent[node2] = parent.SCCIdx, end[parent] = true;
      back to node1, since end[node2] = true, node1.SCCIdx is not updated to node2.SCCIdx which is parent.SCCIdx
      */
      if(top == vertex) break;
    }
  }
  return tmpParent;
}
```

#Kosaraju's algorithm(DFS)

```javascript
/**
 edges = {
   ...fromVertex: [ ... toVertex ]  
 }
*/
stack_global
sccIdx = 1; //to distinguish the node we haven't visit. 
visit 
end // if formed an SCC and poped out of stack
rev_edges // reverse directed graph
for(vertex of vertexs) if(parent[vertex] != 0) visitDFS(vertex, stack_global, edges);
//initialize visit to false
while(!stack_global.isEmpty()) {
  top = stack_global.pop();
  if(!visit[top]) visitDFS(top, SCC, rev_edges);
}

function visitDFS(vertex, stack, edges) {
  stack.push([vertex, sccIdx++]);
  visit[vertex] = true;
  for(toVertext of edges[vertex]) if(!visit[toVertex]) visitDFS(toVertex, stack);
}
```
