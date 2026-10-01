---
title: BOJ 5670 - 携帯電話のキーパッド
author: MINJUN PARK
date: 2022-02-01 13:16:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, トライ, データ構造, 携帯電話のキーパッド]
pin: false
lang: ja
translation_key: boj-5670-phone-keyboard
permalink: /ja/posts/boj-5670-phone-keyboard/
source_permalink: /posts/BOJ-5670/
---

[問題: BOJ 5670 — 携帯電話のキーパッド](https://www.acmicpc.net/problem/5670) · [English](/posts/BOJ-5670/) · [한국어](/ko/posts/boj-5670-phone-keyboard/)

単語を入力するとき、最初の文字は必ず入力します。それ以降の文字は、入力済みの接頭辞に対応するトライのノードに子が2つ以上ある場合、またはその接頭辞自体が単語として完成している場合にのみ入力します。ある単語が別の単語の接頭辞なら、両者を区別するためにその単語の末尾の後でもう1文字入力する必要があります。

## トライによる入力回数の計算

まず、各データセットのすべての単語をトライに挿入します。各ノードは異なる子ノードの数と、単語の終端かどうかを保持します。そのため同じ単語を重複して挿入しても分岐数は増えず、ある単語が別の単語の接頭辞であるケースは終端フラグで判定できます。

各単語をもう一度たどり、最初の文字の入力回数を1とします。その後は、現在のノードの子が2つ以上あるか、現在のノードが単語の終端であれば、次の文字の入力回数を加算します。データセットごとにすべての入力回数を合計して単語数で割り、平均を小数点以下2桁で出力します。

すべての単語の文字数の合計を`S`とすると、トライの構築と各単語の走査はいずれも`O(S)`時間です。トライのノード数は`O(S)`で、各ノードはアルファベット26文字分の子参照を持ちます。

## Java

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		Trie trie;
		ArrayList<String> al = new ArrayList<>();
		while(true) {
			String ss = br.readLine();
			if(ss == null || ss.length() == 0) break;
			int n = toi(ss);
			if(n == 0) break;
			trie = new Trie();
			al.clear();

			for(int i = 0; i < n; i++) {
				String s = br.readLine();
				trie.add(s);
				al.add(s);
			}
			int count = 0;
			for(String s: al) {
				count += trie.solve(s);
			}
			sb.append(String.format(Locale.ROOT, "%.2f", (double)count / n)).append("\n");
		}
		System.out.print(sb);
	}

	static class Trie {
		Trie[] arr = new Trie[26];
		int num = 0;
		boolean tail;

		void add(String s) {
			int l = s.length();
			Trie t = this;
			for(int i = 0; i < l; i++) {
				int ch = s.charAt(i) - 'a';
				if(t.arr[ch] == null) {
					t.arr[ch] = new Trie();
					t.num++;
				}
				t = t.arr[ch];
			}
			t.tail = true;
		}

		int solve(String s) {
			Trie t = arr[s.charAt(0) - 'a'];
			int cnt = 1;

			for(int i = 1; i < s.length(); i++) {
				if(t.num + (t.tail ? 1 : 0) > 1) cnt++;
				t = t.arr[s.charAt(i) - 'a'];
			}
			return cnt;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
}
```
