---
title: AtCoder. 002 괄호 도감 (3)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Encyclopedia of Parentheses,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-002-parentheses
permalink: /ko/posts/atcoder-typical90-002-parentheses/
---

괄호 문자열을 왼쪽부터 하나씩 만듭니다. 어떤 접두사에서도 닫는 괄호 수가 여는 괄호 수보다 많아서는 안 됩니다. 그렇지 않으면 뒤에 어떤 문자열을 붙여도 균형 잡힌 문자열이 될 수 없습니다. 길이 `N`인 완전한 균형 괄호 문자열에는 여는 괄호가 정확히 `N/2`개 있습니다.

따라서 재귀에서는 여는 괄호를 `N/2`개보다 적게 사용했을 때 `(`를 추가하고, `close < open`일 때만 `)`를 추가합니다. 접두사에서 두 괄호 수가 같아도 이후에 여는 괄호를 추가할 수 있으므로 괜찮습니다. 길이가 `N`에 도달하면 앞의 규칙에 의해 문자열은 균형을 이루므로 출력합니다. `(`를 `)`보다 먼저 시도하므로 유효한 문자열은 사전순으로 생성됩니다. `N`이 홀수이면 끝에서 두 수가 같아지는 경우가 없어 결과가 출력되지 않습니다.

접두사 균형 불변식은 `0 <= close <= open <= N/2`입니다. 각 결과는 한 번씩 생성되며, 시간 복잡도는 전체 출력 크기에 비례합니다. 재귀 호출과 현재 문자열을 위한 작업 공간은 `O(N)`이며, 버퍼에 저장하는 출력 공간은 별도입니다.

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_b)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int n;
    private static final StringBuilder current = new StringBuilder();
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        n = input.nextInt();
        generate(0, 0);
        System.out.print(output);
    }

    private static void generate(int open, int close) {
        if (current.length() == n) {
            if (open == close) {
                output.append(current).append('\n');
            }
            return;
        }

        if (open < n / 2) {
            current.append('(');
            generate(open + 1, close);
            current.setLength(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            generate(open, close + 1);
            current.setLength(current.length() - 1);
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
