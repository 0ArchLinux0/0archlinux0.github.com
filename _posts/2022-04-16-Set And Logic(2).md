---
title: Set And Logic(2) - 집합의 조작 - 集合の操作
author: MINJUN PARK
date: 2022-04-16 01:39:00 +0900
categories: [Computer Science, Automata theory]
tags: [Computer Science, Automata theory]
pin: true
---

## <center> 집합의 조작(합, 곱, 포함과 배제의 원리, Closure property、Covering） </center>

---
정의 1. 환, 체, 분배법칙

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

정의 2. 거리  

공집합이 아닌 집합 $X$의 원소 $a,b$에 대해 실수함수 $d(x,y)$가 정의되며 다음과 같은 4가지 조건을 만족할때 $d$는 $X$의 거리라고 말할 수 있다.

1. 거리는 음수가 아니다 $d(x, y) \\geq 0$  
2. 대칭성 $x, y \\in X \\Rightarrow d(x,y) = d(y,x)$  
3. 삼각부등식 $x, y, z \\in X \\Rightarrow d(x,z) \\leq d(x,y) + d(y, z)$  
4. 식별불가능자 동일성 원리 $d(x,y) = 0 \\iff x = y $

위의 성질들중 4번을 만족하지 않고 $d(x,y) = 0 \\Rightarrow x = y$만을 만족할때   
유사거리(pseudometric distance) 혹은 반거리(semimetric distance)라고 불린다.

정의 3. 공간거리  

집합 $X$와 거리함수$d$로 이루어진 쌍 $(X, d)$를 공간거리라고 정의한다.

정의 4. 유클리드 거리  

n차원 유클리드 공간 $(\\mathbb{R}^n, d)$는 대표적인 거리공간중 하나이며  
$\\mathbb{R}^n$는 길이 n의 벡터 $x=(x_{1], x_{2}, \\ldots , x_{n}}), x_{i} \\in \\mathbb{R}, i = 1, 2,\\ldots , n$ 전체로 구성된 집합이다.  
이때 두 점 x, y의 거리는 다음과 같이 정의된다.
$d(x, y) \\colon = (\\displaystyle\\sum_{i=1}^n \\mid x_{i} - y {i} \\mid^2)^{1 \\over 2}$ 

정의 5. Open ball  

경계를 포함하지 않은 구로 중심을 $x_{0}$라고 하면 다음과 같이 표현된다. 
$S_{r}(x_{0}) = \\left\\{ x \\in X \\ \\mid \\ d(x_{0}, x) < r \\right\\}$

정의 6. Open set, Closed set  

거리공간 $(X, d)$에 대해서 $X$의 부분 집합, $U \\subseteq X$를 생각하자.
다음이 성립할때  
$\\forall x \\in U, \\exists r > 0$ $such \\ that$ $S_{r}(x) \\subseteq U$
$U$를 $X$의 열린 집합(open set)이라 하며 닫힌 집합(closed set)는 $closed = open^c$ 여집합이 된다.

---
## <center> 集合の操作（和、積、包含と排除の原理、閉包性、被服） </center>
---

定義 1. 環、体、分配法則

集合とその集合上の「和」（＋）と「積」（・）と呼ばれる（異なる）２種類の２項演算からなる三つの組
$(X, +, \\cdot)$が「環」であるとは、以下の３つの条件が満たされるときである。

1. 和に関して可換性(commutative) $\\forall x, y, z \\in X \\Rightarrow (x\\cdot y)\\cdot z = x \\cdot (y \\cdot z)$  
2. 積に関する結合法則(associative law of multiplication)  
   $\\forall x, y, z \\in X \\Rightarrow x \\cdot(y + z) = x \\cdot y + x \\cdot z$ $and$ $(x+y)\\cdot z = x \\cdot z + y \\cdot z$
3. 分配法則  
さらに、積に関する交換法則： $\\forall x, y \\in \\mathbb{X} \\Rightarrow x \\cdot y = y \\cdot x$
を満たすとき、「可換環」と呼ぶ。(commutative ring)  
また、可換環の条件に加え次の３つの条件が成り立つと期は「体」と呼ばれる。

1. 積に関する閉性　$\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$  
2. 積に関する単位元  $\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$
3. 積に関する逆元  $\\forall x, y \\in X \\Rightarrow x\\cdot y \\in X$  

定義 2.

外延的定義：　最初から幾つかを示し、可能でなれば第 n 番目の要素などを示す、残りは...で表現することが多い。
内包的定義：　集合の要素が満足すべき性質を記すこと。
英小文字の集合は　$\sum$ = $\left\{a,b,c,...,z\right\}$ と書かれる。

定義 3. 集合 X のすべての要素がまた集合 Y の要素であるとき、「X は Y の部分集合である」といい、$X\subseteq Y$と書く。

定義 4. 集合 X または集合 Y のどちらかの要素、あるいは、両方の要素からなる集合を「X と Y の合併集合」と呼び、$X \cup Y$と書く。

定義 5. 集合 X と集合 Y の両方に属する要素からなる集合を「X と Y の共通集合」と呼び、$X \cup Y$と書く。

