---
title: BOJ 2162 - 선분 그룹
author: MINJUN PARK
date: 2022-03-10 16:54:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 기하, CCW, 유니온 파인드, 선분 그룹]
pin: false
lang: ko
translation_key: boj-2162-line-groups
permalink: /ko/posts/boj-2162-line-groups/
---

[문제 링크](https://www.acmicpc.net/problem/2162)

## 모델링: 교차 그래프와 연결 요소

각 입력 선분을 그래프의 정점으로 두고, 두 **닫힌 선분이 교차할 때** 두 정점을 간선으로 연결합니다. 그룹은 이 그래프의 연결 요소입니다. 직접 교차하지 않더라도 교차하는 선분들의 사슬로 이어져 있으면 같은 그룹입니다. 따라서 모든 선분 쌍을 검사하고 교차하는 쌍을 서로소 집합(DSU) 자료구조에서 합칩니다. 모든 검사가 끝난 뒤 DSU의 루트 개수가 그룹 수이고, 가장 큰 DSU 컴포넌트의 크기가 최대 그룹 크기입니다.

## 정확한 닫힌 선분 교차 판정

세 점 `A`, `B`, `C`에 대해 부호 있는 외적
`(B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x)`
의 부호로 `C`가 방향을 가진 직선 `AB`의 어느 쪽에 있는지 알 수 있습니다. 양수와 음수는 서로 반대쪽이고, 0은 세 점이 한 직선 위에 있음을 뜻합니다. 두 선분이 내부에서 제대로 교차하려면 각 선분의 양 끝점이 다른 선분을 지나는 직선에 대해 **엄격히 반대 부호**를 가져야 합니다. 외적 값을 서로 곱하지 말고 부호를 직접 비교해야 합니다. 외적 값 각각이 범위 안에 있더라도 곱은 오버플로할 수 있기 때문입니다.

공식 제한은 `1 ≤ N ≤ 3,000`, 각 좌표는 `[-5,000, 5,000]`입니다. 좌표 차이의 절댓값은 최대 `10,000`이므로 외적의 각 곱은 절댓값 최대 `10^8`, 두 곱의 차이는 절댓값 최대 `2 × 10^8`입니다. 따라서 부호 있는 64비트 `long long`에 안전하게 들어갑니다.

선분 쌍은 `O(N^2)`개입니다. 각 교차 판정은 상수 시간이고, 경로 압축과 크기 기준 합치기를 사용하는 DSU 연산은 분할상환 `O(α(N))` 시간입니다. 총 시간 복잡도는 `O(N^2 α(N))`이며, 입력 선분과 DSU 배열의 보조 공간 복잡도는 `O(N)`입니다.

## C++17 구현

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Point {
    long long x;
    long long y;
};

struct Segment {
    Point a;
    Point b;
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

bool oppositeSigns(int a, int b) {
    return (a < 0 && b > 0) || (a > 0 && b < 0);
}

bool intersects(const Segment& first, const Segment& second) {
    const Point& a = first.a;
    const Point& b = first.b;
    const Point& c = second.a;
    const Point& d = second.b;

    const int abC = orientation(a, b, c);
    const int abD = orientation(a, b, d);
    const int cdA = orientation(c, d, a);
    const int cdB = orientation(c, d, b);

    if (oppositeSigns(abC, abD) && oppositeSigns(cdA, cdB)) return true;

    if (abC == 0 && onSegment(a, b, c)) return true;
    if (abD == 0 && onSegment(a, b, d)) return true;
    if (cdA == 0 && onSegment(c, d, a)) return true;
    if (cdB == 0 && onSegment(c, d, b)) return true;
    return false;
}

class DSU {
public:
    explicit DSU(int n) : parent(n), size(n, 1) {
        for (int i = 0; i < n; ++i) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    void unite(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;
        if (size[a] < size[b]) swap(a, b);
        parent[b] = a;
        size[a] += size[b];
    }

    int componentSize(int root) const {
        return size[root];
    }

private:
    vector<int> parent;
    vector<int> size;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<Segment> segments(n);
    for (Segment& segment : segments) {
        cin >> segment.a.x >> segment.a.y >> segment.b.x >> segment.b.y;
    }

    DSU dsu(n);
    for (int i = 0; i < n; ++i) {
        for (int j = i + 1; j < n; ++j) {
            if (intersects(segments[i], segments[j])) dsu.unite(i, j);
        }
    }

    int groupCount = 0;
    int largestGroup = 0;
    for (int i = 0; i < n; ++i) {
        if (dsu.find(i) == i) {
            ++groupCount;
            largestGroup = max(largestGroup, dsu.componentSize(i));
        }
    }

    cout << groupCount << '\n' << largestGroup << '\n';
    return 0;
}
```
