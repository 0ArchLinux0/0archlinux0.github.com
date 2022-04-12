---
title: BOJ. Max-flow min-cut theorem - 최대 유량 최소 컷 정리
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [BOJ, C++]
tags: [Algorithm, Graph Threory]
pin: false
---

**\# Definition**

> the max-flow min-cut theorem states that in a flow network, the maximum amount of flow passing from the source to the sink is equal to the total weight of the edges in a minimum cut, i.e., the smallest total weight of the edges which if removed would disconnect the source from the sink.  
> [reference](https://en.wikipedia.org/wiki/Max-flow_min-cut_theorem#:~:text=In%20computer%20science%20and%20optimization,removed%20would%20disconnect%20the%20source)

<center>$flow(s,t)\_{max} = cut(s,t)\_{min}$</center>

그래프 $G$에서 최대유량 $flow(s,t)\_{max}$는 항상 source와 sink의 최소컷 $cut(s,t)\_{min}$과 같다​

---

<center>**\# Term Definition**</center>

---

**\#\# Cut \#\#**

> A cut is a partition of the vertices of a graph into two disjoint subsets  
> [reference](<https://en.wikipedia.org/wiki/Cut_(graph_theory)>)

$(S, T \\in G\_{V})$ $\\land$ $(S \\cup T = G\_{V})$ $\\land$ $(S \\cap T = \\emptyset)$

그래프 $G=(V, E)$에 대한 정점의 집합 $G\_{V}$ 를 다음을 만족하는 두개의 부분집합 $S, T$으로 나누었을때, 
$S, T$는 $G\_{V}$의 partition이며

$(S \\cap T = \\emptyset)$이므로 자명하게 disjoint subsets을 만족하게 된다.

Cut의 정의로부터 Cut-set의 개념이 파생된다.

**\#\# Cut-set \#\#**

> A cut $\\displaystyle C=(S,T)$ is a partition of ${\\displaystyle V}$ of a graph ${\\displaystyle G=(V,E)}$ into two subsets S and T.  
> The cut-set of a cut ${\\displaystyle C=(S,T)}$ is the set $\\displaystyle \\{(u,v)\\in E\\mid u\\in S,v\\in T\\}$of edges that have one endpoint in S and the other endpoint in T. If s and t are specified vertices of the graph G, then an s–t cut is a cut in which s belongs to the set S and t belongs to the set T.  
> [reference](<https://en.wikipedia.org/wiki/Cut_(graph_theory)>)

그래프 $G=(V, E)$와 Cut $C = (S, T)$를 가정하자.

이때 Cut $C = (S, T)$에 대한 Cut-set은 $\\{(u, v) \\in E \\ | \\ u \\in S, v \\in T \\}$ 로 정의된다.

다시 말해 $G$에서 모든 Cut-set의 $E$를 제거하면 partition S와 T는 서로 이어진, 혹은 흐를 수 있는 간선이 존재 하지 않게 된다.

그래프 이론에서 어느 한 간선을 선택하여 삭제하였을때 component의 개수가 증가한다면 이 간선을 *bridge, isthmus, cut-edge, cut arc*등으로 부른다.

partition에 대해 서로 흐를 수 있는 간선이 존재 하지 않는 다는 것은 bridge가 존재하지 않는다는 의미이기때문에

이와 같은 상태를 _bridgeless_ 라고 한다.

**\#\# Minimum cut \#\#**

뻔한 정의지만 가능한 모든 Cut중 가중치를 최소로 하는 Cut을 의미한다.

$Minimum \\ cut $ $= min({ cost(C) \\ | \\ \\forall C = (S, T) \\in G })$

**\#\# Maximum cut \#\#**

$Maximum \\ cut $ $= max({ cost(C) \\ | \\ \\forall C = (S, T) \\in G })$

---

**\# Proof**

_증명과정에서 포드-풀커슨 알고리즘(Ford–Fulkerson algorithm)을 사용하기 때문에 미리 이에 대한 사전 지식이 선행되어야 한다._

_#$Variable \\ Definition$_

$Network \\ G$ $=(V,E): source:\\ s,$ $sink: \\ t$

$Cut \\ C$

$S, T: $ $partition \\ of \\ G\_{V} $ $divided \\ by$ $C(s \\in S, t \\in T, S$ $and \\ T \\ has$ $no \\ more \\ than$ $1 \\ components)$

$C(S, T) $ $= \\displaystyle\\sum\_{(u, v) \\in S \\times T} c\_{uv}$

$Flow \\ f\_{uv}: $ $flow \\ from \\ u $ $to \\ v$

$|f|: \\ f\_{st}$

$Lemma \\ 1)$ $\\ |f| \\leq C\_{min}(S, T)$

$proof)$ 귀류법으로 $|f| > C\_{min}(S, T)$라고 가정해보자.

Conservation of Flows에 의해서 $|f|$ = $f\_{s\_{out}} = f\_{t\_{in}}$

마찬가지로 $ f(S\_{out}) = f\_{s\_{out}} $, $f(T\_{in}) = f\_{t\_{in}} $ 으로 부터

$|f| = f\_{s\_{out}} $ $= f\_{t\_{in}} = f(S\_{out}) $ $= f(T\_{in}) = f\_{ST}$ 가 얻어진다.

$|f| = f\_{ST} $ $= \\displaystyle\\sum\_{(u, v) \\in S \\times T} f\_{uv} $ $ \\leq $ $ \\displaystyle\\sum\_{(u, v) \\in S \\times T} c\_{uv} \\leq C\_{min}(S, T)$

이는 가정 $|f| > C\_{min}(S, T)$과 모순 됨으로 $\\ |f| \\leq C\_{min}(S, T)$임이 증명된다.

$Lemma \\ 2)$ $\\exists C:\\  min(|f|) $ $= C\_{min}(S, T)$

$proof)$ G에 대해 *Ford–Fulkerson algorithm*에서 마지막으로 갱신된(Augmented) 그래프를 $G\_{f}$라 하자.

또한, $G\_{f}$의 $s$에서 유량이 흐를 수 있는 $G\_{f}$안의 정점의 집합을 $A$라고 하자.

partition $\\left\\{S, T\\right\\} $ $= \\left\\{A, A^c\\right\\}$ 일때, $min(|f|) = C\_{min}(S, T)$ 만족함을 보이자.

1.  $\\left\\{A, A^c\\right\\}$은 $C$의 올바른 partition이다.

2.  $|f| = C\_{min}(S, T)$이다.

두가지를 보이면 될 것이다.

1번은 *Ford–Fulkerson algorithm*에 따라 $\\forall u \\in A \\subset G\_{f},$ $ v \\in G\_{V}: $ $c\_{f}(u, v)( = c(u, v) - f\_uv) $ $= 0$이여야 하므로 올바른 partition임을 알 수 있다.

$C\_{min}(S, T) $ $= \\sum\_{(u, v) \\in A \\times A^c} c\_{uv} = f\_{s\_{out}} $ $= |f|( \\because Conservation\\  of \\  Flows) =$ $ min(|f|)$

1, 2로부터 $Lemma \\ 2) $도 증명된다.

$Lemma \\ 1)$, $Lemma \\ 2)$로부터 $C\_{min}(S, T) = min(|f|)$이며 $G\_{f}$를 구함으로써 min-cut을 구성함을 있음이 증명된다.
