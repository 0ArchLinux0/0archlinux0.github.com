---
title: BOJ 14425 - 문자열 집합
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 문자열 집합, 해시 집합]
pin: false
lang: ko
translation_key: boj-14425-string-set
permalink: /ko/posts/boj-14425-string-set/
source_permalink: /posts/BOJ-14425/
---

[문제: BOJ 14425 — 문자열 집합](https://www.acmicpc.net/problem/14425) · [English](/posts/BOJ-14425/) · [日本語](/ja/posts/boj-14425-string-set/)

`N`개의 문자열을 저장하고 `M`개의 문자열을 확인합니다. 확인할 문자열 중 저장된 문자열에 포함된 것이 몇 개인지 셉니다. 모든 문자열은 영문 소문자로만 이루어집니다.

## 해시 집합

입력 문자열을 `HashSet<String>`에 저장합니다. 같은 문자열을 여러 번 삽입해도 집합은 변하지 않으므로 저장 문자열의 중복은 결과에 영향을 주지 않습니다. 각 질의마다 `contains`를 사용해 문자열 전체가 정확히 일치하는지 확인하고, 존재하면 개수를 증가시킵니다. 각 질의는 한 번만 처리하므로 답에 최대 1만 기여합니다.

길이가 `L`인 문자열을 해시 집합에 삽입하거나 조회하는 데는 해시 계산과 문자열 내용 비교를 포함해 기대 `O(L)` 시간이 걸립니다. 삽입 문자열과 질의 문자열 전체에 대해 기대 시간 복잡도는 `O(전체 문자 수)`입니다. 공간 복잡도는 서로 다른 저장 문자열의 전체 문자 수에 대해 `O(전체 문자 수)`입니다.

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
