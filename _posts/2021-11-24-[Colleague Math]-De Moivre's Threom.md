---
title: De Moivre's formula
author: MINJUN PARK
date: 2021-11-24 21:29:00 +0900
categories: [Math, Complex Analysis]
tags:
  [
    Colleague Math,
    Complex Analysis
  ]
pin: false
lang: en
translation_key: de-moivre-formula
permalink: /posts/De-Moivre-s-formula/
---

## Statement

Write a complex number in polar form as

$$
z=r(\cos\theta+i\sin\theta),
$$

where $r\ge 0$ is its modulus and $\theta$ is an argument measured in radians. For every integer $n$,

$$
z^n=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr).
$$

For $n<0$, require $z\ne 0$; for $n=0$, the expression is defined when $z\ne 0$ as well. For positive $n$, the identity also holds at $z=0$.

## Proof

Euler's formula gives $z=re^{i\theta}$. Raising both sides to an integer power gives

$$
z^n=(re^{i\theta})^n=r^n e^{in\theta}
=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr),
$$

using $e^{i\phi}=\cos\phi+i\sin\phi$. Equivalently, each multiplication by $z$ multiplies the modulus by $r$ and adds $\theta$ to the argument; after $n$ multiplications, these become $r^n$ and $n\theta$. The same rule extends to negative powers by taking reciprocals when $z\ne0$.

## Example

Taking $z=\cos(\pi/6)+i\sin(\pi/6)$ and $n=3$,

$$
z^3=\cos(3\pi/6)+i\sin(3\pi/6)
=\cos(\pi/2)+i\sin(\pi/2)=i.
$$

The angles here are in radians.