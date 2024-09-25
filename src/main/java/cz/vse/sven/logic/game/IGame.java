package cz.vse.sven.logic.game;

import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.main.Observer.Observable;

/**
 * Rozhraní které musí implementovat hra, je na ně navázáno uživatelské rozhraní
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version únor 2024
 */
public interface IGame extends Observable {

    /**
     * Vrátí úvodní zprávu pro hráče.
     *
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    String returnIntroduction();

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     *
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    String returnEpilogue();

    /**
     * Vrací informaci o tom, zda hra již skončila, je jedno zda výhrou, prohrou nebo příkazem konec.
     *
     * @return vrací true, pokud hra skončila
     */
    boolean gameEnd();

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu.
     *
     * @param radek text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    String processCommand(String radek);


    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    GamePlan getGamePlan();

    /**
     * Metoda vrátí odkaz na batoh ve hře
     *
     * @return odkaz na batoh
     */
    Backpack getBackpack();

    /**
     * Vrátí hodnotu peněz
     *
     * @return hodnota peněz
     */
    String getMoney();

    /**
     * Vrátí instanci třídy peněz, která uchovává stav peněz v aktuální instanci hry
     *
     * @return instance peněz
     */
    Progress getProgressInstance();
}
