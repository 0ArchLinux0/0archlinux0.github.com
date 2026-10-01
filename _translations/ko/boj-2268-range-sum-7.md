---
title: BOJ 2268 - 수들의 합 7
author: MINJUN PARK
date: 2022-02-18 09:28:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 세그먼트 트리, 구간 합]
pin: false
lang: ko
translation_key: boj-2268-range-sum-7
permalink: /ko/posts/boj-2268-range-sum-7/
source_permalink: /posts/BOJ-2268/
---

[문제: BOJ 2268 — 수들의 합 7](https://www.acmicpc.net/problem/2268) · [English](/posts/BOJ-2268/) · [日本語](/ja/posts/boj-2268-range-sum-7/)

배열의 원소는 `N`개이며 처음에는 모두 0입니다. `0 a b` 명령은 1부터 시작하는 양 끝 포함 구간 `[min(a, b), max(a, b)]`의 합을 출력합니다. `1 a b` 명령은 `a`번째 원소를 `b`로 대입하며, 기존 값은 새 값으로 교체됩니다. 문제에서 대입 값은 음수가 아니며 0일 수도 있습니다. 구간 합은 32비트 정수 범위를 넘을 수 있으므로 트리에는 `long long`을 사용합니다.

반복형 세그먼트 트리는 0부터 시작하는 인덱스 `i`의 원소를 `tree[N + i]`에 저장하고, 내부 노드에는 두 자식의 합을 저장합니다. 트리는 0으로 초기화하므로 처음에 모든 배열 원소가 0인 조건을 그대로 나타냅니다. 대입 연산은 해당 리프를 새 값으로 바꾸고 루트까지 조상 노드의 합을 다시 계산하며 `O(log N)`에 끝납니다.

합 명령에서는 먼저 입력 양 끝을 오름차순으로 정렬합니다. 1부터 시작하는 양 끝 포함 구간 `[a, b]`를 0부터 시작하는 반열린 구간 `[a - 1, b)`로 변환한 다음, 양 끝에 `N`을 더해 리프 인덱스로 옮깁니다. `left < right`인 동안 홀수인 왼쪽 끝은 구간 오른쪽 경계에 포함되는 완전한 노드이므로 `tree[left]`를 더하고 왼쪽 끝을 증가시킵니다. 오른쪽 끝이 홀수이면 그 직전 노드가 구간에 속하므로 오른쪽 끝을 감소시킨 뒤 `tree[right]`를 더합니다. 두 끝을 부모로 옮겨 같은 과정을 반복합니다. 반열린 경계 덕분에 원소 하나만 포함하는 구간도 처리되며, 입력 순서가 뒤바뀐 경우에는 끝점을 정렬하는 것만으로 처리됩니다. 대입과 구간 합은 각각 `O(log N)` 시간에 수행되고, `2N`개 항목을 가진 트리는 `O(N)` 메모리를 사용합니다.

## C++

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    vector<long long> tree(2 * n, 0);

    while (m--) {
        int type, a;
        long long b;
        cin >> type >> a >> b;

        if (type == 1) {
            int position = n + a - 1;
            tree[position] = b;
            for (position >>= 1; position > 0; position >>= 1) {
                tree[position] = tree[position << 1] + tree[position << 1 | 1];
            }
        } else {
            int left = min(a, static_cast<int>(b)) - 1 + n;
            int right = max(a, static_cast<int>(b)) + n;
            long long sum = 0;

            while (left < right) {
                if (left & 1) {
                    sum += tree[left++];
                }
                if (right & 1) {
                    sum += tree[--right];
                }
                left >>= 1;
                right >>= 1;
            }
            cout << sum << '\n';
        }
    }
    return 0;
}
```
