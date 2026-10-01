---
title: LeetCode. 11. 盛れる水の最大量
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Container With Most Water,
  ]
pin: false
lang: ja
translation_key: leetcode-11-container-with-most-water
permalink: /ja/posts/leetcode-11-container-with-most-water/
---

![image](https://user-images.githubusercontent.com/88752447/130302060-dbc8a9ac-6d5e-46d6-8d19-e4426a918370.png)

[問題: Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

## 2ポインター

`l < r`となる2つの添字を選ぶと、容器に入る水の高さは2本の線のうち低い方で決まります。幅は`r - l`、高さは`min(height[l], height[r])`なので、面積は次の式です。

$$
(r-l)\times\min(\text{height}[l],\text{height}[r]).
$$

配列の両端から始めます。この2本は取り得る最大幅の容器を作ります。面積を記録したら、低い方の線を指すポインターを内側へ動かします。高さが等しい場合はどちらを動かしてもよく、以下の実装では右ポインターを動かします。

## 低い方を捨てても最適解を逃さない理由

`height[l] <= height[r]`とします。現在の容器の高さは`height[l]`以下です。`l`を固定して右端を`r' < r`にすると、幅は`r-l`より小さくなります。また、左側の壁は変わらず`height[l]`なので、高さが`height[l]`を超えることもありません。したがって、その面積は現在の面積を超えません。`l`を使いながら`r`より内側に端点を置いても、よりよい答えにはなりません。そのため`l`を捨てて右へ進めます。右側の方が低い場合も対称的に同じ議論が成り立ちます。これを繰り返せば、よりよい可能性のある組み合わせを見落としません。

## Java実装

制約は`2 <= height.length <= 10^5`、`0 <= height[i] <= 10^4`です。よって最大面積は`(10^5 - 1) * 10^4 = 999,990,000`以下で、符号付き32ビットの`int`に収まります。乗算と結果の両方を`int`で扱っても安全です。

```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;

        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            best = Math.max(best, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return best;
    }
}
```

各ポインターは最大`N`回だけ内側へ移動するため、時間計算量は`O(N)`、追加領域は`O(1)`です。
