#include <algorithm>
#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <cstring>
#include <stack>
#include <cmath>
#include <tuple>
#include <climits>
#include <fstream>
#include <sstream>
// #include <bits/stdc++.h>
using namespace std;
#ifdef ONLINE_JUDGE
  #define NDEBUG
#endif
#define FASTIO ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0)
#define command_param int argc, char *argv[]
#define for0(i, n) for(int i = 0; i < n; i++)
#define for1(i, n) for(int i = 1; i < n; i++)
#define FOR(i, s, e) for(int i = s; i < e; i++)
#define pf first
#define ps second
#define pii pair<int,int> 
#define ll long long
#define lll __int128
#define endl "\n"
#define nulls '\0'
#define dkdk " "

const int INF = 987654321;
const ll INFLL = 1e13;
// const int mod = (int)1e9+7;
const int mod = 10007;
const int N = 1001;
const int st = 50;
// const int N = 1000000;
int n, m, t, k;

int rev(int n) {
  int exp = mod-2, mul = n, ans = 1;
  while(exp) {
    if(exp & 1) ans = (ans * mul) % mod;
    mul = (mul * mul) % mod;
    exp >>= 1;
  }
  return ans;
}

void solve() {
  int v = 1, fk, fnk;
  if(k == 0) fk = 1;
  if(n-k == 0) fnk = 1;
  for1(i, n+1) {
    v = (v * i) % mod;
    if(i == k) fk = v;
    if(i == n-k) fnk = v;
  }
  cout << v*rev(fk*fnk%mod)%mod;
}

int main() {
  #ifdef NDEBUG 
    FASTIO;
  #else 
    freopen("input.txt", "r", stdin);
  #endif
  cin >> n >> k;
  solve();
}

