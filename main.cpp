#include <algorithm>
#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <cstring>
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
int n, q;

struct line {
  int l, r;
  int idx;
  line(int _l, int _r, int _idx): l(_l), r(_r), idx(_idx) {}
};
vector<pair<line, int>> vec_log;
vector<int> idx_map;

bool can_move(int a, int b) {
  // cout << a << " and " << b <<endl;
  return true;
}

bool cmp(pair<line, int> p1, pair<line, int> p2) {
  if(p1.first.l == p2.first.l) return p1.second < p2.second;
  return p1.first.l < p2.first.l;
}

int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  cin >> n >> q;
  for0(i, n) {
    int x1, x2, y; cin >> x1 >> x2 >> y;
    vec_log.push_back({ line(x1, x2, i), y });
  }
  idx_map.resize(n);
  sort(vec_log.begin(), vec_log.end(), cmp);
  // for(pair<line, int> p : vec_log) cout << p.first.l << " " << p.first.idx << endl;
  for0(i, n) { idx_map[vec_log[i].first.idx] = i; }
  // cout << " ---\n";
  // for(int e: idx_map) cout << e << " ";
  cout << endl;
  for0(i, q) {
    int a, b; cin >> a >> b;
    a--, b--;
    cout << can_move(idx_map[a], idx_map[b]) << endl;
  }
  return 0;
}