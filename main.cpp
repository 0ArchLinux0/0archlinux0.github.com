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
const int N = 200000;
const int st = 50;
// const int N = 1000000;
int n, m, t = 1, k, a, b, x;
string s;
vector<string> S;
vector<ll> A;
vector<ll> B;
// vector<vector<int>> child;
// int dp[N+1][2];
// map<string, int> hole.size()mm;
// char mm[53][53];

// int get(int idx, bool select) {
  
// }

struct my {
  int start;
  int end;
  int size;
};

int getBmove() {
  // vector<my> v;
  // for0(i, n) {
  //   B//
  //   bool f = 0;
  //   for(auto myele: v) {
  //     if(myele.end == )
  //   }
  // }
  map<int, int> cmm;
  map<int, vetor<int>> adjmap;
  map<int, int> deg;
  int deg = 0;
  int tcnt = 0;
  for0(i, n) {
    if(A[i] == B[i]) continue;
    adj[A[i]].push_back(B[i]);
    deg[A[i]]++;
    tcnt++;
  }
  vector<pii> pvec = vector<pii>(adjmap.begin(), adjmap.end());
  sort(pvec.begin(), pvec.end());
  for(auto p: pvec) {
    
  }


  for(int e: A) cmm[e]++;
  vector<pii> pvec = vector<pii>(cmm.begin(), cmm.end());
  sort(pvec.begin(), pvec.end(), [](pii p1, pii p2) { return p1.ps < p2.ps; });
  int to[(int)2e5+1];
  if(pvec.size() == 1) {
    for0(i, n) cout << A[i] << dkdk;
    cout << endl;
    return;
  }
  int pre = -1, fval = 0;
  // vector<int> cnt;
  map<int, int> mm;
  int val = 0, most = pvec.back().pf;
  for(auto p: pvec) {
    // cout << "what: " << p.pf << dkdk << p.ps << endl;
    if(pre == -1) {
      pre = fval = p.pf;
      // cnt.push_back(p.ps);
      mm[p.pf] = p.ps;
      val = p.ps;
    } else {
      to[pre] = p.pf;
      pre = p.pf;
      // cnt.push_back()
      if(p.ps != val) {
        mm[p.pf] = p.ps - val;
        val = p.ps;
      }
    }
  }

  int mcnt = 0;
  // cout << "point 1 \n";
  vector<int> ans(n);
  vector<int> idxvec;
  for0(i, n) {
    if(A[i] == most) {
      idxvec.push_back(i);
    } else {
      ans[i] = to[A[i]];
    }
  }
  // cout << "point 2 \n";
  int iidx = 0;
  for(auto p: mm) {
    // cout << "? " << p.pf << dkdk << p.ps << endl;
    for0(j, p.ps) {
      ans[idxvec[iidx++]] = p.pf;
    }
  }
  // cout << "point 3 \n";
  if(iidx < idxvec.size() - 1) {
    for(;iidx < idxvec.size(); iidx++) {
      ans[idxvec[iidx]] = most;
    }
  }
  // cout << "point 4 \n";
  for(int e: ans) cout << e << dkdk;
  cout << endl;
}

void solve() {
  int testmove = getBmove();
  map<int, int> cmm;
  for(int e: A) cmm[e]++;
  vector<pii> pvec = vector<pii>(cmm.begin(), cmm.end());
  sort(pvec.begin(), pvec.end(), [](pii p1, pii p2) { return p1.ps < p2.ps; });
  int to[(int)2e5+1];
  if(pvec.size() == 1) {
    for0(i, n) cout << A[i] << dkdk;
    cout << endl;
    return;
  }
  int pre = -1, fval = 0;
  // vector<int> cnt;
  map<int, int> mm;
  int val = 0, most = pvec.back().pf;
  for(auto p: pvec) {
    // cout << "what: " << p.pf << dkdk << p.ps << endl;
    if(pre == -1) {
      pre = fval = p.pf;
      // cnt.push_back(p.ps);
      mm[p.pf] = p.ps;
      val = p.ps;
    } else {
      to[pre] = p.pf;
      pre = p.pf;
      // cnt.push_back()
      if(p.ps != val) {
        mm[p.pf] = p.ps - val;
        val = p.ps;
      }
    }
  }

  int mcnt = 0;
  // cout << "point 1 \n";
  vector<int> ans(n);
  vector<int> idxvec;
  for0(i, n) {
    if(A[i] == most) {
      idxvec.push_back(i);
    } else {
      ans[i] = to[A[i]];
    }
  }
  // cout << "point 2 \n";
  int iidx = 0;
  for(auto p: mm) {
    // cout << "? " << p.pf << dkdk << p.ps << endl;
    for0(j, p.ps) {
      ans[idxvec[iidx++]] = p.pf;
    }
  }
  // cout << "point 3 \n";
  if(iidx < idxvec.size() - 1) {
    for(;iidx < idxvec.size(); iidx++) {
      ans[idxvec[iidx]] = most;
    }
  }
  // cout << "point 4 \n";
  for(int e: ans) cout << e << dkdk;
  cout << endl;
}

int main() {
  FASTIO;
  // #ifdef NDEBUG 
  //   FASTIO;
  // #else 
  //   freopen("input.txt", "r", stdin);
  // #endif
  cin >> t;
  while(t--) {
    cin >> n; A.resize(n); B.resize(n);
    for0(i, n) cin >> A[i]; 
    for0(i, n) cin >> B[i]; 
    solve();
  }
}

