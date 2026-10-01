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
#define all(A) A.begin(),A.end()
const int INF = 987654321;
const ll INFLL = 1e18;
// const int mod = (int)1e9+7;
const int mod = 10007;
const int N = 10000;
const int st = 50;
int n, m, t = 1, k, a, b, x;
string s;
vector<string> S;
vector<ll> A;

struct edge{
  int i;
  int w;
  edge(int _i, int _w): i(_i), w(_w) {};
};
vector<vector<edge>> edges;
ll dp[N];
int nodecnt[N];

ll dfs(int idx, int parent, ll v) {
  ll sum = v;
  nodecnt[idx] = 1;
  for(edge e: edges[idx]) {
    if(e.i == parent) continue;
    sum += dfs(e.i, idx, v + e.w);
    nodecnt[idx] += nodecnt[e.i];
  }
  return sum;
}

void propagate(int idx, int parent, int cost) {
  if(idx) dp[idx] = dp[parent] + (ll)(n - 2 * nodecnt[idx]) * cost;
  for(edge e: edges[idx]) {
    if(e.i == parent) continue;
    propagate(e.i, idx, e.w);
  }
}

void solve() {
  memset(nodecnt, 0, sizeof(nodecnt));
  memset(dp, 0, sizeof(dp));
  dp[0] = dfs(0, -1, 0);
  propagate(0, -1, 0);
  cout << *min_element(dp, dp + n) << endl;
}

int main() {
  // FASTIO;
  #ifdef NDEBUG 
    FASTIO;
  #else 
    freopen("input.txt", "r", stdin);
  #endif
  // cin >> t;
  while(1) {
    cin >> n; if(!n) return 0;
    edges.clear(); edges.resize(n);
    for0(i, n-1) {
      int a, b, w; cin >> a >> b >> w;
      edges[a].push_back(edge(b, w));
      edges[b].push_back(edge(a, w));
    }
    solve();
  }
}

