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
const int mod = 32768;
const int N = 802;
const int st = 50;
// const int N = 1000000;
int n, m, t, k, start = -1, ans = 0;
// int adj[1001][1001];
vector<vector<int>> adj;

struct Edge{
  int u, v;
  int cnt;
  Edge(int _u, int _v, int _c): u(_u), v(_v), cnt(_c) {};
};

vector<Edge> edges;

// void dfs(int cur) {
//   while(adj[cur].size()) {
//     int eidx = adj[cur].back();
//     if(edges[eidx].cnt) {
//       while(edges[eidx].cnt > 0) {
//         edges[eidx].cnt--;
//         dfs(edges[eidx].u + edges[eidx].v - cur);
//       }
//     }
//     else adj[cur].pop_back();
//   }
//   cout << cur + 1 << dkdk;
// }

void dfs(int cur) {
  while(adj[cur].size()) {
    int eidx = adj[cur].back();
    if(edges[eidx].cnt) {
      edges[eidx].cnt--;
      dfs(edges[eidx].u + edges[eidx].v - cur);
    }
    else adj[cur].pop_back();
  }
  cout << cur << dkdk;
}

void solve() {
  adj.resize(n+1);
  for1(i, n+1) {
    for1(j, n+1) {
      if(i == j) {
        edges.push_back(Edge(i, i, 1));
        int eidx = edges.size() - 1;
        adj[i].push_back(eidx);
      }
      if(i < j) {
        edges.push_back(Edge(i, j, 1));
        edges.push_back(Edge(j, i, 1));
        int eidx = edges.size() - 2;
        adj[i].push_back(eidx);
        adj[j].push_back(eidx);
        eidx++;
        adj[i].push_back(eidx);
        adj[j].push_back(eidx);
      }
    }
  }
  cout << (n*n + 1) << endl;
  dfs(1);
}

int main() {
  FASTIO; 
  // freopen("input.txt", "r", stdin);
  cin >> n;
  // adj.resize(n);
  solve();
}