---
title: 백준 5373번 - 큐빙
author: MINJUN PARK
date: 2022-03-13 18:28:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, Implementation, Cubing, 큐빙]
pin: false
lang: ko
translation_key: boj-5373-cubing
permalink: /ko/posts/boj-5373-cubing/
---

[백준 5373번: 큐빙](https://www.acmicpc.net/problem/5373)

## 모델과 회전 방향

각 스티커를 큐브 조각의 위치 $(x,y,z)$와 바깥쪽 법선 벡터로 표현합니다. 축은 큐브에 고정하며 $+x$는 오른쪽, $+y$는 위쪽, $+z$는 앞쪽을 가리킵니다. 위치 좌표는 $\{-1,0,1\}$ 중 하나이고 법선은 여섯 방향의 부호 있는 축 벡터 중 하나입니다. 색은 스티커에 붙어 있으므로 층을 돌리면 위치와 법선이 함께 이동합니다.

면 순서는 `U, D, F, B, L, R`이며 초기 색은 각각 흰색, 노란색, 빨간색, 주황색, 초록색, 파란색입니다. 각 면에는 아래쪽 행 방향과 오른쪽 열 방향 벡터를 고정합니다. `U`면에서는 열이 $+x$, 행이 $+z$를 향합니다. 따라서 0행은 뒤쪽 모서리이고 2행은 앞쪽 모서리입니다. 행, 열 순서로 출력하면 문제에서 요구하는 위에서 내려다본 윗면 방향이 됩니다. 코드의 나머지 면 방향은 스티커의 일관된 좌표를 정할 뿐, 큐브의 물리적 배치를 바꾸지 않습니다.

명령은 큐브 바깥에서 해당 면을 정면으로 바라보았을 때의 방향을 나타냅니다. 따라서 `+`는 그 시점에서 시계 방향 90도, `-`는 반시계 방향 90도 회전입니다. 오른손 좌표계에서는 바깥쪽 법선을 축으로 하는 양의 회전이 외부 시점에서 반시계 방향으로 보입니다. 그러므로 `+`는 선택한 면의 바깥쪽 법선에 대해 $-90^\circ$, `-`는 $+90^\circ$ 회전으로 구현합니다.

선택된 바깥 층의 각 스티커에 대해 위치 $p$와 법선 $v$를 면의 단위 법선 $a$ 주위로 회전시킵니다. 부호가 있는 90도 회전은 다음 식으로 계산합니다.

$$v' = a(a\cdot v) + s(a\times v),$$

여기서 시계 방향이면 $s=-1$, 반시계 방향이면 $s=+1$이며 위치 $p$에도 같은 식을 적용합니다. 모든 성분이 $\{-1,0,1\}$이므로 삼각함수 없이 정수 연산만으로 정확한 90도 회전을 구할 수 있습니다. 새 법선으로 도착 면을 찾고, 그 면의 행/열 방향 벡터와 내적하여 셀 좌표를 구합니다. 선택한 층의 모든 스티커를 회전하므로 해당 면 자체도 함께 회전합니다. 별도의 면 격자 회전이나 순서가 헷갈리는 옆면 띠 예외 처리가 필요하지 않습니다.

## 복잡도

스티커는 항상 54개입니다. 각 명령에서 54개를 확인하며 상수 번의 정수 연산을 하므로 명령이 $q$개일 때 시간 복잡도는 $O(54q)=O(q)$이고 공간 복잡도는 $O(54)=O(1)$입니다. 여섯 면의 입력과 출력도 고정 크기이므로 상수 시간입니다.

## C++17

```cpp
#include <array>
#include <iostream>
#include <string>

using namespace std;

struct Vec {
    int x, y, z;
};

Vec operator+(Vec a, Vec b) { return {a.x + b.x, a.y + b.y, a.z + b.z}; }
Vec operator-(Vec a, Vec b) { return {a.x - b.x, a.y - b.y, a.z - b.z}; }
Vec operator*(int k, Vec a) { return {k * a.x, k * a.y, k * a.z}; }
int dot(Vec a, Vec b) { return a.x * b.x + a.y * b.y + a.z * b.z; }
Vec cross(Vec a, Vec b) {
    return {a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x};
}

struct Face {
    Vec normal;
    Vec rowDown;
    Vec colRight;
};

// Face order: U, D, F, B, L, R.
const array<Face, 6> faces = {{
    {{0, 1, 0}, {0, 0, 1}, {1, 0, 0}},   // U
    {{0, -1, 0}, {0, 0, -1}, {1, 0, 0}},  // D
    {{0, 0, 1}, {0, -1, 0}, {1, 0, 0}},   // F
    {{0, 0, -1}, {0, -1, 0}, {-1, 0, 0}}, // B
    {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}},  // L
    {{1, 0, 0}, {0, -1, 0}, {0, 0, -1}}  // R
}};

using Cube = array<array<array<char, 3>, 3>, 6>;

Vec positionOf(int face, int row, int col) {
    return faces[face].normal
         + (row - 1) * faces[face].rowDown
         + (col - 1) * faces[face].colRight;
}

int faceOf(Vec normal) {
    for (int f = 0; f < 6; ++f) {
        if (dot(faces[f].normal, normal) == 1) return f;
    }
    return -1;
}

void destination(Vec position, Vec normal, int& face, int& row, int& col) {
    face = faceOf(normal);
    Vec offset = position - faces[face].normal;
    row = dot(offset, faces[face].rowDown) + 1;
    col = dot(offset, faces[face].colRight) + 1;
}

Vec quarterTurn(Vec v, Vec axis, int sign) {
    return dot(axis, v) * axis + sign * cross(axis, v);
}

void turn(const Cube& cube, Cube& next, char faceName, char direction) {
    int f;
    switch (faceName) {
        case 'U': f = 0; break;
        case 'D': f = 1; break;
        case 'F': f = 2; break;
        case 'B': f = 3; break;
        case 'L': f = 4; break;
        default:  f = 5; break; // R
    }

    const Vec axis = faces[f].normal;
    const int sign = (direction == '+') ? -1 : 1;

    for (int sourceFace = 0; sourceFace < 6; ++sourceFace) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                Vec position = positionOf(sourceFace, row, col);
                if (dot(position, axis) != 1) {
                    next[sourceFace][row][col] = cube[sourceFace][row][col];
                    continue;
                }

                Vec newPosition = quarterTurn(position, axis, sign);
                Vec newNormal = quarterTurn(faces[sourceFace].normal, axis, sign);
                int newFace, newRow, newCol;
                destination(newPosition, newNormal, newFace, newRow, newCol);
                next[newFace][newRow][newCol] = cube[sourceFace][row][col];
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int testCases;
    cin >> testCases;
    while (testCases--) {
        array<Cube, 2> cube;
        int active = 0;
        const array<char, 6> initialColor = {'w', 'y', 'r', 'o', 'g', 'b'};
        for (int f = 0; f < 6; ++f) {
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 3; ++col) {
                    cube[active][f][row][col] = initialColor[f];
                }
            }
        }

        int commandCount;
        cin >> commandCount;
        while (commandCount--) {
            string command;
            cin >> command;
            turn(cube[active], cube[1 - active], command[0], command[1]);
            active = 1 - active;
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                cout << cube[active][0][row][col];
            }
            cout << '\n';
        }
    }
}
```
