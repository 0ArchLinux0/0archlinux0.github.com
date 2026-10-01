---
title: BOJ 1655 - 가운데를 말해요
author: MINJUN PARK
date: 2021-12-24 04:53:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 힙, 코딩 인터뷰, BOJ, 최대 힙, 가운데를 말해요]
lang: ko
translation_key: boj-1655-running-median
permalink: /ko/posts/boj-1655-running-median/
pin: false
---

[백준 1655번: 가운데를 말해요](https://www.acmicpc.net/problem/1655)

지금까지 입력된 수를 두 개의 우선순위 큐에 나누어 저장한다. `lower`는 작은 절반을 담는 최대 힙이고, `upper`는 큰 절반을 담는 최소 힙이다. 다음 두 불변식을 유지한다.

- `lower`의 모든 값은 `upper`의 모든 값보다 작거나 같다.
- `lower`의 원소 수는 `upper`와 같거나, 정확히 하나 더 많다.

각 수를 값의 범위에 맞는 힙에 넣은 뒤, 필요하면 한 힙의 루트 원소를 다른 힙으로 옮겨 크기 불변식을 복구한다. 그러면 `lower`의 루트가 항상 아래쪽 중앙값이다. 접두 구간의 길이가 홀수이면 `lower`에 원소가 하나 더 있으므로 루트가 중앙 원소다. 길이가 짝수이면 두 힙의 크기가 같고, `lower`의 루트는 가운데 두 값 중 작은 값이므로 문제에서 요구하는 아래쪽 중앙값이다.

모든 입력값을 두 힙에 각각 한 번씩만 저장하므로 공간 복잡도는 `O(N)`이다. 삽입과 필요할 때의 재균형 이동은 각각 `O(log N)`이므로 전체 시간 복잡도는 `O(N log N)`이다. 공백으로 구분된 정수 입력은 버퍼를 사용하는 바이트 리더로 읽는다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
  private static final class FastScanner {
    private final BufferedInputStream input = new BufferedInputStream(System.in);
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int position = 0;

    private int read() throws IOException {
      if (position == length) {
        length = input.read(buffer);
        position = 0;
        if (length == -1) return -1;
      }
      return buffer[position++];
    }

    int nextInt() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);

      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value * sign;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner();
    int n = scanner.nextInt();
    PriorityQueue<Integer> lower =
        new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> upper = new PriorityQueue<>();
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < n; i++) {
      int value = scanner.nextInt();
      if (lower.isEmpty() || value <= lower.peek()) {
        lower.add(value);
      } else {
        upper.add(value);
      }

      if (lower.size() > upper.size() + 1) {
        upper.add(lower.poll());
      } else if (upper.size() > lower.size()) {
        lower.add(upper.poll());
      }
      output.append(lower.peek()).append('\n');
    }

    System.out.print(output);
  }
}
```
