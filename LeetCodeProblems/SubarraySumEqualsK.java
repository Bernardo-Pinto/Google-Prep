package LeetCodeProblems;

import java.util.HashMap;

public class SubarraySumEqualsK {
     public int subarraySum(int[] nums, int k) {
        HashMap<Integer,Integer> map =  new HashMap<>();
        int total = 0;
        map.put(0,1); //any value can add 0 to itself and be equal to k
        int sum = 0; //prefix can be represented as the sum of all previous elements
        for(int i=0;i<nums.length;i++){
            sum += nums[i]; // the value of all the previous nums summed up
            int key = sum-k;
            if(map.containsKey(key)){
                // entering here means that the current sum was seen before,
                //  and its possible to add up to k, in map.get(sum-k) ways
                // e.g: 1,1,1,1,1 and k=3 would have sums 1,2,3,4,5.
                // the map would have: <0:1>,<1:1>,<2:1>,<3:1>,<4:1>,<5:1>
                // for i=2, sum would be 3, and 3-3 = 0, which the map does have.
                // it would then add <3:1> to the map. going to i=3, sum=4, 4-3=1, and map has 1.
                // meaning for i=2, we can get to 3(k) map.get(0) ways. for i=3, map.get(1) ways, so we add those
                // e.g: 0,0,0, k=0, map would have <0:4>. i=0, 0-0=0, map has 0 with entry <0:1>. So add 1, update map: <0:2>
                // i=1, sum is still 0, so 0-0 = 0, map has 0 with entry <0:2>, so add 2. update map: <0:3> 
                // i=2, sum still 0, 0-0=0, map has 0 with entry <0:3>, add 3. update map: <0:4>. Final count = 1+2+3 = 6
                total += map.get(key);
            } 
            map.merge(sum,1,(a,b) -> a+b);
        }
        return total;
    }
}
