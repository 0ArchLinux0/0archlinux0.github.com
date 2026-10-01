---
title: BOJ 10999 - 구간 합 구하기 2
author: MINJUN PARK
date: 2022-02-21 05:12:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 세그먼트 트리, 구간 합, 레이지 전파]
pin: false
lang: ko
translation_key: boj-10999-range-sum-2
permalink: /ko/posts/boj-10999-range-sum-2/
source_permalink: /posts/BOJ-10999/
---

[문제: BOJ 10999 — 구간 합 구하기 2](https://www.acmicpc.net/problem/10999) · [English](/posts/BOJ-10999/) · [日本語](/ja/posts/boj-10999-range-sum-2/)

배열에는 `N`개의 값이 있습니다. 1번 연산은 1부터 시작하는 양 끝 포함 구간 `[B, C]`의 모든 원소에 `D`를 더하고, 2번 연산은 같은 형태의 구간 합을 출력합니다. 배열 크기는 최대 백만이고 구간 합은 32비트 정수 범위를 넘을 수 있으므로 세그먼트 트리와 모든 계산에 `long long`을 사용합니다.

재귀 세그먼트 트리는 `tree[node]`에 해당 구간의 합을 저장하고, 아직 자식 구간에 반영되지 않은 원소별 더하기 값을 `lazy[node]`에 저장합니다. `apply(node, start, end, value)`는 구간의 모든 원소가 바뀌므로 합에 `value * (end - start + 1)`을 더하고, 지연 태그에도 `value`를 누적합니다. 일부만 포함된 노드에서 자식으로 내려가기 전에 `push`가 부모의 태그를 각 자식의 구간 길이에 맞춰 적용하고 부모의 태그를 지웁니다. 자식 구간을 갱신한 뒤에는 `pull`과 같이 두 자식의 합으로 부모 합을 다시 계산합니다. 완전히 포함된 구간은 자식으로 내려가지 않고 바로 `apply`합니다.

구간 합 질의에서 완전히 포함된 노드는 저장된 합을 반환합니다. 그 외에는 자식으로 내려가기 전에 대기 중인 값을 전파하고, 질의 구간과 겹치는 자식들의 결과를 더합니다. 각 연산은 세그먼트 트리의 경계 경로와 그 주변 노드만 방문하므로 갱신과 질의는 각각 `O(log N)` 시간입니다. 트리와 지연 태그 배열은 `O(N)` 메모리를 사용하고, 재귀 깊이는 `O(log N)`이므로 `N <= 1,000,000`에서도 안전합니다.

입력 구간은 1부터 시작하며 양 끝을 모두 포함합니다. 구현은 양 끝을 0부터 시작하는 인덱스로 바꾸며, 질의 구간을 반열린 구간으로 바꿀 필요는 없습니다.

## C++

```cpp
#include <iostream>
#include <vector>
using namespace std;

using int64 = long long;

int n;
vector<int64> tree;
vector<int64> lazy;

void build(int node, int start, int end, const vector<int64>& values) {
    if (start == end) {
        tree[node] = values[start];
        return;
    }
    int mid = start + (end - start) / 2;
    build(node * 2, start, mid, values);
    build(node * 2 + 1, mid + 1, end, values);
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

void apply(int node, int start, int end, int64 value) {
    tree[node] += value * (end - start + 1);
    lazy[node] += value;
}

void push(int node, int start, int end) {
    if (lazy[node] == 0 || start == end) return;

    int mid = start + (end - start) / 2;
    apply(node * 2, start, mid, lazy[node]);
    apply(node * 2 + 1, mid + 1, end, lazy[node]);
    lazy[node] = 0;
}

void update(int node, int start, int end, int left, int right, int64 value) {
    if (right < start || end < left) return;
    if (left <= start && end <= right) {
        apply(node, start, end, value);
        return;
    }

    push(node, start, end);
    int mid = start + (end - start) / 2;
    update(node * 2, start, mid, left, right, value);
    update(node * 2 + 1, mid + 1, end, left, right, value);
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

int64 query(int node, int start, int end, int left, int right) {
    if (right < start || end < left) return 0;
    if (left <= start && end <= right) return tree[node];

    push(node, start, end);
    int mid = start + (end - start) / 2;
    return query(node * 2, start, mid, left, right)
         + query(node * 2 + 1, mid + 1, end, left, right);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int m, k;
    cin >> n >> m >> k;

    vector<int64> values(n);
    for (int i = 0; i < n; ++i) cin >> values[i];

    tree.assign(4 * n, 0);
    lazy.assign(4 * n, 0);
    build(1, 0, n - 1, values);

    for (int i = 0; i < m + k; ++i) {
        int type, b, c;
        cin >> type >> b >> c;
        --b;
        --c;

        if (type == 1) {
            int64 d;
            cin >> d;
            update(1, 0, n - 1, b, c, d);
        } else {
            cout << query(1, 0, n - 1, b, c) << '\n';
        }
    }
    return 0;
}
```
