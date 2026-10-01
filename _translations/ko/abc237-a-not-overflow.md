---
title: AtCoder ABC 237 A — Not Overflow
author: MINJUN PARK
date: 2022-01-30 21:05:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 237]
pin: false
lang: ko
translation_key: abc237-a-not-overflow
permalink: /ko/posts/abc237-a-not-overflow/
source_permalink: /posts/Atcoder-A-Not-Overflow/
---

[문제: AtCoder ABC 237 A — Not Overflow](https://atcoder.jp/contests/abc237/tasks/abc237_a)
[English](/posts/Atcoder-A-Not-Overflow/) · [한국어] · [日本語](/ja/posts/abc237-a-not-overflow/)

입력값은 32비트 부호 있는 정수의 범위보다 클 수 있으므로 `long`으로 읽어야 합니다. 32비트 부호 있는 정수의 범위는 `Integer.MIN_VALUE`(`-2^31`)부터 `Integer.MAX_VALUE`(`2^31 - 1`)까지이며 양 끝값도 포함됩니다. `long`으로 읽은 값을 두 경계와 비교해 이 범위 안에 있으면 `Yes`, 아니면 `No`를 출력합니다. 양 끝값을 포함하는 비교를 사용하므로 경계값은 받아들이고, 범위 바로 바깥의 값은 거부합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        long value = Long.parseLong(input.readLine().trim());
        System.out.println(Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE ? "Yes" : "No");
    }
}
```
