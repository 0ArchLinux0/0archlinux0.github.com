---
title: ド・モアブルの公式
author: MINJUN PARK
date: 2021-11-24 21:29:00 +0900
categories: [Math, Complex Analysis]
tags:
  [
    Colleague Math,
    Complex Analysis
  ]
pin: false
lang: ja
translation_key: de-moivre-formula
permalink: /ja/posts/de-moivre-formula/
---

## 公式

複素数を極形式

$$
z=r(\cos\theta+i\sin\theta)
$$

で表します。ここで $r\ge 0$ は複素数の絶対値、$\theta$ はラジアンで測った偏角です。整数 $n$ に対して、ド・モアブルの公式は次のようになります。

$$
z^n=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr)
$$

です。

$n<0$ の場合は $z\ne 0$ が必要です。また、$n=0$ の場合も式は $z\ne 0$ のときに定義されます。正の整数 $n$ では、$z=0$ の場合にも公式が成り立ちます。

## 証明

オイラーの公式より $z=re^{i\theta}$ です。両辺を整数乗すると、

$$
z^n=(re^{i\theta})^n=r^n e^{in\theta}
=r^n\bigl(\cos(n\theta)+i\sin(n\theta)\bigr)
$$

となります。ここでは $e^{i\phi}=\cos\phi+i\sin\phi$ を使いました。掛け算で考えると、$z$ を 1 回掛けるごとに絶対値は $r$ 倍になり、偏角には $\theta$ が加わります。したがって $n$ 回掛けると、絶対値は $r^n$、偏角は $n\theta$ になります。$z\ne0$ なら逆数を取ることで、負の整数乗にも同じ規則を適用できます。

## 例

$z=\cos(\pi/6)+i\sin(\pi/6)$、$n=3$ とすると、

$$
z^3=\cos(3\pi/6)+i\sin(3\pi/6)
=\cos(\pi/2)+i\sin(\pi/2)=i
$$

となります。角度はラジアンで表しています。
