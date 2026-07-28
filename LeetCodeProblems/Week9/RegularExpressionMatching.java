package LeetCodeProblems.Week9;

/**
 * LeetCode #10 - Regular Expression Matching  [HARD]
 *
 * Given a string s and pattern p, implement regex matching supporting:
 *  '.' — matches any single character
 *  '*' — matches zero or more of the preceding element
 *
 * The match must cover the ENTIRE string.
 *
 * Example 1:
 *  s="aa", p="a"     → false  (pattern must match whole string)
 *
 * Example 2:
 *  s="aa", p="a*"    → true   (a* = two a's)
 *
 * Example 3:
 *  s="ab", p=".*"    → true   (.* matches any sequence)
 *
 * Constraints:
 *  - 1 <= s.length <= 20
 *  - 1 <= p.length <= 30
 *  - s contains only lowercase letters
 *  - p contains only lowercase letters, '.', and '*'
 *  - '*' is never the first character of p
 *  - Each '*' has a valid preceding element
 */
public class RegularExpressionMatching {

    public static boolean isMatch(String s, String p) {
        // TODO: implement
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a"));     // false
        System.out.println(isMatch("aa", "a*"));    // true
        System.out.println(isMatch("ab", ".*"));    // true
        System.out.println(isMatch("aab", "c*a*b")); // true  (c*=zero c's, a*=two a's, b=b)
        System.out.println(isMatch("mississippi", "mis*is*p*.")); // false
        System.out.println(isMatch("", "a*"));      // true  (zero a's)
        System.out.println(isMatch("a", "ab*"));    // true  (b*=zero b's)
    }
}

/*
 * Key insight:
 *  dp[i][j] = does s[0..i-1] match p[0..j-1]?
 *
 *  Base: dp[0][0] = true (empty matches empty)
 *        dp[0][j] = dp[0][j-2] if p[j-1]=='*'  (star can match zero of preceding)
 *
 *  Transition:
 *    if p[j-1] == s[i-1] or p[j-1] == '.':
 *       dp[i][j] = dp[i-1][j-1]
 *    if p[j-1] == '*':
 *       dp[i][j] = dp[i][j-2]                          (zero occurrences of preceding)
 *               OR (dp[i-1][j] if p[j-2] matches s[i-1]) (one+ occurrences)
 */
