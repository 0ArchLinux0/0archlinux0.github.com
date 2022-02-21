#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;
typedef long long ll;
const int N = 1 << 20;
const int mod = 1e9 + 7;
const int adjust = 2e5;
int n;
int seg[2 * N];

struct point {
  int x; 
  int y;
  int idx;
};

vector<point> points;
vector<point> sweep;

void update(int y) {
  for(y+=N; y > 0; y>>=1) seg[y]++;
}

int get(int basis) {
  int cnt = 0;
  for(int l=basis + 1 + N, r = 2*N - 1; l < r; l>>=1, r>>=1) {
    if(l & 1) cnt+=seg[l++];
    if(r & 1) cnt+=seg[--r];
  }
  return cnt;
}

void clearSeg() {
  for(int i = 0; i < 2*N; i++) seg[i] = 0;
}

bool cmp(point p1, point p2) { return p1.x < p2.x; }


int main() {
  cin >> n;
  for(int i = 0; i < n; i++) {
    point ptmp;
    cin >> ptmp.x >> ptmp.y;
    ptmp.idx = i;
    ptmp.y += adjust;
    points.push_back(ptmp);
  } 

  sort(points.begin(), points.end(), cmp);

  int staging_x = points[0].x;
  int left[n], right[n];
  vector<vector<point>> x_group;
  vector<point> tmp;

  for(point p : points) {
    if(staging_x != p.x)  {
      x_group.push_back(tmp);
      tmp.clear();
      tmp.push_back(p);
      staging_x = p.x;
    } else tmp.push_back(p);
  }

  if(tmp.size()) x_group.push_back(tmp);
  for(vector<point> vec: x_group) {
    for(point p: vec) left[p.idx] = get(p.y);
    for(point p: vec) update(p.y);
  }

  clearSeg();
  x_group.clear();
  tmp.clear();
  staging_x = points.back().x;

  for(int i = points.size() - 1; i >= 0; i--) {
    point p = points[i];
    if(staging_x != p.x)  {
      x_group.push_back(tmp);
      tmp.clear();
      tmp.push_back(p);
      staging_x = p.x;
    } else {
      tmp.push_back(p);
    }
  }

  if(tmp.size()) x_group.push_back(tmp);
  for(vector<point> vec: x_group) {
    for(point p: vec) {
      right[p.idx] = get(p.y);
    }
    for(point p: vec) {
      update(p.y);
    }
  }

  long sum = 0;
  for(int i = 0; i < n; i++) {
    sum = sum + (long)left[i] * right[i];
    sum %=mod;
  }

  cout << sum;
  return 0;
}