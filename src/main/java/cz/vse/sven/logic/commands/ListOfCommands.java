package cz.vse.sven.logic.commands;

import java.util.*;

/**
 * ListOfCommands - keeps track of the list of valid commands
 */
public class ListOfCommands {
    private final Map<String, ICommand> CommandMap;

    /**
     * Constructor
     */
    public ListOfCommands() {
        CommandMap = new HashMap<>();
    }

    /**
     * Inserts a new command into the list of valid commands
     *
     * @param command to be inserted
     */
    public void insertCommand(ICommand command) {
        CommandMap.put(command.getName(), command);
    }

    /**
     * Returns a command from the list of valid commands
     *
     * @param string command keyword
     * @return instance of the command
     */
    public ICommand returnCommand(String string) {
        return CommandMap.getOrDefault(string, null);
    }
}
