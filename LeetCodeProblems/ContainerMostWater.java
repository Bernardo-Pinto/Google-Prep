package LeetCodeProblems;

public class ContainerMostWater {
        public int maxArea(int[] height) {
        int n = height.length-1;
        //area is distance between two lines * shortest line
        int left=0;
        int right = n;
        int maxArea = 0;
        while(left<right){
            int currArea = (right-left) * Math.min(height[left],height[right]); 
            if(currArea>maxArea){
                maxArea = currArea;
            }
            if(height[left] < height[right]){
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
