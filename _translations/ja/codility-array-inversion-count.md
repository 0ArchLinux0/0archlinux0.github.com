---
title: Codility - 配列の転倒数
author: MINJUN PARK
date: 2021-12-23 01:39:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, コーディング面接, Codility, 配列の転倒数]
pin: false
lang: ja
translation_key: codility-array-inversion-count
permalink: /ja/posts/codility-array-inversion-count/
---

[問題リンク](https://app.codility.com/programmers/trainings/4/array_inversion_count/)

配列の転倒数とは、`i < j` かつ `A[i] > A[j]` を満たす添字の組の数である。値が等しい組は転倒数に含めない。整列済みの `ArrayList` に各値を挿入する場合、二分探索で挿入位置は見つけられるが、挿入時の要素移動に毎回 `O(N)` かかるため、全体の時間計算量は `O(N²)` となる。

マージソートを使えば、`O(N log N)` 時間で転倒数を数えられる。配列を再帰的に二つの範囲に分け、それぞれを整列してから一つの整列済み範囲にマージする。マージ中、左右の未処理部分はそれぞれ整列済みである。右側の次の値が左側の次の値より小さい場合、その値は左側に残っているすべての値より小さい。そのため、左側に残る要素数を加算してから右側の値を取り出す。それ以外では左側の値を取り出す。同値の場合に左側を先に選ぶことで、等しい値を転倒数に数えない。再帰全体で補助配列を一つだけ再利用する。

転倒数はオーバーフローを防ぐため `long` に累積し、合計が `1,000,000,000` を超えた場合に限り `-1` を返す。長さ0または1の配列には組がないため、`0` を返す。時間計算量は `O(N log N)`、補助領域は `O(N)` であり、再帰スタックは `O(log N)` である。

```java
class Solution {
    private static final long LIMIT = 1_000_000_000L;

    public int solution(int[] A) {
        if (A.length < 2) {
            return 0;
        }

        int[] auxiliary = new int[A.length];
        long inversions = sortAndCount(A, auxiliary, 0, A.length);
        return inversions > LIMIT ? -1 : (int) inversions;
    }

    // 半開区間 [start, end) を整列し、その範囲の転倒数を返す。
    private long sortAndCount(int[] values, int[] auxiliary, int start, int end) {
        if (end - start < 2) {
            return 0;
        }

        int middle = start + (end - start) / 2;
        long inversions = sortAndCount(values, auxiliary, start, middle)
                + sortAndCount(values, auxiliary, middle, end);

        int left = start;
        int right = middle;
        int output = start;

        while (left < middle && right < end) {
            if (values[left] <= values[right]) {
                auxiliary[output++] = values[left++];
            } else {
                auxiliary[output++] = values[right++];
                inversions += middle - left;
            }
        }

        while (left < middle) {
            auxiliary[output++] = values[left++];
        }
        while (right < end) {
            auxiliary[output++] = values[right++];
        }
        System.arraycopy(auxiliary, start, values, start, end - start);

        return inversions;
    }
}
```
