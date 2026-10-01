---
title: 백준 3653번 - 영화 수집
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [알고리즘, 자료구조, 펜윅 트리, 백준 3653, 영화 수집]
lang: ko
translation_key: boj-3653-movie-collection
permalink: /ko/posts/boj-3653-movie-collection/
pin: false
---

[백준 3653번: 영화 수집](https://www.acmicpc.net/problem/3653)

## 영화 번호가 아니라 위치를 관리하기

영화를 요청할 때마다 DVD의 위치가 바뀌므로, 영화 번호만 인덱스로 삼으면 특정 영화 위에 DVD가 몇 장 있는지 바로 표현하기 어렵다. 대신 각 위치의 점유 여부를 관리한다. DVD가 있는 위치는 `1`, 비어 있는 위치는 `0`으로 두고, 이 값의 구간 합을 펜윅 트리로 계산한다.

테스트 케이스마다 영화 수를 `N`, 요청 수를 `M`이라고 하자. 맨 위로 옮길 때 사용할 위치 `1`부터 `M`까지를 비워 두고, 처음에는 영화 `i`를 위치 `M + i`에 둔다. 따라서 초기 스택은 위에서 아래 순서로 `M + 1`부터 `M + N`까지 차지한다. `position[i]`에는 영화 `i`의 현재 위치를 저장하고, 점유된 각 위치의 펜윅 트리 값을 `1`로 초기화한다.

위치 번호가 작을수록 스택의 위쪽이다. 영화 `x`가 현재 위치 `p`에 있다면, 그 위에 있는 DVD 수는 `p`보다 작은 위치에 놓인 DVD의 수, 즉 펜윅 트리에서 `p - 1`까지의 누적 합이다.

영화 `x`를 요청하면 먼저 이 개수를 계산해 출력한다. 이어서 트리에서 기존 위치를 제거하고, `next_top` 위치에 영화를 놓은 뒤 `position[x]`를 갱신한다. `next_top`은 `M`에서 시작해 요청을 처리할 때마다 감소한다. 그러면 새 위치는 지금까지 사용한 어떤 위치보다 위쪽이 된다. 같은 영화를 다시 요청하는 경우에도 현재 위치를 저장한 값이 이미 새 위치로 갱신되어 있으므로 올바르게 처리된다. 영화가 여전히 맨 위라면 그 위의 DVD 수는 0이다.

## 복잡도

요청마다 누적 합 질의 한 번과 점 갱신 두 번을 수행하며, 각각 $O(\log(N+M))$ 시간이 걸린다. 아래 구현에서는 초기 점유 위치도 각각 갱신하므로 초기화 시간은 $O(N\log(N+M))$이다. 테스트 케이스당 전체 시간 복잡도는 $O((N+M)\log(N+M))$, 공간 복잡도는 $O(N+M)$이다.

## C++17 구현

```cpp
#include <iostream>
#include <vector>
using namespace std;

class FenwickTree {
    vector<int> tree;

public:
    explicit FenwickTree(int size) : tree(size + 1, 0) {}

    void add(int index, int delta) {
        for (int i = index; i < static_cast<int>(tree.size()); i += i & -i) {
            tree[i] += delta;
        }
    }

    int prefixSum(int index) const {
        int sum = 0;
        for (int i = index; i > 0; i -= i & -i) {
            sum += tree[i];
        }
        return sum;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int testCases;
    cin >> testCases;

    while (testCases--) {
        int n, m;
        cin >> n >> m;

        FenwickTree occupied(n + m);
        vector<int> position(n + 1);

        for (int movie = 1; movie <= n; ++movie) {
            position[movie] = m + movie;
            occupied.add(position[movie], 1);
        }

        int nextTop = m;
        for (int request = 0; request < m; ++request) {
            int movie;
            cin >> movie;

            int current = position[movie];
            cout << occupied.prefixSum(current - 1) << (request + 1 == m ? '\n' : ' ');

            occupied.add(current, -1);
            position[movie] = nextTop;
            occupied.add(nextTop, 1);
            --nextTop;
        }
    }

    return 0;
}
```
