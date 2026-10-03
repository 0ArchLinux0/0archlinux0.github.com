# Tistory migration review — 2026-10-02

Source: [ilikechicken.tistory.com](https://ilikechicken.tistory.com/) · Canonical site: [0archlinux0.github.io](https://0archlinux0.github.io/) · Repository: [0ArchLinux0/0archlinux0.github.com](https://github.com/0ArchLinux0/0archlinux0.github.com) · First migration branch: `tistory-migration-2026` · Completion branch: `tistory-exclusive-completion-2026`

The complete machine-readable source capture, canonical inventory, route map, classification, ranking, audit notes, and correction log are in [`tistory-migration-2026.json`](tistory-migration-2026.json). Twenty numeric Tistory article pages were found through the sitemap and archive pages; all twenty article bodies were captured. Original inventory baselines: `origin/main` at `7d5ec4eb79220aa160821ea1d52b1c14b6fc415d` and `origin/gh-pages` at `5049dc8e7d7379c7f1988dd145f32edfe6084bc0`. The completion batch was based on `origin/main` at `f3a970c65e9def9f54b57671305cd9002fed9034` and `origin/gh-pages` at `71b6d337f3c9b68ee2a783a35046f09f56c59863`.

## Disposition

| Classification | Count | Action |
|---|---:|---|
| Exact duplicate | 0 | — |
| GitHub version is better | 15 | Keep existing canonical article; do not copy legacy text |
| Partial overlap | 1 | Enhanced the existing Bézout article with an independently proved field-polynomial extension; no duplicate article |
| Tistory-only | 4 | Migrated as new canonical articles |
| Conflict requiring review | 0 | ID 52's original threshold was rejected; a safe sufficient bound was proved and migrated |
| Tistory version better | 0 | — |
| Low-value archive | 0 | The inventory covers article pages, not archive pages |

The five-proof first review batch covered Tistory IDs **17, 25, 45, 46, and 47**. Their stronger canonical English proofs were retained rather than duplicated. The first migration batch added IDs **39, 48, and 50**. This completion batch adds ID **52** with a corrected conservative recurrence proof and integrates ID **25**'s polynomial extension into the existing Bézout article. The complete set of twenty Tistory article pages now has an explicit disposition.

## Per-post classification

| Tistory ID | Source topic | Disposition | Canonical route / decision |
|---:|---|---|---|
| 14 | BOJ 3653 — Movie Collection | GitHub better | `/posts/boj3653/` |
| 15 | BOJ 1395 — Switches | GitHub better | `/posts/boj1395/` |
| 17 | Euclidean Algorithm | GitHub better | `/posts/Euclidean-Algorithm/` |
| 23 | Floyd–Warshall Algorithm | GitHub better | `/posts/Floyd-Warshall-Algorithm/` |
| 24 | Max-flow min-cut theorem | GitHub better | `/posts/Max-flow-min-cut-theorem/` |
| 25 | Bézout's identity, Part 1 | Partial overlap; existing article enhanced | `/posts/Bézout's-identity-p1/`; polynomial identity proved over a field in all existing locales |
| 28 | BOJ 1035 — Moving Pieces | GitHub better | `/posts/boj1035/` |
| 31 | BOJ 1006 — Defense | GitHub better | `/posts/boj1006/` |
| 33 | BOJ 19565 — Sequence | GitHub better | `/posts/boj19565/` |
| 34 | Flow Network | GitHub better | `/posts/Flow-network/` |
| 35 | Analysis (1), Chinese title | GitHub better | `/posts/Analysis-解析学-1/` |
| 37 | Analysis (2) | GitHub better | `/posts/Analysis-해석학(2)/` |
| 38 | Analysis (1), Korean title | GitHub better | Same canonical Analysis (1) route as ID 35 |
| 39 | BOJ 7579 — App | Tistory-only; migrated | `/posts/boj-7579-app/` |
| 45 | Bolzano–Weierstrass theorem | GitHub better | `/posts/Bolzano-Weierstrass-theorem/` |
| 46 | Monotone Convergence Theorem | GitHub better | `/posts/Monotone-Convergence-Theorem/` |
| 47 | Nested Interval Property | GitHub better | `/posts/Nested-Interval-Property/` |
| 48 | BOJ 1199 — Euler Circuit | Tistory-only; migrated | `/posts/boj-1199-euler-circuit/` |
| 50 | Fast Fourier Transform | Tistory-only; migrated | `/posts/fast-fourier-transform/` |
| 52 | BOJ 10803 — Make a Square proof | Tistory-only; migrated with a conservative sufficient bound | `/posts/boj-10803-square-tiling-proof/` |


## Complete Tistory-exclusive coverage

The four unique Tistory articles are IDs **39, 48, 50, and 52**. IDs 39, 48, and 50 were migrated in the first batch; ID 52 now has English, Korean, and Japanese routes at `/posts/boj-10803-square-tiling-proof/`, `/ko/posts/boj-10803-square-tiling-proof/`, and `/ja/posts/boj-10803-square-tiling-proof/`. ID 25's distinct polynomial content was added to the existing Bézout article in all three locales rather than creating a duplicate. The fifteen stronger canonical overlaps remain unchanged. No unique Tistory-only article remains unintegrated.

## Ranked review candidates

| Rank | Tistory ID | Priority | Status | Decision |
|---:|---:|---|---|---|
| 1 | 48 | P0 | Migrated | Unique Euler-circuit article; corrected missing disconnected-component check and recursion-depth risk. |
| 2 | 50 | P0 | Migrated | Durable FFT tutorial; replaced two incomplete, non-compiling snippets with a tested C++17 implementation. |
| 3 | 39 | P1 | Migrated | Unique cost-indexed knapsack; clarified at-most-budget DP and removed the GNU VLA. |
| 4 | 52 | P1 | Migrated — conservative bound | The original threshold is not claimed; the corrected sufficient condition and guillotine-cut proof are independently established. |
| 5 | 25 | P2 | Existing article enhanced | The polynomial Bézout extension is independently proved over a field and merged into the existing article; no arbitrary coefficient-ring claim. |

## Mathematical proof audit and corrections

- **ID 17 — Euclidean algorithm:** the legacy code declaration `int gcd(int a, b)` is invalid C++; the canonical article states the nonnegative input domain, handles `gcd(a, 0)`, and gives the direct common-divisor proof.
- **ID 25 — Bézout:** the legacy text's polynomial statement was not proved on the source page. The existing canonical integer proof remains intact; a separate polynomial proof now establishes the result over $K[x]$ for a field $K$ and explicitly avoids generalizing to arbitrary coefficient rings.
- **ID 45 — Bolzano–Weierstrass:** the legacy page conflates the bounded-sequence theorem with compactness of closed bounded sets, assumes a bounded sequence has an infinite range, and uses malformed interval-bisection notation. The canonical proof handles finite-dimensional sequences coordinatewise and treats compactness separately.
- **ID 46 — Monotone convergence:** the legacy tail estimate says `n ≤ N`; the convergence argument requires `n ≥ N`. The canonical proof uses the correct supremum tail bound.
- **ID 47 — Nested intervals:** the legacy uniqueness inequality is invalid. The canonical proof directly obtains `|x-y| ≤ |I_n|` for every `n`, then uses interval lengths tending to zero.
- **ID 52 — BOJ 10803:** the source's threshold has shifted-variable, boundary, inequality, and replacement-tile gaps. The migrated article does not claim that threshold; it proves $m-n\ge n^2/3 \Rightarrow f(n,m)=f(n,m-n)+1$ for positive integers $n\le m$ under guillotine cuts. A standard guillotine DP spot-check is corroborating evidence only, not the proof.
- **IDs 35/38 — Analysis foundations:** the legacy definitions use union where intersection is required and contain malformed group/function statements. Keep the corrected canonical article.

## Code audit and verification

- **ID 39:** legacy code uses a GNU variable-length array. The migrated C++17 article defines `best[b]` as maximum memory with total cost *at most* `b`, and preserves descending 0/1 updates, including zero-cost items.
- **ID 48:** checking degree parity alone can accept disconnected even-degree components; recursive Hierholzer traversal can exhaust the call stack. The migrated code uses iterative traversal and verifies every edge was consumed.
- **ID 50:** both Tistory snippets fail syntax checking (`complex` lacks a template argument; `cpx` is undefined). The migrated article specifies one DFT sign convention and provides forward/inverse FFT, convolution, floating-point precision limits, and the `long long` result bound.

All three migrated C++17 programs compiled with `-std=c++17 -O2 -Wall -Wextra -Wpedantic -Werror -pedantic-errors`. Verified: BOJ 7579 against 200 deterministic brute-force cases and cost-boundary cases; BOJ 1199 for even/odd degrees, disconnected components, isolated vertices, loops, parallel edges, a zero-edge graph, and cycles; FFT round trips for powers of two through 1024 and 625 deterministic convolution comparisons. The production Jekyll 4.2.1 build and `ruby tools/verify_language_routes.rb _site` passed; all six affected English/Korean/Japanese pages rendered with their expected language and article sections. The sole reported archive collision is the existing `/tags/prefix-sum/` collision between `_posts/2021-11-17-[BOJ] - 11659.md` and `_posts/2021-12-28-[BOJ] - 2042.md`.

## Rendering correction — Nested Interval Property

The live English, Korean, and Japanese theorem statements split the interval-length formula into table cells: raw `|...|` delimiters inside numbered-list math were parsed as Markdown table separators. Replaced these absolute-value delimiters with equivalent MathJax `\lvert...\rvert` notation throughout all three variants. Generated markup retains the formulas in the ordered-list items. The three live language variants and the post-deployment `ads.txt` invariant were visually/operationally checked after PR #8 and recorded in the linked Notion tracker.

## Preservation and follow-up

- Original publish dates and author attribution are retained in all four Tistory-only articles. Tistory source links and CC BY 4.0 provenance are recorded where the source displayed the license; IDs 14, 15, and 17 showed no license footer and were not copied.
- No Tistory page was edited or deleted. The GitHub repository cannot redirect the separate Tistory domain; original Tistory URLs remain intact and migrated articles cite their source URLs.
- No direct `gh-pages` edit. Publication uses the repository's existing GitHub Actions workflow.
- Production invariant: root `ads.txt`, generated `gh-pages/ads.txt`, and live `/ads.txt` must remain exactly `google.com, pub-6869608997080714, DIRECT, f08c47fec0942fa0`. The current production source, deployed file, and live response matched before this migration deployment; verify again afterward.
- No unintegrated Tistory-only article or unreviewed unique extension remains. ID 52's original stronger threshold is explicitly rejected; only the corrected conservative sufficient condition is migrated.
- Notion tracker: [Personal Brand & Fame-to-Revenue Strategy — 2026-10-02](https://app.notion.com/p/Personal-Brand-Fame-to-Revenue-Strategy-2026-10-02-3ed7b981c51481cb8e93e31d72415748). Publication run, PR, commit SHAs, and live route checks will be added after CI and Pages deployment.
