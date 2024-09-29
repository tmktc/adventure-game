package cz.vse.sven.logic.commands;

/**
 * CommandHelp - returns the help message
 */
public class CommandHelp implements ICommand {

    private static final String NAME = "help";
    private ListOfCommands validCommands;

    /**
     * Constructor
     *
     * @param validCommands list of valid commands
     */
    public CommandHelp(ListOfCommands validCommands) {
        this.validCommands = validCommands;
    }

    /**
     * Returns helpful information and the list of valid commands
     *
     * @return help message
     */
    @Override
    public String executeCommand(String... parameters) {
        return "The game has four possible endings. Two of which are bad, one good and one perfect.\n" +
                "The game always progresses through talking to an NPC. Pay attention to what NPCs are saying.\n\n" +
                "Available commands: " + validCommands.returnCommandNames();
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
