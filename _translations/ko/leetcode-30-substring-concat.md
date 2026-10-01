---
title: LeetCode. 30. Substring with Concatenation of All Words
author: MINJUN PARK
date: 2021-12-21 02:42:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Substring with Concatenation of All Words,
  ]
pin: false
lang: ko
translation_key: leetcode-30-substring-concat
permalink: /ko/posts/leetcode-30-substring-concat/
---

![image](https://user-images.githubusercontent.com/55131164/146821438-9874ee73-c746-4fd3-adf0-658e277a52e3.png)

[문제 링크](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)

## 풀이

모든 단어의 길이가 같고 0이 아니므로, 문자열을 단어 길이 `L`만큼 이동하는 여러 슬라이딩 윈도로 나눌 수 있습니다. 시작 오프셋을 `0`부터 `L - 1`까지 각각 처리합니다. `target`은 `words`에 포함된 각 단어의 요구 개수를 저장하고, `window`는 현재 윈도에 포함된 단어 개수를 저장합니다. 이렇게 하면 중복 단어도 정확히 처리할 수 있습니다.

오른쪽 포인터가 새 단어를 추가할 때 해당 단어가 `target`에 없으면 현재 윈도는 더 이상 유효할 수 없습니다. `window`와 단어 개수를 비우고 왼쪽 포인터를 오른쪽 포인터로 옮깁니다. 알려진 단어라면 윈도에 추가한 뒤, 그 단어가 요구 개수를 초과하는 동안 왼쪽에서 단어를 제거합니다. 윈도에 단어가 정확히 `words.length`개 있으면 답에 시작 위치를 추가한 다음 왼쪽 단어 하나를 제거합니다. 이 한 칸 축소는 다음 윈도에서 겹치는 답도 찾게 해 줍니다. 오프셋별로 찾은 위치를 마지막에 오름차순 정렬합니다.

단어 길이가 `L`, 입력 문자열 길이가 `N`, `words`의 단어 수가 `W`, 서로 다른 단어 수가 `U`, 반환 위치 수가 `R`이라고 합시다. 각 오프셋의 오른쪽 포인터는 한 번만 진행하므로 단어 구간을 처리하는 횟수는 총 `O(N)`입니다. 각 구간의 문자열 추출 비용 `O(L)`과 마지막 정렬을 포함한 시간 복잡도는 `O(N * L + R log R)`입니다. 요구 개수와 현재 개수를 저장하는 맵 및 윈도에는 각각 최대 `U`, `W`개의 항목이 필요하므로 이 부분의 공간은 `O(U + W)`입니다. 반환 결과의 공간 `O(R)`은 별도입니다.

## Java

```java
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (words.length == 0) {
            return result;
        }

        int wordLength = words[0].length();
        int wordCount = words.length;
        int totalLength = wordLength * wordCount;
        if (s.length() < totalLength) {
            return result;
        }

        Map<String, Integer> target = new HashMap<>();
        for (String word : words) {
            target.put(word, target.getOrDefault(word, 0) + 1);
        }

        for (int offset = 0; offset < wordLength; offset++) {
            Map<String, Integer> window = new HashMap<>();
            int left = offset;
            int right = offset;
            int count = 0;

            while (right + wordLength <= s.length()) {
                String word = s.substring(right, right + wordLength);
                right += wordLength;

                if (!target.containsKey(word)) {
                    window.clear();
                    count = 0;
                    left = right;
                    continue;
                }

                window.put(word, window.getOrDefault(word, 0) + 1);
                count++;

                while (window.get(word) > target.get(word)) {
                    String removed = s.substring(left, left + wordLength);
                    window.put(removed, window.get(removed) - 1);
                    left += wordLength;
                    count--;
                }

                if (count == wordCount) {
                    result.add(left);
                    String removed = s.substring(left, left + wordLength);
                    window.put(removed, window.get(removed) - 1);
                    left += wordLength;
                    count--;
                }
            }
        }

        Collections.sort(result);
        return result;
    }
}
```
