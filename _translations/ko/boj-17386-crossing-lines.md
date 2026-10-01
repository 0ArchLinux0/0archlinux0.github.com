---
title: 백준 17386번 - 선분 교차 1
author: MINJUN PARK
date: 2022-03-09 22:46:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, 백준, 외적, CCW, 기하, 선분 교차]
pin: false
lang: ko
translation_key: boj-17386-crossing-lines
permalink: /ko/posts/boj-17386-crossing-lines/
---

[문제 링크](https://www.acmicpc.net/problem/17386)

## 방향 판정과 선분 교차

세 점 `A`, `B`, `C`의 방향 판정값을 다음과 같이 정의한다.

```text
cross(A, B, C) = (B.x - A.x)(C.y - A.y) - (B.y - A.y)(C.x - A.x)
```

값이 양수이면 `C`는 방향이 지정된 직선 `AB`의 반시계 방향에 있고, 음수이면 시계 방향에 있다. 0이면 세 점이 한 직선 위에 있다.

입력에는 두 선분 `AB`와 `CD`가 주어진다. 문제에서는 네 끝점 중 세 점이 한 직선 위에 놓이지 않는다고 보장한다. 따라서 두 선분은 끝점에서 맞닿거나 같은 직선 위에 놓일 수 없으며, 교차한다면 두 선분의 내부에서 제대로 교차한다. 선분이 교차할 필요충분조건은 `A`, `B`가 직선 `CD`의 서로 반대쪽에 있고 동시에 `C`, `D`가 직선 `AB`의 서로 반대쪽에 있는 것이다. 방향 판정값으로는 두 쌍의 부호가 각각 반대여야 한다.

한 직선에 대한 반대쪽 여부만 확인해서는 유한한 선분끼리의 교차를 보장할 수 없으므로 두 조건을 모두 검사해야 한다. 공선인 경우가 없다는 조건으로 네 방향 판정값은 모두 0이 아니므로 엄격한 부호 비교만으로 충분하다. 방향 판정값끼리 곱해 부호를 비교하면 각각의 외적은 64비트에 들어가더라도 곱이 오버플로할 수 있으므로 곱하지 않는다.

좌표 범위는 `-1,000,000 <= x, y <= 1,000,000`이다. 좌표 차이는 최대 `2,000,000`이고 외적의 절댓값은 최대 `8 × 10^12`이므로 부호 있는 64비트 정수에 들어간다. 시간 복잡도와 추가 공간 복잡도는 모두 `O(1)`이다.

## C++17 구현

```cpp
#include <iostream>
using namespace std;

struct Point {
    long long x, y;
};

long long cross(const Point& a, const Point& b, const Point& c) {
    return (b.x - a.x) * (c.y - a.y)
         - (b.y - a.y) * (c.x - a.x);
}

bool oppositeSigns(long long a, long long b) {
    return (a < 0 && b > 0) || (a > 0 && b < 0);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    Point a, b, c, d;
    cin >> a.x >> a.y >> b.x >> b.y
        >> c.x >> c.y >> d.x >> d.y;

    const long long abC = cross(a, b, c);
    const long long abD = cross(a, b, d);
    const long long cdA = cross(c, d, a);
    const long long cdB = cross(c, d, b);

    cout << (oppositeSigns(abC, abD) && oppositeSigns(cdA, cdB));
}
```
