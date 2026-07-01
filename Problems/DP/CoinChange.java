package Problems.DP;

public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
        int[] tab;
        tab = new int[amount+1];
        for(int i=1;i<amount+1;i++){
            tab[i] = amount+1;
        }

        for(int i=0;i<amount+1;i++){
            for(int j=0;j<coins.length;j++){
                int coin = coins[j];
                if(coin <= i){
                    tab[i] = Math.min(tab[i], tab[i-coin] + 1);
                }
            }
        }

        return tab[amount]>amount ? -1 : tab[amount];
    }
    public static void main(String[] args) {
        int[] t1 =  new int[]{1,2,5};
        System.out.println(coinChange(t1, 11));
    }
}
