---
title: Monotone Convergence Theorem
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, Monotone Convergence Theorem]
pin: true
lang: en
translation_key: monotone-convergence-theorem
permalink: /posts/Monotone-Convergence-Theorem/
---

## Theorem

Let $(a_n)$ be a sequence of real numbers.

- If $(a_n)$ is nondecreasing and bounded above, then it converges to $\sup\{a_n:n\in\mathbb{N}\}$.
- If $(a_n)$ is nonincreasing and bounded below, then it converges to $\inf\{a_n:n\in\mathbb{N}\}$.

Equivalently, a monotone real sequence converges to a finite real limit if and only if it is bounded.

## Proof for a nondecreasing sequence

Assume $(a_n)$ is nondecreasing and bounded above. Its range
$A=\{a_n:n\in\mathbb{N}\}$ is nonempty and bounded above. By completeness of
$\mathbb{R}$, it has a least upper bound
$L=\sup A$.

Let $\varepsilon>0$. The number $L-\varepsilon$ is less than the least upper
bound, so it cannot be an upper bound of $A$. Therefore, there is an index
$N$ such that

$$
L-\varepsilon<a_N\leq L.
$$

For every $n\geq N$, monotonicity and the definition of $L$ give

$$
L-\varepsilon<a_N\leq a_n\leq L.
$$

Thus $|a_n-L|<\varepsilon$ for every $n\geq N$, which proves that
$a_n\to L$.

The nonincreasing case follows by applying the same argument to $(-a_n)$, or
by using the greatest lower bound of the range. Conversely, every convergent
real sequence is bounded, so a monotone sequence has a finite limit exactly
when it is bounded.

## Example

For $a_n=1-\frac{1}{n}$ with $n\geq1$, the sequence is nondecreasing and
bounded above by $1$. Its supremum is $1$, so the theorem gives
$\lim_{n\to\infty}a_n=1$.
