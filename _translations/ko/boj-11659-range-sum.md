---
title: BOJ 11659 - 구간 합 구하기 4
author: MINJUN PARK
date: 2021-11-17 14:11:00 +0900
categories: [Record, Code]
tags: [코드, Java, 알고리즘, 누적 합, BOJ, 구간 합]
lang: ko
translation_key: boj-11659-range-sum
permalink: /ko/posts/boj-11659-range-sum/
pin: false
---

[BOJ 11659: 구간 합 구하기 4](https://www.acmicpc.net/problem/11659)

배열의 누적 합 배열을 만든다. `prefix[0] = 0`으로 두고 `prefix[i + 1] = prefix[i] + value[i]`로 정의한다. 1부터 시작하는 인덱스를 사용하는 입력에서 양 끝이 포함되는 구간 `[a, b]`의 합은 `prefix[b] - prefix[a - 1]`이다. 이 식에서 누적 합 배열의 인덱스를 사용하므로 Java의 0부터 시작하는 배열 인덱스와 입력 위치의 차이를 반영한다.

누적 합 배열은 `O(N)` 시간에 만들 수 있고, `M`개의 질의는 각각 `O(1)` 시간에 답할 수 있다. 따라서 전체 시간 복잡도는 `O(N + M)`, 공간 복잡도는 `O(N)`이다. `BufferedReader`와 `StringTokenizer`로 공백과 줄바꿈에 관계없이 토큰을 읽고, `StringBuilder`에 답을 모아 출력한다.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static BufferedReader reader =
      new BufferedReader(new InputStreamReader(System.in));
  private static StringTokenizer tokenizer;

  private static int nextInt() throws IOException {
    while (tokenizer == null || !tokenizer.hasMoreTokens()) {
      tokenizer = new StringTokenizer(reader.readLine());
    }
    return Integer.parseInt(tokenizer.nextToken());
  }

  public static void main(String[] args) throws IOException {
    int n = nextInt();
    int m = nextInt();
    int[] prefix = new int[n + 1];

    for (int i = 0; i < n; i++) {
      prefix[i + 1] = prefix[i] + nextInt();
    }

    StringBuilder output = new StringBuilder();
    for (int i = 0; i < m; i++) {
      int a = nextInt();
      int b = nextInt();
      output.append(prefix[b] - prefix[a - 1]).append('\n');
    }
    System.out.print(output);
  }
}
```
