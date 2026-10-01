---
title: Codeforces 1637C - Andrew와 돌
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, Codeforces, Andrew와 돌]
pin: false
lang: ko
translation_key: cf-1637-andrew-stones
permalink: /ko/posts/cf-1637-andrew-stones/
source_permalink: /posts/Codeforces-Global-Round-19-C.-Andrew-and-Stones/
---

[문제: Codeforces 1637C — Andrew and Stones](https://codeforces.com/contest/1637/problem/C) · [English](/posts/Codeforces-Global-Round-19-C.-Andrew-and-Stones/) · [日本語](/ja/posts/cf-1637-andrew-stones/)

한 번의 연산에서 `i < j < k`인 세 인덱스를 고르고, 가운데 더미 `j`에 돌이 두 개 이상 있으면 그 더미에서 돌 두 개를 꺼내 `i`와 `k`에 하나씩 놓습니다. 목표는 첫 번째와 마지막 더미에만 돌을 남기는 것입니다.

## 최소 연산 횟수

각 연산은 내부 더미 하나를 중심으로 수행되어 그 더미의 돌 두 개를 없앱니다. 어떤 더미가 처음에 `a[i]`개를 가지고 있고 다른 연산에서 `r`개를 받았다면, 그 더미를 비우기 위해 `2 * moves[i] = a[i] + r`이어야 합니다. 따라서 `moves[i] >= ceil(a[i] / 2)`이며, 전체 연산 횟수는 이 올림값들의 합 이상입니다.

`n > 3`이면 모든 내부 더미가 처음부터 정확히 1개인 경우를 제외하고 이 하한을 달성할 수 있습니다. 내부 더미 중 적어도 하나에 돌이 2개 이상 있으면 그 더미를 이용해 연산을 시작하고, 인접하지 않은 바깥 인덱스도 선택할 수 있다는 점을 활용합니다. 짝홀성 보정이 필요한 홀수 나머지마다 돌 하나를 보내고, 다른 수신 위치는 양 끝 더미로 정합니다. 같은 홀수 더미에 돌을 두 번 이상 더할 필요는 없습니다. 따라서 각 더미는 정확히 `ceil(a[i] / 2)`번 중심이 됩니다. 모든 내부 더미가 1개이면 처음에 가능한 연산이 없어 목표에 도달할 수 없습니다.

`n = 3`이면 가능한 삼중 인덱스는 `(1, 2, 3)` 하나뿐입니다. 가운데 더미는 연산마다 돌 두 개를 잃으므로 짝홀이 바뀌지 않습니다. 가운데 값이 홀수이면 0이 될 수 없어 답은 `-1`이고, 짝수이면 정확히 `a[1] / 2`회가 필요합니다.

공식 제약은 `3 <= n <= 100000`, `1 <= a[i] <= 10^9`이며 모든 테스트 케이스의 `n` 합은 최대 `100000`입니다. `n > 3`인 경우 내부 값의 합 `sum`과 홀수인 값의 개수 `oddCount`를 계산합니다. `ceil(x / 2) = (x + (x mod 2)) / 2`이므로 답은 `(sum + oddCount) / 2`입니다. 합은 최대 `(n - 2) * 10^9 <= 10^14`이므로 `long`으로 안전하게 계산할 수 있습니다.

예시:

- `n = 3`에서 가운데 값이 4이면 답은 2, 3이면 `-1`입니다.
- `n = 4`에서 내부 값 `[1, 1]`이면 `-1`, `[1, 2]`이면 2입니다.
- `n = 5`에서 내부 값 `[1, 2, 3]`이면 `1 + 1 + 2 = 4`입니다.

각 테스트 케이스를 한 번 순회하므로 시간 복잡도는 `O(n)`이고, 입력 배열 저장 공간은 `O(n)`입니다. 문제에서 `n >= 3`을 보장하므로 더 작은 `n`은 입력 범위 밖입니다.

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
