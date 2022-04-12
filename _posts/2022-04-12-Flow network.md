---
title: Flow network - 네트워크 플로우
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Graph Threory, Flow network]
tags:
  [
    Algorithm,
    Graph Threory,
    Max-flow min-cut,
    Ford–Fulkerson algorithm,
    flow network,
    네트워크 플로우,
  ]
pin: true
---

**\# Definition**

> A flow network (also known as a transportation network) is a directed graph where each edge has a capacity and each edge receives a flow. The amount of flow on an edge cannot exceed the capacity of the edge  
> [reference](https://en.wikipedia.org/wiki/Flow_network#:~:text=In%20graph%20theory%2C%20a%20flow,the%20capacity%20of%20the%20edge.)

플로우 네트워크 G는 모든 간선이 용량과 유량의 속성을 가지는 그래프이며 유량은 항상 용량을 초과할 수 없다.

---

**\# Term Definition**

---

**\#\# Flows \#\#**

flow를 정의하는 방법(notation)은 다양하다. 이 중 가장 직관적이며 대표적인 의사-유량(pseudo-flow라서 대충 의사-유량으로 번역하겠다)의 notation은 다음과 같다.  

> pseudo-fow는 함수 $f: \\ V \\times V \\Rightarrow \\mathbb{R}$ 이며  
> $\\forall u, v \\in V$에 다음과 같은 성질을 만족한다.  
> 1) Capacity constraints  
> 2) Skew symmetry  
> 3) Flow conservation  
> 4) Value(f)
  
_1._

1번의 성질은  
|$\\forall(u, v) \\in E:$ $f(u, v) \\leq c(u,v)$  
으로 기본 공리같은 느낌이며 직관적으로도 자명하다.

_2._

Skew symmetric 하다는 것은 Skew-symmetric matrix과 같은 형식으로 표현 할 수 있다는 것이다.

Skew-symmetric matrix란 $A^T = -A$를 만족하는 Square matrix(정방행렬)을 의미한다.  
(이에 따라 $f\_{uu} = 0$도 성립하게 된다)

이와 비슷하게 flow에 대해서도 다음과 같은 성질이 성립한다.
|$f\_{uv} = -f\_{vu}$

정점 u에서 v로 유량이 흐르것과 v에서 u로 같은 양의 다른 부호의 유량이 흐르는 것을  
동치로 취급할 수 있게 되면 flow analysis의 직관성, 논리의 흐름이 굉장히 깔끔해진다  
(예를 들어 유량을 더 흘려보내주는 과정에서 $f\_{uv}$와 $f\_{wx}$가 각각 $\\Delta f\_{uv}$만큼 감소, $\\Delta f\_{uv}$만큼 증가하는 경우를 생각해보자.   $f\_{uv}-\\Delta f\_{uv}$, $f\_{wx}+\\Delta f\_{wx}$로 표현한다면  
 표현의 통일성이 없고 $\\Delta f(s, t)$의 값의 표현이 깔끔하지 않은 반면  
성질 2를 이용하여 표현한다면  
 $f\_{uv}+\\Delta f\_{vu}$, $f\_{wx}+\\Delta f\_{wx}$로  
통일성 있게 표현되기에 $\\Delta f(s, t)$의 표현도 깔끔해진다.)

혹여 $u$에서 $v$로 $E\_{uv} = 6$, $v$에서 $u$로 $E\_{vu} = 2$인 경우 $f\_{uv} = -f\_{vu}$가 성립 안하는게 아니냐는 질문에 대한 대답은 다음과 같다.  
flow analysis에서 논리의 흐름을 위하여 $f\_{uv}$는 단순히 u에서 v로 직접적으로(다른 정점을 거치지 않고) 흐르는 유량 뿐만 아닌 가능한 모든 경로에 대한 유량의 합을 뜻한다.

다시 말하자면 모든 $u \\Rightarrow v$로 갈 수 있는 경로들의 집합  
$P(u,v) = \\left\\{p \\ \\mid \\ p: \\ path \\ from \\ u \\ to \\ v}$  
에 대해서 유량  
$f\_{uv}$ $=\\displaystyle\\sum\_{path \\ p \\in P} f(p)$  
으로 표현 할 수 있다.

따라서 $u$에서 $v$로 $E\_{uv} = 6$, $v$에서 $u$로 $E\_{vu} = 2$인 경우  
$f\_{uv} = 6 -2 = 4 = -(2 - 6) = -f\_{vu}$이 성립하게 되는 것이다.

_3._

