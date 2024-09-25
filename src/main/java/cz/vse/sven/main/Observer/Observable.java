package cz.vse.sven.main.Observer;

/**
 * Rozhraní Observable (subjectu)
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public interface Observable {

    void registruj(GameChange gameChange, Observer observer);
}
