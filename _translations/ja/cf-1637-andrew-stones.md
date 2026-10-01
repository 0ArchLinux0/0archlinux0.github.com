---
title: Codeforces 1637C - Andrewと石
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, Codeforces, Andrewと石]
pin: false
lang: ja
translation_key: cf-1637-andrew-stones
permalink: /ja/posts/cf-1637-andrew-stones/
source_permalink: /posts/Codeforces-Global-Round-19-C.-Andrew-and-Stones/
---

[問題: Codeforces 1637C — Andrew and Stones](https://codeforces.com/contest/1637/problem/C) · [English](/posts/Codeforces-Global-Round-19-C.-Andrew-and-Stones/) · [한국어](/ko/posts/cf-1637-andrew-stones/)

1回の操作では `i < j < k` を満たす3つの添字を選び、中央の山 `j` に石が2個以上あれば、そこから石を2個取り出して山 `i` と山 `k` に1個ずつ置きます。目標は、最初と最後の山だけに石を残すことです。

## 最小操作回数

各操作は1つの内部の山を中心に行い、その山の石を2個減らします。ある山に最初 `a[i]` 個あり、他の操作から `r` 個を受け取った場合、その山を空にするには `2 * moves[i] = a[i] + r` でなければなりません。したがって `moves[i] >= ceil(a[i] / 2)` であり、全体の操作回数はこれらの切り上げ値の合計以上です。

`n > 3` の場合、すべての内部の山が最初からちょうど1個であるケースを除き、この下界を達成できます。内部の山の少なくとも1つに石が2個以上あれば、その山を使って操作を始め、隣接していない外側の添字も選べることを活用します。偶奇の調整が必要な奇数の余りそれぞれに石を1個送り、もう一方の受け取り先は両端の山にします。同じ奇数の山に2回以上石を加える必要はありません。これにより各山はちょうど `ceil(a[i] / 2)` 回、中心として選ばれます。すべての内部の山が1個なら、最初に実行可能な操作がないため目標に到達できません。

`n = 3` のとき、選べる三つ組は `(1, 2, 3)` だけです。中央の山は操作ごとに石を2個失うため、偶奇は変わりません。中央の値が奇数なら0にできず、答えは `-1` です。偶数ならちょうど `a[1] / 2` 回の操作が必要です。

公式制約は `3 <= n <= 100000`、`1 <= a[i] <= 10^9` であり、全テストケースの `n` の合計は最大 `100000` です。`n > 3` では内部の値の合計を `sum`、奇数の値の個数を `oddCount` とします。`ceil(x / 2) = (x + (x mod 2)) / 2` なので答えは `(sum + oddCount) / 2` です。合計は最大 `(n - 2) * 10^9 <= 10^14` なので、`long` で安全に扱えます。

例:

- `n = 3` で中央の値が4なら答えは2、3なら `-1` です。
- `n = 4` で内部の値が `[1, 1]` なら `-1`、`[1, 2]` なら2です。
- `n = 5` で内部の値が `[1, 2, 3]` なら `1 + 1 + 2 = 4` です。

各テストケースを1回走査するため、時間計算量は `O(n)`、入力配列のための空間計算量は `O(n)` です。問題では `n >= 3` が保証されるため、それより小さい `n` は入力範囲外です。

```java
import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int test = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        for (int t = 0; t < test; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] a = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (n == 3) {
                answer.append((a[1] & 1) == 1 ? -1 : a[1] / 2);
            } else {
                boolean allOne = true;
                int oddCount = 0;
                long sum = 0;
                for (int i = 1; i < n - 1; i++) {
                    if (a[i] != 1) allOne = false;
                    if ((a[i] & 1) == 1) oddCount++;
                    sum += a[i];
                }
                answer.append(allOne ? -1 : (sum + oddCount) / 2);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
```
