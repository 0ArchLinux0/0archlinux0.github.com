---
title: 드무아브르 공식
author: MINJUN PARK
date: 2021-11-24 21:29:00 +0900
categories: [Math, Complex Analysis]
tags:
  [
    Colleague Math,
    Complex Analysis
  ]
pin: false
lang: ko
translation_key: de-moivre-formula
permalink: /ko/posts/de-moivre-formula/
---

## 공식

복소수의 극형식을 다음과 같이 쓰겠습니다.

$$
z=r(\cos\theta+i\sin\theta),
$$

여기서 $r\ge 0$은 복소수의 절댓값이고, $\theta$는 라디안 단위로 측정한 편각입니다. 정수 $n$에 대해 드무아브르 공식은 다음과 같습니다.

$$
z^n=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr).
$$

$n<0$이면 $z\ne 0$이어야 하며, $n=0$일 때도 식은 $z\ne 0$인 경우에 정의됩니다. 양의 정수 $n$에서는 $z=0$이어도 공식이 성립합니다.

## 증명

오일러 공식에 따라 $z=re^{i\theta}$입니다. 이를 정수 거듭제곱하면

$$
z^n=(re^{i\theta})^n=r^n e^{in\theta}
=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr),
$$

입니다. 여기서 $e^{i\phi}=\cos\phi+i\sin\phi$를 사용했습니다. 곱셈으로 생각하면 $z$를 한 번 곱할 때마다 절댓값은 $r$배가 되고 편각은 $\theta$만큼 더해집니다. 따라서 $n$번 곱하면 절댓값은 $r^n$, 편각은 $n\theta$가 됩니다. $z\ne0$일 때는 역수를 취해 음의 정수 거듭제곱에도 같은 규칙을 적용할 수 있습니다.

## 예시

$z=\cos(\pi/6)+i\sin(\pi/6)$이고 $n=3$이면,

$$
z^3=\cos(3\pi/6)+i\sin(3\pi/6)
=\cos(\pi/2)+i\sin(\pi/2)=i.
$$

각도는 라디안 단위로 나타냈습니다.
