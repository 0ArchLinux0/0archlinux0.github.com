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
const int mod = 998244353;
const int st = 50;
const int N = 5e3 + 1;
int n, m, k, t = 1, pn, mn, glen, ans;
string s;
ll dp[52][2502];


int get(int idx, int ub) {
  if(dp[idx][ub] != -1) return dp[idx][ub];
  if(idx == n-1) {
    if(ub < 1) return 0;
    else return dp[idx][ub] = min(m, ub);
  } 
  dp[idx][ub]++;
  for1(i, min(ub,m)+1) {
    dp[idx][ub] = (dp[idx][ub]+get(idx+1, ub - i));
  }
  dp[idx][ub] %= mod;
  return dp[idx][ub];
}

int main() {
  FASTIO;
  memset(dp, -1, sizeof(dp));
  cin >> n >> m >> k;
  // dp[0][0] = 1;
  // for(int i = 0; i < n; i++) {
  //   for(int j = 0; j < k; j++) {
  //     // for(int v = 1; v <= min(m, k - j); v++) dp[i+1][j + v] = (dp[i+1][j+v] + dp[i][j]) % mod;
  //     for(int v = 1; v <= m; v++) {
  //       if(j + v <= k) dp[i+1][j + v] = (dp[i+1][j+v] + dp[i][j]) % mod;
  //     }
  //   }
  // }
  // ll ans = 0;
  // for1(i, k+1) ans += dp[n][i];
  // cout << ans%mod;
  cout << get(0, k) << endl;
}

