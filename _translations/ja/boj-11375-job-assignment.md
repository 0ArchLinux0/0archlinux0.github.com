---
title: BOJ 11375 - 情熱的なカンホ
author: MINJUN PARK
date: 2022-03-09 01:49:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, 二部マッチング, 情熱的なカンホ]
pin: false
lang: ja
translation_key: boj-11375-job-assignment
permalink: /ja/posts/boj-11375-job-assignment/
---

[BOJ 11375: 情熱的なカンホ](https://www.acmicpc.net/problem/11375)

## 方針: 二部マッチング

左側の頂点を社員、右側の頂点を仕事とします。社員が担当できる仕事ごとに、社員から仕事へ辺を張ります。有効な割り当てはマッチングです。選んだ辺の中で、同じ社員や仕事が複数回現れてはいけません。求めるのは最大マッチングのサイズです。

社員を1人ずつ処理し、深さ優先探索で増加路を探します。現在の社員が担当できる仕事のうち、この探索でまだ訪問していないものを調べます。その仕事が空いていれば割り当てます。すでに別の社員が担当している場合は、その社員を別の担当可能な仕事へ移せるか再帰的に試します。再割り当てに成功すれば、現在の社員がその仕事を担当できます。この付け替えにより、先に行った選択で仕事が塞がっていても、後の探索で割り当てを変更してより大きなマッチングを作れます。

訪問配列は仕事を添字とし、探索を始める社員ごとにリセットします。現在の仕事を訪問済みにしてから担当者をたどるため、探索の循環を防ぎ、1回の探索で各仕事を高々1度だけ調べます。探索が成功するたびにマッチングサイズはちょうど1増え、失敗した場合は変化しません。

入力の先頭には社員数 `N` と仕事数 `M` が与えられます。その後、社員ごとに1行ずつ、担当可能な仕事数と仕事番号（1始まり）が与えられます。割り当てられる社員の最大数を出力します。各社員と仕事はそれぞれ高々1回だけ割り当てられます。

このDFS実装では、適性を表す辺の数を `E` とすると、1回の探索の最悪時間は `O(E)` です。社員ごとに探索するため、全体の時間計算量は `O(N E)`、グラフとマッチングに必要な空間計算量は `O(N + M + E)` です。

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
