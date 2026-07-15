package LeetCodeProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sum3 {
     public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int left = 0;
        int mid = left+1;
        int right = nums.length-1;
        int limit = nums.length - 3;
        while(left <= limit){
            if(right<=mid){
                left++;
                while(left <= limit && nums[left] == nums[left-1]) left++;
                mid=left+1;
                right=nums.length-1;
                if(left+2>=nums.length)break;
            } 
        
            int sum = nums[left] + nums[mid] + nums[right];
            if(sum == 0){
                result.add(Arrays.asList(nums[left],nums[mid],nums[right]));
                mid++;
                while(mid < right && nums[mid-1] == nums[mid]) mid++;
                while(mid < right && nums[right-1] == nums[right]) right--;
            } else if(sum > 0){
                right--;
            } else {
                mid++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums1 =  new int[]{-100,-70,-60,110,120,130,160};
        int[] nums2 =  new int[]{-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6};
        System.out.println(threeSum(nums2));
    }
}
