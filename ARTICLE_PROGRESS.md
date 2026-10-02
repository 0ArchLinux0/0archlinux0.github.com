# Blog maintenance and article progress

Inventory: **298 Markdown source articles** under `_posts` on the migration branch (295 on the canonical baseline plus three Tistory-only migrations). The duplicate Korean Analysis and LeetCode 4 posts were merged into their canonical entries. The old LeetCode 4 URL redirects to its canonical article. The misplaced deployment script was removed; `tools/deploy.sh` is the canonical copy.

## Work plan

- [x] English is the default site and article language.
- [x] Korean and Japanese translations are separate, explicitly linked pages; no runtime machine translation.
- [x] Remove duplicate copies and generated build artifacts only after verifying the canonical copy exists.
- [x] Replace stale theme documentation and repair build/deploy validation.
- [x] Move unrelated root contest/project scratch files into `_legacy/root-scratch/` and exclude them from the generated site.
- [ ] Revise each article in English, checking technical claims and preserving existing URLs where possible.
- [ ] Add Korean/Japanese translations only as separately reviewed translations.

Article revision: **1/298 verified; 235 awaiting remote render checks; 62 not started**. Korean and Japanese: **1/298 verified each; 235 awaiting remote render checks each; 62 not started each**.

## Verified cycle 1

- `_posts/2022-04-24- Monotone Convergence Theorem.md`: rewritten in English, with separately reviewed Korean and Japanese translations.
- Smoke checks passed for the English homepage listing, all three rendered language pages, reciprocal English/Korean/Japanese links, correct HTML `lang` values, and canonical URLs without `//`.
- Jekyll 4.2.1 built locally on Ruby 3.3 with a temporary compatibility shim for older dependencies. The GitHub Actions `Validate blog` workflow had been manually disabled; it was re-enabled for pushes, pull requests, and manual runs on GitHub-hosted runners.
- The initial build reported tag archive collisions; normalized the three colliding tag spellings. The rebuild completed without archive warnings.
- Visual browser inspection remains unverified because Chromium could not start or attach in this environment; generated HTML assertions covered language labels and links.
- PR #1 is merged into `main`. GitHub Actions run [#36810550181](https://github.com/0ArchLinux0/0archlinux0.github.com/actions/runs/36810550181) completed successfully, including the production build and publish to `gh-pages`.
- PR #2 added a manual validation trigger and documents the GitHub-hosted build/deploy workflow. Validation run [#36838793819](https://github.com/0ArchLinux0/0archlinux0.github.com/actions/runs/36838793819) and deployment run [#36838793828](https://github.com/0ArchLinux0/0archlinux0.github.com/actions/runs/36838793828) passed.

## Current review batch (awaiting remote render validation)

- Rewrote the Analysis foundations, Analysis II, Bolzano–Weierstrass, nested-interval, Euclidean algorithm, Bézout identity, De Moivre's formula, flow network, Floyd–Warshall, max-flow/min-cut, Programmers Network, AtCoder ARC 135 A: Floor, Ceil Decomposition, B: Sum of Three Terms, and C: XOR to All, ABC 235 A: Rotate, B: Climbing Takahashi, C: The Kth Time Query, D: Multiply and Rotate, ABC 237 A: Not Overflow, B: Matrix Transposition, C: Kasaka, D: LR Insertion, and E: Skiing, ABC 238 A: Exponential or Quadratic, B: Pizza, and C: Digitnum, BOJ 1005, 1006, 1009, 10217, 10266, 1035, 1069, 1086, 1094, 10999, 11003, 11049, 11066, 11279, 11280, 11281, 11375, 11376, 11378, 11404, 11438, 11505, 1167, 1168, 11657, 11659, 11723, 11725, 11779, 11780, 1197, 1240, 1275, 12852, 12899, 1305, 1311, 13275, 1339, 13460, 13511, 13913, 1395, 14002, 14003, 14425, 1450, 14500, 14725, 1504, 1509, 1517, 1520, 15681, 15683, 15686, 15927, 16234, 16235, 16236, 1644, 1697, 16975, 1707, 1717, 17131, 17144, 17386, 17387, 17404, 17435, 17472, 1753, 1766, 1786, 1806, 1949, 1956, 1967, 1976, 1991, 20040, 2042, 2098, 2150, 2162, 2169, 2170, 2206, 2213, 2252, 2263, 2357, 2470, 2482, 2494, 2533, 2618, 2636, 2836, 2887, 3176, 3190, 3273, 3584, 3648, 3653, 3665, 3977, 4013, 4195, 4196, 4354, 4803, 5052, 5373, 5419, 5639, 5670, 7469, 7562, 7569, 7869, 9019, 9252, 9345, and 9370, Codeforces Global Round 19 A: Sorting Parts, B: MEX and Array, and C: Andrew and Stones, Codeforces Round 771 A: Reverse, BOJ 11437, 14499, 14503, 14890, 1725, and 2268, Codility ArrayInversionCount, Programmers Largest Number, LeetCode 1, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 53, 83, 94, 131, 312, 461, 540, and 997, the SCC overview, AtCoder Typical 90 001, 002, 003, 004, 005, 006, 007, 008, 009, 010, 011, 012, 013, 014, 015, 016, 018, and 021, and BOJ 1655; added separately reviewed Korean and Japanese pages for each.
- Corrected the Analysis II canonical URL to its original `/posts/Analysis-해석학(2)/` path after the English title changed.
- The reported 404 came from a generated fullwidth `１` slug while the requested route uses ASCII `1`; the canonical page now uses the requested route and keeps redirects from both legacy slugs.
- GitHub Actions will verify the generated language-mode pages, reciprocal article links, canonical routes, and legacy redirects before these 231 articles are marked verified.


