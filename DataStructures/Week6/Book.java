package DataStructures.Week6;

import java.util.UUID;

public class Book extends LendableLibraryItem {
    private String ISBN;

    public Book(UUID libraryId, String ISBN){
        super(libraryId);
        //rules to see if isbn is correct.If not, throw exception
        if(ISBN == null || ISBN.isEmpty()) throw new IllegalArgumentException();
        this.ISBN = ISBN;
    }

    @Override
    public String getItemId(){
        return this.ISBN;
    }
}
