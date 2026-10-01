---
title: Codeforces Global Round 19 A — Sorting Parts
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, Codeforces, Codeforces Global Round 19, Sorting Parts]
pin: false
lang: ko
translation_key: cf-1637a-sorting-parts
permalink: /ko/posts/cf-1637a-sorting-parts/
source_permalink: /posts/Codeforces-Global-Round-19-A.-Sorting-Parts/
---

[문제: Codeforces 1637A — Sorting Parts](https://codeforces.com/contest/1637/problem/A) · [English](/posts/Codeforces-Global-Round-19-A.-Sorting-Parts/) · [日本語](/ja/posts/cf-1637a-sorting-parts/)

각 테스트 케이스에서 `1 <= k < n`인 분할점 `k`를 하나 고릅니다. 접두 구간 `a[1..k]`와 접미 구간 `a[k+1..n]`을 각각 독립적으로 정렬합니다. 이 연산을 한 뒤 전체 배열이 비내림차순이 아니게 되는 유효한 분할이 존재하는지 묻습니다. 임의의 구간 하나를 고르는 연산이 아니라, 분할의 양쪽 구간을 모두 정렬합니다.

처음 배열이 비내림차순이라면 두 부분을 정렬해도 각 원소의 순서는 그대로이고, 두 부분 사이의 경계도 정렬된 상태입니다. 따라서 어떤 분할을 골라도 결과는 정렬되어 있으므로 `NO`를 출력합니다.

배열이 정렬되어 있지 않다면 인접한 역전 `a[i] > a[i + 1]`이 적어도 하나 있습니다. `k = i`를 선택하면 이 역전이 분할의 경계에 놓입니다. 접두 구간을 정렬한 뒤 마지막 값은 접두 구간의 최댓값이므로 `a[i]` 이상입니다. 접미 구간을 정렬한 뒤 첫 값은 접미 구간의 최솟값이므로 `a[i + 1]` 이하입니다. 그러므로 경계에서 여전히 왼쪽 값이 오른쪽 값보다 크고, 전체 배열은 정렬되지 않습니다. 따라서 `YES`를 출력합니다. 역전이 하나뿐인 경우에도 성립하며, 같은 값이 반복되어도 엄격한 `>` 비교만 역전으로 판정합니다.

문제의 제약은 `n >= 2`이므로 유효한 분할이 항상 있습니다. 제약 밖의 `n = 1`을 생각하면 유효한 분할이 없으며 답은 `NO`입니다. 코드도 인접한 두 원소가 없으므로 `NO`를 반환합니다.

인접한 쌍을 한 번씩 확인하므로 시간 복잡도는 `O(n)`입니다. 입력 배열이 `O(n)` 공간을 사용하고, 배열 외의 판정 과정은 `O(1)` 추가 공간만 사용합니다. 프로그램은 테스트 케이스 수를 읽은 뒤 각 `n`과 배열을 처리하고, 케이스마다 대문자 `YES` 또는 `NO`를 한 줄에 출력합니다.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int n = input.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = input.nextInt();

            boolean hasInversion = false;
            for (int i = 0; i + 1 < n; i++) {
                if (a[i] > a[i + 1]) {
                    hasInversion = true;
                    break;
                }
            }

            output.append(hasInversion ? "YES\n" : "NO\n");
        }

        System.out.print(output);
    }

    static class FastScanner {
        private final BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        int nextInt() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokenizer.nextToken());
        }
    }
}
```
