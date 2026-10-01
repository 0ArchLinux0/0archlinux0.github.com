---
title: 単調収束定理
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, 単調収束定理]
lang: ja
translation_key: monotone-convergence-theorem
permalink: /ja/posts/monotone-convergence-theorem/
---

## 定理

$(a_n)$を実数列とする。

- $(a_n)$が単調非減少で上に有界ならば、$\sup\{a_n:n\in\mathbb{N}\}$に収束する。
- $(a_n)$が単調非増加で下に有界ならば、$\inf\{a_n:n\in\mathbb{N}\}$に収束する。

したがって、実数の単調列が有限な実数極限を持つことと、有界であることは同値である。

## 単調非減少列の場合の証明

$(a_n)$が単調非減少で上に有界であるとする。その値域$A=\{a_n:n\in\mathbb{N}\}$は空でなく、上に有界である。実数の完備性により、$A$には最小上界$L=\sup A$が存在する。

任意の$\varepsilon>0$を取る。$L-\varepsilon$は最小上界$L$より小さいため、$A$の上界ではない。したがって、ある番号$N$について

$$
L-\varepsilon<a_N\leq L
$$

となる。

すべての$n\geq N$について、単調性と$L$が上界であることから

$$
L-\varepsilon<a_N\leq a_n\leq L
$$

を得る。よって、すべての$n\geq N$で$|a_n-L|<\varepsilon$となり、$a_n\to L$が示された。

単調非増加列の場合は$(-a_n)$に同じ議論を適用するか、値域の最大下界を用いればよい。逆に、収束する実数列は必ず有界である。したがって、実数の単調列は有界である場合に限り有限な極限を持つ。

## 例

$n\geq1$に対して$a_n=1-\frac{1}{n}$とする。この列は単調非減少で、$1$を上界に持つ。上限は$1$なので、定理から$\lim_{n\to\infty}a_n=1$である。
