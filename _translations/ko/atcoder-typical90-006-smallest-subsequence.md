---
title: AtCoder Typical 90 006 — 가장 작은 부분 수열 (5)
author: MINJUN PARK
date: 2021-12-30 02:45:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Smallest Subsequence,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-006-smallest-subsequence
permalink: /ko/posts/atcoder-typical90-006-smallest-subsequence/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_f)

문자열 `S`에서 문자의 순서를 바꾸지 않고 정확히 `K`개를 골라 사전순으로 가장 작은 부분 수열을 만듭니다.

정확히 `N - K`개의 문자를 버릴 수 있습니다. `S`를 왼쪽부터 훑으면서 선택한 문자를 스택에 저장합니다. 현재 문자가 스택의 마지막 문자보다 작고 아직 버릴 수 있는 문자가 남아 있다면 마지막 문자를 제거합니다. 이는 사전순에 대한 교환입니다. 앞쪽 위치의 더 큰 문자를 현재의 더 작은 문자로 바꾸면 결과가 더 작아지고, 제거한 문자는 더 이상 접두부를 개선할 수 없습니다. 조건이 성립하는 동안 계속 꺼낸 다음 현재 문자를 스택에 추가합니다.

버릴 수 있는 개수의 관리가 중요합니다. `N - K`개를 이미 버렸다면 더는 문자를 제거할 수 없습니다. 순회를 끝낸 뒤에도 버릴 개수가 남아 있다면 스택의 끝에서 남은 개수만큼 제거해야 합니다. 그러면 스택에는 정확히 `K`개의 문자가 남습니다. 각 문자는 한 번 추가되고 최대 한 번 제거되므로 시간 복잡도와 공간 복잡도는 모두 `O(N)`입니다.

```java
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String[] firstLine = input.readLine().trim().split("\\s+");
    int n = Integer.parseInt(firstLine[0]);
    int k = Integer.parseInt(firstLine[1]);
    String s = input.readLine().trim();

    char[] stack = new char[n];
    int size = 0;
    int removalsLeft = n - k;

    for (int i = 0; i < n; i++) {
      char current = s.charAt(i);
      while (removalsLeft > 0 && size > 0 && stack[size - 1] > current) {
        size--;
        removalsLeft--;
      }
      stack[size++] = current;
    }

    size -= removalsLeft;
    System.out.println(new String(stack, 0, size));
  }
}
```
