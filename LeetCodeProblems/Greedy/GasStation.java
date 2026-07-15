package LeetCodeProblems.Greedy;

import java.util.Map;
import java.util.PriorityQueue;

public class GasStation {
    /*
    There are n gas stations in a circle. 
    gas[i] is the fuel you gain at station i, 
    cost[i] is the fuel to travel from i to i+1. 
    Starting with an empty tank, find the starting station index
     that lets you complete the full circle, or return -1 if impossible.
    gas  = [1, 2, 3, 4, 5]
    cost = [3, 4, 5, 1, 2]  → 3

    gas  = [2, 3, 4]
    cost = [3, 4, 3]  → -1

    gas  = [5, 1, 2, 3, 4]
    cost = [4, 4, 1, 5, 1]  → 4
    */

    //assuming equal length for gas and cost
    public static int findStation(int[] gas, int[] cost){
        PriorityQueue<Map.Entry<Integer,Double>> pq =  
            new PriorityQueue<>((e1,e2) -> Double.compare(e1.getValue(), e2.getValue()));

        for(int i=0;i<gas.length;i++){
            pq.add(Map.entry(i,((double)cost[i])/gas[i]));
        }

        int numGasStations = gas.length;
        while(!pq.isEmpty()){
            int starttingGasStation = pq.poll().getKey();
            int currGasStation = starttingGasStation;
            int currGas = 0;
            do{
                currGas += gas[currGasStation];
                currGas -= cost[currGasStation];
                currGasStation = (currGasStation + 1) % numGasStations;
                if(currGas<0) break;
            } while(currGasStation != starttingGasStation);
            if(currGas >= 0 && currGasStation == starttingGasStation) return starttingGasStation;
        }
        return -1;
    }

    public static int findStationBetter(int[] gas, int[] cost){
        int totalGas = 0;
        int totalCost = 0;
        int currGas = 0;
        int currCost = 0;
        int startingStation = 0;
        for(int i=0;i<gas.length;i++){
            totalGas += gas[i];
            totalCost += cost[i];
            currGas += gas[i];
            currCost += cost[i];

            if(currGas<currCost){
                startingStation = i+1;
                currGas = 0;
                currCost = 0;
            }
        }
        return totalGas >= totalCost ? startingStation : -1;
    }

    public static void main(String[] args) {
        int[] g1  = new int[]{1, 2, 3, 4, 5};
        int[] c1  = new int[]{3, 4, 5, 1, 2};

        int[] g2  = new int[]{2,3,4};
        int[] c2  = new int[]{3, 4, 3};

        int[] g3  = new int[]{5,1,2,3,4};
        int[] c3  = new int[]{4,4,1,5,1};

        // System.out.println("G1 (3) : " + findStation(g1, c1));
        // System.out.println("G2 (-1) : " + findStation(g2, c2));
        // System.out.println("G3 (4) : " + findStation(g3, c3));
        
        System.out.println("G1 (3) : "  + findStationBetter(g1, c1));
        System.out.println("G2 (-1) : " + findStationBetter(g2, c2));
        System.out.println("G3 (4) : "  + findStationBetter(g3, c3));
    }
}
