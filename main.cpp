// #include <algorithm>
// #include <iostream>
// #include <vector>
// #include <map>
// #include <set>
// #include <queue>
// #include <cstring>
// #include <stack>
// #include <cmath>
// #include <tuple>
// #include <climits>
// #include <fstream>
// #include <sstream>
#include <bits/stdc++.h>
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
// const int N = 1001;
const int st = 50;
const int N = 5e3 + 1;
int n, m, t, k, pn, mn, glen, ans;
vector<int> a;
int dp[8][8][8];
int g[8], acc[8];

void dfs(int ci, int gn) {
  g[ci] = gn;
  if(ci == n - 1) {
    fill(acc, acc + 8, 0);
    for0(i, n) acc[g[i]] += a[i];
    int mul = 1;
    for0(i, glen) mul *= acc[i];
    if(mul == 1) return;
    ans = max(ans, mul);
    return;
  }
  for0(i, glen) {
    dfs(ci+1, i);
  }
}

void solve() {
  dfs(0, 0);
  cout << ans;
}

int main() {
  #ifdef NDEBUG 
    FASTIO;
  #else
    freopen("input.txt", "r", stdin);
  #endif
  cin >> n;
  a.resize(n); memset(g, -1, sizeof(g));
  for0(i, n) cin >> a[i];
  // group
  cin >> pn >> mn;
  glen = mn + 1;
  // g[0] = 0;
  solve();
}

