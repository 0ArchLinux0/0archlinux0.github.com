---
title: Nested Interval Property - 폐구간 수렴 정리
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Analysis, Nested Interval Property, 폐구간 수렴 정리, 해석학]
pin: false
---

**\# Definition of the **Nested Interval****

> Let $(I\_{n})\_{n\\in \\mathbb {N}}$ be a sequence of closed intervals of the type $I\_{n}=\\left\[a\_{n},b\_{n}\\right\]$, where $\\mid I\_{n} \\mid \\colon = b\_{n} - a\_{n}$ denotes the length of such an interval. One can call $(I\_{n})\_{n\\in \\mathbb {N}}$ a sequence of nested intervals, if  
> 1\. $\\quad \\forall n\\in \\mathbb {N} :\\;\\;I\_{n+1}\\subseteq I\_{n}$  
> 2\. $\\quad \\forall \\varepsilon$ $ >0\\;\\exists N\\in \\mathbb {N} :\\;\\; \\mid I\_{N} \\mid <\\varepsilon $  
> ([reference](https://en.wikipedia.org/wiki/Nested_intervals))

**축소구간열의 정의**

닫힌 구간 $I\_{n}=\\left\[a\_{n},b\_{n}\\right\]$ 들로 구성된 열을 $(I\_{n})\_{n\\in \\mathbb {N}}$ 라고 두자.

이때 구간의 길이는 다음과 같이 정의된다. 

$\\mid I\_{n} \\mid \\colon = b\_{n} - a\_{n}$ 

이때, $(I\_{n})\_{n\\in \\mathbb {N}}$가 축소구간열(nested interval)이기 위해서는 다음의 두가지 성질을 만족해야 한다.

1.  $\\quad \\forall n\\in \\mathbb {N} :\\;\\;I\_{n+1}\\subseteq I\_{n}$
2.  $\\quad \\forall \\varepsilon$ $ >0\\;\\exists N\\in \\mathbb {N} :\\;\\; \\mid I\_{N} \\mid <\\varepsilon $

$i < j$에 대해, $I\_{j} \\subseteq I\_{i}$, 포함관계가 성립한다.

Monotone Convergence Theorem에 따라 $\\forall i \\in \\mathbb{N},$ $\\exists sup(I\_{i}), inf(I\_{i})$

[Monotone Convergence Theorem](https://ilikechicken.tistory.com/46?category=1092797)

축소하는 구간 열이 되기 위해서 이전의 구간이 현재 구간의 상한 하한을 포함해야 한다는 것은 자명한 사실이다.

 어떤 0보다 큰 임의의 자연수 $\\forall \\varepsilon$에 대하여 항상 이 보다 작은 길이의 구간이 존재한다.

구간의 길이가 0에 수렴한다는 성질을 엡실론-델타법으로 표현한 것이다.

---

**#Axiom of completeness**

> Axiom of completeness  
> If $(I\_{n})\_{n\\in \\mathbb {N}}$ is a sequence of nested intervals,  
> there always exists a real number, that is contained in every interval $I\_{n}$.  
> In formal notation this axiom guarantees, that  
> $\\exists x\\in \\mathbb {R} :\\;x\\in \\bigcap \_{n\\in \\mathbb {N} }I\_{n}$  
> ([reference](https://en.wikipedia.org/wiki/Nested_intervals))​

완비성의 공리이다.  

축소구간열 $(I\_{n})\_{n\\in \\mathbb {N}}$에 대해 구간열의 모든 구간에 포함되는 실수 x는 항상 존재한다.

---

> Each sequence $(I\_{n})\_{n\\in \\mathbb {N} }$  
>  of nested intervals contains exactly one real number  $x$  
> ([reference](https://en.wikipedia.org/wiki/Nested_intervals))

.

완비성의 공리 $\\displaystyle \\exists x\\in \\mathbb {R} :\\;x\\in \\bigcap \_{n\\in \\mathbb {N} }I\_{n}$ 에서 얻어지는 '실수 $x$는 존재한다'에서 더 나아가 '$x$는 유일하다'라는 사실이다.

증명해보자.

축소구간열의 모든 구간에 포함되는 서로 다른 두 실수가 존재한다고 가정해보자.

$\\exists x, y \\in \\mathbb{R}, x \\neq y$ 

축소 구간열의 정의로부터

$\\forall \\varepsilon$ $ >0\\;\\exists N\\in \\mathbb {N} :\\;\\; \\mid I\_{n} \\mid <\\varepsilon $$

이때, $\\forall n \\in \\mathbb{N}, x,y \\in I\_{n}$ 이므로

$\\mid x - y \\mid < $ $\\liminf \\mid I\_{n} \\mid < \\mid I\_{n} < \\varepsilon$  
$\\varepsilon \\to 0$ 일때 $x \\to y$이므로 서로 다른 두 실수라는 조건에 모순된다.