---
title: BOJ. アリの巣 (14725)
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Ant tunnel, 개미굴, Review]
pin: false
lang: ja
translation_key: boj-14725-ant-tunnel
permalink: /ja/posts/boj-14725-ant-tunnel/
source_permalink: /posts/BOJ-14725/
---

[問題: BOJ 14725 — アリの巣](https://www.acmicpc.net/problem/14725) · [English](/posts/BOJ-14725/) · [한국어](/ko/posts/boj-14725-ant-tunnel/)

各入力行は、アリの巣のルートから始まる1つの経路です。各トークンをトライに挿入すると、すでに存在する接頭辞は共有されます。ノードの子は、その接頭辞の次に続く食べ物です。`TreeMap` は子を辞書順に保持するため、深さ優先探索では事前に子をソートしたりコピーしたりせずに、兄弟ノードを必要な順序で訪問できます。

探索では、ルートから現在のノードまでの辺の数だけ `--` を1つずつ追加し、その後に現在のトークンと改行を出力します。そのため、ルートの子にはダッシュが付かず、深さが1段増えるごとに `--` が正確に1つ増えます。

トークンの総数を `S`、トライのノード数を `V` とすると、順序付きマップを使う挿入の最悪時間計算量は `O(S log V)`、走査は `O(V)` です。トライの空間計算量は `O(V)` です。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        Trie root = new Trie();

        for (int i = 0; i < n; i++) {
            String[] foods = input.readLine().split(" ");
            Trie node = root;
            for (int j = 1; j < foods.length; j++) {
                node = node.children.computeIfAbsent(foods[j], key -> new Trie());
            }
        }

        root.print(0);
        System.out.print(output);
    }

    private static class Trie {
        private final TreeMap<String, Trie> children = new TreeMap<>();

        private void print(int depth) {
            for (Map.Entry<String, Trie> child : children.entrySet()) {
                for (int i = 0; i < depth; i++) {
                    output.append("--");
                }
                output.append(child.getKey()).append('\n');
                child.getValue().print(depth + 1);
            }
        }
    }
}
```
