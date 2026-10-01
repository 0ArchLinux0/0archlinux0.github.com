---
title: 단조 수렴 정리
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, 단조 수렴 정리]
lang: ko
translation_key: monotone-convergence-theorem
permalink: /ko/posts/monotone-convergence-theorem/
---

## 정리

$(a_n)$을 실수 수열이라고 하자.

- $(a_n)$이 단조 증가하고 위로 유계이면, 수열은 $\sup\{a_n:n\in\mathbb{N}\}$로 수렴한다.
- $(a_n)$이 단조 감소하고 아래로 유계이면, 수열은 $\inf\{a_n:n\in\mathbb{N}\}$로 수렴한다.

따라서 실수 단조 수열이 유한한 실수 극한을 가지는 것과 유계인 것은 동치이다.

## 단조 증가 수열의 증명

$(a_n)$이 단조 증가하고 위로 유계라고 하자. 치역
$A=\{a_n:n\in\mathbb{N}\}$은 공집합이 아니며 위로 유계이다. 실수의
완비성에 따라 $A$에는 최소 상계
$L=\sup A$가 존재한다.

임의의 $\varepsilon>0$을 택하자. $L-\varepsilon$은 최소 상계 $L$보다
작으므로 $A$의 상계가 아니다. 따라서 어떤 $N$에 대해

$$
L-\varepsilon<a_N\leq L
$$

이다.

모든 $n\geq N$에 대해 단조성과 $L$의 상계 성질로부터

$$
L-\varepsilon<a_N\leq a_n\leq L
$$

을 얻는다. 따라서 모든 $n\geq N$에 대해 $|a_n-L|<\varepsilon$이고,
이는 $a_n\to L$임을 뜻한다.

단조 감소 수열의 경우에는 $(-a_n)$에 같은 논증을 적용하거나 치역의
최대 하계를 사용하면 된다. 반대로 수렴하는 모든 실수 수열은 유계이므로,
단조 실수 수열은 유계일 때 그리고 그때에만 유한한 극한을 가진다.

## 예

$n\geq1$에서 $a_n=1-\frac{1}{n}$이라 하자. 이 수열은 단조 증가하고
$1$을 상계로 가진다. 상한은 $1$이므로 정리에 따라
$\lim_{n\to\infty}a_n=1$이다.
