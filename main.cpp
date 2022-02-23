#include <algorithm>
#include <iostream>
#include <vector>
// #include<bits/stdc++.h>
using namespace std;
typedef long long ll;
const int INF = 987654321;
int n, m, ans = 0;
int map[500][500];
bool visit[500][500];
int di[] = { 1, 0, -1, 0 };
int dj[] = { 0, 1, 0, -1 };

void dfs(int ii, int jj, int cnt, int sum) {
  if(cnt == 4) {
    ans = max(ans, sum);
    return;
  }
  for(int i = 0; i < 4; i++) {
    int ai = ii + di[i], aj = jj + dj[i];
    if(ai < 0 || aj < 0 || ai >= n || aj >= m) continue;
    if(visit[ai][aj]) continue;
    visit[ai][aj] = true;
    dfs(ai, aj, cnt + 1, sum + map[ai][aj]);
    visit[ai][aj] = false;
  }  
}

void checkfk() {
  for(int i = 0; i < n; i++) {
    for(int j = 0; j < m; j++) {
      if(i > 0 && j < m - 1 && j > 0) {
        ans = max(ans, map[i][j] + map[i][j+1] + map[i][j-1] + map[i-1][j]);
      }
      if(i > 0 && i < n - 1 && j < m - 1) {
        ans = max(ans, map[i][j] + map[i][j+1] + map[i+1][j] + map[i-1][j]);
      }
      if(i < n - 1 && j < m - 1 && j > 0) {
        ans = max(ans, map[i][j] + map[i][j+1] + map[i][j-1] + map[i+1][j]);
      }
      if(i > 0 && i < n - 1 && j > 0) {
        ans = max(ans, map[i][j] + map[i][j-1] + map[i+1][j] + map[i-1][j]);
      }
    }
  }
}

int main() {
  ios_base::sync_with_stdio(0);
  cin.tie(0); cout.tie(0);
  cin >> n >> m;
  for(int i = 0; i < n; i++) {
    for(int j = 0; j < m; j++) cin >> map[i][j];
  }

  for(int i = 0; i < n; i++) {
    for(int j = 0; j < m; j++) {
      visit[i][j] = true;
      dfs(i, j, 1, map[i][j]);
      visit[i][j] = false;
    }
  }
  checkfk();
  cout << ans;
  return 0;
}