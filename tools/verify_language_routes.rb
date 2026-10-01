# frozen_string_literal: true

require "pathname"
require "uri"

site_root = Pathname.new(ARGV.fetch(0, "_site"))

def read_page(site_root, path)
  file = site_root.join(path)
  abort "Missing generated page: #{file}" unless file.file?

  file.read
end

def assert(condition, message)
  abort message unless condition
end

def language_is?(html, language)
  html.match?(/<html\b[^>]*\blang="#{Regexp.escape(language)}"/)
end

def language_nav(html)
  match = html.match(/<nav class="site-language-switcher[^\"]*"[^>]*>(.*?)<\/nav>/m)
  abort "Missing global language selector" unless match

  match[1]
end

def decoded_hrefs(html)
  html.scan(/\bhref="([^"]+)"/).flatten.map do |href|
    URI::DEFAULT_PARSER.unescape(href)
  end
end

def assert_mode_home(site_root, path, language, translated_article)
  html = read_page(site_root, path)
  assert(language_is?(html, language), "#{path} must declare html lang=#{language}")
  nav = language_nav(html)
  assert(nav.include?("aria-current=\"page\""), "#{path} must mark its active language")
  assert(decoded_hrefs(html).include?(translated_article), "#{path} must list only its reviewed-language article")
  html
end

english_home = read_page(site_root, "index.html")
korean_home = assert_mode_home(site_root, "ko/index.html", "ko", "/ko/posts/analysis-foundations-1/")
japanese_home = assert_mode_home(site_root, "ja/index.html", "ja", "/ja/posts/analysis-foundations-1/")
assert(decoded_hrefs(korean_home).include?("/ko/"), "Korean home navigation must stay in Korean mode")
assert(decoded_hrefs(japanese_home).include?("/ja/"), "Japanese home navigation must stay in Japanese mode")

assert(language_is?(english_home, "en"), "The root home page must default to English")
english_nav = language_nav(english_home)
assert(decoded_hrefs(english_nav).include?("/ko/"), "English mode must link to the Korean index")
assert(decoded_hrefs(english_nav).include?("/ja/"), "English mode must link to the Japanese index")
assert(!decoded_hrefs(english_home).include?("/ko/posts/analysis-foundations-1/"), "Korean article leaked into the English home page")
assert(!decoded_hrefs(english_home).include?("/ja/posts/analysis-foundations-1/"), "Japanese article leaked into the English home page")
english_archive_pages = [
  site_root.join("index.html"),
  *Dir.glob(site_root.join("page*/index.html").to_s).map { |path| Pathname.new(path) }
]
archive_links = english_archive_pages.flat_map { |page| decoded_hrefs(page.read) }
assert(archive_links.any? { |href| href.end_with?("/posts/BOJ-2636/") }, "The English archive must retain unreviewed canonical posts")
assert(!decoded_hrefs(korean_home).include?("/ja/posts/analysis-foundations-1/"), "Japanese article leaked into the Korean home page")
assert(!decoded_hrefs(japanese_home).include?("/ko/posts/analysis-foundations-1/"), "Korean article leaked into the Japanese home page")

english_article_path = "posts/Analysis-解析学-1/index.html"
english_article = read_page(site_root, english_article_path)
assert(language_is?(english_article, "en"), "The corrected Analysis article must declare English")
english_canonical = english_article.match(/<link rel="canonical" href="([^"]+)"/)
assert(english_canonical, "The corrected Analysis article must have a canonical URL")
assert(URI::DEFAULT_PARSER.unescape(english_canonical[1]).end_with?("/posts/Analysis-解析学-1/"), "The corrected Analysis canonical path is wrong")

korean_article = read_page(site_root, "ko/posts/analysis-foundations-1/index.html")
japanese_article = read_page(site_root, "ja/posts/analysis-foundations-1/index.html")
assert(language_is?(korean_article, "ko"), "The Korean Analysis translation must declare Korean")
assert(language_is?(japanese_article, "ja"), "The Japanese Analysis translation must declare Japanese")

english_metric_article = read_page(site_root, "posts/Analysis-해석학(2)/index.html")
korean_metric_article = read_page(site_root, "ko/posts/analysis-metric-spaces-2/index.html")
japanese_metric_article = read_page(site_root, "ja/posts/analysis-metric-spaces-2/index.html")
assert(language_is?(english_metric_article, "en"), "Analysis II must preserve its English canonical route")
assert(language_is?(korean_metric_article, "ko"), "The Korean Analysis II translation must declare Korean")
assert(language_is?(japanese_metric_article, "ja"), "The Japanese Analysis II translation must declare Japanese")

