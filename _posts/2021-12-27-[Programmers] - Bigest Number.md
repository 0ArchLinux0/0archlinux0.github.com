---
title: Programmers. Bigest Number
author: MINJUN PARK
date: 2021-12-27 00:56:00 +0900
categories: [Programmers, Java]
tags:
  [
    Java,
    Algorithm,
    PriorityQueue,
    Coding Interview,
    Programmers,
    Bigest Number,
    가장 큰 수,
    프로그래머스,
  ]
pin: false
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/42746>

<br>

```C++
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool compare(int a, int b) {
    string sa = to_string(a);
    string sb = to_string(b);
    return sa + sb > sb + sa;
}

string solution(vector<int> numbers) {
    string answer = "";
    sort(numbers.begin(), numbers.end(), compare);
    if(numbers.at(0) == 0) return "0";
    for(int num: numbers) answer += to_string(num);
    return answer;
}
```
