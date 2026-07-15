package LeetCodeProblems.DP;

public class Knapsack01 {

    public static int knapsack1D(int[] weights, int[] values, int capacity){
        //length of weights == length of values
        int[] tab = new int[capacity+1];
        for(int i=0; i<weights.length;i++){
            // the w>=weights[i] automatically rejects cases where weights[i] > capacity
            for(int w = capacity; w>=weights[i];w--){
                tab[w] = Math.max(tab[w], tab[w-weights[i]] + values[i]);
            }
        }
        return tab[capacity];
    }

    public static int knapsack2D(int[] weights, int[] values, int capacity){
        // use length + 1 to have the 0 weight and 0 value row
        // use capacity + 1 because there is also capacity 0
        int[][] tab =  new int[weights.length+1][capacity+1];

        // start at i=1 to skip the 00 row,and because we will need to access row-1
        // if we start from 0, that will give out of bounds exception
        for(int i=1;i<=weights.length;i++){
            //the item index is one less than i
            int itemIndex = i-1;
            //w=0 to capacity because we need to set all capacities with the max that 
            //  the current item can set, since next item will depend on 
            //  the max of the previous items at each capacity
            for(int w=0;w<=capacity;w++){

                // must set this, its the max from the previous items used
                tab[i][w] = tab[i-1][w];

                //item must fit
                if(weights[itemIndex]<=w){

                    // either dont take item (keep value from previous items)
                    // or take new item, adding the new item value + the previous value where prevW + newItemW = w
                    // e.g: itemW = 2; itemV = 10; for each w, if itemW < w, lets say w=2;
                    //      either use value from previous row at same capacity, tab[i-1][w]
                    //      or value from previous row, at capacity w-itemW <=> 2-2 = 0, tab[i-1][0]
                    //          plues itemV = 10 <=> tab[i][2] = tab[i-1][0] + 10  
                    tab[i][w] = Math.max(tab[i-1][w], tab[i-1][w-weights[itemIndex]] + values[itemIndex]);
                }
            }
        }
        return tab[weights.length][capacity];
    }
    public static void main(String[] args) {
        int[] weights1 = new int[]{1,2,3,5};
        int[] values1  = new int[]{1,6,10,16};
        int[] weights2 = new int[]{2,2};
        int[] values2  = new int[]{5,6};
        System.out.println("-----1D----");
        System.out.println("Test 1, capacity 0, (0): "  + knapsack1D(weights1, values1, 0));
        System.out.println("Test 1, capacity 1, (1): "  + knapsack1D(weights1, values1, 1));
        System.out.println("Test 1, capacity 5, (16): " + knapsack1D(weights1, values1, 5));
        System.out.println("Test 1, capacity 6, (17): " + knapsack1D(weights1, values1, 6));
        System.out.println("Test 1, capacity 7, (22): " + knapsack1D(weights1, values1, 7));
        System.out.println("Test 2, capacity 4, (11): " + knapsack1D(weights2, values2, 4));
        System.out.println("-----2D----");
        System.out.println("Test 1, capacity 0, (0): "  + knapsack2D(weights1, values1, 0));
        System.out.println("Test 1, capacity 1, (1): "  + knapsack2D(weights1, values1, 1));
        System.out.println("Test 1, capacity 5, (16): " + knapsack2D(weights1, values1, 5));
        System.out.println("Test 1, capacity 6, (17): " + knapsack2D(weights1, values1, 6));
        System.out.println("Test 1, capacity 7, (22): " + knapsack2D(weights1, values1, 7));
        System.out.println("Test 2, capacity 4, (11): " + knapsack2D(weights2, values2, 4));
    }
}
