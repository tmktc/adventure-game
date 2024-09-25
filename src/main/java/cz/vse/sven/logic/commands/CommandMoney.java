package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Money;

/**
 * Třída PrikazPenize - implementuje pro hru příkaz penize.
 * příkaz peníze vypíše počet peněz, který má hráč u sebe
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandMoney implements ICommand {

    private static final String NAZEV = "penize";
    private Money money;


    /**
     * Konstruktor
     *
     * @param money jejichž částku chceme zobrazit
     */
    public CommandMoney(Money money) {
        this.money = money;
    }

    /**
     * Metoda vrátí počet peněz, který má hráč u sebe
     *
     * @return hlášku s početem peněz
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "V kapse máte " + money.getPenize() + " Euro";
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
