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
vector<line> lines;

int main() {
  FASTIO;
  cin >> n;
  for0(i, n) {
    line l; cin >> l.p1.first >> l.p1.second >> l.p2.first >> l.p2.second;
    lines.push_back(l);
  }
}
