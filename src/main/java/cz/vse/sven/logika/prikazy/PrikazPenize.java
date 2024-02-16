package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Penize;

/**
 * Třída PrikazPenize - implementuje pro hru příkaz penize.
 * příkaz peníze vypíše počet peněz, který má hráč u sebe
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazPenize implements IPrikaz {

    private static final String NAZEV = "penize";
    private Penize penize;


    /**
     * Konstruktor
     *
     * @param penize jejichž částku chceme zobrazit
     */
    public PrikazPenize(Penize penize) {
        this.penize = penize;
    }

    /**
     * Metoda vrátí počet peněz, který má hráč u sebe
     *
     * @return hlášku s početem peněz
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "V kapse máte " + penize.getPenize() + " Euro";
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
