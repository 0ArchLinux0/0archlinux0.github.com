---
title: BOJ. LCS 2 (9252)
author: MINJUN PARK
date: 2022-01-13 02:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    LCS 2,
  ]
pin: false
lang: ko
translation_key: boj-9252-lcs-reconstruction
permalink: /ko/posts/boj-9252-lcs-reconstruction/
---

[문제 링크](https://www.acmicpc.net/problem/9252)

`dp[i][j]`를 문자열 `a`의 앞 `i`개 문자와 문자열 `b`의 앞 `j`개 문자 사이 최장 공통 부분 수열의 길이라고 정의합니다. 빈 접두사의 LCS 길이는 0입니다. 두 접두사의 마지막 문자가 같으면 그 문자를 앞부분의 LCS에 추가할 수 있습니다. 다르면 마지막 문자 중 적어도 하나를 제외해야 합니다.

```text
dp[0][j] = dp[i][0] = 0

dp[i][j] = dp[i - 1][j - 1] + 1                         if a[i - 1] == b[j - 1]
           max(dp[i - 1][j], dp[i][j - 1])              otherwise
```

작은 접두사부터 표를 채웁니다. LCS 하나를 복원할 때는 `(n, m)`에서 역방향으로 이동합니다. 두 문자가 같으면 그 문자를 결과에 기록하고 대각선으로 이동합니다. 다르면 LCS 길이가 같은 이웃 칸으로 이동합니다. 표의 불변식에 따라 대각선 일치 한 번은 결과 문자 하나를 만들고, 대각선이 아닌 이동은 최선의 길이를 유지합니다. 기록한 문자를 뒤집으면 길이가 `dp[n][m]`인 LCS를 얻습니다. 두 이웃의 길이가 같으면 어느 쪽으로 이동해도 되므로, 문자가 반복되는 경우에도 하나의 정답을 올바르게 복원할 수 있습니다. 시간 복잡도는 `O(NM)`, 공간 복잡도는 `O(NM)`입니다. 길이를 출력한 뒤 LCS 한 개를 출력하며, 길이가 0이면 두 번째 줄은 빈 줄입니다.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String a = input.readLine();
        String b = input.readLine();
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder reversed = new StringBuilder();
        int i = n;
        int j = m;
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                reversed.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        System.out.println(dp[n][m]);
        System.out.println(reversed.reverse());
    }
}
```
