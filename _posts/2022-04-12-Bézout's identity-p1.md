---
title: Bézout's identity - 베주 항등식(Part. 1)
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

**Bézout's identity** — Let a and b be integers or polynomials with greatest common divisor a. Then there exist integers or polynomials x and y such that ax + by = d. Moreover, the integers or polynomials of the form az + bt are exactly the multiples of d.

([reference](https://en.wikipedia.org/wiki/B%C3%A9zout%27s_identity))

---

Extended Euclidean과 연결되는 정수론의 기본 정리 중 하나이다.

증명부터 해보자.

|$Lemma \\ 1)$   $ For \\ \\forall a, b \\in \\mathbb{N} \\left(a \\neq 0 \\lor b \\neq 0 \\right)$ $\\ Let \\ S \\ = \\ \\left\\{ ax+by>0 \\ \\mid \\ x, y \\in \\mathbb{Z} \\right\\}$  $Then \\ S \\neq \\emptyset $

---

$proof)$  
$ a \\neq 0 \\lor b \\neq 0 $ 에서 일반성을 잃지않고 $ a \\neq 0 $이라 두었을 때 $ x = \\pm1, y = 0 $이라면

$\\mid ax+by\\mid=a\\neq0$, $\\mid ax+by\\mid \\in S$으로 $S$가 공집합이 아님을 알 수 있다.

**$Proposition \\ 1)$ $For \\ \\forall \\ a,b, l \\in \\mathbb{N} \\left(a \\neq 0 \\lor b \\neq 0 \\right)$ $\\ let \\ g=GCD(a,b)$, $then$ $there$ $exists$ $\\exists x,y \\in \\mathbb{Z}$ $such$ $that$ $\\ ax+by=g$**

---

**$ Let \\ S \\ = \\ \\left\\{ ax+by>0 \\ \\mid \\ x,y \\in \\mathbb{Z} \\right\\} $**

$ S \\subset \\mathbb {N} $ , $ S \\neq \\emptyset $(Lemma 1에 의해서) 가 성립하기에 Well-ordering principle에 의해 최솟값 $m \\ = \\ min\\left\\{ S \\right\\}\\left(m \\in \\mathbb{N}\\right)$ 를 잡을 수 있다( \*반드시 존재한다 ).

$ m = ax\_{1} + bY\_{1} \\left( x\_{1}, y\_{1} \\in \\mathbb {Z} \\right)$로 두고 다른 임의의 값 k를 잡자.

$let \\  \\forall k \\in S, k = ax\_{2} + by\_{2} \\left( x\_{2}, y\_{2} \\in \\mathbb{Z} \\right) $

$ k > m $ 일 것이고 $k = mq + r (0 \\leq r < m) $ 으로 표현할 수 있다.

$ \\therefore \\ k$ $= mq + r$ $= \\left(ax\_{1} + bY\_{1}\\right)q + r$

$ r $ $= k - mq $ $= \\left(ax\_{2} + by\_{2}\\right) - \\left(ax\_{1} + by\_{1}\\right) q $ $=a\\left(x\_{2} - qx\_{1}\\right) + b\\left(y\_{2} - qy\_{1}\\right) $

$ m \\not\\mid  k$ 라고 가정해보면$ r \\in \\mathbb{N} $임을 알 수 있고 $ r < m $이 성립하게 되어 m이 최솟값이라는 가정에 모순된다. 따라서 $ m \\mid k $ 가 항상 성립하게 된다.

두 가지 경우로 나눠서 생각을 해보자.

1) $ a = 0 \\lor b = 0 $

일반성을 잃지 않고 $a = 0$이라고 하면 $ GCD(a,b) = GCD(b, 0) = 0 $이 얻어지고 $ x=0 \\  and \\ y =0 $라는 쌍으로 표현 할 수 있음이 자명하다.

_(\* 본 증명에서는 증명의 간결함을 위하여 $ For \\ \\forall n \\in \\mathbb{N} \\ GCD(n, 0) = 0 $으로 정의한다.)_

2) $ a \\neq 0 \\land b \\neq 0 $

$ a \\in S \\land b \\in S$ 에서 $ m\\mid a \\land m\\mid b,\\ \\therefore m\\mid g$ 임을 알 수 있다.

$g = mG\\left(G \\in \\mathbb{Z} \\right)$라 두자.

$g $ $= mG $ $= \\left(ax\_{1} + by\_{1}\\right)G $ $= a(Gx\_{1}) + b(Gy\_{1})$ 

$x = Gx\_{1}$, $y =  Gy\_{1}$으로 표현 가능하다는 것이 얻어진다.

**$Proposition \\ 2)$ $For \\ \\forall \\ a,b,l \\in $ $\\mathbb{N} \\left(a \\neq 0 \\lor b \\neq 0 \\right)$ $ let $ $ g$ $=GCD(a,b)$, $then$ $there$ $exists$ $\\exists x,y \\in \\mathbb{Z} $ $such$ $that$ $\\ ax+by=gl$**

---

Proposition 1로부터 $g = ax\_{1} + by\_{1}$ 라 두면

$gl=\\left(ax\_{1}+by\_{1}\\right)l $ $= a\\left(lx\_{1}\\right) + b\\left(ly\_{1}\\right) $ 으로 표현이 가능함을 알 수 있다.

**$Proposition \\ 3)$ $For \\ \\forall \\ a,b,x,y l \\in $ $\\mathbb{N} \\left(a \\neq 0 \\lor b \\neq 0 \\right)$ $let \\ g=GCD(a,b)$, $then \\ g\\mid ax+by $**

---

$g\\mid x \\land g\\mid y$ 이므로 자명하게 $g\\mid ax+by$가 성립한다.

Proposition 1, 2, 3으로부터

**$Theorom \\ 1)$** **$For \\ \\forall \\ a,b,x,y l \\in $ $\\mathbb{N} \\left(a \\neq 0 \\lor b \\neq 0 \\right)$ $let \\ g=GCD(a,b)$, $S$ $= \\ \\left\\{ax+by>0 \\ \\mid  \\ x,y \\in \\mathbb{Z} \\right\\}$ $T\\ = \\ \\left\\{Mg \\ \\mid  \\ M \\in \\mathbb{Z} \\right\\}$, $then \\ S = T $** 

---

이로써 정수에 대한 Bézout's identity의 증명이 완료된다.

일반적인 확장은 Part.2에서, 다항식으로의 확장은 Part.3에서 마저 기술하겠다.

