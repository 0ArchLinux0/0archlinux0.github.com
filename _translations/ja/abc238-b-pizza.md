---
title: AtCoder ABC 238 B — Pizza
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 238]
pin: false
lang: ja
translation_key: abc238-b-pizza
permalink: /ja/posts/abc238-b-pizza/
source_permalink: /posts/Atcoder-B-Pizza/
---

[問題: AtCoder ABC 238 B — Pizza](https://atcoder.jp/contests/abc238/tasks/abc238_b) · [English](/posts/Atcoder-B-Pizza/) · [한국어](/ko/posts/abc238-b-pizza/)

最初の切れ目を`0°`とします。指示ごとに包丁を時計回りに指定された角度だけ回すため、新しい切れ目の位置は直前の位置に回転角を加え、`360`で割った余りになります。こうして得られる`N`個の位置と`0°`を配列に格納します。同じ位置が複数回現れてもそのまま扱います。これは既存の切れ目と重なる切れ目ができ、幅0の間隔が生じることを表します。

位置をソートすると、隣り合う位置の間隔がピザの各部分の大きさになります。最後の切れ目から`360°`（`0°`と同じ位置）まで戻る間隔も含めます。これらの間隔の最大値が、最も大きいピザの部分の大きさです。位置は合計`N + 1`個なので、時間計算量は`O(N log N)`、空間計算量は`O(N)`です。

例えば回転角が`90, 180, 45, 195`なら、切れ目の位置は`0, 90, 270, 315, 150`です。ソート後は`0, 90, 150, 270, 315`となり、間隔は`90, 60, 120, 45`、最後から最初へ戻る間隔は`45`です。したがって答えは`120`です。

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
