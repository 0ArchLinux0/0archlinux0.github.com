---
title: Monotone Convergence Theorem - 단조 수렴 정리
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Analysis, Monotone Convergence Theorem, 단조 수렴 정리,해석학]
pin: true
---

**\# Definition**

> In the mathematical field of real analysis, the monotone convergence theorem is any of a number of related theorems proving the convergence of monotonic sequences (sequences that are non-decreasing or non-increasing) that are also bounded. Informally, the theorems state that if a sequence is increasing and bounded above by a supremum, then the sequence will converge to the supremum; in the same way, if a sequence is decreasing and is bounded below by an infimum, it will converge to the infimum.  
> ([reference](https://en.wikipedia.org/wiki/Monotone_convergence_theorem))

실해석학에서 Monotone Convergence Theorem이란 어떤 단조 수열(monotonic sequences)의 수렴성을 증명하는 과정에서 등장하는 모든 이론을 총칭한다.

따라서 일반적인 경우 Monotone Convergence Theorem이라 함은 다음을 칭하는 경우가 많다.

> Informally, the theorems state that if a sequence is increasing and bounded above by a supremum, then the sequence will converge to the supremum; in the same way, if a sequence is decreasing and is bounded below by an infimum, it will converge to the infimum.  
> ([reference](https://en.wikipedia.org/wiki/Monotone_convergence_theorem))

> 단조 증가 수열이며 상계가 존재한다면 이 수열은 상한으로 수렴한다.   
> 마찬가지로, 단조 감소 수열이며 하계가 존재한다면 하한으로 수렴한다.

라는 정리이다.

---

증명해보자.

단조 증가 수열과 단조 감소 수열의 증명은 동일한 논리의 흐름을 따라 가기에 단조 증가 수열에 대해서만 증명하겠다.

공집합이 아니며 상계가 존재하는 단조 증가 수열 $(a\_n)\_{n \\in \\mathbb{N}}$ 의 원소들로 이루어진 집합 $\\left\\{a\_{n}\\right\\}$를 생각하자.

이때 실수의 완비성 공리(completeness of the real numbers)에 의해(또는 최소 상계 성질, Least-upper-bound property에 의해) 상한 $ub = sup\_{n}\\left\\{a\_{n}\\right\\}$ 이 존재한다.

**$Lemma \\ 1)$   $ For \\ \\epsilon > 0 \\in \\mathbb{R}$ $\\exists N \\in \\mathbb{N}$ $s.t$ $a\_{N} > sup\_{n}\\left\\{a\_{n}\\right\\} - \\epsilon$**

$a\_{N} > sup\_{n}\\left\\{a\_{n}\\right\\} - \\epsilon$ 을 만족하는 N이 존재하지 않는 다고 가정하면 이는 상계이며  $sup\_{n}{a\_{n}}$ 보다 작기에 모순이다.

**$Lemma \\ 2)$   $\\limsup (a\_n)\_{n \\in \\mathbb{N}} = sup\_{n}\\left\\{a\_{n}\\right\\}$**

**$Lemma \\ 1$**에 의해, 임의의 $N$보다 크거나 같은 자연수 $\\forall n \\in \\mathbb{N}, n \\leq N$에 대하여 $sup\_{n}\\left\\{a\_{n}\\right\\} - a\_{n} \\leq$ $sup\_{n}\\left\\{a\_{n}\\right\\} - a\_{N} < \\epsilon$이 성립한다.

극한의 정의에 따라 $\\limsup (a\_n)\_{n \\in \\mathbb{N}} = sup\_{n}\\left\\{a\_{n}\\right\\}$임이 증명된다.

$Lemma \\ 1$, $Lemma \\ 2$ 로부터 다음과 같은 정리가 얻어진다.

> If $(a\_{n})\_{n\\in \\mathbb {N} }$ is a monotone sequence of real numbers (i.e., if $a\_n \\leq a\_n+1$ for every $n \\geq 1$ or $a\_n \\geq a\_n+1$ for every $n \\geq 1$), then this sequence has a limit if and only if the sequence is bounded.\[1\]  
> ([reference](https://en.wikipedia.org/wiki/Monotone_convergence_theorem))
