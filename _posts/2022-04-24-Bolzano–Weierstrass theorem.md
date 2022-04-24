---
title: Bolzano–Weierstrass theorem - 볼차노-바이어슈트라스 정리
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Number Theory]
tags:
  [
    Math,
    Analysis,
    Bolzano–Weierstrass theorem,
    볼차노-바이어슈트라스 정리,
    해석학,
  ]
pin: true
---

**\# Definition**

> Bolzano–Weierstrass theorem — result about convergence in a finite-dimensional Euclidean space Rn. The theorem states that each bounded sequence in Rn has a convergent subsequence.  
> ([reference](https://en.wikipedia.org/wiki/Bolzano%E2%80%93Weierstrass_theorem))

**볼차노-바이어슈트라스 정리란** 유클리드 공간에서 유계 닫힌집합과 점렬 콤팩트 공간의 개념이 일치한다는 정리이다. ([reference](https://ko.wikipedia.org/wiki/%EB%B3%BC%EC%B0%A8%EB%85%B8-%EB%B0%94%EC%9D%B4%EC%96%B4%EC%8A%88%ED%8A%B8%EB%9D%BC%EC%8A%A4_%EC%A0%95%EB%A6%AC#:~:text=%ED%95%B4%EC%84%9D%ED%95%99%EA%B3%BC%20%EC%9D%BC%EB%B0%98%EC%9C%84%EC%83%81%EC%88%98%ED%95%99,%EC%9D%B4%20%EC%9D%BC%EC%B9%98%ED%95%9C%EB%8B%A4%EB%8A%94%20%EC%A0%95%EB%A6%AC%EC%9D%B4%EB%8B%A4.))

---

해석학과 위상수학에서 매우 중요하며 많이 쓰이는 정리이다.

Definition의 정의부터 보도록하자.

> 유클리드 공간에서 유계 닫힌집합과 점렬 콤팩트 공간의 개념이 일치한다

유클리드 공간에서 유계의 닫힌 집합이란 $R$의 Power Set으로 구성된 공간에 속하는 bounded(limit superior과 limit inferior가 존재) set이며 limit superior와 limit inferior를 구간에 포함하지 않는(closed) 집합을 의미한다.

반면, 점렬 콤팩트 공간(sequentially compact space)이란 공간의 임의의 점렬을 잡았을때, 항상 수렴하는 부분 점렬을 가지는 위상 공간을 뜻한다.

따라서, 위의 문장은

> 유클리드 공간에 속하며 유계이고 닫힌 집합에 대해  
> 임의의 부분점렬 $U$를 잡는다면 $U$는 항상 수렴하는 부분 점렬을 포함한다.

라고 표현될 것이다.

---

증명해보자.

본증명에서 볼차노-바이어슈트라스 정리의 증명은 폐구간 수렴 정리를 이용한다.

[https://ilikechicken.tistory.com/47](https://ilikechicken.tistory.com/47)

[Nested Interval Property - 폐구간 수렴 정리

\# Definition of the Nested Interval Let $(I\_{n})\_{n\\in \\mathbb {N}}$ be a sequence of closed intervals of the type $I\_{n}=\\left\[a\_{n},b\_{n}\\right\]$, where $\\mid I\_{n} \\mid \\colon = b\_{n} - a\_{n}$ d..

ilikechicken.tistory.com](https://ilikechicken.tistory.com/47)

수열 $(a\_n)\_{n \\in \\mathbb{N}}$를 생각하자. 집합 $\\left\\{a\_{n}\\right\\}$ 은 유계이므로

$\\exists M \\in \\mathbb{Z}$ $i.e)$ $M = max(\\mid sup\_{n} \\mid,  \\mid inf\_{n} \\mid)$

를 만족한다.

$\\forall n \\in \\mathbb{N}, a\_{n} \\in \\left\[-M, M\\right\]$

구간을 $\\left\[-M, 0\\right\]$, $\\left\[0, M0\\right\]$ 와 같이 반으로 나누었을때 둘 다 유한개의 원소를 가진다면

수열 $(a\_n)\_{n \\in \\mathbb{N}}$가 무한집합이라는 것에 모순됨으로 최소한 하나는 무한집합일 것이다.

일반성을 잃지 않고 무한집합인 구간(둘다 무한 집합일 경우에 어느 쪽이던 상관없다)을 골라 이를 $I\_{0}$라 하자.

$I\_{0}$에 대하여 위의 동작을 다시 한번 수행해서 얻어지는 구간을 $I\_{1}$, $I\_{k-1}$에 대해 위의 동작을 수행한 후 얻어지는 구간을 $I\_{k}$으로 정의해보자.

이때, 너무나도 자명하게 $\\forall n\\in \\mathbb {N} :\\;\\;I\_{n+1}\\subseteq I\_{n}$가 성립함을 알 수 있고

$\\forall n\\in \\mathbb {N} :\\;\\; \\mid I\_{n+1} \\mid = \\frac{\\mid I\_{n} \\mid}{2}$

그리고 $\\mid I\_{0} \\mid \\in \\mathbb{Z}$ 으로부터 $\\forall \\varepsilon$ $ >0\\;\\exists N\\in \\mathbb {N} :\\;\\; \\mid I\_{N} \\mid <\\varepsilon $ 도 성립한다.

따라서 $(I\_{n})\_{n\\in \\mathbb {N}}$은 Nested Interval(축소 구간열)이 되고 Nested Interval Property에 따라

> Each sequence $(I\_{n})\_{n\\in \\mathbb {N} }$  
>  of nested intervals contains exactly one real number  $x$  
> ([reference](https://en.wikipedia.org/wiki/Nested_intervals))

수렴하는 부분 점렬을 가짐이 보여진다.
