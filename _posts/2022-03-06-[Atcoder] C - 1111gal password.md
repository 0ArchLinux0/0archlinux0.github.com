---
title: AtCoder. ABC 238 C - 1111gal password
author: MINJUN PARK
date: 2022-03-06 09:00:00 +0900
categories: [AtCoder, ABC Contest]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    ABC contest
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/abc242/tasks/abc242_c>
<br>

```c++
#include <algorithm>
#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <cstring>
#include <stack>
// #include <bits/stdc++.h>
using namespace std;
#define for0(i, n) for(int i = 0; i < n; i++)
#define for1(i, n) for(int i = 1; i < n; i++)
#define fori(s, e) for(int i = s; i < e; i++)
#define ll long long
#define endl "\n"
#define nulls '\0'
#define dkdk " "
const int MAX = 5e5 + 1;
const int INF = 987654321;
const ll mod = 998244353;

int n;
vector<vector<ll>> dp;

int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  cin >> n;
  dp.resize(n + 1); dp[1].resize(10);
  for1(i, 10) dp[1][i] = 1l;
  for(int i = 2; i <= n; i++) {
    dp[i].resize(10);
    dp[i][1] = dp[i-1][1] + dp[i-1][2];
    dp[i][9] = dp[i-1][9] + dp[i-1][8];
    for(int j = 2; j < 9; j++) dp[i][j] = dp[i-1][j] + dp[i-1][j-1] + dp[i-1][j+1];
    for1(j, 10) {
      if(dp[i][j] >= mod<<1) dp[i][j] -= mod<<1;
      else if(dp[i][j] >= mod) dp[i][j] -= mod;
    }
  }
  ll ans = 0;
  for1(j, 10) ans+=dp[n][j];
  ans%=mod;
  cout << ans;
  return 0;
}
```
