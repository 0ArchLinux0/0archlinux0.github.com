---
title: AtCoder ABC 237 C — kasaka
author: MINJUN PARK
date: 2022-01-30 21:20:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 237]
pin: false
lang: ko
translation_key: abc237-c-kasaka
permalink: /ko/posts/abc237-c-kasaka/
source_permalink: /posts/Atcoder-C-kasaka/
---

[문제: AtCoder ABC 237 C — kasaka](https://atcoder.jp/contests/abc237/tasks/abc237_c)
[English](/posts/Atcoder-C-kasaka/) · [한국어] · [日本語](/ja/posts/abc237-c-kasaka/)

문자열 앞에 `a`만 덧붙일 수 있으므로 문자열 끝의 문자는 바꿀 수 없습니다. 문자열 앞부분에 연속된 `a`가 몇 개인지(`leadingA`), 뒷부분에 연속된 `a`가 몇 개인지(`trailingA`) 셉니다. `leadingA > trailingA`라면 앞의 `a`들을 뒤에서 맞춰 줄 만큼의 `a`가 부족하므로 회문을 만들 수 없습니다.

그렇지 않다면 `trailingA - leadingA`개의 `a`를 앞에 추가해 두 연속 구간을 균형 맞출 수 있습니다. 그러면 앞뒤의 `a` 구간을 제외한 가운데 부분이 이미 회문이어야 합니다. 양쪽에서 `a`를 건너뛰고 남은 부분을 투 포인터로 비교합니다. 두 포인터가 교차하면 가운데 부분이 비었거나 문자가 하나뿐이므로 회문입니다.

문자열 전체가 `a`인 경우(가운데가 빈 경우)와 양 끝에 `a`가 없는 경우(문자열 전체를 검사)도 처리합니다. 시간 복잡도는 `O(|S|)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();

        int left = 0;
        while (left < s.length() && s.charAt(left) == 'a') {
            left++;
        }

        int right = s.length() - 1;
        while (right >= 0 && s.charAt(right) == 'a') {
            right--;
        }

        int leadingA = left;
        int trailingA = s.length() - 1 - right;
        if (leadingA > trailingA) {
            System.out.println("No");
            return;
        }

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                System.out.println("No");
                return;
            }
            left++;
            right--;
        }
        System.out.println("Yes");
    }
}
```
