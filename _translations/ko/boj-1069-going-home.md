---
title: 백준 1069번 - 집으로
author: MINJUN PARK
date: 2022-03-09 23:16:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, 백준, 기하]
pin: false
lang: ko
translation_key: boj-1069-going-home
permalink: /ko/posts/boj-1069-going-home/
---

[문제 링크](https://www.acmicpc.net/problem/1069)

시작점에서 목표까지의 거리를 $D_0=\sqrt{x^2+y^2}$라 하자. 곧장 걸어가면 걸리는 시간은 $D_0$이다. 점프는 방향과 관계없이 항상 거리 $D$만큼 이동하며 시간 $T$가 걸리고, 남은 거리는 걸을 수 있다.

$q=\lfloor D_0/D\rfloor$, $r=D_0-qD$로 두면 $0\le r<D$이다. 고려할 후보는 다음과 같다.

- 전부 걷기: $D_0$.
- 목표 방향으로 $q$번 점프한 뒤 나머지 $r$만큼 걷기: $qT+r$.
- 목표 방향으로 $q+1$번 점프한 뒤 지나친 거리 $D-r$만큼 되돌아 걸어가기: $(q+1)T+D-r$.
- $q+1$번 점프로 목표에 도달하기: $(q+1)T$. $q\ge1$이면 목표 방향으로 $q-1$번 점프한 다음, 두 점프의 합성 변위가 $D+r$가 되도록 한다. 길이 $D$인 두 점프의 합성 변위 길이는 $0$부터 $2D$까지 가능하고 $D+r<2D$이므로 가능하다. $q=0$일 때도 두 번의 점프로 합성 변위 길이를 $D_0<D$로 만들 수 있어 같은 시간이 든다.

위 후보들로 최솟값을 구할 수 있다. $n\le q$번 점프하면 남은 거리는 최소 $D_0-nD$이므로 시간은 적어도 $nT+D_0-nD$이다. 이는 $n$에 대한 일차식이므로 이 범위의 최솟값은 양 끝인 $n=0$ 또는 $n=q$에서 나온다. $q+1$번 이상 점프하면 점프 시간만으로 최소 $(q+1)T$가 들고, 위에서 보인 방법으로 이 시간이 실제로 가능하다. $q=0$인 경우도 포함된다. 한 번 점프했을 때 최선의 남은 거리는 $|D-D_0|=D-r$이고 두 번 점프하면 목표에 도달할 수 있다. 따라서 $D_0<D$인 경우와 $r=0$인 경우를 포함해 나열한 후보의 최솟값이 정답이다.

## C++17 구현

`hypot`으로 유클리드 거리를 안정적으로 계산한다. 시간 복잡도와 추가 공간 복잡도는 모두 $O(1)$이다.

```cpp
#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    double x, y, jumpDistance, jumpTime;
    std::cin >> x >> y >> jumpDistance >> jumpTime;

    const double distance = std::hypot(x, y);
    const auto q = static_cast<long long>(std::floor(distance / jumpDistance));
    const double remainder = distance - q * jumpDistance;

    double answer = distance;
    if (q == 0) {
        answer = std::min({answer,
                           jumpTime + jumpDistance - distance,
                           2.0 * jumpTime});
    } else {
        answer = std::min({answer,
                           q * jumpTime + remainder,
                           (q + 1) * jumpTime + jumpDistance - remainder,
                           (q + 1) * jumpTime});
    }

    std::cout << std::fixed << std::setprecision(10) << answer << '\n';
    return 0;
}
```
