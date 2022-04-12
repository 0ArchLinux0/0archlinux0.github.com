---
title: Euclidean Algorithm - 유클리드 호제법
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [Math, Number Theory]
tags: [Math, Number Theory, Euclidean Algorithm, 유클리드 호제법, 증명, 정수론]
pin: false
---

**\# Definition**

**$ a = bq + r(0 \\le r < b) $ $\\Rightarrow $ $GCD(a, b) = GCD(b, r)$**

---

최대공약수에 관한 문제에서 자주 쓰이는 기본 정리 중 하나이다.

증명부터 해보자.

|$ Lemma 1)$ $WLOG \\ a >= b,$ $GCD(a, b) = GCD(a-b, b) $

---

$proof)$

$g=GCD(a,b),$ $ \\ a = gA,$ $ \\ b =gB$ 라 두자.  
$ GCD(a-b, b) $ $= GCD(g(A-B), gB) $ 이기에 A-B와 B가 서로소임을 보이면 Lemma 1은 증명된다.

귀류법으로  
$ GCD(A-B, B) = g\_{A,B},$ $A-B = g\_{A,B}M,\\ B $ $= g\_{A,B}N,\\ g\_{A,B} \\neq 1$  
를 만족하는  
$\\exists g\_{A,B} \\in \\mathbb{Z} $가 존재한다고 가정하자.

$ B = g\_{A,B}N,$ $A = (A-B) + B = g\_{A,B}M + g\_{A,B}N $ $= g\_{A,B}(M+N) $  
$ a = gA $ $= gg\_{A,B}(M+N),$ $ b = gB =$ $ gg\_{A,B}N $ 으로 부터  
$ g = GCD(a, b) $ $= \\geq gg\_{A,B} $  
$ g\_{A,B} \\le 1 $

이는 가정과 모순됨으로 Lemma 1이 증명된다.

이제 Lemma 1으로부터 본 정리가 증명됨은 자명하다.

$WLOG\\ a=bq+r(0 \\le r < B) $ 이라 두자.

$ GCD(a, b) = GCD(a - b, b) $ $= GCD(a - 2b, b) = \\cdots $ $= GCD(a - bq, b) = GCD(r, b) $ $= GCD(b, r) $  
을 얻을 수 있음을 확인 할 수 있다.

---

**\# Pseudo Code**

유클리드 호제법을 이용한 최대공약수를 구하는 코드는 다음과 같이 간결하게 쓸 수 있다.

```c++
int gcd(int a, b) {
  if(b == 0) return a;
  return gcd(b, a%b);
}
```
