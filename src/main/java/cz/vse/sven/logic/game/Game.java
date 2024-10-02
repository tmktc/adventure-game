package cz.vse.sven.logic.game;

import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.commands.*;
import cz.vse.sven.main.Observer.Observer;
import cz.vse.sven.main.Observer.GameChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Game - main logic class
 * It creates GamePlan instance, which initializes game objects and creates valid command list.
 * It displays introduction and epilogue messages.
 * It processes commands entered by the player.
 */

public class Game implements IGame {

    private final ListOfCommands validCommands;
    private final GamePlan gamePlan;
    private boolean gameEnd = false;
    private final Backpack backpack;
    private final Money money;
    private final Progress progress;
    private final Map<GameChange, Set<Observer>> listOfObservers = new HashMap<>();

    /**
     * Creates game and initializes objects (through GamePlan class), list of valid commands and list of observers.
     */
    public Game() {
        gamePlan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
        progress = new Progress();
        validCommands = new ListOfCommands();
        validCommands.insertCommand(new Go(gamePlan, progress));
        validCommands.insertCommand(new PickUp(gamePlan, backpack));
        validCommands.insertCommand(new Buy(gamePlan, backpack, money));
        validCommands.insertCommand(new Talk(gamePlan, backpack, money, progress, this));
        validCommands.insertCommand(new Return(gamePlan, backpack, money));
        validCommands.insertCommand(new ThrowAway(gamePlan, backpack));
        for (GameChange gameChange : GameChange.values()) {
            listOfObservers.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Returns epilogue message for the player
     */
    public String returnEpilogue() {
        String epilogue = "";
        if (gamePlan.isPerfectWin()) {
            epilogue = """
                    You managed to obtain food for everyone and Sven bought snus.
                    a Perfect win, congratulations.
                    """;
        }
        if (gamePlan.isWin()) {
            epilogue = """
                    You managed to obtain food for Peppa and yourself.
                    Kim will be hungry today - you can do better next time.
                    a Win, good job.
                    """;
        }
        if (gamePlan.isLoss()) {
            if (progress.getProgress() == 3) {
                epilogue = """
                        Sven got beat by the thief. The thief managed to escape with the stolen clothes.\

                        a Loss, better luck next time.
                        """;
            } else {
                epilogue = """
                        You did not manage to obtain food for Peppa and yourself in time.\

                        a Loss, better luck next time.
                        """;
            }
        }
        return epilogue;
    }

    /**
     * Processes the string parameter, splits it into command keyword etc.,
     * checks whether it is a valid command keyword,
     * if the condition is met it executes the command
     * and notifies the observer to a check possible game change.
     *
     * @param keyWord player's input
     * @return returns processed command message to be displayed
     */
    public String processCommand(String keyWord, String parameter) {
        ICommand command = validCommands.returnCommand(keyWord);
        String message = command.executeCommand(parameter);

        notifyObserver(GameChange.ITEM_CHANGE);
        notifyObserver(GameChange.NPC_CHANGE);
        notifyObserver(GameChange.MONEY_CHANGE);

        return message;
    }

    /**
     * Returns true if the game has ended
     */
    public boolean isGameEnd() {
        return gameEnd;
    }

    /**
     * Sets the game end and notifies the observer
     */
    public void setGameEnd() {
        this.gameEnd = true;
        notifyObserver(GameChange.GAME_END);
    }

    /**
     * Returns GamePlan instance
     *
     * @return GamePlan instance
     */
    public GamePlan getGamePlan() {
        return gamePlan;
    }

    /**
     * Returns Backpack instance
     *
     * @return Backpack instance
     */
    public Backpack getBackpack() {
        return backpack;
    }

    /**
     * Returns Progress instance
     *
     * @return Progress instance
     */
    public Progress getProgressInstance() {
        return progress;
    }

    /**
     * Returns money balance in the current game instance
     *
     * @return money balance
     */
    public String getMoney() {
        return money.toString();
    }

    /**
     * Adds observer to the list of observers of given game change
     *
     * @param observer to be registered
     */
    @Override
    public void register(GameChange gameChange, Observer observer) {
        listOfObservers.get(gameChange).add(observer);
    }

    /**
     * When called, the update method for every observer in the list for a given game change is called.
     */
    private void notifyObserver(GameChange gameChange) {
        for (Observer observer : listOfObservers.get(gameChange)) {
            observer.update();
        }
    }
}
