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

int n;

int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  stack<int> st;
  vector<int> list;
  int max_len = 1;
  cin >> n;
  list.resize(n);
  for0(i, n) cin >> list[i];
  int i = 0;
  while(i < n) {
    while(i < n && (st.empty() || st.top() < list[i])) {
      st.push(list[i++]);
    }
    if(i == n) break;
    int len = 1; if(st.top() == list[i]) { i++, len++; } st.pop(); 
    if(i == n) {
      max_len = max(max_len, len);
      break;
    }
    bool moved = false;
    while(i < n && !st.empty() && st.top() == list[i++]) { moved = true; len+=2; st.pop(); }
    if(moved) i--;
    max_len = max(max_len, len);
    st = {};
    if(i == n) break;
  }
  cout << max_len;
  return 0;
}