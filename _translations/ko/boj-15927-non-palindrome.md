---
title: BOJ 15927 - 회문은 회문이 아니야!!
author: MINJUN PARK
date: 2022-03-09 01:07:00 +0900
categories: [Record, Code]
tags: [C++, 알고리즘, BOJ, 문자열, 회문은 회문이 아니야]
pin: false
lang: ko
translation_key: boj-15927-non-palindrome
permalink: /ko/posts/boj-15927-non-palindrome/
---

[문제 링크](https://www.acmicpc.net/problem/15927)

## 핵심 관찰

문자열 `S`의 부분 문자열 중 회문이 아닌 것의 최대 길이를 구한다. `S` 자체가 회문이 아니면 전체 문자열이 답이므로 `N`이다. `S`가 회문이지만 모든 문자가 같다면 모든 부분 문자열도 회문이므로 답은 `-1`이다. 남은 경우, 즉 회문이지만 문자가 모두 같지는 않다면 답은 `N - 1`이다.

## 증명

전체 문자열이 회문이 아니면 `S`가 길이 `N`인 회문 아닌 부분 문자열이므로 답은 `N`이다. 전체 문자열이 회문이고 모든 문자가 같으면 모든 부분 문자열이 같은 문자만으로 이루어져 회문이므로 답은 `-1`이다.

이제 `S`는 회문이고 모든 문자가 같지는 않다고 하자. 길이 `N`인 문자열은 `S`뿐이므로 그보다 긴 회문 아닌 부분 문자열은 존재하지 않는다. 또한 길이 `N - 1`인 부분 문자열은 맨 앞이나 맨 끝 문자를 제거한 두 문자열뿐이다. `S`가 회문이므로 이 둘은 서로 뒤집은 관계이며, 둘 중 하나가 회문이면 다른 하나도 회문이다.

둘 다 회문이라고 가정하면 특히 마지막 문자를 제거한 접두사도 회문이다. 전체 문자열의 회문 조건은 `S[i] = S[N - 1 - i]`, 접두사의 회문 조건은 `S[i] = S[N - 2 - i]`를 강제한다. 따라서 `i = 0, …, N - 2`에 대해 `S[N - 1 - i] = S[N - 2 - i]`이고, 이는 모든 인접한 문자 쌍이 같다는 뜻이다. 그러면 모든 문자가 같아 모순이다. 따라서 길이 `N - 1`인 두 부분 문자열은 회문이 아니며 답은 `N - 1`이다.

문자열을 한 번 훑어 회문 여부와 모든 문자가 같은지 확인한다. 시간 복잡도는 `O(N)`, 입력 문자열 외 보조 공간 복잡도는 `O(1)`이다.

## C++17 구현

```cpp
#include <iostream>
#include <string>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string s;
    cin >> s;

    const int n = static_cast<int>(s.size());
    bool isPalindrome = true;
    bool allSame = true;

    for (int i = 0; i < n; ++i) {
        if (s[i] != s[n - 1 - i]) isPalindrome = false;
        if (s[i] != s[0]) allSame = false;
    }

    if (!isPalindrome) cout << n << '\n';
    else if (allSame) cout << -1 << '\n';
    else cout << n - 1 << '\n';

    return 0;
}
```
