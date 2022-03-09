#include <algorithm>
#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <cstring>
#include <stack>
#include <cmath>
// #include <bits/stdc++.h>
using namespace std;
#define FASTIO ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0)
#define command_param int argc, char *argv[]
#define for0(i, n) for(int i = 0; i < n; i++)
#define for1(i, n) for(int i = 1; i < n; i++)
#define fori(s, e) for(int i = s; i < e; i++)
#define ll long long
#define endl "\n"
#define nulls '\0'
#define dkdk " "
const int MAX = 2002;
const int INF = 987654321;

int n;
vector<pair<int,int>> points;

bool ccw(pair<int, int> line[2], pair<int,int> p) {
  int dx1 = line[1].first - line[0].first, dy1 = line[1].second - line[0].second;
  int dx2 = p.first - line[0].first, dy2 = p.second - line[0].second;
  ll result = (ll)dx1*dy2 - (ll)dy1*dx2;
  return result > 0;
}
int main() {
  FASTIO;
  pair<int, int> l1[2], l2[2];
  cin >> l1[0].first >> l1[0].second >> l1[1].first >> l1[1].second >> l2[0].first >> l2[0].second >> l2[1].first >> l2[1].second;
  bool meet = true;
  bool c1 = ccw(l1, l2[0]), c2 = ccw(l1, l2[1]);
  if(c1 == c2) meet = false;
  if(meet) {
    c1 = ccw(l2, l1[0]), c2 = ccw(l2, l1[1]);
    if(c1 == c2) meet = false;
  }
  cout << (meet ? 1 : 0);
  return 0;
}
