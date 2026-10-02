---
title: 축소구간 정리
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, 축소구간 정리]
lang: ko
translation_key: nested-interval-property
permalink: /ko/posts/nested-interval-property/
---

## 축소구간 정리

실수 집합 $\mathbb{R}$의 공집합이 아닌 닫힌구간들로 이루어진 열
$(I_n)_{n\in\mathbb{N}}$을 생각하자. 각 구간을 $I_n=[a_n,b_n]$이라 하고,
그 길이를 $\lvert I_n\rvert=b_n-a_n$으로 나타내자.

다음 두 조건을 가정한다.

1. 모든 $n\in\mathbb{N}$에 대해 $I_{n+1}\subseteq I_n$이다.
2. $n\to\infty$일 때 $\lvert I_n\rvert\to 0$이다.

그러면 모든 구간에 공통으로 속하는 실수는 정확히 하나 존재한다. 즉,

$$
\bigcap_{n\in\mathbb{N}} I_n=\{x\}
$$

이다. 구간들이 중첩되고 공집합이 아니라는 조건은 공통점의 존재를 보장하며,
구간의 길이가 0으로 수렴한다는 조건은 그 공통점의 유일성을 보장한다.

## 증명

구간들이 중첩되므로 왼쪽 끝점들은 단조 증가하여
$a_n\leq a_{n+1}$이다. 또한 모든 $n$에 대해 $a_n\leq b_n\leq b_1$이므로
왼쪽 끝점들은 위로 유계이다. 실수의 완비성에 따라 왼쪽 끝점들의 집합은
상한을 가지므로 다음과 같이 놓자.

$$
x=\sup\{a_n:n\in\mathbb{N}\}.
$$

$x$가 모든 $I_n$에 속함을 보이자. $n$을 하나 고정하자. 임의의 지표 $m$에
대해 $a_m\leq b_n$이다. $m\geq n$이면 $I_m\subseteq I_n$이고, $m<n$이면
$a_m\leq a_n\leq b_n$이기 때문이다. 따라서 $b_n$은 모든 왼쪽 끝점의
상계이므로 $x\leq b_n$이다. 한편 상한의 정의에 의해 $a_n\leq x$이다.
그러므로 $a_n\leq x\leq b_n$, 즉 $x\in I_n$이다. 이는 모든 $n$에 대해
성립하므로 공통점이 존재한다.

이제 유일성을 보이자. $x$와 $y$가 모두 모든 구간에 속한다고 가정하면,
각 $n$에 대해

$$
\lvert x-y\rvert\leq b_n-a_n=\lvert I_n\rvert
$$

이다. $\lvert I_n\rvert\to 0$이므로 음이 아닌 수 $\lvert x-y\rvert$는 임의로 작은 양수보다도
작거나 같다. 따라서 $\lvert x-y\rvert=0$, 즉 $x=y$이다. 공통점은 유일하다.
