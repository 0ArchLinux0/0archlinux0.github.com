---
title: 백준 17387번 - 선분 교차 2
author: MINJUN PARK
date: 2022-03-09 22:16:00 +0900
categories: [Record, Code]
tags: [PS, Algorithm, Baekjoon, BOJ, 기하, CCW, 선분 교차]
pin: false
lang: ko
translation_key: boj-17387-crossing-lines
permalink: /ko/posts/boj-17387-crossing-lines/
---

[백준 17387번: 선분 교차 2](https://www.acmicpc.net/problem/17387)

## 풀이: 방향 판정과 닫힌 선분의 경계

세 점 $P$, $Q$, $R$에 대해 외적 $(Q-P) \times (R-P)$의 부호로 점 $R$이 방향이 있는 직선 $PQ$의 어느 쪽에 있는지 알 수 있습니다. 값이 양수면 반시계 방향, 음수면 시계 방향이며, 0이면 세 점이 한 직선 위에 있습니다.

선분을 $AB$, $CD$라고 합시다. $C$와 $D$가 직선 $AB$의 서로 엄격히 반대편에 있고, 동시에 $A$와 $B$가 직선 $CD$의 서로 엄격히 반대편에 있으면 두 선분은 내부에서 교차합니다. 여기서 ‘엄격히 반대편’이 중요합니다. 방향 판정값이 0인 경우는 내부 교차가 아니므로 경계 경우로 따로 확인해야 합니다.

방향 판정값이 0인 각 경우에는 해당 점이 다른 선분의 **닫힌 경계 상자** 안에 있는지 확인합니다. 즉 점의 x좌표와 y좌표가 모두 선분 양 끝점의 각 좌표 사이에 있어야 하며, 양 끝값도 포함합니다. 이 검사는 공선 선분에서 필수입니다. 같은 무한 직선 위에 있다는 사실만으로 유한한 두 선분이 겹치는 것은 아닙니다. 경계값을 포함하면 끝점 접촉과 겹침을 인정하고, 떨어진 공선 선분은 제외합니다. 네 가지 끝점-선분 검사를 모두 하면 점 하나로 이루어진 퇴화 선분도 처리됩니다.

좌표 범위가 $[-10^9, 10^9]$이면 좌표 차의 절댓값은 최대 $2 \cdot 10^9$입니다. 외적을 구성하는 각 곱의 절댓값은 최대 $4 \cdot 10^{18}$이고, 두 곱의 차는 최대 $8 \cdot 10^{18}$이므로 부호 있는 64비트 정수 범위에 들어갑니다. 코드에서는 좌표와 외적을 `long long`으로 저장합니다.

경계 사례:

- 내부에서 제대로 교차하면 `1`입니다.
- 한 끝점에서 만나면 `1`입니다.
- 공선 선분이 끝점을 공유하거나 양의 길이만큼 겹치면 `1`입니다.
- 네 점이 공선이어도 선분 사이에 간격이 있으면 `0`입니다.
- 점 선분이 다른 선분 위에 있으면 `1`, 바깥에 있으면 `0`입니다.

## C++17

```cpp
#include <algorithm>
#include <iostream>

using namespace std;

struct Point {
    long long x;
    long long y;
};

long long cross(const Point& a, const Point& b, const Point& c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
}

int orientation(const Point& a, const Point& b, const Point& c) {
    const long long value = cross(a, b, c);
    if (value > 0) return 1;
    if (value < 0) return -1;
    return 0;
}

bool onSegment(const Point& a, const Point& b, const Point& p) {
    return min(a.x, b.x) <= p.x && p.x <= max(a.x, b.x) &&
           min(a.y, b.y) <= p.y && p.y <= max(a.y, b.y);
}

bool intersects(const Point& a, const Point& b, const Point& c, const Point& d) {
    const int abC = orientation(a, b, c);
    const int abD = orientation(a, b, d);
    const int cdA = orientation(c, d, a);
    const int cdB = orientation(c, d, b);

    if (abC * abD < 0 && cdA * cdB < 0) return true;

    if (abC == 0 && onSegment(a, b, c)) return true;
    if (abD == 0 && onSegment(a, b, d)) return true;
    if (cdA == 0 && onSegment(c, d, a)) return true;
    if (cdB == 0 && onSegment(c, d, b)) return true;
    return false;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    Point a, b, c, d;
    cin >> a.x >> a.y >> b.x >> b.y >> c.x >> c.y >> d.x >> d.y;

    cout << (intersects(a, b, c, d) ? 1 : 0) << '\n';
    return 0;
}
```
