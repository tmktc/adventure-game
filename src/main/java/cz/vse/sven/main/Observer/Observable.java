package cz.vse.sven.main.Observer;

/**
 * Rozhraní Observable (subjectu)
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public interface Observable {

    void register(GameChange gameChange, Observer observer);
}
