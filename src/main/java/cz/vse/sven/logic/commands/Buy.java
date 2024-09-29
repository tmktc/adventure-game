package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * CommandBuy - buys an item
 */
public class Buy implements ICommand {

    public static final String NAME = "buy";
    private GamePlan gamePlan;
    private Backpack backpack;
    private Money money;

    /**
     * Constructor
     *
     * @param gamePlan   of the current game instance
     * @param backpack  which the bought item should be put into
     * @param money amount of money that should be subtracted from players balance
     */
    public Buy(GamePlan gamePlan, Backpack backpack, Money money) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
        this.money = money;
    }

    /**
     * Checks whether the item is located in the current area,
     * checks whether the item is purchasable,
     * checks whether player has enough money to buy the item,
     * checks whether player's backpack is not full,
     * if these conditions are met, the purchase is completed.
     * <p>
     * The ability to purchase the item is set to false, because the player already owns the item,
     * the player can also throw away and pick up the item now.
     *
     * @param parameters item to be bought.
     * @return information about the success of the command
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to buy.";
        }

        String itemName = parameters[0];
        Area currentArea = gamePlan.getCurrentArea();

        if (currentArea.containsItem(itemName)) {
            Item item = currentArea.isItemPurchasable(itemName);
            if (item == null) {
                return "You can not buy this item.";
            } else {
                if (money.getMoney() >= item.getPrice()) {
                    if (backpack.putItem(item)) {
                        currentArea.removeItem(itemName);
                        money.subtractMoney(item.getPrice());
                        item.setCanBePickedUp(true);
                        item.setPurchasable(false);
                        return "You bought " + item.getFullName() + " for " + item.getPrice() + " Euro.";
                    } else {
                        return "Not enough space in the backpack.";
                    }
                } else {
                    return "Not enough money to buy this item.";
                }
            }
        }
        return "There is no such item here.";
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
