package DataStructures.Week6;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        UUID libraryId = UUID.randomUUID();
        Library library = new Library();

        // --- Polymorphism: both added as LibraryItem ---
        Book book = new Book(libraryId, "978-0-13-468599-1");
        Movie movie = new Movie(libraryId, "UPC-12345", "Christopher Nolan");

        library.addItem(book);   // LibraryItem reference
        library.addItem(movie);  // LibraryItem reference

        Client alice = new Client();
        Client bob = new Client();
        library.addClient(alice);
        library.addClient(bob);

        // --- isAvailable() — inherited from LibraryItem, overridden in LendableLibraryItem ---
        System.out.println("Book available: " + book.isAvailable());   // true
        System.out.println("Movie available: " + movie.isAvailable()); // true

        // --- checkoutItem: client uses item inside the library ---
        boolean checkedOut = library.checkoutItem(book.getId(), alice.getId());
        System.out.println("Alice checked out book: " + checkedOut);          // true
        System.out.println("Book available after checkout: " + book.isAvailable()); // false

        // trying to checkout same book again fails
        boolean doubleCheckout = library.checkoutItem(book.getId(), bob.getId());
        System.out.println("Bob checkout same book (should fail): " + doubleCheckout); // false

        library.checkInItem(book.getId());
        System.out.println("Book available after check-in: " + book.isAvailable()); // true

        // --- lendItem: client takes item home ---
        boolean lent = library.lendItem(movie.getId(), bob.getId());
        System.out.println("Bob borrowed movie: " + lent);              // true
        System.out.println("Movie available after lending: " + movie.isAvailable()); // false

        // --- Interface: only Movie implements Playable, Book does not ---
        System.out.println("Movie implements Playable: " + (movie instanceof Playable)); // true
        System.out.println("Book implements Playable: " + (book instanceof Playable));   // false

        movie.play();
        System.out.println("Movie is playing: " + movie.isPlaying()); // true
        movie.stop();
        System.out.println("Movie is playing after stop: " + movie.isPlaying()); // false

        // --- getAvailableItems: polymorphic call over mixed list ---
        List<LibraryItem> available = library.getAvailableItems();
        System.out.println("Available items count (book only, movie lent): " + available.size()); // 1

        // return movie
        library.returnItem(movie.getId(), bob.getId());
        System.out.println("Available items after return: " + library.getAvailableItems().size()); // 2
    }
}
