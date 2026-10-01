---
title: AtCoder ABC 238 B — Pizza
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 238]
pin: false
lang: ko
translation_key: abc238-b-pizza
permalink: /ko/posts/abc238-b-pizza/
source_permalink: /posts/Atcoder-B-Pizza/
---

[문제: AtCoder ABC 238 B — Pizza](https://atcoder.jp/contests/abc238/tasks/abc238_b) · [English](/posts/Atcoder-B-Pizza/) · [日本語](/ja/posts/abc238-b-pizza/)

처음 칼집의 위치를 `0°`로 둡니다. 지시를 하나씩 적용할 때마다 칼은 주어진 각도만큼 시계 방향으로 회전하므로, 새 칼집의 위치는 직전 위치에 회전 각도를 더한 뒤 `360`으로 나눈 나머지입니다. 이렇게 얻은 `N`개 위치와 `0°`를 배열에 저장합니다. 같은 위치가 여러 번 나와도 그대로 둡니다. 이는 기존 칼집과 겹치는 칼집이 생겨 폭이 0인 간격이 있다는 뜻입니다.

위치들을 정렬하면 이웃한 위치 사이의 간격이 피자 조각의 크기입니다. 마지막 칼집에서 `360°`(즉 `0°`와 같은 위치)까지 돌아오는 간격도 포함해야 합니다. 이 간격들 중 최댓값이 가장 큰 조각의 크기입니다. 위치는 총 `N + 1`개이므로 시간 복잡도는 `O(N log N)`, 공간 복잡도는 `O(N)`입니다.

예를 들어 회전 각도가 `90, 180, 45, 195`라면 칼집 위치는 `0, 90, 270, 315, 150`입니다. 정렬하면 `0, 90, 150, 270, 315`이고, 간격은 `90, 60, 120, 45`이며 마지막에서 처음으로 돌아가는 간격은 `45`입니다. 따라서 답은 `120`입니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        StringTokenizer rotations = new StringTokenizer(input.readLine());

        int[] cuts = new int[n + 1];
        int angle = 0;
        for (int i = 1; i <= n; i++) {
            angle = (angle + Integer.parseInt(rotations.nextToken())) % 360;
            cuts[i] = angle;
        }

        Arrays.sort(cuts);

        int largest = 0;
        for (int i = 1; i <= n; i++) {
            largest = Math.max(largest, cuts[i] - cuts[i - 1]);
        }
        largest = Math.max(largest, 360 - cuts[n] + cuts[0]);

        System.out.println(largest);
    }
}
```
