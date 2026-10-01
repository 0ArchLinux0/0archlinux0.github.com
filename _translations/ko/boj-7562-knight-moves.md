---
title: BOJ. 나이트의 이동 (7562)
author: MINJUN PARK
date: 2021-12-30 18:49:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 코딩 인터뷰, BOJ, 나이트의 이동]
pin: false
lang: ko
translation_key: boj-7562-knight-moves
permalink: /ko/posts/boj-7562-knight-moves/
---

[문제 링크](https://www.acmicpc.net/problem/7562)

각 테스트 케이스에서 `L × L` 체스판의 시작 칸에서 목표 칸까지 이동하는 나이트의 최소 이동 횟수를 구합니다. 한 번 이동할 때 한 좌표는 1, 다른 좌표는 2만큼 변하며, 두 좌표의 부호는 각각 달라질 수 있습니다. 나이트는 체스판 밖으로 나갈 수 없습니다.

체스판의 각 칸을 그래프의 정점으로, 가능한 나이트 이동을 간선으로 봅니다. 모든 간선의 비용이 같으므로 너비 우선 탐색(BFS)은 시작점으로부터의 거리가 작은 칸부터 방문합니다. 따라서 목표 칸이 처음 발견될 때의 거리가 최단 이동 횟수입니다. 거리 배열은 방문 표시도 겸합니다. 거리가 `-1`인 칸만 큐에 넣으므로 각 칸은 최대 한 번만 큐에 들어갑니다. 시작 칸과 목표 칸이 같으면 답은 0입니다.

입력 스캐너는 줄바꿈뿐 아니라 임의의 공백을 처리하며, 기본형 배열 큐를 모든 테스트 케이스에서 재사용합니다. 체스판에는 `L²`개의 칸이 있고 각 칸에서 확인하는 이동은 최대 8개이므로 시간 복잡도는 `O(L²)`, 보조 공간 복잡도는 `O(L²)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int[] DR = {-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] DC = {-1, 1, -2, 2, -2, 2, -1, 1};

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner();
        int testCases = in.nextInt();
        int[] queue = new int[300 * 300];
        StringBuilder answer = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int length = in.nextInt();
            int startRow = in.nextInt();
            int startCol = in.nextInt();
            int targetRow = in.nextInt();
            int targetCol = in.nextInt();
            int[][] distance = new int[length][length];
            for (int row = 0; row < length; row++) {
                for (int col = 0; col < length; col++) {
                    distance[row][col] = -1;
                }
            }

            int head = 0;
            int tail = 0;
            int start = startRow * length + startCol;
            int target = targetRow * length + targetCol;
            distance[startRow][startCol] = 0;
            queue[tail++] = start;

            while (head < tail && distance[targetRow][targetCol] == -1) {
                int square = queue[head++];
                int row = square / length;
                int col = square % length;
                for (int move = 0; move < 8; move++) {
                    int nextRow = row + DR[move];
                    int nextCol = col + DC[move];
                    if (nextRow < 0 || nextRow >= length
                            || nextCol < 0 || nextCol >= length
                            || distance[nextRow][nextCol] != -1) {
                        continue;
                    }
                    distance[nextRow][nextCol] = distance[row][col] + 1;
                    queue[tail++] = nextRow * length + nextCol;
                }
            }
            answer.append(distance[targetRow][targetCol]).append('\n');
        }

        System.out.print(answer);
    }

    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

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
