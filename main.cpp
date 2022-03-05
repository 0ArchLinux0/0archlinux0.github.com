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

int num[(int)1e6];
int even[(int)1e6];
int odd[(int)1e6];
void manacher() {

}

int main() {
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  cin >> n;
  for0(i, n) cin >> num[i];
  int m; cin >> m;
  for0(i, m) {
    int s, e; cin >> s >> e;
    cout << query(s, e);
  }
  return 0;
}