package LeetCodeProblems.Week9;

import java.util.*;

/**
 * LeetCode #84 - Largest Rectangle in Histogram  [HARD]
 *
 * Given an array of bar heights, find the area of the largest rectangle
 * that can be formed within the histogram.
 *
 * Example 1:
 *  heights = [2,1,5,6,2,3]
 *  Output: 10  (rectangle formed by bars of height 5 and 6, width 2)
 *    |  
 *   ||
 *   ||
 *   || |
 * | ||||
 * ||||||
 * stack:
 * (2) max = 0 or 2 = 2
 * (1,1): 1<2, remove 2. max = 2*1 = 2
 * (5,1,1) max = 2 or 5 = 5
 * (6,5,1,1) max = 5 or 6 = 6
 * (2,2,2,1,1) 2<5 remove all higher than 2 and replace them with 2. max = 6 or min(5,6) * 2 = 10
 * (3,2,2,2,1,1) end of list. take element from end of stack(1) until a different appears. max = 10 or stack.size() * 1 = 10 or 6*1 = 10 or 6 = 10
 * (3,2,2,2). repeat the same was last time. max = 10 or 2*4 = 10 or 8 = 10
 * (3). repeat the same as last time. max = 10 or 3*1 = 10 
 * ------------------
 *   || 
 * | ||
 * ||||||
 * stack:
 * (2) max = 2*1
 * (2,1): 1<2, remove 2, add 1 (1,1), max = 2 (2*1)
 * (3,1,1) , max = 2
 * (3,3,1,1), max = 2
 * (1,1,1,1,1), 1<3 remove the 3's and replace them with 1's. max = 2 or 3*2 = 6
 * (1,1,1,1,1,1), end of list. remove all 1's, max = 6 or 1*6 = 6
 * 
 * ||
 * ||||
 * ||||
 * stack:
 * (3)
 * (3,3)
 * (2,2,2) 2<3, remove 3 and replace with 2. max = 3*2 = 6
 * (2,2,2,2). end of list. remove all 2's. max = 6 or 2*4 = 8
 * A rectangle can have width 1
 * A rectangle's height is the min height of all bars in its width span
 * 
 * 
 * Example 2:
 *  heights = [2,4]
 *  Output: 4
 *
 * Constraints:
 *  - 1 <= heights.length <= 10^5
 *  - 0 <= heights[i] <= 10^4
 */
public class LargestRectangleHistogram {

    //[2,1,5,6,2,3]
    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> main =  new Stack<>();
        Stack<Integer> popped =  new Stack<>();
        int maxArea = 0;
        main.push(heights[0]);
        for(int i=1;i<heights.length;i++){
            if(heights[i] < main.peek()){
                while(!main.isEmpty() && heights[i] < main.peek()) popped.push(main.pop());
                for(int j=0;j<popped.size();j++) main.add(heights[i]);
                while (!popped.isEmpty()) {
                    int poppedSize = popped.size();
                    int peeked = popped.peek();
                    maxArea = Math.max(maxArea, poppedSize*peeked);
                    while(!popped.isEmpty() && popped.peek() == peeked) popped.pop();
                }
            }
            main.push(heights[i]);
        }
        //[1,1,2,2,2,3]
        while (!main.isEmpty()) {
            int poppedSize = main.size();
            int firstEl = main.firstElement();
            maxArea = Math.max(maxArea, poppedSize*firstEl);
            while(!main.isEmpty() && main.firstElement() == firstEl) main.removeFirst();
        }

        return maxArea;
    }

    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[]{2,1,5,6,2,3})); // Expected: 10
        System.out.println(largestRectangleArea(new int[]{2,4}));         // Expected: 4
        System.out.println(largestRectangleArea(new int[]{1}));           // Expected: 1
        System.out.println(largestRectangleArea(new int[]{0}));           // Expected: 0
        System.out.println(largestRectangleArea(new int[]{6,7,5,2,4,5,9,3})); // Expected: 16
    }
}

/*
 * Key insight: monotonic increasing stack of indices.
 * For each bar i: if it's shorter than the top of the stack, the bar at the top
 * can no longer extend right. Its width spans from the new stack top to i.
 * Pop and compute area = height[popped] * width.
 *
 * Trick: append a sentinel 0 at the end to flush all remaining bars from the stack.
 */
