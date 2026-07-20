package LeetCodeProblems;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * LeetCode #253 - Meeting Rooms II
 *
 * Given an array of meeting time intervals where intervals[i] = [start_i, end_i],
 * return the minimum number of conference rooms required.
 *
 * Constraints:
 *  - 1 <= intervals.length <= 10^4
 *  - 0 <= start_i < end_i <= 10^6
 *
 * Example 1:
 *  Input:  [[0,30],[5,10],[15,20]]
 *  Output: 2
 *
 * Example 2:
 *  Input:  [[7,10],[2,4]]
 *  Output: 1
 */
public class MeetingRoomsII {

    public static int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int rooms = 1;
        int n = intervals.length;
        
        pq.offer(intervals[0][1]);
        for(int j=1;j<n;j++){
            int earliestEnd = pq.peek();
            if(earliestEnd > intervals[j][0]){
                rooms++;
            }else {
                pq.remove();
            }
            pq.offer(intervals[j][1]);
        }
        return rooms;
    }

    public static void main(String[] args) {
        // Expected: 2
        int[][] t1 = {{0,30},{5,10},{15,20}};

        // Expected: 1
        int[][] t2 = {{7,10},{2,4}};

        // Expected: 3  (all three overlap at the same time)
        int[][] t3 = {{1,5},{2,6},{3,7}};

        // Expected: 1  (sequential, no overlap)
        int[][] t4 = {{1,3},{3,6},{6,9}};

        // Expected: 2
        int[][] t5 = {{0,30},{5,10},{15,20},{20,25}};

        // Expected: 1
        int[][] t6 = {{5,8}};
        
        // Expected: 2
        int[][] t7 = {{1,4},{3,5},{4,6},{5,7}};

        System.out.println(minMeetingRooms(t1)); // 2
        System.out.println(minMeetingRooms(t2)); // 1
        System.out.println(minMeetingRooms(t3)); // 3
        System.out.println(minMeetingRooms(t4)); // 1
        System.out.println(minMeetingRooms(t5)); // 2
        System.out.println(minMeetingRooms(t6)); // 1
        System.out.println(minMeetingRooms(t7)); // 2
    }
}
