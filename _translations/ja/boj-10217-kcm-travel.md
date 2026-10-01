---
title: BOJ. KCM Travel (10217)
author: MINJUN PARK
date: 2022-01-05 01:58:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Dijkstra,
    Graph,
    KCM Travel,
    Review,
    difficult
  ]
pin: false
lang: ja
translation_key: boj-10217-kcm-travel
permalink: /ja/posts/boj-10217-kcm-travel/
---

## 解法

`best[v][c]` は、合計費用 `c` 以下で空港 `v` に到着する最小時間を表します。開始地点には費用をかけずに到達できるため、すべての費用上限について `best[0][c] = 0` とし、その他は無限大に初期化します。チケットの新しい費用が `M` 以下の場合だけ緩和します。ある費用上限でより良い時間が見つかったら、それより大きい費用上限にも伝播させます。使える予算が増えても最小時間は悪化しません。

`(空港, 費用)` の状態に対してダイクストラ法を実行します。優先度付きキューは `Integer.compare` で移動時間の昇順に並べるため、減算による比較時のオーバーフローを避けられます。取り出した時間がその費用上限ですでに見つかっている最良時間より大きければスキップします。より少ない費用で同じかそれ以下の時間で到達する経路がその状態を支配するためです。チケット時間は非負なので、目的地がキューから取り出された時点で予算内の最小時間が確定し、探索を終了できます。テストケースごとにグラフ、テーブル、キューを新しく作り、ケース間で状態が混ざらないようにします。

不変条件は、有限のテーブル値がその費用上限以内で空港に到着する最小時間であり、キューの各状態が記録された費用で実際に到達可能な経路を表すことです。緩和では入力予算を超えない次のチケットを調べ、より大きい費用上限にも改善値を伝播してこの意味を保ちます。チケット時間は非負なので、ダイクストラ法は可能な経路を最小時間順に確定します。したがって、古くない目的地状態が初めてキューから取り出された時点で予算内の最小時間となります。

テーブル状態は最大 `N(M + 1)` 個なので、空間計算量は `O(NM)` です。チケット数が `K` の場合、疎な状態での優先度付きキュー処理は概ね `O(K log(NM))` ですが、最悪の場合は複数の費用状態からチケットを調べるため、より明示的には `O(KM log(NM))` となります。

[問題リンク](https://www.acmicpc.net/problem/10217)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static class Ticket {
        final int destination;
        final int cost;
        final int time;

        Ticket(int destination, int cost, int time) {
            this.destination = destination;
            this.cost = cost;
            this.time = time;
        }
    }

    private static class State {
        final int airport;
        final int cost;
        final int time;

        State(int airport, int cost, int time) {
            this.airport = airport;
            this.cost = cost;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int airportCount = input.nextInt();
            int budget = input.nextInt();
            int ticketCount = input.nextInt();

            List<Ticket>[] graph = new ArrayList[airportCount];
            for (int airport = 0; airport < airportCount; airport++) {
                graph[airport] = new ArrayList<>();
            }
            for (int i = 0; i < ticketCount; i++) {
                int from = input.nextInt() - 1;
                int to = input.nextInt() - 1;
                int cost = input.nextInt();
                int time = input.nextInt();
                graph[from].add(new Ticket(to, cost, time));
            }

            int[][] best = new int[airportCount][budget + 1];
            for (int[] times : best) {
                Arrays.fill(times, Integer.MAX_VALUE);
            }
            for (int budgetLimit = 0; budgetLimit <= budget; budgetLimit++) {
                best[0][budgetLimit] = 0;
            }

            PriorityQueue<State> queue = new PriorityQueue<>(
                    (left, right) -> Integer.compare(left.time, right.time));
            queue.add(new State(0, 0, 0));

            int answer = -1;
            while (!queue.isEmpty()) {
                State current = queue.poll();
                if (current.time > best[current.airport][current.cost]) {
                    continue;
                }
                if (current.airport == airportCount - 1) {
                    answer = current.time;
                    break;
                }

                for (Ticket ticket : graph[current.airport]) {
                    int nextCost = current.cost + ticket.cost;
                    if (nextCost > budget) {
                        continue;
                    }
                    int nextTime = current.time + ticket.time;
                    if (nextTime < best[ticket.destination][nextCost]) {
                        for (int limit = nextCost; limit <= budget; limit++) {
                            if (nextTime >= best[ticket.destination][limit]) {
                                break;
                            }
                            best[ticket.destination][limit] = nextTime;
                        }
                        queue.add(new State(ticket.destination, nextCost, nextTime));
                    }
                }
            }

            if (answer == -1) {
                output.append("Poor KCM\n");
            } else {
                output.append(answer).append('\n');
            }
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```
