---
title: AtCoder. ABC 235 C The Kth Time Query
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    ABC contest
  ]
pin: false
lang: en
translation_key: abc235-c-kth-time-query
---


[Problem: AtCoder ABC 235 C — The Kth Time Query](https://atcoder.jp/contests/abc235/tasks/abc235_c)

For each query `(x, k)`, find the 1-indexed position of the `k`th occurrence of `x` in the array. If `x` occurs fewer than `k` times, return `-1`.

Build a map from each value to the list of its positions. Scan the array from left to right and append `i + 1` to that value's list. Because positions are appended in increasing order, the list is already ordered, and the answer is the element at index `k - 1`. If the value is absent or its list has fewer than `k` entries, output `-1`.

This handles repeated values naturally: the first and last entries answer `k = 1` and `k` equal to the total number of occurrences, respectively. A missing value and any `k` larger than the occurrence count both produce `-1`.

With expected constant-time hash-map access, constructing the position lists and answering all queries takes expected `O(N + Q)` time. The position lists store `N` indices; the map has at most `N` keys. The output buffer uses `O(Q)` space, so total auxiliary space is `O(N + Q)`.

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