Flow conservation은 정점 $v$로 들어오는 유량의 합의 절댓값과 나가는 유량의 합은 동일 하다는 성질이다.

|$f\_{v\_{in}}=f\_{v\_{out}}$

$f\_{v\_{in}} = \\displaystyle\\sum\_{u: (u, v) \\in E} f\_{uv}$ $=    \\displaystyle\\sum\_{u: (u, v) \\in E} -f\_{vu}$ $= \\displaystyle\\sum\_{w: (w, v) \\in E} -f\_{vw}$ $=f\_{v\_{out}}$  
를 얻을 수 있다.

_4._

네트워크에서 유량의 값(value of flow)에 관한 성질이며 다음과 같다.  
|$\\mid f\\mid = f\_{s\_{out}} = f\_{t\_{in}}$ 

s에서 직접적으로(다른 정점을 거치지 않고) 연결되어있는 점들의 집합을 $DS=\\left\\{u\\ \\mid \\ u: \\ directly \\ connected \\ from \\ s\\right\\}$라고 하자.

Flow conservation에 의해  
$\\displaystyle\\sum\_{u \\in V, u \\neq (s, t)}f\_{u\_{in}} - f\_{u\_{out}} = 0$

$\\Rightarrow$ $0 = \\displaystyle\\sum\_{u \\in V, u \\neq (s, t)}(\\displaystyle\\sum\_{v: v \\Rightarrow u} f\_{vu}- \\displaystyle\\sum\_{w: u \\Rightarrow v} f\_{uw})= 0$

$=\\displaystyle\\sum\_{(u, v) \\in V, u, v \\neq (s, t)}f\_{uv} - f\_{uv}$ $+\\displaystyle\\sum\_{u: s\\Rightarrow u}f\_{su}$ $-\\displaystyle\\sum\_{u: u\\Rightarrow t}f\_{ut}

$=0$+\\displaystyle\\sum\_{u: s\\Rightarrow u}f\_{su}$ $-\\displaystyle\\sum\_{u: u\\Rightarrow t}f\_{ut}

$= f\_{s\_{out}} - f\_{t\_{in}}$

$\\therefore f\_{s\_{out}} = f\_{t\_{in}}\\  Q.E.D$

**\## Residual capacity **##****

> The residual capacity of an arc with respect to a pseudo-flow $f$, denoted $c\_{f}$, is the difference between the arc's capacity and its flow. That is, $c\_{f} (e) = c(e) - f(e)$  
> [reference](https://en.wikipedia.org/wiki/Flow_network)

잔류 용량이란 $c\_{f}$으로 표기하며 얼마만큼의 유량이 더 흐를 수 있는지를 뜻한다.  
용량이 유량의 최댓값이므로 잔류 용량은 
|$c\_{f} (e) = c(e) - f(e)$
으로 표현된다.

**\## Residual network **##****

> A residual network, denoted $G\_{f} (V, E\_{f})$, which models the amount of available capacity on the set of arcs in $G = (V, E)$  
> [reference](https://en.wikipedia.org/wiki/Flow_network)

잔류 네트워크란 잔류 용량에 초점을 맞추어 구성한 그래프이다.  
$G=(V,E)$라면 간선 $E\_{uv} = $c\_{f}(u, v)$로 표현 된다.

**## Augmenting paths **##****

> An augmenting path is a path $(u\_{1}, u\_{2}, ..., u\_{k})$ in the residual network, where $u\_{1} = s, u\_{k} = t$, and $c\_{f} (u\_{i}, u\_{i} + 1) > 0. A network is at maximum flow if and only if there is no augmenting path in the residual network $G\_{f}$.  
> [reference](https://en.wikipedia.org/wiki/Flow_network)

Augmenting path는 번역하면 증가 경로정도가 되겠다.   
잔류 네트워크 $G\_{f}$에 대해서 $s$에서 출발하여 $t$로 흐를 수 있는 경로가 Augmenting path이다. 

경로는 다음과 같이 표현 할 수 있다.

$(u\_{1}, u\_{2}, ..., u\_{k})$ $\\subset G$,$u\_{1} = s, u\_{k} = t$, $c\_{f} (u\_{i}, u\_{i} + 1) > 0$

증가 경로가 존재한다면 유량 $|f(G\_{f})|$은 최댓값이 아니며  
증가 경로가 존재하지 않을때 $|f(G\_{f})|$이 최댓값, 즉 maximum flow가 된다.  
역 또한 성립한다.  
이의 증명은 _Ford–Fulkerson algorithm_의 포스팅에서 기술하겠다.