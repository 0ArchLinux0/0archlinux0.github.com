---
title: BOJ. 토마토 (7569)
author: MINJUN PARK
date: 2021-12-26 10:37:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 힙, 코딩 인터뷰, BOJ, 토마토]
pin: false
lang: ko
translation_key: boj-7569-tomato
permalink: /ko/posts/boj-7569-tomato/
---

[문제 링크](https://www.acmicpc.net/problem/7569)

토마토 상자는 3차원 격자입니다. 익은 토마토는 축을 따라 인접한 여섯 방향의 익지 않은 토마토를 익게 하며, 모든 변화는 하루 단위로 동시에 일어납니다. 익지 않은 토마토가 모두 익을 때까지 걸리는 날짜를 구하고, 끝내 익지 못하는 토마토가 있으면 `-1`을 출력합니다.

다중 시작점 너비 우선 탐색(BFS)을 사용합니다. 탐색을 시작하기 전에 처음부터 익어 있는 모든 토마토를 큐에 넣습니다. 큐에는 각 칸을 평탄화한 정수 인덱스 하나씩 저장하며, 전체 칸 수만큼의 기본형 `int[]`를 사용합니다. 익지 않은 칸이 익은 상태로 바뀔 때만 큐에 넣으므로 각 칸은 최대 한 번만 삽입됩니다.

격자 값은 날짜 레이블 역할도 합니다. 처음 익은 칸은 `1`이고, 새로 익은 칸에는 이전 칸의 값에 1을 더한 값을 기록합니다. 따라서 BFS의 각 레이어는 하루 동안 동시에 일어나는 익음에 해당합니다. 입력을 읽을 때 익지 않은 토마토 수를 세고, 토마토가 익을 때마다 그 수를 줄입니다. 처음부터 익지 않은 토마토가 없으면 답은 `0`입니다. 큐가 빌 때까지 탐색한 뒤에도 익지 않은 토마토가 남아 있으면 `-1`을 반환하고, 그렇지 않으면 격자의 최댓값에서 1을 뺀 값이 경과 일수입니다.

입력 리더는 줄 구분에 의존하지 않고 공백으로 구분된 정수를 읽습니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int m, n, h;
    private static final int[] DZ = {1, -1, 0, 0, 0, 0};
    private static final int[] DY = {0, 0, 1, -1, 0, 0};
    private static final int[] DX = {0, 0, 0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner();
        m = in.nextInt();
        n = in.nextInt();
        h = in.nextInt();

        int volume = m * n * h;
        int[][][] box = new int[h][n][m];
        int[] queue = new int[volume];
        int tail = 0;
        int unripe = 0;

        for (int z = 0; z < h; z++) {
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < m; x++) {
                    int value = in.nextInt();
                    box[z][y][x] = value;
                    if (value == 1) {
                        queue[tail++] = (z * n + y) * m + x;
                    } else if (value == 0) {
                        unripe++;
                    }
                }
            }
        }

        System.out.println(ripeningDays(box, queue, tail, unripe));
    }

    private static int ripeningDays(int[][][] box, int[] queue, int tail, int unripe) {
        if (unripe == 0) {
            return 0;
        }

        int maxDay = 1;
        for (int head = 0; head < tail; head++) {
            int index = queue[head];
            int z = index / (n * m);
            int remainder = index % (n * m);
            int y = remainder / m;
            int x = remainder % m;
            int nextDay = box[z][y][x] + 1;

            for (int direction = 0; direction < 6; direction++) {
                int nz = z + DZ[direction];
                int ny = y + DY[direction];
                int nx = x + DX[direction];
                if (nz < 0 || nz >= h || ny < 0 || ny >= n || nx < 0 || nx >= m
                        || box[nz][ny][nx] != 0) {
                    continue;
                }

                box[nz][ny][nx] = nextDay;
                maxDay = nextDay;
                unripe--;
                queue[tail++] = (nz * n + ny) * m + nx;
            }
        }

        return unripe == 0 ? maxDay - 1 : -1;
    }

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer, length;

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

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```

각 칸은 최대 한 번 큐에 들어가고 이웃을 최대 여섯 번 확인하므로 시간 복잡도는 `O(MNH)`입니다. 격자와 기본형 큐는 각각 `O(MNH)`의 공간을 사용합니다.
