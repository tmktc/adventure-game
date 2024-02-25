package cz.vse.sven.main.Observer;

/**
 * Rozhraní Observable (subjectu)
 */
public interface PredmetPozorovani {

    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
