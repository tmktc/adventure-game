package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.objects.Backpack;

/**
 * Třída PrikazBatoh - vypisuje obsah batohu
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandBackpack implements ICommand {

    private static final String NAZEV = "batoh";
    private Backpack backpack;

    /**
     * Konstruktor
     *
     * @param backpack jehož obsah se má vypsat
     */
    public CommandBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    /**
     * Metoda vrátí seznam věcí v batohu
     *
     * @return seznam věcí v batohu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return backpack.seznamVeci();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     * <p>
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
