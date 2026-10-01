---
title: BOJ. 벽 부수고 이동하기 (2206)
author: MINJUN PARK
date: 2021-12-28 05:56:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 코딩 면접, BOJ, 벽 부수고 이동하기]
pin: false
lang: ko
translation_key: boj-2206-wall-break
permalink: /ko/posts/boj-2206-wall-break/
---

[문제 링크](https://www.acmicpc.net/problem/2206)

미로는 `N × M` 격자이며, `0`은 빈 칸이고 `1`은 벽입니다. 왼쪽 위 칸에서 출발해 오른쪽 아래 칸까지 가는 경로에 포함되는 칸의 최솟값을 구합니다. 벽은 최대 한 개까지 부술 수 있습니다. 시작 칸과 도착 칸도 경로의 칸 수에 포함하며, 경로가 없으면 `-1`을 출력합니다.

위치만으로는 BFS 상태를 충분히 나타낼 수 없습니다. 벽을 아직 부술 수 있는 상태로 어떤 칸에 도착했을 때와 이미 벽을 부순 뒤 도착했을 때는 이후 이동 가능한 경로가 다릅니다. 따라서 `(칸, 벽을 부쉈는지 여부)`를 하나의 상태로 보고 두 상태를 각각 방문 처리합니다. 벽을 부수지 않은 상태에서는 빈 칸으로 이동해도 그 상태를 유지하고, 벽으로 이동할 때는 벽 부수기를 사용합니다. 이미 벽을 부순 상태에서는 빈 칸으로만 이동할 수 있습니다.

큐에는 `cellIndex * 2 + wallBrokenBit`으로 인코딩한 정수만 저장합니다. 각 상태는 최대 한 번 큐에 들어갑니다. BFS는 거리별 계층을 처리하며 거리를 `1`에서 시작하므로, 도착 상태의 거리는 경로에 포함된 칸의 수입니다. 따라서 칸이 하나뿐인 미로는 곧바로 `1`을 반환합니다. 문제 조건에서 시작점과 도착점은 빈 칸입니다. 벽을 부수는 전이는 중간의 벽을 통과할 때 사용할 수 있지만, 이미 기회를 사용한 뒤에는 다시 벽을 부술 수 없습니다.

칸마다 상태가 최대 두 개이므로 시간 복잡도와 공간 복잡도는 모두 `O(NM)`입니다.

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

        int startState = 0; // 0번 칸, 아직 벽을 부수지 않은 상태.
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
