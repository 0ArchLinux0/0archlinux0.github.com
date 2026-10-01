---
title: 백준 1395번 - 스위치
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [알고리즘, 자료구조, 구간 트리, 느리게 갱신되는 세그먼트 트리, 백준 1395, 스위치]
lang: ko
translation_key: boj-1395-switches
permalink: /ko/posts/boj-1395-switches/
pin: false
---

[백준 1395번 - 스위치](https://www.acmicpc.net/problem/1395)

## 문제와 구간 트리

스위치 $N$개가 있으며 처음에는 모두 꺼져 있다. 각 명령은 $1\le S\le T\le N$인 구간에 대해 주어진다. 명령 `0 S T`는 양 끝을 포함하는 $[S,T]$ 구간의 모든 스위치를 반전하고, `1 S T`는 그 구간에서 켜진 스위치의 개수를 출력한다.

구간의 모든 스위치를 하나씩 바꾸면 한 명령에 선형 시간이 들 수 있다. 대신 재귀 세그먼트 트리의 각 노드에 해당 구간의 켜진 스위치 수를 저장하고, 느리게 갱신되는 반전 비트를 둔다. 전체 구간을 한 번에 반전할 때 스위치 수를 다시 계산할 필요가 없다.

## 반전과 느리게 갱신되는 값

길이가 $L$인 구간에 켜진 스위치가 `on`개라면 그 구간을 반전한 뒤 켜진 개수는 $L-\text{on}$이다. 따라서 노드에 반전을 적용할 때 `on = L - on`으로 바꾸고 lazy 비트를 XOR 1 한다. 비트가 1이면 그 노드의 구간에 반전이 적용되었지만 자식 노드에는 아직 전달되지 않았다는 뜻이다. 반전을 두 번 적용하면 원래 상태로 돌아오므로 두 lazy 비트는 XOR로 합쳐지며, 두 번의 반전은 서로 상쇄된다.

부분 구간을 내려가야 할 때는 `push`로 미뤄 둔 반전을 두 자식에 적용한다. 자식 구간 길이에 맞게 각 자식의 켜진 개수를 바꾸고 각 lazy 비트를 반전한 뒤 부모의 lazy 비트를 0으로 지운다. 리프는 더 전달할 자식이 없다.

## 업데이트와 질의

재귀 함수는 현재 구간이 요청 구간과 겹치지 않으면 바로 반환한다. 현재 구간 전체가 요청 구간에 포함되면 해당 노드에 반전을 적용하고 멈춘다. 그렇지 않으면 먼저 lazy 값을 자식에게 전파하고 왼쪽과 오른쪽 자식을 재귀적으로 갱신한 다음 부모의 켜진 개수를 두 자식의 합으로 다시 계산한다.

질의도 같은 겹침 규칙을 사용한다. 겹치지 않는 노드는 0을 반환하고, 요청 구간에 완전히 포함된 노드는 저장된 켜진 개수를 반환한다. 부분적으로만 겹치면 lazy 값을 전파한 뒤 두 자식의 결과를 더한다. 모든 구간은 양 끝을 포함하므로 재귀의 겹침 판정은 `queryLeft <= nodeRight` 및 `nodeLeft <= queryRight`를 기준으로 한다.

각 명령은 세그먼트 트리의 높이에 비례해 처리되므로 업데이트와 질의 모두 $O(\log N)$ 시간이다. 트리 배열은 $4N$ 크기로 할당하므로 공간은 $O(N)$이다.

## C++17 코드

```cpp
#include <iostream>
#include <vector>
using namespace std;

class SegmentTree {
    int n;
    vector<int> on;
    vector<bool> lazy;

    void apply(int node, int left, int right) {
        on[node] = (right - left + 1) - on[node];
        lazy[node] = !lazy[node];
    }

    void push(int node, int left, int right) {
        if (!lazy[node] || left == right) return;
        int mid = left + (right - left) / 2;
        apply(node * 2, left, mid);
        apply(node * 2 + 1, mid + 1, right);
        lazy[node] = false;
    }

    void toggle(int node, int left, int right, int ql, int qr) {
        if (qr < left || right < ql) return;
        if (ql <= left && right <= qr) {
            apply(node, left, right);
            return;
        }
        push(node, left, right);
        int mid = left + (right - left) / 2;
        toggle(node * 2, left, mid, ql, qr);
        toggle(node * 2 + 1, mid + 1, right, ql, qr);
        on[node] = on[node * 2] + on[node * 2 + 1];
    }

    int countOn(int node, int left, int right, int ql, int qr) {
        if (qr < left || right < ql) return 0;
        if (ql <= left && right <= qr) return on[node];
        push(node, left, right);
        int mid = left + (right - left) / 2;
        return countOn(node * 2, left, mid, ql, qr)
             + countOn(node * 2 + 1, mid + 1, right, ql, qr);
    }

public:
    explicit SegmentTree(int size) : n(size), on(4 * size, 0), lazy(4 * size, false) {}

    void toggle(int left, int right) {
        toggle(1, 1, n, left, right);
    }

    int countOn(int left, int right) {
        return countOn(1, 1, n, left, right);
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    SegmentTree tree(n);

    while (m--) {
        int operation, left, right;
        cin >> operation >> left >> right;
        if (operation == 0) {
            tree.toggle(left, right);
        } else {
            cout << tree.countOn(left, right) << '\n';
        }
    }
    return 0;
}
```
