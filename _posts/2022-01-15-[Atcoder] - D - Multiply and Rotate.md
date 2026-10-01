---
title: AtCoder. ABC 235 D - Multiply and Rotate
author: MINJUN PARK
date: 2022-01-16 02:00:00 +0900
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
translation_key: abc235-d-multiply-and-rotate
---

[Problem: AtCoder ABC 235 D — Multiply and Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_d) · [한국어](/ko/posts/abc235-d-multiply-and-rotate/) · [日本語](/ja/posts/abc235-d-multiply-and-rotate/)

Starting from `1`, apply either operation: multiply the current integer by `A`, or move its last decimal digit to the front. Rotation is allowed only when the number has at least two digits and does not end in `0`. Find the minimum number of operations needed to reach `N`; if it is impossible, print `-1`. In particular, `120` cannot be rotated: although moving its final `0` to the front and reading the result as an integer would give `12`, that operation is forbidden by the problem statement.

Treat each integer as a vertex in an unweighted directed graph, with an edge for each valid operation. Breadth-first search from `1` visits states in nondecreasing order of operation count, so the first distance assigned to `N` is the minimum. A `dist` array both records that distance and prevents revisiting states. Under the constraints `A, N ≤ 10^6`, it is sufficient to search states from `1` through `10^6`: products are enqueued only when they are at most `10^6`, and a rotation of a searched state is also at most `10^6`. Thus the array and queue each need `10^6 + 1` entries. Each state has at most two outgoing operations, giving `O(10^6)` time and space in the bounded state graph.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final int LIMIT = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String[] values = input.readLine().split(" ");
        int a = Integer.parseInt(values[0]);
        int target = Integer.parseInt(values[1]);

        int[] distance = new int[LIMIT + 1];
        Arrays.fill(distance, -1);
        int[] queue = new int[LIMIT + 1];
        int head = 0;
        int tail = 0;

        distance[1] = 0;
        queue[tail++] = 1;

        while (head < tail) {
            int current = queue[head++];
            if (current == target) {
                System.out.println(distance[current]);
                return;
            }

            long product = (long) current * a;
            if (product <= LIMIT && distance[(int) product] == -1) {
                distance[(int) product] = distance[current] + 1;
                queue[tail++] = (int) product;
            }

            if (current >= 10 && current % 10 != 0) {
                int place = 1;
                while (place <= current / 10) {
                    place *= 10;
                }
                int rotated = current % 10 * place + current / 10;
                if (distance[rotated] == -1) {
                    distance[rotated] = distance[current] + 1;
                    queue[tail++] = rotated;
                }
            }
        }

        System.out.println(-1);
    }
}
```
