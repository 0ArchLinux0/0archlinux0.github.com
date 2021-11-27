---
title: Sort. Radix Sort
author: MINJUN PARK
date: 2021-05-09 08:53:00 +0900
categories: [Algorithm, Sort]
tags:
  [
    Code Block,
    Code Snippet,
    JavaScript,
    Radix,
    Code,
    Radix,
    Sort,
    Coding Interview,
  ]
pin: true
---
## Radix Sort

Time Complexity: O(nw) (n: number of items, w: length of item) <br>
Space Complexity: O(n+w)

```javascript
function radixSort(array) {
  let max;
  let digitArr = new Array(10);
  for(let i = 0; i < digitArr.length ; i++){
      digitArr[i] = [];
  }
  for(let i = 0 ; i < array.length ; i++){
      if(i===0 || max < array[i]){
          max = array[i];
      }
  }
  let maxLog = Math.log10(max)+1;
  for (let digit = 1 ; digit <= maxLog ; digit++){
      let digit10 = Math.pow(10,digit);
      let tempArr = [];
      for(let i = 0 ; i < array.length ;i++){
          if(digit === 1){
              digitArr[array[i]%digit10].push(array[i]);
          }else{
              tempDigit = Math.floor((array[i]%digit10)*10/digit10);
              digitArr[tempDigit].push(array[i]);
          }
      }

      for(let i = 0 ; i < digitArr.length ; i++){
          while(digitArr[i].length!==0){
              tempArr.push(digitArr[i][0]);
              digitArr[i].shift();
          }
      }
      array = [...tempArr];
    }
    return array;
}
```
