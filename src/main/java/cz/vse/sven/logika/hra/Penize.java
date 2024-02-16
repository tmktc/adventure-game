package cz.vse.sven.logika.hra;

/**
 * Třída Peníze - třída představující peníze hráče
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class Penize {
    private double penize;

    /**
     * Konstruktor - nastavuje, kolik peněz má hráč na začátku hry u sebe
     */
    public Penize() {
        penize = 0;
    }

    /**
     * Metoda vrací počet peněz, které má hráč právě u sebe
     *
     * @return částka
     */
    public double getPenize() {
        return penize;
    }

    /**
     * Metoda přidává hráči peníze
     *
     * @param castka ke přidání
     * @return zpráva, že byly přičteny peníze
     */
    public String pridejPenize(double castka) {
        penize += castka;
        return " - Dostali jste " + castka + " Euro";
    }

    /**
     * Metodá odečítá hráči peníze
     *
     * @param castka k odečtení
     */
    public void odectiPenize(int castka) {
        penize -= castka;
    }
}
