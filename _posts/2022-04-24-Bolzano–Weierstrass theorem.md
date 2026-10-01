---
title: Bolzano–Weierstrass Theorem
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, Bolzano–Weierstrass theorem]
lang: en
translation_key: bolzano-weierstrass
pin: true
---

## Theorem for sequences

Let $(x_n)$ be a bounded sequence in the finite-dimensional Euclidean space
$\mathbb{R}^d$, where $d\geq 1$. Then $(x_n)$ has a convergent subsequence.
The limit lies in $\mathbb{R}^d$; it need not be one of the terms of the
sequence.

Boundedness is the only hypothesis on the sequence. In particular, its set of
terms need not be closed.

## Proof

Write $x_n=(x_n^{(1)},\ldots,x_n^{(d)})$. Since $(x_n)$ is bounded in the
Euclidean norm, each coordinate sequence $(x_n^{(j)})$ is bounded in
$\mathbb{R}$.

We first recall the one-dimensional argument. A bounded real sequence lies in
some closed interval $I_0$. Bisect that interval. At least one of the two
closed halves contains terms with infinitely many indices; choose such a half
as $I_1$. Continue, each time bisecting the chosen interval and retaining a
closed half that contains terms with infinitely many indices. This gives
nested closed intervals

$$
I_0\supseteq I_1\supseteq I_2\supseteq\cdots
$$

whose lengths tend to zero. By the nested interval theorem their intersection
consists of one real number $a$. Choose indices $n_k$ strictly increasing so
that the $k$th selected term lies in $I_k$. For every $m\geq k$, both the
$m$th selected term and $a$ lie in $I_k$, so their distance is at most the
length of $I_k$. Hence the selected subsequence converges to $a$.

Apply this real result first to the first coordinate of $(x_n)$, obtaining a
subsequence along which that coordinate converges. Apply it to the second
coordinate of this subsequence, then to the third, and so on. There are only
finitely many coordinates, so after the final step there is one subsequence
$(x_{n_k})$ along which every coordinate converges, say
$x_{n_k}^{(j)}\to a^{(j)}$ for $1\leq j\leq d$. (Passing to a subsequence at
a later step preserves convergence already obtained.)

Let $a=(a^{(1)},\ldots,a^{(d)})$. For any $\varepsilon>0$, coordinate
convergence gives, for all sufficiently large $k$ and every $j$,
$|x_{n_k}^{(j)}-a^{(j)}|<\varepsilon/\sqrt d$. Therefore

$$
 \|x_{n_k}-a\|_2
=\sqrt{\sum_{j=1}^d|x_{n_k}^{(j)}-a^{(j)}|^2}<\varepsilon.
$$

Thus $x_{n_k}\to a$ in $\mathbb{R}^d$.

## Compactness of subsets

The sequence theorem should not be confused with a statement that every
bounded subset is compact. For a subset $K\subseteq\mathbb{R}^d$, the
Heine–Borel theorem says that $K$ is compact if and only if it is both closed
and bounded. Equivalently, in Euclidean space, $K$ is compact if and only if
every sequence in $K$ has a subsequence converging to a point of $K$
(sequential compactness).

Closedness matters in this set formulation: for example, $(0,1)$ is bounded
but not compact, and the sequence $x_n=1/n$ in it has no subsequence
converging to a point of $(0,1)$. The Bolzano–Weierstrass theorem itself only
asserts that a bounded sequence in $\mathbb{R}^d$ has a subsequence converging
in $\mathbb{R}^d$.

## Reference

- [Bolzano–Weierstrass theorem](https://en.wikipedia.org/wiki/Bolzano%E2%80%93Weierstrass_theorem)
