package DataStructures.Week6;

import java.util.UUID;

public class Movie extends LendableLibraryItem implements Playable {
    private String upc;
    private String director;
    private boolean playing;

    public Movie(UUID libraryId, String upc, String director) {
        super(libraryId);
        if (upc == null || upc.isEmpty()) throw new IllegalArgumentException();
        this.upc = upc;
        this.director = director;
        this.playing = false;
    }

    @Override
    public String getItemId() {
        return this.upc;
    }

    public String getDirector() {
        return this.director;
    }

    @Override
    public void play() {
        this.playing = true;
    }

    @Override
    public void stop() {
        this.playing = false;
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }
}
