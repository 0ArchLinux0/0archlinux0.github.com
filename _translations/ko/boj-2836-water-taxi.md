---
title: BOJ 2836 - 수상 택시
author: MINJUN PARK
date: 2022-02-17 12:24:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 스위핑, 수상 택시]
pin: false
lang: ko
translation_key: boj-2836-water-taxi
permalink: /ko/posts/boj-2836-water-taxi/
source_permalink: /posts/BOJ-2836/
---

[문제: BOJ 2836 — 수상 택시](https://www.acmicpc.net/problem/2836) · [English](/posts/BOJ-2836/) · [日本語](/ja/posts/boj-2836-water-taxi/)

택시는 위치 `0`에서 출발해 위치 `M`까지 이동해야 하며, 각 승객은 일직선 경로를 따라 어느 방향으로든 이동할 수 있습니다. 택시는 먼저 `0`에서 `M` 방향으로 이동합니다. 출발지보다 오른쪽에 목적지가 있는 승객은 택시가 지나갈 때 내려 줄 수 있으므로, 반드시 이동해야 하는 거리 `M` 외에 추가 거리가 들지 않습니다.

반대로 승객의 목적지 `destination`이 출발지 `start`보다 왼쪽이면 (`destination < start`), 택시는 진행 경로를 벗어나 `start`까지 간 뒤 `destination`으로 돌아와야 합니다. 이 승객 한 명만 고려하면 추가 이동 거리는 `2 * (start - destination)`입니다. 하지만 여러 승객의 왼쪽 이동을 위해 같은 구간을 함께 왕복할 수 있습니다. 경로 위에서 각 승객이 요구하는 구간은 `[destination, start]`이며, 이 구간들의 합집합에 포함되는 부분만 한 번 왕복하면 됩니다. 따라서 답은 `M + 2 * 합집합의 길이`입니다.

왼쪽으로 가는 승객의 구간만 왼쪽 끝점 기준 오름차순으로 정렬한 뒤 스캔합니다. 다음 구간의 왼쪽 끝이 현재 구간의 오른쪽 끝 이하라면 두 구간은 겹치거나 맞닿으므로 하나의 연속 구간으로 합칠 수 있습니다. 겹치지 않는 구간을 만나면 현재 구간의 길이를 합산하고 새 구간으로 시작합니다. 마지막 구간까지 길이에 포함한 뒤 두 배로 만들어 `M`에 더합니다. 왼쪽으로 가는 승객이 없다면 합집합의 길이는 0이므로 답은 `M`입니다.

정렬에 `O(N log N)`, 스캔에 `O(N)` 시간이 걸립니다. 왼쪽 이동 구간을 저장하므로 공간 복잡도는 `O(N)`입니다. 문제의 좌표 범위는 `int`에 들어가지만, 전체 이동 거리가 넘치지 않도록 합계는 `long`으로 계산합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static class TripInterval {
        int left;
        int right;

        TripInterval(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int passengerCount = Integer.parseInt(tokens.nextToken());
        long destination = Long.parseLong(tokens.nextToken());
        ArrayList<TripInterval> intervals = new ArrayList<>();

        for (int i = 0; i < passengerCount; i++) {
            tokens = new StringTokenizer(reader.readLine());
            int start = Integer.parseInt(tokens.nextToken());
            int end = Integer.parseInt(tokens.nextToken());
            if (end < start) intervals.add(new TripInterval(end, start));
        }

        intervals.sort(Comparator.comparingInt(interval -> interval.left));
        long extraDistance = 0;
        if (!intervals.isEmpty()) {
            int currentLeft = intervals.get(0).left;
            int currentRight = intervals.get(0).right;

            for (int i = 1; i < intervals.size(); i++) {
                TripInterval next = intervals.get(i);
                if (next.left <= currentRight) {
                    currentRight = Math.max(currentRight, next.right);
                } else {
                    extraDistance += (long) currentRight - currentLeft;
                    currentLeft = next.left;
                    currentRight = next.right;
                }
            }
            extraDistance += (long) currentRight - currentLeft;
        }

        System.out.println(destination + 2L * extraDistance);
    }
}
```
