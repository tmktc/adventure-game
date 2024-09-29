package cz.vse.sven.main.Observer;

/**
 * Observable interface
 */
public interface Observable {

    void register(GameChange gameChange, Observer observer);
}
