#include <bits/stdc++.h>
using namespace std;
int c, p, n;

struct skill {
  string name;
  int level;
  skill(string n, int l): name(n), level(l) {}
};

struct project {
  string name;
  int day, score, best_before, roles;
  vector<skill> skills;
  // vector
  project(string n, int d, int s, int bb, int r): name(n), day(d), score(s), best_before(bb), roles(r) {}
};

int main() {
  cin >> c >> p;
  set<string> pskill;
  map<string, map<string, int>> contrib_skills;
  map<string, map<string, int>> project_skills;
  vector<project> projects;
  int same_cnt = 0;
  for(int i = 0; i < c; i++) {
    string name; cin >> name;
    int skill_num; cin >> skill_num;
    map<string, int> skill_set;
    for(int j = 0; j < skill_num; j++) {
      string skill_name;
      int skill_level;
      cin >> skill_name >> skill_level;
      pskill.insert(skill_name);
      skill_set.insert(pair<string, int> (skill_name, skill_level ));
    }
    contrib_skills.insert(pair<string, map<string, int>>(name, skill_set));
  }
  for(int i = 0; i < p; i++) {
    string name;
    int day, score, best_before, roles;
    cin >> name >> day >> score >> best_before >> roles;
    map<string, int> skill_set;
    vector<skill> skill_vec;

    //for checking same skill
    bool same = false;
    set<string> skillset; 
    for(int j = 0; j < roles; j++) {
      string skill_name;
      int skill_level;
      cin >> skill_name >> skill_level;
      if(skillset.count(skill_name)) same_cnt++;
      skillset.insert(skill_name);
      skill_set.insert(pair<string, int> (skill_name, skill_level ));
      skill_vec.push_back(skill(skill_name, skill_level));
    }
    contrib_skills.insert(pair<string, map<string, int>>(name, skill_set));
    project p = project(name, day, score, best_before, roles);
    p.skills = skill_vec;
    projects.push_back(p);
  }
  cout << " fk " << endl;
  for(project pro: projects) {
    for(skill s: pro.skills) {
      cout << s.name << " and " << s.level << endl;
    }
    cout << endl;
  }
  string str;
  ifstream file("file.txt");
    
  while (getline(file, str)) {
    cout << str << '\n';
  }
  cout << c << " , " << "p skill num " << pskill.size() << endl;;
  cout << same_cnt;
  return 0;
}


// #include<bits/stdc++.h>
// using namespace std;

// struct Skill{
//     string name;
//     int level;
// };

// struct Human{
//     string name;
//     vector<Skill> skills;
//     int time = 0;
// };

// struct Project{
//     int Days;
//     int Score;
//     int Best;
//     string name;
//     vector<Skill> skills;
// };

// struct Answer{
//     string ProjName;
//     vector<int> People;
// };

// vector<Human> humans;
// vector<Project> projects;

// int main(){
//     ios_base::sync_with_stdio(false);
//     cin.tie(0);

//     int C, P; cin >> C >> P;
    
//     humans.resize(C);
//     for(int i=0; i<C; ++i){
//         string Name; int N; cin >> Name >> N;
//         humans[i].name = Name;
//         humans[i].skills.resize(N);
//         for(int j=0; j<N; ++j){
//             string sk; int lv;
//             cin >> sk >> lv;
//             humans[i].skills[j] = {sk,lv};
//         }
//     }
    
//     projects.resize(P);
//     for(int i=0; i<P; ++i){
//         string PName; int D, S, B, R;
//         cin >> PName >> D >> S >> B >> R;
//         projects[i].Days = D;
//         projects[i].Score = S;
//         projects[i].Best = B;
//         projects[i].name = PName;
//         for(int j=0; j<B; ++j){
//             string sk; int lv;
//             cin >> sk >> lv;
//             projects[i].skills[j] = {sk,lv};
//         }
//     }

//     sort(projects.begin(), projects.end(), [&](Project &x, Project &y)->bool{
//         int sx=0, sy=0;
//         int nx=x.skills.size(), ny=y.skills.size();
//         for(auto &xx : x.skills) sx += xx.level;
//         for(auto &yy : y.skills) sy += yy.level;
//         return (sx*ny==sy*nx)?( x.Best < y.Best ):(sx*ny<sy*nx);
//     });

//     vector<Answer> answer;
//     for(int pidx=0; pidx<P; ++pidx){
//         Project proj = projects[pidx];
//         Answer ans; ans.ProjName = proj.name; ans.People.resize(proj.skills.size());
//         for(int i=0; i<proj.skills.size(); ++i){
//             string sk = proj.skills[i].name;
//             int lv = proj.skills[i].level;
//             for(int j=0; j<C; ++j){
//                 for(auto &ski : humans[j].skills){
//                     if(ski.name != sk) continue;
//                     if(ski.level < lv) continue;
//                     if(ski.level == lv){
//                         ans.People[i]
//                     }
//                 }
//             }
//         }
//     }
// }