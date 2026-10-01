---
title: 베주 항등식 - 정수에 대한 증명 (Part 1)
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Number Theory, 베주 항등식, 증명, 정수론]
lang: ko
translation_key: bezout-identity-1
permalink: /ko/posts/bezout-identity-1/
---

## 정수에 대한 베주 항등식

$a,b\in\mathbb Z$가 동시에 0은 아니라고 하고,

$$g=\gcd(|a|,|b|)>0$$

로 놓자. 그러면 다음을 만족하는 정수 $x,y$가 존재한다.

$$ax+by=g.$$

더 나아가 $a,b$의 모든 정수 선형결합은 정확히 $g$의 배수 전체이다.

$$\{ax+by:x,y\in\mathbb Z\}=g\mathbb Z=\{ng:n\in\mathbb Z\}.$$

이 글에서는 정수에 대한 명제만 증명한다. 다항식에 대한 유사한 명제는 별도의 결과이며 여기서 증명하지 않는다.

## 증명

양의 정수 선형결합들의 집합을 다음과 같이 두자.

$$S=\{ax+by:x,y\in\mathbb Z,\ ax+by>0\}.$$

이 집합은 공집합이 아니다. $a,b$가 동시에 0은 아니므로 둘 중 적어도 하나는 0이 아니다. $a\ne0$이면 $a>0$일 때 $x=1$, $a<0$일 때 $x=-1$로 두고 $y=0$으로 놓으면 $ax+by=|a|>0$이다. $a=0$이면 $b\ne0$이다. 이때 $x=0$으로 두고 $y$의 부호를 $b$의 부호와 같게 선택하면 $ax+by=|b|>0$이다.

정수의 정렬성 원리에 따라 $S$에는 최소 원소 $m$이 존재한다. 따라서 어떤 $x_0,y_0\in\mathbb Z$에 대해 $m=ax_0+by_0$이며 $m>0$이다.

양의 정수 $m$으로 $a$를 나누어 나머지를 취하면, 어떤 $q,r\in\mathbb Z$에 대해

$$a=qm+r,\qquad 0\le r<m$$

이다. $m=ax_0+by_0$이므로

$$r=a-qm=a(1-qx_0)+b(-qy_0),$$

즉 $r$도 $a,b$의 정수 선형결합이다. 만약 $r>0$이면 $r\in S$이고 $r<m$이 되어 $m$의 최소성에 모순이다. 그러므로 $r=0$이고, 따라서 $m\mid a$이다. 같은 논리를 $b$에 적용하면 $m\mid b$도 얻는다.

반대로 $c$가 $a,b$의 임의의 공약수라면 모든 정수 선형결합, 특히 $m=ax_0+by_0$를 나눈다. 따라서 $a,b$의 모든 공약수는 $m$을 나눈다. $m\mid a$와 $m\mid b$라는 사실과 함께 보면 $m$은 양의 최대공약수이다. 즉 $m=g$이며, 이미 얻은 표현으로부터 $ax_0+by_0=g$이다.

이제 모든 선형결합을 살펴보자. $g$는 $a,b$를 모두 나누므로 임의의 $x,y\in\mathbb Z$에 대해 $g\mid ax+by$이다. 따라서 모든 선형결합은 $g$의 배수이다. 반대로 임의의 $n\in\mathbb Z$에 대해 $ax_0+by_0=g$에 $n$을 곱하면

$$ng=a(nx_0)+b(ny_0)$$

을 얻는다. 이는 $a,b$의 정수 선형결합이다. 그러므로 모든 선형결합은 정확히 $g$의 배수 전체이며, 증명이 끝난다.
