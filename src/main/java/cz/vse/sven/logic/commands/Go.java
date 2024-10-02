package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Progress;
import cz.vse.sven.logic.objects.NPC;
import cz.vse.sven.logic.objects.Area;

/**
 * CommandGo - moves the player to a neighboring area
 */
public class Go implements ICommand {

    public static final String NAME = "go";
    private final GamePlan gamePlan;
    private final Progress progress;

    /**
     * Constructor
     *
     * @param gamePlan    of the current game instance
     * @param progress of the current game instance
     */
    public Go(GamePlan gamePlan, Progress progress) {
        this.gamePlan = gamePlan;
        this.progress = progress;
    }

    /**
     * Checks whether the area neighbors the current area
     *
     * @param parameters area where the player wants to go
     * @return info message
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must enter the area you want to go to.";
        }

        String direction = parameters[0];

        Area neighboringArea = gamePlan.getCurrentArea().returnNeighboringArea(direction);
        Area currentArea = gamePlan.getCurrentArea();

        if (neighboringArea == null) {
            return "You can not go there from here.";
        } else {
            gamePlan.setCurrentArea(neighboringArea);
            //Kim has to follow us at one point, so we remove him from current area and put him into the one we are about to enter
            if (progress.getProgress() == 4) {
                currentArea.removeNPC("kim");
                neighboringArea.addNPC(new NPC("kim", "Kim"));
            }
            if (progress.getProgress() > 4) {
                if (neighboringArea.getName().equals("soupKitchen")) {
                    neighboringArea.addNPC(new NPC("kim", "Kim"));
                }
                if (!(currentArea.getName().equals("soupKitchen"))) {
                    currentArea.removeNPC("kim");
                }
            }
            return neighboringArea.longDescription();
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
