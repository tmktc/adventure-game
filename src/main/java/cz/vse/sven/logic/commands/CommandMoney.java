package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Money;

/**
 * CommandMoney - returns player's money balance
 */
public class CommandMoney implements ICommand {

    private static final String NAME = "money";
    private Money money;

    /**
     * Constructor
     *
     * @param money instance
     */
    public CommandMoney(Money money) {
        this.money = money;
    }

    /**
     * Returns player's money balance
     *
     * @return balance message
     */
    @Override
    public String executeCommand(String... parameters) {
        return "You have " + money.getMoney() + " Euro in your pocket.";
    }

    /**
     * Returns the name of the command
     *
     * @return name of the command
     */
    @Override
    public String getName() {
        return NAME;
    }
}
