---
title: AtCoder ARC 135 B - Sum of Three Terms
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ARC]
pin: false
lang: ko
translation_key: arc135-b-sum-three-terms
permalink: /ko/posts/arc135-b-sum-three-terms/
source_permalink: /posts/Atcoder-B-Sum-of-Three-Terms/
---

[문제: AtCoder ARC 135 B — Sum of Three Terms](https://atcoder.jp/contests/arc135/tasks/arc135_b) · [English](/posts/Atcoder-B-Sum-of-Three-Terms/) · [日本語](/ja/posts/arc135-b-sum-three-terms/)

길이 `N`인 배열 `A`가 주어집니다. 길이 `N + 2`인 음이 아닌 정수 배열 `B`가 존재하여 모든 `0 <= i < N`에 대해

`A[i] = B[i] + B[i + 1] + B[i + 2]`

를 만족하는지 판정합니다. 존재하면 임의의 배열 하나와 함께 `Yes`를, 존재하지 않으면 `No`를 출력합니다.

연속한 두 식을 빼면 다음을 얻습니다.

`A[i + 1] - A[i] = B[i + 3] - B[i]`.

따라서 `B`의 인덱스를 3으로 나눈 나머지가 같은 원소들은 각각 독립적인 사슬을 이룹니다. 각 사슬의 첫 값을 정하면 이후 값은 `A`의 차이로 결정됩니다. 나머지가 `r`인 사슬에서 이 차이들을 누적한 합을 `prefix[r]`라 하고, 초기값 0도 포함해 그 최솟값을 `minPrefix[r]`라 합시다. 사슬의 모든 값은 `B[r] + prefix[r]`이므로, 모든 값을 음이 아니게 하려면 `B[r] >= -minPrefix[r]`여야 합니다.

나머지 0, 1인 사슬의 시작값은 가능한 최솟값으로 정합니다. 즉 `B[0] = -minPrefix[0]`, `B[1] = -minPrefix[1]`입니다. 첫 번째 식은 `B[0] + B[1] + B[2] = A[0]`이므로 `B[2] = A[0] - B[0] - B[1]`로 결정됩니다. `B[2] < -minPrefix[2]`이면 해가 없습니다. 세 사슬이 요구하는 최소 시작값의 합이 이미 `A[0]`보다 크기 때문입니다. 그렇지 않으면 세 사슬의 모든 값이 음이 아니며, 점화식으로 구성한 `B`는 유효합니다.

`N = 1`인 경우도 같은 방식으로 처리됩니다. 차이가 없으므로 세 사슬의 최소 누적합은 모두 0이고, `A[0]`을 음이 아닌 세 값으로 나누면 됩니다. 특히 `A[0] = 0`이면 구성 결과는 모두 0입니다.

인접한 `A` 원소를 한 번씩 처리하므로 시간 복잡도는 `O(N)`입니다. `A`와 구성한 배열을 저장하므로 공간 복잡도는 `O(N)`입니다. 차이, 누적합, 구성 값에는 `long`을 사용합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        long[] a = new long[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(tokens.nextToken());
        }

        long[] prefix = new long[3];
        long[] minPrefix = new long[3];
        for (int i = 0; i + 1 < n; i++) {
            int residue = i % 3;
            prefix[residue] += a[i + 1] - a[i];
            minPrefix[residue] = Math.min(minPrefix[residue], prefix[residue]);
        }

        long[] b = new long[n + 2];
        b[0] = -minPrefix[0];
        b[1] = -minPrefix[1];
        b[2] = a[0] - b[0] - b[1];
        if (b[2] < -minPrefix[2]) {
            System.out.println("No");
            return;
        }

        for (int i = 0; i + 3 < n + 2; i++) {
            b[i + 3] = b[i] + a[i + 1] - a[i];
        }

        StringBuilder output = new StringBuilder("Yes\n");
        for (int i = 0; i < b.length; i++) {
            if (i > 0) {
                output.append(' ');
            }
            output.append(b[i]);
        }
        System.out.println(output);
    }
}
```
