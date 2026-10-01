---
title: BOJ 11279 - 최대 힙
author: MINJUN PARK
date: 2021-12-23 03:42:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 힙, 코딩 인터뷰, BOJ, 최대 힙]
lang: ko
translation_key: boj-11279-max-heap
permalink: /ko/posts/boj-11279-max-heap/
pin: false
---

[BOJ 11279: 최대 힙](https://www.acmicpc.net/problem/11279)

최대 힙에서는 모든 부모 노드의 값이 자식 노드의 값보다 크거나 같으므로, 가장 큰 값이 항상 루트에 있다. 값을 삽입할 때는 배열 끝에 추가한 뒤 부모보다 큰 동안 위로 올린다. 최댓값을 삭제할 때는 루트에 마지막 값을 옮기고, 더 큰 자식과 교환하며 아래로 내린다. 각 연산은 루트에서 리프까지의 경로를 최대 한 번 따라가므로 삽입과 삭제의 시간 복잡도는 각각 `O(log N)`이다. 원시형 배열을 사용하므로 공간 복잡도는 `O(N)`이며, 배열 크기는 최대 삽입 명령 수에 맞춰 정한다.

입력은 공백으로 구분된 토큰으로 읽고, 결과는 `StringBuilder`에 모아 출력한다. 비어 있는 힙에서 삭제를 요청하면 문제의 조건에 따라 `0`을 출력한다.

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
    int[] heap = new int[n + 1];
    int size = 0;
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < n; i++) {
      int value = nextInt();
      if (value == 0) {
        if (size == 0) {
          output.append(0).append('\n');
        } else {
          output.append(heap[1]).append('\n');
          heap[1] = heap[size--];

          int parent = 1;
          while (parent * 2 <= size) {
            int child = parent * 2;
            if (child + 1 <= size && heap[child + 1] > heap[child]) {
              child++;
            }
            if (heap[parent] >= heap[child]) {
              break;
            }
            int temp = heap[parent];
            heap[parent] = heap[child];
            heap[child] = temp;
            parent = child;
          }
        }
      } else {
        int child = ++size;
        while (child > 1 && heap[child / 2] < value) {
          heap[child] = heap[child / 2];
          child /= 2;
        }
        heap[child] = value;
      }
    }

    System.out.print(output);
  }
}
```
