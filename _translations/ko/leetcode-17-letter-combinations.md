---
title: LeetCode. 17. Letter Combinations of a Phone Number
author: MINJUN PARK
date: 2021-12-04 02:44:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, DFS, Letter Combinations of a Phone Number]
pin: false
lang: ko
translation_key: leetcode-17-letter-combinations
permalink: /ko/posts/leetcode-17-letter-combinations/
---

![image](https://user-images.githubusercontent.com/55131164/144647989-bdb91fe5-3725-409c-b187-a37ffe84882d.png)

[문제](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

## 백트래킹

입력에는 `2`부터 `9`까지의 숫자가 주어집니다. 각 숫자는 전화 키패드의 문자에 직접 대응합니다. 입력이 비어 있으면 만들 수 있는 조합도 없으므로 빈 리스트를 반환합니다.

재귀는 숫자를 한 자리씩 처리합니다. 인덱스가 `i`인 호출에 진입할 때 `StringBuilder`에는 앞의 `i`개 숫자에 대해 각각 하나씩 선택한 문자가 입력 순서대로 들어 있다는 것이 불변식입니다. 현재 숫자에 대응하는 각 문자에 대해 문자를 추가하고 다음 인덱스로 재귀 호출한 뒤, 다음 문자를 시도하기 전에 방금 추가한 문자를 삭제합니다. 깊이가 `N`에 도달하면 접두 문자열에 입력 숫자마다 문자가 하나씩 있으므로 이를 완성된 조합으로 저장합니다.

조합은 최대 `4^N`개이고 각 완성 문자열을 복사하는 데 `O(N)` 시간이 걸리므로, 출력 크기를 고려한 최악 시간 복잡도는 `O(N * 4^N)`입니다. 결과 리스트를 제외하면 빌더와 재귀 호출 스택의 보조 공간 복잡도는 `O(N)`입니다.

## Java

```java
class Solution {
    private static final String[] KEYPAD = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        List<String> answer = new ArrayList<>();
        if (digits.isEmpty()) {
            return answer;
        }

        backtrack(digits, 0, new StringBuilder(), answer);
        return answer;
    }

    private void backtrack(String digits, int index, StringBuilder prefix, List<String> answer) {
        if (index == digits.length()) {
            answer.add(prefix.toString());
            return;
        }

        String letters = KEYPAD[digits.charAt(index) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            prefix.append(letters.charAt(i));
            backtrack(digits, index + 1, prefix, answer);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
```
