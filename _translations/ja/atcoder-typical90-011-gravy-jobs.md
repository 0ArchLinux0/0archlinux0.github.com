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
lang: ja
translation_key: atcoder-typical90-011-gravy-jobs
permalink: /ja/posts/atcoder-typical90-011-gravy-jobs/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_k)

各仕事には締切 `D`、所要時間 `C`、報酬 `S` が与えられます。仕事を締切の昇順に並べ、その順に処理します。`dp[t]` を、時刻 `t` までに終了するスケジュールの最大報酬とします。

各仕事を選ぶ場合を考え、`t` を `D` から `C` まで降順に走査して `dp[t] = max(dp[t], dp[t - C] + S)` と更新します。仕事は締切順に並べて実行できます。順序が逆になっている隣り合う二つの仕事を入れ替えると、締切の早い仕事はより早く終了し、もう一方の完了時刻は変わらないため、どちらの締切も守れます。この交換を繰り返せば、締切の昇順に並べられます。先に処理した仕事の締切はすべて現在の仕事の締切以下なので、時刻 `t - C` までに終わる以前の仕事のスケジュールの後に現在の仕事を追加すれば、`t <= D` のとき締切を守れます。この交換論法により、締切順に処理しても実行可能な仕事の選択を漏らしません。

`t` を降順に走査すると、`dp[t - C]` は現在の仕事を考慮する前の状態のままなので、同じ仕事を複数回選ぶことはありません。各仕事の処理後に累積最大値（`dp[t] = max(dp[t], dp[t - 1])`）を伝播させ、状態が「ちょうど `t` 時間かかる」ではなく「時刻 `t` までに終了する」ことを表すようにします。答えは `dp` の最大値です。最大締切を `Dmax` とすると、時間計算量は `O(N * Dmax)`、空間計算量は `O(Dmax)` です。

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
