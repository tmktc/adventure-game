package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * CommandThrowAway - puts an item from the backpack into the area
 */

public class ThrowAway implements ICommand {

    public static final String NAME = "throwaway";
    private GamePlan gamePlan;
    private Backpack backpack;

    /**
     * Constructor
     *
     * @param gamePlan  of the current game instance
     * @param backpack where the item is
     */
    public ThrowAway(GamePlan gamePlan, Backpack backpack) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
    }

    /**
     * Checks whether the item is in the backpack,
     * checks whether player is not located in lidl or kiosk,
     * if the conditions are met, the item is put into the area
     *
     * @param parameters item to be thrown away
     * @return information about the success of the command
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the item you want to throw away.";
        }

        String itemName = parameters[0];
        Area currentArea = gamePlan.getCurrentArea();
        if (backpack.containsItem(itemName)) {

            if (!(currentArea.getName().equals("lidl") || currentArea.getName().equals("kiosk"))) {
                Item item = backpack.selectItem(itemName);
                backpack.removeItem(itemName);
                currentArea.addItem(item);
                return item.getFullName() + " now lies on the ground.";
            }
            return "You can not throw away items in this area.";


        }
        return "There is no such thing in you backpack.";
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
