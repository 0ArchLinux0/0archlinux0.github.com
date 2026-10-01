---
title: AtCoder ABC 235 C - The Kth Time Query
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC]
pin: false
lang: ko
translation_key: abc235-c-kth-time-query
permalink: /ko/posts/abc235-c-kth-time-query/
source_permalink: /posts/Atcoder-C-The-Kth-Time-Query/
---

[문제: AtCoder ABC 235 C — The Kth Time Query](https://atcoder.jp/contests/abc235/tasks/abc235_c) · [English](/posts/Atcoder-C-The-Kth-Time-Query/) · [日本語](/ja/posts/abc235-c-kth-time-query/)

각 질의 `(x, k)`에 대해 배열에서 `x`가 `k`번째로 등장하는 위치(1부터 세는 위치)를 구합니다. `x`의 등장 횟수가 `k`보다 적으면 `-1`을 출력합니다.

값마다 등장 위치를 저장하는 맵을 만듭니다. 배열을 왼쪽에서 오른쪽으로 순회하면서 해당 값의 리스트에 `i + 1`을 추가합니다. 위치가 증가하는 순서대로 추가되므로 리스트는 이미 정렬되어 있으며, 답은 인덱스 `k - 1`의 값입니다. 해당 값이 없거나 리스트 크기가 `k`보다 작으면 `-1`을 출력합니다.

반복해서 등장하는 값도 같은 방식으로 처리됩니다. 첫 번째 위치는 `k = 1`일 때, 마지막 위치는 `k`가 전체 등장 횟수와 같을 때 답이 됩니다. 없는 값과 등장 횟수보다 큰 `k`에 대해서는 모두 `-1`입니다.

해시 맵 접근이 평균적으로 상수 시간이라고 가정하면, 위치 목록 구성과 모든 질의 처리를 합쳐 예상 시간 복잡도는 `O(N + Q)`입니다. 위치 리스트가 `N`개의 인덱스를 저장하고, 맵의 키 수는 최대 `N`개입니다. 출력 버퍼까지 포함한 보조 공간 복잡도는 `O(N + Q)`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static final class FastScanner {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens;

        int nextInt() throws IOException {
            while (tokens == null || !tokens.hasMoreTokens()) {
                tokens = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokens.nextToken());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int q = input.nextInt();
        Map<Integer, ArrayList<Integer>> positions = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            int value = input.nextInt();
            positions.computeIfAbsent(value, ignored -> new ArrayList<>()).add(i);
        }

        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < q; query++) {
            int value = input.nextInt();
            int k = input.nextInt();
            ArrayList<Integer> occurrences = positions.get(value);
            if (occurrences == null || occurrences.size() < k) {
                answer.append(-1).append('\n');
            } else {
                answer.append(occurrences.get(k - 1)).append('\n');
            }
        }
        System.out.print(answer);
    }
}
```
