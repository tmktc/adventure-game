package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;

/**
 * CommandReturn - returns bottles to the Bottle machine
 */
public class CommandReturn implements ICommand {

    public static final String NAME = "return";
    private GamePlan gamePlan;
    private Backpack backpack;
    private Money money;

    /**
     * Constructor
     *
     * @param gamePlan   of the current game instance
     * @param backpack  where the bottle is located
     * @param money balance that money should be added to
     */
    public CommandReturn(GamePlan gamePlan, Backpack backpack, Money money) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
        this.money = money;
    }

    /**
     * Checks whether the item is located in player's backpack,
     * checks whether the item can be returned,
     * if these conditions are met, the item is returned and money is added to the player's balance.
     *
     * @param parameters item to be returned
     * @return information about the success of the command
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to return.";
        }

        String bottle = parameters[0];
        Area currentArea = gamePlan.getCurrentArea();
        if (currentArea.containsItem("bottleMachine")) {
            if (backpack.containsItem(bottle)) {
                if (backpack.selectItem(bottle).isReturnable()) {
                    String l = backpack.selectItem(bottle).getFullName();
                    backpack.removeItem(bottle);
                    money.addMoney(1);
                    return "You returned " + l + " and got 1 Euro.";
                } else {
                    return "You cannot return this item.";
                }
            } else {
                return "There is no such thing in you backpack.";
            }
        } else {
            return "There is not Bottle machine in here.";
        }
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
