---
title: BOJ. 壁を壊して移動する (2206)
author: MINJUN PARK
date: 2021-12-28 05:56:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, コーディング面接, BOJ, 壁を壊して移動する]
pin: false
lang: ja
translation_key: boj-2206-wall-break
permalink: /ja/posts/boj-2206-wall-break/
---

[問題ページ](https://www.acmicpc.net/problem/2206)

迷路は `N × M` の格子で、`0` は通行可能なマス、`1` は壁です。左上のマスから出発し、右下のマスに到達する経路に含まれるマス数の最小値を求めます。壁は最大1つまで壊せます。始点と終点も経路のマス数に含め、到達できない場合は `-1` を出力します。

位置だけではBFSの状態を十分に表せません。同じマスに到着しても、壁を壊す権利が残っている状態と、すでに壁を壊した状態では、その後に通れる経路が異なります。そのため `(マス, 壁を壊したかどうか)` を状態として扱い、2つの状態を個別に訪問済みにします。壁を壊していない状態では、通行可能なマスへの移動後もその状態を保ち、壁へ移動する場合に壁を壊す権利を使います。すでに壁を壊した状態では、通行可能なマスだけに進めます。

キューには `cellIndex * 2 + wallBrokenBit` で符号化した整数を格納します。各状態はキューに最大1回だけ追加されます。BFSでは距離ごとに層を処理し、距離を `1` から始めるため、終点の距離がそのまま経路のマス数になります。したがって、1マスだけの迷路は直ちに `1` を返します。問題の条件では始点と終点は通行可能です。壁を壊す遷移によって途中の壁を通過できますが、権利を使った後に別の壁を壊すことはできません。

各マスにつき状態は最大2つなので、時間計算量と空間計算量はいずれも `O(NM)` です。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer dimensions = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(dimensions.nextToken());
        int m = Integer.parseInt(dimensions.nextToken());

        char[][] maze = new char[n][m];
        for (int row = 0; row < n; row++) {
            maze[row] = in.readLine().toCharArray();
        }

        System.out.println(shortestPath(maze, n, m));
    }

    private static int shortestPath(char[][] maze, int n, int m) {
        int cells = n * m;
        boolean[] visited = new boolean[cells * 2];
        int[] queue = new int[cells * 2];
        int head = 0;
        int tail = 0;

        int startState = 0; // 0番目のマス。まだ壁を壊していない状態。
        queue[tail++] = startState;
        visited[startState] = true;
        int distance = 1;

        while (head < tail) {
            int layerEnd = tail;
            while (head < layerEnd) {
                int state = queue[head++];
                int cell = state / 2;
                int wallBroken = state % 2;
                int row = cell / m;
                int col = cell % m;

                if (row == n - 1 && col == m - 1) {
                    return distance;
                }

                for (int direction = 0; direction < 4; direction++) {
                    int nextRow = row + DR[direction];
                    int nextCol = col + DC[direction];
                    if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= m) {
                        continue;
                    }

                    int nextWallBroken = wallBroken;
                    if (maze[nextRow][nextCol] == '1') {
                        if (wallBroken == 1) {
                            continue;
                        }
                        nextWallBroken = 1;
                    }

                    int nextCell = nextRow * m + nextCol;
                    int nextState = nextCell * 2 + nextWallBroken;
                    if (!visited[nextState]) {
                        visited[nextState] = true;
                        queue[tail++] = nextState;
                    }
                }
            }
            distance++;
        }

        return -1;
    }
}
```
