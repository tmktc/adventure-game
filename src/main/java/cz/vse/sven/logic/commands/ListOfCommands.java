package cz.vse.sven.logic.commands;

import java.util.*;

/**
 * ListOfCommands - keeps track of the list of valid commands
 */
public class ListOfCommands {
    private Map<String, ICommand> CommandMap;

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

    /**
     * Checks whether the keyword is a valid command
     *
     * @param string keyword to check
     * @return true - false, false - invalid
     */
    public boolean isValidCommand(String string) {
        return CommandMap.containsKey(string);
    }

    /**
     * Returns list of valid commands sorted alphabetically
     *
     * @return list
     */
    public String returnCommandNames() {
        List<String> commands = new ArrayList<>(CommandMap.keySet());
        Collections.sort(commands);

        String list = "";
        for (String commandWord : commands) {
            list += commandWord + " ";
        }
        return list;

    }
}
