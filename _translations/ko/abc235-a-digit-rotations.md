---
title: AtCoder ABC 235 A - Rotate
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC]
pin: false
lang: ko
translation_key: abc235-a-digit-rotations
permalink: /ko/posts/abc235-a-digit-rotations/
source_permalink: /posts/Atcoder-A-Rotate/
---

[문제: AtCoder ABC 235 A — Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_a) · [English](/posts/Atcoder-A-Rotate/) · [日本語](/ja/posts/abc235-a-digit-rotations/)

세 자리 십진수 `ABC`가 주어집니다. 여기서 `A`, `B`, `C`는 각각 백의 자리, 십의 자리, 일의 자리 숫자입니다. 숫자를 왼쪽으로 한 칸씩 회전해 얻는 세 수 `ABC`, `BCA`, `CAB`의 합을 구하면 됩니다. 회전은 숫자 세 개의 순서를 바꾸는 것이므로, 숫자가 반복되거나 `0`이 포함되어도 똑같이 처리합니다. 예를 들어 입력이 `123`이면 세 수는 `123`, `231`, `312`이고 합은 `666`입니다.

문자열의 각 문자를 숫자로 바꾼 뒤 각 회전의 자릿값을 계산합니다. 답은 `100A + 10B + C`, `100B + 10C + A`, `100C + 10A + B`의 합입니다. 세 자리 수의 최대값이 `999`이므로 합은 최대 `2997`이며 `int`로 충분합니다. 시간 복잡도와 추가 공간 복잡도는 모두 `O(1)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String digits = input.readLine();
        int a = digits.charAt(0) - '0';
        int b = digits.charAt(1) - '0';
        int c = digits.charAt(2) - '0';

        int answer = (100 * a + 10 * b + c)
                + (100 * b + 10 * c + a)
                + (100 * c + 10 * a + b);
        System.out.println(answer);
    }
}
```
