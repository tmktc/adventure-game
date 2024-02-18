package cz.vse.sven.main;

/**
 * Rozhraní Observable (subjectu)
 */
public interface PredmetPozorovani {

    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
