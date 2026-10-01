---
title: AtCoder ABC 235 B — 高橋の登山
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 235]
pin: false
lang: ja
translation_key: abc235-b-climbing-takahashi
permalink: /ja/posts/abc235-b-climbing-takahashi/
source_permalink: /posts/Atcoder-B-Climbing-Takahashi/
---

[問題: AtCoder ABC 235 B — Climbing Takahashi](https://atcoder.jp/contests/abc235/tasks/abc235_b)
[English](/posts/Atcoder-B-Climbing-Takahashi/) · [한국어](/ko/posts/abc235-b-climbing-takahashi/)

各地点の高さは、進む順に並んでいます。高橋は最初の地点から出発し、次の地点の高さが現在の地点より**厳密に高い**場合にだけ進み続けます。初めて同じ高さまたは低い高さの地点に来たら、その地点には到着せずに止まります。求めるのは最後に到着した地点の高さです。

答えを最初の高さで初期化し、2番目の高さから順に調べます。高さが増加している間は答えを更新し、初めて増加しない高さを見つけたら走査を終了します。すべての高さが増加し続ける場合は最後の高さが答えになり、2番目の高さが同じか低い場合は最初の高さが答えのままです。

時間計算量は`O(N)`、高さ配列を保持する追加領域計算量は`O(N)`です。

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
        int[] heights = new int[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(tokens.nextToken());
        }

        int answer = heights[0];
        for (int i = 1; i < n; i++) {
            if (heights[i] <= heights[i - 1]) {
                break;
            }
            answer = heights[i];
        }

        System.out.println(answer);
    }
}
```
