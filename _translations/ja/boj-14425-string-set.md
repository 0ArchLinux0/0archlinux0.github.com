---
title: BOJ 14425 - 文字列集合
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 文字列集合, ハッシュセット]
pin: false
lang: ja
translation_key: boj-14425-string-set
permalink: /ja/posts/boj-14425-string-set/
source_permalink: /posts/BOJ-14425/
---

[問題: BOJ 14425 — 文字列集合](https://www.acmicpc.net/problem/14425) · [English](/posts/BOJ-14425/) · [한국어](/ko/posts/boj-14425-string-set/)

`N`個の文字列を保存し、`M`個の文字列を確認します。確認する文字列のうち、保存した文字列に含まれるものがいくつあるか数えます。すべての文字列は英小文字のみで構成されます。

## ハッシュセット

入力文字列を`HashSet<String>`に保存します。同じ文字列を複数回挿入してもセットの内容は変わらないため、保存文字列の重複は結果に影響しません。各クエリでは`contains`を使って文字列全体が完全一致するかを調べ、存在すれば個数を増やします。各クエリは1回だけ処理するため、答えへの寄与は最大1です。

長さ`L`の文字列をハッシュセットに挿入または検索する時間は、ハッシュ計算と文字列内容の比較を含めて期待`O(L)`です。挿入する文字列とクエリ文字列全体に対する期待時間計算量は`O(全体の文字数)`です。空間計算量は、異なる保存文字列の全体の文字数に対して`O(全体の文字数)`です。

## Java

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = getArr();
		int n = arr[0], m = arr[1];
		Set<String> words = new HashSet<>();

		for(int i = 0; i < n; i++) words.add(br.readLine());

		int count = 0;
		for(int i = 0; i < m; i++) {
			if(words.contains(br.readLine())) count++;
		}

		System.out.print(count);
	}

	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
}
```
