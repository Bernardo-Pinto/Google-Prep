package DataStructures.Week6;

import java.time.LocalDate;
import java.util.UUID;

public abstract class LendableLibraryItem extends LibraryItem {
    private UUID clientIdBorrowingThis;
    private LocalDate dueDate; 
    
    public LendableLibraryItem(UUID libraryId){
        super(libraryId);
    }

    public boolean lendToClient(UUID clientId, int daysUntilDue){
        if(clientId == null || daysUntilDue < 0) throw new IllegalArgumentException();
        if(this.isAvailable()){
            this.clientIdBorrowingThis = clientId;
            this.dueDate = LocalDate.now().plusDays(daysUntilDue);
            return true;
        } else {
            return false;
        }
    }

    public void checkedIn(){
        this.clientIdBorrowingThis = null;
        this.dueDate = null;
    }
    @Override 
    public boolean isAvailable(){
        return (super.isAvailable() && this.clientIdBorrowingThis == null);
    }

    public int daysUntilDue(){
        return LocalDate.now().until(dueDate).getDays();
    }

    public LocalDate getDueDate(){
        return dueDate; //immutable, safe to return it
    }
}
