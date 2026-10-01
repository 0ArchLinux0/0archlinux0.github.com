---
title: BOJ 2836 - 水上タクシー
author: MINJUN PARK
date: 2022-02-17 12:24:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, スイープ, 水上タクシー]
pin: false
lang: ja
translation_key: boj-2836-water-taxi
permalink: /ja/posts/boj-2836-water-taxi/
source_permalink: /posts/BOJ-2836/
---

[問題: BOJ 2836 — 水上タクシー](https://www.acmicpc.net/problem/2836) · [English](/posts/BOJ-2836/) · [한국어](/ko/posts/boj-2836-water-taxi/)

タクシーは位置 `0` から出発し、位置 `M` まで移動しなければなりません。乗客は一直線上の経路に沿って、どちらの方向にも移動できます。タクシーはまず `0` から `M` に向かって進みます。出発地点より右側が目的地の乗客は、通過時に降ろせるため、必ず進む距離 `M` 以外の追加距離は発生しません。

一方、目的地 `destination` が出発地点 `start` より左にある乗客（`destination < start`）を乗せるには、タクシーは順路を外れて `start` まで進み、そこから `destination` へ戻らなければなりません。この乗客だけを考えた追加距離は `2 * (start - destination)` です。ただし、複数の左向きの移動では、同じ区間を共有して往復できます。経路上で各乗客が必要とする区間は `[destination, start]` なので、これらの区間の和集合に含まれる部分だけを一度ずつ往復すればよくなります。したがって答えは `M + 2 * 和集合の長さ` です。

左向きの区間だけを左端の昇順に並べ、左から順に走査します。次の区間の左端が現在の右端以下なら、区間同士は重なるか接しているため、一つの連続した区間としてまとめられます。重ならない区間が現れたら、現在の区間の長さを加算し、新しい区間を開始します。最後の区間の長さも加え、和集合の長さを2倍して `M` に足します。左向きの乗客がいなければ和集合の長さは0なので、答えは `M` です。

ソートに `O(N log N)`、走査に `O(N)` の時間がかかります。左向きの区間を保存するため、空間計算量は `O(N)` です。問題の制約では座標は `int` に収まりますが、合計距離のオーバーフローを防ぐため、合計は `long` で計算します。

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
