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
const int mod = (int)1e9;
const int st = 50;
const int N = 5e3 + 1;
int n, m, k, t = 1, pn, mn, glen, ans;
string s;
ll dp[201][201];

int get(int left, int target) {
  if(!left) return !target;
  int &ref = dp[left][target];
  if(ref != -1) return ref;
  for0(i, min(target, n)+1) ref += get(left-1, target-i);
  ref++; ref%=mod;
  return ref;
}

int main() {
  FASTIO;
  cin >> n >> k;
  memset(dp, -1, sizeof(dp));
  cout << get(k, n);
}

