---
title: 고속 푸리에 변환 (FFT)
author: MINJUN PARK
date: 2022-05-15 06:59:08 +0900
categories: [Math]
tags: [수학, 알고리즘, FFT, DFT, Cooley–Tukey, 다항식 곱셈]
lang: ko
translation_key: fast-fourier-transform
permalink: /ko/posts/fast-fourier-transform/
pin: false
---

## 변환이 계산하는 값

계수 벡터 $a_0,\dots,a_{n-1}$의 이산 푸리에 변환(DFT)을 다항식을 $n$차 단위근에서 평가한 값으로 정의한다.

$$
X_k = \sum_{j=0}^{n-1} a_j e^{-2\pi i jk/n},\qquad 0\leq k<n.
$$

역변환은 부호를 반대로 하고 $n$으로 나눈다.

$$
a_j = \frac1n\sum_{k=0}^{n-1} X_k e^{2\pi i jk/n}.
$$

고속 푸리에 변환(FFT)은 $n$이 2의 거듭제곱일 때 DFT를 $O(n\log n)$ 시간에 계산한다. 입력 길이가 2의 거듭제곱이 아니면 다음 2의 거듭제곱까지 0을 채운다.

## Radix-2 버터플라이

다항식을 짝수 번째 계수와 홀수 번째 계수로 나눈다.

$$
P(x)=P_{\mathrm{even}}(x^2)+xP_{\mathrm{odd}}(x^2).
$$

$\omega_n=e^{-2\pi i/n}$라 두면 $\omega_n^{k+n/2}=-\omega_n^k$이므로 $k<n/2$에 대해 두 출력은 다음과 같다.

$$
X_k=E_k+\omega_n^k O_k,\qquad
X_{k+n/2}=E_k-\omega_n^k O_k,
$$

여기서 $E$와 $O$는 짝수 및 홀수 계수 벡터의 변환 결과다. 출력 한 쌍은 복소수 곱셈 한 번과 덧셈·뺄셈으로 계산되는 버터플라이다. 입력을 재귀적으로 반씩 나누면 $T(n)=2T(n/2)+O(n)=O(n\log n)$을 얻는다.

## 다항식 곱셈

다항식 계수의 곱셈은 두 계수 벡터의 합성곱이다. 두 벡터를 `a.size() + b.size() - 1` 이상인 가장 작은 2의 거듭제곱 길이로 채운 뒤 각각 변환하고, 대응 원소끼리 곱한 다음 역변환한다. 아래 구현은 제자리 비트 역순 배열과 버터플라이를 사용하므로 단계마다 벡터 전체를 복사하지 않는다.

## C++17 예제

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

출력:

```text
4 13 22 15
```

FFT 자체는 $O(n\log n)$ 시간과 $O(1)$ 보조 공간을 사용한다. 다항식 곱셈은 패딩한 벡터 때문에 $O(n)$ 공간을 사용한다. 이 구현은 실수 기반 복소수를 사용한다. 반올림 오차가 0.5보다 작을 때만 정확한 정수 결과를 기대할 수 있으며, 임의로 큰 계수에 대해 정확성을 보장하지 않는다. 반올림한 계수는 `long long` 범위에도 들어와야 하며, 이 함수는 그 범위를 검사하지 않는다. 더 큰 정수의 정확한 계산에는 NTT나 계수 분할을 사용한다.

## 출처와 수정 이력

MINJUN PARK이 2022-05-15에 게시하고 2023-12-08에 수정했으며 CC BY 4.0을 표시한 [“Fast Fourier Transform(FFT) - 고속 푸리에 변환”](https://ilikechicken.tistory.com/50)을 바탕으로 작성했다. DFT 부호 규약을 명시하고, 불완전한 원문 코드를 역변환과 수치 정밀도 주의를 포함한 완전한 C++17 구현으로 교체했다.

## 참고 자료

- [고속 푸리에 변환 — Algorithms for Competitive Programming](https://cp-algorithms.com/algebra/fft.html)