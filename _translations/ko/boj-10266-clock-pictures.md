---
title: BOJ 10266 - 시계 사진들
author: MINJUN PARK
date: 2022-01-31 13:16:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 시계 사진들, KMP]
pin: false
lang: ko
translation_key: boj-10266-clock-pictures
permalink: /ko/posts/boj-10266-clock-pictures/
source_permalink: /posts/BOJ-10266/
---

[문제: BOJ 10266 — 시계 사진들](https://www.acmicpc.net/problem/10266) · [English](/posts/BOJ-10266/) · [日本語](/ja/posts/boj-10266-clock-pictures/)

시계의 각도 위치는 `0`부터 `359999`까지 총 `360000`개이며, 각 위치 단위는 `1/1000`도입니다. 각 사진을 길이 `360000`인 불리언 배열로 나타내고, 시계 바늘이 있는 위치만 `true`로 표시합니다. 같은 위치에 바늘이 여러 개 있어도 이 표현과 매칭 방법은 그대로 사용할 수 있습니다.

한 사진을 회전해 다른 사진과 겹칠 수 있으면 두 사진은 같습니다. 이는 두 사진에서 이웃한 바늘 사이의 시계 방향 간격이 같은 순환 수열을 이루는 것과 같습니다. 시작 바늘은 달라도 되지만 간격의 순서는 같아야 합니다. 가능한 모든 시작 바늘을 각각 비교하는 대신 첫 번째 불리언 패턴을 두 번 이어 붙여 원형으로 만듭니다. 그러면 모든 회전은 이 두 배 패턴 안의 길이 `360000`인 부분 문자열로 나타납니다. 두 번째 패턴을 KMP로 찾되, 시작점이 중복되지 않도록 처음 `2 * 360000 - 1`개 위치만 검사합니다. 이렇게 하면 가능한 모든 시작점이 정확히 한 번씩 포함됩니다.

접두사 테이블과 검색 과정은 불리언 값을 직접 비교하므로 바늘이 있는 위치와 빈 위치를 구별합니다. `C = 360000`개의 위치와 `N`개의 바늘이 있을 때 배열을 만들고 KMP를 실행하는 시간은 `O(C + N)`입니다. 위치 배열과 접두사 테이블은 `O(C)` 공간을 사용하고, 임시 입력 배열은 `O(N)`을 사용합니다. 문제의 제한인 `N <= 200000 < C`에 따라 전체 보조 공간 복잡도는 `O(C)`입니다.

```java
import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;
    static int timeNum = 360000;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        boolean[] clock1 = new boolean[2 * timeNum];
        boolean[] clock2 = new boolean[timeNum];
        int[] arr = getArr();
        for (int e : arr) clock1[e] = clock1[e + timeNum] = true;
        arr = getArr();
        for (int e : arr) clock2[e] = true;
        print(kmp(clock1, clock2) ? "possible" : "impossible");
    }

    static int[] pi(boolean[] s) {
        int[] pi = new int[s.length];
        int l = 0;
        for (int r = 1; r < s.length; r++) {
            while (l > 0 && s[l] != s[r]) l = pi[l - 1];
            if (s[l] == s[r]) {
                pi[r] = l + 1;
                l++;
            }
        }
        return pi;
    }

    static boolean kmp(boolean[] t, boolean[] s) {
        int[] pi = pi(s);
        int r = 0;
        for (int l = 0; l < 2 * timeNum - 1; l++) {
            while (r > 0 && t[l] != s[r]) r = pi[r - 1];
            if (t[l] == s[r]) {
                if (r == s.length - 1) return true;
                r++;
            }
        }
        return false;
    }

    static int toi(String s) { return Integer.parseInt(s); }
    static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
    static <T> void print(T s) { System.out.print(s); }
}
```
