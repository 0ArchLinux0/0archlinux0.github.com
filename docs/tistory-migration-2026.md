# Tistory migration review — 2026-10-02

Source: [ilikechicken.tistory.com](https://ilikechicken.tistory.com/) · Canonical site: [0archlinux0.github.io](https://0archlinux0.github.io/) · Repository: [0ArchLinux0/0archlinux0.github.com](https://github.com/0ArchLinux0/0archlinux0.github.com) · Branch: `tistory-migration-2026`

The complete machine-readable source capture, canonical inventory, route map, classification, ranking, audit notes, and correction log are in [`tistory-migration-2026.json`](tistory-migration-2026.json). Twenty numeric Tistory article pages were found through the sitemap and archive pages; all twenty article bodies were captured. The canonical inventory baseline is `origin/main` at `e1f3e140c6818e04ecfb587795a31f6abdfc696a`; generated-site baseline is `origin/gh-pages` at `da83489ccf8645637b53d8b4128828d484e4bb71`.

## Disposition

| Classification | Count | Action |
|---|---:|---|
| Exact duplicate | 0 | — |
| GitHub version is better | 15 | Keep existing canonical article; do not copy legacy text |
| Partial overlap | 1 | Keep the integer-only Bézout article; defer the unproved polynomial extension |
| Tistory-only | 3 | Migrated as new canonical articles |
| Conflict requiring review | 1 | Keep BOJ 10803 proof unpublished pending independent proof review |
| Tistory version better | 0 | — |
| Low-value archive | 0 | The inventory covers article pages, not archive pages |

The five-proof first review batch covered Tistory IDs **17, 25, 45, 46, and 47**. Their existing canonical English proofs are retained; none was duplicated or republished. ID 52 was separately audited and blocked. The code/algorithm batch migrated IDs **39, 48, and 50**. This yields three English articles plus three reviewed Korean and three Japanese translations.

## Per-post classification

| Tistory ID | Source topic | Disposition | Canonical route / decision |
|---:|---|---|---|
| 14 | BOJ 3653 — Movie Collection | GitHub better | `/posts/boj3653/` |
| 15 | BOJ 1395 — Switches | GitHub better | `/posts/boj1395/` |
| 17 | Euclidean Algorithm | GitHub better | `/posts/Euclidean-Algorithm/` |
| 23 | Floyd–Warshall Algorithm | GitHub better | `/posts/Floyd-Warshall-Algorithm/` |
| 24 | Max-flow min-cut theorem | GitHub better | `/posts/Max-flow-min-cut-theorem/` |
| 25 | Bézout's identity, Part 1 | Partial overlap | `/posts/Bézout's-identity-p1/`; retain integer proof, defer polynomial claim |
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
| 52 | BOJ 10803 — Make a Square proof | Conflict; blocked | No canonical article created; proof review required |

## Ranked review candidates

| Rank | Tistory ID | Priority | Status | Decision |
|---:|---:|---|---|---|
| 1 | 48 | P0 | Migrated | Unique Euler-circuit article; corrected missing disconnected-component check and recursion-depth risk. |
| 2 | 50 | P0 | Migrated | Durable FFT tutorial; replaced two incomplete, non-compiling snippets with a tested C++17 implementation. |
| 3 | 39 | P1 | Migrated | Unique cost-indexed knapsack; clarified at-most-budget DP and removed the GNU VLA. |
| 4 | 52 | P1 | Blocked | Recurrence proof has boundary, variable-shift, inequality, and replacement-tile gaps. Do not publish until an independent proof establishes the full claim. |
| 5 | 25 | P2 | Partial-overlap review | The 2025 Tistory revision mentions a polynomial identity but proves only the integer case. Its linked Part 3 page returned HTTP 403 during review. Do not import the extension without an accessible source and proof. |

## Mathematical proof audit and corrections

- **ID 17 — Euclidean algorithm:** the legacy code declaration `int gcd(int a, b)` is invalid C++; the canonical article states the nonnegative input domain, handles `gcd(a, 0)`, and gives the direct common-divisor proof.
- **ID 25 — Bézout:** the legacy text defines `S` using positive combinations, then claims it equals all integer multiples of `g`, which includes zero and negative values. Its polynomial statement is not proved on that page. The current canonical article correctly limits its proof to integers.
- **ID 45 — Bolzano–Weierstrass:** the legacy page conflates the bounded-sequence theorem with compactness of closed bounded sets, assumes a bounded sequence has an infinite range, and uses malformed interval-bisection notation. The canonical proof handles finite-dimensional sequences coordinatewise and treats compactness separately.
- **ID 46 — Monotone convergence:** the legacy tail estimate says `n ≤ N`; the convergence argument requires `n ≥ N`. The canonical proof uses the correct supremum tail bound.
- **ID 47 — Nested intervals:** the legacy uniqueness inequality is invalid. The canonical proof directly obtains `|x-y| ≤ |I_n|` for every `n`, then uses interval lengths tending to zero.
- **ID 52 — BOJ 10803:** the claimed recurrence has an unresolved positive-side boundary, mismatched shifted variables/threshold, malformed reciprocal-sum inequality, and an unproved replacement-tile bound. No recurrence or proof was migrated.
- **IDs 35/38 — Analysis foundations:** the legacy definitions use union where intersection is required and contain malformed group/function statements. Keep the corrected canonical article.

## Code audit and verification

- **ID 39:** legacy code uses a GNU variable-length array. The migrated C++17 article defines `best[b]` as maximum memory with total cost *at most* `b`, and preserves descending 0/1 updates, including zero-cost items.
- **ID 48:** checking degree parity alone can accept disconnected even-degree components; recursive Hierholzer traversal can exhaust the call stack. The migrated code uses iterative traversal and verifies every edge was consumed.
- **ID 50:** both Tistory snippets fail syntax checking (`complex` lacks a template argument; `cpx` is undefined). The migrated article specifies one DFT sign convention and provides forward/inverse FFT, convolution, floating-point precision limits, and the `long long` result bound.

All three migrated C++17 programs compiled with `-std=c++17 -O2 -Wall -Wextra -Wpedantic -Werror -pedantic-errors`. Verified: BOJ 7579 against 200 deterministic brute-force cases and cost-boundary cases; BOJ 1199 for even/odd degrees, disconnected components, isolated vertices, loops, parallel edges, a zero-edge graph, and cycles; FFT round trips for powers of two through 1024 and 625 deterministic convolution comparisons. The local Jekyll 4.2.1 build completed, and `ruby tools/verify_language_routes.rb _site` passed. The build reports one pre-existing collision for `/tags/prefix-sum/` from canonical posts `_posts/2021-11-17-[BOJ] - 11659.md` and `_posts/2021-12-28-[BOJ] - 2042.md`; no Tistory migration post introduces it.

## Preservation and follow-up

- Original publish dates and author attribution are retained in all three migrated articles. Tistory source links and the source license are recorded where the source page displayed CC BY 4.0; IDs 14, 15, and 17 showed no license footer and were not copied.
- No Tistory page was edited or deleted. The GitHub repository cannot redirect the separate Tistory domain; original Tistory URLs remain intact and migrated articles cite their source URLs.
- No direct `gh-pages` edit. Publication uses the repository's existing GitHub Actions workflow.
- Open review items: establish or reject the full BOJ 10803 recurrence proof; locate and verify the polynomial Bézout extension before considering it for the canonical site.
- Notion tracker: [Personal Brand & Fame-to-Revenue Strategy — 2026-10-02](https://app.notion.com/p/Personal-Brand-Fame-to-Revenue-Strategy-2026-10-02-3ed7b981c51481cb8e93e31d72415748). Publication run, PR, commit SHAs, and live route checks will be added after CI and Pages deployment.
