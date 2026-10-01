---
title: Nested Interval Property
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, Nested Interval Property]
pin: false
lang: en
translation_key: nested-interval-property
---

## The nested interval theorem

Let $(I_n)_{n\in\mathbb{N}}$ be a sequence of nonempty closed intervals
$I_n=[a_n,b_n]$ in $\mathbb{R}$. Write
$|I_n|=b_n-a_n$ for the length of $I_n$.

If

1. $I_{n+1}\subseteq I_n$ for every $n\in\mathbb{N}$, and
2. $|I_n|\to 0$ as $n\to\infty$,

then there is exactly one real number that belongs to every interval:

$$
\bigcap_{n\in\mathbb{N}} I_n=\{x\}
$$

The nesting and nonemptiness guarantee a common point; the condition that
the lengths tend to zero guarantees that this point is unique.

## Proof

Because the intervals are nested, their left endpoints form a nondecreasing
sequence: $a_n\leq a_{n+1}$. They are bounded above by $b_1$, since
$a_n\leq b_n\leq b_1$. By completeness of $\mathbb{R}$, the set of left
endpoints has a supremum. Define

$$
x=\sup\{a_n:n\in\mathbb{N}\}.
$$

We show that $x$ lies in every $I_n$. Fix $n$. For every index $m$, we have
$a_m\leq b_n$: if $m\geq n$, then $I_m\subseteq I_n$; if $m<n$, then
$a_m\leq a_n\leq b_n$. Thus $b_n$ is an upper bound for all the left
endpoints, so $x\leq b_n$. Also $a_n\leq x$, because $x$ is their
supremum. Hence $a_n\leq x\leq b_n$, and therefore $x\in I_n$. This holds
for every $n$, proving existence.

For uniqueness, suppose that $x$ and $y$ both belong to every interval.
Then for every $n$,

$$
|x-y|\leq b_n-a_n=|I_n|.
$$

Since $|I_n|\to 0$, the nonnegative number $|x-y|$ is at most arbitrarily
small positive numbers. It follows that $|x-y|=0$, so $x=y$. Thus the
common point is unique.