import java.util.ArrayDeque;
import java.util.Random;

class Solution {
    public int longestValidParentheses(String s) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') stack.push(i);
            else {
                stack.pop();
                if (stack.isEmpty()) stack.push(i);
                else max = Math.max(max, i - stack.peek());
            }
        }
        return max;
    }
}

public class LongestValidParenthesesCheck {
    private static int brute(String s) {
        int best = 0;
        for (int left = 0; left < s.length(); left++) {
            for (int right = left + 2; right <= s.length(); right += 2) {
                int balance = 0;
                boolean valid = true;
                for (int i = left; i < right; i++) {
                    balance += s.charAt(i) == '(' ? 1 : -1;
                    if (balance < 0) { valid = false; break; }
                }
                if (valid && balance == 0) best = Math.max(best, right - left);
            }
        }
        return best;
    }
    private static void check(Solution solution, String s) {
        int expected = brute(s);
        int actual = solution.longestValidParentheses(s);
        if (actual != expected) throw new AssertionError("s=" + s + " expected=" + expected + " actual=" + actual);
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        for (int n = 0; n <= 12; n++) {
            for (int bits = 0; bits < (1 << n); bits++) {
                StringBuilder s = new StringBuilder(n);
                for (int i = 0; i < n; i++) s.append(((bits >>> i) & 1) == 0 ? '(' : ')');
                check(solution, s.toString());
            }
        }
        check(solution, "");
        check(solution, ")(");
        check(solution, "((((((");
        check(solution, "))))))");
        check(solution, "()(())");
        check(solution, "()(()))))((()())");
        Random random = new Random(320032L);
        for (int t = 0; t < 500; t++) {
            int n = 13 + random.nextInt(188);
            StringBuilder s = new StringBuilder(n);
            for (int i = 0; i < n; i++) s.append(random.nextBoolean() ? '(' : ')');
            check(solution, s.toString());
        }
        System.out.println("PASS: exhaustive strings through length 12, explicit edge cases, and 500 seeded random strings");
    }
}
