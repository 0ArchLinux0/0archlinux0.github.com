---
title: Programmers — Largest Number
author: MINJUN PARK
date: 2021-12-27 00:56:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, Coding Interview, Programmers, Largest Number, 가장 큰 수, 프로그래머스]
pin: false
lang: en
translation_key: programmers-largest-number
permalink: /posts/Programmers-Bigest-Number/
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/42746>

## Approach

Convert each number to a decimal string, then sort the strings with the rule that `a` comes before `b` exactly when `a + b > b + a`. For example, `"3"` precedes `"30"` because `"330" > "303"`. After sorting, concatenating in order produces the largest possible number.

This comparator is justified by an exchange argument. For any two neighboring strings `a` and `b` in a concatenation, the two possible orders contribute either `a + b` or `b + a`; all surrounding digits remain unchanged. Choosing the larger of these two therefore never makes the result worse. Sorting applies this pairwise ordering throughout, so no adjacent exchange can improve the result and the resulting concatenation is maximal.

If the first sorted string is `"0"`, every input number is zero, so return `"0"` rather than a string of repeated zeroes. The solution assumes the input contains at least one non-negative integer, as in the problem constraints.

Sorting performs `O(N log N)` comparisons, each taking `O(L)` character work for concatenation and comparison, where `L` is the maximum number of digits in an input number. The final output takes `O(NL)` time and space.

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