metric_canonical = english_metric_article.match(/<link rel="canonical" href="([^"]+)"/)
assert(metric_canonical, "Analysis II must have a canonical URL")
assert(URI::DEFAULT_PARSER.unescape(metric_canonical[1]).end_with?("/posts/Analysis-해석학(2)/"), "Analysis II's existing canonical route changed")

{
  english_metric_article => ["/ko/posts/analysis-metric-spaces-2/", "/ja/posts/analysis-metric-spaces-2/"],
  korean_metric_article => ["/posts/Analysis-해석학(2)/", "/ja/posts/analysis-metric-spaces-2/"],
  japanese_metric_article => ["/posts/Analysis-해석학(2)/", "/ko/posts/analysis-metric-spaces-2/"]
}.each do |html, expected_links|
  actual_links = decoded_hrefs(language_nav(html))
  expected_links.each do |expected|
    assert(actual_links.include?(expected), "Analysis II language selector is missing #{expected}")
  end
end

{
  english_article => ["/ko/posts/analysis-foundations-1/", "/ja/posts/analysis-foundations-1/"],
  korean_article => ["/posts/Analysis-解析学-1/", "/ja/posts/analysis-foundations-1/"],
  japanese_article => ["/posts/Analysis-解析学-1/", "/ko/posts/analysis-foundations-1/"]
}.each do |html, expected_links|
  actual_links = decoded_hrefs(language_nav(html))
  expected_links.each do |expected|
    assert(actual_links.include?(expected), "Language selector is missing #{expected}")
  end
end

