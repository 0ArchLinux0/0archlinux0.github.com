---
title: LeetCode. 10. Regular Expression Matching
author: MINJUN PARK
date: 2021-12-09 02:28:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Regular Expression Matching]
pin: false
lang: ko
translation_key: leetcode-10-regex-matching
permalink: /ko/posts/leetcode-10-regex-matching/
---

[문제](https://leetcode.com/problems/regular-expression-matching/)

## 동적 계획법

이 문제에서 사용하는 패턴 연산자는 두 가지뿐입니다. `.`은 임의의 문자 하나와 일치하고, `*`는 바로 앞에 있는 원자를 0회 이상 반복한 것과 일치합니다. 따라서 `*`는 바로 앞의 원자와 함께 처리하며, 단독으로 쓰이거나 패턴의 더 앞부분에 적용되지 않습니다. 문제에서 모든 패턴은 유효하다고 보장하므로 각 `*` 앞에는 원자가 있습니다.

`dp[i][j]`를 `s`의 앞에서 `i`개 문자와 `p`의 앞에서 `j`개 문자가 일치하는지를 나타내는 값으로 정의합니다. 답은 `dp[s.length()][p.length()]`입니다. 빈 접두사는 서로 일치하므로 `dp[0][0]`은 참입니다. 빈 문자열과 일치하는 비어 있지 않은 패턴은 마지막 원자-별표 쌍을 건너뛸 수 있을 때뿐입니다. 각 유효한 `x*` 쌍에 대해 `dp[0][j] = dp[0][j - 2]`로 초기화합니다.

패턴 문자가 `*`가 아니라면 현재 입력 문자와 일치해야 합니다(같은 리터럴 문자이거나 `.`이어야 함). 또한 그 앞의 접두사끼리 이미 일치해야 합니다. 즉, `dp[i][j] = matches(s[i - 1], p[j - 1]) && dp[i - 1][j - 1]`입니다.

`x*` 쌍은 두 가지 경우로 처리합니다. `x`를 0회 사용하는 경우에는 `dp[i][j - 2]`를 사용합니다. 또는 일치하는 입력 문자 하나를 소비하고 같은 패턴 쌍을 더 반복할 수 있도록 남겨 둡니다. 이 경우 조건은 `matches(s[i - 1], p[j - 2]) && dp[i - 1][j]`입니다.

테이블을 계산하는 시간 복잡도는 `O(|s||p|)`, 공간 복잡도도 `O(|s||p|)`입니다.

## Java

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char patternChar = p.charAt(j - 1);
                if (patternChar == '*') {
                    char atom = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2]
                            || (matches(s.charAt(i - 1), atom) && dp[i - 1][j]);
                } else {
                    dp[i][j] = matches(s.charAt(i - 1), patternChar)
                            && dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

    private boolean matches(char input, char atom) {
        return atom == '.' || input == atom;
    }
}
```
