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

struct line {
  pair<int, int> p1, p2;
};

int n;
line l1, l2;

int cross_check(line base, line line) {
  int di1 = line.p1.first - base.p1.first, dj1 = line.p1.second - base.p1.second;
  int di2 = line.p2.first - base.p1.first, dj2 = line.p2.second - base.p1.second;
  int base_di = base.p2.first - base.p1.first, base_dj = base.p2.second - base.p1.second;
  bool ccw1 = di1*base_dj - base_di*dj1, ccw = di2*base_dj - base_di*dj2;
  if((ccw1 > 0 && ccw2 > 0) || (ccw1 < 0 && ccw2 < 0)) return -1;
  if(ccw1 == ccw2) return 0;
  return 1;
}

int main() {
  FASTIO;
  cin >> l1.p1.first >> l1.p1.second >> l1.p2.first >> l1.p2.second;
  cin >> l2.p1.first >> l2.p1.second >> l2.p2.first >> l2.p2.second;
  if(!cross_check(l1, l2) || !cross_check(l2, l1)) {
    cout << 0;
    return 0;
  }
  
}
