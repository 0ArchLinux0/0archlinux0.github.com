---
title: BOJ. Friend Network (4195)
author: MINJUN PARK
date: 2022-01-05 16:11:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Union Find,
    Friend Network,
    친구 네트워크,
		Review
  ]
pin: false
lang: ja
translation_key: boj-4195-friend-network
permalink: /ja/posts/boj-4195-friend-network/
source_permalink: /posts/BOJ-4195/
---

## 解法

各人の名前を親の名前に対応させ、各ルートにはコンポーネントのサイズを保存します。初めて登場した名前は自身をルートとし、サイズを 1 にします。友人関係を読み込んだら両者のルートを探し、小さいコンポーネントを大きいコンポーネントの下に結び、残るルートのサイズを合算します。すでに同じルートなら構造を変更せず、そのルートの現在のサイズを出力します。テストケースごとに新しい素集合データ構造を作り、ケース間で状態が混ざらないようにします。

不変条件は、同じ友人関係の連結成分に属するすべての名前が同一のルートに到達し、そのルートに保存されたサイズが実際の人数と一致することです。経路圧縮はルートを保ちながら今後の探索経路を短くし、サイズによる併合は木が過度に深くなることを防ぎます。両方を用いると、友人関係 1 件あたりの素集合操作は償却 `O(α(V))` 時間です。`V` はケース内の異なる名前の数です。ハッシュマップのアクセスは平均 `O(1)` なので、`F` 件全体では平均 `O(F α(V))` 時間、マップの空間計算量は `O(V)` です。`find` は反復処理で実装しているため、親の経路が深くなっても再帰呼び出しのスタックを使いません。

[問題リンク](https://www.acmicpc.net/problem/4195)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int testCase = 0; testCase < testCases; testCase++) {
            int friendshipCount = input.nextInt();
            DisjointSet friends = new DisjointSet();

            for (int i = 0; i < friendshipCount; i++) {
                String first = input.next();
                String second = input.next();
                output.append(friends.union(first, second)).append('\n');
            }
        }

        System.out.print(output);
    }

    private static class DisjointSet {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> size = new HashMap<>();

        private void add(String name) {
            if (!parent.containsKey(name)) {
                parent.put(name, name);
                size.put(name, 1);
            }
        }

        private String find(String name) {
            String root = name;
            while (!parent.get(root).equals(root)) {
                root = parent.get(root);
            }

            while (!name.equals(root)) {
                String next = parent.get(name);
                parent.put(name, root);
                name = next;
            }
            return root;
        }

        int union(String first, String second) {
            add(first);
            add(second);

            String firstRoot = find(first);
            String secondRoot = find(second);
            if (firstRoot.equals(secondRoot)) {
                return size.get(firstRoot);
            }

            if (size.get(firstRoot) < size.get(secondRoot)) {
                String temporary = firstRoot;
                firstRoot = secondRoot;
                secondRoot = temporary;
            }

            parent.put(secondRoot, firstRoot);
            int combinedSize = size.get(firstRoot) + size.get(secondRoot);
            size.put(firstRoot, combinedSize);
            size.remove(secondRoot);
            return combinedSize;
        }
    }

    private static class FastScanner {
        private final BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
