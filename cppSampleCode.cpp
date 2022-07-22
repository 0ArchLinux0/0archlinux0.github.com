#include<bits/stdc++.h>
using namespace std;
const int MAX=1e6+1;
int arr[MAX], dp[MAX], fnd[MAX];

int main(){
    ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
    int n; cin >> n;
    int ans=1;
    cin >> arr[0]; fnd[0]=1;
    for(int i=1 ; i<n ; ++i){
        cin >> arr[i];
        if(dp[ans]<arr[i]){
            dp[++ans]=arr[i];
            fnd[i]=ans;
        }
        else{
            dp[lower_bound(dp,dp+ans-1,arr[i])-dp]=arr[i];
            fnd[i]=lower_bound(dp,dp+ans-1,arr[i])-dp;
        }
    }
    cout << ans << '\n';
    
    //for(int i=0;i<n;i++) cout << fnd[i] << ' ';
    stack<int> lis;
    for(int i=n-1; i+1 && ans+1 ; i--){
        if(fnd[i]==ans){
            ans--;
            lis.push(i);}
    }
    
    n=lis.size();
    while(n--){
        cout << arr[lis.top()] << ' ';
        lis.pop();
    }
}