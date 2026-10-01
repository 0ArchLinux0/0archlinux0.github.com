---
title: BOJ 1006 - 襲撃者チョラギ
author: MINJUN PARK
date: 2022-04-12 18:28:00 +0900
categories: [PS, baekjoon]
tags: [PS, アルゴリズム, BOJ, DP]
lang: ja
translation_key: boj-1006-defense
permalink: /ja/posts/boj-1006-defense/
---

[問題ページ](https://www.acmicpc.net/problem/1006)

## 問題のモデル

敵が配置された2行`N`列の円形グリッドがある。部隊1つは1マスを担当するか、敵の数の合計が`W`以下である隣接する2マスをまとめて担当できる。すべてのマスを担当するために必要な部隊数の最小値を求める。隣接するマスは同じ列の上下、または同じ行の隣り合う列である。同じ行では`N`列と1列も隣接する。

円周をまたぐ2組が、通常のプロファイルDPをそのまま適用する際の障害になる。そこで、境界をまたぐ組をどちらも使わない、上段だけ使う、下段だけ使う、両方使う、の4通りを列挙する。選んだ組はあらかじめ配置済みとし、両端にある該当マスを残りの問題から除く。残りは直線状の帯になるため、各列につき定数個のDP状態で処理できる。

## 線形プロファイルDP

境界ペアの選択を1つ固定し、`forced[c]`を列`c`ですでに覆われているマスを表す2ビットマスクとする。ビット0は上段、ビット1は下段を表す。あらかじめ覆われるマスがあるのは0列目と`N-1`列目だけである。選択した境界ペアに使う部隊数は、線形DPの結果に最後に加える。

左から右へ列を処理する。列`c`の開始時のDP状態`incoming`は、前の列から来た横向きの部隊がすでに覆っているマスを示す2ビットマスクである。`incoming`と事前に覆われたマスが重なる状態は破棄する。それ以外では`incoming | forced[c]`を現在の列ですでに覆われた状態として、残りを埋める。

現在の列でまだ覆われていない最初のマスを選び、次の方法を試す。

- そのマスだけを部隊1つで担当する。
- 上段のマスであり、上下2マスの敵数合計が`W`以下なら、同じ列の上下を部隊1つで担当する。
- 2マスの敵数合計が`W`以下なら、次の列の同じ行のマスと一緒に担当する。このとき次のマスを`outgoing`マスクに記録する。次のマスが選択済みの境界ペアですでに覆われている場合、この横ペアは作らない。

列の2マスをすべて処理したら、`outgoing`を次の列のDP状態として渡す。各列のマスクは4種類だけで、列内での再帰が扱うマスも最大2つなので、時間計算量は`O(N)`、空間計算量も`O(N)`である。境界ペアの4通りを列挙しても定数倍にしかならない。

`N = 1`の場合、異なる横隣接マスは存在しない。可能な2マス部隊は、その列の上下をまとめて担当する場合だけである。敵数の合計が`W`以下なら答えは1、そうでなければ2となる。1列の円の両端を別々のマスと誤認しないよう、個別に処理する。

`N >= 2`では、`top[0] + top[N-1] <= W`の場合にだけ上段の境界ペアを選択でき、下段も同様である。境界ペアを選ばない場合は必ず調べる。それぞれの選択で両端のマスクを使い、すでに割り当てたマスが他の部隊に再割り当てされないようにしてから、残りを線形DPで覆う。`N = 2`でも、選択した境界ペアが占有したマスは線形辺でもう一度ペアにできない。

## C++17実装

```cpp
#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int N, W;
        cin >> N >> W;
        vector<array<int, 2>> enemy(N);
        for (int r = 0; r < 2; ++r)
            for (int c = 0; c < N; ++c)
                cin >> enemy[c][r];

        if (N == 1) {
            cout << (enemy[0][0] + enemy[0][1] <= W ? 1 : 2) << '\n';
            continue;
        }

        const int INF = 1e9;
        int answer = INF;

        // wrapのビット0は上段、ビット1は下段の境界ペア。
        for (int wrap = 0; wrap < 4; ++wrap) {
            bool valid = true;
            int wrapCost = 0;
            vector<int> forced(N, 0);
            for (int r = 0; r < 2; ++r) {
                if ((wrap >> r) & 1) {
                    if (enemy[0][r] + enemy[N - 1][r] > W) {
                        valid = false;
                        break;
                    }
                    forced[0] |= 1 << r;
                    forced[N - 1] |= 1 << r;
                    ++wrapCost;
                }
            }
            if (!valid) continue;

            // dp[incoming mask] = この列を埋める前までに使った最小部隊数。
            array<int, 4> dp{0, INF, INF, INF};
            for (int c = 0; c < N; ++c) {
                array<int, 4> next{INF, INF, INF, INF};
                for (int incoming = 0; incoming < 4; ++incoming) {
                    if (dp[incoming] == INF || (incoming & forced[c])) continue;
                    int occupied = incoming | forced[c];

                    auto fill = [&](auto&& self, int mask, int outgoing, int cost) -> void {
                        if (mask == 3) {
                            next[outgoing] = min(next[outgoing], dp[incoming] + cost);
                            return;
                        }
                        int row = (mask & 1) ? 1 : 0;
                        int bit = 1 << row;

                        // このマスだけを部隊1つで担当する。
                        self(self, mask | bit, outgoing, cost + 1);

                        // 同じ列の上下2マスを部隊1つで担当する。
                        if (row == 0 && mask == 0 &&
                            enemy[c][0] + enemy[c][1] <= W) {
                            self(self, 3, outgoing, cost + 1);
                        }

                        // 次の列の同じ行のマスと一緒に担当する。
                        if (c + 1 < N && !(outgoing & bit) &&
                            !(forced[c + 1] & bit) &&
                            enemy[c][row] + enemy[c + 1][row] <= W) {
                            self(self, mask | bit, outgoing | bit, cost + 1);
                        }
                    };
                    fill(fill, occupied, 0, 0);
                }
                dp = next;
            }
            answer = min(answer, dp[0] + wrapCost);
        }

        cout << answer << '\n';
    }
}
```

各手順で選んだ未割り当ての最初のマスは、単独で担当するか、同じ列のもう一方と縦に組むか、次の列の同じ行のマスと横に組む必要がある。そのため、この遷移はすべての場合を漏れなく扱う。使うマスが未割り当てであることを確認し、2マス部隊には収容上限を適用する。よってプロファイルDPは重複のないすべての配置を表現し、各遷移は部隊をちょうど1つ加算する。最後に境界ペアの4通りの選択肢の最小値を取れば、円形配置の最適解が得られる。