#include<iostream>
#include<algorithm>
using namespace std;
bool cmp(pair<int,string> st1, pair<int,string> st2){
    return st1.first<st2.first;
}
int main(){//10814
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    int N;
    cin>>N;
    pair<int,string> people[N];
    for(int i=0; i<N; ++i){
        cin>>people[i].first>>people[i].second;
    }
    stable_sort(people,people+N,cmp);
    for(int i=0; i<N; ++i){
        cout<<people[i].first<<' '<<people[i].second<<'\n';
    }
}