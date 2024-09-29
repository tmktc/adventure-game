package cz.vse.sven.logic.game;

/**
 * Money - keeps track of player's money balance
 */
public class Money {
    private double money;

    /**
     * Constructor
     */
    public Money() {
        money = 0;
    }

    /**
     * Returns current money balance
     *
     * @return sum
     */
    public double getMoney() {
        return money;
    }

    /**
     * Adds money to the balance
     *
     * @param sum to be added
     * @return info message
     */
    public String addMoney(double sum) {
        money += sum;
        return " - You received " + sum + " Euro.";
    }

    /**
     * Subtracts money from the balance
     *
     * @param sum to be subtracted
     */
    public void subtractMoney(int sum) {
        money -= sum;
    }

    /**
     * toString method
     *
     * @return money balance status
     */
    @Override
    public String toString() {
        return String.valueOf(money);
    }
}
