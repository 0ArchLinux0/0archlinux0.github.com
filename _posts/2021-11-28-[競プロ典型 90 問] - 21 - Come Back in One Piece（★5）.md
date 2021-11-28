---
title: Atcoder. 021 - Come Back in One Piece(5)
author: MINJUN PARK
date: 2021-11-28 3:02:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Graph,
    SCC,
    Tarjan's algorithm
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_u>

<br>
Using class would make code lot more readable and easy to understand but I wanted to solved it fast since it's late.
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  public static int[] parentArr;
  public static boolean[] end;
  public static int cnt = 1;
  public static long ans = 0;
  public static Stack<Integer> stack = new Stack<>();
  public static ArrayList<ArrayList<Integer>> edge = new ArrayList<ArrayList<Integer>>();;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] line = br.readLine().split(" ");
    int n = atoi(line[0]), m = atoi(line[1]);
    parentArr = new int[n + 1];
    end = new boolean[n + 1];
    for(int i = 0; i <= n; i++) edge.add(new ArrayList<Integer>());

    for(int i = 0; i < m; i++) {
      line = br.readLine().split(" ");
      edge.get(atoi(line[0])).add(atoi(line[1]));
    }

    for(int i = 1; i <= n; i++) if(parentArr[i] == 0) dfs(i);
    System.out.println(ans);
  }

  public static int dfs(int v) {
    int parent, count = 0;
    parentArr[v] = parent = cnt++;
    stack.push(v);
    for(int desV : edge.get(v)) {
      if(parentArr[desV] == 0) parent = Math.min(parent, dfs(desV));
      else if(!end[desV]) parent = Math.min(parent, parentArr[desV]);
    } 

    if(parent == parentArr[v]) {
      while(true) {
        count++;
        int pop = stack.pop();
        end[pop] = true;
        if(pop == v) break;
      }
      ans += count * (count - 1l)/2;
    }

    return parent;
  }

  public static int atoi(String s) { return Integer.parseInt(s); }
}
```
