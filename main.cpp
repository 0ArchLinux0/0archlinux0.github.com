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

int num[(int)1e6];
int even[(int)1e6];
int odd[(int)1e6];

void manacher() {
  int l_even = 0, l_odd = 0, r_even = -1, r_odd = -1;
  for0(i, n) {
    if(i <= r_odd) odd[i] = min(r_odd - i + 1, odd[r_odd + l_odd - i]);
    if(i <= r_even) even[i] = min(r_even - i, even[r_odd + l_odd - i - i]);
    while(i-odd[i] >= 0 && i+odd[i] < n && s[i-odd[i]] == s[i+odd[i]]) odd[i]++;
    while(i+even)
  }
}

int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  cin >> n;
  fill(even, even+n, 1);
  for0(i, n) cin >> num[i];
  int m; cin >> m;
  for0(i, m) {
    int s, e; cin >> s >> e;
    cout << query(s, e);
  }
  return 0;
}