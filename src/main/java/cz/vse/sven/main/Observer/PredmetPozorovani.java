package cz.vse.sven.main.Observer;

/**
 * Rozhraní Observable (subjectu)
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public interface PredmetPozorovani {

    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
