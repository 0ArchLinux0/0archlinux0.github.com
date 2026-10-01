---
title: BOJ 7469 - K번째 수
author: MINJUN PARK
date: 2022-03-11 22:44:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 세그먼트 트리, 영속 세그먼트 트리, K번째 수]
pin: false
lang: ko
translation_key: boj-7469-kth-number
permalink: /ko/posts/boj-7469-kth-number/
---

[문제 링크](https://www.acmicpc.net/problem/7469)

## 기존 접근이 느린 이유

`(값, 인덱스)` 쌍을 한 번 정렬하는 데는 `O(N log N)` 시간이 걸리지만, 이후 각 구간 질의마다 전체 `N`개 원소를 훑으므로 최악의 경우 질의 처리에 `O(NM)` 시간이 걸립니다. 변수를 전역으로 선언하는 것은 저장 기간을 바꿀 뿐 수행해야 하는 연산량을 바꾸지 않으므로 점근적 시간 복잡도는 달라지지 않습니다.

## 영속 세그먼트 트리

배열의 값을 정렬된 서로 다른 값 `0 ... U-1`의 순위로 좌표 압축합니다. 배열의 각 접두 구간마다 영속 세그먼트 트리의 루트를 만듭니다. `root[i]`는 처음 `i`개 원소를 나타내며, 각 노드는 자신의 값 구간에 속하는 원소 수를 저장합니다. 다음 배열 값을 추가할 때는 루트에서 해당 리프까지의 노드만 복사하고 개수를 증가시키며, 나머지 노드는 이전 버전과 공유합니다.

질의 `[L, R]`에서 어떤 값 구간이 등장한 횟수는 `root[R]`의 개수에서 `root[L-1]`의 개수를 뺀 값입니다. 전체 순위 구간에서 시작해 두 루트의 왼쪽 자식 개수 차이를 `k`와 비교합니다. k번째 값이 왼쪽에 있으면 왼쪽으로 내려가고, 그렇지 않으면 그 개수만큼 `k`를 줄인 뒤 오른쪽으로 내려갑니다. 리프에 도달하면 해당 순위가 k번째 작은 값입니다. 중복된 값도 각 등장 횟수만큼 개수에 반영됩니다.

좌표 압축과 영속 루트 구성에는 `O(N log U)`, 각 질의에는 `O(log U)` 시간이 필요합니다. 노드, 입력 배열, 압축 좌표, prefix 루트가 `O(N log U)` 공간을 사용하고, 질의는 저장하지 않고 하나씩 처리합니다.

## C++17 구현

```cpp
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Node {
    int left = 0;
    int right = 0;
    int count = 0;
};


vector<Node> tree(1);

int update(int previous, int low, int high, int position) {
    const int current = static_cast<int>(tree.size());
    tree.push_back(tree[previous]);
    ++tree[current].count;

    if (low != high) {
        const int middle = low + (high - low) / 2;
        if (position <= middle) {
            tree[current].left = update(tree[previous].left, low, middle, position);
        } else {
            tree[current].right = update(tree[previous].right, middle + 1, high, position);
        }
    }
    return current;
}

int kth(int leftRoot, int rightRoot, int low, int high, int k) {
    if (low == high) return low;

    const int leftCount =
        tree[tree[rightRoot].left].count - tree[tree[leftRoot].left].count;
    const int middle = low + (high - low) / 2;

    if (k <= leftCount) {
        return kth(tree[leftRoot].left, tree[rightRoot].left, low, middle, k);
    }
    return kth(tree[leftRoot].right, tree[rightRoot].right, middle + 1, high,
               k - leftCount);
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<int> values(n);
    for (int& value : values) cin >> value;

    vector<int> coordinates = values;
    sort(coordinates.begin(), coordinates.end());
    coordinates.erase(unique(coordinates.begin(), coordinates.end()), coordinates.end());
    const int distinctCount = static_cast<int>(coordinates.size());

    vector<int> roots(n + 1, 0);
    for (int i = 0; i < n; ++i) {
        const int rank = static_cast<int>(
            lower_bound(coordinates.begin(), coordinates.end(), values[i]) - coordinates.begin());
        roots[i + 1] = update(roots[i], 0, distinctCount - 1, rank);
    }

    int left, right, k;
    while (m--) {
        cin >> left >> right >> k;
        const int rank = kth(roots[left - 1], roots[right],
                             0, distinctCount - 1, k);
        cout << coordinates[rank] << '\n';
    }
    return 0;
}
```
