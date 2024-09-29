package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;

/**
 * CommandEnd - ends the current game instance
 */

public class CommandEnd implements ICommand {

    private static final String NAME = "end";

    private Game game;

    /**
     * Constructor
     *
     * @param game instance that should be ended
     */
    public CommandEnd(Game game) {
        this.game = game;
    }

    /**
     * Ends the game instance
     *
     * @return info message
     */
    @Override
    public String executeCommand(String... parameters) {

        game.setGameEnd();
        return "The game was ended through a command. Thank you for playing.";
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
