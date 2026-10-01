---
title: BOJ 10266 - 時計の写真
author: MINJUN PARK
date: 2022-01-31 13:16:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 時計の写真, KMP]
pin: false
lang: ja
translation_key: boj-10266-clock-pictures
permalink: /ja/posts/boj-10266-clock-pictures/
source_permalink: /posts/BOJ-10266/
---

[問題: BOJ 10266 — 時計の写真](https://www.acmicpc.net/problem/10266) · [English](/posts/BOJ-10266/) · [한국어](/ko/posts/boj-10266-clock-pictures/)

時計の角度位置は `0` から `359999` までの `360000` 個で、各位置の単位は `1/1000` 度です。各写真を長さ `360000` の Boolean 配列で表し、時計の針がある位置だけを `true` にします。同じ位置に針が複数あっても、この表現と照合方法で問題ありません。

一方の写真を回転してもう一方と重ねられるなら、2 枚の写真は一致します。これは、両方の写真で隣り合う針の間の時計回りの間隔が、同じ巡回順序の列になることと同値です。開始する針は異なっても構いませんが、間隔の順序は一致しなければなりません。すべての開始位置を個別に試す代わりに、最初の Boolean パターンを 2 回連結して円形にします。すると、すべての回転は連結後のパターン内にある長さ `360000` の部分列として表されます。2 番目のパターンを KMP で検索しますが、重複した開始位置を含めないよう、先頭から `2 * 360000 - 1` 個の位置だけを走査します。これですべての開始位置をちょうど 1 回ずつ調べられます。

接頭辞テーブルと検索では Boolean 値を直接比較するため、針のある位置と空の位置を区別できます。`C = 360000` 個の位置と `N` 本の針に対し、配列の作成と KMP の実行にかかる時間は `O(C + N)` です。位置配列と接頭辞テーブルは `O(C)`、一時入力配列は `O(N)` の空間を使います。問題の制約 `N <= 200000 < C` により、補助空間計算量は全体で `O(C)` です。

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
