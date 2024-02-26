package cz.vse.sven.logika.hra;

import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.main.Observer.PredmetPozorovani;

/**
 * Rozhraní které musí implementovat hra, je na ně navázáno uživatelské rozhraní
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version únor 2024
 */
public interface IHra extends PredmetPozorovani {
    //== VEŘEJNÉ KONSTANTY =====================================================
    //== DEKLAROVANÉ METODY ====================================================

    /**
     * Vrátí úvodní zprávu pro hráče.
     *
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String vratUvitani();

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     *
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String vratEpilog();

    /**
     * Vrací informaci o tom, zda hra již skončila, je jedno zda výhrou, prohrou nebo příkazem konec.
     *
     * @return vrací true, pokud hra skončila
     */
    public boolean konecHry();

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu.
     *
     * @param radek text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek);


    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public HerniPlan getHerniPlan();

    /**
     * Metoda vrátí odkaz na batoh ve hře
     *
     * @return odkaz na batoh
     */
    public Batoh getBatoh();

    public String getPenize();

    //== ZDĚDĚNÉ METODY ========================================================
    //== INTERNÍ DATOVÉ TYPY ===================================================
}