[
  ["Bolzano-Weierstrass-theorem", "bolzano-weierstrass"],
  ["Nested-Interval-Property", "nested-interval-property"],
  ["Bézout's-identity-p1", "bezout-identity-1"],
  ["Euclidean-Algorithm", "euclidean-algorithm"],
  ["Max-flow-min-cut-theorem", "max-flow-min-cut-theorem"],
  ["Flow-network", "flow-network"],
  ["Floyd-Warshall-Algorithm", "floyd-warshall-algorithm"],
  ["boj1006", "boj-1006-defense"],
  ["boj1395", "boj-1395-switches"],
  ["boj19565", "boj-19565-sequence"],
  ["boj1035", "boj-1035-moving-pieces"],
  ["boj3653", "boj-3653-movie-collection"],
  ["BOJ-17386", "boj-17386-crossing-lines"],
  ["BOJ-17387", "boj-17387-crossing-lines"],
  ["BOJ-7869", "boj-7869-two-circles"],
  ["BOJ-1069", "boj-1069-going-home"],
  ["BOJ-11375", "boj-11375-job-assignment"],
  ["BOJ-11376", "boj-11376-job-assignment"],
  ["BOJ-11378", "boj-11378-extra-jobs"],
  ["BOJ-15927", "boj-15927-non-palindrome"],
  ["BOJ-2162", "boj-2162-line-groups"],
  ["BOJ-7469", "boj-7469-kth-number"],
  ["BOJ-1520", "boj-1520-downhill-paths"],
  ["BOJ-2494", "boj-2494-number-matching"],
  ["BOJ-5373", "boj-5373-cubing"],
  ["BOJ-2636", "boj-2636-cheese-melting"],
  ["LeetCode-1.-Two-Sum", "leetcode-1-two-sum"],
  ["LeetCode-11.-Container-With-Most-Water", "leetcode-11-container-with-most-water"],
  ["LeetCode-12.Integer-to-Roman", "leetcode-12-integer-to-roman"],
  ["LeetCode-15.-3Sum", "leetcode-15-three-sum"],
  ["LeetCode-16.-3Sum-Closest", "leetcode-16-three-sum-closest"],
  ["LeetCode-3.-Longest-Substring-Without-Repeating-Characters", "leetcode-3-longest-unique-substring"],
  ["LeetCode-5.-Longest-Palindromic-Substring", "leetcode-5-longest-palindromic-substring"],
  ["LeetCode-4-Median-of-Two-Sorted-Arrays", "leetcode-4-median-of-two-sorted-arrays"],
  ["LeetCode-6.-ZigZag-Conversion", "leetcode-6-zigzag-conversion"],
  ["LeetCode-7.-Reverse-Integer", "leetcode-7-reverse-integer"],
  ["LeetCode-9.-Palindrome-Number", "leetcode-9-palindrome-number"],
  ["LeetCode-26.-Remove-Duplicates-from-Sorted-Array", "leetcode-26-remove-duplicates"],
  ["LeetCode-27.-Remove-Element", "leetcode-27-remove-element"],
  ["LeetCode-20.-Valid-Parentheses", "leetcode-20-valid-parentheses"],
  ["LeetCode-10.-Regular-Expression-Matching", "leetcode-10-regex-matching"],
  ["LeetCode-13.-Roman-to-Integer", "leetcode-13-roman-to-integer"],
  ["LeetCode-14.-Longest-Common-Prefix.-Longest-Common-Prefix", "leetcode-14-longest-common-prefix"],
  ["LeetCode-21.-Merge-Two-Sorted-Lists", "leetcode-21-merge-two-sorted-lists"],
  ["LeetCode-53.-Maximum-Subarray", "leetcode-53-maximum-subarray"],
  ["LeetCode-17.-Letter-Combinations-of-a-Phone-Number", "leetcode-17-letter-combinations"],
  ["LeetCode-19.-Remove-Nth-Node-From-End-of-List", "leetcode-19-remove-nth-node"],
  ["LeetCode-23.-Merge-k-Sorted-Lists", "leetcode-23-merge-k-sorted-lists"],
  ["LeetCode-24.-Swap-Nodes-in-Pairs", "leetcode-24-swap-nodes-in-pairs"],
  ["LeetCode-25.-Reverse-Nodes-in-k-Group", "leetcode-25-reverse-k-group"],
  ["LeetCode-28.-Implement-strStr()", "leetcode-28-strstr"],
  ["LeetCode-83.-Remove-Duplicates-from-Sorted-List", "leetcode-83-remove-duplicates-sorted-list"],
  ["LeetCode-540.-Single-Element-in-a-Sorted-Array", "leetcode-540-single-element-sorted-array"],
  ["BOJ-11659", "boj-11659-range-sum"],
  ["LeetCode-461.-Hamming-Distance", "leetcode-461-hamming-distance"],
  ["LeetCode-94.-Binary-Tree-Inorder-Traversal", "leetcode-94-inorder-traversal"],
  ["Graph-theory-Strongly-Connected-Component", "strongly-connected-components"],
  ["競プロ典型-90-問-21-Come-Back-in-One-Piece-5", "atcoder-typical90-021-come-back-one-piece"],
  ["LeetCode-35.-Search-Insert-Position", "leetcode-35-search-insert"],
  ["BOJ-11279", "boj-11279-max-heap"],
  ["LeetCode-29.-Divide-Two-Integers", "leetcode-29-divide-two-integers"],
  ["LeetCode-30.-Substring-with-Concatenation-of-All-Words", "leetcode-30-substring-concat"],
  ["LeetCode-31.-Next-Permutation", "leetcode-31-next-permutation"],
  ["Leetcode-32.-Longest-Valid-Parentheses", "leetcode-32-longest-valid-parentheses"],
  ["Leetcode-33.-Search-in-Rotated-Sorted-Array", "leetcode-33-rotated-search"],
  ["Leetcode-34.-Find-First-and-Last-Position-of-Element-in-Sorted-Array", "leetcode-34-search-range"],
  ["Leetcode-36.-Valid-Sudoku", "leetcode-36-valid-sudoku"],
  ["Leetcode-38.-Count-and-Say", "leetcode-38-count-and-say"],
  ["Codility-ArrayInversionCount", "codility-array-inversion-count"],
  ["BOJ-1655", "boj-1655-running-median"],
  ["Programmers-Bigest-Number", "programmers-largest-number"],
  ["BOJ-1697", "boj-1697-hide-and-seek"],
  ["De-Moivre-s-formula", "de-moivre-formula"],
  ["BOJ-7569", "boj-7569-tomato"],
  ["BOJ-2042", "boj-2042-fenwick"],
  ["Leetcode-40.-Combination-Sum-II", "leetcode-40-combination-sum-ii"],
  ["Leetcode-37.-Sudoku-Solver", "leetcode-37-sudoku-solver"],
  ["BOJ-2206", "boj-2206-wall-break"],
  ["Leetcode-39.-Combination-Sum", "leetcode-39-combination-sum"],
  ["BOJ-1504", "boj-1504-required-path"],
  ["BOJ-1717", "boj-1717-disjoint-set"],
  ["BOJ-1753", "boj-1753-dijkstra"],
  ["BOJ-7562", "boj-7562-knight-moves"],
  ["競プロ典型-90-問-003-Longest-Circular-Road-4", "atcoder-typical90-003-tree-diameter"],
  ["BOJ-1707", "boj-1707-bipartite-graph"],
  ["競プロ典型-90-問-001-Yokan-Party-4", "atcoder-typical90-001-yokan-party"],
  ["競プロ典型-90-問-002-Encyclopedia-of-Parentheses-3", "atcoder-typical90-002-parentheses"],
  ["競プロ典型-90-問-004-Cross-Sum-2", "atcoder-typical90-004-cross-sum"],
  ["競プロ典型-90-問-005-Restricted-Digits", "atcoder-typical90-005-restricted-digits"],
  ["競プロ典型-90-問-006-Smallest-Subsequence", "atcoder-typical90-006-smallest-subsequence"],
  ["競プロ典型-90-問-008-AtCounter(4)", "atcoder-typical90-008-atcounter"],
  ["競プロ典型-90-問-007-CP-Classes(3)", "atcoder-typical90-007-cp-classes"],
  ["競プロ典型-90-問-010-Score-Sum-Queries", "atcoder-typical90-010-score-sum-queries"],
  ["競プロ典型-90-問-011-Gravy-Jobs", "atcoder-typical90-011-gravy-jobs"],
  ["競プロ典型-90-問-009-Three-Point-Angle", "atcoder-typical90-009-three-point-angle"],
  ["競プロ典型-90-問-012-Red-Painting(4)", "atcoder-typical90-012-red-painting"],
  ["競プロ典型-90-問-013-Passing(5)", "atcoder-typical90-013-passing"],
  ["競プロ典型-90-問-014-We-Used-to-Sing-a-Song-Together", "atcoder-typical90-014-pair-distances"],
  ["競プロ典型-90-問-015-Don't-be-too-close(6)", "atcoder-typical90-015-non-adjacent"],
  ["競プロ典型-90-問-016-Minimum-Coins", "atcoder-typical90-016-minimum-coins"],
  ["競プロ典型-90-問-018-Statue-of-Chokudai(3)", "atcoder-typical90-018-statue-angle"],
  ["BOJ-11505", "boj-11505-range-product"],
  ["Leetcode-312.-Burst-Balloons", "leetcode-312-burst-balloons"],
  ["BOJ-9370", "boj-9370-unidentified-destination"],
  ["BOJ-11404", "boj-11404-floyd-warshall"],
  ["BOJ-11657", "boj-11657-time-machine"],
  ["Programmers-Network", "programmers-network-components"],
  ["Leetcode-997.-Find-the-Town-Judge", "leetcode-997-town-judge"],
  ["BOJ-11066", "boj-11066-file-merge"],
  ["BOJ-1956", "boj-1956-shortest-cycle"],
  ["BOJ-1976", "boj-1976-travel-plan"],
  ["BOJ-11725", "boj-11725-tree-parents"],
  ["BOJ-20040", "boj-20040-cycle-game"],
  ["BOJ-1991", "boj-1991-tree-traversals"],
  ["Leetcode-131.-Palindrome-Partitioning", "leetcode-131-palindrome-partitioning"],
  ["BOJ-1517", "boj-1517-bubble-sort-inversions"],
  ["BOJ-1167", "boj-1167-tree-diameter"],
  ["BOJ-1967", "boj-1967-tree-diameter"],
  ["BOJ-4195", "boj-4195-friend-network"],
  ["BOJ-5639", "boj-5639-bst-postorder"],
  ["BOJ-10217", "boj-10217-kcm-travel"],
  ["BOJ-2357", "boj-2357-range-min-max"],
  ["BOJ-2263", "boj-2263-tree-reconstruction"],
  ["BOJ-4803", "boj-4803-count-trees"],
  ["BOJ-12852", "boj-12852-make-one"],
  ["BOJ-14002", "boj-14002-lis-sequence"],
  ["BOJ-9252", "boj-9252-lcs-reconstruction"],
  ["BOJ-14003", "boj-14003-lis-reconstruction"],
  ["BOJ-13913", "boj-13913-hide-and-seek-path"],
  ["BOJ-9019", "boj-9019-dslr-shortest-commands"],
  ["BOJ-11779", "boj-11779-shortest-path"],
  ["BOJ-16975", "boj-16975-range-add-point-query"],
  ["BOJ-12899", "boj-12899-order-statistics"],
  ["BOJ-2618", "boj-2618-police-cars"],
  ["BOJ-1168", "boj-1168-josephus"],
  ["BOJ-11780", "boj-11780-all-pairs-paths"],
  ["BOJ-11723", "boj-11723-bitmask-set"],
  ["BOJ-1311", "boj-1311-assignment-dp"],
  ["BOJ-2150", "boj-2150-scc"],
  ["BOJ-2252", "boj-2252-topological-sort"],
  ["BOJ-1644", "boj-1644-consecutive-prime-sum"],
  ["BOJ-9345", "boj-9345-dvd-permutations"],
  ["BOJ-2887", "boj-2887-planet-tunnel-mst"],
  ["BOJ-15681", "boj-15681-tree-subtree-queries"],
  ["BOJ-11049", "boj-11049-matrix-chain"],
  ["BOJ-1197", "boj-1197-minimum-spanning-tree"],
  ["BOJ-3273", "boj-3273-pair-sum-count"],
  ["BOJ-1450", "boj-1450-meet-in-middle"],
  ["BOJ-1806", "boj-1806-minimum-subarray-length"],
  ["BOJ-2470", "boj-2470-closest-to-zero-pair"],
  ["BOJ-14725", "boj-14725-ant-tunnel"],
  ["BOJ-14425", "boj-14425-string-set"],
  ["BOJ-4354", "boj-4354-string-power"],
  ["BOJ-1786", "boj-1786-kmp-search"],
  ["BOJ-1094", "boj-1094-stick"],
  ["BOJ-17472", "boj-17472-island-bridges"],
  ["BOJ-5670", "boj-5670-phone-keyboard"],
  ["BOJ-2098", "boj-2098-tsp-bitmask-dp"],
  ["BOJ-17404", "boj-17404-rgb-distance-2"],
  ["BOJ-1005", "boj-1005-acm-craft"],
  ["BOJ-1305", "boj-1305-advertisement-period"],
  ["BOJ-17435", "boj-17435-function-composition"],
  ["BOJ-11003", "boj-11003-sliding-window-minimum"],
  ["BOJ-3584", "boj-3584-lowest-common-ancestor"],
  ["BOJ-11438", "boj-11438-lca-binary-lifting"],
  ["BOJ-1086", "boj-1086-permutation-probability"],
  ["BOJ-1509", "boj-1509-palindrome-partitioning"],
  ["BOJ-1949", "boj-1949-good-village"],
  ["BOJ-2213", "boj-2213-tree-independent-set"],
  ["BOJ-2533", "boj-2533-early-adopter-tree-dp"],
  ["BOJ-11280", "boj-11280-two-sat"],
  ["BOJ-11281", "boj-11281-two-sat-assignment"],
  ["BOJ-2482", "boj-2482-circular-color-selection"],
  ["BOJ-3977", "boj-3977-soccer-tactics-scc"],
  ["BOJ-13511", "boj-13511-tree-query-2"],
  ["BOJ-4196", "boj-4196-domino-scc"],
  ["BOJ-3648", "boj-3648-idol-two-sat"],
  ["BOJ-3665", "boj-3665-final-ranking"],
  ["BOJ-4013", "boj-4013-atm"],
  ["BOJ-3176", "boj-3176-road-network"],
  ["Atcoder-A-Rotate", "abc235-a-digit-rotations"],
  ["Atcoder-B-Climbing-Takahashi", "abc235-b-climbing-takahashi"],
  ["Atcoder-C-The-Kth-Time-Query", "abc235-c-kth-time-query"],
  ["Atcoder-D-Multiply-and-Rotate", "abc235-d-multiply-and-rotate"],
  ["BOJ-1009", "boj-1009-distributed-processing"],
  ["BOJ-2836", "boj-2836-water-taxi"],
  ["BOJ-2170", "boj-2170-line-drawing"],
  ["BOJ-5419", "boj-5419-northwest-wind"],
  ["Atcoder-C-XOR-to-All", "arc135-c-xor-to-all"],
  ["BOJ-10868", "boj-10868-range-minimum-query"],
  ["BOJ-1275", "boj-1275-coffee-shop"],
  ["Atcoder-A-Exponential-or-Quadratic", "abc238-a-exponential-or-quadratic"],
  ["Atcoder-C-digitnum", "abc238-c-digitnum"],
  ["Atcoder-A-Floor,-Ceil-Decomposition", "arc135-a-floor-ceil-decomposition"],
  ["Atcoder-B-Sum-of-Three-Terms", "arc135-b-sum-three-terms"],
  ["Atcoder-B-Pizza", "abc238-b-pizza"],
  ["Atcoder-A-Not-Overflow", "abc237-a-not-overflow"],
  ["Atcoder-B-Matrix-Transposition", "abc237-b-matrix-transposition"],
  ["Codeforces-Global-Round-19-A.-Sorting-Parts", "cf-1637a-sorting-parts"],
  ["BOJ-1725", "boj-1725-largest-rectangle"],
  ["Atcoder-C-kasaka", "abc237-c-kasaka"],
  ["Atcoder-D-LR-insertion", "abc237-d-lr-insertion"],
  ["BOJ-2268", "boj-2268-range-sum-7"],
  ["BOJ-14500", "boj-14500-tetromino"],
  ["BOJ-15686", "boj-15686-chicken-delivery"],
  ["BOJ-17131", "boj-17131-fox-triples"],
  ["BOJ-10999", "boj-10999-range-sum-2"],
  ["BOJ-16236", "boj-16236-baby-shark"],
  ["BOJ-10266", "boj-10266-clock-pictures"],
  ["Atcoder-E-Skiing", "abc237-e-skiing"],
  ["BOJ-1766", "boj-1766-workbook"],
  ["BOJ-13275", "boj-13275-longest-palindrome"],
  ["BOJ-5052", "boj-5052-phone-number-list"],
  ["Codeforces-Global-Round-19-C.-Andrew-and-Stones", "cf-1637-andrew-stones"],
  ["BOJ-1339", "boj-1339-word-math"],
  ["Codeforces-Global-Round-19-B.-MEX-and-Array", "cf-1637b-mex-and-array"],
  ["BOJ-3190", "boj-3190-snake"],
  ["Codeforces-Codeforces-Round-771-(Div.-2)-A.-Reverse", "cf-1638a-reverse"],
  ["BOJ-11437", "boj-11437-lowest-common-ancestor"],
  ["BOJ-14890", "boj-14890-runway"],
  ["BOJ-14503", "boj-14503-robot-vacuum"],
  ["BOJ-14499", "boj-14499-rolling-dice"],
  ["BOJ-2169", "boj-2169-robot-control"],
  ["BOJ-15683", "boj-15683-cctv"],
  ["BOJ-16234", "boj-16234-population-movement"],
  ["BOJ-13460", "boj-13460-marble-escape"],
  ["BOJ-17144", "boj-17144-dust-simulation"],
  ["BOJ-1240", "boj-1240-tree-distance"],
  ["BOJ-16235", "boj-16235-tree-investment"],
].each do |source_slug, translation_key|
  source_url = "/posts/#{source_slug}/"
  korean_url = "/ko/posts/#{translation_key}/"
  japanese_url = "/ja/posts/#{translation_key}/"
  english = read_page(site_root, "posts/#{source_slug}/index.html")
  korean = read_page(site_root, "ko/posts/#{translation_key}/index.html")
  japanese = read_page(site_root, "ja/posts/#{translation_key}/index.html")

  [
    [english, "en", [korean_url, japanese_url]],
    [korean, "ko", [source_url, japanese_url]],
    [japanese, "ja", [source_url, korean_url]]
  ].each do |html, language, expected_links|
    assert(language_is?(html, language), "#{source_slug} translation must declare lang=#{language}")
    actual_links = decoded_hrefs(language_nav(html))
    expected_links.each do |expected|
      assert(actual_links.include?(expected), "#{source_slug} selector is missing #{expected}")
    end
  end

  canonical = english.match(/<link rel="canonical" href="([^"]+)"/)
  assert(canonical, "#{source_slug} must have a canonical URL")
  assert(URI::DEFAULT_PARSER.unescape(canonical[1]).end_with?(source_url), "#{source_slug} source route changed")
end

{
  "posts/Analysis-解析学-１/index.html" => "/posts/Analysis-解析学-1/",
  "posts/Analysis-해석학(1)/index.html" => "/posts/Analysis-解析学-1/",
  "posts/LeetCode-4.-Median-of-Two-Sorted-Arrays/index.html" => "/posts/LeetCode-4-Median-of-Two-Sorted-Arrays/"
}.each do |legacy_path, canonical_path|
  legacy_page = read_page(site_root, legacy_path)
  assert(
    URI::DEFAULT_PARSER.unescape(legacy_page).include?(canonical_path),
    "#{legacy_path} must redirect to #{canonical_path}"
  )
end

puts "Language-mode homes, canonical archive, reviewed article routes, reciprocal selectors, HTML languages, and legacy redirects passed."
