#include<bits/stdc++.h>
using namespace std;
typedef long long ll;
const int N=1e6+1;
ll seg[2*N];
int n;

void modify(int pos, int v){
  for(seg[pos+=n]=v; pos>1; pos>>=1) 
    seg[pos>>1] = seg[pos]+seg[pos^1];
}

ll sum(int l, int r){
  ll sum=0;
  for(l+=n, r+=n; l<r; l>>=1, r>>=1){
    if(l&1) sum+=seg[l++];
    if(r&1) sum+=seg[--r];
  }
  return sum;
}

int main(){
  ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
  int m; 
  cin>>n>>m;
  while(m--){
    int query, a, b; cin>>query>>a>>b;
    if(query!=0) modify(a,b);
    else {
      if(a>b) swap(a,b);
      cout<<sum(a,b+1)<<'\n';
    }
  }
  return 0;
}

