---
title: Set And Logic(2) - 집합의 조작 - 集合の操作
author: MINJUN PARK
date: 2022-04-16 01:39:00 +0900
categories: [Computer Science, Automata theory]
tags: [Computer Science, Automata theory]
pin: true
---

## <center> 집합의 조작 </center> ##

---
정의 1. 환, 체, 분배법칙  
Ring, Field, Distributive law  

집합과 그 집합위의 연산 $+,\\cdot$의 두가지 이항연산자로 구성되는 쌍  
$(X, +, \\cdot)$이 환이라함은 다음 과 같은 3가지 성질을 만족할 때이다.

1. 덧셈에 대한 가환성(commutative) $(X, +)$에 대해서 가환군(아벨군)이다.
2. 곱셈에 대한 결합법칙(associative law of multiplication)  $\\forall x, y, z \\in X \\Rightarrow (x\\cdot y)\\cdot z = x \\cdot (y \\cdot z)$  
   
3. 분배법칙 $\\forall x, y, z \\in X \\Rightarrow x \\cdot(y + z) = x \\cdot y + x \\cdot z$ $and$ $(x+y)\\cdot z = x \\cdot z + y \\cdot z라

추가적으로 곱에 대한 교환법칙($\\forall x, y \\in X \\Rightarrow x \\cdot y = y \\cdot x $)을 만족할때 가환환(commutative ring)이라 불린다.
또한, 가환환의 조건에 더해 다음과 같은 3가지 성질이 성립할때 체(field)라고 불린다.

1. 곱셈에대해 닫혀있다　$\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$  
2. 곱셈에 대한 항등원을 가진다 $\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$
3. 곱셈에 대한 역원을 가진다  $\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$ 

이에 더해 곱에 대한 교환 법칙이 성립할때 가환체라고 하며 일반적으로 체라고 하면 가환체를 말하는 경우가 많다.

---

정의 2. 거리  

공집합이 아닌 집합 $X$의 원소 $a,b$에 대해 실수함수 $d(x,y)$가 정의되며 다음과 같은 4가지 조건을 만족할때 $d$는 $X$의 거리라고 말할 수 있다.

1. 거리는 음수가 아니다 $d(x, y) \\geq 0$  
2. 대칭성 $x, y \\in X \\Rightarrow d(x,y) = d(y,x)$  
3. 삼각부등식 $x, y, z \\in X \\Rightarrow d(x,z) \\leq d(x,y) + d(y, z)$  
4. 식별불가능자 동일성 원리 $d(x,y) = 0 \\iff x = y $

위의 성질들중 4번을 만족하지 않고 $d(x,y) = 0 \\Rightarrow x = y$만을 만족할때   
유사거리(pseudometric distance) 혹은 반거리(semimetric distance)라고 불린다.

---

정의 3. 거리공간 Metric Space   

집합 $X$와 거리함수$d$로 이루어진 쌍 $(X, d)$를 거리공간이라고 정의한다.

---

정의 4. 유클리드 거리 Euclidean Distance  

n차원 유클리드 공간 $(\\mathbb{R}^n, d)$는 대표적인 거리공간중 하나이며  
$\\mathbb{R}^n$는 길이 n의 벡터 $x=(x_{1], x_{2}, \\ldots , x_{n}}), x_{i} \\in \\mathbb{R}, i = 1, 2,\\ldots , n$ 전체로 구성된 집합이다.  
이때 두 점 x, y의 거리는 다음과 같이 정의된다.  
$d(x, y) \\colon = (\\displaystyle\\sum_{i=1}^n \\mid x_{i} - y {i} \\mid^2)^{1 \\over 2}$ 

---

정의 5. 구 Open ball  

경계를 포함하지 않은 구로 중심을 $x_{0}$라고 하면 다음과 같이 표현된다.  
$B_{r}(x_{0}) = \\left\\{ x \\in X \\ \\mid \\ d(x_{0}, x) < r \\right\\}$

---

정의 6. 근방 Neighborhood

거리공간 $X$와 점 $p \\in X$, 거리 $r > 0$ 에 대하여 근방은 다음과 같이 정의한다.  
$N_{r}(p) = \\left\\{q \\ \\mid \\ q \\in X \\land d(p, q) < r\\right\\}$  
$N_{r}(p) \\setminus \\left\\{p\\right\\}$ 를 $N_{r}^{\\prime}(p)$ 라고도 표기한다.

