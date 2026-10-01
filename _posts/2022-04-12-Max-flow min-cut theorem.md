---
title: Max-flow min-cut theorem - 최대 유량 최소 컷 정리
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph theory, Flow network]
tags:
  [
    Algorithm,
    Graph Threory,
    Max-flow min-cut,
    mfmc,
    flow network,
    네트워크 플로우,
    그래프
  ]
pin: true
lang: en
translation_key: max-flow-min-cut-theorem
---

## The theorem

Let $G=(V,E)$ be a finite directed network with distinct source $s$ and sink $t$. Each directed edge $e$ has a finite, nonnegative capacity $c_e$. A feasible flow assigns a value $f_e$ to every edge such that

$$
0\leq f_e\leq c_e
$$

and, at every vertex other than $s$ and $t$, total incoming flow equals total outgoing flow. The value $|f|$ is the net flow leaving $s$ (equivalently, the net flow entering $t$).

An $s$–$t$ cut is a partition $V=S\mathbin{\dot\cup}T$ with $s\in S$ and $t\in T$. Its capacity is the sum of capacities of the original directed edges whose tail is in $S$ and head is in $T$:

$$
c(S,T)=\sum_{e=(u,v)\in E:\ u\in S,\ v\in T} c_e.
$$

The max-flow min-cut theorem states that the maximum value of a feasible flow equals the minimum capacity among all $s$–$t$ cuts.

## Proof

First, consider any feasible flow $f$ and any $s$–$t$ cut $(S,T)$. Summing flow conservation over the vertices in $S$ cancels flows on edges with both ends in $S$ and gives

$$
|f|=\sum_{e=(u,v):\,u\in S,v\in T}f_e-\sum_{e=(u,v):\,u\in T,v\in S}f_e.
$$

The second sum is nonnegative, while each term in the first sum is at most its edge's capacity. Hence $|f|\leq c(S,T)$. This holds for every feasible flow and every cut.

Because the network is finite and all capacities are finite, the feasible-flow set is a nonempty compact subset of a finite-dimensional space, so a maximum flow $f$ exists. Form its residual graph: for each original edge $u\to v$, a forward residual arc has capacity $c_e-f_e$, and a reverse residual arc $v\to u$ has capacity $f_e$ (residual arcs are associated with their original edges). Let $S$ be the vertices reachable from $s$ by positive-capacity residual arcs, and let $T=V\setminus S$. Since $f$ is maximum, $t$ is not reachable: otherwise a residual $s$–$t$ path could be augmented by a positive amount, increasing the flow value.

There is no positive-capacity residual arc from $S$ to $T$. Therefore every original edge directed from $S$ to $T$ is saturated, so $f_e=c_e$. Also, for every original edge directed from $T$ to $S$, its reverse residual arc goes from $S$ to $T$; it must have zero residual capacity, so $f_e=0$. Substituting these facts into the cut identity above gives

$$
|f|=\sum_{e=(u,v):\,u\in S,v\in T}c_e=c(S,T).
$$

Thus this maximum flow equals the capacity of this particular cut. Since every flow is at most every cut capacity, this flow is maximum and this cut is minimum, and their values are equal. ∎