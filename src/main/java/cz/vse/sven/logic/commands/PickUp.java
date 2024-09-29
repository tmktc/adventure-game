package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * CommandPickUp - picks up an item and puts it into player's backpack
 */
public class PickUp implements ICommand {

    public static final String NAME = "pickUp";
    private GamePlan gamePlan;
    private Backpack backpack;

    /**
     * Constructor
     *
     * @param gamePlan  of the current game instance
     * @param backpack which the picked up item should be put into
     */
    public PickUp(GamePlan gamePlan, Backpack backpack) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
    }

    /**
     * Checks whether the item is in the current area,
     * checks whether the item can be picked up,
     * checks whether player's backpack is not full,
     * if these conditions are met, the purchase is completed.
     *
     * @param parameters item to be picked up
     * @return information about the success of the command
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to pick up.";
        }

        String item = parameters[0];
        Area aktualniArea = gamePlan.getCurrentArea();
        if (aktualniArea.containsItem(item)) {
            Item i = aktualniArea.canItemBePickedUp(item);
            if (i == null) {
                return "You can not pick up this item.";
            } else {
                if (backpack.putItem(i)) {
                    aktualniArea.removeItem(item);
                    return "You picked up " + i.getFullName() + ".";
                } else {
                    return "Not enough space in the backpack.";
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
