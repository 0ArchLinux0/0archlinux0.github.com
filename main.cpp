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

string s;
int q;
int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  cin >> s >> q;
  for0(i, q) {
    ll t, k, plus = 0; cin >> t >> k;
    k--;
    while(k != 0 && t) { 
      plus += (k & 1) ? 2 : 1;
      k >>= 1; t--;
    }
    ll idx = (plus + s[k] - 'A' + t) % 3;
    cout << (char)('A' + idx) << endl;
  }
  return 0;
}