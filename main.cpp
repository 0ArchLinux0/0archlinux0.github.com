// #include <algorithm>
// #include <iostream>
// #include <vector>
// #include <map>
// #include <set>
// #include <queue>
// #include <cstring>
// #include <stack>
// #include <cmath>
// // #include <bits/stdc++.h>
// using namespace std;
// #define FASTIO ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0)
// #define command_param int argc, char *argv[]
// #define for0(i, n) for(int i = 0; i < n; i++)
// #define for1(i, n) for(int i = 1; i < n; i++)
// #define fori(s, e) for(int i = s; i < e; i++)
// #define ll long long
// #define endl "\n"
// #define nulls '\0'
// #define dkdk " "

// int n, m, N;
// // vector<int> nvec;
// vector<pair<int, int>> nvec;
// vector<vector<int>> seg;


// int main() {
//   FASTIO;
//   cin >> n >> m; 
//   // N = 
//   nvec.resize(n); 
//   // seg.resize
//   for0(i, n) { int in; cin >> in; nvec[i] = { in, i }; };
//   sort(nvec.begin(), nvec.end());
//   // init();
//   for0(i, m) {
//     int a, b, c; cin >> a >> b >> c;
//     a--, b--;
//     // cout << query(a, b, c) << endl;
//     int num, rank = 0;
//     for0(j, n) {
//       pair<int,int> p = nvec[j];
//       if(a <= p.second && p.second <= b) rank++;
//       if(rank == c) {
//         num = p.first;
//         break;
//       }
//     }
//     cout << num << endl;
//   }
//   return 0;
// }
