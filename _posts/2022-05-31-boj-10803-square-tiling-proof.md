---
title: "BOJ 10803 — Making Squares: A Verified Recurrence Bound"
author: MINJUN PARK
date: 2022-05-31 12:10:58 +0900
categories: [PS, baekjoon, Math]
tags: [PS, Algorithm, BOJ, Dynamic Programming, Geometry, Proof]
pin: false
lang: en
translation_key: boj-10803-square-tiling-proof
permalink: /posts/boj-10803-square-tiling-proof/
---

[BOJ 10803 — Making Squares](https://www.acmicpc.net/problem/10803)

## Model and claim

A valid cutting plan is a **guillotine dissection**: every cut splits one rectangle into two rectangles, and every final piece is a square with a positive integer side length. Let $f(n,m)$ be the minimum number of squares for an $n\times m$ rectangle. Rotation gives $f(n,m)=f(m,n)$.

The source post claimed that $f(n,m)=f(n,m-n)+1$ when $m\ge n^2/3$. Its proof shifts between $m$ and $n+m$ without shifting the condition. The argument below proves the conservative sufficient condition

$$m-n\ge \frac{n^2}{3}\quad\Longrightarrow\quad f(n,m)=f(n,m-n)+1,$$

for positive integers $n\le m$. This rewrite does **not** claim the stronger threshold from the source post.

## A bound for a remainder rectangle

For positive integers $a,b$,

$$f(a,b)\le \max(a,b).$$

By symmetry, assume $a\le b$. If $a=1$, use $b$ unit squares. Otherwise write $b=qa+s$, where $q\ge1$ and $0\le s<a$. If $s=0$, $q$ squares suffice. If $s>0$, place $q$ $a\times a$ squares and cut the remaining $a\times s$ rectangle. Strong induction on the longer side gives $f(s,a)\le a$, so

$$f(a,b)\le q+a\le qa+s=b.$$

The last inequality follows from $b-(q+a)=(q-1)(a-1)+(s-1)\ge0$. This construction uses only guillotine cuts.

## Proof of the recurrence bound

Set $r=m-n$, and assume $r\ge n^2/3$. Placing one $n\times n$ square beside an optimal $n\times r$ dissection gives

$$f(n,n+r)\le f(n,r)+1. \tag{1}$$

We prove the reverse strict inequality. Let $F=f(n,n+r)$ and choose an optimal guillotine dissection of an $n\times(n+r)$ rectangle.

If it contains an $n\times n$ square, that square spans the full height. Removing it leaves left and right guillotine rectangles whose widths sum to $r$. Place their dissections side by side to obtain an $n\times r$ dissection with $F-1$ squares. Hence $f(n,r)\le F-1$.

Now suppose the optimal dissection contains no $n\times n$ square. Define

$$q=\left\lfloor\frac n3\right\rfloor+1.$$

Then $q>n/3$ and $nq\le n+n^2/3\le n+r$. Consider the left prefix $W$ of width $nq$. Integer-sided square leaves in an integer guillotine dissection have integer boundary coordinates, so $W$ contains $nq$ unit-width columns.

In each column, the squares crossing it have side lengths $s_1,\ldots,s_c<n$ whose vertical intervals partition height $n$. There must be at least two such squares. By Cauchy–Schwarz,

$$\sum_{i=1}^c\frac1{s_i}\ge \frac{c^2}{\sum_i s_i}=\frac{c^2}{n}\ge\frac4n.$$

Count a square of side $s$ as a fraction $1/s$ in each unit column it crosses. Across all columns of $W$, the total fractional count is at least $4q$. Any one square contributes at most $s/s=1$ across $W$, so at least $4q$ distinct original squares intersect $W$. Call their number $D$; thus $D\ge4q$.

Delete those $D$ squares and fill $W$ with $q$ $n\times n$ squares. To the right of $W$, crop the original guillotine dissection at the vertical boundary of $W$. The cropped layout remains guillotine: by induction on the slicing tree, discard subtrees wholly to the left and keep those wholly to the right; at an intersected vertical node, crop the left child and keep the right child if the crop line is before the cut, and otherwise discard the left child and crop the right child; at a horizontal node, crop both children and keep the cut. Thus only squares crossed by the line become rectangular fragments. Each such fragment has height $s$ and width at most $s$; their vertical intervals are disjoint, so the sum of their heights is at most $n$. The preceding bound tiles these fragments with at most $n$ squares in total. All other squares to the right remain unchanged.

The resulting full-width dissection uses at most $F-D+q+n$ squares. Remove the leftmost new $n\times n$ square; the remaining rectangle has width $r$ and uses at most

$$F-D+q+n-1\le F-3q+n-1<F,$$

because $D\ge4q$ and $q>n/3$. Therefore $f(n,r)<F$ in this case as well.

We have proved $f(n,n+r)>f(n,r)$. Since both values are integers, combining this with (1) gives

$$f(n,n+r)=f(n,r)+1.$$

Substituting $r=m-n$ proves the stated bound.

## Scope

The threshold is sufficient, not necessary. It is deliberately stronger than the unverified threshold in the original post; do not use the original threshold on the strength of this proof. The proof relies on the guillotine-cut model used by the rectangular-cut dynamic program.

## Source history

Adapted from [“BOJ-10803 정사각형 만들기(증명)”](https://ilikechicken.tistory.com/52), published on 2022-05-31 by 0archlinux0 / MINJUN PARK and marked CC BY 4.0. This version corrects the shifted threshold, makes the tile-count argument explicit, and states only the sufficient bound established here.

## References

- [BOJ 10803 — Making Squares](https://www.acmicpc.net/problem/10803)
- [Tistory source post, ID 52](https://ilikechicken.tistory.com/52)
