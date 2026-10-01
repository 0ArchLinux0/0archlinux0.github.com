---
title: Euclidean Algorithm - 유클리드 호제법
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Number Theory, Euclidean Algorithm, 유클리드 호제법, 증명, 정수론]
pin: false
lang: en
translation_key: euclidean-algorithm
---

The **Euclidean algorithm** computes the greatest common divisor (gcd) of two nonnegative integers by repeatedly replacing the pair with a smaller pair that has the same common divisors.

## The key identity

Let $a$ and $b$ be nonnegative integers, with $b>0$. Euclidean division gives unique integers $q$ and $r$ such that

$$a=bq+r,\qquad 0\le r<b.$$

The key fact is

$$\gcd(a,b)=\gcd(b,r).$$

To prove it, compare the common divisors of the two pairs. If an integer $d$ divides both $a$ and $b$, then it divides their difference $a-bq=r$, so $d$ divides both $b$ and $r$. Conversely, if $d$ divides both $b$ and $r$, then it divides $bq+r=a$, so it divides both $a$ and $b$. Thus the pairs $(a,b)$ and $(b,r)$ have exactly the same common divisors, and in particular the same greatest common divisor.

## Repeating the step

Apply the identity again to $(b,r)$ whenever $r>0$. Each remainder is a nonnegative integer smaller than the preceding divisor, so the remainders strictly decrease until one is zero. For example,

$$252=105\cdot2+42,\qquad
105=42\cdot2+21,\qquad
42=21\cdot2+0.$$

Therefore

$$\gcd(252,105)=\gcd(105,42)=\gcd(42,21)=\gcd(21,0)=21.$$

For nonnegative $a$, the terminal case is $\gcd(a,0)=a$: every nonnegative integer divides $0$, and the greatest nonnegative common divisor of $a$ and $0$ is $a$. This also gives the conventional value $\gcd(0,0)=0$. Hence the algorithm also handles an initial input with $b=0$ immediately.

## Iterative C++ implementation

The following function accepts nonnegative `int` values only. Its precondition is that both arguments are nonnegative and representable as `int`; it returns their gcd, with `gcd(0, 0) == 0`.

```cpp
int gcd(int a, int b) {
    // Precondition: a >= 0 and b >= 0.
    while (b != 0) {
        int remainder = a % b;
        a = b;
        b = remainder;
    }
    return a;
}
```

At each iteration, `remainder` is the $r$ in $a=bq+r$, so the invariant $\gcd(a,b)$ is unchanged as the pair becomes $(b,r)$. When `b` reaches zero, `a` is the gcd.
