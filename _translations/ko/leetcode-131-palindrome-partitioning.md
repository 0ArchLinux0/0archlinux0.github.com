---
title: LeetCode 131. 팰린드롬 분할
author: MINJUN PARK
date: 2022-01-06 01:57:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Palindrome Partitioning]
pin: false
lang: ko
translation_key: leetcode-131-palindrome-partitioning
permalink: /ko/posts/leetcode-131-palindrome-partitioning/
---

![image](https://user-images.githubusercontent.com/55131164/148258068-2e1914b9-8232-4518-8d93-13770bd229e7.png)

[문제 링크](https://leetcode.com/problems/palindrome-partitioning/)

## 팰린드롬 표를 미리 계산한 뒤 백트래킹

`palindrome[left][right]`를 양 끝 인덱스를 포함하는 부분 문자열 `s[left..right]`가 팰린드롬인지 나타내는 값으로 둔다. 양 끝 문자가 같고, 길이가 2 이하이거나 그 내부 부분 문자열도 팰린드롬이면 해당 부분 문자열은 팰린드롬이다.

`palindrome[left][right] = (s.charAt(left) == s.charAt(right)) && (right - left < 2 || palindrome[left + 1][right - 1])`

`left`를 오른쪽에서 왼쪽 방향으로 움직이며 표를 채우면 더 짧은 내부 구간을 먼저 계산하게 된다. 표 계산은 시간과 공간 모두 `O(n^2)`이다.

백트래킹으로 왼쪽부터 분할 하나를 만든다. 현재 시작 인덱스에서 끝 인덱스를 하나씩 시도하고, 표에서 팰린드롬으로 확인된 부분 문자열을 경로에 추가한 뒤 다음 인덱스부터 재귀한다. 시작 인덱스가 `n`에 도달하면 현재 경로를 복사해 완성된 분할로 저장한다. 이 종료 조건은 빈 문자열도 처리한다. 빈 경로를 결과에 추가하므로 `partition("")`은 빈 분할 하나를 반환한다. 비어 있지 않은 문자열의 완성된 분할 수는 최대 `2^(n-1)`개다. 결과 문자열 생성은 제외할 때 DFS 경로 및 호출 스택은 `O(n)` 공간을 사용한다. 결과 문자열 생성까지 포함한 최악 시간 복잡도는 `O(n^2 + n * 2^n)`, 보조 공간 복잡도는 `O(n^2)`이다.

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] palindrome = new boolean[n][n];

        for (int left = n - 1; left >= 0; left--) {
            for (int right = left; right < n; right++) {
                palindrome[left][right] = s.charAt(left) == s.charAt(right)
                        && (right - left < 2 || palindrome[left + 1][right - 1]);
            }
        }

        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, palindrome, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(
            String s,
            int start,
            boolean[][] palindrome,
            List<String> path,
            List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (!palindrome[start][end]) {
                continue;
            }
            path.add(s.substring(start, end + 1));
            backtrack(s, end + 1, palindrome, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```
