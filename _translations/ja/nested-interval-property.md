---
title: 入れ子区間定理
author: MINJUN PARK
date: 2022-04-24 18:28:00 +0900
categories: [Math, Analysis]
tags: [Math, Analysis, 入れ子区間定理]
lang: ja
translation_key: nested-interval-property
permalink: /ja/posts/nested-interval-property/
---

## 入れ子区間定理

実数全体の集合 $\mathbb{R}$ における、空でない閉区間の列
$(I_n)_{n\in\mathbb{N}}$ を考える。各区間を $I_n=[a_n,b_n]$ とし、その
長さを $|I_n|=b_n-a_n$ と表す。

次の二つの条件を仮定する。

1. すべての $n\in\mathbb{N}$ について $I_{n+1}\subseteq I_n$ である。
2. $n\to\infty$ のとき $|I_n|\to 0$ である。

このとき、すべての区間に共通して含まれる実数がただ一つ存在する。すなわち、

$$
\bigcap_{n\in\mathbb{N}} I_n=\{x\}
$$

である。区間が空でなく、互いに入れ子になっていることから共通点の存在が
保証され、区間の長さが 0 に収束することから、その共通点の一意性が保証される。

## 証明

区間が入れ子になっているので、左端点は単調非減少であり
$a_n\leq a_{n+1}$ となる。また、すべての $n$ について
$a_n\leq b_n\leq b_1$ だから、左端点の列は上に有界である。実数の完備性
により左端点全体の集合には上限が存在する。そこで

$$
x=\sup\{a_n:n\in\mathbb{N}\}
$$

と定める。

$x$ がすべての $I_n$ に属することを示そう。$n$ を固定する。任意の添字 $m$
について $a_m\leq b_n$ である。$m\geq n$ なら $I_m\subseteq I_n$ であり、
$m<n$ なら $a_m\leq a_n\leq b_n$ だからである。したがって $b_n$ はすべての
左端点の上界であり、$x\leq b_n$ となる。一方、上限の定義から
$a_n\leq x$ である。よって $a_n\leq x\leq b_n$、すなわち $x\in I_n$
である。これはすべての $n$ について成り立つため、共通点が存在する。

次に一意性を示す。$x$ と $y$ がともにすべての区間に属すると仮定すると、
すべての $n$ について

$$
|x-y|\leq b_n-a_n=|I_n|
$$

が成り立つ。$|I_n|\to 0$ なので、非負の数 $|x-y|$ はいくらでも小さい正の数
以下である。したがって $|x-y|=0$、つまり $x=y$ である。ゆえに共通点は
ただ一つである。
