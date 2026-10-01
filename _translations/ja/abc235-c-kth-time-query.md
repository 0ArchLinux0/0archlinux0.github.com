---
title: AtCoder ABC 235 C - The Kth Time Query
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC]
pin: false
lang: ja
translation_key: abc235-c-kth-time-query
permalink: /ja/posts/abc235-c-kth-time-query/
source_permalink: /posts/Atcoder-C-The-Kth-Time-Query/
---

[問題: AtCoder ABC 235 C — The Kth Time Query](https://atcoder.jp/contests/abc235/tasks/abc235_c) · [English](/posts/Atcoder-C-The-Kth-Time-Query/) · [한국어](/ko/posts/abc235-c-kth-time-query/)

各クエリ `(x, k)` について、配列内で `x` が `k` 回目に現れる位置（1 始まり）を求めます。`x` の出現回数が `k` 未満なら `-1` を出力します。

値から出現位置のリストへのマップを作ります。配列を左から順に走査し、その値のリストに `i + 1` を追加します。位置は昇順に追加されるので、リストは最初から整列済みです。したがって答えはインデックス `k - 1` の要素です。値が存在しない場合、またはリストの要素数が `k` 未満の場合は `-1` を出力します。

同じ値が複数回現れても、そのまま処理できます。先頭の位置が `k = 1` の答えであり、`k` が出現総数と等しい場合は末尾の位置が答えです。値がない場合も、出現数を超える `k` の場合も `-1` になります。

ハッシュマップへのアクセスが期待 O(1) であるため、位置リストの構築と全クエリの処理にかかる期待時間計算量は `O(N + Q)` です。位置リストには合計 `N` 個のインデックスを保存し、マップのキー数は最大 `N` 個です。出力バッファを含む補助領域計算量は `O(N + Q)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static final class FastScanner {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens;

        int nextInt() throws IOException {
            while (tokens == null || !tokens.hasMoreTokens()) {
                tokens = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokens.nextToken());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int q = input.nextInt();
        Map<Integer, ArrayList<Integer>> positions = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            int value = input.nextInt();
            positions.computeIfAbsent(value, ignored -> new ArrayList<>()).add(i);
        }

        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < q; query++) {
            int value = input.nextInt();
            int k = input.nextInt();
            ArrayList<Integer> occurrences = positions.get(value);
            if (occurrences == null || occurrences.size() < k) {
                answer.append(-1).append('\n');
            } else {
                answer.append(occurrences.get(k - 1)).append('\n');
            }
        }
        System.out.print(answer);
    }
}
```
