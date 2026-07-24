package LeetCodeProblems.Week8;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


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
*/
public class WordSearch {

    public static boolean exist(char[][] board, String word) {

        if(word.isEmpty()) return false; // idk what is the expected return on empty word
        if(board.length == 0) return false;

        char[] wordchars = word.toCharArray();
        char wordStart = wordchars[0];
        List<int[]> wordStarts = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j] == wordStart) wordStarts.add(new int[]{i,j});
            }
        }

        boolean[][] visited;
        for(int[] startPos : wordStarts){
            visited = new boolean[board.length][board[0].length];
            if(dfs(startPos, board, wordchars, 0, visited)) return true;
        }

        return false;
    }

    private static boolean dfs(int[] pos, char[][] board, char[] word, int charCount, boolean[][] visited){
        int i = pos[0];
        int j = pos[1];
        if(!inBounds(board, i, j)) return false;
        if(board[i][j] == word[charCount]) charCount++;
        else return false;

        if(visited[i][j]) return false;
        visited[i][j] = true;
        
        if(charCount == word.length) return true;
        
        if(dfs(new int[]{i+1,j}, board, word, charCount,visited)) return true;
        if(dfs(new int[]{i-1,j}, board, word, charCount,visited)) return true;
        if(dfs(new int[]{i,j+1}, board, word, charCount,visited)) return true;
        if(dfs(new int[]{i,j-1}, board, word, charCount,visited)) return true;
        visited[i][j] = false;
        return false;
    }

    private static boolean inBounds(char[][] board, int i, int j){
        return i<board.length && i>=0 && j < board[i].length && j>=0;
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

/**
 * 
 *  * Hint: DFS backtracking from every cell that matches word[0].
 *  Mark the cell as visited (e.g., board[r][c] = '#') before recursing,
 *  then restore it after (backtrack).
 *
 */
