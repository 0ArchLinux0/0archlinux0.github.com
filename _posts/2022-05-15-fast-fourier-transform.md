---
title: Fast Fourier Transform (FFT)
author: MINJUN PARK
date: 2022-05-15 06:59:08 +0900
categories: [Math]
tags: [Math, Algorithm, FFT, DFT, Cooley–Tukey, Polynomial Multiplication]
pin: false
lang: en
translation_key: fast-fourier-transform
permalink: /posts/fast-fourier-transform/
---

## What the transform computes

For a coefficient vector $a_0,\dots,a_{n-1}$, define the discrete Fourier transform (DFT) by evaluating its polynomial at the $n$th roots of unity:

$$
X_k = \sum_{j=0}^{n-1} a_j e^{-2\pi i jk/n},\qquad 0\leq k<n.
$$

The inverse transform uses the opposite sign and divides by $n$:

$$
a_j = \frac1n\sum_{k=0}^{n-1} X_k e^{2\pi i jk/n}.
$$

The fast Fourier transform (FFT) computes the DFT in $O(n\log n)$ time when $n$ is a power of two. If the input length is not a power of two, pad it with zeros to the next power of two.

## The radix-2 butterfly

Split the polynomial into its even- and odd-indexed coefficients:

$$
P(x)=P_{\mathrm{even}}(x^2)+xP_{\mathrm{odd}}(x^2).
$$

Let $\omega_n=e^{-2\pi i/n}$. Since $\omega_n^{k+n/2}=-\omega_n^k$, the two outputs for each $k<n/2$ are

$$
X_k=E_k+\omega_n^k O_k,\qquad
X_{k+n/2}=E_k-\omega_n^k O_k,
$$

where $E$ and $O$ are the transforms of the even and odd coefficient vectors. Each pair is a butterfly: one complex multiplication and a sum/difference. Recursively splitting the input gives $T(n)=2T(n/2)+O(n)=O(n\log n)$.

## Polynomial multiplication

The coefficient vector of a product is the convolution of the input vectors. Pad both vectors to a power-of-two length at least `a.size() + b.size() - 1`, transform them, multiply corresponding values, then apply the inverse transform. The implementation below uses in-place bit-reversal and butterflies; it does not copy the full vector at every stage.

## C++17 example

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

Output:

```text
4 13 22 15
```

The transform itself uses $O(n\log n)$ time and $O(1)$ auxiliary space; polynomial multiplication uses $O(n\log n)$ time and $O(n)$ space for its padded vectors. This implementation uses floating-point complex numbers. Rounding is reliable only while numerical error stays below one half; it is **not** an exact method for arbitrary coefficient sizes. The rounded coefficient must also fit in `long long`; this function does not check that bound. Use an NTT or coefficient splitting when exact integer results are required at larger magnitudes.

## Source history

Adapted from [“Fast Fourier Transform(FFT) - 고속 푸리에 변환”](https://ilikechicken.tistory.com/50), originally published on 2022-05-15 and updated on 2023-12-08 by MINJUN PARK; the source is marked CC BY 4.0. The DFT convention is stated explicitly, and the incomplete source snippets are replaced by a complete C++17 implementation with an inverse transform and a numerical-precision caveat.

## Reference

- [Fast Fourier transform — Algorithms for Competitive Programming](https://cp-algorithms.com/algebra/fft.html)