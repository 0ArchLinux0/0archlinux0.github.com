---
title: 高速フーリエ変換 (FFT)
author: MINJUN PARK
date: 2022-05-15 06:59:08 +0900
categories: [Math]
tags: [数学, アルゴリズム, FFT, DFT, Cooley–Tukey, 多項式乗算]
lang: ja
translation_key: fast-fourier-transform
permalink: /ja/posts/fast-fourier-transform/
pin: false
---

## 変換で計算する値

係数ベクトル $a_0,\dots,a_{n-1}$ の離散フーリエ変換 (DFT) を、多項式を $n$ 個の $n$ 乗根で評価した値として定義する。

$$
X_k = \sum_{j=0}^{n-1} a_j e^{-2\pi i jk/n},\qquad 0\leq k<n.
$$

逆変換では符号を反転し、$n$ で割る。

$$
a_j = \frac1n\sum_{k=0}^{n-1} X_k e^{2\pi i jk/n}.
$$

高速フーリエ変換 (FFT) は、$n$ が2のべき乗なら DFT を $O(n\log n)$ 時間で計算する。入力長が2のべき乗でない場合は、次の2のべき乗まで0で埋める。

## Radix-2 バタフライ

多項式を偶数番目と奇数番目の係数に分ける。

$$
P(x)=P_{\mathrm{even}}(x^2)+xP_{\mathrm{odd}}(x^2).
$$

$\omega_n=e^{-2\pi i/n}$ とすると $\omega_n^{k+n/2}=-\omega_n^k$ なので、$k<n/2$ に対する二つの出力は次のようになる。

$$
X_k=E_k+\omega_n^k O_k,\qquad
X_{k+n/2}=E_k-\omega_n^k O_k,
$$

ここで $E$ と $O$ は偶数・奇数係数ベクトルの変換結果である。出力の組は、複素数の乗算一回と加減算で計算できるバタフライである。入力を再帰的に半分に分けると、$T(n)=2T(n/2)+O(n)=O(n\log n)$ となる。

## 多項式乗算

多項式の係数の積は、二つの係数ベクトルの畳み込みである。両方のベクトルを `a.size() + b.size() - 1` 以上の最小の2のべき乗まで0で埋め、変換後に対応要素を掛け合わせ、逆変換する。以下の実装はインプレースのビット反転とバタフライを使うため、各段階でベクトル全体をコピーしない。

## C++17 の例

```cpp
#include <algorithm>
#include <cmath>
#include <complex>
#include <iostream>
#include <stdexcept>
#include <vector>
using namespace std;

using Complex = complex<double>;

void fft(vector<Complex>& values, bool inverse) {
    const size_t n = values.size();
    if (n == 0 || (n & (n - 1)) != 0) {
        throw invalid_argument("FFT length must be a nonzero power of two");
    }

    for (size_t i = 1, j = 0; i < n; ++i) {
        size_t bit = n >> 1;
        while (j & bit) {
            j ^= bit;
            bit >>= 1;
        }
        j ^= bit;
        if (i < j) swap(values[i], values[j]);
    }

    const double pi = acos(-1.0);
    for (size_t length = 2; length <= n;) {
        const double angle = (inverse ? 2.0 : -2.0) * pi / length;
        const Complex root(cos(angle), sin(angle));
        const size_t half = length / 2;

        for (size_t begin = 0; begin < n; begin += length) {
            Complex factor(1.0, 0.0);
            for (size_t offset = 0; offset < half; ++offset) {
                const Complex even = values[begin + offset];
                const Complex odd = values[begin + offset + half] * factor;
                values[begin + offset] = even + odd;
                values[begin + offset + half] = even - odd;
                factor *= root;
            }
        }

        if (length == n) break;
        length <<= 1;
    }

    if (inverse) {
        for (Complex& value : values) value /= static_cast<double>(n);
    }
}

vector<long long> convolve(const vector<long long>& a, const vector<long long>& b) {
    if (a.empty() || b.empty()) return {};

    const size_t result_size = a.size() + b.size() - 1;
    size_t n = 1;
    while (n < result_size) n <<= 1;

    vector<Complex> fa(n), fb(n);
    for (size_t i = 0; i < a.size(); ++i) fa[i] = static_cast<double>(a[i]);
    for (size_t i = 0; i < b.size(); ++i) fb[i] = static_cast<double>(b[i]);

    fft(fa, false);
    fft(fb, false);
    for (size_t i = 0; i < n; ++i) fa[i] *= fb[i];
    fft(fa, true);

    vector<long long> result(result_size);
    for (size_t i = 0; i < result_size; ++i) {
        result[i] = llround(fa[i].real());
    }
    return result;
}

int main() {
    const vector<long long> a{1, 2, 3};
    const vector<long long> b{4, 5};
    const vector<long long> product = convolve(a, b);

    for (size_t i = 0; i < product.size(); ++i) {
        if (i > 0) cout << ' ';
        cout << product[i];
    }
    cout << '\n';
}
```

出力:

```text
4 13 22 15
```

FFT 自体は $O(n\log n)$ 時間、$O(1)$ の補助空間を使う。多項式乗算ではパディングしたベクトルに $O(n)$ の空間を使う。この実装は浮動小数点の複素数を使用する。丸め誤差が0.5未満の範囲でのみ整数係数の正確な結果を期待でき、任意に大きな係数に対する正確性は保証しない。丸め後の係数は `long long` の範囲内にある必要があり、この関数はその範囲を検査しない。大きな整数を正確に計算するにはNTTまたは係数分割を使う。

## 出典と改訂履歴

MINJUN PARKが2022-05-15に公開し、2023-12-08に更新、CC BY 4.0を表示した[「Fast Fourier Transform(FFT) - 고속 푸리에 변환」](https://ilikechicken.tistory.com/50)を基にした。DFTの符号規約を明記し、不完全だったコードを逆変換と数値精度上の注意を含む完全なC++17実装に置き換えた。

## 参考資料

- [Fast Fourier transform — Algorithms for Competitive Programming](https://cp-algorithms.com/algebra/fft.html)