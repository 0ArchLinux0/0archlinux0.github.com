---
title: Codeforces Global Round 19 C. Andrew and Stones
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
		Codeforces Global Round,
    Codeforces,
    Andrew and Stones
  ]
pin: false
lang: en
translation_key: cf-1637-andrew-stones
---

[Problem: Codeforces 1637C — Andrew and Stones](https://codeforces.com/contest/1637/problem/C) · [한국어](/ko/posts/cf-1637-andrew-stones/) · [日本語](/ja/posts/cf-1637-andrew-stones/)

In one operation, choose indices `i < j < k` such that pile `j` has at least two stones, remove two stones from pile `j`, and add one stone to each of piles `i` and `k`. The goal is to leave stones only in the first and last piles.

## Minimum operations

Each operation is centered on exactly one interior pile and removes two stones from it. If a pile starts with `a[i]` stones and receives `r` stones from other operations, then emptying it requires `2 * moves[i] = a[i] + r`. Thus `moves[i] >= ceil(a[i] / 2)`, and the total number of operations is at least the sum of these ceilings.

For `n > 3`, the lower bound is attainable unless every interior pile initially contains exactly one stone. When at least one interior pile has two or more stones, use it to start a sequence of operations and exploit the freedom to choose non-adjacent outer indices: route one incoming stone to each odd remainder that needs parity correction, while sending any other recipient to an endpoint. It is never necessary to add a stone to the same odd pile more than once. This lets each pile be centered exactly `ceil(a[i] / 2)` times. If every interior pile is one, no operation is possible initially, so the goal cannot be reached.

For `n = 3`, the only triple is `(1, 2, 3)`. The middle pile loses two stones each time, so its parity never changes. An odd middle value can never reach zero; an even value requires exactly `a[1] / 2` operations.

The official constraints are `3 <= n <= 100000` and `1 <= a[i] <= 10^9`, with the sum of `n` over all test cases at most `100000`. For `n > 3`, let `sum` be the sum of the interior values and `oddCount` the number of odd interior values. Since `ceil(x / 2) = (x + (x mod 2)) / 2`, the answer is `(sum + oddCount) / 2`. The sum is accumulated in a `long`; it is at most `(n - 2) * 10^9 <= 10^14`, safely within `long`.

Examples:

- `n = 3`, middle `4` gives `2`; middle `3` gives `-1`.
- For `n = 4`, interior piles `[1, 1]` give `-1`; `[1, 2]` give `2`.
- For `n = 5`, interior piles `[1, 2, 3]` give `1 + 1 + 2 = 4`.

The algorithm scans each test case once: `O(n)` time and `O(n)` space for the input array. The problem guarantees `n >= 3`; smaller values are outside the input domain.

```java
import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int test = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        for (int t = 0; t < test; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] a = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (n == 3) {
                answer.append((a[1] & 1) == 1 ? -1 : a[1] / 2);
            } else {
                boolean allOne = true;
                int oddCount = 0;
                long sum = 0;
                for (int i = 1; i < n - 1; i++) {
                    if (a[i] != 1) allOne = false;
                    if ((a[i] & 1) == 1) oddCount++;
                    sum += a[i];
                }
                answer.append(allOne ? -1 : (sum + oddCount) / 2);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
```
