---
title: BOJ. 집합 (11723)
author: MINJUN PARK
date: 2022-01-26 04:59:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Bitmask,
    BOJ,
    Set,
    집합
  ]
pin: false
lang: ko
translation_key: boj-11723-bitmask-set
permalink: /ko/posts/boj-11723-bitmask-set/
source_permalink: /posts/BOJ-11723/
---

## 비트마스크

집합에는 `1`부터 `20`까지의 정수만 들어가므로 하나의 `int`에 원소의 포함 여부를 저장할 수 있습니다. 값 `x`를 비트 위치 `x - 1`에 대응시킵니다. `1 << (x - 1)`은 해당 비트만 1인 마스크를 만듭니다. 시프트 위치는 0부터 시작하므로 값에서 1을 빼야 합니다. 전체 집합 마스크 `(1 << 20) - 1`은 하위 20비트가 모두 `1`이며, `0`은 빈 집합입니다.

`add`는 마스크를 OR하여 원소를 포함시키고, `remove`는 마스크의 반전값과 AND하여 원소를 제거합니다. `check`는 AND 결과가 0이 아닌지 확인하며, `toggle`은 XOR로 해당 비트를 뒤집습니다. `all`은 전체 집합 마스크를 대입하고 `empty`는 집합을 비웁니다. 입력에서 원소를 사용하는 명령의 `x`는 `1 <= x <= 20`을 만족합니다. `add`와 `remove`는 원소가 이미 원하는 상태여도 안전합니다. 각 명령은 `O(1)` 시간, 집합은 `O(1)` 공간을 사용합니다.

[문제 링크](https://www.acmicpc.net/problem/11723)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int FULL_SET = (1 << 20) - 1;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int commandCount = input.nextInt();
        int set = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < commandCount; i++) {
            String command = input.next();
            if (command.equals("all")) {
                set = FULL_SET;
            } else if (command.equals("empty")) {
                set = 0;
            } else {
                int x = input.nextInt();
                int mask = 1 << (x - 1);
                switch (command) {
                    case "add":
                        set |= mask;
                        break;
                    case "remove":
                        set &= ~mask;
                        break;
                    case "check":
                        output.append((set & mask) != 0 ? 1 : 0).append('\n');
                        break;
                    case "toggle":
                        set ^= mask;
                        break;
                }
            }
        }

        System.out.print(output);
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

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

        String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            StringBuilder token = new StringBuilder();
            while (c > ' ') {
                token.append((char) c);
                c = read();
            }
            return token.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
