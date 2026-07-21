package LeetCodeProblems.Week8;

/**
 * LeetCode #79 - Word Search
 *
 * Given an m x n grid of characters and a string word, return true if
 * the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells
 * (horizontally or vertically neighboring). The same cell may not be used more than once.
 *
 * Example 1:
 *  [["A","B","C","E"],
 *   ["S","F","C","S"],
 *   ["A","D","E","E"]]
 *  word = "ABCCED"  →  true
 *  word = "SEE"     →  true
 *  word = "ABCB"    →  false  (can't reuse 'B')
 *
 * Example 2:
 *  [["A","B"],
 *   ["C","D"]]
 *  word = "ABDC"   →  true   (A→B→D→C)
 *  word = "ABCD"   →  false
 *
 * Constraints:
 *  - m == board.length, n == board[i].length
 *  - 1 <= m, n <= 6
 *  - 1 <= word.length <= 15
 *  - board and word consist only of uppercase English letters
 *
 * Hint: DFS backtracking from every cell that matches word[0].
 *  Mark the cell as visited (e.g., board[r][c] = '#') before recursing,
 *  then restore it after (backtrack).
 */
public class WordSearch {

    public static boolean exist(char[][] board, String word) {
        // TODO: implement
        return false;
    }

    public static void main(String[] args) {
        char[][] board1 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };

        System.out.println(exist(board1, "ABCCED")); // Expected: true
        System.out.println(exist(board1, "SEE"));     // Expected: true
        System.out.println(exist(board1, "ABCB"));    // Expected: false

        char[][] board2 = {
            {'A','B'},
            {'C','D'}
        };

        System.out.println(exist(board2, "ABDC")); // Expected: true
        System.out.println(exist(board2, "ABCD")); // Expected: false

        // Edge: single cell
        System.out.println(exist(new char[][]{{'A'}}, "A")); // Expected: true
        System.out.println(exist(new char[][]{{'A'}}, "B")); // Expected: false
    }
}
