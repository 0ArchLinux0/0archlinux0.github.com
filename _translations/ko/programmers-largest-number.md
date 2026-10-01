---
title: 프로그래머스 — 가장 큰 수
author: MINJUN PARK
date: 2021-12-27 00:56:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, Coding Interview, Programmers, Largest Number, 가장 큰 수, 프로그래머스]
pin: false
lang: ko
translation_key: programmers-largest-number
permalink: /ko/posts/programmers-largest-number/
---

[문제 링크](https://programmers.co.kr/learn/courses/30/lessons/42746)

## 풀이

각 숫자를 십진수 문자열로 바꾼 다음, `a + b > b + a`일 때 `a`가 `b`보다 앞에 오도록 정렬합니다. 예를 들어, `"330" > "303"`이므로 `"3"`은 `"30"`보다 앞에 옵니다. 정렬된 순서대로 이어 붙이면 만들 수 있는 가장 큰 수가 됩니다.

비교 규칙은 교환 논증으로 설명할 수 있습니다. 이어 붙인 결과에서 이웃한 두 문자열이 `a`, `b`라면 두 순서가 결과에 기여하는 부분은 각각 `a + b`와 `b + a`이며, 그 바깥의 숫자들은 변하지 않습니다. 따라서 두 문자열 중 더 큰 결합을 선택하면 결과가 나빠지지 않습니다. 정렬은 모든 쌍에 이 순서를 적용하므로, 이웃한 두 문자열을 바꿔 결과를 더 크게 만들 수 없는 최대 결합을 만듭니다.

정렬 후 첫 문자열이 `"0"`이면 입력의 모든 숫자가 0이므로, 0을 반복한 문자열 대신 `"0"`을 반환합니다. 문제의 조건처럼 입력에는 0 이상의 정수가 하나 이상 있다고 가정합니다.

정렬은 `O(N log N)`번 비교하며, 각 비교에서 결합과 비교에 `O(L)`의 문자 작업이 필요합니다. 여기서 `L`은 입력 숫자 중 가장 긴 자릿수입니다. 최종 문자열 생성에는 `O(NL)` 시간과 공간이 듭니다.

## C++

```cpp
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

string solution(vector<int> numbers) {
    vector<string> values;
    values.reserve(numbers.size());
    for (int number : numbers) {
        values.push_back(to_string(number));
    }

    sort(values.begin(), values.end(), [](const string& a, const string& b) {
        return a + b > b + a;
    });

    if (values.front() == "0") {
        return "0";
    }

    string answer;
    for (const string& value : values) {
        answer += value;
    }
    return answer;
}
```
