---
title: Programmers. Find number of animals with same name
author: MINJUN PARK
date: 2021-12-27 05:30:00 +0900
categories: [Programmers, SQL]
tags:
  [
    SQL,
    Coding Interview,
    Programmers,
    Find number of animals with same name,
    동명 동물 수 찾기,
    프로그래머스
  ]
pin: false
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/59041>

<br>

```sql
SELECT NAME, COUNT(NAME) AS COUNT 
FROM ANIMAL_INS
WHERE NAME IS NOT NULL
GROUP BY NAME
HAVING COUNT(*) >= 2
ORDER BY NAME ASC;
```
