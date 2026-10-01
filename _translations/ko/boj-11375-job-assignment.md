---
title: 백준 11375번 - 열혈강호
author: MINJUN PARK
date: 2022-03-09 01:49:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, 이분 매칭, 열혈강호]
pin: false
lang: ko
translation_key: boj-11375-job-assignment
permalink: /ko/posts/boj-11375-job-assignment/
---

[백준 11375번: 열혈강호](https://www.acmicpc.net/problem/11375)

## 풀이: 이분 매칭

왼쪽 정점은 직원, 오른쪽 정점은 일이라고 생각합니다. 직원이 할 수 있는 일마다 직원에서 해당 일로 간선을 연결합니다. 유효한 배정은 매칭입니다. 선택된 간선에 직원이나 일이 두 번 이상 등장할 수 없습니다. 구할 값은 최대 매칭의 크기입니다.

직원을 하나씩 처리하면서 깊이 우선 탐색으로 증가 경로를 찾습니다. 현재 직원이 할 수 있는 일 중 이번 탐색에서 아직 방문하지 않은 일을 확인합니다. 해당 일이 아직 배정되지 않았다면 바로 배정합니다. 이미 배정되었다면, 그 일을 맡은 직원을 다른 가능한 일로 옮길 수 있는지 재귀적으로 시도합니다. 재배정에 성공하면 현재 직원이 그 일을 맡을 수 있습니다. 이 과정 덕분에 앞선 직원의 선택 때문에 가능한 일이 막혀 있더라도, 이후 탐색에서 기존 배정을 바꾸어 더 큰 매칭을 만들 수 있습니다.

방문 배열은 일 기준으로 두고, 시작 직원을 바꾸기 전에 초기화합니다. 현재 일을 방문 처리한 뒤 기존 담당자를 따라가므로 순환을 막고 한 번의 탐색에서 각 일을 최대 한 번만 확인합니다. 탐색이 성공할 때마다 매칭 크기는 정확히 1 증가하고, 실패하면 크기는 그대로입니다.

입력 첫 줄에는 직원 수 `N`과 일의 수 `M`이 주어집니다. 이어지는 직원별 한 줄에는 할 수 있는 일의 개수와 일 번호(1부터 시작)가 주어집니다. 배정할 수 있는 직원의 최대 수를 출력합니다. 직원과 일은 각각 최대 한 번씩만 배정될 수 있습니다.

이 DFS 구현에서 탐색 한 번의 최악 시간은 자격 간선 수를 `E`라고 할 때 `O(E)`입니다. 직원마다 한 번씩 탐색하므로 전체 시간 복잡도는 `O(N E)`이고, 그래프와 매칭에 필요한 공간 복잡도는 `O(N + M + E)`입니다.

## C++17

```cpp
#include <iostream>
#include <vector>

using namespace std;

vector<vector<int>> eligibleJobs;
vector<int> assignedWorker;
vector<bool> visitedJob;

bool findAssignment(int worker) {
    for (int job : eligibleJobs[worker]) {
        if (visitedJob[job]) continue;
        visitedJob[job] = true;

        if (assignedWorker[job] == -1 ||
            findAssignment(assignedWorker[job])) {
            assignedWorker[job] = worker;
            return true;
        }
    }
    return false;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int workerCount, jobCount;
    cin >> workerCount >> jobCount;

    eligibleJobs.resize(workerCount);
    assignedWorker.assign(jobCount, -1);
    for (int worker = 0; worker < workerCount; ++worker) {
        int count;
        cin >> count;
        eligibleJobs[worker].resize(count);
        for (int& job : eligibleJobs[worker]) {
            cin >> job;
            --job;
        }
    }

    int matchingSize = 0;
    for (int worker = 0; worker < workerCount; ++worker) {
        visitedJob.assign(jobCount, false);
        if (findAssignment(worker)) ++matchingSize;
    }

    cout << matchingSize << '\n';
    return 0;
}
```
