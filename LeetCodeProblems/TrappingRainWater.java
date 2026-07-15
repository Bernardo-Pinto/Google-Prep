package LeetCodeProblems;

public class TrappingRainWater {
    public int trap(int[] height) {
        int totalRainArea = 0;
        
        //my own solution
        /*
        int n = 0;
        for(int i=0;i<height.length-1;i++){
            while(i < height.length-2 && height[i+1] >= height[i]) i++;
            n = i+1;
            int maxEl = height[n];
            int maxIndex = n;
            while(n<height.length-1){
                n++;
                if(maxEl < height[n]){
                    maxEl = height[n];
                    maxIndex = n;
                }
                if(height[n-1] < height[n] && height[n] >= height[i]) break;
            } 
            if(maxEl == height[i+1]) continue;
            totalRainArea += (maxIndex-i-1) * Math.min(height[i],maxEl);
            for(int k=i+1;k<maxIndex;k++){
                totalRainArea -= height[k];
            }
            i = maxIndex-1;
        }*/

        //two pointers
        int left = 0;
        int right = height.length-1;
        int leftMax = height[left];
        int rightMax = height[right];
        while(left<right){
            if(leftMax <= rightMax){
                totalRainArea += leftMax - height[left];
                left++;
                leftMax = Math.max(leftMax,height[left]);
            } else {
                totalRainArea += rightMax - height[right];
                right--;
                rightMax = Math.max(rightMax,height[right]);
            }
        }

        return totalRainArea;
    }
}
