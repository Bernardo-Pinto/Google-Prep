package DataStructures.Week6;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import DataStructures.Week6.ClientTier.Tier;

public class Client {
    
    private UUID id;
    private List<UUID> borrowedItems;
    private Tier tier;

    public Client(){
        this.id = UUID.randomUUID();
        this.borrowedItems =  new ArrayList<>();
        this.tier = Tier.Silver;
    }

    public boolean increaseTier(){
        if(this.tier == Tier.Platinum) return false;
        if(this.tier == Tier.Silver) this.tier = Tier.Gold;
        if(this.tier == Tier.Gold) this.tier = Tier.Platinum;
        return true;
    }

    public boolean decreaseTier(){
        if(this.tier == Tier.Silver) return false;
        if(this.tier == Tier.Platinum) this.tier = Tier.Gold;
        if(this.tier == Tier.Gold) this.tier = Tier.Silver;
        return true;
    }

    public int getLendingTime(){
        switch (this.tier) {
            case Tier.Silver:
                return 3;
            case Tier.Gold:
                return 7;
            case Tier.Platinum:
                return 14;
            default:
                return -1;
        }
    }

    public UUID getId(){
        return this.id;
    }

    public boolean borrowItem(UUID itemId){
        if(itemId == null) throw new IllegalArgumentException();
        return this.borrowedItems.add(itemId);
    }

    public void returnItem(UUID itemId){
        this.borrowedItems.remove(itemId);
    }
}
