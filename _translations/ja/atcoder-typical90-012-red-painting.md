---
title: AtCoder Typical 90 012 — Red Painting (4)
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
    Red Painting,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-012-red-painting
permalink: /ja/posts/atcoder-typical90-012-red-painting/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_l)

最初、すべてのマスは白です。マスを赤く塗る操作、または2つのマスを指定するクエリが与えられます。2つのマスがどちらも赤く、赤いマスだけを上下左右に移動して互いに到達できる場合は `Yes`、そうでなければ `No` を出力します。

各マスを素集合データ構造（DSU）の要素として扱い、赤く塗られたマスを示す `red` 配列を別に管理します。マスを塗る際、まだ赤くなければ赤として有効化し、上下左右に隣接する赤いマスと集合を併合します。一度赤くなったマスはそのままなので、連結関係は併合だけで変化します。したがってDSUは常に現在の連結成分を表します。

クエリでは、両方のマスが赤いことを確認してから、それぞれの代表元が同じかを比較します。この確認により、まだ塗られていないマスは、同じマスを指定したクエリでも連結とは判定されません。境界を確認してから隣接マスを調べるため、グリッド外を参照することもありません。

経路圧縮と union by size を用いるDSUの各操作は償却 `O(α(HW))` 時間です。各マスは最大1回だけ有効化され、確認する隣接マスは4つなので、全体の時間計算量は `O((HW + Q) α(HW))` です。DSUと赤色配列に `O(HW)` の空間を使います。

```java
import java.io.*;

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
  }

  static class DSU {
    private final int[] parent;
    private final int[] size;

    DSU(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int x) {
      if (parent[x] != x) parent[x] = find(parent[x]);
      return parent[x];
    }

    void unite(int a, int b) {
      int rootA = find(a);
      int rootB = find(b);
      if (rootA == rootB) return;
      if (size[rootA] < size[rootB]) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
      }
      parent[rootB] = rootA;
      size[rootA] += size[rootB];
    }
  }

  static final int[] DR = {-1, 1, 0, 0};
  static final int[] DC = {0, 0, -1, 1};

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int h = input.nextInt();
    int w = input.nextInt();
    int q = input.nextInt();
    int cells = h * w;
    boolean[] red = new boolean[cells];
    DSU dsu = new DSU(cells);
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < q; i++) {
      int type = input.nextInt();
      if (type == 1) {
        int r = input.nextInt() - 1;
        int c = input.nextInt() - 1;
        int cell = r * w + c;
        if (!red[cell]) {
          red[cell] = true;
          for (int direction = 0; direction < 4; direction++) {
            int nr = r + DR[direction];
            int nc = c + DC[direction];
            if (nr >= 0 && nr < h && nc >= 0 && nc < w) {
              int neighbor = nr * w + nc;
              if (red[neighbor]) dsu.unite(cell, neighbor);
            }
          }
        }
      } else {
        int r1 = input.nextInt() - 1;
        int c1 = input.nextInt() - 1;
        int r2 = input.nextInt() - 1;
        int c2 = input.nextInt() - 1;
        int first = r1 * w + c1;
        int second = r2 * w + c2;
        output.append(red[first] && red[second] && dsu.find(first) == dsu.find(second) ? "Yes" : "No").append('\n');
      }
    }
    System.out.print(output);
  }
}
```
