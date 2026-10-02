---
title: Bézout's identity
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Number Theory, Bézout's identity, 베주 항등식, 증명, 정수론]
pin: true
lang: en
translation_key: bezout-identity-1
---

## Bézout's identity for integers

Let $a,b\in\mathbb Z$ be not both zero, and let

$$g=\gcd(|a|,|b|)>0.$$

Then there are integers $x,y$ such that

$$ax+by=g.$$

In fact, the set of all integer linear combinations of $a$ and $b$ is exactly the set of multiples of $g$:

$$\{ax+by:x,y\in\mathbb Z\}=g\mathbb Z=\{ng:n\in\mathbb Z\}.$$

This article proves the integer statement only. A polynomial analogue is a separate result and is not established here.

## Proof

Consider the set of positive integer linear combinations

$$S=\{ax+by:x,y\in\mathbb Z,\ ax+by>0\}.$$

This set is nonempty. Since $a,b$ are not both zero, at least one is nonzero. If $a\ne0$, choose $x=1$ when $a>0$ and $x=-1$ when $a<0$, and take $y=0$; then $ax+by=|a|>0$. If $a=0$, then $b\ne0$, and choosing $x=0$ and $y$ to have the sign of $b$ gives $ax+by=|b|>0$.

By the well-ordering principle, $S$ has a least element $m$. Thus $m=ax_0+by_0$ for some $x_0,y_0\in\mathbb Z$, and $m>0$.

Apply division with remainder to $a$ and the positive integer $m$: there are $q,r\in\mathbb Z$ such that

$$a=qm+r,\qquad 0\le r<m.$$

Since $m=ax_0+by_0$,

$$r=a-qm=a(1-qx_0)+b(-qy_0),$$

so $r$ is an integer linear combination of $a$ and $b$. If $r>0$, then $r\in S$ and $r<m$, contradicting the minimality of $m$. Therefore $r=0$, and $m\mid a$. Applying the same argument to $b$ shows that $m\mid b$.

Conversely, if $c$ is any common divisor of $a$ and $b$, then $c$ divides every integer linear combination of them, in particular $m=ax_0+by_0$. Thus every common divisor of $a,b$ divides $m$. Together with $m\mid a$ and $m\mid b$, this means that $m$ is their positive greatest common divisor. Hence $m=g$, and the representation of $m$ already gives integers $x_0,y_0$ with $ax_0+by_0=g$.

It remains to identify all the combinations. Because $g$ divides both $a$ and $b$, it divides $ax+by$ for every $x,y\in\mathbb Z$. Thus every such combination is a multiple of $g$. Conversely, for any $n\in\mathbb Z$, multiplying $ax_0+by_0=g$ by $n$ gives

$$ng=a(nx_0)+b(ny_0),$$

which is an integer linear combination of $a$ and $b$. Therefore the combinations are exactly the multiples of $g$, as claimed.
