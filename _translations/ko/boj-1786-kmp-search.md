---
title: BOJ 1786 - 찾기
author: MINJUN PARK
date: 2022-01-29 23:10:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 문자열, KMP]
pin: false
lang: ko
translation_key: boj-1786-kmp-search
permalink: /ko/posts/boj-1786-kmp-search/
source_permalink: /posts/BOJ-1786/
---

[문제: BOJ 1786 — 찾기](https://www.acmicpc.net/problem/1786) · [English](/posts/BOJ-1786/) · [日本語](/ja/posts/boj-1786-kmp-search/)

문자열 `T`에서 패턴 `P`가 나타나는 모든 위치를 찾아야 합니다. KMP는 불일치가 발생해도 `T`의 이미 비교한 부분을 다시 훑지 않습니다. 입력 조건상 패턴은 비어 있지 않지만, 아래 검색 함수도 빈 패턴이면 인덱싱하지 않고 결과가 없는 것으로 처리합니다.

## 접두사 함수와 검색

`pi[i]`는 `P[0..i]`의 proper prefix이면서 suffix인 문자열 중 가장 긴 것의 길이입니다. 접두사 함수를 만들 때 문자가 일치하지 않으면 `pi[j - 1]`로 이동해 더 짧은 접두사 후보를 확인하고, 일치하면 길이를 늘립니다.

검색 중 `j`는 현재까지 일치한 패턴 문자 수입니다. 문자가 다르면 같은 방식으로 `pi[j - 1]`까지 되돌려 비교를 이어갑니다. `j == P.length()`가 되면 일치가 끝난 위치 `i`로부터 시작 위치 `i - P.length() + 2`를 계산합니다. 이는 0-based 인덱스를 문제에서 요구하는 1-based 위치로 바꾼 값입니다. 일치 직후 `j = pi[j - 1]`로 되돌려 접미사와 다음 접두사가 겹치는 가능성을 보존하므로, 겹치는 일치도 모두 오름차순으로 기록됩니다.

전체 시간 복잡도는 접두사 함수 계산과 검색을 합쳐 `O(|T| + |P|)`이며, 추가 공간 복잡도는 접두사 배열과 결과 목록을 포함해 `O(|P| + K)`입니다(`K`는 일치 횟수).

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String text = input.readLine();
        String pattern = input.readLine();

        List<Integer> positions = findMatches(text, pattern);
        StringBuilder output = new StringBuilder();
        output.append(positions.size()).append('\n');
        for (int i = 0; i < positions.size(); i++) {
            if (i > 0) output.append(' ');
            output.append(positions.get(i));
        }
        System.out.print(output);
    }

    private static List<Integer> findMatches(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        if (pattern.isEmpty()) return positions;

        int[] pi = prefixFunction(pattern);
        int matched = 0;
        for (int i = 0; i < text.length(); i++) {
            while (matched > 0 && text.charAt(i) != pattern.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (text.charAt(i) == pattern.charAt(matched)) {
                matched++;
                if (matched == pattern.length()) {
                    positions.add(i - pattern.length() + 2);
                    matched = pi[matched - 1];
                }
            }
        }
        return positions;
    }

    private static int[] prefixFunction(String pattern) {
        int[] pi = new int[pattern.length()];
        int matched = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (matched > 0 && pattern.charAt(i) != pattern.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(matched)) {
                pi[i] = ++matched;
            }
        }
        return pi;
    }
}
```
