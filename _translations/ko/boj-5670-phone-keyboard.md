---
title: BOJ 5670 - 휴대폰 자판
author: MINJUN PARK
date: 2022-02-01 13:16:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트라이, 자료 구조, 휴대폰 자판]
pin: false
lang: ko
translation_key: boj-5670-phone-keyboard
permalink: /ko/posts/boj-5670-phone-keyboard/
source_permalink: /posts/BOJ-5670/
---

[문제: BOJ 5670 — 휴대폰 자판](https://www.acmicpc.net/problem/5670) · [English](/posts/BOJ-5670/) · [日本語](/ja/posts/boj-5670-phone-keyboard/)

단어를 입력할 때 첫 글자는 항상 입력합니다. 그다음 글자는 현재까지 입력한 접두사의 트라이 노드에 자식이 둘 이상 있거나, 그 접두사 자체가 완성된 단어일 때만 입력합니다. 어떤 단어가 다른 단어의 접두사라면 그 단어와 더 긴 단어를 구별해야 하므로 끝 글자 다음의 추가 입력이 필요합니다.

## 트라이에서 입력 횟수 세기

먼저 각 데이터셋의 모든 단어를 트라이에 삽입합니다. 각 노드는 서로 다른 자식 노드의 수와 단어의 끝인지 여부를 저장합니다. 따라서 같은 단어를 중복 삽입해도 분기 수는 증가하지 않으며, 한 단어가 다른 단어의 접두사인 경우는 끝 표시로 처리됩니다.

각 단어를 다시 순회하며 첫 글자에 대해 횟수 1을 더합니다. 이후 현재 노드의 자식 수가 2 이상이거나 현재 노드가 단어의 끝이면 다음 글자 입력 횟수를 더합니다. 데이터셋마다 모든 단어의 입력 횟수를 합산한 뒤 단어 수로 나누어 평균을 구하고, 소수점 이하 두 자리까지 출력합니다.

전체 단어의 문자 수를 `S`라 하면 트라이 구성과 단어별 순회 모두 `O(S)` 시간입니다. 트라이에는 `O(S)`개의 노드가 필요하며, 각 노드는 알파벳 26개에 대한 자식 참조를 가집니다.

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