| Source file | English | Korean | Japanese | Notes |
|---|---|---|---|---|
| `_posts/2021-08-21-[LeetCode] - 11. Container With Most Water.md` | Awaiting CI | Awaiting CI | Awaiting CI | Shorter-side proof and O(N) Java solution; brute-force comparison passed |
| `_posts/2021-08-21-[LeetCode] - 12.Integer to Roman.md` | Awaiting CI | Awaiting CI | Awaiting CI | Greedy subtractive-denomination solution; all 3,999 valid inputs checked |
| `_posts/2021-08-21-[LeetCode] - 15. 3Sum.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two-pointer solution with duplicate suppression; brute-force comparison passed |
| `_posts/2021-08-21-[LeetCode] - 16. 3Sum Closest.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced unrelated radix-sort code with fixed-index/two-pointer solution |
| `_posts/2021-08-21-[LeetCode] - 3. Longest Substring Without Repeating Characters.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sliding-window invariant; exhaustive and seeded brute-force comparisons passed |
| `_posts/2021-08-21-[LeetCode] - 4 Median of Two Sorted Arrays.md` | Awaiting CI | Awaiting CI | Awaiting CI | Binary-search partition replaces full merge; the duplicate route redirects here |
| `_posts/2021-08-21-[LeetCode] - 5. Longest Palindromic Substring.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced opaque loop with odd/even center expansion; brute-force comparisons passed |
| `_posts/2021-08-21-[LeetCode] - 6. ZigZag Conversion.md` | Awaiting CI | Awaiting CI | Awaiting CI | Added `numRows >= s.length()` boundary; independent grid simulation passed |
| `_posts/2021-08-21-[LeetCode] - 7. Reverse Integer.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced `long` intermediate with signed pre-multiply overflow checks |
| `_posts/2021-08-21-[LeetCode] - 9. Palindrome Number.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced string allocation with overflow-safe half reversal |
| `_posts/2021-08-21-[Sort] - Radix Sort.md` | Pending | Not started | Not started | — |
| `_posts/2021-11-12-[Info] - Contact.md` | Pending | Not started | Not started | — |
| `_posts/2021-11-12-[LeetCode] - 14. Longest Common Prefix. Longest Common Prefix.md` | Awaiting CI | Awaiting CI | Awaiting CI | Vertical scan checks every string boundary |
| `_posts/2021-11-12-[競プロ典型 90 問] - 019 - Pick Two(6).md` | Pending | Not started | Not started | — |
| `_posts/2021-11-13-[LeetCode] - 83. Remove Duplicates from Sorted List.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted linked-list deduplication reuses first node per value |
| `_posts/2021-11-14-[LeetCode] - 13. Roman to Integer.md` | Awaiting CI | Awaiting CI | Awaiting CI | Left-to-right subtract-if-next-is-larger decoding |
| `_posts/2021-11-16-[LeetCode] - 53. Maximum Subarray.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced unrelated four-sum code with Kadane's algorithm |
| `_posts/2021-11-17-[BOJ] - 11659.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced segment tree with O(N+M) prefix-sum range queries |
| `_posts/2021-11-17-[LeetCode] - 461. Hamming Distance.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected mislabeled title; XOR plus bitCount |
| `_posts/2021-11-17-[LeetCode] - 540. Single Element in a Sorted Array.md` | Awaiting CI | Awaiting CI | Awaiting CI | Pair-aligned binary search uses the singleton parity invariant |
| `_posts/2021-11-17-[LeetCode] - 94. Binary Tree Inorder Traversal.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced mutating JS variant with iterative Java traversal |
| `_posts/2021-11-19-[LeetCode] - 1. Two Sum.md` | Awaiting CI | Awaiting CI | Awaiting CI | One-pass complement map with overflow-safe complement calculation |
| `_posts/2021-11-24-[Colleague Math]-De Moivre's Threom.md` | Awaiting CI | Awaiting CI | Awaiting CI | Added statement/proof/example; numerical identity checks passed |
| `_posts/2021-11-26-[Graph theory]-Strongly Connected Component.md` | Awaiting CI | Awaiting CI | Awaiting CI | Correct Tarjan low-link/on-stack invariant and SCC extraction |
| `_posts/2021-11-26-[LeetCode] - 19. Remove Nth Node From End of List.md` | Awaiting CI | Awaiting CI | Awaiting CI | Dummy head and two pointers unlink the target in one pass |
| `_posts/2021-11-28-[競プロ典型 90 問] - 21 - Come Back in One Piece（★5）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced recursive Tarjan with iterative Kosaraju and 64-bit pair count |
| `_posts/2021-12-04-[LeetCode] - 17. Letter Combinations of a Phone Number.md` | Awaiting CI | Awaiting CI | Awaiting CI | Backtracking reuses one StringBuilder prefix |
| `_posts/2021-12-04-[LeetCode] - 20. Valid Parentheses.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced bracket arithmetic with explicit ArrayDeque matching |
| `_posts/2021-12-09-[LeetCode] - 10. Regular Expression Matching.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bottom-up DP matches LeetCode `.` and `*` semantics |
| `_posts/2021-12-09-[LeetCode] - 21. Merge Two Sorted Lists.md` | Awaiting CI | Awaiting CI | Awaiting CI | Dummy-head merge reuses sorted input nodes |
| `_posts/2021-12-10-[LeetCode] - 23. Merge k Sorted Lists.md` | Awaiting CI | Awaiting CI | Awaiting CI | Min-heap merges nodes in O(N log K) |
| `_posts/2021-12-10-[LeetCode] - 24. Swap Nodes in Pairs.md` | Awaiting CI | Awaiting CI | Awaiting CI | Dummy-head pointer relinking preserves all original nodes |
| `_posts/2021-12-11-[LeetCode] - 25. Reverse Nodes in k-Group.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reverses only full groups with constant auxiliary space |
| `_posts/2021-12-13-[BOJ] - 6549.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-13-[LeetCode] - 26. Remove Duplicates from Sorted Array.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted two-pointer compaction handles empty input |
| `_posts/2021-12-13-[LeetCode] - 27. Remove Element.md` | Awaiting CI | Awaiting CI | Awaiting CI | Stable in-place filtering; suffix is explicitly unspecified |
| `_posts/2021-12-13-[LeetCode] - 28. Implement strStr().md` | Awaiting CI | Awaiting CI | Awaiting CI | KMP handles overlapping prefixes in linear time |
| `_posts/2021-12-14-[BOJ] - 2261.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-14-[Programmers] - Disk Controller.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-19-[Programmers] - Stock Price.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-21-[LeetCode] - 29. Divide Two Integers.md` | Awaiting CI | Awaiting CI | Awaiting CI | Greedy powers-of-two subtraction handles signed boundaries |
| `_posts/2021-12-21-[LeetCode] - 30. Substring with Concatenation of All Words.md` | Awaiting CI | Awaiting CI | Awaiting CI | Offset sliding windows account for repeated words and overlaps |
| `_posts/2021-12-22-[LeetCode] - 31. Next Permutation.md` | Awaiting CI | Awaiting CI | Awaiting CI | Pivot/successor/suffix reversal is in place |
| `_posts/2021-12-22-[LeetCode] 35. Search Insert Position.md` | Awaiting CI | Awaiting CI | Awaiting CI | Lower-bound binary search returns insertion index |
| `_posts/2021-12-23-[BOJ] - 11279.md` | Awaiting CI | Awaiting CI | Awaiting CI | Primitive-array max-heap with logarithmic sift operations |
| `_posts/2021-12-23-[Codility] ArrayInversionCount.md` | Awaiting CI | Awaiting CI | Awaiting CI | Merge-sort inversion counting, strict pairs and 1e9 cap |
| `_posts/2021-12-24-[BOJ] - 1655.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two heaps keep the lower median at the lower max-heap root |
| `_posts/2021-12-24-[Leetcode] 32. Longest Valid Parentheses.md` | Awaiting CI | Awaiting CI | Awaiting CI | Fixed pin metadata and typed index stack |
| `_posts/2021-12-24-[Leetcode] 33. Search in Rotated Sorted Array.md` | Awaiting CI | Awaiting CI | Awaiting CI | Single binary search selects the sorted half |
| `_posts/2021-12-24-[Leetcode] 34. Find First and Last Position of Element in Sorted Array.md` | Awaiting CI | Awaiting CI | Awaiting CI | Independent lower/upper bounds return the full range |
| `_posts/2021-12-24-[Leetcode] 36. Valid Sudoku.md` | Awaiting CI | Awaiting CI | Awaiting CI | Constant-size row/column/box bitmasks |
| `_posts/2021-12-25-[Leetcode] 37. Sudoku Solver.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bitmask-backed backtracking uses the fewest-candidate cell first |
| `_posts/2021-12-26-[BOJ] - 7569.md` | Awaiting CI | Awaiting CI | Awaiting CI | Multi-source BFS uses a primitive flattened-index queue |
| `_posts/2021-12-26-[Leetcode] 38. Count and Say.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative maximal-run encoding |
| `_posts/2021-12-27-[Programmers] - Bigest Number.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected title/tags; concatenation comparator and all-zero case |
| `_posts/2021-12-27-[Programmers] - Find number of animals with same name.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-28-[BOJ] - 1697.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bounded-state BFS finds minimum move count |
| `_posts/2021-12-28-[BOJ] - 2042.md` | Awaiting CI | Awaiting CI | Awaiting CI | Fenwick tree supports point updates and inclusive range sums |
| `_posts/2021-12-28-[BOJ] - 2206.md` | Awaiting CI | Awaiting CI | Awaiting CI | BFS tracks visited state with and without a wall break |
| `_posts/2021-12-29-[BOJ] - 1717.md` | Awaiting CI | Awaiting CI | Awaiting CI | Path halving and union by size maintain DSU representatives |
| `_posts/2021-12-29-[Leetcode] 39. Combination Sum.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reusable candidates with sorted backtracking |
| `_posts/2021-12-30-[BOJ] - 1707.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative two-color BFS checks every graph component |
| `_posts/2021-12-30-[BOJ] - 7562.md` | Awaiting CI | Awaiting CI | Awaiting CI | Primitive-queue BFS checks exactly eight knight moves |
| `_posts/2021-12-30-[Leetcode] 40. Combination Sum II.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted DFS skips same-depth duplicates and consumes candidates once |
| `_posts/2021-12-30-[競プロ典型 90 問] - 001 - Yokan Party（4）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Greedy feasibility plus binary search on minimum piece length |
| `_posts/2021-12-30-[競プロ典型 90 問] - 002 - Encyclopedia of Parentheses（3）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Backtracking enforces prefix balance and lexicographic order |
| `_posts/2021-12-30-[競プロ典型 90 問] - 003 - Longest Circular Road（4）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two iterative tree traversals find diameter in edges; answer adds one vertex |
| `_posts/2021-12-30-[競プロ典型 90 問] 004 - Cross Sum（2）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Long row/column sums subtract the center once |
| `_posts/2021-12-30-[競プロ典型 90 問] 005 - Restricted Digits.md` | Awaiting CI | Awaiting CI | Awaiting CI | Remainder transition matrix exponentiation |
| `_posts/2021-12-30-[競プロ典型 90 問] 006 - Smallest Subsequence.md` | Awaiting CI | Awaiting CI | Awaiting CI | Monotonic stack preserves the smallest feasible subsequence |
| `_posts/2021-12-30-[競プロ典型 90 問] 007 - CP Classes(3).md` | Awaiting CI | Awaiting CI | Awaiting CI | Binary search finds the nearest class rating using long distances |
| `_posts/2021-12-30-[競プロ典型 90 問] 008 - AtCounter(4).md` | Awaiting CI | Awaiting CI | Awaiting CI | Descending 1D subsequence DP prevents character reuse |
| `_posts/2021-12-30-[競プロ典型 90 問] 009 - Three Point Angle.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sort angles around each pivot and inspect antipodal neighbors |
| `_posts/2021-12-30-[競プロ典型 90 問] 010 - Score Sum Queries.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two class prefix sums answer inclusive range totals |
| `_posts/2021-12-30-[競プロ典型 90 問] 011 - Gravy Jobs.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sort deadlines and keep feasible rewards in one-dimensional DP |
| `_posts/2021-12-30-[競프로典型 90 問] 012 - Red Painting(4).md` | Awaiting CI | Awaiting CI | Awaiting CI | Union painted neighboring cells with a disjoint-set structure |
| `_posts/2021-12-30-[競プロ典型 90 問] 013 - Passing(5).md` | Awaiting CI | Awaiting CI | Awaiting CI | Two Dijkstra runs sum shortest distances through each vertex |
| `_posts/2021-12-30-[競プロ典型 90 問] 014 - We Used to Sing a Song Together.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted equal-rank pairing minimizes absolute-difference sum |
| `_posts/2021-12-30-[競プロ典型 90 問] 015 - Don't be too close(6).md` | Awaiting CI | Awaiting CI | Awaiting CI | Gap-removal bijection reduces selections to a binomial coefficient |
| `_posts/2021-12-30-[競プロ典型 90 問] 016 - Minimum Coins.md` | Awaiting CI | Awaiting CI | Awaiting CI | Ascending unbounded DP computes minimum coin count |
| `_posts/2021-12-30-[競プロ典型 90 問] 018 - Statue of Chokudai(3).md` | Awaiting CI | Awaiting CI | Awaiting CI | Periodic 3D position yields elevation via atan2 |
| `_posts/2021-12-30-[競プロ典型 90 問] 020 - Log Inequality.md` | Pending | Not started | Not started | — |
| `_posts/2021-12-31-[BOJ] - 11505.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative segment tree handles zero updates and modular range products |
| `_posts/2021-12-31-[BOJ] - 1753.md` | Awaiting CI | Awaiting CI | Awaiting CI | Lazy-PQ Dijkstra uses long distances and stale-entry skipping |
| `_posts/2021-12-31-[Programmers] - Network.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative DFS counts undirected connected components |
| `_posts/2022-01-01-[BOJ] - 1504.md` | Awaiting CI | Awaiting CI | Awaiting CI | Three Dijkstra runs combine the two required visit orders |
| `_posts/2022-01-01-[Leetcode] 312. Burst Balloons.md` | Awaiting CI | Awaiting CI | Awaiting CI | Interval DP chooses each balloon as the last burst |
| `_posts/2022-01-02-[BOJ] - 9370.md` | Awaiting CI | Awaiting CI | Awaiting CI | Three shortest-path trees test whether a candidate's optimum uses the marked edge |
| `_posts/2022-01-04-[BOJ] - 11404.md` | Awaiting CI | Awaiting CI | Awaiting CI | Floyd–Warshall keeps minimum parallel edges and zero-length diagonals |
| `_posts/2022-01-04-[BOJ] - 11657.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bellman–Ford skips unreachable edges and detects reachable negative cycles |
| `_posts/2022-01-04-[Leetcode] 997. Find the Town Judge.md` | Awaiting CI | Awaiting CI | Awaiting CI | Trust indegree/outdegree characterize the unique judge |
| `_posts/2022-01-05-[BOJ] - 10217.md` | Awaiting CI | Awaiting CI | Awaiting CI | Budget-indexed shortest-time Dijkstra prunes dominated states |
| `_posts/2022-01-05-[BOJ] - 11066.md` | Awaiting CI | Awaiting CI | Awaiting CI | Knuth-optimized interval DP reduces file merging to quadratic time |
| `_posts/2022-01-05-[BOJ] - 1956.md` | Awaiting CI | Awaiting CI | Awaiting CI | Floyd–Warshall plus each original edge finds the shortest directed cycle |
| `_posts/2022-01-05-[BOJ] - 1976.md` | Awaiting CI | Awaiting CI | Awaiting CI | DSU confirms every itinerary city is in the same road component |
| `_posts/2022-01-05-[BOJ] - 4195.md` | Awaiting CI | Awaiting CI | Awaiting CI | Map-backed DSU returns component size after each new friendship |
| `_posts/2022-01-06-[BOJ] - 1167.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two iterative weighted traversals find a tree diameter |
| `_posts/2022-01-06-[BOJ] - 11725.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative BFS assigns each parent at discovery |
| `_posts/2022-01-06-[BOJ] - 20040.md` | Awaiting CI | Awaiting CI | Awaiting CI | DSU identifies the first edge that closes a cycle |
| `_posts/2022-01-06-[BOJ] - 2357.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative segment tree merges min/max with identity sentinels |
| `_posts/2022-01-06-[Leetcode] 131. Palindrome Partitioning.md` | Awaiting CI | Awaiting CI | Awaiting CI | Palindrome table plus backtracking enumerates all valid partitions |
| `_posts/2022-01-07-[BOJ] - 1967.md` | Awaiting CI | Awaiting CI | Awaiting CI | Farthest-endpoint theorem finds diameter of the weighted tree |
| `_posts/2022-01-07-[BOJ] - 1991.md` | Awaiting CI | Awaiting CI | Awaiting CI | Three recursive traversals emit prefix, infix, and postfix order |
| `_posts/2022-01-07-[BOJ] - 5639.md` | Awaiting CI | Awaiting CI | Awaiting CI | Ancestor stack builds preorder BST before iterative postorder traversal |
| `_posts/2022-01-08-[BOJ] - 1517.md` | Awaiting CI | Awaiting CI | Awaiting CI | Merge sort counts strict inversions without counting equal pairs |
| `_posts/2022-01-09-[BOJ] - 2263.md` | Awaiting CI | Awaiting CI | Awaiting CI | Explicit traversal ranges reconstruct preorder without recursion |
| `_posts/2022-01-10-[BOJ] - 4803.md` | Awaiting CI | Awaiting CI | Awaiting CI | Component traversal marks on enqueue and detects back edges |
| `_posts/2022-01-11-[BOJ] - 12852.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bottom-up shortest-operation DP stores predecessors |
| `_posts/2022-01-11-[BOJ] - 14002.md` | Awaiting CI | Awaiting CI | Awaiting CI | Lower-bound LIS tails retain predecessors for sequence reconstruction |
| `_posts/2022-01-12-[BOJ] - 14003.md` | Awaiting CI | Awaiting CI | Awaiting CI | Lower-bound LIS reconstruction links each tail to a real predecessor |
| `_posts/2022-01-13-[BOJ] - 9252.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bottom-up LCS table backtracks one optimal common subsequence |
| `_posts/2022-01-14-[BOJ] - 13913.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bounded BFS stores predecessor states for the shortest path |
| `_posts/2022-01-14-[BOJ] - 9019.md` | Awaiting CI | Awaiting CI | Awaiting CI | Ordered D/S/L/R BFS records one shortest command sequence |
| `_posts/2022-01-15-[Atcoder] - A - Rotate.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sums all three cyclic digit rotations in linear time |
| `_posts/2022-01-15-[Atcoder] - B - Climbing Takahashi.md` | Awaiting CI | Awaiting CI | Awaiting CI | Strictly increasing scan stops before the first non-increase |
| `_posts/2022-01-15-[Atcoder] - C - The Kth Time Query.md` | Awaiting CI | Awaiting CI | Awaiting CI | Ordered occurrence lists answer kth-position queries or -1 |
| `_posts/2022-01-15-[Atcoder] - D - Multiply and Rotate.md` | Awaiting CI | Awaiting CI | Awaiting CI | BFS finds the shortest path while forbidding rotations that lose a digit |
| `_posts/2022-01-15-[BOJ] - 11779.md` | Awaiting CI | Awaiting CI | Awaiting CI | Adjacency-list Dijkstra stores long distances and reconstructs parents |
| `_posts/2022-01-15-[BOJ] - 11780.md` | Awaiting CI | Awaiting CI | Awaiting CI | Floyd–Warshall next-hop matrix reconstructs ordered-pair routes |
| `_posts/2022-01-18-[BOJ] - 16975.md` | Awaiting CI | Awaiting CI | Awaiting CI | Fenwick difference tree handles inclusive range additions and point queries |
| `_posts/2022-01-18-[BOJ] - 2618.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two-car DP reconstructs the minimum-distance event assignment |
| `_posts/2022-01-19-[BOJ] - 12899.md` | Awaiting CI | Awaiting CI | Awaiting CI | Fenwick binary lifting selects the requested frequency rank |
| `_posts/2022-01-20-[BOJ] - 1168.md` | Awaiting CI | Awaiting CI | Awaiting CI | Fenwick tree maintains alive ranks for Josephus elimination |
| `_posts/2022-01-20-[BOJ] - 9345.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative min/max tree checks the permutation interval after swaps |
| `_posts/2022-01-22-[BOJ] - 1197.md` | Awaiting CI | Awaiting CI | Awaiting CI | Kruskal and DSU handle negative edges and long total weights |
| `_posts/2022-01-22-[BOJ] - 2887.md` | Awaiting CI | Awaiting CI | Awaiting CI | Adjacent-coordinate candidate edges preserve an MST for min-axis cost |
| `_posts/2022-01-23-[BOJ] - 17472.md` | Awaiting CI | Awaiting CI | Awaiting CI | Island flood fill, valid bridge generation, and Kruskal connectivity check |
| `_posts/2022-01-24-[BOJ] - 15681.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reverse iterative traversal accumulates subtree sizes without recursion |
| `_posts/2022-01-26-[BOJ] - 11049.md` | Awaiting CI | Awaiting CI | Awaiting CI | Matrix-chain interval DP uses an exact-safe sentinel above valid costs |
| `_posts/2022-01-26-[BOJ] - 11723.md` | Awaiting CI | Awaiting CI | Awaiting CI | A 20-bit mask implements add, remove, check, toggle, all, and empty |
| `_posts/2022-01-26-[BOJ] - 1311.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bottom-up bitmask DP caches zero-cost states and large totals |
| `_posts/2022-01-26-[BOJ] - 2150.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative Kosaraju sorts vertices and components for exact output |
| `_posts/2022-01-26-[BOJ] - 2252.md` | Awaiting CI | Awaiting CI | Awaiting CI | Kahn's algorithm emits each zero-indegree vertex once |
| `_posts/2022-01-27-[BOJ] - 1094.md` | Awaiting CI | Awaiting CI | Awaiting CI | Target binary popcount equals the number of required stick pieces |
| `_posts/2022-01-27-[BOJ] - 1450.md` | Awaiting CI | Awaiting CI | Awaiting CI | Meet-in-the-middle subset sums count capacities with upper bounds |
| `_posts/2022-01-27-[BOJ] - 1644.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sieve and sliding window count consecutive-prime sums |
| `_posts/2022-01-27-[BOJ] - 1806.md` | Awaiting CI | Awaiting CI | Awaiting CI | Exclusive-right sliding window measures each qualifying interval before shrinking |
| `_posts/2022-01-27-[BOJ] - 2470.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted long-safe two pointers minimize the absolute pair sum |
| `_posts/2022-01-27-[BOJ] - 3273.md` | Awaiting CI | Awaiting CI | Awaiting CI | Sorted two-pointer scan counts each distinct-value pair once |
| `_posts/2022-01-29-[BOJ] - 14425.md` | Awaiting CI | Awaiting CI | Awaiting CI | HashSet stores exact strings and counts each matching query |
| `_posts/2022-01-29-[BOJ] - 14725.md` | Awaiting CI | Awaiting CI | Awaiting CI | Trie merges shared prefixes and prints sorted children by depth |
| `_posts/2022-01-29-[BOJ] - 1786.md` | Awaiting CI | Awaiting CI | Awaiting CI | KMP reports overlapping matches with one-based positions |
| `_posts/2022-01-29-[BOJ] - 4354.md` | Awaiting CI | Awaiting CI | Awaiting CI | Prefix function yields the shortest repeated-string period |
| `_posts/2022-01-30-[Atcoder] A - Not Overflow.md` | Awaiting CI | Awaiting CI | Awaiting CI | Compare long input against inclusive signed 32-bit bounds |
| `_posts/2022-01-30-[Atcoder] B - Matrix Transposition.md` | Awaiting CI | Awaiting CI | Awaiting CI | Transpose rectangular dimensions without reversing row/column order |
| `_posts/2022-01-30-[Atcoder] C - kasaka.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two-pointer comparison strips matching edge `a` characters |
| `_posts/2022-01-30-[Atcoder] D - LR insertion.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reverse-order deque insertion constructs the sequence in linear time |
| `_posts/2022-01-31-[Atcoder] E - Skiing.md` | Awaiting CI | Awaiting CI | Awaiting CI | Directed climb-cost Dijkstra maximizes remaining descent-based happiness |
| `_posts/2022-01-31-[BOJ] - 10266.md` | Awaiting CI | Awaiting CI | Awaiting CI | Doubled circular-position pattern plus KMP handles rotations in linear time |
| `_posts/2022-02-01-[BOJ] - 2533.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative reverse-order tree DP handles a singleton and million-node chain |
| `_posts/2022-02-02-[BOJ] - 1949.md` | Awaiting CI | Awaiting CI | Awaiting CI | Weighted independent-set DP reads populations and handles paths and stars |
| `_posts/2022-02-02-[BOJ] - 2213.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative weighted independent-set reconstruction returns a valid optimum |
| `_posts/2022-02-03-[BOJ] - 1005.md` | Awaiting CI | Awaiting CI | Awaiting CI | Topological DP maximizes prerequisite completion time |
| `_posts/2022-02-03-[BOJ] - 1305.md` | Awaiting CI | Awaiting CI | Awaiting CI | Prefix-function border gives minimum advertisement length |
| `_posts/2022-02-03-[BOJ] - 5670.md` | Awaiting CI | Awaiting CI | Awaiting CI | Trie keystroke count handles branches and words that prefix others |
| `_posts/2022-02-04-[BOJ] - 2482.md` | Awaiting CI | Awaiting CI | Awaiting CI | Pascal binomial recurrence counts both cases on the circular boundary |
| `_posts/2022-02-05-[Atcoder] A - Exponential or Quadratic.md` | Awaiting CI | Awaiting CI | Awaiting CI | Exact threshold avoids overflow from exponential comparison |
| `_posts/2022-02-05-[Atcoder] B - Pizza.md` | Awaiting CI | Awaiting CI | Awaiting CI | Cumulative cuts and circular wraparound determine the largest slice |
| `_posts/2022-02-05-[Atcoder] C - digitnum.md` | Awaiting CI | Awaiting CI | Awaiting CI | Digit-length ranges are summed with modular arithmetic and safe bounds |
| `_posts/2022-02-06-[BOJ] - 1009.md` | Awaiting CI | Awaiting CI | Awaiting CI | Binary exponentiation modulo 10 maps residue zero to computer 10 |
| `_posts/2022-02-06-[BOJ] - 1766.md` | Awaiting CI | Awaiting CI | Awaiting CI | Min-heap Kahn emits the smallest currently available problem |
| `_posts/2022-02-06-[BOJ] - 3665.md` | Awaiting CI | Awaiting CI | Awaiting CI | Rebuilt the complete ranking graph and classifies unique, ambiguous, or cyclic outcomes |
| `_posts/2022-02-07-[BOJ] - 17404.md` | Awaiting CI | Awaiting CI | Awaiting CI | Three fixed-first-color passes enforce circular house constraints |
| `_posts/2022-02-07-[BOJ] - 2098.md` | Awaiting CI | Awaiting CI | Awaiting CI | Bitmask TSP DP checks directed return edges and impossible tours |
| `_posts/2022-02-07-[Codeforces] Round #770 (Div. 2) A. Reverse and Concatenate.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-07-[Codeforces] Round #770 (Div. 2) B. Fortune Telling.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-08-[BOJ] - 1086.md` | Awaiting CI | Awaiting CI | Awaiting CI | Subset DP counts position-distinct permutations and reduces the exact fraction |
| `_posts/2022-02-08-[BOJ] - 17435.md` | Awaiting CI | Awaiting CI | Awaiting CI | Binary lifting answers repeated function composition from exponent bits |
| `_posts/2022-02-08-[BOJ] - 3584.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative parent climbing handles ancestor, self-query, and maximum-depth chain cases |
| `_posts/2022-02-09-[BOJ] - 11003.md` | Awaiting CI | Awaiting CI | Awaiting CI | Primitive monotone deque expires stale indices and retains current minima |
| `_posts/2022-02-09-[BOJ] - 11438.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative binary lifting answers LCA queries safely on a 100,000-node chain |
| `_posts/2022-02-09-[BOJ] - 3176.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative binary lifting queries path edge minima and maxima without recursion |
| `_posts/2022-02-10-[BOJ] - 1509.md` | Awaiting CI | Awaiting CI | Awaiting CI | O(N²) palindrome table plus iterative prefix DP avoids cubic checks and recursion |
| `_posts/2022-02-11-[BOJ] - 11280.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative Kosaraju checks literal/negation SCCs without recursion |
| `_posts/2022-02-11-[BOJ] - 13511.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative LCA and weighted jump tables handle path distances and kth vertices |
| `_posts/2022-02-11-[BOJ] - 3977.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative SCC condensation prints only a unique source component |
| `_posts/2022-02-11-[BOJ] - 4196.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative Kosaraju counts source SCCs as required initial pushes |
| `_posts/2022-02-12-[Codeforces] Global Round 19 A. Sorting Parts.md` | Awaiting CI | Awaiting CI | Awaiting CI | Adjacent inversion exactly characterizes a splittable unsorted array |
| `_posts/2022-02-12-[Codeforces] Global Round 19 C. Andrew and Stones.md` | Awaiting CI | Awaiting CI | Awaiting CI | Positive interior piles need at least one move each; long arithmetic covers the maximum sum |
| `_posts/2022-02-12-[Leetcode] 100. Same Tree.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-13-[BOJ] - 3648.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative 2-SAT enforces variable 1 and processes input cases until EOF |
| `_posts/2022-02-13-[Codeforces] Global Round 19 B. MEX and Array.md` | Awaiting CI | Awaiting CI | Awaiting CI | Partitions attain length plus zero-count; subsegment count gives the formula |
| `_posts/2022-02-14-[Atcoder] A - Floor, Ceil - Decomposition.md` | Awaiting CI | Awaiting CI | Awaiting CI | Memoized floor/ceil recurrence uses overflow-safe modular products |
| `_posts/2022-02-14-[Atcoder] B - Sum of Three Terms.md` | Awaiting CI | Awaiting CI | Awaiting CI | Residue-class prefix minima construct a feasible nonnegative sequence |
| `_posts/2022-02-14-[Atcoder] C - XOR to All.md` | Awaiting CI | Awaiting CI | Awaiting CI | Per-bit counts compute every XOR total using long-safe contributions |
| `_posts/2022-02-14-[BOJ] - 11281.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative 2-SAT includes component-ordered satisfying assignment |
| `_posts/2022-02-14-[Codeforces] Codeforces Round #771 (Div. 2) A. Reverse.md` | Awaiting CI | Awaiting CI | Awaiting CI | First misplaced value fixes the longest possible sorted prefix with one reversal |
| `_posts/2022-02-14-[Codeforces] Codeforces Round #771 (Div. 2) B. Odd Swap Sort.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-14-[Codeforces] Codeforces Round #771 (Div. 2) C. Inversion Graph.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-14-[Codeforces] Codeforces Round #771 (Div. 2) D. Big Brush.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-15-[BOJ] - 4013.md` | Awaiting CI | Awaiting CI | Awaiting CI | Primitive-array SCC condensation and topological cash DP exclude unreachable restaurants |
| `_posts/2022-02-16-[BOJ] - 2170.md` | Awaiting CI | Awaiting CI | Awaiting CI | Ascending interval union handles overlap, touching, disjoint, and reversed endpoints |
| `_posts/2022-02-17-[BOJ] - 2836.md` | Awaiting CI | Awaiting CI | Awaiting CI | Union of left-going intervals accounts for the doubled return detour |
| `_posts/2022-02-17-[BOJ] - 5419.md` | Awaiting CI | Awaiting CI | Awaiting CI | Compressed-y Fenwick suffix counts duplicate-safe northwest pairs in 64-bit total |
| `_posts/2022-02-18-[BOJ] - 10868.md` | Awaiting CI | Awaiting CI | Awaiting CI | Half-open iterative segment tree answers range minima in logarithmic time |
| `_posts/2022-02-18-[BOJ] - 1275.md` | Awaiting CI | Awaiting CI | Awaiting CI | Long-sum iterative tree queries normalized ranges before point updates |
| `_posts/2022-02-18-[BOJ] - 1725.md` | Awaiting CI | Awaiting CI | Awaiting CI | Monotone stack finds nearest smaller boundaries; long area handles maximum heights |
| `_posts/2022-02-18-[BOJ] - 2268.md` | Awaiting CI | Awaiting CI | Awaiting CI | Zero-based point updates and inclusive range sums use a half-open iterative tree |
| `_posts/2022-02-21-[BOJ] - 10999.md` | Awaiting CI | Awaiting CI | Awaiting CI | Recursive lazy propagation applies range additions using segment lengths |
| `_posts/2022-02-21-[BOJ] - 17131.md` | Awaiting CI | Awaiting CI | Awaiting CI | Equal-x grouped Fenwick sweeps count strict-height valleys without coordinate assumptions |
| `_posts/2022-02-21-[Leetcode] 43. Multiply Strings.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-22-[BOJ] - 13275.md` | Awaiting CI | Awaiting CI | Awaiting CI | Odd/even Manacher radii reuse mirrored palindromes in linear time |
| `_posts/2022-02-22-[BOJ] - 15686.md` | Awaiting CI | Awaiting CI | Awaiting CI | Enumerates exactly M stores and prunes combinations that cannot reach M |
| `_posts/2022-02-23-[BOJ] - 16236.md` | Awaiting CI | Awaiting CI | Awaiting CI | BFS chooses prey by distance/row/column; equal-size pass-through and growth are explicit |
| `_posts/2022-02-24-[BOJ] - 14499.md` | Awaiting CI | Awaiting CI | Awaiting CI | Four face permutations preserve orientation through every accepted roll and ignore boundary moves |
| `_posts/2022-02-24-[BOJ] - 14500.md` | Awaiting CI | Awaiting CI | Awaiting CI | Four T orientations are checked separately because a straight DFS misses them |
| `_posts/2022-02-24-[BOJ] - 14503.md` | Awaiting CI | Awaiting CI | Awaiting CI | Four left-turn checks clean new cells; backward retreat preserves heading |
| `_posts/2022-02-24-[BOJ] - 3190.md` | Awaiting CI | Awaiting CI | Awaiting CI | Deque/body simulation handles tail vacating, apples, and timed turns |
| `_posts/2022-02-25-[BOJ] - 13460.md` | Awaiting CI | Awaiting CI | Awaiting CI | BFS simulates tilts by front marble and excludes every blue-hole outcome |
| `_posts/2022-02-25-[BOJ] - 15683.md` | Awaiting CI | Awaiting CI | Awaiting CI | Enumerates camera orientations; rays pass other cameras and stop at walls |
| `_posts/2022-02-25-[BOJ] - 16234.md` | Awaiting CI | Awaiting CI | Awaiting CI | Per-day BFS unions use inclusive population thresholds and simultaneous floor averages |
| `_posts/2022-02-26-[BOJ] - 1339.md` | Awaiting CI | Awaiting CI | Awaiting CI | Greedy digit assignment follows descending positional weights; exchange proof provided |
| `_posts/2022-02-26-[BOJ] - 17144.md` | Awaiting CI | Awaiting CI | Awaiting CI | Simultaneous diffusion and separate purifier circulation preserve cell totals and remove intake dust |
| `_posts/2022-02-26-[BOJ] - 2169.md` | Awaiting CI | Awaiting CI | Awaiting CI | Two row sweeps combine above-entry and one-direction horizontal paths with negative-safe DP |
| `_posts/2022-02-26-[Memo] - To prove.md` | Pending | Not started | Not started | — |
| `_posts/2022-02-28-[BOJ] - 11437.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative parent/depth preprocessing and binary lifting handle deep trees without recursion |
| `_posts/2022-02-28-[BOJ] - 14890.md` | Awaiting CI | Awaiting CI | Awaiting CI | Explicit ramp occupancy check prevents overlapping slopes and handles L=1 |
| `_posts/2022-02-28-[BOJ] - 5052.md` | Awaiting CI | Awaiting CI | Awaiting CI | Lexicographic order makes any prefix conflict adjacent |
| `_posts/2022-03-01-[BOJ] - 1240.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative parent traversal sums the unique weighted path for each query |
| `_posts/2022-03-01-[BOJ] - 13325.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 14267.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 15685.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 1761.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 2250.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 2636.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reworked simultaneous exterior-air melting and last-hour cheese count |
| `_posts/2022-03-01-[BOJ] - 4256.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-01-[BOJ] - 7578.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-02-[BOJ] - 16437.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-02-[BOJ] - 6416.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-02-[BOJ] - 9202.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-02-[BOJ] - 9934.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 11000.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 15684.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 1744.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 2075.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 2243.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 2437.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 2517.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[BOJ] - 7453.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-03-[Leetcode] 1028. Recover a Tree From Preorder Traversal.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 1135.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 11559.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 11758.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 14921.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 16235.md` | Awaiting CI | Awaiting CI | Awaiting CI | Seasonal simulation processes surviving trees youngest-first, returns half-age food, and batches reproduction |
| `_posts/2022-03-04-[BOJ] - 16496.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 17135.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 17140.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 17143.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 2212.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 2473.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-04-[BOJ] - 2638.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-05-[BOJ] - 1092.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-05-[BOJ] - 16163.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-05-[BOJ] - 1854.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-06-[Atcoder] C - 1111gal password.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-06-[Atcoder] D - ABC Transform.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-07-[BOJ] - 13713.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-07-[BOJ] - 16229.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-08-[BOJ] - 17412.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-08-[BOJ] - 2188.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-09-[BOJ] - 1069.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected title to Going Home; proved walk/jump candidates and preserved `/posts/BOJ-1069/` |
| `_posts/2022-03-09-[BOJ] - 11375.md` | Awaiting CI | Awaiting CI | Awaiting CI | Rewrote as unit-capacity bipartite matching; brute-force comparison passed |
| `_posts/2022-03-09-[BOJ] - 11376.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced flawed assignment logic with two matching slots per worker |
| `_posts/2022-03-09-[BOJ] - 11378.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected bonus-node max-flow construction and per-worker/global extra limits |
| `_posts/2022-03-09-[BOJ] - 15927.md` | Awaiting CI | Awaiting CI | Awaiting CI | Proved O(N) palindrome characterization; exhaustive small-string comparison passed |
| `_posts/2022-03-09-[BOJ] - 17386.md` | Awaiting CI | Awaiting CI | Awaiting CI | Strict orientation test matches official no-three-collinear guarantee |
| `_posts/2022-03-09-[BOJ] - 17387.md` | Awaiting CI | Awaiting CI | Awaiting CI | Added closed-bounds handling for touch, overlap, gaps, and point segments |
| `_posts/2022-03-09-[BOJ] - 2166.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-09-[BOJ] - 6086.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-09-[BOJ] - 7869.md` | Awaiting CI | Awaiting CI | Awaiting CI | Derived circle-lens area; clamps cosine inputs and handles tangency/containment |
| `_posts/2022-03-10-[BOJ] - 20149.md` | Pending | Not started | Not started | — |
| `_posts/2022-03-10-[BOJ] - 2162.md` | Awaiting CI | Awaiting CI | Awaiting CI | Rebuilt exact intersection test and DSU; independent oracle passed |
| `_posts/2022-03-11-[BOJ] - 1520.md` | Awaiting CI | Awaiting CI | Awaiting CI | Iterative DAG DP uses exact `cpp_int` counts; local Boost header unavailable, algorithm smoke-tested with shim |
| `_posts/2022-03-11-[BOJ] - 7626.md` | Pending | Not started | Not started | Tag casing normalized; article review pending |
| `_posts/2022-03-11-[BOJ] -7469.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected problem identity and replaced O(NM) scan with persistent segment tree |
| `_posts/2022-03-12-[BOJ] -2494.md` | Awaiting CI | Awaiting CI | Awaiting CI | Reworked carry-state DP and signed-turn reconstruction; randomized operation simulation passed |
| `_posts/2022-03-13-[BOJ] -5373.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced fragile strip cases with exact sticker coordinates; 437 reference cases passed |
| `_posts/2022-04-12-Bézout's identity-p1.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected integer proof; Korean/Japanese translations added |
| `_posts/2022-04-12-Euclidean Algorithm.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected invariant proof and iterative C++ example; translations added |
| `_posts/2022-04-12-Flow network.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced flawed flow notation with edge-indexed feasible-flow and residual-network definitions |
| `_posts/2022-04-12-Floyd-Warshall Algorithm.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected recurrence, initialization, sentinel handling, and negative-cycle conditions; translations added |
| `_posts/2022-04-12-Max-flow min-cut theorem.md` | Awaiting CI | Awaiting CI | Awaiting CI | Corrected directed-cut and residual-graph proof; translations added |
| `_posts/2022-04-12-boj1006.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced failed quadratic attempt with four-case O(N) profile DP; brute-force cross-check passed |
| `_posts/2022-04-12-boj1035.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced destination/Manhattan search with legal-move BFS over configurations |
| `_posts/2022-04-12-boj1395.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced iterative toggle tree with a recursive lazy-XOR segment tree and inclusive-range solution |
| `_posts/2022-04-12-boj19565.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced confusing undirected construction with maximum directed Euler circuit |
| `_posts/2022-04-12-boj3653.md` | Awaiting CI | Awaiting CI | Awaiting CI | Replaced position tracking with a Fenwick tree and repeated-request-safe updates |
| `_posts/2022-04-13-Analysis - 解析学（１）.md` | Awaiting CI | Awaiting CI | Awaiting CI | Explicit ASCII-`1` permalink fixes reported 404; old fullwidth and Korean routes redirect to canonical page |
| `_posts/2022-04-16-Analysis - 해석학(2).md` | Awaiting CI | Awaiting CI | Awaiting CI | English rewrite and Korean/Japanese translations added |
| `_posts/2022-04-18-boj-7579-app.md` | Awaiting CI | Awaiting CI | Awaiting CI | Tistory ID 39; at-most-cost DP invariant; strict C++17 and 200 brute-force cases passed |
| `_posts/2022-04-24- Monotone Convergence Theorem.md` | Verified | Verified | Verified | Corrected proof; existing permalink preserved; rendered pages smoke-tested |
| `_posts/2022-04-24-Bolzano–Weierstrass theorem.md` | Awaiting CI | Awaiting CI | Awaiting CI | English rewrite and Korean/Japanese translations added |
| `_posts/2022-04-24-Nested Interval Property.md` | Awaiting CI | Awaiting CI | Awaiting CI | English rewrite and Korean/Japanese translations added |
| `_posts/2022-04-26-boj-1199-euler-circuit.md` | Awaiting CI | Awaiting CI | Awaiting CI | Tistory ID 48; iterative Hierholzer checks all edges; disconnected/loop/parallel-edge cases passed |
| `_posts/2022-05-15-fast-fourier-transform.md` | Awaiting CI | Awaiting CI | Awaiting CI | Tistory ID 50; tested forward/inverse FFT and convolution; 625 randomized comparisons passed |
| `_posts/IBM_Internship_temp.md` | Pending | Not started | Not started | — |
| `_posts/2026-10-02-GitHub Pages CI CD.md` | Awaiting CI | Awaiting CI | Awaiting CI | New guide from the session notes; CI triggers, manual actions, and deployment usage |
