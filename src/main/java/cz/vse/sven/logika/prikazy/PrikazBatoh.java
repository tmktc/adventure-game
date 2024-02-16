package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.objekty.Batoh;

/**
 * Třída PrikazBatoh - vypisuje obsah batohu
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazBatoh implements IPrikaz {

    private static final String NAZEV = "batoh";
    private Batoh batoh;

    /**
     * Konstruktor
     *
     * @param batoh jehož obsah se má vypsat
     */
    public PrikazBatoh(Batoh batoh) {
        this.batoh = batoh;
    }

    /**
     * Metoda vrátí seznam věcí v batohu
     *
     * @return seznam věcí v batohu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return batoh.seznamVeci();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