定義 6. 集合 X には属するが集合 Y には属さない要素からなる集合を「X の Y による差集合」と呼び、$X \setminus Y$と書く。

定義 7. 全体集合 U の要素で集合 X に属さない要素からなる集合を「X の補集合」と呼び、$\bar{X}, X^c$と書く。

定義 8. 全体集合 U の要素で集合 X に属さない要素からなる集合を「X の補集合」と呼び、$\bar{X}, X^c$と書く。

定義 9. 空集合$\emptyset$ は要素を持たない集合を言う。すべての集合の部分集合となる。

定義 10. べき集合:　一つの集合 X のすべての部分集合からなる集合。空集合と全体集合も入る。
$Powerset(X), P(X), 2^X, 2^{\\mid X \\mid}$のように書く。

定義 11. 直積、順序対:　$A \\times B = \\left\{(a, b) \ \mid \ a \in A, b \in B\right\} $
一般に、n個の集合の直積は $X_{1} \\times X_{2} \times ... \times X_{n}$ $=\left\{x_{1}, x_{2},...,x_{n} \ \mid \ x_{i} \in X_{i}, 1 \\leq i \leq n\right\}$
特に $\forall i, X_{i}=X$ の場合は $X^n$ と書く。

定義 12. 対称差、排他的論理和:　
$A \Delta B \colon = (A \setminus B) \cup (B \setminus A) = (A\cup B)\setminus(A\cap B)$

定義 13. 群、単位半群(モノイド)、可換群、位数：
一つの集合とその集合上の 2 項演算の組$(X, \cdot)$が「群」であるとは、以下の４つの条件が満たされるときできる。

閉性　
$\forall x, y \in X, x \cdot y \in X$
結合法則
$\forall x, y, z \in X, (x \cdot y) \cdot = x \cdot (y \cdot z)$
単位元の存在
$\exists e, \forall x \in X, x \cdot e = e \cdot x = x$
逆元の存在
$\forall x, \exists y \in X, x \cdot y = y \cdot x = e$
このうち、逆元の存在だけが成立しない場合、「単位半群(モノイド)」と呼ばれる。
この４つの条件に加えてさらに、
(交換法則)： $\forall x, y\in X, x\cdot y = y \cdot x$
が成立すると「可換群」と呼ばれる。
有限群では集合 X の要素数を「位数」と呼ぶ。
定義 14. 対数的数、超越数：
有理数係数の 0 でない多項式の根となる複素数を「代数的数」、それ以外の複素数を「超越数」と呼ぶ。

定義 15. 写像：
集合 X,Y について X の一つの要素に Y の要素が一つ対応するとき、この対応を「X から Y への写像」と呼ぶ。
$f \colon X \Rightarrow Y$
$X \ni x \mapsto y = f(x) \in Y$

定義 15. 写像：  
集合 X,Y について X の一つの要素に Y の要素が一つ対応するとき、この対応を「X から Y への写像」と呼ぶ。  
$f \\colon X \\Rightarrow Y$  
$X \\ni x \\mapsto y = f(x) \\in Y$

X の「定義域」あるいは「始域」、Y を「終域」と呼ぶ。さらに、$f(x)=\\left\\{y \\ \\mid y = f(x), x \\in X \\right\\} \\subseteq Y$ を「地域」と呼ぶ。

定義 16. 全射、単射、全単射：

写像　$f \\colon X \\Rightarrow Y$ において、以下の種別がある。

（全射）$\\forall y \\in Y, \\exists x \\in$ $such$ $that$ $f(x) = y$

（単射）$f(x)=f(x^\\prime) \\Rightarrow x = x^\\prime$

（全単射）全射かつ単射

定義 17.　逆写像：

写像 $f \\colon X \\Rightarrow Y$ が全単射であるとき $f^{-1} \\colon Y \\Rightarrow X$ と書く。

$Y \\ni y \\mapsto x = f^{-1}(y)$

定義 18. 対等：

二つの集合が「対等」であるとは、A から B への全単射が存在するときを言う。

$A~B$と書く。

定義 19. 濃度:

加算（可付番）集合の「濃度」:とは、有限集合では要素数である。それ以外の時は、$\\mathbb{N}$と対等であれば「加算濃度」や「連続濃度」と呼ばれる。加算濃度を持つ集合は「加算集合」あるいは「可付番集合」と呼ばれる。

一般に$\\mid A \\mid$や$|A|$のように書かれる。

定義 20. 濃度の大小：

A, B において、A から B への単射が存在するとき、$\\mid A \\mid \\leq \\mid B \\mid$と書き、

「A（の濃度）は B（の濃度）と同じかそれより小さい。」と言う。

全単射が存在せず、単射のみが存在するときは、

$\\mid A \\mid < \\mid B \\mid$や「A（の濃度）は B（の濃度）より小さい。」と言う。

定義 21.

定義 22.

定義 23.

定義 24.

定義 25.

定義 26.

定義 27.

定義 28.

定義 29.

定義 30.

定義 19.
