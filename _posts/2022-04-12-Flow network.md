---
title: Flow network - 네트워크 플로우
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph theory, Flow network]
tags:
  [
    Algorithm,
    Graph Threory,
    Max-flow min-cut,
    Ford–Fulkerson algorithm,
    flow network,
    네트워크 플로우,
    그래프,
  ]
pin: true
lang: en
translation_key: flow-network
---

## Flow networks and feasible flows

A **flow network** is a finite directed graph $G=(V,E)$ with a distinguished source $s$ and sink $t$, where $s\ne t$. Each edge $e$ has a finite, nonnegative capacity $c_e$. Edges are treated as distinct objects, even when they have the same endpoints; this matters when the network contains parallel or antiparallel edges.

A **flow** assigns a real number $f_e$ to every original edge $e$ and is feasible when it satisfies the capacity constraints

$$
0\le f_e\le c_e \qquad(e\in E)
$$

and flow conservation at every vertex other than $s$ and $t$:

$$
\sum_{e:\,\operatorname{head}(e)=v} f_e
=\sum_{e:\,\operatorname{tail}(e)=v} f_e
\qquad(v\in V\setminus\{s,t\}).
$$

The **value** of a feasible flow is its net flow out of the source,

$$
|f|=\sum_{e:\,\operatorname{tail}(e)=s} f_e
   -\sum_{e:\,\operatorname{head}(e)=s} f_e.
$$

Summing conservation over all vertices other than $s$ and $t$ shows that this is also the net flow into the sink:

$$
|f|=\sum_{e:\,\operatorname{head}(e)=t} f_e
   -\sum_{e:\,\operatorname{tail}(e)=t} f_e.
$$

Thus flow value is not simply the sum on all edges leaving $s$ or entering $t$ if edges can point into $s$ or out of $t$.

## Residual arcs and residual networks

Given a feasible flow $f$, each original edge $e=(u,v)$ gives rise to up to two **residual arcs**, each labeled by its original edge:

- The forward residual arc $(u,v,e,+)$ has residual capacity $c_e-f_e$. It represents the additional flow that can be sent along $e$.
- The reverse residual arc $(v,u,e,-)$ has residual capacity $f_e$. It represents the amount of existing flow on $e$ that can be canceled, or sent back, by reducing $f_e$.

Only arcs with positive residual capacity are available in the residual network. These are not interchangeable with original edges: in particular, a reverse residual arc belonging to $e=(u,v)$ remains distinct from an original edge in the opposite direction $v\to u$. If both original directions exist, each contributes its own labeled forward and reverse residual arcs.

## Cuts bound every feasible flow

An $s$–$t$ cut is a partition $V=S\mathbin{\dot\cup}T$ with $s\in S$ and $t\in T$. Its capacity is the sum of the capacities of original edges directed from $S$ to $T$:

$$
c(S,T)=\sum_{e=(u,v)\in E:\,u\in S,\ v\in T}c_e.
$$

For any feasible flow, summing net outflow over the vertices in $S$ gives

$$
|f|=\sum_{e=(u,v)\in E:\,u\in S,\ v\in T}f_e
    -\sum_{e=(u,v)\in E:\,u\in T,\ v\in S}f_e
    \le c(S,T).
$$

Thus every feasible flow is at most the capacity of every $s$–$t$ cut.

## Augmenting paths

An **augmenting path** is a path from $s$ to $t$ in the residual network, using only positive-capacity residual arcs. Its bottleneck capacity is the minimum residual capacity along that path. Sending an amount $\Delta$ equal to this minimum augments the flow: for each forward residual arc belonging to $e$, replace $f_e$ by $f_e+\Delta$; for each reverse residual arc belonging to $e$, replace $f_e$ by $f_e-\Delta$. The resulting flow remains within every edge's capacity bounds, conserves flow at intermediate vertices, and has value $|f|+\Delta$.

A feasible flow is a **maximum flow if and only if** its residual network has no augmenting path from $s$ to $t. This is a statement about the current flow and its residual network, not merely about the original graph. If a path exists, its positive bottleneck gives a feasible flow of strictly greater value. Conversely, when no such path exists, the vertices reachable from $s$ by positive-capacity residual arcs define an $s$–$t$ cut whose forward crossing edges are saturated and whose backward crossing edges carry zero flow; the flow value equals that cut's capacity, so no flow can have a larger value.

This condition characterizes optimality, but it does not by itself guarantee that every path-selection procedure reaches that condition after finitely many augmentations. Starting from the zero flow, with integer capacities, Ford–Fulkerson augmentations preserve integer flows and increase the integer flow value by at least one each time; the value is bounded by the finite capacity leaving $s$, so the process terminates. Rational capacities can be scaled to integers and have the same guarantee. With arbitrary real capacities, Ford–Fulkerson path choices may produce infinitely many augmentations without reaching a maximum flow in finite time.
