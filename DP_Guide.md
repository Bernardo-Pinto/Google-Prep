Steps to take when solving a DP problem:
1. Define the subproblem:
    - What does dp[i][j] represent?
2. What is the recurrence?
    -  how does dp[i][j] depend on smaller states?
3. Identify base cases
    - What are the smallest inputs where the answer is trivially known?
4. Determine the answer
    - Is it dp[n]? dp[m][n]? max(dp[i][j])?
5. Check the order
    - Fill from left-to-right, right-to-left, bottom-up, etc? 
    - Make sure each cell depends on already computed cells 