---
title: AtCoder. 008 AtCounter (4)
author: MINJUN PARK
date: 2021-12-30 02:47:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    AtCounter,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-008-atcounter
permalink: /ko/posts/atcoder-typical90-008-atcounter/
---

문자열 `S`가 주어졌을 때 `atcoder`와 같은 부분 수열의 개수를 셉니다. 부분 수열은 문자의 순서를 바꾸지 않고 0개 이상의 문자를 삭제해 만들며, 선택한 위치가 다르면 서로 다른 부분 수열로 셉니다.

`dp[j]`를 지금까지 처리한 문자들로 `atcoder`의 처음 `j`개 문자를 만드는 방법의 수라고 합니다. 초기 상태의 `dp[0] = 1`은 빈 접두사를 만드는 한 가지 방법을 나타내며, 나머지는 모두 0입니다. 입력 문자를 하나씩 처리하면서 목표 문자열에서 일치하는 위치를 오른쪽부터 확인하고 `dp[j]`를 `dp[j + 1]`에 더합니다. 값은 `1,000,000,007`로 나눈 나머지로 유지합니다. 오른쪽에서 왼쪽으로 갱신하면 현재 입력 문자의 위치를 두 번 사용할 수 없습니다. 원본인 `dp[j]`에는 아직 현재 문자를 처리하기 전의 부분 수열만 들어 있기 때문입니다.

불변식은 `S`의 접두사를 처리한 뒤 `dp[j]`가 그 접두사 안에서 목표 문자열의 처음 `j`개 문자를 만드는 인덱스 선택 수와 정확히 같다는 것입니다. 정답은 `dp[7]`입니다. 입력 문자마다 목표 문자열의 일곱 위치를 확인하므로 시간 복잡도는 `O(7N)`, 추가 공간 복잡도는 `O(1)`입니다.

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_h)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long MOD = 1_000_000_007L;
    private static final String TARGET = "atcoder";

    public static void main(String[] args) throws IOException {
        BufferedInputStream in = new BufferedInputStream(System.in);
        int n = 0;
        int c;
        while ((c = in.read()) <= ' ') {
            if (c == -1) return;
        }
        do {
            n = n * 10 + c - '0';
            c = in.read();
        } while (c > ' ');

        char[] s = new char[n];
        int length = 0;
        while (length < n) {
            c = in.read();
            if (c > ' ') s[length++] = (char) c;
        }

        long[] dp = new long[TARGET.length() + 1];
        dp[0] = 1;
        for (char ch : s) {
            for (int j = TARGET.length() - 1; j >= 0; j--) {
                if (ch == TARGET.charAt(j)) {
                    dp[j + 1] = (dp[j + 1] + dp[j]) % MOD;
                }
            }
        }
        System.out.println(dp[TARGET.length()]);
    }
}
```
