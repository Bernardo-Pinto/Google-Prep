package DataStructures.Week6;

import java.util.UUID;

public abstract class LibraryItem {
    
    //internal system id
    private UUID id;
    // libraryId that owns this item
    //private UUID currentLibraryId;
    private UUID clientUsingThis;

    //bunch of other fields such as:
    //  - date the library got the item
    //  - cost of the item
    //  - where the item came from
    //  - etc
    //not going to implement

    protected LibraryItem(UUID currentLibraryId){
        this.id = UUID.randomUUID();
        //this.currentLibraryId = currentLibraryId;
    }
    public UUID getId(){
        return this.id;
    }
    
    public abstract String getItemId();

    public boolean isAvailable(){
        return this.clientUsingThis == null;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(o instanceof LibraryItem){
            return ((LibraryItem)o).getId().equals(this.getId());
        }
        return false;
    }

    public void setClientUsingThis(UUID clientId){
        this.clientUsingThis = clientId;
    }
}
