---
title: 백준 7869번 - 두 원
author: MINJUN PARK
date: 2022-03-09 23:16:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 기하, 두 원]
pin: false
lang: ko
translation_key: boj-7869-two-circles
permalink: /ko/posts/boj-7869-two-circles/
---

[문제 링크](https://www.acmicpc.net/problem/7869)

## 교집합 넓이

두 원의 중심을 $C_1=(x_1,y_1)$, $C_2=(x_2,y_2)$, 반지름을 $r_1,r_2$라 하고 중심 사이 거리를
$d=\sqrt{(x_1-x_2)^2+(y_1-y_2)^2}$라 하자. 원이 만나는 형태에 따라 교집합 넓이를 구한다.

- $d\ge r_1+r_2$이면 두 원은 서로 떨어져 있거나 외접하므로 교집합 넓이는 $0$이다.
- $d\le |r_1-r_2|$이면 한 원이 다른 원 안에 있거나 내접한다. 교집합은 작은 원판이므로 넓이는 $\pi\min(r_1,r_2)^2$이다. 이 조건은 중심이 같은 경우($d=0$)도 포함하므로, 두 원이 일치하더라도 $d$로 나누지 않고 처리한다.
- 그 외에는 두 원이 일부 겹치며 경계가 두 점에서 만난다.

일부 겹치는 경우, 두 중심과 교점 하나가 이루는 삼각형에 코사인 법칙을 적용하면 각 중심에서의 반각을 구할 수 있다.

$$
\theta_1=\cos^{-1}\left(\frac{d^2+r_1^2-r_2^2}{2dr_1}\right),\qquad
\theta_2=\cos^{-1}\left(\frac{d^2+r_2^2-r_1^2}{2dr_2}\right).
$$

각 원에서 중심각이 $2\theta_i$인 부채꼴에서 그 안의 이등변 삼각형 넓이를 빼면 원호와 현 사이의 원분 넓이 $r_i^2\theta_i-\frac12r_i^2\sin(2\theta_i)$를 얻는다. 두 원분 넓이를 더한 렌즈 모양 영역의 넓이는 다음과 같다.

$$
A=r_1^2\theta_1+r_2^2\theta_2
-\frac12r_1^2\sin(2\theta_1)-\frac12r_2^2\sin(2\theta_2).
$$

부동소수점 반올림 오차로 코사인 법칙의 결과가 수학적인 범위에서 약간 벗어날 수 있으므로 `acos`에 넣기 전에 `[-1, 1]`로 제한한다. 일부 겹침 공식은 앞의 두 경계 조건을 통과한 경우에만 사용하므로 0으로 나누지 않으며, 접하는 경우도 경계 조건에서 처리된다.

## C++17 구현

시간 복잡도와 추가 공간 복잡도는 모두 $O(1)$이다.

```cpp
#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    double x1, y1, r1, x2, y2, r2;
    std::cin >> x1 >> y1 >> r1 >> x2 >> y2 >> r2;

    const double dx = x1 - x2;
    const double dy = y1 - y2;
    const double d = std::hypot(dx, dy);
    const double pi = std::acos(-1.0);
    double area;

    if (d >= r1 + r2) {
        area = 0.0;
    } else if (d <= std::abs(r1 - r2)) {
        const double radius = std::min(r1, r2);
        area = pi * radius * radius;
    } else {
        const double d2 = d * d;
        const double r1_2 = r1 * r1;
        const double r2_2 = r2 * r2;
        const double cos1 = std::clamp((d2 + r1_2 - r2_2) / (2.0 * d * r1), -1.0, 1.0);
        const double cos2 = std::clamp((d2 + r2_2 - r1_2) / (2.0 * d * r2), -1.0, 1.0);
        const double theta1 = std::acos(cos1);
        const double theta2 = std::acos(cos2);

        area = r1_2 * theta1 + r2_2 * theta2
             - 0.5 * r1_2 * std::sin(2.0 * theta1)
             - 0.5 * r2_2 * std::sin(2.0 * theta2);
    }

    std::cout << std::fixed << std::setprecision(3) << area << '\n';
    return 0;
}
```
