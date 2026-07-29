package LeetCodeProblems.Week9;

import java.util.*;

/**
 * LeetCode #739 - Daily Temperatures  [MEDIUM]
 *
 * Given an array of daily temperatures, return an array answer where answer[i]
 * is the number of days you have to wait after day i to get a warmer temperature.
 * If no future day is warmer, answer[i] = 0.
 *
 * Example 1:
 *  temperatures = [73,74,75,71,69,72,76,73]
 *  Output:        [1, 1, 4, 2, 1, 1, 0, 0]
 *
 * Example 2:
 *  temperatures = [30,40,50,60]
 *  Output:        [1,1,1,0]
 *
 * Example 3:
 *  temperatures = [30,60,90]
 *  Output:        [1,1,0]
 *
 * Constraints:
 *  - 1 <= temperatures.length <= 10^5
 *  - 30 <= temperatures[i] <= 100
 */
public class DailyTemperatures {

    public static int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> positions = new Stack<>();
        int[] result =  new int[temperatures.length];
        for(int i=0;i<temperatures.length;i++){
            while (!positions.isEmpty() && temperatures[positions.peek()] < temperatures[i]) {
                result[positions.peek()] = i - positions.pop();
            }
            positions.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        // [1,1,4,2,1,1,0,0]
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));

        // [1,1,1,0]
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{30,40,50,60})));

        // [1,1,0]
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{30,60,90})));

        // [0] — single day, no future
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{50})));

        // [0,0,0,0] — decreasing, never warmer
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{90,80,70,60})));
    }
}

/*
 * Key insight: monotonic decreasing stack of indices.
 * For each day i: pop all days from the stack that are cooler than today.
 * Those days were waiting for a warmer day — that warmer day is i.
 * answer[popped] = i - popped
 */
