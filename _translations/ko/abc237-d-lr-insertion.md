---
title: AtCoder ABC 237 D — LR 삽입
author: MINJUN PARK
date: 2022-01-30 21:50:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 237]
pin: false
lang: ko
translation_key: abc237-d-lr-insertion
permalink: /ko/posts/abc237-d-lr-insertion/
source_permalink: /posts/Atcoder-D-LR-insertion/
---

[문제: AtCoder ABC 237 D — LR insertion](https://atcoder.jp/contests/abc237/tasks/abc237_d) · [English](/posts/Atcoder-D-LR-insertion/) · [日本語](/ja/posts/abc237-d-lr-insertion/)

최대 500,000개의 노드를 재귀 중위 순회하면 호출 스택이 넘칠 수 있습니다. 대신 덱(deque)을 사용해 답을 직접 구성합니다. 먼저 `N`을 넣고, `S`를 오른쪽에서 왼쪽으로 처리합니다. 각 인덱스 `i`에 대해 `S[i]`가 `L`이면 `i`를 덱의 뒤에 추가하고, 그렇지 않으면 앞에 추가합니다. 처리가 끝나면 덱이 필요한 순서가 됩니다.

이 방법은 삽입을 역순으로 되돌리는 것입니다. 마지막에 삽입되는 값은 `N`이므로 이를 덱의 시작으로 둡니다. 그보다 앞선 각 `i`에 대해 `L`은 `i + 1`이 `i` 바로 앞에 삽입되었다는 뜻이므로 `i`를 뒤쪽 끝에 둡니다. `R`은 `i + 1`이 `i` 바로 뒤에 삽입되었다는 뜻이므로 `i`를 앞쪽 끝에 둡니다. 각 연산은 해당 끝에 원소를 하나씩 추가하여 필요한 상대 순서를 유지합니다. 각 값을 한 번씩만 추가하므로 시간 복잡도와 공간 복잡도는 모두 `O(N)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        String s = input.readLine().trim();

        Deque<Integer> order = new ArrayDeque<>();
        order.addLast(n);
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == 'L') {
                order.addLast(i);
            } else {
                order.addFirst(i);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int value : order) {
            if (answer.length() > 0) {
                answer.append(' ');
            }
            answer.append(value);
        }
        System.out.println(answer);
    }
}
```
