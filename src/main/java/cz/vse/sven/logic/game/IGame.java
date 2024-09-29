package cz.vse.sven.logic.game;

import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.main.Observer.Observable;

/**
 * Game interface
 */
public interface IGame extends Observable {

    /**
     * Returns introduction message for the player
     */
    String returnIntroduction();

    /**
     * Returns epilogue message for the player
     */
    String returnEpilogue();

    /**
     * Returns true if the game has ended
     */
    boolean isGameEnd();

    /**
     * Processes the string parameter, splits it into command keyword etc.,
     * checks whether it is a valid command keyword,
     * if the condition is met it executes the command
     * and notifies the observer to a check possible game change.
     *
     * @param line player's input
     * @return returns processed command message to be displayed
     */
    String processCommand(String line);


    /**
     * Returns GamePlan instance
     *
     * @return GamePlan instance
     */
    GamePlan getGamePlan();

    /**
     * Returns Backpack instance
     *
     * @return Backpack instance
     */
    Backpack getBackpack();

    /**
     * Returns Progress instance
     *
     * @return Progress instance
     */
    Progress getProgressInstance();

    /**
     * Returns money balance in the current game instance
     *
     * @return money balance
     */
    String getMoney();
}