---

정의 7. 집적점, 한계점(Limit Point)  

거리공간 $X$와 $X$의 부분 집합 $E$를 잡자. 
점 $ p \\in X$ 에 대해  
$\\forall r > 0, N_{r}^{\\prime}(p) \\in E \\neq \\emptyset$  
가 만족된다면 $p$를 $E$의 집적점이라고 한다.

---

정의 8. 내부점, 외부점(Interior Point, Exterior Point)

거리공간 $X$와 $X$의 부분 집합 $E$를 잡자.
점 $ p \\in X$ 에 대해 이
$\\exists r > 0$ $such$ $that$ $N_{r}^{\\prime}(p) \\in E \\neq \\emptyset$  
가 만족된다면 $p$를 $E$의 내부점이라고 한다.
또한, $E^c = X \\setminus E$ 의 내부점을 $E$의 외부점이라고 한다.
일반적인 집합의 여접합과는 달리 $E \\cap E^c = \\emptyset$ 이 성립한다.

---

정의 9. 열린 집합, 닫힌 집합  
Open set, Closed set  

거리공간 $(X, d)$에 대해서 $X$의 부분 집합, $E \\subseteq X$를 생각하자.
다음이 성립할때  
$\\forall x \\in E, \\exists r > 0$ $such \\ that$ $B_{r}(x) \\subseteq E$
$U$를 $X$의 열린 집합(open set)이라 하며 닫힌 집합(closed set)는 $closed = open^c$ 여집합이 된다.  
다시 표현하면 E의 모든 점이 E의 interior point일때 E는 열려있다고 하며 E를 열린 집합,  
E의 모든 limit point가 E의 원소일때 E는 닫혀있다고 하며 E를 닫힌 집합이라 한다.

---

정의 10. 덮개 Cover

용어대로 특정 거리공간을 덮는다.
거리공간 $X$와 $X$의 부분 집합 $E$를 잡자.
집합족 $\\mathcal{F} = \\left\\{\\mathit{O}_{\\alpha} \\ \\mid \\ \\alpha \\in \\mathit{I}\\right\\}$에 대해  
1. $E \\subset \\bigcup_{\\alpha \\in \\mathit{I}} \\mathit{O}_{\\alpha}$ 일때, $\\mathcal{F}$는 $E$의 덮개라 한다.
2. $\\mathcal{F}$의 모든 원소가 open set이라면 $\\mathcal{F}$는 open cover이다.
3. $\\mathcal{F}$의 부분집합족(family of subsets) $\\mathcal{S} \\subset P(\\mathcal{F})$가 $E$의 cover일때, $\\mathcal{S}$을 $\\mathcal{F}$의 subcover(부분 덮개)라고 한다.
4. $\\mid \\mathcal{F} \\mid \\in \\mathbb{N}$일때 $\\mathcal{S}$를 finite subcover(유한 부분 덮개)라고 한다.

정의 10. 컴팩트 집합 Compact Set

compact한 집합이라는 어감대로 유한성과 관련이 있다.  
거리공간 $X$와 $X$의 부분 집합 $E$를 잡자. 이때,  
1. 한가지 표현으로  
$\\forall \\mathcal{C}=Open \\ cover \\ of E$, $\\exists \\mathcal{S}$ $which \\ is \\ finite$ $subcover \\ of  \\ \\mathcal{C}$   
를 만족한다면 $E$는 compact하다고 하며 Compact Set이라고 한다.
2. 다르게 표현하자면  
$\\forall \\mathcal{C}=Open \\ cover \\ of E$ 에 대해   
$\\exists A = \\left\\{\\alpha \\ \\mid \\ \\alpha \\in \\mathit{I} \\right\\}, s.t$  
$E \\subset \\bigcup_{\\alpha \\in A}  \\alpha$
를 만족할때 $E$는 Compact Set이다.

정의 11. 상계, 하계, 상한, 하한  


---
## <center> 集合の操作（和、積、包含と排除の原理、閉包性、被服） </center>
---

