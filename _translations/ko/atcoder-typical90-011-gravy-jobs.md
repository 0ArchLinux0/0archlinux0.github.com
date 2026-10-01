---
title: AtCoder Typical 90 011 — Gravy Jobs
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Gravy Jobs,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-011-gravy-jobs
permalink: /ko/posts/atcoder-typical90-011-gravy-jobs/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_k)

각 작업에는 마감 시각 `D`, 소요 시간 `C`, 보상 `S`가 주어집니다. 작업을 마감 시각의 오름차순으로 정렬해 차례로 처리합니다. `dp[t]`를 시각 `t`까지 끝나는 일정에서 얻을 수 있는 최대 보상이라고 정의합니다.

각 작업을 선택하는 경우를 고려해 `t`를 `D`부터 `C`까지 내림차순으로 순회하며 `dp[t] = max(dp[t], dp[t - C] + S)`로 갱신합니다. 작업은 마감 시각 순서로 배치할 수 있습니다. 순서가 뒤바뀐 인접 작업 두 개를 바꾸면 더 이른 마감의 작업은 더 일찍 끝나고, 다른 작업의 완료 시각은 그대로이므로 두 작업 모두 마감 시각을 지킵니다. 따라서 교환을 반복하면 마감 시각의 오름차순으로 정렬할 수 있습니다. 앞서 처리한 작업의 마감 시각은 모두 현재 작업 이하이므로, 시각 `t - C`까지 끝나는 이전 작업 일정 뒤에 현재 작업을 추가하면 `t <= D`일 때 마감 시각을 지킵니다. 이 교환 논증에 따라 마감 순서로 처리해도 가능한 작업 선택을 빠뜨리지 않습니다.

`t`를 내림차순으로 순회하면 `dp[t - C]`는 현재 작업을 고려하기 전의 상태이므로 같은 작업을 두 번 선택할 수 없습니다. 각 작업을 처리한 뒤 누적 최댓값(`dp[t] = max(dp[t], dp[t - 1])`)을 전파하여 상태가 정확히 `t`만큼 걸리는 일정이 아니라 `t`까지 끝나는 일정을 뜻하도록 합니다. 답은 `dp`의 최댓값입니다. 최대 마감 시각을 `Dmax`라 할 때 시간 복잡도는 `O(N * Dmax)`, 공간 복잡도는 `O(Dmax)`입니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream input;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

    FastScanner(InputStream input) {
      this.input = input;
    }

    private int read() throws IOException {
      if (pointer == length) {
        length = input.read(buffer);
        pointer = 0;
        if (length == -1) return -1;
      }
      return buffer[pointer++];
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

    long nextLong() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);

      long value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value;
    }
  }

  static class Job {
    int deadline;
    int duration;
    long reward;

    Job(int deadline, int duration, long reward) {
      this.deadline = deadline;
      this.duration = duration;
      this.reward = reward;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    Job[] jobs = new Job[n];
    int maxDeadline = 0;
    for (int i = 0; i < n; i++) {
      int deadline = input.nextInt();
      int duration = input.nextInt();
      long reward = input.nextLong();
      jobs[i] = new Job(deadline, duration, reward);
      maxDeadline = Math.max(maxDeadline, deadline);
    }

    Arrays.sort(jobs, Comparator.comparingInt(job -> job.deadline));
    long[] dp = new long[maxDeadline + 1];
    for (Job job : jobs) {
      for (int time = job.deadline; time >= job.duration; time--) {
        dp[time] = Math.max(dp[time], dp[time - job.duration] + job.reward);
      }
      for (int time = 1; time <= maxDeadline; time++) {
        dp[time] = Math.max(dp[time], dp[time - 1]);
      }
    }

    long answer = 0;
    for (long reward : dp) {
      answer = Math.max(answer, reward);
    }
    System.out.println(answer);
  }
}
```
