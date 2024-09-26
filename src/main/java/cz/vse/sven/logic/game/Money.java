package cz.vse.sven.logic.game;

/**
 * Třída Peníze - třída představující peníze hráče
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class Money {
    private double money;

    /**
     * Konstruktor - nastavuje, kolik peněz má hráč na začátku hry u sebe
     */
    public Money() {
        money = 0;
    }

    /**
     * Metoda vrací počet peněz, které má hráč právě u sebe
     *
     * @return částka
     */
    public double getMoney() {
        return money;
    }

    /**
     * Metoda přidává hráči peníze
     *
     * @param sum ke přidání
     * @return zpráva, že byly přičteny peníze
     */
    public String addMoney(double sum) {
        money += sum;
        return " - You received " + sum + " Euro.";
    }

    /**
     * Metodá odečítá hráči peníze
     *
     * @param sum k odečtení
     */
    public void subtractMoney(int sum) {
        money -= sum;
    }

    /**
     * toString metoda Peněz
     *
     * @return hodnota peněz
     */
    @Override
    public String toString() {
        return String.valueOf(money);
    }
}
