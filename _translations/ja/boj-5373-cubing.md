---
title: "BOJ 5373番 - キュービング"
author: MINJUN PARK
date: 2022-03-13 18:28:00 +0900
categories: [Record, Code]
tags: [C++, Algorithm, BOJ, Implementation, Cubing, 큐빙]
pin: false
lang: ja
translation_key: boj-5373-cubing
permalink: /ja/posts/boj-5373-cubing/
---

[BOJ 5373番: キュービング](https://www.acmicpc.net/problem/5373)

## モデルと回転方向

各ステッカーをキューブ片の位置 $(x,y,z)$ と外向き法線で表します。軸はキューブに固定し、$+x$ は右、$+y$ は上、$+z$ は前を向くものとします。位置の各座標は $\{-1,0,1\}$ のいずれかで、法線は6方向の符号付き軸ベクトルのいずれかです。色はステッカーに属するため、層を回すと位置と法線が一緒に移動します。

面の順序は `U, D, F, B, L, R` で、初期色はそれぞれ白、黄、赤、オレンジ、緑、青です。各面には下向きの行方向ベクトルと右向きの列方向ベクトルを固定します。`U` 面では列が $+x$、行が $+z$ を向きます。そのため0行目は奥側の辺、2行目は手前側の辺です。行、列の順に出力すると、問題で求められる上から見た上面の向きになります。コードで定める他の面のローカル方向はステッカーに一貫した座標を与えるものであり、キューブの物理的な配置を変えるものではありません。

コマンドの方向は、キューブの外側から指定された面を正面に見たときの向きです。したがって `+` はその視点で時計回りに90度、`-` は反時計回りに90度回します。右手系では外向き法線を軸とする正の回転は、外側から見ると反時計回りです。よって `+` は選択面の外向き法線を軸に $-90^\circ$、`-` は $+90^\circ$ 回転として実装します。

選択された外層にある各ステッカーについて、位置 $p$ と法線 $v$ を面の単位法線 $a$ の周りに回します。符号付き90度回転は次の式で計算できます。

$$v' = a(a\cdot v) + s(a\times v),$$

ここで時計回りなら $s=-1$、反時計回りなら $s=+1$ で、位置 $p$ にも同じ式を適用します。成分はすべて $\{-1,0,1\}$ なので、三角関数を使わず整数演算だけで正確な90度回転を得られます。新しい法線から移動先の面を特定し、その面の行・列方向ベクトルとの内積でセル座標を求めます。層内のすべてのステッカーを回すため、選択した面自体も同時に回ります。面グリッドを別途回す処理や、側面の帯の順序を個別に扱うケース分けは不要です。

## 計算量

ステッカーは常に54枚です。各コマンドで54枚を調べ、それぞれ定数回の整数演算を行うため、コマンド数を $q$ とすると時間計算量は $O(54q)=O(q)$、空間計算量は $O(54)=O(1)$ です。6面の読み込みと出力も固定サイズなので定数時間です。

## C++17

{% raw %}
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
{% endraw %}
